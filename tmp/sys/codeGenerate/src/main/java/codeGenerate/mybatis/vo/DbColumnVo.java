
package codeGenerate.mybatis.vo;


/**
 * TODO 数据库底层的表vo
 * @Copyright: fudewei
 * @author: dw.fu
 * @since: 2015年9月26日
 */
public class DbColumnVo {

	/**
	 * 列名
	 */
	private String columnName;
	
	/**
	 * 列注释
	 */
	private String columnComment;

	/**
	 * 列的类型名
	 */
	private Integer columnType;
	/**
	 * 列的类型名
	 */
	private String columnTypeName;
	
	/**
	 * 精度（字段长度）
	 */
	private int precision;
	
	/**
	 *范围（小数位数）
	 */
	private int  scale;
	
	public String getColumnName() {
		return columnName;
	}
	
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	
	
	public Integer getColumnType() {
		return columnType;
	}
	
	public void setColumnType(Integer columnType) {
		this.columnType = columnType;
	}

	
	public String getColumnTypeName() {
		return columnTypeName;
	}

	
	public void setColumnTypeName(String columnTypeName) {
		this.columnTypeName = columnTypeName;
	}

	
	

	
	public int getPrecision() {
		return precision;
	}

	
	public void setPrecision(int precision) {
		this.precision = precision;
	}

	
	public int getScale() {
		return scale;
	}

	
	public void setScale(int scale) {
		this.scale = scale;
	}

	public String getColumnComment() {
		return columnComment;
	}

	
	public void setColumnComment(String columnComment) {
		this.columnComment = columnComment;
	}

	@Override
	public String toString() {
		return "DbColumnVo [columnName=" + columnName + ", columnComment=" + columnComment + ", columnType=" + columnType + ", columnTypeName=" + columnTypeName + ", precision="
				+ precision + ", scale=" + scale + "]";
	}
	
}
