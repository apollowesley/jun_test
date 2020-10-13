package com.blade.view;

import com.blade.web.http.Request;
import blade.kit.Assert;

public class ModelMap {
	
	private Request request;
	
	public ModelMap(Request request) {
		this.request = request;
	}
	
	public void addAttribute(String name, Object value) {
		Assert.notNull(name, "Model attribute name must not be null");
		request.attribute(name, value);
	}
	
	public void attribute(String name, Object value) {
		addAttribute(name, value);
	}
	
	public void attr(String name, Object value) {
		addAttribute(name, value);
	}
	
	public void put(String name, Object value) {
		addAttribute(name, value);
	}
}
