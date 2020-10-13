package component.app;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.myapp.dao.UserInfoDao;
import com.myapp.entity.TUser;
import com.myapp.entity.UserInfo;
import com.myapp.service.TUserService;

public class LockTest extends TestBase {

	@Autowired
	TUserService userService;
	@Autowired
	UserInfoDao userInfoDao;
	
	@Test
	public void testA() {
		UserInfo userInfo = userInfoDao.get(3);
		print(userInfo);
		
		
		TUser user = userService.get(3);
		print(user);
	}
	
	@Test
	public void testUpdate() throws InterruptedException {
		final TUser user = userService.get(3);
		user.setUsername("44");
		
		final TUser user2 = userService.get(3);
		user2.setUsername("55");
		
		
		Thread thread1 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(300); // 暂停，让threa2跑完
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				userService.updateIgnoreNull(user);
			}
		});
		
		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				userService.updateIgnoreNull(user2);
			}
		});
		
		thread1.start();
		Thread.sleep(10);
		thread2.start();
		
		thread1.join();
		thread2.join();
		
	}
	
}
