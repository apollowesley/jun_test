package com.mkfree.sample.httpserver4;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.UUID;
import java.util.concurrent.*;


public class HttpServer {

    public static Logger log = LoggerFactory.getLogger(HttpServer.class);

    public static final int SERVER_PORT = 8080;


    // 这里使用 SynchronousQueue ，接收到请求马上处理，当如果以下 10个线程都在使用，继续有请求进入，那么会开启新的线程执行，当maximumPoolSize到达50个时，那么会拒绝任务提交
    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 200, 0, TimeUnit.MILLISECONDS, new SynchronousQueue<>());

    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket(8080, 1000);
        log.info("HttpStatus server start port {} ...", SERVER_PORT);

        new Thread(() -> {
            int index = 0;
            while (true) {
                int activeCount = threadPoolExecutor.getActiveCount();
                log.info("getActiveCount : {}", activeCount);
                String uuid = UUID.randomUUID().toString();
                if (activeCount < 200) {
                    Socket socket = null;
                    try {
                        socket = serverSocket.accept();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    index++;
                    threadPoolExecutor.execute(new Processor(socket, uuid, index));
                } else {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();

        new Thread(() -> {
            int index = 0;
            while (true) {
                int activeCount = threadPoolExecutor.getActiveCount();
                log.info("getActiveCount : {}", activeCount);
                String uuid = UUID.randomUUID().toString();
                if (activeCount < 200) {
                    Socket socket = null;
                    try {
                        socket = serverSocket.accept();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    index++;
                    threadPoolExecutor.execute(new Processor(socket, uuid, index));
                } else {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();

        new Thread(() -> {
            int index = 0;
            while (true) {
                int activeCount = threadPoolExecutor.getActiveCount();
                log.info("getActiveCount : {}", activeCount);
                String uuid = UUID.randomUUID().toString();
                if (activeCount < 200) {
                    Socket socket = null;
                    try {
                        socket = serverSocket.accept();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    index++;
                    threadPoolExecutor.execute(new Processor(socket, uuid, index));
                } else {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();


    }

}
