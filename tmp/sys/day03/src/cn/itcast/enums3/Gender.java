package cn.itcast.enums3;

public abstract class Gender {
	
	public static Gender male = new Gender("男"){
		public String getAge(){
			return "25";
		}
	};
	public static Gender female = new Gender("女"){
		public String getAge() {
			return "23";
		}
		
	};
	
	private String value;
	
	private Gender(String val){
		this.value = val;
	}
	
	public String getValue(){
		return this.value;
	}
	
	public abstract String getAge();

}
