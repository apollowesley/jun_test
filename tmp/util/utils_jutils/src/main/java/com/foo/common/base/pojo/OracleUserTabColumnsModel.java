package com.foo.common.base.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USER_TAB_COLUMNS")
/**
 * oracle在这个表里面保存了所有表和列的详细信息
 * @author Steve
 *
 */
public class OracleUserTabColumnsModel {

	@Id
	private OracleUserTabColumnsPK pk;

	private String data_type = "";

	private String nullable = "";

	public String getData_type() {
		return data_type.toLowerCase();
	}

	public void setData_type(String data_type) {
		this.data_type = data_type;
	}

	public String getNullable() {
		return nullable.toLowerCase();
	}

	public void setNullable(String nullable) {
		this.nullable = nullable;
	}

	public OracleUserTabColumnsPK getPk() {
		return pk;
	}

	public void setPk(OracleUserTabColumnsPK pk) {
		this.pk = pk;
	}

}
