package com.site.biz.initialize;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;

import com.site.common.utils.ApplicationContextUtil;
import com.site.quartz.service.SchedulerService;

/**
 * 
 * @Description 项目启动成功后执行一次，初始化JOB List
 * @author 王鑫 
 * @date Oct 19, 2016 9:08:29 AM
 */
public class InitializeServlet extends HttpServlet {

    private static final long serialVersionUID = -8652114793504953973L;

    /**
     * 
     * @Description 初始化JOB List
     * @author 王鑫
     */
    public void initialize() {
        SchedulerService schedulerService = ApplicationContextUtil.getBean("schedulerService", SchedulerService.class);
        schedulerService.schedule("退款", "simpleServiceHandler", "0/20 * * ? * * *");
    }

    public InitializeServlet() {

    }

    @Override
    public void init(final ServletConfig config) {
        System.out.println("启动成功后执行一次");
        initialize();
    }

}
