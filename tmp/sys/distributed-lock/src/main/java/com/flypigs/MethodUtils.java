package com.flypigs;

import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class MethodUtils {
    private static final DefaultParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();

    /**
     * SpEL表达式
     * @param spEL el表达式
     * @param values 原始值
     * @return
     */
    public static Object parseSpElValue(String spEL ,Map<String,Object> values){
        EvaluationContext context = new StandardEvaluationContext();
        ((StandardEvaluationContext) context).setVariables(values);
        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression(spEL);
        return exp.getValue(context);
    }

    /**
     * 解析方法的参数名及对应值
     * @param method
     * @param methodArgs
     * @return
     */
    public static Map<String,Object> parse(Method method,Object[] methodArgs){
        //获取参数名和值
        String[] names = parameterNameDiscoverer.getParameterNames(method);
        Map<String, Integer> nameIndexs = new HashMap<>();
        if (names != null) {
            for (int i = 0; i < names.length; i++) {
                String name = names[i];
                nameIndexs.put(name, i);
            }
        }
        Map<String, Object> nameAndValues = new HashMap<>();
        Object[] args = methodArgs;
        for (String name : nameIndexs.keySet()) {
            Integer index = nameIndexs.get(name);
            nameAndValues.put(name, args[index]);
        }
        return nameAndValues;
    }
}
