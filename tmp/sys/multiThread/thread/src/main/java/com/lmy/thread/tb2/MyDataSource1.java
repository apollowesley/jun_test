package com.lmy.thread.tb2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyDataSource1 {
	private LinkedList<Connection> pool=new LinkedList<>();
	private static final int MAX_CONNECTIONS=10;
	private static final String URL="";
	private static final String USER="";
	private static final String PASSWORD="";
	private Lock lock=new ReentrantLock();
	private Condition a=lock.newCondition();
	static{
		try {
			Class.forName("com.sql.driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public MyDataSource1(){
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
		lock.lock();
		try {
			while(pool.size()<=0){
				try {
					a.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(!pool.isEmpty()){
				result=pool.removeFirst();
			}
			return result;
		} finally{
			lock.unlock();
		}
	}
	public void release(Connection conn){
		if(conn!=null){
			lock.lock();
			try{
				pool.addLast(conn);
				a.signal();
			}finally{
				lock.unlock();
			}
		}
	}
}
