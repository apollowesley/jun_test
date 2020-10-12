package cn.itcast.enums2;

public class Gender {
	
	public static Gender male = new Gender("男");
	public static Gender female = new Gender("女");
	
	private String value;
	
	private Gender(String val){
		this.value = val;
	}
	
	public String getValue(){
		return this.value;
	}

}
