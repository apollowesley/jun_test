package kiang.tei;

import kiang.teb.TebEngine;
import kiang.teb.TebModel;
import kiang.wetter.Engine;

import java.io.OutputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Copyright (c) 2014 by kiang
 *
 * @author kiang
 * @version 0.1-pre
 */
public final class Wetter implements TebEngine {
    private Engine engine;

    @Override
    public TebEngine init(Properties properties) throws Exception {
        final Properties ps = new Properties();
        ps.setProperty("wetter.reflect.factory", "kiang.toolkit.reflect.RxAsmFactory");
        ps.setProperty("wetter.loader.class", "kiang.wetter.io.ClassPathLoader");
        ps.setProperty("wetter.loader.space", "kiang/tpl");
        ps.setProperty("wetter.inputEncoding", properties.getProperty("source", "UTF-8"));
        ps.setProperty("wetter.outputEncoding", properties.getProperty("target", "UTF-8"));
        ps.setProperty("wetter.useTemplateCache", "true");
        ps.setProperty("wetter.grabber", "kiang.wetter.io.GrabberRapid");
        ps.setProperty("wetter.textFilter", "");
        ps.setProperty("wetter.debugger", "");
        engine = Engine.get(ps);
        return this;
    }

    @Override
    public void test(Map arguments, Writer writer) throws Exception {
        engine.getTemplate("wetter.tpl").execute(arguments, writer);
    }

    @Override
    public void test(Map arguments, OutputStream output) throws Exception {
        engine.getTemplate("wetter.tpl").execute(arguments, output);
    }

    @Override
    public boolean isBinarySupport() {
        return true;
    }

    @Override
    public void shut() throws Exception {
    }

    public static void main(String args[]) throws Exception {
        String source="UTF-8", target = "UTF-8";
        OutputStream output = System.out;
        Map data = new HashMap();
        data.put("target", target);
        data.put("models", TebModel.dummyModels(20));
        Properties properties = new Properties();
        properties.setProperty("source", source);
        properties.setProperty("target", target);
        properties.setProperty("binary", String.valueOf(true));
        TebEngine engine = new Wetter().init(properties);
        engine.test(data, output);
        output.flush();
        engine.shut();
    }
}
