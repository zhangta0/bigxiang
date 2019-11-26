package com.bigxiang.constant;

/**
 * Created by zhangtao47 on 2019/11/26.
 *
 * @author zhangtao47
 */
public enum LoadbalanceEnum {

    HASH("hash"),

    PLAIN("plain"),

    RANDOM("random");

    public String code;

    LoadbalanceEnum(String code) {
        this.code = code;
    }
}
