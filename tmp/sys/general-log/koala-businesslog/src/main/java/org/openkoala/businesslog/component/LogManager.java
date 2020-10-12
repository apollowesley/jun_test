package org.openkoala.businesslog.component;

import java.io.IOException;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

/**
 * 
 * @author xmfang
 *
 */
public class LogManager {

	private LogGenerator logGenerator = new LogGenerator();


	//@Inject
	private LogRecorder logRecorder;
	
	public void logging(MethodInfo methodInfo) {
		try {
			logRecorder.recordLog(logGenerator.generateLog(methodInfo));
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
