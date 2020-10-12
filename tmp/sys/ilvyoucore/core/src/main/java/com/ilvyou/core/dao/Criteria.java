package com.ilvyou.core.dao;


import com.ilvyou.core.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GuanYuCai on 2016/9/7 0007.
 */
public class Criteria {
    private List<String> conditions = new ArrayList<String>();
    private List<Object> args = new ArrayList<Object>();

    public Criteria() {

    }

    public Criteria(String condition, Object... values) {
        _add(condition, values);
    }

    public Criteria add(String condition, Object... values) {
        _add(condition, values);
        return this;
    }

    public String getSql() {
        StringBuffer sql = new StringBuffer();
        String where = getConditions();
        if (!StringUtil.isEmpty(where)) {
            sql.append(" WHERE ").append(where);
        }
        return sql.toString();
    }

    public Object[] getArgs() {
        return args.toArray();
    }

    public List<Object> getArgsList() {
        return args;
    }

    /**
     * 查询条件的拼接，如果有多条件，使用（）装入条件使用And语句连接。
     *
     * @return
     */
    public String getConditions() {
        if (conditions.size() == 0) {
            return "";
        } else {
            StringBuffer sb = new StringBuffer("(").append(conditions.get(0)).append(")");
            for (int i = 1; i < conditions.size(); i++) {
                sb.append(" AND ").append("(").append(conditions.get(i)).append(")");
            }
            return sb.toString();
        }
    }

    /**
     * 条件中语句拼接
     *
     * @param condition
     * @param values
     */
    private void _add(String condition, Object... values) {
        if (!StringUtil.isEmpty(condition)) {
            List<Object> list = new ArrayList<Object>();
            int i = 1;
            boolean like = condition.contains(" like ");
            boolean IN = condition.contains(" IN ");
            for (Object obj : values) {
                if (obj == null || StringUtil.isEmpty(obj.toString())) {
                    return;
                }
                boolean flag = false;
                String str = "";
                /** 如果为like语句 **/
                if (like) {
                    int index = indexOf(condition, "?", i++);
                    str = obj.toString();
                    if (index > 0) {
                        if (condition.charAt(index - 1) == '%') {
                            str = "%" + str;
                            flag = true;
                        }
                        if (condition.charAt(index + 1) == '%') {
                            str = str + "%";
                            flag = true;
                        }
                    }
                }
                /** 如果为IN语句 **/
                if (IN) {
                    int index = indexOf(condition, "?", i++);
                    str = obj.toString();
                    if (index > 0) {
                        if (condition.charAt(index - 1) == '(') {
                            str = "(" + str;
                            flag = true;
                        }

                        if (condition.charAt(index + 1) == ')') {
                            str = str + ")";
                            flag = true;
                        }
                    }
                }
                list.add(flag ? str : obj);
            }
            conditions.add(like ? condition.replaceAll("%", "") : condition);
            args.addAll(list);
        }
    }

    private int indexOf(String obj, String str, int index) {
        int j = 0;
        for (int i = 0; i < index; i++) {
            j = obj.indexOf(str, j);
        }
        return j;
    }
}
