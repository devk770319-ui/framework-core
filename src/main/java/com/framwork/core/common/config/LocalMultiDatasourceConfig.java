package com.framwork.core.common.config;

import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("local")
@ConditionalOnProperty(prefix = "framework.datasource", name = "mode", havingValue = "multi", matchIfMissing = true)
public class LocalMultiDatasourceConfig {

    @Bean
    @Primary
    public DataSource primaryDataSource(CoreDatasourceProperties properties) {
        CoreDatasourceProperties.Db db = properties.getLocal().getPrimary();
        return DataSourceBuilder.create()
                .driverClassName(db.getDriverClassName())
                .url(db.getUrl())
                .username(db.getUsername())
                .password(db.getPassword())
                .build();
    }

    @Bean(name = "secondaryDataSource")
    public DataSource secondaryDataSource(CoreDatasourceProperties properties) {
        CoreDatasourceProperties.Db db = properties.getLocal().getSecondary();
        return DataSourceBuilder.create()
                .driverClassName(db.getDriverClassName())
                .url(db.getUrl())
                .username(db.getUsername())
                .password(db.getPassword())
                .build();
    }
}
