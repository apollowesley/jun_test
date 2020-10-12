package org.openkoala.businesslog;

import org.openkoala.businesslog.config.BusinessLogContextQuery;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * User: zjzhai
 * Date: 12/4/13
 * Time: 2:33 PM
 */
public class BusinessLogContextQueryExecutor {


    public static Map<String, Object> startQuery(Map<String, Object> initContext, List<BusinessLogContextQuery> queries) {
        if (null == initContext) {
            initContext = new HashMap<String, Object>();
        }

        if (null == queries) {
            return initContext;
        }

        Map<String, Object> result = initContext;

        Iterator<BusinessLogContextQuery> it = queries.iterator();

        //TODO 需要考虑更多情况
        while (it.hasNext()) {
            result.putAll(it.next().query());
        }

        return result;
    }
}
