package org.gen.code.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

public enum FM {
    FM;
    Configuration configuration = new Configuration(new Version("2.3.0"));


    FM() {
        configuration.setDefaultEncoding("UTF-8");
        try {
            configuration.setDirectoryForTemplateLoading(new File(Thread.currentThread().getContextClassLoader().getResource("template").getPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String runTemplate(String templateName, Map<String, Object> dataModel) {
        StringWriter out = new StringWriter();
        try {
            Template template = this.configuration.getTemplate(templateName + ".ftl");
            template.process(dataModel, out);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return out.toString();

    }

}
