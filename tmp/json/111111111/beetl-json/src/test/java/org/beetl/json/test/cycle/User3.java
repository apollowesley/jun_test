package org.beetl.json.test.cycle;

public class User3 {
	private int  id ;
	User3 friend ;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public User3 getFriend() {
		return friend;
	}
	public void setFriend(User3 friend) {
		this.friend = friend;
	}
	
}
