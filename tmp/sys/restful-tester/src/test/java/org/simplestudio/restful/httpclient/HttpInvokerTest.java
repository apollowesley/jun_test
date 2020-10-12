package org.simplestudio.restful.httpclient;

import org.apache.http.client.methods.HttpGet;
import org.junit.Test;

public class HttpInvokerTest {

    @Test
    public void test() throws Exception {
        HttpInvoker.initInvoker();

        try {
            System.out.println(HttpInvoker.invoke(HttpGet.METHOD_NAME, "${查询存在的快递}", null));
            System.out.println(HttpInvoker.invoke(HttpGet.METHOD_NAME, "${查询不存在的快递}", null));
        } finally {
            HttpInvoker.release();
        }
    }

}
