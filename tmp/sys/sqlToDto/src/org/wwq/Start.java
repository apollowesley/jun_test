package org.wwq;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.gaofeng.common.CommonFunction;
import org.gaofeng.common.MappingRule;
import org.gaofeng.common.db.ConnectionPool;
import org.gaofeng.common.db.DBHelper;

public class Start extends CommonFunction {
	static ConnectionPool pool = null;
	public static void main(String[] args) throws Exception {
		StringBuffer sb=new StringBuffer();
		System.out.println("处理开始");
		System.out.println("正在查询所有手机号");
		List<String> listPhone = findAllPhone();
		System.out.println("一共有"+listPhone.size()+"条手机号需要处理");
	    for (int i=0;i<listPhone.size();i++){
	    	sb.append(listPhone.get(i)+"\t");
	    	System.out.println("正在处理第"+(i+1)+"/"+listPhone.size()+"条手机号手机号为："+listPhone.get(i));
	    	List<String> listTmmo=findTmmoByPhone(listPhone.get(i));
	    	System.out.println("该手机号一共发过"+listTmmo.size()+"条短信");
	    	List<Integer> listDuanShu=duanshu(listTmmo);
	    	System.out.println("共"+listDuanShu.size()+"段");
	    	sb.append(listDuanShu.size()+"\t");
	    	for (int j=0;j<listDuanShu.size();j++){
	    		System.out.println("第"+(j+1)+"段有"+listDuanShu.get(j)+"条");
	    		sb.append(listDuanShu.get(j));
	    		if(j!=listDuanShu.size()-1){
	    			sb.append(",");
	    		}
	    	}
	    	sb.append("\n");
	    }
	    System.out.println(sb);
	    MappingRule.makeFile("D:\\test.txt", sb+"");
	    pool.closePool();
	}

	/**
	 * 查找所有的手机号，去除重复
	 * @return
	 * @throws Exception
	 */
	public static List<String> findAllPhone() throws Exception 	{
		List<String> listReturn = new ArrayList<String>();
		sql = "select t.mobilenumber from huiyuanzhuanfa_10 t group by t.mobilenumber";
		db = new DBHelper(sql);
		ret = db.pst.executeQuery();
		while (ret.next()) {
			String phoneNo = ret.getString(1);
			listReturn.add(phoneNo);
		}
		db.close();
		ret.close();
		return listReturn;

	}
	/**
	 * 通过手机号查找次手机号发送的所有的短信时间
	 * @param phone
	 * @return
	 * @throws Exception
	 */
	public static List<String> findTmmoByPhone(String phone) throws Exception 	{
		List<String> listReturn = new ArrayList<String>();
		sql = "select t.tmmo from huiyuanzhuanfa_10 t where t.mobilenumber='"+phone+"' ORDER BY t.tmmo";
		
		 pool = ConnectionPool.getInstance();
         Connection conn = pool.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery(sql);
		
		while (rs.next()) {
			String phoneNo = rs.getString(1);
			listReturn.add(phoneNo);
		}
		rs.close();
        stmt.close();
        pool.release(conn);
		return listReturn;
	}
	public static List<Integer> duanshu(List<String> listTmmo) throws Exception{
		List<Integer> listTiaoShu=new ArrayList<Integer>();
		int tiaoShu=0;
		for (int i=0;i<listTmmo.size();i++){
			if(i==0){
				tiaoShu=1;
			}else if((parseLong(listTmmo.get(i))-parseLong(listTmmo.get(i-1)))>60000){
				listTiaoShu.add(tiaoShu);
				tiaoShu=1;
			}else{
				tiaoShu++;
			}
			
			if(i==listTmmo.size()-1){
				listTiaoShu.add(tiaoShu);
			}
			
		}
		return listTiaoShu;
	}
	public static  Long parseLong( String dateValue) throws Exception {

		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long timeStart=sdf.parse(dateValue).getTime();
		return timeStart;
	}

}
