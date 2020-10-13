package net.oschina.durcframework.easymybatis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;

import junit.framework.TestCase;
import net.oschina.durcframework.easymybatis.handler.FillHandler;
import net.oschina.durcframework.easymybatis.handler.FillType;

public class OrderTest extends TestCase {

	class A extends FillHandler<Integer> {

		@Override
		public String getColumnName() {
			// TODO Auto-generated method stub
			return "a";
		}

		@Override
		public FillType getFillType() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		protected Object getFillValue(Integer defaultValue) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public int getOrder() {
			return 1;
		}
	}
	
	class B extends FillHandler<String> {

		@Override
		public String getColumnName() {
			// TODO Auto-generated method stub
			return "b";
		}

		@Override
		public FillType getFillType() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getOrder() {
			return 4;
		}

		@Override
		protected Object getFillValue(String defaultValue) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	@Test
	public void testOrder() {
		List<FillHandler<?>> list = new ArrayList<>();
		list.add(new B());
		list.add(new A());
		
		Collections.sort(list,new Comparator<FillHandler<?>>() {
			@Override
			public int compare(FillHandler<?> o1, FillHandler<?> o2) {
				return Integer.compare(o1.getOrder(), o2.getOrder());
			}
		});
		
		for (FillHandler<?> fillHandler : list) {
			System.out.println(fillHandler.getColumnName());
		}
	}
}
