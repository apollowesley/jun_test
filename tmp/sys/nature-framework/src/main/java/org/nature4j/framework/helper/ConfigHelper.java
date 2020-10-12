package org.nature4j.framework.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.nature4j.framework.bean.RedisHostBean;
import org.nature4j.framework.config.ConfigConstant;
import org.nature4j.framework.util.CastUtil;
import org.nature4j.framework.util.FileUtil;
import org.nature4j.framework.util.PropsUtil;
import org.nature4j.framework.util.StringUtil;

/**
 * Created by Ocean on 2016/3/9.
 */
public final class ConfigHelper {

    private static final Properties CONFIG_PROPS = PropsUtil.loadProps(ConfigConstant.CONFIG_FILE);

	public static String[] getDb(){
    	String dbstr = PropsUtil.getString(CONFIG_PROPS, ConfigConstant.DB_JDBC,null);
    	if (dbstr==null){
    		return new String[0];
		}
    	String[] dbs = dbstr.split(",");
    	for (int i = 0; i < dbs.length; i++) {
			if (StringUtil.isNotBank(dbs[i])) {
				dbs[i] = dbs[i].trim()+".";
			}
		}
    	return dbs;
    }
    
    public static String getJdbcDriver(String db) {
        return PropsUtil.getString(CONFIG_PROPS, db+ConfigConstant.JDBC_DRIVER);
    }

    public static String getJdbcUrl(String db) {
    	 String jdbcUrl = PropsUtil.getString(CONFIG_PROPS, db+ConfigConstant.JDBC_URL);
    	 if ("org.sqlite.JDBC".endsWith(getJdbcDriver(db))) {
			String slqliteDb = jdbcUrl.replace("jdbc:sqlite:","");
			if (slqliteDb.startsWith("/")) {
				jdbcUrl = "jdbc:sqlite:"+FileUtil.getClassPath()+slqliteDb.substring(1);
			}
		}
        return jdbcUrl;
    }

    public static String getJdbcUserName(String db) {
        return PropsUtil.getString(CONFIG_PROPS, db+ConfigConstant.JDBC_USERNAME);
    }

    public static String getJdbcPassWord(String db) {
        return PropsUtil.getString(CONFIG_PROPS, db+ConfigConstant.JDBC_PASSWORD);
    }
    
