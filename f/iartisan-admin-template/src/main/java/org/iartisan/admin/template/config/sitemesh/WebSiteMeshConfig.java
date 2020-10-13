package org.iartisan.admin.template.config.sitemesh;

import org.iartisan.runtime.env.EnvContextConfig;
import org.iartisan.runtime.utils.StringUtils;
import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class WebSiteMeshConfig extends ConfigurableSiteMeshFilter {

    private List<String> excluedPaths = new ArrayList<>();

    private static final String _CONFIG = "iartisan.sitemesh.excluedpath";

    public void setExcluedPaths() {
        String excluedPath = EnvContextConfig.get(_CONFIG);
        excluedPath = excluedPath + ",/authenticate,/index,/error/*";
        excluedPath = excluedPath + ",/index";
        excluedPath = excluedPath + ",/error/*";
        excluedPath = excluedPath + ",/druid/**";
        excluedPath = excluedPath + ",/userSupport/queryDetailPage";
        excluedPath = excluedPath + ",/bpm/deployment/toDesign";
        excluedPath = excluedPath + ",/modler/*";
        excluedPath = excluedPath + ",/reminder/*";
        if (StringUtils.isNotEmpty(excluedPath)) {
            excluedPaths = Arrays.asList(excluedPath.split(","));
        }
    }

    public WebSiteMeshConfig() {
        setExcluedPaths();
    }

    @Override
    protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
        builder.addTagRuleBundles(new ExtHtmlRuleBundle());
        //include 请求
        builder.addDecoratorPath("/*", "/decorator");
        for (String excluedPath : excluedPaths) {
            builder.addExcludedPath(excluedPath);
        }
    }
}
