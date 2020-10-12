package org.wwq;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.gaofeng.common.CommonFunction;
import org.gaofeng.common.MappingRule;
import org.gaofeng.common.db.ConnectionPool;
import org.gaofeng.common.db.DBHelper;

public class StartNew extends CommonFunction {
	/**
	 * 是否插入数据库
	 */
	static Boolean ifInsert = true;
	/**
	 * 是否生成文本
	 */
	static Boolean ifMakeFile = false;
	/**
	 * 声明数据库连接池
	 */
	static ConnectionPool pool = null;

	public static void main(String[] args) throws Exception {
		System.out.println("开始处理,正在查询数据...");
		// 记录时间处理开始时间
		Long a = System.currentTimeMillis();
		// 获取所有按照手机号和时间排序的数据集
		List<String[]> listPhone = findAllPhone();
		System.out.println("数据查询完毕。。。");
		System.out.println("数据查询用时：" + (System.currentTimeMillis() - a) / 1000
				+ "s");
		// 处理结果集
		List<String[]> listResult = getListResult(listPhone);

		System.out.println("逻辑处理完成，用时："+ ((System.currentTimeMillis() - a) / 1000) + "s");
		if (ifMakeFile) {
			System.out.println("正在生产本地文件：");
			long aa=System.currentTimeMillis();
			System.out.println("正在组织数据...");
			String sb=buidFileStr(listResult);
			System.out.println("组织数据完成...正在生成本地文件");
			MappingRule.makeFile("D:\\test.txt", sb + "");
			System.out.println("本地文件已经生成");
			System.out.println("生成本地文件用时:"+ (System.currentTimeMillis() - aa)/1000+"s");
		}
		if (ifInsert) {
			System.out.println("正在插入数据库：");
			System.out.println("共"+listResult.size()+"条");
			insertTableBath(listResult);
			if (pool != null) {
				pool.closePool();
			}
		}
		System.out.println("全部处理完成,总共用时：" + (System.currentTimeMillis() - a)/ 1000 + "s");
	}

	/**
	 * 将处理结果集转成字符串格式,用作生成本地文件
	 * @param listResult
	 * @return
	 */
	private static String buidFileStr(List<String[]> listResult) {
		StringBuffer sb =new StringBuffer();
		for (int i=0;i<listResult.size();i++){
			sb.append(listResult.get(i)[0]);
			sb.append("\t");
			sb.append(listResult.get(i)[1]);
			sb.append("\t");
			sb.append(listResult.get(i)[2]);
			sb.append("\n");
		}
		return sb.toString();
	}

	/**
	 * 此程序的核心逻辑,将数据库的数据分析成所需内容
	 * @param listPhone
	 * @return
	 * @throws Exception
	 */
	private static List<String[]> getListResult(List<String[]> listPhone)
			throws Exception {

		List<String[]> listResult = new ArrayList<String[]>();
		String[] insert = new String[3];
		List<String> listTmmo = new ArrayList<String>();
		for (int i = 0; i < listPhone.size(); i++) {
			if (i == 0) {
				listTmmo.add(listPhone.get(i)[1]);
			} else if (listPhone.get(i)[0].equals(listPhone.get(i - 1)[0])) {
				listTmmo.add(listPhone.get(i)[1]);
			} else {
				insert[0] = listPhone.get(i - 1)[0];
				List<Integer> listDuanShu = duanshu(listTmmo);
				insert[1] = listDuanShu.size() + "";
				StringBuffer tmmo = new StringBuffer();
				for (int j = 0; j < listDuanShu.size(); j++) {
					tmmo.append(listDuanShu.get(j) + "");
					if (j != listDuanShu.size() - 1) {
						tmmo.append(",");
					}
				}
				insert[2] = tmmo + "";
				listResult.add(insert);
				insert = new String[3];
				listTmmo = new ArrayList<String>();
				listTmmo.add(listPhone.get(i)[1]);
			}
			if (i == listPhone.size() - 1) {
				insert[0] = listPhone.get(i - 1)[0];
				List<Integer> listDuanShu = duanshu(listTmmo);
				insert[1] = listDuanShu.size() + "";
				StringBuffer tmmo = new StringBuffer();
				for (int j = 0; j < listDuanShu.size(); j++) {
					tmmo.append(listDuanShu.get(j) + "");
					if (j != listDuanShu.size() - 1) {
						tmmo.append(",");
					}
				}
				insert[2] = tmmo + "";
				listResult.add(insert);
				insert = new String[3];
				listTmmo = new ArrayList<String>();
				listTmmo.add(listPhone.get(i)[1]);
			}
		}
		return listResult;
	}

