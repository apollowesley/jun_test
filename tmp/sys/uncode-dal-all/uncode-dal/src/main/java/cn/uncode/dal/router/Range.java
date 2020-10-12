package cn.uncode.dal.router;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Range implements Serializable {

	private static final long serialVersionUID = 2277774145343302435L;
	
	private String tableName;
	private long start;
	private long end;
	private List<String> hashs = new ArrayList<String>();
	

	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public long getStart() {
		return start;
	}
	public void setStart(long start) {
		this.start = start;
	}
	public long getEnd() {
		return end;
	}
	public void setEnd(long end) {
		this.end = end;
	}
	public List<String> getHashs() {
		return hashs;
	}
	public void setHashs(List<String> hashs) {
		this.hashs = hashs;
	}
	
	

}
