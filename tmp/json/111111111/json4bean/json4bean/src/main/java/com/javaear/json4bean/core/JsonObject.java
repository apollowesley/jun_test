package com.javaear.json4bean.core;

import com.javaear.json4bean.util.MapUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author aooer
 */
class JsonObject implements Serializable {

    private static final Object OBJECT_END = new Object();
    private static final Object ARRAY_END = new Object();
    private static final Object COLON = new Object();
    private static final Object COMMA = new Object();

    private transient CharacterIterator it = null;
    private char c = 0;
    private transient Object jsonObj = null;
    private StringBuilder builder = new StringBuilder();

    public JsonObject(String sourceStr) {
        it = new StringCharacterIterator(sourceStr);
        c = it.first();
        read();
    }

    /**
     * 遍历sourceStr
     * 细粒度处理过程
     *
     * @return jsonObj
     */
    private Object read() {
        while (Character.isWhitespace(c)) c = it.next();
        char ch = c;
        c = it.next();
        switch (ch) {
            case '"':
                jsonObj = matchString();
                break;
            case '[':
                jsonObj = matchArray();
                break;
            case ']':
                jsonObj = ARRAY_END;
                break;
            case ',':
                jsonObj = COMMA;
                break;
            case '{':
                jsonObj = matchObject();
                break;
            case '}':
                jsonObj = OBJECT_END;
                break;
            case ':':
                jsonObj = COLON;
                break;
            case 't':
                loopNext(3);
                jsonObj = Boolean.TRUE;
                break;
            case 'f':
                loopNext(4);
                jsonObj = Boolean.FALSE;
                break;
            case 'n':
                loopNext(3);
                jsonObj = null;
                break;
            default:
                c = it.previous();
                if (Character.isDigit(c) || c == '-')
                    jsonObj = matchNumber();
                else
                    throw new RuntimeException("json4bean unknown value type! check is have null value and fix it!");
        }
        return jsonObj;
    }

    /**
     * 循环next次数
     *
     * @param times times
     */
    private void loopNext(int times) {
        for (int i = 0; i < times; i++)
            c = it.next();
    }

    /**
     * 匹配到Object
     *
     * @return object
     */
    private Object matchObject() {
        Map<Object, Object> result = new LinkedHashMap<>();
        Object key = read();
        while (jsonObj != OBJECT_END) {
            read();
            if (jsonObj != OBJECT_END) {
                result.put(key, read());
                if (read() == COMMA) key = read();
            }
        }
        return result;
    }

    /**
     * 匹配到集合Array
     *
     * @return object
     */
    private Object matchArray() {
        List<Object> result = new ArrayList<>();
        Object value = read();
        while (jsonObj != ARRAY_END) {
            result.add(value);
            if (read() == COMMA) {
                value = read();
            }
        }
        return result;
    }

    /**
     * 匹配到数字
     *
     * @return object
     */
    private Object matchNumber() {
        int length = 0;
        boolean isFloatingPoint = false;
        builder.setLength(0);

        if (c == '-') {
            add(c);
        }
        length += addDigits();
        if (c == '.') {
            add(c);
            length += addDigits();
            isFloatingPoint = true;
        }
        if (c == 'e' || c == 'E') {
            add(c);
            if (c == '+' || c == '-') {
                add(c);
            }
            addDigits();
            isFloatingPoint = true;
        }

        String s = builder.toString();
        return isFloatingPoint
                ? (length < 17) ? (Object) Double.valueOf(s) : new BigDecimal(s)
                : (length < 19) ? (Object) Long.valueOf(s) : new BigInteger(s);
    }

    /**
     * 匹配数字
     *
     * @return object
     */
    private int addDigits() {
        int result;
        for (result = 0; Character.isDigit(c); ++result) add(c);
        return result;
    }

    /**
     * 匹配字符串
     *
     * @return object
     */
    private Object matchString() {
        builder.setLength(0);
        while (c != '"') {
            if (c == '\\') {
                c = it.next();
                add(c == 'u' ? unicode() : MapUtils.TRANSEFER_CHAR_MAP.get(c));
            } else
                add(c);
        }
        c = it.next();
        return builder.toString();
    }

    private void add(Character cc) {
        if (cc == null) return;
        builder.append(cc);
        c = it.next();
    }

    /**
     * 处理unicode
     *
     * @return char
     */
    private char unicode() {
        int value = 0;
        for (int i = 0; i < 4; ++i) {
            switch (c = it.next()) {
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    value = (value << 4) + c - '0';
                    break;
                case 'a':
                case 'b':
                case 'c':
                case 'd':
                case 'e':
                case 'f':
                    value = (value << 4) + (c - 'a') + 10;
                    break;
                case 'A':
                case 'B':
                case 'C':
                case 'D':
                case 'E':
                case 'F':
                    value = (value << 4) + (c - 'A') + 10;
                    break;
                default:
                    ;
            }
        }
        return (char) value;
    }

    public Object getJsonObj() {
        return (jsonObj instanceof ArrayList&&((ArrayList) jsonObj).size()>0)?((ArrayList) jsonObj).get(0):jsonObj;
    }
}
