package com.dtdream.rdic.saas.utils;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

import java.io.StringWriter;

/**
 * Created by Ozz8 on 2015/07/07.
 */
public class TextUtils {
    private TemplateUtils utils;
    public TextUtils(TemplateUtils utils) {
        this.utils = utils;
    }
    public StringWriter template (String template, String[] vars, Object[] vals) {
        StringWriter sw = new StringWriter();
        Template templ = utils.get(template);
        if (null == templ)
            return sw;
        VelocityContext context = new VelocityContext();
        if (null != vars && null != vals && vars.length == vals.length )
            for (int i = 0; i < vars.length; ++ i)
                context.put(vars[i], vals[i]);
        templ.merge(context, sw);
        return sw;
    }
}
