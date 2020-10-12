package priv.mdc.index.dumper.model;

import java.util.HashMap;
import java.util.Map;

public class ModelRecord {
	
	private String id;
	private Map<String,String> fields = new HashMap<String,String>();
	
	public ModelRecord(){}
	
	public ModelRecord(String id_, Map<String,String> fields_){
		id = id_;
		fields = fields_;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Map<String, String> getFields() {
		return fields;
	}
	public void setFields(Map<String, String> fields) {
		this.fields = fields;
	}
	
	/**
	 * 放入字段的值
	 * @param name
	 * @param value
	 */
	public void putField(String name, String value){
		fields.put(name.toLowerCase(), value);
	}
	
	/*
	 * 根据字段名取其值
	 */
	public String getFieldValue(String field){
		return fields.get(field.toLowerCase());
	}
	
	
}
