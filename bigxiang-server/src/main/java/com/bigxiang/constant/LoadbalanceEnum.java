package com.bigxiang.constant;

/**
 * Created by Zhon.Thao on 2019/11/26.
 *
 * @author Zhon.Thao
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
