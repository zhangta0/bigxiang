package com.bigxiang.config;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by Zhon.Thao on 2019/4/24.
 *
 * @author Zhon.Thao
 */
public class PortAutoGet {

    private static Integer default_port = 4428;
    private static Integer port = null;

    public static int port() {
        int initPort = 3012;
        ServerSocket serverSocket = null;
        if (null != port) {
            return port;
        }
        synchronized (PortAutoGet.class) {
            if (null == port) {
                port = default_port;
                while (true) {
                    try {
                        serverSocket = new ServerSocket(port);
                        break;
                    } catch (IOException e) {
                        port = initPort++;
                    } finally {
                        if (null != serverSocket) {
                            try {
                                serverSocket.close();
                            } catch (IOException e) {
                            }
                        }
                    }
                }
            }
            return port;
        }
    }
}
