package com.siweifu.utils;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * xml 转换测试
 * @title XmlTest.java
 * @description 
 * @company 北京思维夫网络科技有限公司
 * @author 卢春梦
 * @version 1.0
 * @created 2015年7月21日下午2:16:43
 */
public class XmlTest {

	@JacksonXmlRootElement(localName = "xml")
	public static class Test {

		@JacksonXmlCData
		private String name;
		private int age;

		private CC cc;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getAge() {
			return age;
		}
		public void setAge(int age) {
			this.age = age;
		}
		public CC getCc() {
			return cc;
		}
		public void setCc(CC cc) {
			this.cc = cc;
		}
	}

	public static class CC {
		private int a;

		public int getA() {
			return a;
		}

		public void setA(int a) {
			this.a = a;
		}

		// 转换时需要默认的构造函数
		public CC() {
			super();
		}

		public CC(int a) {
			super();
			this.a = a;
		}
	}

	@SuppressWarnings("rawtypes")
	public static void main(String[] args) throws IOException {
		Test t = new Test();
		t.setName("卢春梦");
		t.setAge(26);
		t.setCc(new CC(1));

		String xml = XmlUtils.toXml(t);

		System.out.println(xml);

		Test r = XmlUtils.toBean(xml, Test.class);
		System.out.println(JsonUtils.toJson(r));

		Map rr = XmlUtils.toBean(xml, Map.class);
		System.out.println(rr);
	}
}
