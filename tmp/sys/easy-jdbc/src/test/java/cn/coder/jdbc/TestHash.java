package cn.coder.jdbc;

import java.util.Objects;

public class TestHash {
	public static void main(String[] args) {
		System.out.println(Objects.hash("select count(1) from weike", Weike.class));
		System.out.println(Objects.hash("select count(1) from weike", Weike.class));
		System.out.println(Objects.hash("select * from weike", Weike.class));
		System.out.println(Objects.hash("select * from weike", Weike.class));
		System.out.println(Objects.hash("select * from weike ", Weike.class));
		System.out.println(Objects.hash("123", Weike.class));
		System.out.println(Objects.hash("1234", Weike.class));
	}
}
