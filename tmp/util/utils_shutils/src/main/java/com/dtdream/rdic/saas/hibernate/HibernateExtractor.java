package com.dtdream.rdic.saas.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ozz8 on 2015/07/18.
 */
public abstract class HibernateExtractor<T> {
    public abstract T extract (Object[] row, Object... objects);
    public final static class HiMap extends HibernateExtractor<Map<String,Object>> {
        @Override
        public Map<String,Object> extract(Object[] row, Object... objects) {
            if (null == objects || objects.length <= 0)
                return null;
            if (! (objects[0] instanceof List))
                return null;
            if (! (((List)objects[0]).get(0) instanceof HibernateSelector))
                return null;
            List<HibernateSelector> selectors = (List<HibernateSelector>) objects[0];
            if (row.length != selectors.size())
                return null;
            Map<String, Object> record = new HashMap<>(row.length);
            HibernateSelector selector;
            for (int i = 0; i < row.length; ++ i) {
                selector = selectors.get(i);
                record.put(selector.getAs(), row[i]);
            }
            return record;
        }
    }
}