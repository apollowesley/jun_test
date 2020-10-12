package org.nature.framework.db;

import java.util.UUID;

import org.nature.framework.core.NatureMap;

public class PrimarkeyStrategy {
	public static void putUuid(String primaryKey, NatureMap natureMap) {
		String uuid =  UUID.randomUUID().toString().replaceAll("-", "");
		natureMap.put(primaryKey, uuid);
	}
}
