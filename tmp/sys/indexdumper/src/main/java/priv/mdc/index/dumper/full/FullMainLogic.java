package priv.mdc.index.dumper.full;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import priv.mdc.index.dumper.filter.FilterBean;
import priv.mdc.index.dumper.filter.FilterModel;
import priv.mdc.index.dumper.full.DatabaseBean.FieldBean;
import priv.mdc.index.dumper.full.DatabaseBean.TableBean;
import priv.mdc.index.dumper.model.IndexDocReq;
import priv.mdc.index.dumper.model.ModelRecord;
import priv.mdc.index.dumper.model.ModelUtil;
import priv.mdc.index.dumper.model.UpdateType;
import priv.mdc.index.dumper.process.IndexDocReqQueue;
import priv.mdc.index.dumper.process.ProcessChannelMgr;
import priv.mdc.index.dumper.util.ConfigInfo;

/**
 * 全量键索引的主逻辑
 * @author xuhuahai
 *
 */
public class FullMainLogic {
	
	protected final static Logger logger = LoggerFactory
			.getLogger(FullMainLogic.class);
	
	static{
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
		}
	}
	
	
	/**
	 * 读出所有需要执行的目标数据库节点
	 */
	public static List<DatabaseBean> loadDbNodes(){
		Yaml yaml = new Yaml();
		List list = yaml.loadAs(ConfigInfo.class.getClassLoader().getResourceAsStream( 
		        "db_nodes.yaml"), List.class);
		List<DatabaseBean> databaseBeanList = new ArrayList<DatabaseBean>();
		if(list!=null && !list.isEmpty()){
			for(int i=0; i<list.size(); i++){
				Map map = (Map)list.get(i);
				DatabaseBean bean = DatabaseBean.parse(map);
				if(bean!=null){
					databaseBeanList.add(bean);
				}
			}
		}
		return databaseBeanList;
	}
	
	/**
	 * 获取连接
	 * @param databaseBean
	 * @return
	 * @throws Exception
	 */
	public static Connection requireConnection(DatabaseBean databaseBean) throws Exception{
		logger.info("require database connection : {}:{}/{}",databaseBean.getHost(),databaseBean.getPort(),databaseBean.getInstance());
		Connection conn = null;
		String url = "jdbc:mysql://"+databaseBean.getHost()+":"+databaseBean.getPort()+"/"+databaseBean.getInstance();
		String user = databaseBean.getUser();
		String psw = databaseBean.getPass();
		conn = DriverManager.getConnection(url, user, psw);
		return conn;
	}
	
	/**
	 * 处理这个数据库
	 * @param databaseBean
	 * @throws SQLException 
	 */
	public static void handleDatabase(DatabaseBean databaseBean) throws Exception{
		Connection conn = requireConnection(databaseBean);
		Iterator<TableBean> tableIter = databaseBean.getTables().iterator();
		try{
			while(tableIter.hasNext()){
				handleTable(tableIter.next(),conn);
			}
		}catch(Exception ex){
			throw ex;
		}finally{
			conn.close();
		}
	}
	
	/**
	 * 查询出表的所有记录
	 * 为了效率考虑，避免因为数据量膨胀而效率直线下降,采取下面的办法来搜索记录
	 * 分为两步：
	 * 一，先依赖主键排序分页，将所有的ID值查出来
	 * 二，直接使用ID查询出数据的内容
	 * 
	 * 备注：
	 * mysql> explain select USER_ID,NICK_NAME,MOBILE_PHONE from USERS order by USER_ID limit 0,10;
	 * +----+-------------+-------+-------+---------------+---------+---------+------+------+-------+
	 * | id | select_type | table | type  | possible_keys | key     | key_len | ref  | rows | Extra |
	 * +----+-------------+-------+-------+---------------+---------+---------+------+------+-------+
	 * |  1 | SIMPLE      | USERS | index | NULL          | PRIMARY | 72      | NULL |    6 |       |
	 * +----+-------------+-------+-------+---------------+---------+---------+------+------+-------+
	 * 
	 * mysql> explain select USER_ID from USERS order by USER_ID limit 0,10;
	 * +----+-------------+-------+-------+---------------+---------+---------+------+------+-------------+
	 * | id | select_type | table | type  | possible_keys | key     | key_len | ref  | rows | Extra       |
	 * +----+-------------+-------+-------+---------------+---------+---------+------+------+-------------+
	 * |  1 | SIMPLE      | USERS | index | NULL          | PRIMARY | 72      | NULL |    6 | Using index |
	 * +----+-------------+-------+-------+---------------+---------+---------+------+------+-------------+
	 * 可以看到如果仅仅返回主键，那么数据是仅仅从索引树返回的，不需要再到实际数据集中查询信息了，这样做会更快！
	 * 
	 * @param tableBean
	 * @param conn
	 */
	public static void handleTable(TableBean tableBean, Connection conn) throws Exception{
		logger.info("handle table : {}",tableBean.getName());
		int queryPageSize = ConfigInfo.getInt("full.query.page.size");
		int pageNum = 0;
		Statement stmt = conn.createStatement();
		do{
			//查询ID
			String queryIDSql = "select "+tableBean.getIdFieldName()+" from "+tableBean.getName()
					+" order by "+tableBean.getIdFieldName()+" limit "+(pageNum*queryPageSize)+","+queryPageSize;
			logger.info("queryIDSql = {}",queryIDSql);
		    ResultSet rs = stmt.executeQuery(queryIDSql);
		    List<String> idList = new ArrayList<String>();
			int count = 0;
		    while(rs.next()){
		    	count++;
		    	idList.add(rs.getString(1));
		    }
		    rs.close();
		    //根据ID查询字段数据
		    handleIDList(idList, tableBean, conn);
		    if(count<queryPageSize){
		    	break;
		    }
			pageNum++;
		}while(true);
		stmt.close();
	}
	
	public static void handleIDList(List<String> idList, TableBean tableBean, Connection conn) throws Exception{
		if(idList.isEmpty()){
			return;
		}
		logger.info("handle id list : {}",idList);
		StringBuilder fieldSb = new StringBuilder();
		fieldSb.append(tableBean.getIdFieldName());
		List<FieldBean> fields = tableBean.getFields();
		for(int i = 0; i<fields.size(); ++i){
			fieldSb.append(',').append(fields.get(i).getName());
		}
		FilterBean filterBean = FilterModel.queryFilterBean(tableBean.getName());
		int handlePageSize = ConfigInfo.getInt("full.handle.page.size");
		int count = idList.size() / handlePageSize;
		if(idList.size() % handlePageSize > 0){
			count++;
		}
		int pageIdx = 0;
		Statement stmt = conn.createStatement();
		while(pageIdx<count){
			int from = pageIdx * handlePageSize;
			int end  = (pageIdx+1) * handlePageSize - 1;
			if(end > idList.size()-1){
				end = idList.size()-1;
			}
			StringBuilder inSb = new StringBuilder();
			for(int i=from; i<=end; ++i){
				if(i == from){
					inSb.append(tableBean.retrieveIdValue(idList.get(i)));
				}else{
					inSb.append(',').append(tableBean.retrieveIdValue(idList.get(i)));
				}
			}
			String queryDataSql = "select "+fieldSb.toString()+" from "+tableBean.getName()
					+" where "+tableBean.getIdFieldName()+" in ("+ inSb.toString() +")";
			logger.info("queryDataSql = {}",queryDataSql);
			ResultSet rs = stmt.executeQuery(queryDataSql);
		    while(rs.next()){
		    	ModelRecord record = new ModelRecord();
		    	record.setId(getFieldValue(rs,tableBean.getIdFieldName(),tableBean.getIdFieldType()));
				for(int i = 0; i<fields.size(); ++i){
					FieldBean fieldBean = fields.get(i);
					record.putField(fieldBean.getName(), getFieldValue(rs,fieldBean.getName(),fieldBean.getType()));
				}
				IndexDocReq indexDocReq = ModelUtil.generate(filterBean, record, UpdateType.CREATE);
				IndexDocReqQueue queue = ProcessChannelMgr.getFullProcessQueue();
				if(queue!=null){
					try {
						queue.putReq(indexDocReq);
					} catch (Exception e) {
						logger.error(e.getMessage(),e);
					}
				}
		    }
			rs.close();
			pageIdx++;
		}
		stmt.close();
	}
	
	public static String getFieldValue(ResultSet rs, String fieldName, String fieldType) throws SQLException{
		if("text".equals(fieldType)){
			return rs.getString(fieldName);
		}else if("int".equals(fieldType)){
			return rs.getInt(fieldName)+"";
		}
		return null;
	}
	
	/**
	 * 扫描数据库，全量扫描数据表，构造出ModelRecord，放入全量处理通道
	 * 
	 */
	public static void execute(){
		long startTime = System.currentTimeMillis();
		List<DatabaseBean> databaseBeanList = loadDbNodes();
		Iterator<DatabaseBean> dbIter = databaseBeanList.iterator();
		while(dbIter.hasNext()){
			try {
				handleDatabase(dbIter.next());
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}
		}
		long cost = System.currentTimeMillis() - startTime;
		logger.info("!!!!!!!  Full index cost = {} s", cost/1000 );
	}
	
	
}
