package kiang.tei;

import kiang.teb.TebEngine;
import kiang.teb.TebModel;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.FileTemplateResolver;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author eyeLee(yinjun622@163.com)
 *         Time 2014/11/1.
 */
public class Thymeleaf implements TebEngine {

    private TemplateEngine engine;

    @Override
    public TebEngine init(Properties properties) throws Exception {

        FileTemplateResolver templateResolver =  new FileTemplateResolver();
        templateResolver.setPrefix("./src/kiang/tpl/");
        templateResolver.setSuffix(".tpl");
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setTemplateMode("XHTML");

        engine = new TemplateEngine();
        engine.setTemplateResolver(templateResolver);
        engine.initialize();
        return this;
    }

    @Override
    public void test(Map arguments, Writer writer) throws Exception {
        Context ctx = new Context();
        ctx.setVariables(arguments);
        engine.process("thymeleaf",  ctx, writer);
    }

    @Override
    public void test(Map arguments, OutputStream output) throws Exception {
    }

    @Override
    public void shut() throws Exception {

    }

    @Override
    public boolean isBinarySupport() {
        return false;
    }

    public static void main(String args[]) throws Exception {
        String source="UTF-8", target = "UTF-8";
        Writer writer = new OutputStreamWriter(System.out, target);
        Map data = new HashMap();
        data.put("target", target);
        data.put("models", TebModel.dummyModels(20));
        Properties properties = new Properties();
        properties.setProperty("source", source);
        properties.setProperty("target", target);
        properties.setProperty("binary", String.valueOf(true));
        TebEngine engine = new Thymeleaf().init(properties);
        engine.test(data, writer);
        writer.flush();
        engine.shut();
    }

}
