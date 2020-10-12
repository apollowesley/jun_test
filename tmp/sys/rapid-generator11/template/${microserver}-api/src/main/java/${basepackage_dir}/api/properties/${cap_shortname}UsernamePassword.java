package ${basepackage}.api.properties;

import java.io.Serializable;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

<#include "java_author.include">
@Component
@ConfigurationProperties(prefix = "microservice")
public class ${cap_shortname}UsernamePassword implements Serializable {

	private String ${shortname}Username;

	private String ${shortname}Password;

	public String get${shortname?cap_first}Username() {
		return ${shortname}Username;
	}

	public void set${shortname?cap_first}Username(String ${shortname}Username) {
		this.${shortname}Username = ${shortname}Username;
	}

	public String get${shortname?cap_first}Password() {
		return ${shortname}Password;
	}

	public void set${shortname?cap_first}Password(String ${shortname}Password) {
		this.${shortname}Password = ${shortname}Password;
	}

}
