package org.frameworkset.hibernate.serial;

import org.frameworkset.soa.PluginFactory;

public class HibernatePluginFactory implements PluginFactory{
	private static String hibernatePluginNames[] = new String[]{
			"org.frameworkset.hibernate.serial.PersistentBagSerial",
			"org.frameworkset.hibernate.serial.PersistentList",
			"org.frameworkset.hibernate.serial.PersistentMap",
			"org.frameworkset.hibernate.serial.PersistentSet",
			"org.frameworkset.hibernate.serial.PersistentSortedMap",
			"org.frameworkset.hibernate.serial.PersistentSortedSet",
			"org.frameworkset.hibernate.serial.v4.PersistentBagSerial",
			"org.frameworkset.hibernate.serial.v4.PersistentList",
			"org.frameworkset.hibernate.serial.v4.PersistentMap",
			"org.frameworkset.hibernate.serial.v4.PersistentSet",
			"org.frameworkset.hibernate.serial.v4.PersistentSortedMap",
			"org.frameworkset.hibernate.serial.v4.PersistentSortedSet"
	};
	@Override
	public String[] getPlugins() {
		// TODO Auto-generated method stub
		return hibernatePluginNames;
	}

}
