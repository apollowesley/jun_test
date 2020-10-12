package com.turingoal.generator.commons;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.turingoal.generator.constant.TgConstantSystemValues;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import freemarker.cache.StringTemplateLoader;
import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.TemplateNotFoundException;
import freemarker.template.Version;

/**
 * FreeMarkerInit
 */
public final class TgFreeMarker {
    private static Logger logger = LoggerFactory.getLogger(TgFreeMarker.class);
    private static Configuration cfg = null;
    private static Configuration cfg4StringTemplate = null;

    public TgFreeMarker() {
        super();
    }

    /**
     * getInstance
     */
    public TgFreeMarker(final Version freeMarkerVersion, final String templateDir) throws Exception {
        // 配置类
        cfg = new Configuration(freeMarkerVersion);
        cfg.setDirectoryForTemplateLoading(new File(templateDir));
        cfg.setDefaultEncoding(TgConstantSystemValues.DEFAULT_ENCODING);
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        // cfg4StringTemplate
        cfg4StringTemplate = new Configuration(freeMarkerVersion);
        StringTemplateLoader templateLoader = new StringTemplateLoader();
        cfg4StringTemplate.setTemplateLoader(templateLoader);
        cfg4StringTemplate.setDefaultEncoding(TgConstantSystemValues.DEFAULT_ENCODING);
        cfg4StringTemplate.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    }

    /**
     * getDefinedTemplate
     */
    public Template getDefinedTemplate(final String templateName) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException {
        Template template = cfg.getTemplate(FileUtil.normalize(templateName));
        return template;
    }

    /**
     * getStringTemplate
     */
    public Template getStringTemplate(final String templateName, final String templateValue) throws IOException {
        Template template = new Template(templateName, templateValue, cfg4StringTemplate);
        return template;
    }

    /**
     * 解析字符串模板
     */
    public String processStringTemplate(final String templateValue, final Map<String, Object> map) throws IOException, TemplateException {
        StringWriter stringWriter = new StringWriter();
        Template template = getStringTemplate("tgStringTemplate", templateValue);
        template.process(map, stringWriter);
        String result = stringWriter.toString();
        stringWriter.close();
        return result;
    }

    /**
     * 解析模板
     */
    public void processTemplate(final String templateFilePath, final String templateDir, final String outputAbsolutePath, final Map<String, Object> map) {
        if ("ftl".equals(FileUtil.extName(templateFilePath))) { // freemarker模板
            FileOutputStream fos = null;
            Writer writer = null;
            try {
                String filePath = outputAbsolutePath.substring(0, outputAbsolutePath.lastIndexOf("."));
                fos = new FileOutputStream(filePath);
                writer = new OutputStreamWriter(fos, TgConstantSystemValues.DEFAULT_ENCODING);
                Template temp = getDefinedTemplate(StrUtil.removePrefix(templateFilePath, templateDir));
                temp.process(map, writer);
            } catch (IOException e) {
                logger.error("!!! " + e.getMessage() + " !!!");
            } catch (TemplateException e) {
                logger.error("!!! " + e.getMessage() + " !!!");
            } finally {
                try {
                    if (writer != null) {
                        writer.close();
                    }
                    if (fos != null) {
                        fos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            FileUtil.copy(templateFilePath, outputAbsolutePath, true); // 复制文件
        }
    }
}
