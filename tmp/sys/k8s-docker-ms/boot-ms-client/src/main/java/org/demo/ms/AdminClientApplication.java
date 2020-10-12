package org.demo.ms;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author liangcai_zhu
 * @Description TODO
 * @Date 2020/5/8 13:45
 */
@SpringBootApplication
public class AdminClientApplication {


  public static void main(String[] args) {
    SpringApplication.run(AdminClientApplication.class, args);
  }

//    @Configuration
//    public static class SecurityPermitAllConfig extends WebSecurityConfigurerAdapter {
//        @Override
//        protected void configure(HttpSecurity http) throws Exception {
//            http.authorizeRequests().anyRequest().permitAll()
//                    .and().csrf().disable();
//        }
//    }
}


