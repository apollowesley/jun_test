package net.oschina.durcframework.easymybatis;

import java.lang.reflect.Field;
import java.util.List;

import org.junit.Test;

import junit.framework.TestCase;
import net.oschina.durcframework.easymybatis.ext.code.util.ReflectionUtils;

public class FieldTest extends TestCase {

	class A {
		private Integer id;
		private String name;
		private int age;
		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}
	}

	@Test
	public void testA() {
		List<Field> fields = ReflectionUtils.getDeclaredFields(A.class);
		for (Field field : fields) {
			System.out.println("fieldName:[" + field.getName() + "],type:[" + field.getType().getSimpleName() + "]");
		}
	}

}
