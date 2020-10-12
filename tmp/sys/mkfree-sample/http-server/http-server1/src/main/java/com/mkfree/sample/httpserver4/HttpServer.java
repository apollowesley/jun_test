package com.mkfree.sample.httpserver4;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class HttpServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080, 1, InetAddress.getByName("127.0.0.1"));

        while (true) {
            Socket socket = serverSocket.accept();

            InputStream inputStream = socket.getInputStream();
            int input;
            StringBuilder requestMsg = new StringBuilder();
            while ((input = inputStream.read()) != -1) {
                requestMsg.append((char) input);
                // 暂时这样模拟http get 结束
                if (requestMsg.lastIndexOf("\r\n\r\n") != -1) {
                    break;
                }
            }
            System.out.println(requestMsg);


            // 返回数据
            String body = "{\"id\":1,\"name\":\"oyhk\"}";
            OutputStream outputStream = socket.getOutputStream();
            String response = "HTTP/1.1 200 OK\r\n" +
                    "Content-Type:application/json\r\n" +
                    "Content-Length:" + body.length() + "\r\n" +
                    "\r\n" + body;

            System.out.println(response);
            outputStream.write(response.getBytes());

            inputStream.close();
            outputStream.flush();
            outputStream.close();
            socket.close();

        }
    }

}
