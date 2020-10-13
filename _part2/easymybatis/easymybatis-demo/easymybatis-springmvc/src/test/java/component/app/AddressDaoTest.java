package component.app;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.myapp.dao.AddressDao;
import com.myapp.entity.Address;

import net.oschina.durcframework.easymybatis.query.Query;

public class AddressDaoTest extends TestBase {

	@Autowired
	private AddressDao addressDao;

	@Test
	public void testGet() {
		Address a = new Address();
		a.setId("1");
		Address addre = addressDao.get(a);
		this.print(addre);
	}

	@Test
	public void testUpdate() {
		Address addr = addressDao.get("1");
		addr.setAddress("aaa");
		int i = addressDao.update(addr);
		this.print(i);
	}

	@Test
	public void testUpdate2() {
		Address addr = addressDao.get("1");
		addr.setAddress("aaa");
		int i = addressDao.updateIgnoreNull(addr);
		this.print(i);
	}

	@Test
	public void testUpdate3() {
		Address addr = new Address();
		addr.setAddress("aaa");
		Query query = Query.build().eq("id", "1");
		int i = addressDao.updateIgnoreNullByExpression(addr, query);
		this.print(i);
	}
	
	/**
	 * 乐观锁测试
	 */
	@Test
	public void testUpdate4() {
		CountDownLatch latch = new CountDownLatch(1);
		for (int i = 0; i < 5; i++) {
			Address addr = addressDao.get("1");
			Address address = new Address();
			BeanUtils.copyProperties(addr, address); // 复制一份避免缓存问题
			
			address.setAddress("addr" + i);
			new Thread(new Service(address, latch)).start();
		}
		latch.countDown(); // 执行到这里后,所有等待的Service会同时解除await
		System.out.println("finished");
	}

	class Service implements Runnable {
		Address addr;
		CountDownLatch latch;

		public Service(Address addr, CountDownLatch latch) {
			super();
			this.addr = addr;
			this.latch = latch;
		}

		@Override
		public void run() {
			try {
				latch.await(); // 先等待,直到CountDownLatch的count减到0
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			int i = addressDao.update(addr);
			System.out.println(Thread.currentThread().getName() + ",update->" + i + ",address:" + addr);
		}
	}
	
	@Test
	public void testDel() {
		Address obj = addressDao.get("3");
		int i = addressDao.del(obj);
		this.print("testDel --> " + i);
	}

}
