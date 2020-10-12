package org.nature4j.framework.config;

/**
 * Created by Ocean on 2016/3/9.
 */
public interface ConfigConstant {
    String CONFIG_FILE              = "nature.properties";
    String NATURE_BASE_PACKAGE  	= "nature.base_package";
    String NATURE_PAGE_PATH     	= "nature.page_path";
    String NATURE_BASE_PATH 		= "nature.base_path";
    String NATURE_EXCLUDES_PATTERN  = "nature.excludes_pattern";
    String NATURE_I18N   			= "nature.i18n";
    String NATURE_MULTIPART_MAXSIZE = "nature.multipart_maxsize";
    String NATURE_ALLOW_FILE_REGEX 	= "nature.allow_file_regex";
    String NATURE_AUTH 				= "nature.auth";
    String NATURE_QUARTZ_CLUSTER	= "nature.quartz_cluster";
    String NATURE_WS_ENDPOINT_ADDRESS= "nature.ws_endpoint_address";
    String NATURE_PAGESIZE 			= "nature.pagesize";
    String NATURE_PAGINATION 		= "nature.pagination";
    String NATURE_PLUGINS 			= "nature.plugin";
    

    String DB_AUTOCREATE   			= "db.table_auto_create";
    String DB_CACHE_MANAGER   		= "db.cache_manager";
    String DB_JDBC					= "db.jdbc";
    String JDBC_DATASOURCE          = "db.datasource";

    String JDBC_DRIVER              = "jdbc.driver";
    String JDBC_URL                 = "jdbc.url";
    String JDBC_USERNAME            = "jdbc.username";
    String JDBC_PASSWORD            = "jdbc.password";
    
    String JDBC_INITIAL_SIZE        = "jdbc.initial_size";
    String JDBC_MINIDLE        		= "jdbc.minidle";
    String JDBC_MAXACTIVE          	= "jdbc.maxactive";
    String JDBC_MAXWAIT          	= "jdbc.maxwait";
    String JDBC_TIME_BETWEEN_EVICTION_RUNS_MILLIS 				= "jdbc.time_between_eviction_runs_millis";
    String JDBC_POOL_PREPARED_STATEMENTS 						= "jdbc.pool_prepared_statements";
    String JDBC_MAXPOOL_PREPARED_STATEMENT_PER_CONNECTION_SIZE	= "jdbc.maxpool_prepared_statement_per_connection_size";
    String JDBC_DEFAULT_TRANSACTION_ISOLATION          			= "jdbc.default_transaction_isolation";
    String JDBC_FILTERS          	= "jdbc.filters";
    
    String QUARTZ_LIVE_CYCLE_SECONDS= "quartz.live_cycle_seconds";
    String QUARTZ_LIVE_TEST_SECONDS = "quartz.live_test_seconds";
    String QUARTZ_THREAD_COUNT 		= "quartz.thread_count";
    
    String REDIS_HOST  				= "redis.host";
    String REDIS_MAXIDLE  			= "redis.maxidle";
    String REDIS_MINIDLE 			= "redis.minidle";
    String REDIS_MAXTOTAL  			= "redis.maxtotal";
    String REDIS_TESTWHILEIDLE  	= "redis.testwhileidle";
    String REDIS_MAXWAITMILLIS  	= "redis.maxwaitmillis";
    
    String AUTH_QUERY_PASSWORD_SQL	= "auth.query_password_sql";
    String AUTH_QUERY_USERINFO_SQL	= "auth.query_userinfo_sql";
    String AUTH_QUERY_PREMS_SQL		= "auth.query_prems_sql";
    String AUTH_QUERY_ALL_PREMS_SQL	= "auth.query_all_prems_sql";
    String AUTH_NO_LOGIN_URL		= "auth.no_login_url";
    String AUTH_NO_PREM_URL			= "auth.no_prem_url";
    String AUTH_CACHE_NAME			= "auth.cache_name";


    String JDBC_SSH_USER            = "jdbc.ssh.user";
    String JDBC_SSH_HOST            = "jdbc.ssh.host";
    String JDBC_SSH_REMOTE_PORT     = "jdbc.ssh.remote_port";
    String JDBC_SSH_PASSWORD        = "jdbc.ssh.password";
    String JDBC_SSH_LOCAL_PORT      = "jdbc.ssh.local_port";
    String JDBC_SSH_PORT            = "jdbc.ssh.port";
    String JDBC_SSH_TIME_OUT        = "jdbc.ssh.timeout";
    String JDBC_SSH_REMOTE_HOST     = "jdbc.ssh.remote_host";
}
