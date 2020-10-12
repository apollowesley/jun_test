package com.dtdream.rdic.saas.def;

import org.apache.log4j.PatternLayout;
import org.apache.log4j.helpers.PatternParser;

/**
 * Created by Ozz8 on 2015/07/23.
 */
public class Log4jLayout extends PatternLayout {
    @Override
    protected PatternParser createPatternParser(String pattern) {
        return new Log4jParser(pattern);
    }
}
