package cn.itcast.enums5;

public enum Gender {
	male("男2"),female("女2");
	
	private String value;
	
	private Gender(String val){
		this.value = val;
	}
	
	public String getValue(){
		return this.value;
	}

}
