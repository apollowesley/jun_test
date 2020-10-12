package com.kld.task;


import com.kld.task.api.ISysUserService;
import com.kld.task.dto.SysUser;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by anpushang on 2016/3/13.
 */
public class TaskUserTest {

    private ClassPathXmlApplicationContext context;

    /***
     * 查询 从库
     * @throws Exception
     */
    @Test
    public void test03()throws Exception{
        context = new ClassPathXmlApplicationContext(new String[] {"applicationContext.xml"});
        context.start();

        ISysUserService sysUserService = (ISysUserService)context.getBean("sysUserService") ;

        SysUser sysUser = sysUserService.getSysUserById(1);
        System.out.println("hansijing.."+sysUser.getEmail());
    }


    /***
     * 删除 主库
     * @throws Exception
     */
    @Test
    public void delete05()throws Exception{
        context = new ClassPathXmlApplicationContext(new String[] {"applicationContext.xml"});
        context.start();

        ISysUserService sysUserService = (ISysUserService)context.getBean("sysUserService") ;

        int resultCount = sysUserService.deleteSysUserById(1);
    }
}
