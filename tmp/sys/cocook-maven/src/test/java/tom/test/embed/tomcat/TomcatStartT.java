package tom.test.embed.tomcat;

import tom.cocook.TomcatStart;

public class TomcatStartT {
	
public static void main(String[] args) {
	//支持 annotation 方式加载
	args = new String[2];
	args[0] = "-p";
	args[1] = "7070";
	TomcatStart.run(TomcatStartT.class, args);
}

}
