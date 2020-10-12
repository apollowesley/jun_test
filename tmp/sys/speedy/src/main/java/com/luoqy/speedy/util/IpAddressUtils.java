package com.luoqy.speedy.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;
/**
 * 获取内外网ip地址
 * 
 * @author lumberer
 * @version 2017/5/10
 */
public class IpAddressUtils {
	public static final String INTRANET_IP = getIntranetIp(); // 内网IP
	public static final String INTERNET_IP = getInternetIp(); // 外网IP，无用
	public static final String DETAIL_DESC = getV4IP(); // 外网信息
	private IpAddressUtils() {
	}

	public static void main(String[] args){
//		InetAddress addr = InetAddress.getLocalHost();
//		String ip = addr.getHostAddress();// 获得本机IP
//		String address = addr.getHostName();// 获得本机名称
//		System.out.println(ip);
//		System.out.println(address);
		System.err.println(getV4IP());
	}

	/**
	 * 通过外部网络查询真是IP地址
	 * @return
	 */
	private static String getV4IP() {
		String ip = "";
		//http://ip.chinaz.com/
		String chinaz = "https://202020.ip138.com/";
		String inputLine = "";
		String read = "";
		try {
			URL url = new URL(chinaz);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			 //设置post方法
//			urlConnection.setRequestMethod("GET");
//            //不使用缓存
//			urlConnection.setUseCaches(false);
//            //这一句很重要，设置不要302自动跳转，后面会讲解到
//			urlConnection.setInstanceFollowRedirects(false);
//			urlConnection.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64; rv:46.0) Gecko/20100101 Firefox/46.0");

			 // 发送请求参数
		    // flush输出流的缓冲
			BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"UTF-8"));
			while ((read = in.readLine()) != null) {
				inputLine += read+"";
			}
			String regex="<p.*?>(.*?)</p>";
			Pattern p =Pattern.compile(regex);
			Matcher m=p.matcher(inputLine);
			while(m.find()){
				ip=m.group(1);
				break;
			}
			return ip;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获得内网IP
	 * 
	 * @return 内网IP
	 */
	private static String getIntranetIp() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获得外网IP
	 * 
	 * @return 外网IP
	 */
	private static String getInternetIp() {
		try {
			Enumeration<NetworkInterface> networks = NetworkInterface.getNetworkInterfaces();
			InetAddress ip = null;
			Enumeration<InetAddress> addrs;
			while (networks.hasMoreElements()) {
				addrs = networks.nextElement().getInetAddresses();
				while (addrs.hasMoreElements()) {
					ip = addrs.nextElement();
					if (ip != null && ip instanceof Inet4Address && ip.isSiteLocalAddress()
							&& !ip.getHostAddress().equals(INTRANET_IP)) {
						return ip.getHostAddress();
					}
				}
			}

			// 如果没有外网IP，就返回内网IP
			return INTRANET_IP;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}