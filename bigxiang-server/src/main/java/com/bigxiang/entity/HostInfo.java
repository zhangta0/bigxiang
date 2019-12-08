package com.bigxiang.entity;

/**
 * Created by Zhon.Thao on 2019/11/18.
 *
 * @author Zhon.Thao
 */
public class HostInfo {

    private String ip;

    private int port;

    public String getIp() {
        return ip;
    }

    public HostInfo setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public int getPort() {
        return port;
    }

    public HostInfo setPort(int port) {
        this.port = port;
        return this;
    }

    public HostInfo(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public HostInfo(String host) {
        String[] s = host.split(":");
        this.ip = s[0];
        this.port = Integer.parseInt(s[1]);
    }

    @Override
    public boolean equals(Object obj) {
        HostInfo o = (HostInfo) obj;
        return ip.equals(o.getIp()) && port == o.getPort();
    }

    @Override
    public int hashCode() {
        return String.format("%s:%s", ip, port).hashCode();
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("HostInfo{");
        sb.append("ip='").append(ip).append('\'');
        sb.append(", port=").append(port);
        sb.append('}');
        return sb.toString();
    }
}
