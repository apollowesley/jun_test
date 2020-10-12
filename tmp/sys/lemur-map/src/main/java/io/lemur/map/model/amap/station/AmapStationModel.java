package io.lemur.map.model.amap.station;

import java.io.Serializable;
import java.util.List;

public class AmapStationModel implements Serializable{

	
	private static final long serialVersionUID = 7296990361700842420L;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 版本
	 */
	private String version;
	/**
	 * 记录
	 */
	private String record;
	/**
	 * 总数
	 */
	private String total;
	/**
	 * 
	 */
	private String server;
	
	private List<AmapBusModel> list;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getRecord() {
		return record;
	}
	public void setRecord(String record) {
		this.record = record;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public List<AmapBusModel> getList() {
		return list;
	}
	public void setList(List<AmapBusModel> list) {
		this.list = list;
	}
	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
	}
	
}
