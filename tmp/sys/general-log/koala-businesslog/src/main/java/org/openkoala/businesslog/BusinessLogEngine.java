package org.openkoala.businesslog;

import org.openkoala.businesslog.config.BusinessLogConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * User: zjzhai
 * Date: 12/4/13
 * Time: 2:21 PM
 */
public class BusinessLogEngine {

    private BusinessLogConfig config;

    private BusinessLogRender render;


    private Map<String, Object> initContext = new HashMap<String, Object>();

    public BusinessLogEngine(BusinessLogConfig config, BusinessLogRender render) {
        this.config = config;
        this.render = render;
    }

    public BusinessLogEngine(BusinessLogConfig config, BusinessLogRender render, Map<String, Object> aContext) {
        this.config = config;
        this.render = render;
        initContext = aContext;
    }

    public String exportLogBy(BusinessLogExporter exporter) {
        String log = render();
        exporter.export(log);
        return log;
    }

    private String render() {
        render.render(createContext(), config.getPreTemplate(), config.getTemplate());
        return render.build();
    }

    private Map<String, Object> createContext() {
        if (null == initContext) {
            initContext = new HashMap<String, Object>();
        }
        return BusinessLogContextQueryExecutor.startQuery(initContext, config.getQueries());
    }

}
