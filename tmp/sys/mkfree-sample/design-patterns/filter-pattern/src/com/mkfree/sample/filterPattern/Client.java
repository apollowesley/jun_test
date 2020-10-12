package com.mkfree.sample.filterPattern;

/**
 * 调用者
 */
public class Client {

    public static void main(String[] args) {
        Servlet servlet = new Servlet();
        Request request = new Request();
        request.setUrl("http://blog.mkfree.com/post?id=1");
        Response response = new Response();
        servlet.service(request, response);
    }

}
