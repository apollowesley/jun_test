package cn.kiwipeach.demo.classloader;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试Java类的热加载
 * @author liuyazhuang
 *
 */
public class HotClassLoaderTest {
	public static void main(String[] args) {
		//启动热加载线程
		new Thread(new HotDetectingTask()).start();
	}
}
