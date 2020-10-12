package notransaction;

import com.freedom.mysql.myrwsplit.bean.Role;
import com.freedom.mysql.myrwsplit.helper.LoggerHelper;
import com.freedom.mysql.myrwsplit.helper.MapperUtils;
import com.freedom.mysql.myrwsplit.interfaces.RoleMapper;

public class MyRwSplitWithNoTransactionAdvanced {
	@SuppressWarnings("unused")
	private static LoggerHelper LOGGER = LoggerHelper.getLogger(MyRwSplitWithNoTransactionAdvanced.class);

	public static void main(String[] args) {
		//
		RoleMapper mapper = MapperUtils.getMapper(RoleMapper.class);
		Role role = mapper.getRole0(13);

		//
		mapper = MapperUtils.getMapper(RoleMapper.class);
		role = new Role();
		role.setAuthor("xxx");
		role.setTitle("yyy");
		mapper.insertRole(role);
		//
		// try {
		// Thread.currentThread().sleep(6*1000);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//
		//
		mapper = MapperUtils.getMapper(RoleMapper.class);
		role = mapper.getRole0(13);
		//
		//
		mapper = MapperUtils.getMapper(RoleMapper.class);
		role = new Role();
		role.setAuthor("xxx");
		role.setTitle("yyy");
		mapper.insertRole(role);

	}
}
