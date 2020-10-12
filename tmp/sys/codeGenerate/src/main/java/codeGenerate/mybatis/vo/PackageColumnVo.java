
package codeGenerate.mybatis.vo;

/**
 * TODO 
 * @Copyright: fudewei
 * @author: dw.fu
 * @since: 2015年9月26日
 */
public class PackageColumnVo {

	private String dbColumnName;

	private Integer dbColumnType;

	private String dbColumnComment;

	private Integer dbColumnPrecision = Integer.valueOf(0);

	private Integer dbColumnScale = Integer.valueOf(0);

	private String javaPropertyType;

	private String javaPropertyTypePackage;

	private String javaPropertyName;

	private String mybatisJdbcType;

	private String mybatisJavaType;

	private String mybatisTypeHandler;

	public String getDbColumnName() {
		return dbColumnName;
	}

	public void setDbColumnName(String dbColumnName) {
		this.dbColumnName = dbColumnName;
	}

	public String getDbColumnComment() {
		return dbColumnComment;
	}

	public void setDbColumnComment(String dbColumnComment) {
		this.dbColumnComment = dbColumnComment;
	}

	public Integer getDbColumnType() {
		return dbColumnType;
	}

	public void setDbColumnType(Integer dbColumnType) {
		this.dbColumnType = dbColumnType;
	}

	public Integer getDbColumnPrecision() {
		return dbColumnPrecision;
	}

	public void setDbColumnPrecision(Integer dbColumnPrecision) {
		this.dbColumnPrecision = dbColumnPrecision;
	}

	public Integer getDbColumnScale() {
		return dbColumnScale;
	}

	public void setDbColumnScale(Integer dbColumnScale) {
		this.dbColumnScale = dbColumnScale;
	}

	public String getJavaPropertyType() {
		return javaPropertyType;
	}

	public void setJavaPropertyType(String javaPropertyType) {
		this.javaPropertyType = javaPropertyType;
	}

	public String getJavaPropertyName() {
		return javaPropertyName;
	}

	public void setJavaPropertyName(String javaPropertyName) {
		this.javaPropertyName = javaPropertyName;
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

	public String getJavaPropertyTypePackage() {
		return javaPropertyTypePackage;
	}

	public void setJavaPropertyTypePackage(String javaPropertyTypePackage) {
		this.javaPropertyTypePackage = javaPropertyTypePackage;
	}

	@Override
	public String toString() {
		return "PackageColumnVo [dbColumnName=" + dbColumnName + ", dbColumnType=" + dbColumnType + ", dbColumnComment=" + dbColumnComment + ", dbColumnPrecision="
				+ dbColumnPrecision + ", dbColumnScale=" + dbColumnScale + ", javaPropertyType=" + javaPropertyType + ", javaPropertyName=" + javaPropertyName
				+ ", mybatisJdbcType=" + mybatisJdbcType + ", mybatisJavaType=" + mybatisJavaType + ", mybatisTypeHandler=" + mybatisTypeHandler + "]";
	}

}
