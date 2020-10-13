package net.oschina.durcframework.easymybatis.generator.run;

/**
 * 代码生成执行程序
 */
public class Run {
	
	
	public static void main(String[] args) {
		Client client = new Client();
		
		// resources/cfg下
		String[] propFiles = { 
				"cfg/t_user-oracle.properties",
				};
		client.gen(propFiles);
	}
	
}
