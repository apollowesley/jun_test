package cn.coder.jdbc;

public class Simple {
	public static void main(String[] args) {
		SqlSessionFactory.createSessions();
		SqlSession session = SqlSessionFactory.getSession();
		try {
			Weike weike = session.selectOne(Weike.class, "select * from weike where id=?", 2);
			System.out.println(weike.getTitle());
		} finally {
			
		}
		SqlSessionFactory.destory();
	}
}
