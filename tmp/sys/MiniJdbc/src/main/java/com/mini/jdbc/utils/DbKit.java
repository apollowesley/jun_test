package com.mini.jdbc.utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mini.jdbc.MiniDaoException;

/**
 * DbKit
 */
@SuppressWarnings("rawtypes")
public final class DbKit {
	
	
	static final Object[] NULL_PARA_ARRAY = new Object[0];
	public static final String MAIN_CONFIG_NAME = "main";
	
	private DbKit() {}
	
	public static String replaceFormatSqlOrderBy(String sql) {
		sql = sql.replaceAll("(\\s)+", " ");
		int index = sql.toLowerCase().lastIndexOf("order by");
		if (index > sql.toLowerCase().lastIndexOf(")")) {
			String sql1 = sql.substring(0, index);
			String sql2 = sql.substring(index);
			sql2 = sql2.replaceAll("[oO][rR][dD][eE][rR] [bB][yY] [\u4e00-\u9fa5a-zA-Z0-9_.]+((\\s)+(([dD][eE][sS][cC])|([aA][sS][cC])))?(( )*,( )*[\u4e00-\u9fa5a-zA-Z0-9_.]+(( )+(([dD][eE][sS][cC])|([aA][sS][cC])))?)*", "");
			return sql1 + sql2;
		}
		return sql;
	}
	
	public static String replaceFormatSqlFrom(String sql) {
		  List<Integer> left = new ArrayList<Integer>();
		  List<Integer> right = new ArrayList<Integer>();
		  List<Integer> from = new LinkedList<Integer>();
		  
		  //获取FROM
		  Pattern pattern2 = Pattern.compile("from");
		  Matcher matcher2 = pattern2.matcher(sql.toLowerCase());
		  while(matcher2.find())
	      {
			  from.add(matcher2.start());
	      }		
		  
		  if(from.size()==1){
			  int index = sql.toLowerCase().indexOf("from");
			  int _index = sql.toLowerCase().lastIndexOf("(");
			  if(_index<0||_index>index){
			      return sql.substring(index);
			  }
		  }else{
			  //获取左括号
			  Pattern pattern = Pattern.compile("\\(");
			  Matcher matcher = pattern.matcher(sql.toLowerCase());
			  while(matcher.find())
		      {
				  left.add(matcher.start());
		      }
			  
			  //获取右括号
			  Pattern pattern1 = Pattern.compile("\\)");
			  Matcher matcher1 = pattern1.matcher(sql.toLowerCase());
			  while(matcher1.find())
		      {
				  right.add(matcher1.start());
		      }
			  
			  //找到FROM的位置
			  int k = 0;
			  int size = left.size();
			  int[][] two = new int[size][2];
			  for(int i =0; i<size;i++){
				  two[i][0] = left.get(i);
				  int j=k;
				  int t=0;
				  do{
					  two[i][1] = right.get(j);
					  t++;
				  }while(j<size-1 && i<size-1 && right.get(j++)>left.get(i+1));
				  if(t==1)
					  k++;
			  }
			  for(int i=0;i<size;i++){
				  for(int j=from.size()-1;j>-1;j--){
					  if(from.get(j)>two[i][0]&&from.get(j)<two[i][1])
						  from.remove(j);
				  }
			  }
			  if(from.size()==1)
				  return sql.substring(from.get(0));
			  else
				  throw new MiniDaoException("There are "+from.size()+" 'from' size,don't know which 'from' is true");
		  }
		return sql;
	}
	
}




