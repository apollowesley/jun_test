package com.rannn.netty.pio;

import com.rannn.netty.bio.TimeServerHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by hztaoran on 2016/7/20.
 * M:N的伪异步IO的时间服务器
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
            TimeServerHandlerExecutePool executePool = new TimeServerHandlerExecutePool(50, 10000);
            while (true) {
                socket = server.accept();
                executePool.execute(new TimeServerHandler(socket));
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
