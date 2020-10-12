package com.handy.config;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.HttpPutFormContentFilter;

/**
 * @author handy
 * @Description: {设置接收put的值}
 * @date 2019/8/28 10:17
 */
@Component
public class PutFilter extends HttpPutFormContentFilter {
}
