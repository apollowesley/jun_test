package net.oschina.durcframework.easymybatis.generator;

import net.oschina.durcframework.easymybatis.generator.client.Client;

/**
 * @author tanghc
 *
 */
public class RunAll {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Client client = new Client();
		
		client.genAll("cfg/all.properties");
	}

}
