package x.javaplus.mysql.db;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import x.javaplus.util.Closer;

/**
 * 数据库对象
 * 操作对象必须要继承Serializable
 * 可用transient来表示临时变量 不会保存数据库
 * @author deng		
 * @date 2015-12-12 下午9:08:44
 */
public class SqlObject {
	
	public static Object wrap( byte[] bytes ) {
		if( bytes == null )
			return null;
		ByteArrayInputStream bi = null;
		ObjectInputStream oi = null;
		try {
			bi = new ByteArrayInputStream(bytes);
			oi = new ObjectInputStream(bi);
			return oi.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			Closer.close(bi, oi);
		}
		return null;
	}
	
	public static byte[] toBytes( Serializable object ){
		if( object == null )
			return null;
		ByteArrayOutputStream bo = null;
		ObjectOutputStream oo = null;
		try {
			bo = new ByteArrayOutputStream();
			oo = new ObjectOutputStream(bo);
			oo.writeObject(object);
			return bo.toByteArray();
		} catch (Exception e) {
//			System.out.println( e.getMessage() );
			e.printStackTrace();
		} finally {
			Closer.close(bo,oo);
		}
		return null;
	}
}
