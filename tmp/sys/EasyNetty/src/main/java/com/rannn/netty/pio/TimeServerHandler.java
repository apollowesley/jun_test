package com.rannn.netty.pio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

/**
 * Created by hztaoran on 2016/7/20.
 */
public class TimeServerHandler implements Runnable {

    private Socket socket;

    public TimeServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader br = null;
        PrintWriter pw = null;

        try {
            br = new BufferedReader(new InputStreamReader(this.socket.getInputStream(), "UTF-8"));
            pw = new PrintWriter(this.socket.getOutputStream(), true);
            String currentTime = null;
            String body = null;
            while (true) {
                body = br.readLine();
                if (null == body) {
                    break;
                }
                System.out.println("The time server receive order : " + body);
                currentTime = "QUERY TIME".equalsIgnoreCase(body) ? new Date(System.currentTimeMillis()).toString() : "BAD ORDER";
                pw.println(currentTime);
            }
        } catch (IOException e) {
            if (null != br) {
                try {
                    br.close();
                    br = null;
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (null != pw) {
                pw.close();
                pw = null;
            }
            if (null != this.socket) {
                try {
                    this.socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                this.socket = null;
            }
            e.printStackTrace();
        }
    }

}
