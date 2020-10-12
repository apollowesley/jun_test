package org.beetl.json.test.cycle;

public class User {
	private int id ;
	private User self ;
	public User(int id){
		this.id = id ;
		this.self = this;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public User getSelf() {
		return self;
	}
	public void setSelf(User self) {
		this.self = self;
	}
	
}
