package cn.itcast.enums6;

public enum Gender {
	male("男2"){
		public String getAge(){
			return "255";
		}
	},female("女2"){
		public String getAge(){
			return "233";
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
