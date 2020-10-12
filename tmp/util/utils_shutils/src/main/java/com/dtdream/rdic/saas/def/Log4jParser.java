package com.dtdream.rdic.saas.def;

import org.apache.log4j.helpers.PatternConverter;
import org.apache.log4j.helpers.PatternParser;

/**
 * Created by Ozz8 on 2015/07/23.
 */
public class Log4jParser extends PatternParser {
    public Log4jParser(String pattern) {
        super(pattern);
    }

    @Override
    protected void finalizeConverter(char c) {
        if ('m' == c) {
            PatternConverter pc = new Log4jConverter(formattingInfo);
            //LogLog.debug("MESSAGE converter.");
            //formattingInfo.dump();
            super.addConverter(pc);
            currentLiteral.setLength(0);
        } else
            super.finalizeConverter(c);
    }
}
