package com.bigxiang.constant;

/**
 * Created by Zhon.Thao on 2019/4/24.
 *
 * @author Zhon.Thao
 */
public enum SerializerEnum {

    JSON((byte) 1),
    HESSIAN((byte) 2);

    final public byte code;
    SerializerEnum(byte code) {
        this.code = code;
    }
}
