package org.demo.ms;
import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author liangcai_zhu
 * @Description TODO
 * @Date 2020/5/8 13:36
 */
@SpringBootApplication
@EnableAdminServer
@EnableDiscoveryClient
@EnableScheduling
public class K8sDockerMicroServiceAdmin {

  public static void main(String[] args) {
    SpringApplication.run(K8sDockerMicroServiceAdmin.class, args);
  }

}
