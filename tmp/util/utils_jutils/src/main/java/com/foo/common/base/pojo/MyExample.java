package com.foo.common.base.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import com.google.gson.annotations.Expose;

@Entity
@Table(name = "W_EXAMPLE")
/**
 * hibernate4的model示例。
 * 
 * 特别注意：对于字符类型，需要精确指定大小，否则hibernate在mysql下会自动生成255长度的字串
 * 
 * @author Steve
 *
 */
public class MyExample implements Comparable<MyExample> {
	@Id
	@Column(name = "id", length = 36)
	@javax.persistence.GeneratedValue(generator = "system-uuid")
	@org.hibernate.annotations.GenericGenerator(name = "system-uuid",
			strategy = "uuid2")
	@Expose
	private String id;

	@Column(nullable = false)
	private Date expDate = new Date();

	@Column(length = 100)
	private String expStr = "";

	@Column(name = "EXP_INT")
	private int expInt = 0;

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof MyExample) {
			MyExample that = (MyExample) obj;
			return Objects.equal(this.id, that.id);
			// && Objects.equal(this.expDate, that.expDate)
			// && Objects.equal(this.expStr, that.expStr)
			// && Objects.equal(this.expInt, that.expInt);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id, expDate, expStr, expInt);
	}

	@Override
	public int compareTo(MyExample that) {
		return ComparisonChain.start().compare(this.id, that.id)
				.compare(this.expDate, that.expDate).result();

	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("id", id)
				.add("expDate", expDate).add("expStr", expStr).toString();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getExpDate() {
		return expDate;
	}

	public void setExpDate(Date expDate) {
		this.expDate = expDate;
	}

	public String getExpStr() {
		return expStr;
	}

	public void setExpStr(String expStr) {
		this.expStr = expStr;
	}

	public int getExpInt() {
		return expInt;
	}

	public void setExpInt(int expInt) {
		this.expInt = expInt;
	}

}
