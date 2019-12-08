package com.bigxiang.config;

import com.bigxiang.constant.LoadbalanceEnum;
import com.bigxiang.log.LogFactory;
import com.bigxiang.log.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Zhon.Thao on 2019/11/26.
 *
 * @author Zhon.Thao
 */
public class ConfigContainer {

    private static final Logger LOGGER = LogFactory.getLogger(ConfigContainer.class);
    private static boolean started = false;
    protected static String defaultLoadBalance = LoadbalanceEnum.PLAIN.code;
    protected static String zkAddress;
    protected static String loadBalance = defaultLoadBalance;
    protected static int serverPort = PortAutoGet.port();

    public ConfigContainer() {
        if (!started) {
            synchronized (ConfigContainer.class) {
                if (!started) {
                    start();
                    started = true;
                }
            }

        }
    }

    public void start() {
        Properties props = new Properties();
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("bigxiang.properties");
        try {
            if (null != in) {
                props.load(in);
                if (null == zkAddress) {
                    zkAddress = props.getProperty("zkAddress");
                    if (null == zkAddress) {
                        throw new IllegalStateException("zkAddress is null");
                    }
                }
                if (null == loadBalance) {
                    loadBalance = props.getProperty("loadbalance", defaultLoadBalance);
                }
                if (0 >= serverPort) {
                    serverPort = Integer.parseInt(props.getProperty("serverPort", String.valueOf(serverPort)));
                }
            }
        } catch (IOException e) {
            LOGGER.error("load bigxiang.properties error", e);
            System.exit(0);
        }
    }

    public String getZkAddress() {
        return zkAddress;
    }

    public void setZkAddress(String zkAddress) {
        ConfigContainer.zkAddress = zkAddress;
    }

    public String getLoadBalance() {
        return loadBalance;
    }

    public void setLoadBalance(String loadBalance) {
        ConfigContainer.loadBalance = loadBalance;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        ConfigContainer.serverPort = serverPort;
    }
}
