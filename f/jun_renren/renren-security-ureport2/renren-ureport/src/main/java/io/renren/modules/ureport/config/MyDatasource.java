package io.renren.modules.ureport.config;

import com.bstek.ureport.definition.datasource.BuildinDatasource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 内置数据源
 * @author kool.zhao
 * 2020年5月25日
 */
@Component
public class MyDatasource implements BuildinDatasource {

    @Autowired
    private DataSource dataSource;

    @Override
    public String name() {
        return "本机数据源";
    }

    @Override
    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
