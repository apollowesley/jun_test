package com.mkfree.sample.httpserver4;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class HttpServer {

    private static Logger log = LoggerFactory.getLogger(HttpServer.class);

    public static final int SERVER_PORT = 8080;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080, 1, InetAddress.getByName("127.0.0.1"));
        log.info("HttpStatus server start port {} ...", SERVER_PORT);
        while (true) {
            Socket socket = serverSocket.accept();

            InputStream inputStream = socket.getInputStream();
            Request request = new Request(inputStream);

            log.info("{} {} {} {}", request.getMethod(), request.getUrl(), request.getProtocol(), request.getUri());
            request.getHeaders().forEach((s, s2) -> log.info("{} : {}", s, s2));
            if (request.getBody() != null) {
                log.info("{}", request.getBody());
            }
            // 返回数据
            String body = "{\"id\":1,\"name\":\"oyhk\"}";
            OutputStream outputStream = socket.getOutputStream();
            Response response = new Response(request, outputStream);
            response.write(body);

            log.info(response.getContent());

            inputStream.close();
            outputStream.flush();
            outputStream.close();
        }
    }

}
