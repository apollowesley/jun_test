package site.yaotang.xgen.orm.utils;

import java.util.List;

import site.yaotang.xgen.orm.mapping.ManyToOne;
import site.yaotang.xgen.orm.mapping.Property;
import site.yaotang.xgen.orm.mapping.Table;

/**
 * 表信息工具
 * @author hyt
 * @date 2017年12月30日
 */
public class TableUtil {

	public static Property getProperty(Table table, String name) {
		List<Property> properties = table.getProperties();
		if (properties != null && properties.size() > 0) {
			for (Property property : properties) {
				if (property.getName().equals(name)) {
					return property;
				}
			}
		}
		return null;
	}

	public static ManyToOne getManyToOne(Table table, String name) {
		List<ManyToOne> manyList = table.getManyList();
		if (manyList != null && manyList.size() > 0) {
			for (ManyToOne manyToOne : manyList) {
				if (manyToOne.getName().equals(name)) {
					return manyToOne;
				}
			}
		}
		return null;
	}
}
