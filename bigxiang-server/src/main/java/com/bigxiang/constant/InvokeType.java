package com.bigxiang.constant;

/**
 * Created by Zhon.Thao on 2019/11/27.
 *
 * @author Zhon.Thao
 */
public enum InvokeType {

    SYNC((byte) 1),

    ASYNC((byte) 2);

    public byte code;

    InvokeType(byte code) {
        this.code = code;
    }
}
