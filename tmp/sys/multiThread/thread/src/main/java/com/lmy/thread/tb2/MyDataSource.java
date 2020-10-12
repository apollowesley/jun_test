package com.lmy.thread.tb2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;

public class MyDataSource {
	private LinkedList<Connection> pool=new LinkedList<>();
	private static final int MAX_CONNECTIONS=10;
	private static final String URL="";
	private static final String USER="";
	private static final String PASSWORD="";
	static{
		try {
			Class.forName("com.sql.driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public MyDataSource(){
		for(int i=0;i<MAX_CONNECTIONS;i++){
			try {
				Connection conn=DriverManager.getConnection(URL, USER, PASSWORD);
				pool.addLast(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public Connection getConnection(){
		Connection result=null;
		synchronized (pool) {
			while(pool.size()<=0){
				try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(!pool.isEmpty()){
				result=pool.removeFirst();
			}
		}
		return result;
	}
	public void release(Connection conn){
		if(conn!=null){
			synchronized (pool) {
				pool.addLast(conn);
				notifyAll();
			}
		}
	}
}
