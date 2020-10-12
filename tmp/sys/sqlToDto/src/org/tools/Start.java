package org.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.gaofeng.common.db.ConnectionPool;

/**
 * 将txt导入到数据库
 * @author gaofeng
 *
 */
public class Start {
	/**
	 * 怕你写错了
	 */
	private static String stringType="String";//一般不用动number类型可以用此类型
	private static String dateType="Date";//一般不用动
	private static String dateFormat="yyyy-MM-dd HH24:mi:ss";//一般不用动
	
	
	/**
	 * 一下四项需要修改
	 */
	//表名
	private static String tableName="kkk";
	//字段名
	private static String[] colName={"a","b","c","d","e","f","g","h","i"};
	//字段类型
	private static String[] colType={stringType,stringType,stringType,stringType,stringType,stringType,stringType,stringType,dateType};
	//文件路径
	private static String filePath="D:\\新建文本文档.txt";
	
	private static int pageSize=50;
	private static ConnectionPool pool  = ConnectionPool.getInstance();
	private static Connection conn=pool.getConnection();
	public static void main(String[] args) {
		if(colName.length!=colType.length){
			System.out.println("字段名或者字段类型数量不对");
			return;
		}
		List<String> lintError=new ArrayList<String>();
		String lineTxt = null;
		try {
			String encoding = "GBK";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				lineTxt = bufferedReader.readLine();
				List<String> listInsert=new ArrayList<String>();
				while ((lineTxt = bufferedReader.readLine()) != null) {
					if(lineTxt!=null && !"".equals(lineTxt)){
						if(lineTxt.split(",").length!=colName.length){
							lintError.add(lineTxt);
						}else{
							listInsert.add(lineTxt);
							if(listInsert.size()>pageSize){
								insertBath(listInsert);
								listInsert.clear();
							}
						}
					}
				}
				if(listInsert.size()!=0){
					insertBath(listInsert);
				}
				for(int i=0;i<lintError.size();i++){
					System.out.println(lintError.get(i));
				}
				if (pool != null) {
					pool.closePool();
				}
				read.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("error="+lineTxt);
			e.printStackTrace();
		}
	}
	private static void insertBath(List<String> listInsert) throws SQLException {
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("insert into "+tableName+" (");
			for(int i=0;i<colName.length;i++){
				sql.append(colName[i]);
				if(i!=colName.length-1){
					sql.append(",");
				}
			}
			sql.append(")   ");
			
			
			String tempLine="";
			String[] tempArr=new String[colName.length];
			for (int i=0;i<listInsert.size();i++){
				if(colType[0].equals(dateType)){
					sql.append(" select ");
				}else{
					sql.append(" select '");
				}
				tempLine=listInsert.get(i);
				tempArr=tempLine.split(",");
				for(int j=0;j<tempArr.length;j++){
					if(colType[j].equals(dateType)){
						sql.append("to_date('"+tempArr[j]+"','"+dateFormat+"')");
					}else{
						sql.append(tempArr[j]);
					}
					
					if(j!=tempArr.length-1){
						if(!colType[j].equals(dateType)){
							sql.append("'");
						}
						sql.append(",");
						if(!colType[j+1].equals(dateType)){
							sql.append("'");
						}
					}
				}
				if(!colType[colType.length-1].equals(dateType)){
					sql.append("'");
				}
				sql.append(" from dual ");
				if(i!=listInsert.size()-1){
					sql.append(" union all  ");
				}
			}
			PreparedStatement stmt = conn.prepareStatement(sql.toString());
			stmt.execute(sql.toString());
			stmt.close();
			pool.release(conn);
		} catch (Exception e) {
			for(int i=0;i<listInsert.size();i++){
				System.out.println(listInsert.get(i));
			}
		}
		
	}
}
