/**
 * Copyright 2013 ABSir's Studio
 * 
 * All right reserved
 *
 * Create on 2013-12-24 下午2:55:55
 */
package com.absir.system.test.configure;

import com.absir.appserv.system.bean.value.JaLang;

/**
 * @author absir
 * 
 */
public class XCardDefine {

	@JaLang("卡牌名称")
	private String name;

	@JaLang("卡片介绍")
	private String desc;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

}
