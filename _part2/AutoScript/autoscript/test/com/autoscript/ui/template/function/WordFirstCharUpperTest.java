package com.autoscript.ui.template.function;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import freemarker.template.TemplateModelException;

public class WordFirstCharUpperTest {

	@Test
	public void testExec() {
		WordFirstCharUpper upper = new WordFirstCharUpper();
		List parameters = new ArrayList();
		parameters.add(null);
		parameters.add("_");
		try {
			System.out.println(upper.exec(parameters));			
		} catch (TemplateModelException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		parameters.clear();
		parameters.add("assert_code_table");
		parameters.add(null);
		try {
			System.out.println(upper.exec(parameters));			
		} catch (TemplateModelException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		parameters.clear();
		parameters.add("assert_code_table");
		parameters.add("_");
		try {
			System.out.println(upper.exec(parameters));			
		} catch (TemplateModelException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		parameters.clear();
		parameters.add("assert_code_table_");
		parameters.add("_");
		try {
			System.out.println(upper.exec(parameters));			
		} catch (TemplateModelException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}		
		parameters.clear();
		parameters.add("assert_code_table");
		try {
			System.out.println(upper.exec(parameters));			
		} catch (TemplateModelException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}
