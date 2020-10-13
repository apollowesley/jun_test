package net.oschina.durcframework.easymybatis.generator;

import net.oschina.durcframework.easymybatis.generator.client.Client;

/**
 * @author tanghc
 *
 */
public class RunAll_easydoc {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Client client = new Client();
		
		client.genAll("cfg/easydoc.properties");
	}

}
