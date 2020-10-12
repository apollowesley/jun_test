package org.openkoala.businesslog.config;

import java.util.List;

/**
 * User: zjzhai
 * Date: 12/4/13
 * Time: 2:51 PM
 */
public abstract class AbstractBusinessLogConfigAdapter implements BusinessLogConfigAdapter {


    private String preTemplate;

    private String template;

    private List<BusinessLogContextQuery> queries;

    public String getPreTemplate() {
        return preTemplate;
    }

    public void setPreTemplate(String preTemplate) {
        this.preTemplate = preTemplate;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public List<BusinessLogContextQuery> getQueries() {
        return queries;
    }

    public void setQueries(List<BusinessLogContextQuery> queries) {
        this.queries = queries;
    }
}
