package com.bvg.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Profile("heroku")
@Configuration
public class DataBaseHerokuConfig {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Bean
    public DataSource getDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url(getDbUrlWithP6Spy());

        return dataSourceBuilder.build();
    }

    private String getDbUrlWithP6Spy() {
        return dbUrl.replaceFirst(":", ":p6spy:");
    }
}
