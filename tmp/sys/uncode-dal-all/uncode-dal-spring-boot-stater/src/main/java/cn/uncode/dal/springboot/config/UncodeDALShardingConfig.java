package cn.uncode.dal.springboot.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "uncode.dal.sharding",ignoreInvalidFields = false)
public class UncodeDALShardingConfig{
	
	private List<String> table;
	private List<String> type;
	private List<String> field;
	private List<String> value;
	
	
	public List<String> getTable() {
		return table;
	}
	public void setTable(List<String> table) {
		this.table = table;
	}
	public List<String> getType() {
		return type;
	}
	public void setType(List<String> type) {
		this.type = type;
	}
	public List<String> getField() {
		return field;
	}
	public void setField(List<String> field) {
		this.field = field;
	}
	public List<String> getValue() {
		return value;
	}
	public void setValue(List<String> value) {
		this.value = value;
	}
	
	
	
	
	

}
