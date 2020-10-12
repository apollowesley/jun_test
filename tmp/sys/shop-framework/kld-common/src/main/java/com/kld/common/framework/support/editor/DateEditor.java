package com.kld.common.framework.support.editor;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * 日期属性转化
 * @author lvqi  
 * @date 2016年2月3日 下午3:39:07 
 * @version V1.0
 */
public class DateEditor extends PropertyEditorSupport {

	private SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (text != null && text.trim().length() > 0) {
            try {
                setValue(formater.parse(text));
            }
            catch (ParseException ex) {
            }
        }
    }

    @Override
    public String getAsText() {
        return (getValue() == null) ? "" : formater.format(getValue());
    }
	
}
