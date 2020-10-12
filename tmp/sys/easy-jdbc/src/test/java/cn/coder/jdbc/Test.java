package cn.coder.jdbc;

public class Test {
	public static void main(String[] args) {
		SqlSessionFactory.createSessions();
		SqlSession session = SqlSessionFactory.getSession("test");
		SqlSession session1 = SqlSessionFactory.getSession("test1");
		// System.out.println(session.selectOne(Integer.class, "select count(1)
		// from weike"));
		// System.out.println(session.selectOne(Weike.class, "select * from
		// weike"));
		// System.out.println(session.selectList(Weike.class, "select * from
		// weike"));
		// System.out.println(session1.selectOne(Integer.class, "select count(1)
		// from tes"));
		// SqlSessionFactory.destory();
		Weike weike = new Weike();
		weike.setTitle("这是一个测试");
		session.insert(weike);
		System.out.println(weike.getId());

		SqlTranction tran = null;
		try {
			tran = session.beginTranction(session1.beginTranction());
			session.execute("INSERT INTO `user_t` (`user_name`) VALUES (?)", "sdf");
			session1.execute("INSERT INTO `tes` (`aa`) VALUES (?)", "sef");
			tran.commit();
		} catch (Exception e) {
			tran.rollback(e);
			e.printStackTrace();
		} 
		System.out.println(session.selectOne(Integer.class, "select count(1) from user_t"));
		System.out.println(session1.selectOne(Integer.class, "select count(1) from tes"));
		SqlSessionFactory.destory();
	}
}
