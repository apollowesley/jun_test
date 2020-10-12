package com.mkfree.sample.httpserver4;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.*;


public class Client {

    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1000, 1000, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1000));

    public static void main(String[] args) throws IOException {
        test();
        test();
        Executors.newFixedThreadPool(100);

    }

    public static void test() {
        new Thread(() -> {
            for (int i = 0; i < 10000; i++) {

                Socket socket = null;
                try {
                    socket = new Socket("127.0.0.1", 8080);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Socket finalSocket = socket;
                threadPoolExecutor.execute(() -> {
                    OutputStream outputStream = null;
                    InputStream inputStream = null;
                    try {
                        if (finalSocket == null) {
                            return;
                        }
                        outputStream = finalSocket.getOutputStream();
                        String content = "GET /user/info HTTP/1.1\r\n" +
                                "Cache-Control : no-cache\r\n" +
                                "Accept : */*\r\n" +
                                "Connection : keep-alive\r\n" +
                                "User-Agent : Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36\r\n" +
                                "Host : 127.0.0.1\r\n" +
                                "Postman-Token : 4c7a490a-301d-9af5-23a0-121240550814\r\n" +
                                "Accept-Encoding : gzip, deflate, br\r\n" +
                                "Accept-Language : zh-CN,zh;q=0.9,en;q=0.8\r\n\r\n";
                        outputStream.write(content.getBytes());

                        inputStream = finalSocket.getInputStream();
//                        System.out.println(new String(inputStream.readAllBytes()));

                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (outputStream != null) {
                            try {
                                outputStream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        try {
                            if (finalSocket != null) {
                                finalSocket.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }).start();
    }

    public static void aa() {
        new Thread(() -> {
            while (true) {
                long taskCount = threadPoolExecutor.getTaskCount();
                System.out.println("taskCount : " + taskCount);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (taskCount == 10000L) {
                    threadPoolExecutor.shutdown();
                }
            }
        }).start();
    }
}
