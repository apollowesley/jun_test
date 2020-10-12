package com.foo.common.base.utils;

import org.joda.time.DateTime;

import java.util.Date;

public class JodaTimeHelper {
    private final static org.slf4j.Logger logger = org.slf4j.LoggerFactory
            .getLogger(JodaTimeHelper.class);

    public static DateTime getMinValueOfDay(DateTime dateTime) {
        return dateTime.secondOfDay().withMinimumValue();
    }

    public static DateTime getMaxValueOfDay(DateTime dateTime) {
        return dateTime.secondOfDay().withMaximumValue();
    }

    public static void main(String[] args) {
        final DateTime dateTime = new DateTime();
        logger.info("getMinValueOfDay as:{}",getMinValueOfDay(dateTime));
        logger.info("getMaxValueOfDay as:{}",getMaxValueOfDay(dateTime));
    }
}
