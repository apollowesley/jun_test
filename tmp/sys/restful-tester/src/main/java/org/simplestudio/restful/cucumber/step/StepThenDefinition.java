package org.simplestudio.restful.cucumber.step;

import org.apache.log4j.Logger;
import org.simplestudio.restful.exception.RestException;
import org.simplestudio.restful.expression.ExecuteEngine;
import org.simplestudio.restful.expression.ExpressionParser;
import org.simplestudio.restful.util.RestUtil;

import cucumber.api.java.en.Then;

public class StepThenDefinition {

    private static Logger logger = Logger.getLogger(StepThenDefinition.class);

    @Then("^进行结果校验,表达式为\\[(.+)\\]$")
    public void check(String expression) throws Exception {
        String parseExpression = ExpressionParser.parse(expression).toString();
        //执行表达式
        Boolean result = (Boolean) ExecuteEngine.execute(parseExpression);
        if (!result) {
            throw new RestException(expression + " 执行结果为：" + parseExpression + ",校验不通过！");
        }
    }

    @Then("^打印结果,打印的记录值为\\[(.+)\\]$")
    public void print(String paramName) throws Exception {
        if (!RestUtil.isBlank(paramName)) {
            paramName = paramName.trim();
        }
        Object cacheObj = ExpressionParser.parse(paramName);
        if (cacheObj == null) {
            throw new RestException("参数未设置,请先设置再打印！");
        }
        logger.info("[" + paramName + "]打印结果为 : " + cacheObj.toString());
    }
}
