package site.yaotang.xgen.orm.tools;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import lombok.extern.slf4j.Slf4j;
import site.yaotang.xgen.orm.mapping.Id;
import site.yaotang.xgen.orm.mapping.Id.IdBuilder;
import site.yaotang.xgen.orm.mapping.ManyToOne;
import site.yaotang.xgen.orm.mapping.OneToMany;
import site.yaotang.xgen.orm.mapping.Property;
import site.yaotang.xgen.orm.mapping.Property.PropertyBuilder;
import site.yaotang.xgen.orm.mapping.Table;
import site.yaotang.xgen.orm.mapping.Table.TableBuilder;
import site.yaotang.xgen.orm.utils.LogUtil;

/**
 * 读取配置文件
 * @author hyt
 * @date 2017年12月30日
 */
@Slf4j
public class XMLFactory {

	// 配置文件常用词
	public static final String CLASS = "class";
	public static final String NAME = "name";
	public static final String TABLE = "table";
	public static final String ID = "id";
	public static final String COLUMN = "column";
	public static final String DEFAULT_VALUE = "defaultValue";
	public static final String AUTO_INCREMENT = "autoIncrement";
	public static final String PROPERTY = "property";
	public static final String MANY_TO_ONE = "manyToOne";
	public static final String ONE_TO_MANY = "oneToMany";
	public static final String DATASOURCE = "datasource";
	public static final String JDBC = "jdbc";
	public static final String DS = "ds";
	public static final String VALUE = "value";
	public static final String ORM_MAPPING = "orm-mapping";
	public static final String LIST = "list";
	public static final String KEY = "key";
	public static final String FIELD = "field";

	@SuppressWarnings("unchecked")
	public static void readDBInfo(String fileName) {
		File file = new File(fileName);
		SAXReader saxReader = new SAXReader();
		Document document = null;
		try {
			document = saxReader.read(file);
			Element datasources = document.getRootElement();
			Element datasource = datasources.element(DATASOURCE);
			Element jdbc = datasource.element(JDBC);
			if (jdbc != null) {
				List<Element> properties = jdbc.elements();
				properties.stream().forEach(property -> {
					String name = property.attributeValue(NAME);
					Attribute value = property.attribute(VALUE);
					if (value != null) {
						Constant.DBMAP.put(name, value.getStringValue());
					} else {
						Constant.DBMAP.put(name, property.getText());
					}
				});
			}
			Element ds = datasource.element(DS);
			if (ds != null) {
				ds.attribute(CLASS);
				Constant.DBMAP.put("dataSource", ds.attributeValue(CLASS));
				List<Element> properties = ds.elements(PROPERTY);
				properties.stream().forEach(p -> {
					String name = p.attributeValue(NAME);
					String value = null;
					Attribute attribute = p.attribute(VALUE);
					if (attribute != null) {
						value = attribute.getValue();
					} else {
						value = p.getText();
					}
					Constant.DBMAP.put("dataSource." + name, value);
				});
			}
		} catch (Exception e) {
			LogUtil.error(log, e);
		}
	}

	@SuppressWarnings("unchecked")
	public static void readTableInfo(String fileName) {
		File file = new File(fileName);
		SAXReader saxReader = new SAXReader();
		Document document = null;
		try {
			document = saxReader.read(file);
		} catch (DocumentException e) {
			LogUtil.error(log, e);
		}
		Element datasources = document.getRootElement();
		Element ormMapping = datasources.element(ORM_MAPPING);
		Element list = ormMapping.element(LIST);
		List<Element> values = list.elements(VALUE);
		values.stream().forEach(value -> {
			TableBuilder builder = Table.builder();
			String fileLocation = value.getTextTrim();
			String fileURL = XMLFactory.class.getResource(fileLocation).getFile();
			File xmlFile = new File(fileURL);
			Document doc = null;
			try {
				doc = saxReader.read(xmlFile);
			} catch (DocumentException e) {
				LogUtil.error(log, e);
			}
			Element clazz = doc.getRootElement();

			builder.className(clazz.attributeValue(NAME));
			builder.tableName(clazz.attributeValue(TABLE));
			Element id = clazz.element(ID);
			builder.id(buildId(id));

			List<Element> properties = clazz.elements(PROPERTY);
			List<Property> propertyList = properties.stream().map(property -> {
				return buildProperty(property);
			}).collect(Collectors.toList());
			builder.properties(propertyList);

			List<Element> manyToOnes = clazz.elements(MANY_TO_ONE);
			builder.manyList(manyToOnes.stream().map(mto -> {
				return buildManyToOne(mto);
			}).collect(Collectors.toList()));

			List<Element> oneToManys = clazz.elements(ONE_TO_MANY);
			builder.oneList(oneToManys.stream().map(otm -> {
				return buildOneToMany(otm);
			}).collect(Collectors.toList()));
			Table table = builder.build();
			Constant.TABLEMAP.put(table.getClassName(), table);
		});
	}

	private static Id buildId(Element element) {
		IdBuilder builder = Id.builder().name(element.attributeValue(NAME)).column(element.attributeValue(COLUMN));
		Attribute attribute = element.attribute(AUTO_INCREMENT);
		if (attribute != null) {
			builder.autoIncrement(Boolean.valueOf(attribute.getValue()));
		}
		return builder.build();
	}

	private static Property buildProperty(Element element) {
		PropertyBuilder builder = Property.builder().name(element.attributeValue(NAME)).column(element.attributeValue(COLUMN));
		Attribute attribute = element.attribute(DEFAULT_VALUE);
		if (attribute != null) {
			builder.defaultValue(attribute.getValue());
		}
		return builder.build();
	}

	private static ManyToOne buildManyToOne(Element element) {
		return ManyToOne.builder().name(element.attributeValue(NAME)).clazz(element.attributeValue(CLASS)).column(element.element(KEY).attributeValue(COLUMN)).field(element.element(KEY).attributeValue(FIELD)).build();
	}

	private static OneToMany buildOneToMany(Element element) {
		return OneToMany.builder().name(element.attributeValue(NAME)).clazz(element.attributeValue(CLASS)).column(element.element(KEY).attributeValue(COLUMN)).field(element.element(KEY).attributeValue(FIELD)).build();
	}

	public static void main(String[] args) {
		System.out.println(Constant.DBMAP);
	}
}
