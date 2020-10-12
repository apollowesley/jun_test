package com.xieke.java8.demo.lambda;

import java.util.Arrays;
import java.util.List;

/**
 * Lambda表达式和函数式接口
 * 
 * @author 邪客
 *
 */
public class LambdaDemo {
	public static void main(String[] args) {
		// 最简单的Lambda表达式可由逗号分隔的参数列表、->符号和语句块组成，例如：
		Arrays.asList("a", "b", "d").forEach(e -> System.out.println(e));

		// 如果Lambda表达式需要更复杂的语句块，则可以使用花括号将该语句块括起来，类似于Java中的函数体，例如：
		Arrays.asList("a", "b", "d").forEach(e -> {
			System.out.print(e);
			System.out.println(e);
		});

		// Lambda表达式有返回值，返回值的类型也由编译器推理得出。如果Lambda表达式中的语句块只有一行，则可以不用使用return语句，下列两个代码片段效果相同：
		List<String> list = Arrays.asList("a", "d", "c");
		list.sort((e1, e2) -> e1.compareTo(e2));
		System.out.println(list);
		// Arrays.asList("a", "d", "c").sort((e1, e2) -> {
		// int result = e1.compareTo(e2);
		// return result;
		// });

	}
}

