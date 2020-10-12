package cn.uncode.dal.mongo;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.MongoException;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;

public class MongoDataBase implements InitializingBean, MongoDB {
	
	private static Logger LOG = LoggerFactory.getLogger(MongoDataBase.class);
	
	private String host;
	
	private int port;
	
	private String db;
	
	private String username;

	private String password;
	private String maxConnectionsPerHost;
	private String threadsAllowedToBlockForConnectionMultiplier;
	
	// 超时设置(毫秒)
	private int serverSelectionTimeout= 1000 * 30; ;
	private int maxWaitTime=1000 * 60 * 2;;
	private int connectTimeout = 1000 * 10;
	private int socketTimeout= 0 ;
	
	private DB mongo;
	
	private com.mongodb.client.MongoDatabase mongoDB;

	@Override
	public DB getDB() throws MongoException {
		if(null == mongo){
			try {
				mongo = initDB(host, port, db, username, password);
			} catch (UnknownHostException e) {
				LOG.error("Init mongo db error.", e);
			} 
		}
		return mongo;
	}

	@Override
	public DB getDB(String username, String password) throws MongoException {
		if(null == mongo){
			try {
				mongo = initDB(host, port, db, username, password);
			} catch (UnknownHostException e) {
				LOG.error("Init mongo db error.", e);
			} 
		}
		return mongo;
	}
	
	public void setHost(String host) {
		this.host = host;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setDb(String db) {
		this.db = db;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMaxConnectionsPerHost() {
		return maxConnectionsPerHost;
	}

	public void setMaxConnectionsPerHost(String maxConnectionsPerHost) {
		this.maxConnectionsPerHost = maxConnectionsPerHost;
	}

	public String getThreadsAllowedToBlockForConnectionMultiplier() {
		return threadsAllowedToBlockForConnectionMultiplier;
	}

	public void setThreadsAllowedToBlockForConnectionMultiplier(
			String threadsAllowedToBlockForConnectionMultiplier) {
		this.threadsAllowedToBlockForConnectionMultiplier = threadsAllowedToBlockForConnectionMultiplier;
	}

	public int getServerSelectionTimeout() {
		return serverSelectionTimeout;
	}

	public void setServerSelectionTimeout(int serverSelectionTimeout) {
		this.serverSelectionTimeout = serverSelectionTimeout;
	}

	public int getMaxWaitTime() {
		return maxWaitTime;
	}

	public void setMaxWaitTime(int maxWaitTime) {
		this.maxWaitTime = maxWaitTime;
	}

	public int getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public int getSocketTimeout() {
		return socketTimeout;
	}

	public void setSocketTimeout(int socketTimeout) {
		this.socketTimeout = socketTimeout;
	}

	private DB initDB(String url, Integer port, String dbName,
			String user, String password) throws UnknownHostException,
			MongoException {
		Mongo m = null;
		if (null != port) {
			m = new Mongo(url, port);
		} else {
			m = new Mongo(url);
		}
		DB db = m.getDB(dbName);
		/*if (!db.authenticate(user, password.toCharArray())) {
			LOG.error("Couldn't Authenticate MongoDB!!!!!!.........");
			throw new MongoException("Couldn't Authenticate !!!!!!.........");
		}*/
		return db;
	}
	
	public com.mongodb.client.MongoDatabase initDB3(String url, Integer port, String dbName,
			String user, String password, MongoClientOptions options, Map<String, Object> param) 
			throws UnknownHostException, MongoException {
		MongoClient mongoClient = null;
		List<ServerAddress> seeds = new ArrayList<ServerAddress>();
		if(StringUtils.isNotEmpty(url)){
			String[] urls = url.split(",");
			for(String ul:urls){
				if(StringUtils.isNotEmpty(ul)){
					seeds.add(new ServerAddress(ul, port != null ? port : 27017));
				}
			}
		}
		MongoCredential credential = MongoCredential.createCredential(user, dbName, password.toCharArray());
		List<MongoCredential> credentials = new ArrayList<MongoCredential>();
		//credentials.add(credential);
		mongoClient = new MongoClient(seeds, credentials, options);
		return mongoClient.getDatabase(dbName);
	}
	

	private com.mongodb.client.MongoDatabase initDB3(String url, Integer port, String dbName,
			String user, String password) throws UnknownHostException,
			MongoException {
		MongoClient mongoClient = null;
		List<ServerAddress> seeds = new ArrayList<ServerAddress>();
		if(StringUtils.isNotEmpty(url)){
			String[] urls = url.split(",");
			for(String ul:urls){
				if(StringUtils.isNotEmpty(ul)){
					seeds.add(new ServerAddress(ul, port != null ? port : 27017));
				}
			}
		}
		MongoCredential credential = MongoCredential.createCredential(user, dbName, password.toCharArray());
		List<MongoCredential> credentials = new ArrayList<MongoCredential>();
		//credentials.add(credential);
		mongoClient = new MongoClient(seeds, credentials);
		return mongoClient.getDatabase(dbName);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if(StringUtils.isNotEmpty(host)){
			try {
				buildMongoDB();
			} catch (Exception e) {
				LOG.error("afterPropertiesSet Init mongo db error.", e);
			}
		}
		
	}

	@Override
	public MongoDatabase getMongoDB() throws MongoException {
		if(null == mongoDB){
			try {
				buildMongoDB();
			} catch (UnknownHostException e) {
				LOG.error("Init mongo db error.", e);
			} 
		}
		return mongoDB;
	}
	
	public MongoDatabase buildMongoDB()throws UnknownHostException{
		// mongoDB = initDB3(host, port, db, username, password);
		MongoClientOptions.Builder builder=new MongoClientOptions.Builder();
		
		if(StringUtils.isNotBlank(maxConnectionsPerHost) && maxConnectionsPerHost.matches("[\\d]+")){
			builder.connectionsPerHost(Integer.valueOf(maxConnectionsPerHost));
		}
		if(StringUtils.isNotBlank(threadsAllowedToBlockForConnectionMultiplier) && threadsAllowedToBlockForConnectionMultiplier.matches("[\\d]+")){
			builder.threadsAllowedToBlockForConnectionMultiplier(Integer.valueOf(threadsAllowedToBlockForConnectionMultiplier));
		}

		builder.serverSelectionTimeout(serverSelectionTimeout);
		builder.maxWaitTime(maxWaitTime);
		builder.connectTimeout(connectTimeout);
		builder.socketTimeout(socketTimeout);
		
		
		MongoClientOptions options=builder.build();
		LOG.info("maxConnectionsPerHost="+options.getMinConnectionsPerHost()+", threadsAllowedToBlockForConnectionMultiplier="+options.getThreadsAllowedToBlockForConnectionMultiplier()
				+", serverSelectionTimeout="+options.getServerSelectionTimeout()+", maxWaitTime="+options.getMaxWaitTime()
				+", connectTimeout="+options.getConnectTimeout()+", socketTimeout="+options.getSocketTimeout());
		mongoDB = initDB3(host, port, db, username, password, options, null);
		return mongoDB;
	}

	@Override
	public MongoDatabase getMongoDB(String username, String password)
			throws MongoException {
		if(null == mongoDB){
			try {
				mongoDB = initDB3(host, port, db, username, password);
			} catch (UnknownHostException e) {
				LOG.error("Init mongo db error.", e);
			} 
		}
		return mongoDB;
	}

}
