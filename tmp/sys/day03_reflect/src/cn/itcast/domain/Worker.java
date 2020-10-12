package cn.itcast.domain;

public class Worker {

	private String name;
	private String age;
	private String menoy;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getMenoy() {
		return menoy;
	}
	public void setMenoy(String menoy) {
		this.menoy = menoy;
	}
	@Override
	public String toString() {
		return "Worker [age=" + age + ", menoy=" + menoy + ", name=" + name
				+ "]";
	}
	public Worker() {
		System.out.println("worker");
	}
	
	
}
