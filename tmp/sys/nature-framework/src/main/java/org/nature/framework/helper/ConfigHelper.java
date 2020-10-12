package org.nature.framework.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.nature.framework.bean.RedisHostBean;
import org.nature.framework.config.ConfigConstant;
import org.nature.framework.util.CastUtil;
import org.nature.framework.util.PropsUtil;
import org.nature.framework.util.StringUtil;

/**
 * Created by Ocean on 2016/3/9.
 */
public final class ConfigHelper {

    private static final Properties CONFIG_PROPS = PropsUtil.loadProps(ConfigConstant.CONFIG_FILE);

    public static String getJdbcDriver() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_DRIVER);
    }

    public static String getJdbcUrl() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_URL);
    }

    public static String getJdbcUserName() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_USERNAME);
    }

    public static String getJdbcPassWord() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_PASSWORD);
    }
    
    public static String getJdbcDataSource() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_DATASOURCE);
    }
    
    public static String getJdbcFilters() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_FILTERS);
    }
    public static int getJdbcInitialSize() {
        return PropsUtil.getInt(CONFIG_PROPS, ConfigConstant.JDBC_INITIAL_SIZE,5);
    }
    public static int getJdbcMinidle() {
        return PropsUtil.getInt(CONFIG_PROPS, ConfigConstant.JDBC_MINIDLE,5);
    }
    public static int getJdbcMaxAction() {
        return PropsUtil.getInt(CONFIG_PROPS, ConfigConstant.JDBC_MAXACTIVE,20);
    }
    public static long getJdbcMaxWait() {
        return PropsUtil.getLong(CONFIG_PROPS, ConfigConstant.JDBC_MAXWAIT,60000);
    }
    public static long getJdbcTimeBetweenEvictionRunsMillis() {
        return PropsUtil.getLong(CONFIG_PROPS, ConfigConstant.JDBC_TIME_BETWEEN_EVICTION_RUNS_MILLIS,300000);
    }
    public static int getJdbcMaxPoolPreparedStatementPerConnectionSize() {
        return PropsUtil.getInt(CONFIG_PROPS, ConfigConstant.JDBC_MAXPOOL_PREPARED_STATEMENT_PER_CONNECTION_SIZE,20);
    }
    public static boolean getJdbcPoolPreparedStatements() {
        return PropsUtil.getBoolean(CONFIG_PROPS, ConfigConstant.JDBC_POOL_PREPARED_STATEMENTS,true);
    }
    public static int getJdbcDefaultTransactionIsolation() {
        return PropsUtil.getInt(CONFIG_PROPS, ConfigConstant.JDBC_DEFAULT_TRANSACTION_ISOLATION,2);
    }
    
    

    public static String getAppBasePackage() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.NATURE_BASE_PACKAGE)+",org.nature.framework";
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
    	return PropsUtil.getInt(CONFIG_PROPS, ConfigConstant.NATURE_MULTIPART_MAXSIZE, 100*1024*1024);
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
				System.out.println(hostAndport);
				String pass = hostAndportAndpass[1];
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
    
}
