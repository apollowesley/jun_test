package me.chanjar.dmpbug;

import org.junit.Test;

public class TomcatIT {

  @Test
  public void test() {
    String tomcatPort = System.getProperty("tomcat.port");
    System.out.println("=================================");
    System.out.println("Tomcat port: " + tomcatPort);
    System.out.println("=================================");
    Integer.parseInt(tomcatPort);
  }

}