    public static String getJdbcDataSource() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_DATASOURCE);
    }
    
    public static String getJdbcFilters(String db) {
        return PropsUtil.getString(CONFIG_PROPS, db+ConfigConstant.JDBC_FILTERS);
    }
    public static int getJdbcInitialSize(String db) {
        return PropsUtil.getInt(CONFIG_PROPS, db+ConfigConstant.JDBC_INITIAL_SIZE,5);
    }
    public static int getJdbcMinidle(String db) {
        return PropsUtil.getInt(CONFIG_PROPS, db+ConfigConstant.JDBC_MINIDLE,5);
    }
    public static int getJdbcMaxAction(String db) {
        return PropsUtil.getInt(CONFIG_PROPS, db+ConfigConstant.JDBC_MAXACTIVE,20);
    }
    public static long getJdbcMaxWait(String db) {
        return PropsUtil.getLong(CONFIG_PROPS, db+ConfigConstant.JDBC_MAXWAIT,60000);
    }
    public static long getJdbcTimeBetweenEvictionRunsMillis(String db) {
        return PropsUtil.getLong(CONFIG_PROPS, db+ConfigConstant.JDBC_TIME_BETWEEN_EVICTION_RUNS_MILLIS,300000);
    }
    public static int getJdbcMaxPoolPreparedStatementPerConnectionSize(String db) {
        return PropsUtil.getInt(CONFIG_PROPS, db+ConfigConstant.JDBC_MAXPOOL_PREPARED_STATEMENT_PER_CONNECTION_SIZE,20);
    }
    public static boolean getJdbcPoolPreparedStatements(String db) {
        return PropsUtil.getBoolean(CONFIG_PROPS, db+ConfigConstant.JDBC_POOL_PREPARED_STATEMENTS,true);
    }
    public static int getJdbcDefaultTransactionIsolation(String db) {
        return PropsUtil.getInt(CONFIG_PROPS, db+ConfigConstant.JDBC_DEFAULT_TRANSACTION_ISOLATION,2);
    }
    

    public static String getAppBasePackage() {
        return PropsUtil.getString(CONFIG_PROPS,ConfigConstant.NATURE_BASE_PACKAGE);
    }

    public static String getAppPagePath() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.NATURE_PAGE_PATH, "/WEB-INF/view/");
    }
    
    public static String getExcludesPattern() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.NATURE_EXCLUDES_PATTERN);
    }

    public static String getFileEncoding(){
    	return System.getProperty("file.encoding","UTF-8");
    }
    
    public static String getCacheManager(){
    	return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.DB_CACHE_MANAGER);
    }
    
    public static boolean getDbAutocreate() {
        return PropsUtil.getBoolean(CONFIG_PROPS, ConfigConstant.DB_AUTOCREATE, false);
    }
    
    public static boolean getI18n() {
        return PropsUtil.getBoolean(CONFIG_PROPS, ConfigConstant.NATURE_I18N, false);
    }
    /**
     * 默认值100兆
     */
    public static int getMultipartMaxSize(){
    	return PropsUtil.getInt(CONFIG_PROPS, ConfigConstant.NATURE_MULTIPART_MAXSIZE, 1024*1024*1024);
    }
    
    public static String getAllowFileRegex() {
		return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.NATURE_ALLOW_FILE_REGEX,"^(?!.*(\\.jsp)).*$");
	}
    
    public static boolean getAuth() {
        return PropsUtil.getBoolean(CONFIG_PROPS, ConfigConstant.NATURE_AUTH, false);
    }

	public static String getQueryPaswordSql() {
		return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.AUTH_QUERY_PASSWORD_SQL);
	}

	public static String getQueryUserInfoSql() {
		return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.AUTH_QUERY_USERINFO_SQL);
	}

	public static String getQueryPremsSql() {
		return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.AUTH_QUERY_PREMS_SQL);
	}

	public static String getQueryAllPremsSql() {
		return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.AUTH_QUERY_ALL_PREMS_SQL);
	}
	public static String getNoLoginUrl() {
		return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.AUTH_NO_LOGIN_URL);
	}

	public static String getNoPremUrl() {
		return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.AUTH_NO_PREM_URL);
	}
	
	public static String getAuthCacheName() {
		return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.AUTH_CACHE_NAME);
	}

	public static boolean getQuartzCluster() {
		return PropsUtil.getBoolean(CONFIG_PROPS, ConfigConstant.NATURE_QUARTZ_CLUSTER, false);
	}

	public static int getRedisMaxIdle() {
		return PropsUtil.getInt(CONFIG_PROPS, ConfigConstant.REDIS_MAXIDLE, 8);
	}

	public static int getRedisMinIdle() {
		return PropsUtil.getInt(CONFIG_PROPS, ConfigConstant.REDIS_MAXIDLE, 0);
	}

	public static long getRedisMaxWaitMillis() {
		return PropsUtil.getLong(CONFIG_PROPS, ConfigConstant.REDIS_MAXWAITMILLIS, 1000);
	}

	public static int getRedisMaxTotal() {
		return PropsUtil.getInt(CONFIG_PROPS, ConfigConstant.REDIS_MAXTOTAL, 8);
	}

	public static List<RedisHostBean> getRedisHostPortMap() {
		String hostStr = PropsUtil.getString(CONFIG_PROPS, ConfigConstant.REDIS_HOST);
		List<RedisHostBean> list = null;
		if (StringUtil.isNotBank(hostStr)) {
			list = new ArrayList<RedisHostBean>();
			String[] hosts = hostStr.split(",");
			for (String h : hosts) {
				RedisHostBean redisHostBean = new RedisHostBean();
				String[] hostAndportAndpass = h.split("\\s+");
				String hostAndport = hostAndportAndpass[0];
				String pass = null;
				if(hostAndportAndpass.length>1){
					pass = hostAndportAndpass[1];
				}
				String[] hostport = hostAndport.split("\\:");
				String host = hostport[0];
				String port = hostport[1];
				redisHostBean.setHost(host);
				redisHostBean.setPort(CastUtil.castInt(port));
				redisHostBean.setPassword(pass);
				list.add(redisHostBean);
			}
		}
		return list;
	}

	public static int getQuartzLiveCycle() {
		return PropsUtil.getInt(CONFIG_PROPS, ConfigConstant.QUARTZ_LIVE_CYCLE_SECONDS, 100);
	}

	public static int getQuartzLiveTest() {
		return PropsUtil.getInt(CONFIG_PROPS, ConfigConstant.QUARTZ_LIVE_TEST_SECONDS, 60);
	}

	public static String getQuartzThreadCount() {
		return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.QUARTZ_THREAD_COUNT, String.valueOf(10));
	}

	public static String getEndpointAddress() {
		return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.NATURE_WS_ENDPOINT_ADDRESS);
	}

	public static String getBasePath() {
		return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.NATURE_BASE_PATH);
	}

	public static int pageSize() {
		return PropsUtil.getInt(CONFIG_PROPS, ConfigConstant.NATURE_PAGESIZE, 10);
	}

	public static String getPagination() {
		return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.NATURE_PAGINATION);
	}
	
	public static String[] getPlugins(){
		String dbstr = PropsUtil.getString(CONFIG_PROPS, ConfigConstant.NATURE_PLUGINS);
		String[] dbs = null;
		if (StringUtil.isNotBank(dbstr)) {
			dbs = dbstr.replaceAll(" ", "").split(",");
		}
    	return dbs;
	}
	
	/**
	 * 获取nature.properties配置文件中的值
	 * @param key
	 * @return 如果不存在则返回""
	 */
	public static String getValue(String key) {
		return PropsUtil.getString(CONFIG_PROPS, key);
	}
	
	/**
	 * 获取nature.properties配置文件中的值
	 * @param key
	 * @param defaultValue 默认值
	 * @return 如果不存在则返回defaultValue
	 */
	public static String getValue(String key,String defaultValue) {
		return PropsUtil.getString(CONFIG_PROPS, key,defaultValue);
	}


    public static String getDbSSHUser(String db) {
        return PropsUtil.getString(CONFIG_PROPS, db+ConfigConstant.JDBC_SSH_USER);
    }

	public static String getDbSSHHost(String db) {
		return PropsUtil.getString(CONFIG_PROPS, db+ConfigConstant.JDBC_SSH_HOST);
	}

	public static int getDbSSHRemotePort(String db) {
		return PropsUtil.getInt(CONFIG_PROPS, db+ConfigConstant.JDBC_SSH_REMOTE_PORT);
	}

	public static String getDbSSHPassword(String db) {
		return PropsUtil.getString(CONFIG_PROPS, db+ConfigConstant.JDBC_SSH_PASSWORD);
	}

	public static int getDbSSHLocalPort(String db) {
		return PropsUtil.getInt(CONFIG_PROPS, db+ConfigConstant.JDBC_SSH_LOCAL_PORT);
	}

	public static int getDbSSHPort(String db) {
		return PropsUtil.getInt(CONFIG_PROPS, db+ConfigConstant.JDBC_SSH_PORT);
	}

	// milliseconds
	public static int getDbSSHTimeOut(String db) {
		return PropsUtil.getInt(CONFIG_PROPS, db+ConfigConstant.JDBC_SSH_TIME_OUT,5000);
	}

    public static String getDbSSHRemoteHost(String db) {
        return PropsUtil.getString(CONFIG_PROPS, db+ConfigConstant.JDBC_SSH_REMOTE_HOST);
    }
}
