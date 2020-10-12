package com.szfore.util.basic;


public class MultipleKey {
	
	public static final String UNDERLINE = "_";
	
	private String dilimer;
	
	private String[] names;

	public static MultipleKey create(String dilimer, String... names)
	{
		return new MultipleKey(dilimer, names);
	}
	
	private MultipleKey(String dilimer, String[] names) 
	{
		this.dilimer = dilimer;
		this.names = names;
	}

	public String getDilimer() {
		return dilimer;
	}

	public void setDilimer(String dilimer) {
		this.dilimer = dilimer;
	}

	public String[] getNames() {
		return names;
	}

	public void setNames(String[] names) {
		this.names = names;
	}
	
	
}
