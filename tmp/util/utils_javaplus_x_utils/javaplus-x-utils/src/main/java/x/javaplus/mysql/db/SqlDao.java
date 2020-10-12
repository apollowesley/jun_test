package x.javaplus.mysql.db;

import java.sql.SQLException;
import java.util.List;

import x.javaplus.collections.Lists;

public class SqlDao {
	
	private static final String SELECT = "SELECT";
	private static final String INSERT = "INSERT";
	private static final String UPDATE = "UPDATE";
	private static final String DELETE = "DELETE";
	
	private DBObject o;
	private String tableName;
	private String type;
	
	private String statement;
	private String statement1;
	private String condition;
	private List<Object> update = null;
	
	protected SqlDao( String tableName ) {
		this.o = DBObject.create();
		this.tableName = tableName;
	}
	
	//---select
	protected void select( String id, boolean isUpdate ) {
		type = SELECT;
		try {
			String sql = "SELECT * FROM " + tableName + " WHERE id=" + id;
			o.prepareStatement( sql);
			o.executeQuery();
			if( isUpdate )
				_update();
		} catch (SQLException e) { }
	}
	
	protected void selectByExact( String arg ) {
		type = SELECT;
		try {
			String sql = "SELECT * FROM " + tableName + (arg.isEmpty() ? "" : (" WHERE " + arg));
			o.prepareStatement( sql);
			o.executeQuery();
		} catch (SQLException e) { }
	}
	
	protected boolean next() {
		try {
			return o.next();
		} catch (SQLException e) {
			return false;
		}
	}
	
	//---insert
	protected void insert() {
		if( type != null && type.equals( INSERT ) ) 
			throw new RuntimeException( type + "需要先调用commit()才能再次使用" );
		type 		= INSERT;
		statement 	= "";
		statement1 	= "";
		update 		= Lists.newArrayList();
	}
	
	//---update
	protected void _update(){
		if( type != null && type.equals( UPDATE ) ) 
			throw new RuntimeException( type + "需要先调用commit()才能再次使用" );
		type 		= UPDATE;
		statement 	= "";
		condition	= null;
		update 		= Lists.newArrayList();
		update.add( null );
	}
	protected void _updateByExact( String arg ){
		if( type != null && type.equals( UPDATE ) ) 
			throw new RuntimeException( type + "需要先调用commit()才能再次使用" );
		type 		= UPDATE;
		statement 	= "";
		condition	= arg;
		update 		= Lists.newArrayList();
//		update.add( null );
	}
	
	//---delete
	protected void delete( String id ){
		type = DELETE;
		try {
			String sql = "DELETE FROM " + tableName + " WHERE id=" + id;
			o.prepareStatement( sql);
			o.executeUpdate();
		} catch (SQLException e) { }
	}
	protected void deleteByExact( String arg ) {
		type = DELETE;
		try {
			String sql = "DELETE FROM " + tableName + (arg.isEmpty() ? "" : (" WHERE " + arg));
			o.prepareStatement( sql);
			o.executeUpdate();
		} catch (SQLException e) { }
	}
	
	//-----------
	protected boolean isSelect(){
		return type.equals( SELECT );
	}
	protected boolean isUpdate(){
		return type.equals( UPDATE );
	}
	protected boolean isInsert(){
		return type.equals( INSERT );
	}
	protected boolean isDelete(){
		return type.equals( DELETE );
	}
	
	protected void commit( boolean isSave ){
		if( update != null && !update.isEmpty() && isSave ){
			save();
		}
		o.close();
		type = "";
		statement = "";
		statement1 = "";
		condition = "";
		update = null;
	}
	
	private void save(){
		try {
			String sql;
			if( isInsert() ){
				sql = "INSERT INTO " + tableName + "(" + statement + ") values (" + statement1 + ")";
				o.prepareStatement( sql);
				for( int i = 0; i < update.size(); i++ )
					o.set( update.get(i) );
				o.executeUpdate();
				
			} else if( isUpdate() || isSelect() ){
				if( condition == null ){
					if( update.get(0) == null )
						throw new RuntimeException( "id为null" );
					condition = " WHERE id=" + update.get(0);
				}else if( !condition.isEmpty() ){
					condition = " WHERE " + condition;
				}
				sql = "UPDATE " + tableName + " SET " + statement + condition;
				o.prepareStatement( sql);
				for( int i = 0; i < update.size(); i++ )
					o.set( update.get(i) );
				o.executeUpdate();
			}
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	protected void setObject( String key, Object value ){
		if( value == null )
			return;
		if( !statement.isEmpty() )
			statement += ",";
		if( (value == null && isUpdate()) || isInsert() ){
			statement += key;
		}else if( isUpdate() || isSelect() ) {
			statement += (key + "=?");
		}
		if( isInsert() ){
			if( !statement1.isEmpty() )
				statement1 += ",";
			statement1 += "?";
		}
		if( update != null ){
			if( (isUpdate() || isSelect()) && key.equals( "id" ) && update.size() >= 1 ){
				update.set( 0, value );
			} else {
				update.add( value );
			}
		}
	}
	
	protected DBObject getObject() {
		return o;
	}
	
}
