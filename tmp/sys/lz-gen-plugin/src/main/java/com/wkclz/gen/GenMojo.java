package com.wkclz.gen;

import com.wkclz.core.helper.GenHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.util.List;

@Mojo(name="gen",defaultPhase= LifecyclePhase.PACKAGE)
public class GenMojo extends AbstractMojo {

    @Parameter
    private String url;

    @Parameter
    private List<String> options;

    @Parameter(property = "args")
    private String args;

    @Override
    public void execute() {
        if (StringUtils.isNotBlank(url)){
            System.out.println("gen url: " + url);
            GenHelper.setBaseUrl(url);
        }
        if (CollectionUtils.isEmpty(options)){
            System.err.println("未发现可用的配置");
        }
        for (String option : options) {
            GenHelper.genCode(option);
        }
    }

}