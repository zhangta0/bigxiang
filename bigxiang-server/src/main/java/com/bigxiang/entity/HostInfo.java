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
}
