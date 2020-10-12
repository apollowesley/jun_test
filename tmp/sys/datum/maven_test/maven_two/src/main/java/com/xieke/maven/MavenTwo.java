package com.xieke.maven;

public class MavenTwo
{
	public String hello(String str){
		return new MavenOne().hello(str);
	}
}