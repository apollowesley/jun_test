package org.lanqiao.tjut.entity;

public class JWTeacherEntity {
	
private String teacher_id ;
private   int teacher_num;
public String getTeacher_id() {
	return teacher_id;
}
public void setTeacher_id(String teacher_id) {
	this.teacher_id = teacher_id;
}
public int getTeacher_num() {
	return teacher_num;
}
public void setTeacher_num(int teacher_num) {
	this.teacher_num = teacher_num;
}
@Override
public String toString() {
	return "JWTeacherEntity [teacher_id=" + teacher_id + ", teacher_num=" + teacher_num + "]";
}

}
