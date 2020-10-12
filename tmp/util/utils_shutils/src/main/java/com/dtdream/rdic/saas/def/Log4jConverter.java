package com.dtdream.rdic.saas.def;

import org.apache.log4j.helpers.FormattingInfo;
import org.apache.log4j.helpers.PatternConverter;
import org.apache.log4j.spi.LoggingEvent;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Ozz8 on 2015/07/23.
 */
public class Log4jConverter extends PatternConverter {

    Log4jConverter(FormattingInfo formattingInfo) {
        super(formattingInfo);
    }

    public String convert(LoggingEvent event) {
        String message = event.getRenderedMessage();
        StringBuilder sb = new StringBuilder();
        if (null == message)
            return String.valueOf(event.getMessage());
        char[] cs = message.toCharArray();
        char last = 0;
        for (int i = 0; i < cs.length; ++ i) {
            if ('\\' != last) {
                switch (cs[i]) {
                    case '\'':
                    case '"':
                    case '`':
                        sb.append('\\');
                        break;
                    default:
                        break;
                }
            }
            last = cs[i];
            sb.append(cs[i]);
        }
        return sb.toString();
    }
}
