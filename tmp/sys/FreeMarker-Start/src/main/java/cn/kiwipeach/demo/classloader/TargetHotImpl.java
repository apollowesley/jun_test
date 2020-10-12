package cn.kiwipeach.demo.classloader;

/**
 * BaseManager的子类，此类需要实现java类的热加载功能
 * @author liuyazhuang
 *
 */
public class TargetHotImpl implements IHot {

	@Override
	public void logic() {
		System.out.println("刘卜铷***********");
	}

}
