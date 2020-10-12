package org.buzheng.demo.model;

import java.util.List;
import java.util.Map;

public class User {
	
	private String username;
	
	private String password;
	
	private Map<String, String> attributes;
	
	private List<String> books;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}

	public List<String> getBooks() {
		return books;
	}

	public void setBooks(List<String> books) {
		this.books = books;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password
				+ ", attributes=" + attributes + ", books=" + books + "]";
	}
		
}
