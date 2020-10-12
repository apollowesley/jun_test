package com.jun.plugin.core.utils.demo;

import java.io.IOException;

import com.jun.plugin.core.utils.lang.Base64;
import com.jun.plugin.core.utils.lang.Console;

/**
 * Base64样例
 * @author Looly
 *
 */
public class Base64Demo {
	public static void main(String[] args) throws IOException {
		String text = "我是待编码的字符串5sdksdvksdskldgklfsd///////////";
		String encode = Base64.encode(text);
		Console.log(encode);
		
		//解码
		String decodeStr = Base64.decodeStr(encode);
		Console.log(decodeStr);
	}
}
