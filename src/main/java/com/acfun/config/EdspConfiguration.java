package com.acfun.config;

import cn.sunline.edsp.microcore.GlobalContext;
import cn.sunline.edsp.microcore.plugin.impl.PluginManager;
import cn.sunline.ltts.base.logging.LogConfigManager;
import cn.sunline.ltts.core.api.util.LttsCoreBeanUtil;
import cn.sunline.ltts.core.api.util.ProfileSwitcher;
import org.springframework.context.annotation.Configuration;

import java.io.File;

/**
 * Created by jianghong on 2017/6/7.
 */
@Configuration
public class EdspConfiguration {
    static {
        try {
            loadPlatform();// 一个jvm下只加载一次
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    private static void loadPlatform() throws Throwable {
        GlobalContext.get().setStatus(
                GlobalContext.SystemStatus.started);
        System.setProperty("setting.file", "setting.dev.properties");
        System.setProperty("dmbClientFile", "dmb-client.dev.properties");
        System.setProperty("log4j.configurationFile", "ltts_log_dev.xml");

        System.setProperty("ltts.home", new File(
                System.getProperty("user.dir"), ".").getCanonicalPath());
        System.setProperty("ltts.log.home",
                new File(System.getProperty("user.dir"), ".")
                        .getCanonicalPath());
        System.setProperty("ltts.vmid", "UnitTestApp");

        LogConfigManager.get().setCurrentLogType(
                LogConfigManager.LogType.busi_onl);
        LogConfigManager.get().setCurrentSystemType(
                LogConfigManager.SystemType.onl);

        PluginManager.initPluginServices();
        PluginManager.startAllPluginServices();

        ProfileSwitcher.get().useDebugListener = true;

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    LttsCoreBeanUtil.getDBConnectionManager().close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    Thread.sleep(2 * 1000);// 停止JVM,停止2秒，让后台线程池中的任务跑完
                    // System.exit(0);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    // System.exit(0);// 如果有异常则强制停止
                }

            }
        });
    }
}
