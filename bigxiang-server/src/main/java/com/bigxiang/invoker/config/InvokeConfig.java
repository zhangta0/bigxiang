package com.bigxiang.invoker.config;

import java.io.Serializable;

/**
 * Created by Zhon.Thao on 2019/4/28.
 *
 * @author Zhon.Thao
 */
public class InvokeConfig implements Serializable {

    private Class interfaceClz;

    private String url;

    private long timeout;

    private byte serializer;

    public String getUrl() {
        return url;
    }

    public InvokeConfig setUrl(String url) {
        this.url = url;
        return this;
    }

    public long getTimeout() {
        return timeout;
    }

    public InvokeConfig setTimeout(long timeout) {
        this.timeout = timeout;
        return this;
    }

    public byte getSerializer() {
        return serializer;
    }

    public InvokeConfig setSerializer(byte serializer) {
        this.serializer = serializer;
        return this;
    }

    public Class getInterfaceClz() {
        return interfaceClz;
    }

    public InvokeConfig setInterfaceClz(Class interfaceClz) {
        this.interfaceClz = interfaceClz;
        return this;
    }
}
