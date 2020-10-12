package com.kld.common.framework.dto;

import java.util.Map;

public class QueryParaDto implements java.io.Serializable{

	private static final long serialVersionUID = -7681158012712607540L;
	private Map<String,Object> map;

	public Map<String,Object> getMap() {
		return map;
	}

	public void setMap(Map<String,Object> map) {
		this.map = map;
	}

}
