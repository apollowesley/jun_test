package com.dtdream.rdic.saas.utils;

import com.dtdream.rdic.saas.def.CommonException;
import com.dtdream.rdic.saas.def.Result;
import org.apache.velocity.Template;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Ozz8 on 2015/07/04.
 * 依赖于FileUtils的approot，要求FileUtils.approot必须设置
 */
public class TemplateUtils {
    protected Logger logger = LoggerFactory.getLogger(TemplateUtils.class);
    Velocity velocity;
    Map<String,String> templates;
    Map<String,Template> templateMap;

    public TemplateUtils(String properties) throws IOException, CommonException {
        velocity = new Velocity();
        templates = new HashMap<>();
        templateMap = new HashMap<>();
        File file = new File(properties);
        String realpath = file.getParent();
        Properties props = new Properties();
        props.load(new FileInputStream(properties));
        String path = new StringBuilder(realpath).append(File.separator).append("velocity").append(File.separator).toString();
        props.setProperty("file.resource.loader.path", path);
        velocity.init(props);
    }

    public Template get (String template) {
        if (this.templates.containsKey(template)) {
            Template temp = templateMap.get(template);
            if (null == temp) {
                try {
                    temp = Velocity.getTemplate(this.templates.get(template));
                    templateMap.put(template, temp);
                    return temp;
                } catch (ResourceNotFoundException e) {
                    logger.error("资源不存在：", e);
                } catch (ParseErrorException e) {
                    logger.error("解析资源失败：", e);
                }
            } else {
                return temp;
            }

        }
        return null;
    }

    public Map<String,String> getTemplates() {
        return templates;
    }

    public void setTemplates(Map<String,String> templates) {
        this.templates = templates;
    }
}
