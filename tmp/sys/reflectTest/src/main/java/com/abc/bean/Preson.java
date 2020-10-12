package com.abc.bean;

public class Preson {
	private String name;
	private int age;
	public Preson() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Preson(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public void eat() {
		System.out.println("人类要吃饭");
	}

}
