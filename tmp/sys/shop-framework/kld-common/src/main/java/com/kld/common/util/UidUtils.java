package com.kld.common.util;

import java.util.UUID;

public class UidUtils {

	/** 空串"" */
	private final static String EMPTY_STR = "";
	
	/** -　hyphen　连字号 */
	private final static String HYPHEN = "-";
	
    public static String UID() {
        return UUID.randomUUID().toString();
    }
    
	/**
	 * 生成32位UUID
	 */
	public static String getUUID32() {
		return UUID.randomUUID().toString().replaceAll(HYPHEN, EMPTY_STR).toUpperCase();
	}

}
