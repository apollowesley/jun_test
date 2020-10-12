package org.openkoala.businesslog.impl;

import com.dayatang.domain.InstanceFactory;
import org.openkoala.businesslog.config.AbstractBusinessLogConfigAdapter;
import org.openkoala.businesslog.config.BusinessLogConfigAdapter;
import org.openkoala.businesslog.config.BusinessLogContextQuery;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: zjzhai
 * Date: 12/4/13
 * Time: 10:36 AM
 */
public class BusinessLogXmlConfigDefaultAdapter extends AbstractBusinessLogConfigAdapter {

    /**
     * 业务日志节点的节点名s
     */
    private final static String BUSINESS_LOG_CONFIG_NODE_NAME = "businessLogConfig";

    /**
     * 前置模板的节点名
     */
    private final static String PRE_TEMPLATE_NODE_NAME = "preTemplate";

    /**
     * 业务操作的节点名
     */
    private final static String BUSINESS_OPERATOR_NODE_NAME = "method";

    /**
     * 模板的节点名
     */
    private final static String TEMPLATE_NODE_NAME = "template";

    /**
     * 配置文件
     */
    private final static String XML_CONFIG_PATH = "koala-businesslog-config.xml";


    /**
     * 查询定义子节点
     */
    private final static String TARGET_BEAN_NODE_NAME = "targetBean";

    private final static String TARGET_BEAN_REFID_NODE_ATTR = "refId";
    private final static String TARGET_BEAN_CLASS_NODE_ATTR = "class";

    /**
     * 查询定义子节点
     */
    private final static String TARGET_METHOD_NODE_NAME = "method";

    @Override
    public AbstractBusinessLogConfigAdapter findConfigByBusinessOperator(String businessOperator) {

        Document document = loadXmlConfig();

        Node configNode = findConfigByBusinessOperator(document, businessOperator);

        //找不到该业务方法对应的日志配置
        if (null == configNode) {
            return null;
        }

        setPreTemplate(findPreTemplateFrom(document));

        setTemplate(findTemplateFrom(configNode));

        return this;
    }

    private List<BusinessLogContextQuery> findAndSortQueriesFrom(Node queriesNode) {
        List<BusinessLogContextQuery> result = new ArrayList<BusinessLogContextQuery>();
        NodeList children = queriesNode.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node queryNode = children.item(i);
            result.add(createQueryBy(queryNode));
        }
        return result;
    }


    private BusinessLogXmlConfigDefaultContextQuery createQueryBy(Node queryNode) {
        BusinessLogXmlConfigDefaultContextQuery query = new BusinessLogXmlConfigDefaultContextQuery();
        String contextKey = queryNode.getAttributes().getNamedItem("id").getNodeValue();
        Object bean = null;
        String targetMethod = "";
        NodeList children = queryNode.getChildNodes();


        for (int i = 0; i < children.getLength(); i++) {
            Node node = children.item(i);
            if (TARGET_BEAN_NODE_NAME.equals(node.getNodeName())) {
                bean = getBeanFromTargetBeanNode(node);
                if (null == bean) {
                    continue;
                }

            }

            if (TARGET_METHOD_NODE_NAME.equals(node.getNodeName())) {
                targetMethod = (node.getTextContent() != null ? node.getTextContent().trim() : null);
                if (null == targetMethod || "".equals(targetMethod)) {
                    continue;
                }
            }


        }


    }

    private Object getBeanFromTargetBeanNode(Node node) {
        if (!node.hasAttributes()) {
            return null;
        }

        String beanId = node.getAttributes().getNamedItem(TARGET_BEAN_REFID_NODE_ATTR).getNodeValue();
        beanId = beanId == null ? null : beanId.trim();

        try {
            if (beanId != null && !"".equals(beanId)) {
                return InstanceFactory.getInstance(Class.forName(beanId), beanId);
            }
        } catch (ClassNotFoundException e) {
            new RuntimeException(e);
        }

        String className = node.getAttributes().getNamedItem(TARGET_BEAN_CLASS_NODE_ATTR).getNodeValue();
        className = className == null ? null : className.trim();

        try {
            if (beanId != null && !"".equals(beanId)) {
                return Class.forName(className);
            }
        } catch (ClassNotFoundException e) {
            new RuntimeException(e);
        }

        return null;
    }


    private Node findConfigByBusinessOperator(Document document, String businessOperator) {
        List<Node> configNodes = findConfigsForm(document);
        for (Node eachNode : configNodes) {
            NodeList nodeList = eachNode.getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (BUSINESS_OPERATOR_NODE_NAME.equals(node.getNodeName())
                        && businessOperator.equals(node.getTextContent())) {
                    return eachNode;
                }
            }
        }
        return null;
    }

    private List<Node> findConfigsForm(Document document) {
        List<Node> results = new ArrayList<Node>();
        Element root = document.getDocumentElement();
        NodeList rootChildren = root.getChildNodes();
        for (int i = 0; i < rootChildren.getLength(); i++) {
            Node node = rootChildren.item(i);
            if (BUSINESS_LOG_CONFIG_NODE_NAME.equals(node.getNodeName())) {
                results.add(node);
            }
        }
        return results;
    }

    private String findPreTemplateFrom(Document document) {
        Element root = document.getDocumentElement();
        NodeList rootChildren = root.getChildNodes();
        for (int i = 0; i < rootChildren.getLength(); i++) {
            Node node = rootChildren.item(i);
            if (PRE_TEMPLATE_NODE_NAME.equals(node.getNodeName())) {
                return node.getTextContent();
            }
        }
        return "";
    }

    private String findTemplateFrom(Node configNode) {
        NodeList nodeList = configNode.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (TEMPLATE_NODE_NAME.equals(node.getNodeName())) {
                return node.getTextContent();
            }
        }
        return "";
    }


    private Document loadXmlConfig() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
            return builder.parse(getXmlConfigFileInputStream());
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private FileInputStream getXmlConfigFileInputStream() {
        try {
            return new FileInputStream(this.getClass().getClassLoader().getResource(XML_CONFIG_PATH).getFile());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}