package org.simplestudio.restful.cucumber.step;

import org.simplestudio.restful.context.SessionContext;
import org.simplestudio.restful.exception.RestException;
import org.simplestudio.restful.util.RestUtil;

import cucumber.api.java.en.Given;

public class StepGivenDefinition {
    @Given("^使用用户名=\\[(.+)\\]登录系统$")
    public void login(String name, String system) throws Exception {
        if (RestUtil.isBlank(name)) {
            throw new RestException("用户名称不能为空");
        }

        //如果系统需要登陆，此处处理登录操作

        SessionContext.CURRENT_USER = name;
    }
}
