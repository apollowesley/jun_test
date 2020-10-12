package component.app;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.myapp.dao.AddressDao;
import com.myapp.entity.Address;

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
}
