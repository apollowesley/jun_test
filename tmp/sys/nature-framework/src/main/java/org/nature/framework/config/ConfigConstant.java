package org.nature.framework.config;

/**
 * Created by Ocean on 2016/3/9.
 */
public interface ConfigConstant {
    String CONFIG_FILE              = "nature.properties";
    String NATURE_BASE_PACKAGE  	= "nature.base_package";
    String NATURE_PAGE_PATH     	= "nature.page_path";
    String NATURE_EXCLUDES_PATTERN  = "nature.excludes_pattern";
    String NATURE_I18N   			= "nature.i18n";
    String NATURE_MULTIPART_MAXSIZE = "nature.multipart_maxsize";
    String NATURE_AUTH 				= "nature.auth";
    String NATURE_QUARTZ_CLUSTER	= "nature.quartz_cluster";
    String NATURE_WS_ENDPOINT_ADDRESS= "nature.ws_endpoint_address";
    

    String DB_AUTOCREATE   			= "db.table_auto_create";
    String DB_CACHE_MANAGER   		= "db.cache_manager";
    
    String QUARTZ_LIVE_CYCLE_SECONDS= "quartz.live_cycle_seconds";
    String QUARTZ_LIVE_TEST_SECONDS = "quartz.live_test_seconds";
    String QUARTZ_THREAD_COUNT 		= "quartz.thread_count";
    
    String REDIS_HOST  				= "redis.host";
    String REDIS_MAXIDLE  			= "redis.maxidle";
    String REDIS_MINIDLE 			= "redis.minidle";
    String REDIS_MAXTOTAL  			= "redis.maxtotal";
    String REDIS_TESTWHILEIDLE  	= "redis.testwhileidle";
    String REDIS_MAXWAITMILLIS  	= "redis.maxwaitmillis";
    
    String JDBC_DRIVER              = "jdbc.driver";
    String JDBC_URL                 = "jdbc.url";
    String JDBC_USERNAME            = "jdbc.username";
    String JDBC_PASSWORD            = "jdbc.password";
    
    String JDBC_DATASOURCE          = "jdbc.datasource";
    String JDBC_INITIAL_SIZE        = "jdbc.initial_size";
    String JDBC_MINIDLE        		= "jdbc.minidle";
    String JDBC_MAXACTIVE          	= "jdbc.maxactive";
    String JDBC_MAXWAIT          	= "jdbc.maxwait";
    String JDBC_TIME_BETWEEN_EVICTION_RUNS_MILLIS 				= "jdbc.time_between_eviction_runs_millis";
    String JDBC_POOL_PREPARED_STATEMENTS 						= "jdbc.pool_prepared_statements";
    String JDBC_MAXPOOL_PREPARED_STATEMENT_PER_CONNECTION_SIZE	= "jdbc.maxpool_prepared_statement_per_connection_size";
    String JDBC_DEFAULT_TRANSACTION_ISOLATION          			= "jdbc.default_transaction_isolation";
    String JDBC_FILTERS          	= "jdbc.filters";
    
    String AUTH_QUERY_PASSWORD_SQL	= "auth.query_password_sql";
    String AUTH_QUERY_USERINFO_SQL	= "auth.query_userinfo_sql";
    String AUTH_QUERY_PREMS_SQL		= "auth.query_prems_sql";
    String AUTH_QUERY_ALL_PREMS_SQL	= "auth.query_all_prems_sql";
    String AUTH_NO_LOGIN_URL		= "auth.no_login_url";
    String AUTH_NO_PREM_URL			= "auth.no_prem_url";
    String AUTH_CACHE_NAME			= "auth.cache_name";
    
    
}
