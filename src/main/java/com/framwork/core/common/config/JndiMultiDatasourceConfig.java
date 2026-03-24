package com.framwork.core.common.config;

import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

@Configuration
@Profile({"dev", "stag", "prod"})
@ConditionalOnProperty(prefix = "framework.datasource", name = "mode", havingValue = "multi")
public class JndiMultiDatasourceConfig {

    @Bean
    @Primary
    public DataSource primaryDataSource(CoreDatasourceProperties properties) {
        JndiDataSourceLookup lookup = new JndiDataSourceLookup();
        lookup.setResourceRef(true);
        return lookup.getDataSource(properties.getJndi().getPrimaryName());
    }

    @Bean(name = "secondaryDataSource")
    public DataSource secondaryDataSource(CoreDatasourceProperties properties) {
        JndiDataSourceLookup lookup = new JndiDataSourceLookup();
        lookup.setResourceRef(true);
        return lookup.getDataSource(properties.getJndi().getSecondaryName());
    }
}
