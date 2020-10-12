package com.dtdream.rdic.saas.def;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Ozz8 on 2015/07/21.
 */
public abstract class AbstractLogger {
    public Logger logger = LoggerFactory.getLogger(this.getClass());
}
