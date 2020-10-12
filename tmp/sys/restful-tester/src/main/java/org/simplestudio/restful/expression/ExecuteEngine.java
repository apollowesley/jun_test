package org.simplestudio.restful.expression;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class ExecuteEngine {

    private static ScriptEngineManager ENGIEN_FACTORY = new ScriptEngineManager();
    private static ScriptEngine        JS_ENGINE      = ENGIEN_FACTORY
            .getEngineByName("JavaScript");

    public static Boolean execute(String expression) throws Exception {
        String parseExpression = ExpressionParser.parse(expression).toString();
        Boolean result = (Boolean) JS_ENGINE.eval(parseExpression);

        return result;
    }

}
