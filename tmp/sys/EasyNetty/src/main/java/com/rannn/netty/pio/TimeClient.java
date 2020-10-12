package com.rannn.netty.pio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.channels.FileChannel;

/**
 * Created by hztaoran on 2016/7/20.
 */
public class TimeClient {
    public static void main(String[] args) {
        int port = 8080;
        if (null != args && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                // 默认值
            }
        }
        Socket socket = null;
        BufferedReader br = null;
        PrintWriter pw = null;
        try {
            socket = new Socket("127.0.0.1", port);
            br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            pw = new PrintWriter(socket.getOutputStream(), true);
            pw.println("QUERY TIME");
            System.out.println("Send request to server succeed.");
            String resp = br.readLine();
            System.out.println("Now is : " + resp);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                br = null;
            }
            if (pw != null) {
                pw.close();
                pw = null;
            }
            if (null != socket) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                socket = null;
            }
        }
    }
}
