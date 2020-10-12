package com.generator.framework;
/**
 * 
 * @author loye
 * @email ling16852@gmail.com
 */
public class GeneratorMain {
	public static void main(String[] args) throws Exception {
		Generator g = new Generator();
		
		g.clean();
		g.generateTable("blog");
//		g.generateAllTable();
	}
}
