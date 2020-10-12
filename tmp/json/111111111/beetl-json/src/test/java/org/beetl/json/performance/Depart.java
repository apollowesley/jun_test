package org.beetl.json.performance;

import org.beetl.json.annotation.Json;
import org.beetl.json.annotation.JsonPolicy;

public class Depart {
	public int id = 1;
	private Org org ;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Org getOrg() {
		return org;
	}
	public void setOrg(Org org) {
		this.org = org;
	}
	
	
}
