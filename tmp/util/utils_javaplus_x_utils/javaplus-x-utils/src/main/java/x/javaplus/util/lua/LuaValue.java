package x.javaplus.util.lua;

import org.keplerproject.luajava.LuaException;
import org.keplerproject.luajava.LuaObject;

/**
 * lua返回专用值
 * @author deng		
 * @date 2015-7-13 下午9:55:17
 */
public class LuaValue {

	private Number number 	= null;
	private String string 	= null;
	private Object object 	= null;
	private boolean boo 	= false;
	
	public LuaValue( LuaObject lo ) throws LuaException {
		if( lo.isBoolean() ){
			boo = lo.getBoolean();
		}else if( lo.isNumber() ){
			number = lo.getNumber();
			string = lo.getString();
			object = number;
		}else if( lo.isString() ){
			string = lo.getString();
			object = string;
		}else if( lo.isJavaObject() ){
			object = lo.getObject();
		}
	}
	
	public boolean getBoolean(){
		try {
			return boo;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public int getInt(){
		try {
			return number.intValue();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public float getFloat(){
		try {
			return number.floatValue();
		} catch (Exception e) {
			e.printStackTrace();
			return 0f;
		}
	}
	
	public long getLong(){
		try {
			return number.longValue();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public short getShort(){
		try {
			return number.shortValue();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public byte getByte(){
		try {
			return number.byteValue();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public String getString(){
		try {
			return string;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public Object getObject(){
		try {
			return object;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
