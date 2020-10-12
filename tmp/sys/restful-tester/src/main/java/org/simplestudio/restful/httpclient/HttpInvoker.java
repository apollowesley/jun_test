package org.simplestudio.restful.httpclient;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.simplestudio.restful.context.SessionContext;
import org.simplestudio.restful.exception.RestException;
import org.simplestudio.restful.expression.ExpressionParser;
import org.simplestudio.restful.util.RestUtil;

import com.alibaba.fastjson.JSONObject;

import cucumber.api.DataTable;

public class HttpInvoker {
    public static CloseableHttpClient DEFAULT_HTTP_INVOKER = null;
    private static RequestConfig      REQUEST_CONFIG       = RequestConfig.custom()
            .setSocketTimeout(5000).setConnectTimeout(5000).build();               //设置请求和传输超时时间

    public static void initInvoker() throws Exception {
        SSLContext sslContext = new SSLContextBuilder()
                .loadTrustMaterial(null, new TrustStrategy() {
                    //信任所有
                    public boolean isTrusted(X509Certificate[] chain, String authType)
                            throws CertificateException {
                        return true;
                    }
                }).build();
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);

        //创建默认的http invoker,并禁用重定向(理论上单纯的Restful接口不应该重定向). UserAgent需要设置
        DEFAULT_HTTP_INVOKER = HttpClients.custom().setDefaultRequestConfig(REQUEST_CONFIG)
                .setSSLSocketFactory(sslsf)
                .setUserAgent(
                        "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.112 Safari/537.36")
                .disableRedirectHandling().build();
    }

    public static String invoke(String method, String url, DataTable dataTable) throws Exception {
        if (!url.trim().startsWith("http")) {
            url = RestUtil.trimQuote(ExpressionParser.parse(url).toString());
        }
        method = method.toUpperCase();

        HttpRequestBase request = null;

        CloseableHttpResponse response = null;
        String responseStr;
        try {
            switch (method) {
                case HttpPost.METHOD_NAME:
                    request = post(url, dataTable);
                    break;
                case HttpGet.METHOD_NAME:
                    request = get(url, dataTable);
                    break;
                case HttpDelete.METHOD_NAME:
                    request = delete(url, dataTable);
                    break;
                case HttpPut.METHOD_NAME:
                    request = put(url, dataTable);
                    break;

                default://默认为post
                    request = post(url, dataTable);
            }

            response = DEFAULT_HTTP_INVOKER.execute(request, SessionContext.currentContext());

            //检查请求是否正常响应
            if (HttpStatus.SC_OK != response.getStatusLine().getStatusCode()) {
                throw new RestException("HTTP请求失败，HTTP状态码=["
                        + response.getStatusLine().getStatusCode() + "]！请确认访问URL：" + url + "是否正确！");
            }

            responseStr = EntityUtils.toString(response.getEntity());
        } finally {
            if (response != null) {
                closeHttpResponse(response);
            }
            if (request != null) {
                request.releaseConnection();
            }
        }

        return responseStr;
    }

    private static HttpRequestBase post(String url, DataTable dataTable) throws Exception {
        HttpPost post = new HttpPost(formatURI(url, dataTable));
        //检验是否需要设置body
        formatRequestEntity(post, dataTable);

        return post;
    }

    private static HttpRequestBase get(String url, DataTable dataTable) throws Exception {
        HttpGet get = new HttpGet(formatURI(url, dataTable));

        return get;
    }

    private static HttpRequestBase put(String url, DataTable dataTable) throws Exception {
        HttpPut put = new HttpPut(formatURI(url, dataTable));
        //检验是否需要设置body
        formatRequestEntity(put, dataTable);

        return put;
    }

    private static HttpRequestBase delete(String url, DataTable dataTable) throws Exception {
        HttpDelete delete = new HttpDelete(formatURI(url, dataTable));

        return delete;
    }

    private static URI formatURI(String url, DataTable dataTable) throws Exception {
        URIBuilder builder = new URIBuilder(url).setCharset(Charset.forName("UTF-8"));

        if (dataTable != null) {
            for (List<String> param : dataTable.asLists(String.class)) {
                if (param.size() > 1) {
                    builder.addParameter(param.get(0),
                            RestUtil.trimQuote(ExpressionParser.parse(param.get(1)).toString()));
                }
            }
        }

        return builder.build();
    }

    private static void formatRequestEntity(HttpEntityEnclosingRequestBase request,
            DataTable dataTable) {
        if (dataTable != null) {
            List<String> list = dataTable.asList(String.class);
            if (list != null && list.size() == 1) {
                try {
                    String requestBody = list.get(0);
                    requestBody = ExpressionParser.parse(requestBody).toString();
                    //验证是否为合法的json格式
                    JSONObject.parseObject(requestBody);
                    request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
                    HttpEntity entity = new ByteArrayEntity(requestBody.getBytes("UTF-8"));
                    request.setEntity(entity);
                } catch (Exception e) {//忽略

                }
            }
        }
    }

    public static void release() {
        try {
            if (DEFAULT_HTTP_INVOKER != null) {
                DEFAULT_HTTP_INVOKER.close();
            }
        } catch (IOException e) {//ignore

        }
    }

    public static void closeHttpResponse(CloseableHttpResponse resp) {
        if (resp != null) {
            try {
                resp.close();
            } catch (IOException e) {//ignore

            }
        }
    }

}
