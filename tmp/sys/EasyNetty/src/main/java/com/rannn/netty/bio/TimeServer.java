package com.rannn.netty.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by hztaoran on 2016/7/20.
 * 1:1的基于BIO模型的时间服务器
 */
public class TimeServer {
    public static void main(String[] args) throws IOException {
        int port = 8080;
        if (null != args && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                // 默认值
            }
        }
        ServerSocket server = null;
        try {
            server = new ServerSocket(port);
            System.out.println("The time server is start in port : " + port);
            Socket socket = null;
            while (true) {
                socket = server.accept();
                new Thread(new TimeServerHandler(socket)).start();
            }
        } finally {
            if (null != server) {
                System.out.println("The time server closed");
                server.close();
                server = null;
            }
        }
    }
}
