package com.mkfree.sample.httpserver4;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * HTTP 响应信息
 *
 * 例如：
 * HTTP/1.1 200 OK
 * Content-Type:application/json
 * Content-Length:22
 *
 * {"id":1,"name":"oyhk"}
 *
 */
public class Response {

    public static final String LINE_END = "\r\n";

    /**
     * 请求信息
     */
    private Request request;

    /**
     * 输出流
     */
    private OutputStream outputStream;

    /**
     * 响应头
     */
    private Map<String, Object> headers = new HashMap<>();

    /**
     * 响应内容
     */
    private String[] content = new String[1];

    /**
     * 创建响应信息
     * @param request
     * @param outputStream SocketOutputStream
     */
    public Response(Request request, OutputStream outputStream) {
        this.request = request;
        this.outputStream = outputStream;
        this.headers.put("Content-Type", "application/json");
    }

    public void write(String body) throws IOException {
        this.headers.put("Content-Length", body.length());

        content[0] = request.getProtocol() + " " + HttpStatus.OK.code + " " + HttpStatus.OK.desc + LINE_END;
        headers.forEach((s, o) -> content[0] += s + ":" + o + LINE_END);
        content[0] += LINE_END + body;
        this.outputStream.write(content[0].getBytes());
    }

    public String getContent() {
        return content[0];
    }
}
