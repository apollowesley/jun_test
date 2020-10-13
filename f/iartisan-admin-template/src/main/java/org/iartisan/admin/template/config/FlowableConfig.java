package org.iartisan.admin.template.config;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseConnection;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.flowable.common.engine.impl.cfg.IdGenerator;
import org.flowable.job.service.impl.asyncexecutor.AsyncExecutor;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.flowable.spring.boot.FlowableHttpProperties;
import org.flowable.spring.boot.FlowableMailProperties;
import org.flowable.spring.boot.FlowableProperties;
import org.flowable.spring.boot.ProcessEngineAutoConfiguration;
import org.flowable.spring.boot.app.FlowableAppProperties;
import org.flowable.spring.boot.idm.FlowableIdmProperties;
import org.flowable.spring.boot.process.FlowableProcessProperties;
import org.flowable.spring.boot.process.Process;
import org.flowable.spring.boot.process.ProcessAsync;
import org.flowable.spring.boot.process.ProcessAsyncHistory;
import org.flowable.ui.modeler.properties.FlowableModelerAppProperties;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * flowable 配置
 *
 * @author King  2019-8-28
 */
@Configuration
public class FlowableConfig extends ProcessEngineAutoConfiguration {

    protected static final String LIQUIBASE_CHANGELOG_PREFIX = "ACT_DE_";

    public FlowableConfig(FlowableProperties flowableProperties,
                          FlowableProcessProperties processProperties,
                          FlowableAppProperties appProperties,
                          FlowableIdmProperties idmProperties,
                          FlowableMailProperties mailProperties,
                          FlowableHttpProperties httpProperties) {
        super(flowableProperties, processProperties, appProperties, idmProperties, mailProperties, httpProperties);
    }

    @Bean
    public FlowableModelerAppProperties initFlowableModelerAppProperties() {
        FlowableModelerAppProperties flowableModelerAppProperties = new FlowableModelerAppProperties();
        return flowableModelerAppProperties;
    }

    @Bean
    public Liquibase liquibase(DataSource dataSource) {
        try {
            DatabaseConnection connection = new JdbcConnection(dataSource.getConnection());
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(connection);
            database.setDatabaseChangeLogTableName(LIQUIBASE_CHANGELOG_PREFIX + database.getDatabaseChangeLogTableName());
            database.setDatabaseChangeLogLockTableName(LIQUIBASE_CHANGELOG_PREFIX + database.getDatabaseChangeLogLockTableName());

            Liquibase liquibase = new Liquibase("META-INF/liquibase/flowable-modeler-app-db-changelog.xml", new ClassLoaderResourceAccessor(), database);
            liquibase.update("flowable");
            return liquibase;

        } catch (Exception e) {
            throw new RuntimeException("Error creating liquibase database", e);
        }
    }

    @Override
    @Bean
    public SpringProcessEngineConfiguration springProcessEngineConfiguration(DataSource dataSource, PlatformTransactionManager platformTransactionManager,
                                                                             @Process ObjectProvider<IdGenerator> processIdGenerator,
                                                                             ObjectProvider<IdGenerator> globalIdGenerator,
                                                                             @ProcessAsync ObjectProvider<AsyncExecutor> asyncExecutorProvider,
                                                                             @ProcessAsyncHistory ObjectProvider<AsyncExecutor> asyncHistoryExecutorProvider) throws IOException {

        SpringProcessEngineConfiguration conf = super.springProcessEngineConfiguration(dataSource, platformTransactionManager, processIdGenerator, globalIdGenerator, asyncExecutorProvider, asyncHistoryExecutorProvider);
        conf.setActivityFontName("宋体");
        conf.setLabelFontName("宋体");
        conf.setAnnotationFontName("宋体");
        return conf;
    }
}
