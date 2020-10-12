package com.dboper.search.sqlparams.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.dboper.search.sqlparams.util.Assert;

public class TimeSqlParamsParser extends AbstractSqlParamsParser{
	
	private String timeFormat="yyyy-MM-dd";
	
	private String fullTimeFlag="";
	
	private String[] opers=new String[]{"time>","time>=","time<","time<="};

	public TimeSqlParamsParser(){
		setOpers(opers);
	}
	
	public TimeSqlParamsParser(String timeFormat,String fullTimeFlag){
		setTimeFormat(timeFormat);
		setFullTimeFlag(fullTimeFlag);
		String[] newOpers=new String[opers.length];
		for(int i=0,len=opers.length;i<len;i++){
			newOpers[i]=this.fullTimeFlag+opers[i];
		}
		setOpers(newOpers);
	}
	
	public void setFullTimeFlag(String fullTimeFlag) {
		this.fullTimeFlag=fullTimeFlag==null?this.fullTimeFlag:fullTimeFlag;
	}

	public void setTimeFormat(String timeFormat) {
		this.timeFormat = timeFormat;
	}
	
	/**
	 * 以d.time@time>'2015-3-1'为例
	 * 初始参数 key=d.time; value='2015-3-1'; oper=time>
	 * 解析后的key=unix_timestamp(d.time); value=1425139200('2015-3-1'对应的秒数); oper=>
	 */
	@Override
	protected SqlParamsParseItemResult doParams(String key, Object value, String oper) {
		String timeKey="unix_timestamp("+key+")";
		String realOper=oper.substring(4+fullTimeFlag.length());
		if(value instanceof String){
			String tmp=(String)value;
			Assert.isLarger(tmp.length(),2,"时间参数不合法");
			//默认进行了字符串处理，即加上了''，现在要去掉，然后解析成时间的秒数
			value=tmp.substring(1,tmp.length()-1);
			try {
				SimpleDateFormat format=new SimpleDateFormat(timeFormat);
				Date date=format.parse((String)value);
				value=date.getTime()/1000;
			} catch (ParseException e) {
				e.printStackTrace();
				throw new IllegalArgumentException("timeFormat为"+timeFormat+";value="+value+";出现了解析异常");
			}
		}else{
			Assert.isInstanceof(value,Number.class,"时间参数必须为时间的秒数");
		}
		return new SqlParamsParseItemResult(timeKey,realOper,value);
	}

}
