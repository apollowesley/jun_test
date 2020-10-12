package com.luoqy.speedy.config.pay;
import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {

	//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	    public static String app_id = "2018082261130155";

	    // 商户私钥，您的PKCS8格式RSA2私钥
	    public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCf8nQ4LzHUmBOaoB4QHrswj+ePZvFBwM6XD5Ex0ewpRxf86GyHQLtv98skJdqeoIfWrMnLtPsjkzZPL6UoX1obCo/v/bkU5CWSG78r+1YESeZGDOiflxMVGesCG09ni5QolyF24JS85MLaTPqEit4b/5kBZcC16SP8afREgfWZSgG4/A3GJ2DNn8IAXsA23BxNFnkDDZYULWgar34A8cRY3cHfrdiMFb9i4fjcHJWsfw7BLT3xVHvoYufeTyEjl/Lyn/X8k37Gh85Bq1edgEqz1YY/zhH8w+gerCmTyBpawd5+RxQVQbQTa2SGwDylJWyCHCFh9USXrsP+HJCYLPbbAgMBAAECggEAA7+q+aYhhNoLm5QlIDuYJ4s34iSHgwMB1IiI3qIM5urDpSnNYV4jBnzIRhCdDxq0FFP4Zs3XFP5eR3zWYhvaAMg+l2Fa9SJjvwjnciBx46fXvcyPpy3jRJtW5rW9JrAeyNTB9AdPdR3nczZaqMCYdA+Jhn7cBWdxcHkLQI/LMcyAsgrZW/KhkP4DBymnnmG/OjWN2eTqSo5GOlwH1WaQxh3OVOBwW3DDSnVSsyLQShByRVu4Ues2tomEuYJhOrqbdpEJ+Qz6qL6C3tQumzwFfNQysQWzMiLWRaL6U1i6iI4b50F5l38uMCXUYIcdEK3rFFevs0cRAYcjJZmz+i6AAQKBgQDimIVLdUfUlvsd7yeFKu44HdihJAXqFP52kUtaQla4mRRWSsKtxJ0WDJaBjfzkOAWWOx8sl8JZy+YSPieAOigdg2+UMYOFdJMD8dhM040cgSQ0PSjFe2XC1TDv2ddGqp9iu2pRe4oCqAzE0KY22Rm9IkFmv3bSav3SsQ8hRB1TIQKBgQC0s9+GhDVLnAv4icTEX7OfhnHVS+b5axbQGQPGLDSu+AYDInT2IfOKIjFF5TR0pFoU47kfE6YHJx2fyaMsD3bNPRr1cedbCG2Fa/t4OC26DmlmeT9DMwdpNUXmAW6DeVxoM0yecEFpiAiJUYUXKjcO/2S4zrMQvgAp2D6BWPtGewKBgAlZYaaLYDtVP2csxuSnsMx/7sA3TEcsNpONyx4ePpW/OyeUThP9WL3gJxWRVi3UYeag6dhdKpep4gQ+rEKOI7mUAraKlHKdLLOs9s+0qs3SRjwhid0cCkbPpQVAZ7FyuMDKGsjh3//cDyrPsQvySS2UjD5BCBYGdBKNW6JzvQIBAoGAXylOsJdVPcEq6LMcxVGk1YDG184GEBmeRO63dxh2kiIZgHMX5zrP91G0YxumeTrIGbInHj+JssCdsDVO0fg+1yyz0sguT1lr29SW9KOZKR9H0GhPsYLhBz2/1rWwTlqOGG3TjqiNOhcib7ZXgXCA2EwU7eIkmM+GZIL1v8ZtZZsCgYEAgyNS4EbQ9nXZDKnpeV/pGV0NKkVJPclGqo6WMpg6HKzZOhh2N8oJeOjHY2+9rktLYLJvHcbUl21o+iZwfcMq30eJQH+HTiCX4JGto3HLtfgQxeH4RUL3c3ay7htCamMk/pXj43BcIwfnvSgI08XRsOSVMKr9bnxFuEMo4ZcPyG8=";

	    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
	    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArr84CCS89jmJR2/qOVoDcLhdrqAoftMMrWo7hmqxxLBkCi5ATAMaqCRUQ9aWParLu+SqEskbsbF4xQtdjkOjGwP1Kmh6rZGvk8gZ7LtWMZiC0Np9I12P/EqWwQZCq/ULQZyIclr4wM8QTfITQ37IpJ5+qbLCtw+b8EkH/JXnFJtttTjaXJA5piiHpmcWbsKBGlKP9v7OABtf8/sm9ypnspiF9tmJGDAPUPNk6FN0Tuhl7QILUwRP4h7tGxvwwYrBrzpCg/gPPdbPhXIeDN2UH0u/opwbAbmTZqQVbHANv/HNbDeZ/U0fQtuH5fqVL88FSFqZ6r1ze7cKUCjZPgWMMwIDAQAB";

	    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	    public static String notify_url = "http://52.83.160.112/api/pay/verify";

	    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	    public static String return_url = "http://工程公网访问地址/alipay.trade.page.pay-JAVA-UTF-8/return_url.jsp";

	    // 签名方式
	    public static String sign_type = "RSA2";

	    // 字符编码格式
	    public static String charset = "utf-8";

	    // 支付宝网关
	    public static String gatewayUrl = "https://openapi.alipay.com/gateway.do";

	    // 支付宝网关
	    public static String log_path = "F:\\";


	//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	    /**
	     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
	     * @param sWord 要写入日志里的文本内容
	     */
	    public static void logResult(String sWord) {
	        FileWriter writer = null;
	        try {
	            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
	            writer.write(sWord);
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            if (writer != null) {
	                try {
	                    writer.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	    }
}

