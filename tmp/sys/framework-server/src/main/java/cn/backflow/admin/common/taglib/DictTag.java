package cn.backflow.admin.common.taglib;


import cn.backflow.admin.entity.Dict;
import cn.backflow.admin.service.DictService;
import cn.backflow.lib.util.SpringContextUtil;
import cn.backflow.lib.util.StringUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class DictTag extends AbstractTagSupport {

    private String code; // 字典编码
    private String name; // 元素name属性
    private String element = "select"; // HTML 标签, 默认为select
    private String value; // 默认值
    private List<String> values; // 默认值, 用于checkbox, multiple-select
    private String defaultOption; // select 元素默认选项
    private Map<Comparable, Dict> dictMap;

    private static final DictService dictService = SpringContextUtil.getBean(DictService.class);

    @Override
    protected void init() {
        if (StringUtils.isBlank(name))
            name = code;
        if (StringUtil.isBlank(value) && values != null && !values.isEmpty())
            value = values.get(0);
        else
            values = Collections.singletonList(value);

        dictMap = dictService.findMapByCode(code);
    }

    @Override
    public void doTagLogic() throws JspException, IOException {

        switch (element) {
            case "select":
                printSelectElement();
                break;
            case "checkbox":
                printCheckboxElement();
                break;
            case "radio":
                printRadioElement();
                break;
            case "text":
                printTextElement();
                break;
            default:
                throw new JspTagException("标签类型参数[ " + element + " ]不被支持");
        }
    }

    private void printTextElement() throws IOException, JspException {
        Dict dict = dictMap.get(value);
        if (dict != null)
            append(dict.getValue());
    }

    private void printCheckboxElement() throws IOException {
        for (Dict d : dictMap.values()) {
            append("<label class='checkbox checkbox-primary checkbox-inline'><input type='checkbox' name='");
            append(name).append("' value='").append(d.getKey()).append("'");
            append(values.contains(d.getKey()) ? " checked" : "").append("><i></i>");
            append(d.getValue()).append("</label>");
        }
    }

    private void printRadioElement() throws IOException {
        for (Dict d : dictMap.values()) {
            append("<label").append(attributes());
            append("><input type='radio' name='");
            append(name).append("' value='").append(d.getKey()).append("'");
            append(d.getKey().equals(value) ? " checked" : "").append("><i></i>");
            append(d.getValue()).append("</label>");
        }
    }

    private void printSelectElement() throws IOException {
        append("<select name='").append(name).append("'").append(attributes()).append(">");
        if (StringUtil.isNotBlank(defaultOption)) {
            append("<option value=''>").append(defaultOption).append("</option>");
        }
        for (Dict d : dictMap.values()) {
            append("<option value='").append(d.getKey()).append("'")
                    .append(d.getKey().equals(value) ? " selected" : "").append(">")
                    .append(d.getValue())
                    .append("</option>");
        }
        append("</select>");
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setElement(String element) {
        this.element = element.toLowerCase();
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setDefaultOption(String defaultOption) {
        this.defaultOption = defaultOption;
    }
}