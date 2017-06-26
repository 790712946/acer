package com.acfun.config;

import com.alibaba.druid.pool.DruidDataSourceStatLogger;
import com.alibaba.druid.pool.DruidDataSourceStatLoggerAdapter;
import com.alibaba.druid.pool.DruidDataSourceStatValue;
import com.alibaba.druid.support.logging.Log;

import java.util.Properties;

/**
 * Created by jianghong on 2017/5/19.
 */
public class TestStatLogger extends DruidDataSourceStatLoggerAdapter implements DruidDataSourceStatLogger {
    @Override
    public void log(DruidDataSourceStatValue statValue) {
        //TODO
    }

    @Override
    public void configFromProperties(Properties properties) {
        super.configFromProperties(properties);
    }

    @Override
    public void setLogger(Log logger) {

    }

    @Override
    public void setLoggerName(String loggerName) {

    }
}
