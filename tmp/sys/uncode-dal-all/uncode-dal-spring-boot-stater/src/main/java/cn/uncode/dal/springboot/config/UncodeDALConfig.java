package cn.uncode.dal.springboot.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "uncode.dal.config",ignoreInvalidFields = true)
public class UncodeDALConfig{
	
	private boolean useCache = true;
	private String versionField = "version";
	private List<String> versionTables;
	private List<String> noCacheTables;
	
	
	public boolean isUseCache() {
		return useCache;
	}
	public void setUseCache(boolean useCache) {
		this.useCache = useCache;
	}
	public String getVersionField() {
		return versionField;
	}
	public void setVersionField(String versionField) {
		this.versionField = versionField;
	}
	public List<String> getVersionTables() {
		return versionTables;
	}
	public void setVersionTables(List<String> versionTables) {
		this.versionTables = versionTables;
	}
	public List<String> getNoCacheTables() {
		return noCacheTables;
	}
	public void setNoCacheTables(List<String> noCacheTables) {
		this.noCacheTables = noCacheTables;
	}
	
	

}
