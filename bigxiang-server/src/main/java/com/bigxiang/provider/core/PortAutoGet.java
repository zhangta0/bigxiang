package com.bigxiang.provider.core;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by Zhon.Thao on 2019/4/24.
 *
 * @author Zhon.Thao
 */
public class PortAutoGet {

    public static int port = 4003;

    static {
        int initPort = 1024;

        while (true) {
            try {
                new ServerSocket(port);
                break;
            } catch (IOException e) {
                port = initPort++;
            }
        }
    }
}
