package gome.DHResend;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gaofeng.common.CommonFunction;
import org.gaofeng.common.MappingRule;

/**
 * 重推DH订单
 * @author gaofeng
 *
 */
public class StartDHAL extends CommonFunction {
	//文本读取出来的订单号
	static List<String> listOrderNums = new ArrayList<String>();
	static StringBuffer sql = new StringBuffer();
	static int batch=30;
	//去空格去重后的订单号
	static List<String> listInsert = new ArrayList<String>();
	public static void main(String[] args) throws Exception {
		File file = new File(".\\src");
		String filePath = file.getCanonicalPath()
				+ "\\gome\\DHResend\\";
		// 获得单号
		readTxtFile(filePath+"OrderNum.txt");
		// 单号去重去空格
		check(listOrderNums);
		// 插入tnt_pending
		insertTntPendingBath(listInsert);
		// update tnt_status_restrict
		updateTntStatusRestrict(listInsert);
		MappingRule.makeFile(filePath+"sql.txt", sql.toString());
		System.out.println("执行完毕");
	}

	/**
	 * 批量插入数据库
	 * 
	 * @param listInsert
	 * @throws Exception
	 */
	public static void insertTntPendingBath(List<String> listInsert)
			throws Exception {
		sql.append(" insert into tnt_pending(order_num,buid,status_code,reason_code,source_code,status_date,trigger_date) ");
		for (int i = 0; i < listInsert.size(); i++) {
			if (i % batch == batch-1) {
				sql.append(" select '"
						+ listInsert.get(i)
						+ "',8270,'DHAL','OF','HQSAP',sysdate,sysdate-1 from dual ");
				sql.append(";\n");
				if(i!=(listInsert.size() - 1)){
					sql.append(" insert into tnt_pending(order_num,buid,status_code,reason_code,source_code,status_date,trigger_date) ");
				}
			} else if (i == (listInsert.size() - 1)) {
				sql.append(" select '"
						+ listInsert.get(i)
						+ "',8270,'DHAL','OF','HQSAP',sysdate,sysdate-1 from dual ");
				sql.append(";\n");
			} else {
				sql.append(" select '"
						+ listInsert.get(i)
						+ "',8270,'DHAL','OF','HQSAP',sysdate,sysdate-1 from dual ");
				sql.append(" union all  ");
			}
		}
	}
	/**
	 * 批量更新TntStatusRestrict
	 * @param listUpdate
	 * @throws Exception
	 */
	public static void updateTntStatusRestrict(List<String> listUpdate)
			throws Exception {
		sql.append(" UPDATE tnt_status_restrict tt SET tt.next_allow_status= tt.next_allow_status || ',DHAL' WHERE tt.order_num in (");
		for (int i = 0; i < listInsert.size(); i++) {
			if (i % batch == batch-1) {
				sql.append(" '"+listUpdate.get(i)+"' ");
				sql.append(" ) ");
				sql.append(";\n");
				if(i!=(listUpdate.size() - 1)){
					sql.append(" UPDATE tnt_status_restrict tt SET tt.next_allow_status= tt.next_allow_status || ',DHAL' WHERE tt.order_num in (");
				}
			} else if (i == (listUpdate.size() - 1)) {
				sql.append(" '"+listUpdate.get(i)+"' ");
				sql.append(" ) ");
				sql.append(";\n");
			} else {
				sql.append(" '"+listUpdate.get(i)+"' ");
				sql.append(" , ");
			}
		}
	}
	/**
	 * 读取TXT文件内容
	 * @param filePath
	 */
	public static void readTxtFile(String filePath) {
		try {
			String encoding = "GBK";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					listOrderNums.add(lineTxt);
				}
				read.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}

	}

	/**
	 * 去空格去重复
	 * @param list
	 */
	public static void check(List<String> list) {
		if (list == null || list.size() == 0) {
			return;
		}
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		for (int i = 0; i < list.size(); i++) {
			map.put(list.get(i).replaceAll(" ", ""), true);
		}
		Object s[] = map.keySet().toArray();
		for (int i = 0; i < map.size(); i++) {
			if (s[i] != null && !"".equals(s[i])) {
				listInsert.add((String) s[i]);
			}
		}
	}
}
