package cn.backflow.admin.common.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.DynamicAttributes;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

abstract class AbstractTagSupport extends SimpleTagSupport implements DynamicAttributes {

    private Map<String, Object> dynamicAttributes;
    protected JspWriter out;

    /**
     * Called before doTagLogic()
     */
    protected abstract void init();

    /**
     * 元素必需包含的css样式名
     */
    protected String getRequiredStyle() {
        return "";
    }

    /**
     * Subclasses do there logic by override this method.
     *
     * @throws JspException
     * @throws IOException
     */
    public abstract void doTagLogic() throws JspException, IOException;

    @Override
    public final void doTag() throws JspException, IOException {
        init();
        out = getJspContext().getOut();
        doTagLogic();
    }

    public Writer append(String value) throws IOException {
        return out.append(value);
    }


    public void attr(String name, String value) throws IOException {
        append(" ").append(name).append("=\"").append(value).append("\"");
    }

    public String attributes() {
        if (dynamicAttributes == null || dynamicAttributes.isEmpty()) return "";
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, Object> e : dynamicAttributes.entrySet()) {
            builder.append(" ").append(e.getKey()).append("=\"").append(e.getValue()).append("\"");
        }
        return builder.toString();
    }

    public void setDynamicAttribute(String uri, String name, Object value) throws JspException {
        if (this.dynamicAttributes == null) {
            this.dynamicAttributes = new HashMap<>();
        }
        if ("class".equalsIgnoreCase(name.trim())) {
            value = getRequiredStyle() + " " + value;
        }
        dynamicAttributes.put(name, value);
    }
}