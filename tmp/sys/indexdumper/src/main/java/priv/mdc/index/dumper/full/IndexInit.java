package priv.mdc.index.dumper.full;

import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import priv.mdc.index.dumper.process.IndexDump;
import priv.mdc.index.dumper.util.ConfigInfo;

/**
 * 重建索引
 * 
 * @author xuhuahai
 *
 */
public class IndexInit {

	protected final static Logger logger = LoggerFactory
			.getLogger(IndexInit.class);

	public static void reinit() {
		try {
			InputStream is = ConfigInfo.class.getClassLoader()
					.getResourceAsStream("IndexInit.xml");
			SAXReader reader = new SAXReader();  
			Document  document = reader.read(is);  
			Element root = document.getRootElement();  
			List actionList = root.elements("Action");
			for(int i = 0; i < actionList.size(); ++i){
				Element element = (Element)actionList.get(i);
				String type = element.attribute("type").getText();
				String uri = element.attribute("url").getText();
				String body = element.getText();
				logger.info("\nAction={},\nUri={},\nBody={}",new Object[]{type,uri,body});
				IndexDump.executeAction(type, uri, body);
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
	}

}
