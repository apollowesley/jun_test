package com.yonge.crud.test;

import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import com.yonge.crud.core.Configuration;
import com.yonge.crud.core.CrudSession;
import com.yonge.crud.core.CrudSessionFactory;
import com.yonge.crud.core.GenerateConfiguration;
import com.yonge.crud.core.db.model.Table;

public class CrudGeneratorTest {

	public static void main(String[] args) throws JAXBException {

		/*
		 * Configuration config = new Configuration(
		 * "oracle.jdbc.driver.OracleDriver",
		 * "jdbc:oracle:thin:@192.168.10.134:1521:orcl", "margin_gy",
		 * "margin_gy", null, "margin_gy");
		 */
		// "src/generateConfigration.xml"
		GenerateConfiguration genConfig = toObject(args[0],
				GenerateConfiguration.class);

		Configuration config = genConfig.getDbConfiguration();

		CrudSessionFactory sessionFactory = config.buildCrudSessionFactory();
		CrudSession session = sessionFactory.openSession();
		List<Table> tables = session.getTables();
		String srcBase = genConfig.getSrcBase();
		String pojoPackageName = genConfig.getPojoPackageName();
		String daoPackageName = genConfig.getDaoPackageName();
		String servicePackageName = genConfig.getServicePackageName();
		String sqlmapPackageName = genConfig.getSqlmapPackageName();
		String sqlmapConfigPackageName = genConfig.getSqlmapConfigPackageName();
		String springConfigPackageName = genConfig.getSpringConfigPackageName();
		for (Table table : tables) {
			session.reverse(table, srcBase, pojoPackageName, sqlmapPackageName,
					daoPackageName, servicePackageName);

		}
		// 生成sqlmapConfig
		session.generateSqlmapConfig(tables, srcBase, sqlmapConfigPackageName,
				sqlmapPackageName);
		// 生成spring配置文件
		session.generateSpringConfig(tables, srcBase, springConfigPackageName,
				daoPackageName, servicePackageName);
		session.closeSession();
	}

	public static <T> T toObject(String systemId, Class<T> clazz)
			throws JAXBException {
		JAXBContext jc = JAXBContext.newInstance(clazz);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		StreamSource xml = new StreamSource(systemId);
		JAXBElement<T> je = unmarshaller.unmarshal(xml, clazz);
		return je.getValue();
	}

}
