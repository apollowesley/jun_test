package cn.afterturn.http.client;

import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;

import javax.annotation.PostConstruct;
import javax.net.ssl.SSLException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;
import java.nio.charset.CodingErrorAction;

/**
 * 连接池参数
 *
 * @author by jueyue on 18-8-2.
 */
public class ClientBuild {

    private int maxTotal = 200;
    private int defaultMaxPerRoute = 20;
    private int soTimeout = 90 * 1000;
    private int connectTimeout = 30 * 1000;

    private HttpClient httpclient;

    public CloseableHttpClient getClient() {
        return (CloseableHttpClient) httpclient;
    }

    public String getBody(CloseableHttpResponse response) throws IOException {
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            InputStream inputStream = entity.getContent();
            try {
                byte[] bytes = new byte[0];
                bytes = new byte[inputStream.available()];
                inputStream.read(bytes);
                return new String(bytes);
            } catch (IOException ex) {
                throw ex;
            } finally {
                inputStream.close();
            }
        }
        return null;
    }

    @PostConstruct
    public void init() {
        /**
         * 创建连接管理器，并设置相关参数
         */
        //连接管理器，使用无惨构造
        PoolingHttpClientConnectionManager connManager
                = new PoolingHttpClientConnectionManager();

        /**
         * 连接数相关设置
         */
        //最大连接数
        connManager.setMaxTotal(maxTotal);
        //默认的每个路由的最大连接数
        connManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
        /**
         * socket配置（默认配置 和 某个host的配置）
         */
        SocketConfig socketConfig = SocketConfig.custom()
                //是否立即发送数据，设置为true会关闭Socket缓冲，默认为false
                .setTcpNoDelay(true)
                //是否可以在一个进程关闭Socket后，即使它还没有释放端口，其它进程还可以立即重用端口
                .setSoReuseAddress(true)
                //接收数据的等待超时时间，单位ms
                .setSoTimeout(soTimeout)
                //关闭Socket时，要么发送完所有数据，要么等待60s后，就关闭连接，此时socket.close()是阻塞的
                .setSoLinger(60)
                //开启监视TCP连接是否有效
                .setSoKeepAlive(true)
                .build();
        connManager.setDefaultSocketConfig(socketConfig);
        //Http connection相关配置
        ConnectionConfig connectionConfig = ConnectionConfig.custom()
                .setMalformedInputAction(CodingErrorAction.IGNORE)
                .setUnmappableInputAction(CodingErrorAction.IGNORE)
                .setCharset(Consts.UTF_8)
                .build();

        /**
         *request请求相关配置
         */
        RequestConfig defaultRequestConfig = RequestConfig.custom()
                .setConnectTimeout(connectTimeout)
                .setSocketTimeout(connectTimeout)
                .setConnectionRequestTimeout(connectTimeout)
                .build();

        /**
         * 重试处理
         * 默认是重试3次
         */
        //禁用重试(参数：retryCount、requestSentRetryEnabled)
        HttpRequestRetryHandler requestRetryHandler = new DefaultHttpRequestRetryHandler(0, false);
        //自定义重试策略
        HttpRequestRetryHandler myRetryHandler = new HttpRequestRetryHandler() {

            @Override
            public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
                //Do not retry if over max retry count
                if (executionCount >= 3) {
                    return false;
                }
                //Timeout
                if (exception instanceof InterruptedIOException) {
                    return false;
                }
                //Unknown host
                if (exception instanceof UnknownHostException) {
                    return false;
                }
                //Connection refused
                if (exception instanceof ConnectTimeoutException) {
                    return false;
                }
                //SSL handshake exception
                if (exception instanceof SSLException) {
                    return false;
                }

                HttpClientContext clientContext = HttpClientContext.adapt(context);
                HttpRequest request = clientContext.getRequest();
                boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
                //Retry if the request is considered idempotent
                //如果请求类型不是HttpEntityEnclosingRequest，被认为是幂等的，那么就重试
                //HttpEntityEnclosingRequest指的是有请求体的request，比HttpRequest多一个Entity属性
                //而常用的GET请求是没有请求体的，POST、PUT都是有请求体的
                //Rest一般用GET请求获取数据，故幂等，POST用于新增数据，故不幂等
                if (idempotent) {
                    return true;
                }

                return false;
            }

        };

        httpclient = HttpClients.custom()
                .setConnectionManager(connManager)
                .setDefaultRequestConfig(defaultRequestConfig)
                .setRetryHandler(myRetryHandler)
                .build();

    }
}
