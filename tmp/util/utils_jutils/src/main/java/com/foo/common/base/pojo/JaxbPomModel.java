package com.foo.common.base.pojo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "project")
public class JaxbPomModel {

	private String properties;
	private String name;

	public String getProperties() {
		return properties;
	}

	@XmlElement(type = String.class)
	public void setProperties(String properties) {
		this.properties = properties;
	}

	public String getName() {
		return name;
	}

	@XmlElement(type = String.class)
	public void setName(String name) {
		this.name = name;
	}
}
