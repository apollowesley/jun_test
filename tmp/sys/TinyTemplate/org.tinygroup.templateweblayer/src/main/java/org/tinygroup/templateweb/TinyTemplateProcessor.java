package org.tinygroup.templateweb;

import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.template.TemplateContext;
import org.tinygroup.template.TemplateEngine;
import org.tinygroup.weblayer.AbstractTinyProcessor;
import org.tinygroup.weblayer.WebContext;

import javax.servlet.http.HttpServletResponse;

/**
 * 添加url处理器 Created by luoguo on 2014/7/14.
 */
public class TinyTemplateProcessor extends AbstractTinyProcessor {

    private TemplateEngine engine;
    private static final Logger logger = LoggerFactory
            .getLogger(TinyTemplateProcessor.class);

    public TemplateEngine getEngine() {
        return engine;
    }

    public void setEngine(TemplateEngine engine) {
        this.engine = engine;
    }

    public void reallyProcess(String servletPath, WebContext context) {
        HttpServletResponse response = context.getResponse();
        try {
            long startTime = System.currentTimeMillis();
            TemplateContext templateContext = new TinyWebTemplateContext(context);
            if (servletPath.endsWith("let")) {
                engine.renderTemplateWithOutLayout(servletPath.substring(0, servletPath.length() - 3), templateContext, response.getWriter());
            } else {
                engine.renderTemplate(servletPath, templateContext, response.getWriter());
            }
            long endTime = System.currentTimeMillis();
            logger.logMessage(LogLevel.DEBUG, "路径<{}>处理时间：{}ms", servletPath,
                    endTime - startTime);
        } catch (Exception e) {
            logger.errorMessage(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

}
