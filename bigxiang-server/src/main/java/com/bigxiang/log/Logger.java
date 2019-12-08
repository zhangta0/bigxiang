package com.bigxiang.log;


import org.apache.logging.log4j.LogManager;

/**
 * Created by Zhon.Thao on 2019/12/8.
 *
 * @author Zhon.Thao
 */
public class Logger {

    private org.apache.logging.log4j.Logger logger;

    private Logger() {
    }

    public Logger(Class clz) {
        logger = LogManager.getLogger(clz);
    }

    public void error(String msg, Exception e) {
        logger.error(msg, e);
    }

    public void warn(String msg) {
        logger.warn(msg);
    }
}
