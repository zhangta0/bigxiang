package com.bigxiang.invoker.entity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Created by Zhon.Thao on 2019/4/28.
 *
 * @author Zhon.Thao
 */
public class InvokeRequest implements Serializable {

    private String url;

    private String interfaceName;

    private String methodName;

    private String[] args;

    private List<Object> values;

    private long timeout;

    private transient byte serializer;

    private transient Class<?> returnType;

    public String getUrl() {
        return url;
    }

    public InvokeRequest setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public InvokeRequest setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
        return this;
    }

    public List<Object> getValues() {
        return values;
    }

    public InvokeRequest setValues(List<Object> values) {
        this.values = values;
        return this;
    }

    public long getTimeout() {
        return timeout;
    }

    public InvokeRequest setTimeout(long timeout) {
        this.timeout = timeout;
        return this;
    }

    public byte getSerializer() {
        return serializer;
    }

    public InvokeRequest setSerializer(byte serializer) {
        this.serializer = serializer;
        return this;
    }

    public String[] getArgs() {
        return args;
    }

    public InvokeRequest setArgs(String[] args) {
        this.args = args;
        return this;
    }

    public String getMethodName() {
        return methodName;
    }

    public InvokeRequest setMethodName(String methodName) {
        this.methodName = methodName;
        return this;
    }

    public Class<?> getReturnType() {
        return returnType;
    }

    public InvokeRequest setReturnType(Class<?> returnType) {
        this.returnType = returnType;
        return this;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("InvokeRequest{");
        sb.append(", url='").append(url).append('\'');
        sb.append(", interfaceName='").append(interfaceName).append('\'');
        sb.append(", methodName='").append(methodName).append('\'');
        sb.append(", args=").append(args == null ? "null" : Arrays.asList(args).toString());
        sb.append(", values=").append(values);
        sb.append(", timeout=").append(timeout);
        sb.append(", serializer=").append(serializer);
        sb.append('}');
        return sb.toString();
    }
}