	/**
	 * 查询所有数据,按照手机号和时间排序
	 * 
	 * @return
	 * @throws Exception
	 */
	public static List<String[]> findAllPhone() throws Exception {

		List<String[]> listReturn = new ArrayList<String[]>();
		sql = "select t.mobilenumber,t.tmmo from huiyuanzhuanfa_10 t order by t.mobilenumber,t.tmmo";
		db = new DBHelper(sql);
		ret = db.pst.executeQuery();
		while (ret.next()) {
			String[] velu = new String[2];
			velu[0] = ret.getString(1);
			velu[1] = ret.getString(2);
			listReturn.add(velu);
		}
		db.close();
		ret.close();
		return listReturn;

	}

	/**
	 * 用一个手机号的所有发送时间计算段数和每段的条数
	 * 
	 * @param listTmmo
	 * @return
	 * @throws Exception
	 */
	public static List<Integer> duanshu(List<String> listTmmo) throws Exception {
		List<Integer> listTiaoShu = new ArrayList<Integer>();
		int tiaoShu = 0;
		for (int i = 0; i < listTmmo.size(); i++) {
			if (i == 0) {
				tiaoShu = 1;
			} else if ((parseLong(listTmmo.get(i)) - parseLong(listTmmo
					.get(i - 1))) > 60000) {
				listTiaoShu.add(tiaoShu);
				tiaoShu = 1;
			} else {
				tiaoShu++;
			}

			if (i == listTmmo.size() - 1) {
				listTiaoShu.add(tiaoShu);
			}
		}
		return listTiaoShu;
	}

	/**
	 * 插入数据库
	 * @param listInsert
	 * @throws Exception
	 */
	public static void insertTable(List<String[]> listInsert) throws Exception {
		System.out.println("正在插入数据");
		for (int i = 0; i < listInsert.size(); i++) {
			sql = "insert into gfTemp (phone,duanshu,tiaoshu) values('"
					+ listInsert.get(i)[0] + "','" + listInsert.get(i)[1]
					+ "','" + listInsert.get(i)[2] + "')";
			pool = ConnectionPool.getInstance();
			Connection conn = pool.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.execute(sql);
			stmt.close();
			pool.release(conn);
		}
	}

	/**
	 * 批量插入数据库
	 * @param listInsert
	 * @throws Exception
	 */
	public static void insertTableBath(List<String[]> listInsert)
			throws Exception {
		StringBuffer sql = new StringBuffer();
		pool = ConnectionPool.getInstance();
		System.out.println("正在插入数据");
		Long a = System.currentTimeMillis();
		sql.append("insert into gfTemp (phone,duanshu,tiaoshu)   ");
		for (int i = 0; i < listInsert.size(); i++) {
			if (i % 100 == 0) {
				sql.append(" select '" + listInsert.get(i)[0] + "','"
						+ listInsert.get(i)[1] + "','" + listInsert.get(i)[2]
						+ "' from dual ");
				// System.out.println(sql.toString());
				pool = ConnectionPool.getInstance();
				Connection conn = pool.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql.toString());
				stmt.execute(sql.toString());
				stmt.close();
				pool.release(conn);
				sql.setLength(0);
				sql.append(" insert into gfTemp (phone,duanshu,tiaoshu)   ");
			} else if (i == (listInsert.size() - 1)) {
				sql.append(" select '" + listInsert.get(i)[0] + "','"
						+ listInsert.get(i)[1] + "','" + listInsert.get(i)[2]
						+ "' from dual ");
				// System.out.println(sql.toString());
				pool = ConnectionPool.getInstance();
				Connection conn = pool.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql.toString());
				stmt.execute(sql.toString());
				stmt.close();
				pool.release(conn);
			} else {
				sql.append(" select '" + listInsert.get(i)[0] + "','"
						+ listInsert.get(i)[1] + "','" + listInsert.get(i)[2]
						+ "' from dual ");
				sql.append(" union all  ");
			}
		}
		System.out.println("total Times:" + (System.currentTimeMillis() - a)/ 1000 + "s");
	}

	public static Long parseLong(String dateValue) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long timeStart = sdf.parse(dateValue).getTime();
		return timeStart;
	}

}
