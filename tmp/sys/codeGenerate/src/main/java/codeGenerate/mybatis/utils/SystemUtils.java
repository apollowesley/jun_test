
package codeGenerate.mybatis.utils;

public class SystemUtils {

	/**
	 * @description: 根据代码路径生成默认实体类的名称
	 * @return
	 */
	public static String getBeanNameByPath(String path) {
		String[] split = path.split("\\.");
		String name = split[split.length - 1];
		return name.substring(0, 1).toUpperCase() + name.substring(1);
	}
	
	public static void main(String[] args) {
		System.out.println(getBeanNameByPath("a.b.chidk"));
	}
}
