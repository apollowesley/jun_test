package priv.mdc.index.dumper.full;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class DatabaseBean {

	private String host;
	private int    port;
	private String instance;
	private String user;
	private String pass;
	private List<TableBean> tables;
	
	
	/**
	 * 解析出数据库节点配置
	 * @param map
	 * @return
	 */
	public static DatabaseBean parse(Map map){
		DatabaseBean bean = new DatabaseBean();
		bean.setHost((String)map.get("host"));
		bean.setPort((Integer)map.get("port"));
		bean.setInstance((String)map.get("instance"));
		bean.setUser((String)map.get("user"));
		bean.setPass((String)map.get("pass"));
		List list = (List)map.get("tables");
		for(int i=0; i<list.size(); ++i){
			TableBean tableBean = TableBean.parse((Map)list.get(i));
			bean.getTables().add(tableBean);
		}
		return bean;
	}
	

	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getInstance() {
		return instance;
	}
	public void setInstance(String instance) {
		this.instance = instance;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public List<TableBean> getTables() {
		if(tables==null){
			tables = new ArrayList<TableBean>();
		}
		return tables;
	}
	public void setTables(List<TableBean> tables) {
		this.tables = tables;
	}

	@Override
	public String toString() {
		return "DatabaseBean [host=" + host + ", port=" + port + ", instance="
				+ instance + ", user=" + user + ", pass=" + pass + ", tables="
				+ tables + "]";
	}

	public static class TableBean{
		private String name;
		private String idFieldName;
		private String idFieldType;
		private List<FieldBean> fields;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getIdFieldName() {
			return idFieldName;
		}
		public void setIdFieldName(String idFieldName) {
			this.idFieldName = idFieldName;
		}
		public String getIdFieldType() {
			return idFieldType;
		}
		public void setIdFieldType(String idFieldType) {
			this.idFieldType = idFieldType;
		}
		public List<FieldBean> getFields() {
			if(fields==null){
				fields = new ArrayList<FieldBean>();
			}
			return fields;
		}
		public void setFields(List<FieldBean> fields) {
			this.fields = fields;
		}
		public String getFieldType(String fieldName) {
			if(fieldName.endsWith(idFieldName)){
				return idFieldType;
			}
			for(int i=0; i<fields.size(); ++i){
				if(fields.get(i).equals(fieldName)){
					return fields.get(i).getType();
				}
			}
			return "text";
		}
		@Override
		public String toString() {
			return "TableBean [name=" + name + ", idFieldName=" + idFieldName
					+ ", idFieldType=" + idFieldType + ", fields=" + fields
					+ "]";
		}
		public static TableBean parse(Map map){
			TableBean bean = new TableBean();
			bean.setName((String)map.get("table"));
			bean.setIdFieldName((String)map.get("id_field"));
			bean.setIdFieldType((String)map.get("id_type"));
			List list = (List)map.get("fields");
			for(int i=0; i<list.size(); ++i){
				FieldBean fieldBean = FieldBean.parse((Map)list.get(i));
				bean.getFields().add(fieldBean);
			}
			return bean;
		}
		public String retrieveIdValue(String value){
			if("text".equals(idFieldType)){
				return "'"+value+"'";
			}else{
				return value;
			}
		}
	}
	
	public static class FieldBean{
		private String name;
		private String type;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}		
		@Override
		public String toString() {
			return "FieldBean [name=" + name + ", type=" + type + "]";
		}
		public static FieldBean parse(Map map){
			FieldBean bean = new FieldBean();
			bean.setName((String)map.get("field"));
			bean.setType((String)map.get("type"));
			return bean;
		}
	}
	
}
