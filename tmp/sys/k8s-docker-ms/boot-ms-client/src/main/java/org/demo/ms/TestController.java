package org.demo.ms;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.Random;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liangcai_zhu
 * @Description TODO
 * @Date 2020/5/8 17:36
 */
@RestController
public class TestController {

  Random random = new Random();
  @RequestMapping("/hello")
  public String hello(){

    return "随机数:"+random.nextInt(1000000) + "本地ip:"+getHostIp();
  }

  private static String getHostIp(){
    try{
      Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
      while (allNetInterfaces.hasMoreElements()){
        NetworkInterface netInterface =  allNetInterfaces.nextElement();
        Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
        while (addresses.hasMoreElements()){
          InetAddress ip = addresses.nextElement();
          if (ip != null
              && ip instanceof Inet4Address
              && !ip.isLoopbackAddress() //loopback地址即本机地址，IPv4的loopback范围是127.0.0.0 ~ 127.255.255.255
              && ip.getHostAddress().indexOf(":")==-1){
            return ip.getHostAddress();
          }
        }
      }
    }catch(Exception e){
      e.printStackTrace();
    }
    return null;
  }

  public static void main(String[] args) {
    System.out.println(getHostIp());
  }
}
