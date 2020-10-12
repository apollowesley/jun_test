/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcshuo.json.object;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author tengda
 */
public abstract class Converter {

    public abstract Object converter(Object o);

    public static Map<Class, Converter> converters = null;

    public static Object converter(Object o, Class clazz) {
        initCnverters();
        Converter converter = converters.get(clazz);
        if (converter == null) {
            throw new RuntimeException("未找到默认的Converter:" + clazz.getName());
        }
        return converter.converter(o);

    }

    private static void initCnverters() {
        if (converters != null) {
            return;
        }
        converters = new HashMap<Class, Converter>();
        converters.put(int.class, new IntegerConverter());
        converters.put(Integer.class, converters.get(int.class));
        converters.put(boolean.class, new BooleanConverter());
        converters.put(Boolean.class, converters.get(boolean.class));
        converters.put(long.class, new LongConverter());
        converters.put(Long.class, converters.get(long.class));
        converters.put(double.class, new DoubleConverter());
        converters.put(Double.class, converters.get(double.class));
        converters.put(float.class, new FloatConverter());
        converters.put(Float.class, converters.get(float.class));
        converters.put(String.class, new StringConverter());
        converters.put(StringBuffer.class, converters.get(String.class));
        converters.put(StringBuilder.class, converters.get(String.class));
        converters.put(Date.class, new DateConverter());
        converters.put(Calendar.class, new CalendarConverter());
    }

    static class DoubleConverter extends Converter {

        @Override
        public Object converter(Object o) {
            if (o == null) {
                return null;
            }
            return Double.parseDouble(o.toString());
        }

    }

    static class LongConverter extends Converter {

        @Override
        public Object converter(Object o) {
            if (o == null) {
                return null;
            }
            return new Double(Double.parseDouble(o.toString())).longValue();
        }

    }

    static class IntegerConverter extends Converter {

        @Override
        public Object converter(Object o) {
            if (o == null) {
                return null;
            }
            return new Double(Double.parseDouble(o.toString())).intValue();
        }

    }

    static class BooleanConverter extends Converter {

        @Override
        public Object converter(Object o) {
            if (o == null) {
                return false;
            }
            String code = o.toString().trim().toLowerCase();
            if ("1".equals(code) || "true".equals(code)) {
                return true;
            }
            return false;
        }

    }

    static class FloatConverter extends Converter {

        @Override
        public Object converter(Object o) {
            if (o == null) {
                return null;
            }
            return new Double(Double.parseDouble(o.toString())).floatValue();
        }
    }

    static class StringConverter extends Converter {

        @Override
        public Object converter(Object o) {
            if (o == null) {
                return null;
            }
            return o.toString();
        }
    }

    static class CalendarConverter extends Converter {

        private String format = "yyyy-MM-dd hh:mm:ss";

        @Override
        public Object converter(Object o) {
            if (o == null) {
                return null;
            }
            Calendar calendar = Calendar.getInstance();
            Object _o = new DateConverter(format).converter(o);
            calendar.setTime((Date) _o);
            return calendar;
        }
    }

    static class DateConverter extends Converter {

        private static final SimpleDateFormat _df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        private SimpleDateFormat format = _df;

        public DateConverter() {
        }

        public DateConverter(String _format) {
            this.format = new SimpleDateFormat(_format);
        }

        @Override
        public Object converter(Object o) {
            if (o == null) {
                return null;
            }
            if (o instanceof Long) {
                return new Date((Long) o);
            }

            try {
                return format.parse(o.toString());
            } catch (ParseException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

}
