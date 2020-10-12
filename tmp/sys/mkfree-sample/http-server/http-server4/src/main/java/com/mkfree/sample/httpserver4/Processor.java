package com.mkfree.sample.httpserver4;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 处理器
 */
public class Processor implements Runnable {

    private Socket socket;
    private int index;
    private String uuid;

    public Processor(Socket socket, String uuid, int index) {
        this.socket = socket;
        this.index = index;
        this.uuid = uuid;
    }

    @Override
    public void run() {

        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            inputStream = socket.getInputStream();
            Request request = new Request(inputStream);

            HttpServer.log.info("{} , {} {} {} {}", uuid, request.getMethod(), request.getUrl(), request.getProtocol(), request.getUri());
            request.getHeaders().forEach((s, s2) -> HttpServer.log.info("{} , {} : {}", uuid, s, s2));
            if (request.getBody() != null) {
                HttpServer.log.info("{} , {}", uuid, request.getBody());
            }
            // 返回数据
            String body = "{\"id\":1,\"name\":\"oyhk\"}";
            outputStream = socket.getOutputStream();
            Response response = new Response(request, outputStream);
            response.write(body);

            HttpServer.log.info("{} , {}", uuid, response.getContent());
            HttpServer.log.info("{} , Processor request no : {}", uuid, index);
        } catch (FuckException e) {
            HttpServer.log.error("{} , {} , request no : {}", uuid, "inputStream.read() == -1", index);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                }
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
        }
    }
}