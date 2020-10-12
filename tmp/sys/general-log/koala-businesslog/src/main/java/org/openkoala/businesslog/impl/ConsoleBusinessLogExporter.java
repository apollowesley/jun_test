package org.openkoala.businesslog.impl;

import org.openkoala.businesslog.BusinessLogExporter;

/**
 * User: zjzhai
 * Date: 12/1/13
 * Time: 9:46 PM
 */
public class ConsoleBusinessLogExporter implements BusinessLogExporter {


    @Override
    public BusinessLogExporter export(String log) {
        System.out.println(log);
        return this;
    }
}
