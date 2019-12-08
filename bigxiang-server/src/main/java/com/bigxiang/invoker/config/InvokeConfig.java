package com.bigxiang.invoker.config;

import com.bigxiang.constant.InvokeType;

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

    private InvokeType typeEnum;

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

    public InvokeType getTypeEnum() {
        return typeEnum;
    }

    public InvokeConfig setTypeEnum(InvokeType typeEnum) {
        this.typeEnum = typeEnum;
        return this;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("InvokeConfig{");
        sb.append("interfaceClz=").append(interfaceClz);
        sb.append(", url='").append(url).append('\'');
        sb.append(", timeout=").append(timeout);
        sb.append(", typeEnum=").append(typeEnum);
        sb.append(", serializer=").append(serializer);
        sb.append('}');
        return sb.toString();
    }
}
