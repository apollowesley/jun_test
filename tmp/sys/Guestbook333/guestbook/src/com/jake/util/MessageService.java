package com.jake.util;

import java.util.ArrayList;

import com.jake.bean.Message;
import com.jake.dao.DBC;

public class MessageService {
	private int pageSize;

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPage() {
		int totalPage;
		DBC dbc = new DBC();
		ArrayList<Message> messages = dbc.getAllMess();
		totalPage = messages.size() / getPageSize() + 1;
		return totalPage;
	}

	public ArrayList<Message> getPage(int pageNum) {
		DBC dbc = new DBC();
		int total = getTotalPage();
		ArrayList<Message> messages = dbc.getAllMess();
		ArrayList<Message> current = new ArrayList<Message>();
		if (pageNum <= 1) {
			pageNum = 1;
			for (int i = 0; i < getPageSize(); i++) {
				current.add(messages.get(i));
			}
		} else if (pageNum>=total) {
			pageNum = total;
			for (int i = (pageNum - 1) * getPageSize(); i < messages.size(); i++) {
				current.add(messages.get(i));
			}
		} else{
			for (int i = (pageNum - 1) * getPageSize(); i < getPageSize() * pageNum; i++) {
				current.add(messages.get(i));
			}
		}
		return current;
	}
}
