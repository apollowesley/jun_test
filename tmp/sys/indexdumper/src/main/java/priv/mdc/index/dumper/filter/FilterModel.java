package priv.mdc.index.dumper.filter;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import priv.mdc.index.dumper.util.ConfigInfo;

/**
 * 
 * @author xuhuahai
 *
 */
public class FilterModel {

	protected final static Logger logger = LoggerFactory.getLogger(FilterModel.class);
	
	private static Map<String,FilterBean> filters = new HashMap<String,FilterBean>();
	
	/**
	 * 根据数据库名和表名，找到合适的过滤器定义
	 * @param database
	 * @param table
	 * @return
	 * @throws FileNotFoundException
	 * @throws FilterException
	 */
	public static FilterBean queryFilterBean(String table) throws FileNotFoundException,FilterException {
		if(filters.isEmpty()){
			synchronized(filters){
				if(filters.isEmpty()){
					Yaml yaml = new Yaml();
					List list = yaml.loadAs(ConfigInfo.class.getClassLoader().getResourceAsStream( 
					        "filters.yaml"), List.class);
					if(list!=null && !list.isEmpty()){
						for(int i=0; i<list.size(); i++){
							Map map = (Map)list.get(i);
							FilterBean bean = FilterBean.parse(map);
							if(bean!=null){
								filters.put(bean.getTable().toLowerCase(), bean);
							}else{
								logger.error("Invalid filter define : {}", map);
							}
						}
					}else{
						throw new FilterException("Not found valid filters in filters.yaml!");
					}
				}
			}
		}
		return filters.get(table.toLowerCase());
	}

	
}
