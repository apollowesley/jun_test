package common.framework.jaxb;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.soap.Node;

public class JAXBTool {
	public static byte[] marshal(JAXBElement<?> jaxbElment, Class<?> clazz) throws JAXBException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		JAXBContext jaxbConext = JAXBContext.newInstance(clazz);
		Marshaller marshaller = jaxbConext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.marshal(jaxbElment, baos);
		return baos.toByteArray();
	}

	public static byte[] marshal(JAXBElement<?> jaxbElment, String packageName, ClassLoader classLoader) throws JAXBException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		JAXBContext jaxbConext = JAXBContext.newInstance(packageName, classLoader);
		Marshaller marshaller = jaxbConext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.marshal(jaxbElment, baos);
		return baos.toByteArray();
	}

	public static byte[] marshal(Object obj) throws JAXBException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		JAXBContext jaxbConext = JAXBContext.newInstance(obj.getClass());
		Marshaller marshaller = jaxbConext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		// if (namespacePrefixMapper != null) {
		// marshaller.setProperty("com.sun.xml.internal.bind.marshaller.NamespacePrefixMapper",
		// namespacePrefixMapper);
		// }
		marshaller.marshal(obj, baos);
		return baos.toByteArray();
	}

	public static void marshal(Object obj, Node node) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(obj.getClass());
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		// if (namespacePrefixMapper != null) {
		// marshaller.setProperty("com.sun.xml.internal.bind.marshaller.NamespacePrefixMapper",
		// namespacePrefixMapper);
		// }
		marshaller.marshal(obj, node);
	}

	public static Object unmarshal(byte[] bytes, Class clazz) throws JAXBException {
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		JAXBContext jaxbConext = JAXBContext.newInstance(clazz);
		Unmarshaller unmarshaller = jaxbConext.createUnmarshaller();
		return unmarshaller.unmarshal(bais);
	}

	public static Object unmarshal(byte[] bytes, String packageName, ClassLoader classLoader) throws JAXBException {
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		JAXBContext jaxbConext = JAXBContext.newInstance(packageName, classLoader);
		Unmarshaller unmarshaller = jaxbConext.createUnmarshaller();
		return unmarshaller.unmarshal(bais);
	}

	public static Object unmarshal(URL url, Class clazz) throws JAXBException {
		JAXBContext jaxbConext = JAXBContext.newInstance(clazz);
		Unmarshaller unmarshaller = jaxbConext.createUnmarshaller();
		return unmarshaller.unmarshal(url);
	}

	public static Object unmarshal(URL url, String packageName, ClassLoader classLoader) throws JAXBException {
		JAXBContext jaxbConext = JAXBContext.newInstance(packageName, classLoader);
		Unmarshaller unmarshaller = jaxbConext.createUnmarshaller();
		return unmarshaller.unmarshal(url);
	}

	public static Object unmarshal(InputStream is, Class clazz) throws JAXBException {
		JAXBContext jaxbConext = JAXBContext.newInstance(clazz);
		Unmarshaller unmarshaller = jaxbConext.createUnmarshaller();
		return unmarshaller.unmarshal(is);
	}

	public static Object unmarshal(InputStream is, String packageName, ClassLoader classLoader) throws JAXBException {
		JAXBContext jaxbConext = JAXBContext.newInstance(packageName, classLoader);
		Unmarshaller unmarshaller = jaxbConext.createUnmarshaller();
		return unmarshaller.unmarshal(is);
	}
}
