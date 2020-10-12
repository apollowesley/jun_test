package cn.uncode.dal.router;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TableRouter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String tableName;
	
	private SharingType sharingType;
	
	private String fieldName;
	
	private List<Range> ranges = new ArrayList<Range>();

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public SharingType getSharingType() {
		return sharingType;
	}

	public void setSharingType(SharingType sharingType) {
		this.sharingType = sharingType;
	}

	public List<Range> getRanges() {
		return ranges;
	}

	public void setRanges(List<Range> ranges) {
		this.ranges = ranges;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}


	
	
	

}
