package com.acfun.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;


/**
 * Created by jianghong on 2017/3/28.
 */
@Configuration
public class DruidDataSourceConfig {
    @Value("${spring.datasource.driver-class-name}")
    private String driver;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String userName;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.initialSize}")
    private String initialSize;
    @Value("${spring.datasource.minIdle}")
    private int minIdle;
    @Value("${spring.datasource.maxActive}")
    private int maxActive;
    @Value("${spring.datasource.maxWait}")
    private long maxWait;
    @Value("${spring.datasource.timeBetweenEvictionRunsMillis}")
    private long minEvictableIdleTimeMillis;
    @Value("${spring.datasource.validationQuery}")
    private String validationQuery;
    @Value("${spring.datasource.testWhileIdle}")
    private String testOnBorrow;
    @Value("${spring.datasource.testOnReturn}")
    private String testOnReturn;
    @Value("{spring.datasource.testWhileIdle}")
    private String testWhileIdle;
    @Value("${spring.datasource.poolPreparedStatements}")
    private String poolPreparedStatements;
    @Value("${spring.datasource.maxPoolPreparedStatementPerConnectionSize}")
    private int maxPoolPreparedStatementPerConnectionSize;
    @Value("${spring.datasource.filters}")
    private String filters;
    @Value("${spring.datasource.useGlobalDataSourceStat}")
    private String useGlobalDataSourceStat;
    @Bean
    public DataSource dataSource() throws SQLException {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUsername(this.userName);
        druidDataSource.setPassword(this.password);
        druidDataSource.setDriverClassName(this.driver);
        druidDataSource.setUrl(this.url);
        druidDataSource.setMinIdle(this.minIdle);
        druidDataSource.setMaxActive(this.maxActive);
        druidDataSource.setMaxWait(this.maxWait);
        druidDataSource.setMinEvictableIdleTimeMillis(this.minEvictableIdleTimeMillis);
        druidDataSource.setValidationQuery(this.validationQuery);
        druidDataSource.setTestOnBorrow(Boolean.valueOf(this.testOnBorrow));
        druidDataSource.setTestOnReturn(Boolean.valueOf(this.testOnReturn));
        druidDataSource.setTestWhileIdle(Boolean.valueOf(this.testWhileIdle));
        druidDataSource.setPoolPreparedStatements(Boolean.valueOf(this.poolPreparedStatements));
        druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(this.maxPoolPreparedStatementPerConnectionSize);
        druidDataSource.setFilters(this.filters);
        druidDataSource.setUseGlobalDataSourceStat(Boolean.valueOf(this.useGlobalDataSourceStat));
        druidDataSource.setStatLogger(getTestStatLogger());
        System.out.println(druidDataSource.getPooledConnection().toString());
        return druidDataSource;
    }
    @Bean
    public TestStatLogger getTestStatLogger(){
        return new TestStatLogger();
    }
}
