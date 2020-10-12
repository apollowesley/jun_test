package com.xieke.java8.demo.lambda;

import java.util.Arrays;
import java.util.List;

/**
 * 方法引用
 * 
 * @author 邪客
 *
 */
public class CarDemo {

	public static void main(String[] args) {
		Car car = Car.create(Car::new);
		List<Car> cars = Arrays.asList(car);

		cars.forEach(Car::collide);

		cars.forEach(Car::repair);

		final Car police = Car.create(Car::new);
		cars.forEach(police::follow);
	}

}

