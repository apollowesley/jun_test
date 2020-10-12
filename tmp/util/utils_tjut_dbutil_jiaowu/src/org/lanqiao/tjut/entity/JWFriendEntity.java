package org.lanqiao.tjut.entity;

public class JWFriendEntity {
private	String f_id;
private String f_s_num; 
private String 	f_beizhu;
public String getF_id() {
	return f_id;
}
public void setF_id(String f_id) {
	this.f_id = f_id;
}
public String getF_s_num() {
	return f_s_num;
}
public void setF_s_num(String f_s_num) {
	this.f_s_num = f_s_num;
}
public String getF_beizhu() {
	return f_beizhu;
}
public void setF_beizhu(String f_beizhu) {
	this.f_beizhu = f_beizhu;
}
@Override
public String toString() {
	return "JWFriendEntity [f_id=" + f_id + ", f_s_num=" + f_s_num + ", f_beizhu=" + f_beizhu + "]";
}

}
