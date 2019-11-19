package com.bigxiang.provider.entity;

import java.io.Serializable;

/**
 * Created by Zhon.Thao on 2019/4/30.
 *
 * @author Zhon.Thao
 */
public class ProviderResponse implements Serializable {

    private byte serializeType;

    private Object result;

    public Object getResult() {
        return result;
    }

    public ProviderResponse setResult(Object result) {
        this.result = result;
        return this;
    }

    public byte getSerializeType() {
        return serializeType;
    }

    public ProviderResponse setSerializeType(byte serializeType) {
        this.serializeType = serializeType;
        return this;
    }
}
