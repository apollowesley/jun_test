package com.mkfree.nio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException, InterruptedException {
        Socket socket = new Socket("127.0.0.1", 4700);
        System.out.println("connection success");


//        new Thread(() -> {
//            OutputStream outputStream = null;
//            try {
//                while (true) {
//                    outputStream = socket.getOutputStream();
//                    outputStream.write("fuck".getBytes());
////                outputStream.close();
////                outputStream.flush();
//                    Thread.sleep(1000L);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }).start();
//
//        new Thread(() -> {
//            InputStream inputStream = null;
//            try {
//                while (true) {
//                    inputStream = socket.getInputStream();
//                    System.out.println(new String(inputStream.readAllBytes()));
////                outputStream.close();
////                outputStream.flush();
//                    Thread.sleep(1000L);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }).start();
//
//        Thread.sleep(1000000L);
    }
}
