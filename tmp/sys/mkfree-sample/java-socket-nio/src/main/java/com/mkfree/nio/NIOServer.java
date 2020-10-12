package com.mkfree.nio;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {

    public static int id = 1;

    public static void main(String[] args) throws InterruptedException {

//        new Thread(() -> {
//            try {
//                // 创建通道,并设置非阻塞
//                ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
//                serverSocketChannel.configureBlocking(false);
//                // 创建选择器，并为通道绑定感兴趣的事件
//                Selector selector = Selector.open();
//                serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT).attach("主监听通道");
//                System.out.println("监听的channel Id：" + serverSocketChannel.hashCode());
//                // 通道绑定端口号
//                InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 4700);
//                serverSocketChannel.socket().bind(inetSocketAddress);
//
//                while (true) {
//                    // 阻塞 直到有事件就绪为止
//                    int readySelect = selector.selectNow();
//                    if (readySelect == 0) {
//                        continue;
//                    }
//
//                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
//
//                    System.out.println("selectionKeys.size() " + selectionKeys.size());
//
//                    Iterator<SelectionKey> selectionKeyIterator = selectionKeys.iterator();
//
//
//                    while (selectionKeyIterator.hasNext()) {
//                        SelectionKey selectionKey = selectionKeyIterator.next();
//                        // 当事件处理完成后，必须删除，否则继续循环下去
//                        selectionKeyIterator.remove();
//                        System.out.println("selectionKeyIterator.remove()");
//
//                        if (selectionKey.isAcceptable()) {
//                            ServerSocketChannel readyChannel = (ServerSocketChannel) selectionKey.channel();
//                            System.out.println("可接受连接的channel Id: " + readyChannel.hashCode() +" "+ selectionKey.attachment());
//                            SocketChannel socketChannel1 = (SocketChannel) readyChannel.accept().configureBlocking(false);
//                            System.out.println("接受连接后返回的channel Id: " + socketChannel1.hashCode());
//                            socketChannel1.register(selector, (SelectionKey.OP_READ | SelectionKey.OP_WRITE)).attach(id++);
//                        }
//                        if (selectionKey.isReadable()) {
//                            System.out.println("isReadable");
//                        }
//                        if (selectionKey.isWritable()) {
//                            System.out.println("isWritable");
//                        }
//
//
//                    }
//                    Thread.sleep(500L);
//                }
//
//
//            } catch (Exception e) {
//
//            }
//        }).start();
//
//
//        Thread.sleep(1000000000L);
    }

}
