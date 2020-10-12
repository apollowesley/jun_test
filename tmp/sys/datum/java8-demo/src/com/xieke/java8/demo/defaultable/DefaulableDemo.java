package com.xieke.java8.demo.defaultable;

import java.util.function.Supplier;

/**
 * 接口的默认方法和静态方法
 * 
 * @author 邪客
 *
 */
public class DefaulableDemo {
	private interface Defaulable {
		// Interfaces now allow default methods, the implementer may or
		// may not implement (override) them.
		default String notRequired() {
			return "Default implementation";
		}
	}

	private static class DefaultableImpl implements Defaulable {

	}

	private static class OverridableImpl implements Defaulable {
		@Override
		public String notRequired() {
			return "Overridden implementation";
		}
	}

	private interface DefaulableFactory {
		// Interfaces now allow static methods
		static Defaulable create(Supplier<Defaulable> supplier) {
			return supplier.get();
		}
	}

	public static void main(String[] args) {
		Defaulable defaulable = DefaulableFactory.create(DefaultableImpl::new);
		System.out.println(defaulable.notRequired());

		defaulable = DefaulableFactory.create(OverridableImpl::new);
		System.out.println(defaulable.notRequired());
	}
}
