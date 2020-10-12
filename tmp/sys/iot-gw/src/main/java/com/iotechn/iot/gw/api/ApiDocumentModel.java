package com.iotechn.iot.gw.api;

import com.iotechn.iot.commons.core.annotation.HttpParamType;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2018-09-13
 * Time: 上午9:35
 */
@Data
public class ApiDocumentModel {
    private List<Group> groups;
    @Data
    public static class Group {
        private String name;
        private List<Method> methods;

    }
    @Data
    public static class Method {
        private String name;
        private String description;
        private String retType;
        private List<Field> retObj;
        private List<Parameter> parameters;
    }
    @Data
    public static class Parameter {
        private String name;
        private String description;
        /**
         * 参数class类型
         */
        private String paramType;
        /**
         * 参数枚举
         */
        private HttpParamType type;
        private Boolean required;
    }
    @Data
    public static class Field {
        private String name;
        private String type;
    }

}
