package common.framework.dsb.example;

import javax.xml.bind.JAXBElement;

import common.framework.dsb.config.DSBConfType;
import common.framework.dsb.config.ObjectFactory;
import common.framework.dsb.config.SystemPropsType;
import common.framework.jaxb.JAXBTool;

public class DSBConfTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		ObjectFactory of = new ObjectFactory();
		DSBConfType dsbConfType = of.createDSBConfType();
		dsbConfType.setDsbDeployDir("deploy");
		dsbConfType.setDsbSocketPort(10000);
		dsbConfType.setDsbRmiServerPort(10001);
		dsbConfType.setDsbServerLanguage("zh");
		dsbConfType.setDsbServerLogDir("log");
		dsbConfType.setDsbServerLogName("dsbserver");
		dsbConfType.setDsbServerLogLevel(0);
		SystemPropsType spt1 = of.createSystemPropsType();
		spt1.setPropName("java.library.path");
		spt1.setPropValue("dll");
		dsbConfType.getSystemprops().add(spt1);
		SystemPropsType spt2 = of.createSystemPropsType();
		spt2.setPropName("java.rmi.server.hostname");
		spt2.setPropValue("server.dsb.ideal.com");
		dsbConfType.getSystemprops().add(spt2);
		JAXBElement<DSBConfType> jaxbElement = of.createDSBConf(dsbConfType);
		byte[] bytes = JAXBTool.marshal(jaxbElement, "common.framework.dsb.bo", DSBConfTest.class.getClassLoader());
		System.out.println(new String(bytes));

	}

}
