package priv.mdc.index.dumper.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FilterBean {
	
	private String table;
	private String idFieldName;
	private String indexName;
	private String docName;
	private List<FieldBean> dataFields;
	
	public FilterBean(){
	}
	
	public static FilterBean parse(Map map){
		FilterBean bean = new FilterBean();
		bean.setTable((String)map.get("table"));
		bean.setIndexName((String)map.get("idx_name"));
		bean.setDocName((String)map.get("doc_name"));
		bean.setIdFieldName((String)map.get("id_field"));
		List<FieldBean> dataFields = new ArrayList<FieldBean>();
		List list = (List)map.get("data_fields");
		for(int i=0; i<list.size(); ++i){
			Map fieldMap = (Map)list.get(i);
			FieldBean field = new FieldBean();
			field.setFieldName((String)fieldMap.get("name"));
			field.setFieldType((String)fieldMap.get("type"));
			field.setDocFieldName((String)fieldMap.get("name2"));
			dataFields.add(field);
		}
		bean.setDataFields(dataFields);
		return bean;
	}
	
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	public String getIdFieldName() {
		return idFieldName;
	}
	public void setIdFieldName(String idFieldName) {
		this.idFieldName = idFieldName;
	}
	public List<FieldBean> getDataFields() {
		if(dataFields==null){
			dataFields = new ArrayList<FieldBean>();
		}
		return dataFields;
	}
	public void setDataFields(List<FieldBean> dataFields) {
		this.dataFields = dataFields;
	}
	public String getIndexName() {
		return indexName;
	}
	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}
	public String getDocName() {
		return docName;
	}
	public void setDocName(String docName) {
		this.docName = docName;
	}

	/**
	 * 根据字段名取字段映射定义
	 * @param fieldName
	 * @return
	 */
	public FieldBean getFieldBean(String fieldName){
		if(dataFields==null || dataFields.isEmpty()){
			return null;
		}
		for(int i=0; i<dataFields.size(); ++i){
			if(dataFields.get(i).getFieldName().equals(fieldName)){
				return dataFields.get(i);
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		return "{table=" + table
				+ ", idFieldName=" + idFieldName + ", dataFields=" + dataFields
				+ "}";
	}

	public static class FieldBean{
		private String fieldName;
		private String fieldType;
		private String docFieldName;
		public FieldBean(){}
		public FieldBean(String fieldName_, String fieldType_, String docFieldName_){
			fieldName = fieldName_;
			fieldType = fieldType_;
			docFieldName = docFieldName_;
		}
		public String getFieldName() {
			return fieldName;
		}
		public void setFieldName(String fieldName) {
			this.fieldName = fieldName;
		}
		public String getFieldType() {
			return fieldType;
		}
		public void setFieldType(String fieldType) {
			this.fieldType = fieldType;
		}
		public String getDocFieldName() {
			return docFieldName;
		}
		public void setDocFieldName(String docFieldName) {
			this.docFieldName = docFieldName;
		}
		@Override
		public String toString() {
			return "{fieldName=" + fieldName + ", fieldType="
					+ fieldType + ", docFieldName="
							+ docFieldName + "}";
		}
	}
	
}
