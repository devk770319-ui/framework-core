package com.framwork.core.common.config;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import java.util.Properties;
import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("local")
@ConditionalOnProperty(prefix = "framework.datasource", name = "mode", havingValue = "xa")
public class LocalXaDatasourceConfig {

    @Bean
    @Primary
    public DataSource primaryDataSource(CoreDatasourceProperties properties) {
        return createAtomikosDataSource("xaPrimary", properties.getLocal().getPrimary());
    }

    @Bean(name = "secondaryDataSource")
    public DataSource secondaryDataSource(CoreDatasourceProperties properties) {
        return createAtomikosDataSource("xaSecondary", properties.getLocal().getSecondary());
    }

    private DataSource createAtomikosDataSource(String resourceName, CoreDatasourceProperties.Db db) {
        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setUniqueResourceName(resourceName);
        xaDataSource.setXaDataSourceClassName(db.getXaDataSourceClassName());

        Properties xaProperties = new Properties();
        xaProperties.putAll(db.getXaProperties());
        xaProperties.putIfAbsent("URL", db.getUrl());
        xaProperties.putIfAbsent("user", db.getUsername());
        xaProperties.putIfAbsent("password", db.getPassword());
        xaDataSource.setXaProperties(xaProperties);
        return xaDataSource;
    }
}
