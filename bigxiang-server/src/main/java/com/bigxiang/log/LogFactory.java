package com.bigxiang.log;

/**
 * Created by Zhon.Thao on 2019/12/8.
 *
 * @author Zhon.Thao
 */
public class LogFactory {

    public static Logger getLogger(Class clz) {
        return new Logger(clz);
    }
}
