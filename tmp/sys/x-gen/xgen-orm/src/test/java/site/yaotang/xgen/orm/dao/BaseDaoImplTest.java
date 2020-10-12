package site.yaotang.xgen.orm.dao;

import static org.junit.Assert.fail;

import org.junit.Test;

import site.yaotang.xgen.orm.bean.Book;
import site.yaotang.xgen.orm.bean.Style;

public class BaseDaoImplTest {

	@Test
	public void testSave() {
		Style style = Style.builder().styleId(1).build();
		new BaseDaoImpl().save(Book.builder().author("张三").publisher("拿破仑").style(style).build());
	}

	@Test
	public void testUpdate() {
		Style style = Style.builder().styleId(4).build();
		new BaseDaoImpl().update(Book.builder().bookId(4).style(style).build());
	}

	@Test
	public void testDelete() {
		fail("Not yet implemented");
	}

	@Test
	public void testQuery() {
		fail("Not yet implemented");
	}

	@Test
	public void testQueryFromCache() {
		fail("Not yet implemented");
	}

	@Test
	public void testQueryListString() {
		fail("Not yet implemented");
	}

	@Test
	public void testQueryListDetachCriteria() {
		fail("Not yet implemented");
	}

}
