package common.framework.dsb.orm;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import common.framework.log.Logger;

public class MybatisSqlSessionProvider implements SqlSessionProvider {

	private Map<String, SqlSessionFactory> factories = new Hashtable<String, SqlSessionFactory>();

	public MybatisSqlSessionProvider() {
		super();
	}

	public void start(String configFile) throws Exception {
		File config = new File(configFile);
		final FileInputStream fis = new FileInputStream(config);
		SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
		Collection<String> environments = new ArrayList<String>();
		DocumentBuilderFactory docBuildFactory = DocumentBuilderFactory.newInstance();
		docBuildFactory.setValidating(false);
		docBuildFactory.setNamespaceAware(false);
		DocumentBuilder docBuilder = docBuildFactory.newDocumentBuilder();
		docBuilder.setEntityResolver(new EntityResolver() {
			@Override
			public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
				return new InputSource(new ByteArrayInputStream(new byte[] {}));
			}
		});
		Document doc = docBuilder.parse(fis);
		Element root = doc.getDocumentElement();
		NodeList nodes = root.getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			if (!node.getNodeName().equalsIgnoreCase("environments")) {
				continue;
			}
			NodeList envs = node.getChildNodes();
			for (int j = 0; j < envs.getLength(); j++) {
				Node envNode = envs.item(j);
				String evnID = getAttribute(envNode, "id");
				if (evnID != null) {
					environments.add(evnID);
				}
			}
		}
		fis.close();

		for (String environment : environments) {
			Logger.log(Logger.PROGRAM_LEVEL, "DB environment: " + environment);
			final FileInputStream inputStream = new FileInputStream(config);
			SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(inputStream, environment);
			factories.put(environment, sqlSessionFactory);
			inputStream.close();
		}

	}

	@Override
	public void close() {
		factories.clear();
	}

	private String getAttribute(Node n, String name) {
		NamedNodeMap nnm = n.getAttributes();
		if (nnm == null) {
			return null;
		}
		for (int k = 0; k < nnm.getLength(); k++) {
			String nodeName = nnm.item(k).getNodeName();
			String nodeVal = nnm.item(k).getNodeValue();
			if (name.equalsIgnoreCase(nodeName)) {
				return nodeVal;
			}
		}
		return null;
	}

	@Override
	public SqlSession getSqlSession(String environment) {
		SqlSessionFactory sqlSessionFactory = factories.get(environment);
		if (sqlSessionFactory != null) {
			return sqlSessionFactory.openSession();
		}
		return null;
	}

	@Override
	public SqlSession getSqlSession(String environment, boolean autoCommit) {
		SqlSessionFactory sqlSessionFactory = factories.get(environment);
		if (sqlSessionFactory != null) {
			return sqlSessionFactory.openSession(autoCommit);
		}
		return null;
	}

}
