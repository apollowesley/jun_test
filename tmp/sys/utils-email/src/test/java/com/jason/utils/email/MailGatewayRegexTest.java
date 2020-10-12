package com.jason.utils.email;

public class MailGatewayRegexTest {
	public static void main(String[] args) {
	   Gateway gateway=Gateway.mailSuffixAdaptation("380146330@qq.com");
       System.out.println(gateway.resolveAccount("380146330@qq.com"));
	}
}