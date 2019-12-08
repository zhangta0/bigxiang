package com.bigxiang.provider.config;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by Zhon.Thao on 2019/4/23.
 *
 * @author Zhon.Thao
 */
public class ProviderConfig {


    private static int DEFAULT_PORT = 4003;

    private String url;
    private int port = DEFAULT_PORT;
    private String interfaceName;
    private Object bean;
    private List<Method> methods;

    private ProviderConfig(Builder builder) {
        this.url = builder.url;
        this.port = builder.port;
        this.interfaceName = builder.interfaceName;
        this.bean = builder.bean;
        this.methods = builder.methods;
    }

    public static class Builder {
        private String url;
        private int port;
        private String interfaceName;
        private Object bean;
        private List<Method> methods;

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder port(int port) {
            if (port < 1024 || port > 65535) {
                port = DEFAULT_PORT;
            }
            this.port = port;
            return this;
        }

        public Builder InterfaceName(String interfaceName) {
            this.interfaceName = interfaceName;
            return this;
        }

        public Builder Bean(Object bean) {
            this.bean = bean;
            return this;
        }

        public Builder Methods(List<Method> methods) {
            this.methods = methods;
            return this;
        }

        public ProviderConfig build() {
            return new ProviderConfig(this);
        }
    }

    public static int getDefaultPort() {
        return DEFAULT_PORT;
    }

    public String getUrl() {
        return url;
    }

    public int getPort() {
        return port;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public Object getBean() {
        return bean;
    }

    public List<Method> getMethods() {
        return methods;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ProviderConfig{");
        sb.append("url='").append(url).append('\'');
        sb.append(", port=").append(port);
        sb.append(", interfaceName='").append(interfaceName).append('\'');
        sb.append(", bean=").append(bean);
        sb.append(", methods=").append(methods);
        sb.append('}');
        return sb.toString();
    }
}
