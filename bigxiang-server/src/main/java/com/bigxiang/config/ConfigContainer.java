package com.bigxiang.config;

import com.bigxiang.constant.LoadbalanceEnum;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by zhangtao47 on 2019/11/26.
 *
 * @author zhangtao47
 */
public class ConfigContainer {

    private static String defaultZkAddress = "localhost:2181";
    private static String defaultLoadbalance = LoadbalanceEnum.PLAIN.code;
    private static String zkAddress = defaultZkAddress;
    private static String loadbalance = defaultLoadbalance;

    static {
        init();
    }

    public static void init() {
        Properties props = new Properties();
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("bigxiang.properties");
        try {
            if (null != in) {
                props.load(in);
                zkAddress = props.getProperty("zkAddress", defaultZkAddress);
                loadbalance = props.getProperty("loadbalance", defaultLoadbalance);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getLoadbalance() {
        return loadbalance;
    }

    public static String getZkAddress() {
        return zkAddress;
    }
}
