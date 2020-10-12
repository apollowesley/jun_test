package schedule;

import cn.uncode.schedule.ZKScheduleManager;
import cn.uncode.schedule.web.ManagerServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhm on 16-9-2.
 */
@SpringBootApplication
@EnableScheduling
public class Applications {
    public static void main(String[] args) {
        SpringApplication.run(Applications.class,args);
    }
    @Bean
    public ZKScheduleManager getZKScheduleManager() throws Exception {
        ZKScheduleManager beanResult = new ZKScheduleManager();
        Map<String,String> zkConfig = new HashMap<String,String>();
        zkConfig.put("zkConnectString","127.0.0.1:2181");
        zkConfig.put("rootPath","/com/schedule");
        zkConfig.put("zkSessionTimeout","60000");
        zkConfig.put("userName","zhm");
        zkConfig.put("password","1q2w3e4r");
        zkConfig.put("isCheckParentPath","true");
        zkConfig.put("ipBlacklist","127.0.0.2,127.0.0.3");
        beanResult.setZkConfig(zkConfig);
        beanResult.init();
        return beanResult;
    }

    @Bean
    public ServletRegistrationBean servletRegistrationBean(){
        return new ServletRegistrationBean(new ManagerServlet(),"/uncode/schedule");
    }
}
