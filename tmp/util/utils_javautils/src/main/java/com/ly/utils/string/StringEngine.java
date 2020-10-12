package com.ly.utils.string;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * 字符串处理工具
 *  
 * @version 1.2
 */
public class StringEngine {
	/**
	 * 管理者
	 */
	private static ScriptEngineManager manager;
	/**
	 * Script引擎
	 */
	private static ScriptEngine engine;
	
	static {
		manager = new ScriptEngineManager();
		engine = manager.getEngineByName("js");
	}

	/**
	 * ScriptEngine.eval(String)
	 */
	public static Object eval(String eval){
		try {
			return engine.eval(eval);
		} catch (ScriptException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * ScriptEngine.get(Key)
	 */
	public static Object getEval(String key){
		return engine.get(key);
	}
}
