package org.beetl.json.test.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Item {
	int id =1;
	double sar = 3.15674;
	Date date = null;
	public Item(){
		try {
			date= new SimpleDateFormat("yyyy-MM-dd").parse("2014-05-18");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getSar() {
		return sar;
	}
	public void setSar(double sar) {
		this.sar = sar;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	
}
