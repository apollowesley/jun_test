package jaxp;

import org.junit.Ignore;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileInputStream;

/**
 * User: zjzhai
 * Date: 12/4/13
 * Time: 4:26 PM
 */
public class JaxpTest {

    @Test
    public void testName() throws Exception {
        File configFile = new File(this.getClass().getClassLoader().getResource("koala-businesslog-config.xml").getFile());

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new FileInputStream(configFile));

        assert "businessLogConfigs".equals(document.getDocumentElement().getTagName());

        Element root = document.getDocumentElement();

        NodeList rootChildren = root.getChildNodes();

        for (int i = 0; i < rootChildren.getLength(); i++) {
            Node node = rootChildren.item(i);

            if ("preTemplate".equals(node.getNodeName())) {
                assert "${user}:${ip}:${time!Date}".equals(node.getTextContent());
            }

            if ("businessLogConfig".equals(node.getNodeName())) {
                NodeList configNodes = node.getChildNodes();
                System.out.println(configNodes);

            }


        }


    }


}
