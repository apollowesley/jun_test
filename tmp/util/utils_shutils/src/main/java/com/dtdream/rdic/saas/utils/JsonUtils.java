package com.dtdream.rdic.saas.utils;

import com.dtdream.rdic.saas.app.Results;
import com.dtdream.rdic.saas.def.JsonMapper;
import com.dtdream.rdic.saas.def.Result;
import com.dtdream.rdic.saas.def.StdRet;
import com.dtdream.rdic.saas.httpresponse.ResponseBasic;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.exc.UnrecognizedPropertyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class JsonUtils {
    protected final static Logger logger = LoggerFactory.getLogger(JsonUtils.class);
    private final static JsonMapper mapper = new JsonMapper();
    private final static String UTF8 = "UTF-8";
    private final static ResponseBasic responseSuccess = new ResponseBasic();
    private final static ResponseBasic responseFailure = new ResponseBasic(Results.FAIL_COMMON);

    public static String json (Map<String, Object> map){
        String result = "";
        try {
            byte[] bytes = mapper.writeValueAsBytes(map);
            result = new String(bytes, UTF8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String json (Object objToBeSerialized){
        String result = "";
        try {
            byte[] bytes =mapper.writeValueAsBytes(objToBeSerialized);
            result = new String(bytes, UTF8);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 获取Json字符串对应的Bean
     * @param src           String || InputStream
     * @param valueType
     * @return
     */
    public static<T> StdRet json (Object src, Class<T> valueType) {
        StdRet ret = new StdRet();
        if (null == src) {
            ret.setResult(Result.FAILURE_NULL);
            return ret;
        }

        try {
            T target;
            if (src instanceof String) {
                target = mapper.readValue((String) src, valueType);
            } else if (src instanceof InputStream) {
                target = mapper.readValue((InputStream) src, valueType);
            } else {
                ret.setResult(Result.FAILURE_UNEXPECTED_TYPE);
                return ret;
            }
            ret.setData(target);
        } catch (JsonParseException e) {
            ret.setResult(Results.FAIL_JSON_PARSE);
            logger.error("参数不是有效JSON格式", e);
        } catch (JsonMappingException e) {
            if (e instanceof UnrecognizedPropertyException)
                ret.setResult(
                    new Result(
                        Result.FAILURE_INV_PARAM,
                        (((UnrecognizedPropertyException) e).getUnrecognizedPropertyName())));
            else
                ret.setResult(Result.FAILURE_INV_PARAM);
            logger.error("属性映射错误", e);
        } catch (IOException e) {
            ret.setResult(Results.FAIL_IO_READ);
            logger.error("读写流出错", e);
        }
        return ret;
    }

    public static String success() {
        return json(responseSuccess);
    }
    public static String failure() {
        return json(responseFailure);
    }
    public static String failure(Result result) {
        return json(new ResponseBasic(result));
    }
}
