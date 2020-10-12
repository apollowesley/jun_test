package x.javaplus.util.lua;


import org.keplerproject.luajava.LuaException;
import org.keplerproject.luajava.LuaState;
import org.keplerproject.luajava.LuaStateFactory;

/**
 * 一个lua操作
 * @author deng		
 * @date 2015-7-13 下午9:37:46
 */
public class Lua {
	
	private static Class<?> clazz = null;
	/**
	 * 设置lua中打印对象
	 * @param c
	 */
	public static void setLogClass( Class<?> c ){ clazz = c; }
	
	
	// lua对象
	private LuaState luaState;
	
	
	/**
	 * 创建一个lua
	 * @param filePath 文件路径
	 */
	public Lua( String filePath ) {
		luaState = LuaStateFactory.newLuaState();
		luaState.openLibs();
		registerObject( clazz, "Logs" );
		luaState.LdoFile( filePath );
	}

	public void close() { luaState.close(); }
	
	/**
	 * 为lua里面注册对象
	 * @param obj ( 对象 )
	 * @param name ( 在lua中调用名 )
	 */
	public void registerObject( Object obj, String name ){
		try {
			luaState.pushObjectValue( obj );
			luaState.setGlobal( name );
		} catch (LuaException e) { e.printStackTrace(); }
	}
	
	/**
	 * 获取函数 
	 * @param funName
	 * @return
	 */
	public Function getField( String funName ) {
		
		// 获取lua中的函数
		luaState.getField( LuaState.LUA_GLOBALSINDEX, funName );
		
		return new Function();
	}
	
	public final class Function{
		private Function(){};
		/**
		 * 运行函数 
		 * @param retlen 返回值个数
		 * @param args 参数
		 * @return
		 */
		public LuaValue[] call( int retlen, Object ... args ){
			
			// 参数压栈
			int len = args.length;
			try {
				for( int i = 0; i < len; i++ ){
					luaState.pushObjectValue( args[i] );
				}
			} catch (LuaException e) { e.printStackTrace(); }
		
			// 调用!! 一共len个参数, retlen 返回值
			luaState.call( len, retlen );
			
			if( retlen == 0 ) return null;
			
			LuaValue[] ret = new LuaValue[retlen];
			// 保存返回值, 到 ret 中
			for( int i = 0; i < retlen; i++ ){
				luaState.setField(LuaState.LUA_GLOBALSINDEX, "ret" + i );
				try {
					ret[retlen-i-1] = new LuaValue( luaState.getLuaObject( "ret" + i ) );
				} catch (LuaException e) {
					e.printStackTrace();
				}
			}
			luaState.close();
			return ret;
		}
		
	}
	
}



