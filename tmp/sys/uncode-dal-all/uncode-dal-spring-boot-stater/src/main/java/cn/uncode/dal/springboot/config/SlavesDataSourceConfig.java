package cn.uncode.dal.springboot.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "uncode.dal.datasource.slave",ignoreInvalidFields = false)
public class SlavesDataSourceConfig extends DataSourceProperties{
	
	private List<String> name;
	private List<String> url;
	private List<String> username;
	private List<String> password;
	public List<String> getUrl() {
		return url;
	}
	public void setUrl(List<String> url) {
		this.url = url;
	}
	public List<String> getUsername() {
		return username;
	}
	public void setUsername(List<String> username) {
		this.username = username;
	}
	public List<String> getPassword() {
		return password;
	}
	public void setPassword(List<String> password) {
		this.password = password;
	}
	public List<String> getName() {
		return name;
	}
	public void setName(List<String> name) {
		this.name = name;
	}
	public Map<Object, DataSource> build() {
		Map<Object, DataSource> dataSources= new HashMap<Object, DataSource>();
		if(isNotEmpty(url) && isNotEmpty(username) && isNotEmpty(password)){
			int length = url.size();
			for (int i = 0;i < length ; i++){
				String urlItem = url.get(i);
				String userNameItem = username.get(i);
				String passWordItem = password.get(i);
				String nameItem = name.get(i);
				if(StringUtils.isNotBlank(urlItem) && StringUtils.isNotBlank(userNameItem) && StringUtils.isNotBlank(passWordItem)){
					//添加slave库
					BasicDataSource basicDataSource = new BasicDataSource();
					coinfig(basicDataSource);
					basicDataSource.setUrl(urlItem);
					basicDataSource.setUsername(userNameItem);
					basicDataSource.setPassword(passWordItem);
					if(StringUtils.isNotBlank(nameItem)){
						dataSources.put(nameItem, basicDataSource);
					}else{
						dataSources.put("slave"+i, basicDataSource);
					}
				}
			}
		}
		return dataSources;
	}
	
	
	private boolean isNotEmpty(List<?> list){
		if(list == null){
			return false;
		}
		if(list.size() == 0){
			return false;
		}
		return true;
	}
	

}
