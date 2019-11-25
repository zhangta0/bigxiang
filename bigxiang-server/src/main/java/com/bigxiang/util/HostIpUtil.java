package com.bigxiang.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by Zhon.Thao on 2019/11/18.
 *
 * @author Zhon.Thao
 */
public class HostIpUtil {

    private static String local_ip = "";

    public static String getIpAddress() throws Exception {
        if (!local_ip.isEmpty()) {
            return local_ip;
        }
        synchronized (HostIpUtil.class) {
            if (local_ip.isEmpty()) {
                Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
                List<String> addresses = new ArrayList<>();
                while (networkInterfaces.hasMoreElements()) {
                    NetworkInterface networkInterface = networkInterfaces.nextElement();
                    Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                    while (inetAddresses.hasMoreElements()) {
                        InetAddress inetAddress = inetAddresses.nextElement();
                        if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress()) {
                            addresses.add(inetAddress.getHostAddress());
                        }
                    }
                }

                if (addresses.isEmpty()) {
                    throw new IllegalStateException("get local ip error");
                }
                local_ip = addresses.get(addresses.size() - 1);
            }
        }
        return local_ip;
    }
}
