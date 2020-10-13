package tom.cocook.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;


public abstract class DBOperations {

	
	public abstract int execute(String paramString, Object... paramArrayOfObject) throws SQLException;
	public abstract int insert(String paramString, Object... paramArrayOfObject) throws SQLException;
	public abstract int insert(Connection conn, String paramString, Object... paramArrayOfObject) throws SQLException;
	public abstract int update(Connection conn, String paramString, Object... paramArrayOfObject) throws SQLException;
	public abstract int update(String paramString, Object... paramArrayOfObject) throws SQLException;


	public abstract <T> T queryForObject(String paramString, Class<T> paramClass, Object... paramArrayOfObject) throws SQLException;
	public abstract <T> T queryForObject(Connection conn ,String paramString, Class<T> paramClass, Object... paramArrayOfObject) throws SQLException;

	public abstract Long queryForLong(String paramString, Object... paramArrayOfObject) throws SQLException;
	
	public abstract Integer queryForInt(String paramString, Object... paramArrayOfObject) throws SQLException;
	
	public abstract String queryForString(String paramString, Object... paramArrayOfObject) throws SQLException;
	
	public abstract <T> List<T> queryForList(String paramString, Class<T> paramClass, Object... paramArrayOfObject) throws SQLException;
	public abstract <T> List<T> queryForList(Connection conn, String paramString, Class<T> paramClass, Object... paramArrayOfObject) throws SQLException;

	public abstract List<Map<String, Object>> queryForList(String paramString, Object... paramArrayOfObject) throws SQLException;
	public abstract List<Map<String, Object>> queryForList(Connection conn,String paramString, Object... paramArrayOfObject) throws SQLException;
	public abstract List<Map<String, Object>> queryForLinkedMapList(String paramString, Object... paramArrayOfObject) throws SQLException;

	
	public abstract Map<String, Object> queryForMap(String paramString, Object... paramArrayOfObject) throws SQLException;
	public abstract Map<String, Object> queryForMap(Connection conn, String paramString, Object... paramArrayOfObject) throws SQLException;
	public abstract Map<String, Object> queryForLinkedMap(String paramString) throws SQLException;
	public abstract Map<String, Object> queryForLinkedMap(String paramString, Object... paramArrayOfObject) throws SQLException;

	
	public abstract int[] batchUpdate(String paramString, List<Object[]> paramList) throws SQLException;;
	public abstract int[] batchUpdate(String paramString,Object... obj) throws SQLException;;

}
