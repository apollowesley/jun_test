/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcshuo.json.node;

import java.util.HashMap;
import java.util.Map;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;

/**
 *
 * JS运行上下文
 *
 * @author tengda
 */
public class JsContext {

    private Map<String, Object> variables = new HashMap<String, Object>();

    private ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");
    
    public <T> T getInterface(Object scriptObject , Class<T> face){
        Invocable inv = (Invocable) engine;  
        return inv.getInterface(scriptObject, face);  
    }

    public JsContext setVariable(String name, Object value) {
        variables.put(name, value);
        return this;
    }

    public JsContext setVariables(Map<String, Object> variables) {
        this.variables.putAll(variables);
        return this;
    }

    public Object execute(String js) throws ScriptException {
        SimpleScriptContext context = new SimpleScriptContext();
        for (String key : variables.keySet()) {
            context.setAttribute(key, variables.get(key), ScriptContext.ENGINE_SCOPE);
        }
        Compilable compilable = (Compilable) engine;
        CompiledScript script = compilable.compile(js);
        return script.eval(context);
    }

    public ScriptContext executeContext(String js) throws ScriptException {
        for (String key : variables.keySet()) {
            engine.put(key, variables.get(key));
        }
         engine.eval(js);

        return engine.getContext();
    }

}
