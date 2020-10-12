package cn.kiwipeach.demo.classloader;

/**
 * 后台启动一条线程不断刷新重新加载实现了热加载的类
 * @author liuyazhuang
 *
 */
public class HotDetectingTask implements Runnable {

	@Override
	public void run() {
		while (true) {
			IHot manager = HotClassImplFactory.getManager(HotClassImplFactory.MY_MANAGER);
			manager.logic();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
