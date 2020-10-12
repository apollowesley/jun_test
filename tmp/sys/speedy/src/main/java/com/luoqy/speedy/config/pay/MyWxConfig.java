package com.luoqy.speedy.config.pay;
import com.github.wxpay.sdk.WXPayConfig;
import java.io.*;

public class MyWxConfig implements WXPayConfig{
	//异步回调地址
	public static String NOTIFY_URL="";
	//币种 CNY 人民币
	public static String FEE_TYPE="CNY";
	//扫码
	public static String NATIVE="NATIVE";
	//公众号
	public static String JSAPI="JSAPI";
    //APP支付
	public static String APP="APP";
	private byte[] certData;

    public MyWxConfig() throws Exception {
        String certPath ="/path/to/apiclient_cert.p12";
        File file = new File(certPath);
        if(file.exists()){
        	InputStream certStream = new FileInputStream(file);
            this.certData = new byte[(int) file.length()];
            certStream.read(this.certData);
            certStream.close();
        }
    }

    public String getAppID() {
        return "wx8c415d10caef70a3";
    }

    public String getMchID() {
        return "1399334302";
    }

    public String getKey() {
        return "A5E926C0E9010778EE0D7B579EC41C15";
    }

    public InputStream getCertStream() {
        ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }

    public int getHttpConnectTimeoutMs() {
        return 8000;
    }

    public int getHttpReadTimeoutMs() {
        return 10000;
    }
}
