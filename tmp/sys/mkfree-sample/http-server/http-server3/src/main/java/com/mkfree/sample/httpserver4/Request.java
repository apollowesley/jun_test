package com.mkfree.sample.httpserver4;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * HTTP 请求信息
 *
 * GET /user/info HTTP/1.1 http://127.0.0.1:8080/user/info
 * Cache-Control : no-cache
 * Accept : **
 * Connection:keep-alive
 * User-Agent:Mozilla/5.0(Macintosh;Intel Mac OS X 10_13_2)AppleWebKit/537.36(KHTML,like Gecko)Chrome/68.0.3440.106Safari/537.36
 * Host:127.0.0.1
 * Postman-Token:e46ac458-4bf7-71ed-3b28-21f5448f3d25
 * Accept-Encoding:gzip,deflate,br
 * Accept-Language:zh-CN,zh;q=0.9,en;q=0.8
 * Content-Type:application/json
 *
 *
 */
public class Request {

    // 请求头 Host 名称
    private static final String HEADER_HOST_NAME = "Host";
    // 请求头 Content-Length 名称
    public static final String HEADER_CONTENT_LENGTH_NAME = "Content-Length";
    // http 请求前缀
    private static final String HTTP_PREFIX = "http://";


    /**
     * 输入流
     */
    private InputStream inputStream;
    /**
     * uri 例如：http://127.0.0.1:8080/user/info
     */
    private String uri;
    /**
     * url 例如: /user/info
     */
    private String url;

    /**
     * http 请求方法
     */
    private HttpMethod method;
    /**
     * 协议
     */
    private String protocol;

    /**
     * 请求头
     */
    private Map<String, String> headers = new HashMap<>();

    /**
     * 请求内容
     */
    private String body;

    /**
     * 创建Request
     *
     * @param inputStream SocketInputStream
     * @throws IOException
     */
    public Request(InputStream inputStream) throws IOException {
        this.inputStream = inputStream;

        // 请求行信息
        String requestLineMessage = this.requestLineMessage();
        String[] requestLines = requestLineMessage.trim().split(" ");
        this.method = HttpMethod.valueOf(requestLines[0]);
        this.url = requestLines[1];
        this.protocol = requestLines[2];

        // 请求头信息
        String requestHeadersMessage = this.requestHeadersMessage();
        String[] requestHeaders = requestHeadersMessage.trim().split("\r\n");
        Stream.of(requestHeaders).map(String::trim).forEach(headerLine -> {
            String[] tempHeader = headerLine.split(":");
            this.headers.put(tempHeader[0].trim(), tempHeader[1].trim());
        });
        // 组合成uri
        this.uri = HTTP_PREFIX + headers.get(HEADER_HOST_NAME) + ":" + HttpServer.SERVER_PORT + this.url;

        // POST PUT PATCH DELETE 需要读取 body 信息
        if (this.method == HttpMethod.POST) {
            this.body = this.requestBody();
        }
    }

    /**
     * 获取头信息
     *
     * @return
     * @throws IOException
     */
    private String requestHeadersMessage() throws IOException {
        byte[] buffer = new byte[1024];
        int input;
        int index = 0;

        // 这段代码是有点恶心了，到时候重构一下
        while ((input = this.inputStream.read()) != -1) {
            buffer[index] = (byte) input;
            index++;
            if (input == 13) {
                int n1 = this.inputStream.read();
                buffer[index] = (byte) n1;
                index++;
                if (n1 == 10) {
                    int r2 = this.inputStream.read();
                    buffer[index] = (byte) r2;
                    index++;
                    if (r2 == 13) {
                        int n2 = this.inputStream.read();
                        buffer[index] = (byte) n2;
                        index++;
                        break;
                    }
                }
            }
        }
        byte[] result = Arrays.copyOf(buffer, index);
        return new String(result);
    }

    /**
     *
     * 获取请求行信息
     *
     * @return
     * @throws IOException
     */
    private String requestLineMessage() throws IOException {
        byte[] buffer = new byte[1024];
        int input;
        int index = 0;
        while ((input = this.inputStream.read()) != -1) {
            if (input == 13 && this.inputStream.read() == 10) {
                break;
            }
            buffer[index] = (byte) input;
            index++;
        }
        byte[] result = Arrays.copyOf(buffer, index);
        return new String(result);
    }

    /**
     * 获取请求内容
     * @return
     * @throws IOException
     */
    private String requestBody() throws IOException {
        int contentLength = Integer.valueOf(this.headers.get(HEADER_CONTENT_LENGTH_NAME));
        byte[] result = new byte[contentLength];
        inputStream.read(result, 0, contentLength);
        return new String(result);
    }

    public String getUri() {
        return uri;
    }

    public String getUrl() {
        return url;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getProtocol() {
        return protocol;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }
}
