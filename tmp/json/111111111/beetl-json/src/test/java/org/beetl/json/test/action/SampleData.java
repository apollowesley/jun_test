package org.beetl.json.test.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SampleData {
	Calendar cal = null;
	Date date = null;
	double sar = 1.2378;
	List users = null;
	List book = new ArrayList();
	public SampleData(){
		date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse("2015--5-21");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cal = Calendar.getInstance();
		cal.setTime(date);
		book.add("hello");
	}
	public Calendar getCal() {
		return cal;
	}
	public void setCal(Calendar cal) {
		this.cal = cal;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public double getSar() {
		return sar;
	}
	public void setSar(double sar) {
		this.sar = sar;
	}
	public List getUsers() {
		return users;
	}
	public void setUsers(List users) {
		this.users = users;
	}
	public List getBook() {
		return book;
	}
	public void setBook(List book) {
		this.book = book;
	}
	
	
}
