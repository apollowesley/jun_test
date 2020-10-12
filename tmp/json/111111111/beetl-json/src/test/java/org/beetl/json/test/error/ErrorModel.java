package org.beetl.json.test.error;

public class ErrorModel {
	int id = 1;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getName(){
		throw new RuntimeException("test");
	}
}
