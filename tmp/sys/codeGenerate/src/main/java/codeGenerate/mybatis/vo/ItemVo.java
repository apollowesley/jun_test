
package codeGenerate.mybatis.vo;

public class ItemVo {

	private String typeHandler;
	
	private String javaPropertyType;

	private String mybatisJdbcType;

	private String mybatisJavaType;

	private String mybatisTypeHandler;

	
	public String getJavaPropertyType() {
		return javaPropertyType;
	}

	
	public void setJavaPropertyType(String javaPropertyType) {
		this.javaPropertyType = javaPropertyType;
	}

	
	public String getMybatisJdbcType() {
		return mybatisJdbcType;
	}

	
	public void setMybatisJdbcType(String mybatisJdbcType) {
		this.mybatisJdbcType = mybatisJdbcType;
	}

	
	public String getMybatisJavaType() {
		return mybatisJavaType;
	}

	
	public void setMybatisJavaType(String mybatisJavaType) {
		this.mybatisJavaType = mybatisJavaType;
	}

	
	public String getMybatisTypeHandler() {
		return mybatisTypeHandler;
	}

	
	public void setMybatisTypeHandler(String mybatisTypeHandler) {
		this.mybatisTypeHandler = mybatisTypeHandler;
	}


	
	public String getTypeHandler() {
		return typeHandler;
	}


	
	public void setTypeHandler(String typeHandler) {
		this.typeHandler = typeHandler;
	}


	@Override
	public String toString() {
		return "ItemVo [typeHandler=" + typeHandler + ", javaPropertyType=" + javaPropertyType + ", mybatisJdbcType=" + mybatisJdbcType + ", mybatisJavaType=" + mybatisJavaType
				+ ", mybatisTypeHandler=" + mybatisTypeHandler + "]";
	}
	
}
