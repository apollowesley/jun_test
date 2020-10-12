package com.dtdream.rdic.saas.controller;

import com.dtdream.rdic.saas.def.Constant;
import com.dtdream.rdic.saas.def.Result;
import com.dtdream.rdic.saas.def.StdRet;
import com.dtdream.rdic.saas.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public class ControllerBasic {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public final String ENCODING_UTF8 = "UTF-8";

    public byte[] body(HttpServletRequest objRequest) {
        /**
         * 获取HTTP请求的BODY部分内容
         */
        try {
            objRequest.setCharacterEncoding(ENCODING_UTF8);
        } catch (UnsupportedEncodingException e) {
            logger.error("设置请求编码方式错误：", e);
            return null;
        }
        ServletInputStream objHttpIstream;
        try {
            objHttpIstream = objRequest.getInputStream();
        } catch (IOException e) {
            logger.error("获取请求输入流失败：", e);
            return null;
        }

        /**
         * 获取BODY内容并转换为请求对象
         */
        final int iBodyLen = objRequest.getContentLength();
        if (iBodyLen <= 0) {
            IoUtils.close(objHttpIstream);
            return null;
        }
        byte[] bRead = new byte[iBodyLen];
        int iOffset = 0;
        try {
            while ((iOffset += objHttpIstream.read(bRead, iOffset, iBodyLen-iOffset)) < iBodyLen);
        } catch (IOException e) {
            logger.error("读取请求Body失败：", e);
            bRead = null;
        } finally {
            IoUtils.close(objHttpIstream);
        }

        return bRead;
    }

    @SuppressWarnings("unchecked")
    public<T> StdRet parameter(HttpServletRequest objRequest, T to) {
        StdRet ret = new StdRet();
        StringBuilder sblog = ThreadUtils.info(1);
        Map<String, String[]> objMapParams = objRequest.getParameterMap();
        if ((null == objMapParams) || (objMapParams.size() <= 0)) {
            String sBody = Constant.EMPTY_STRING;
            try {
                byte[] bytes = this.body(objRequest);
                if (null != bytes)
                    sBody = new String(bytes, ENCODING_UTF8);
                sblog.append(sBody);
            } catch (UnsupportedEncodingException e) {
                logger.error("读取请求Body遇到非法编码：", e);
                ret.setResult(Result.FAILURE_UNSUPPORTED_ENC);
                return ret;
            } finally {
                logger.debug(sblog.toString());
            }
            if (sBody.length() <= 0) {
                ret.setResult(Result.FAILURE_EMPTY);
                return ret;
            }
            return JsonUtils.json(sBody, to.getClass());
        } else {
            ret.setData(BeanUtils.copy(objMapParams, to));
            return ret;
        }
    }

    public<T> StdRet parameter(HttpServletRequest objRequest, Class<T> type) {
        StdRet ret = new StdRet();
        StringBuilder sblog = ThreadUtils.info(2);
        Map<String, String[]> objMapParams = objRequest.getParameterMap();
        if ((null == objMapParams) || (objMapParams.size() <= 0)) {
            String sBody = Constant.EMPTY_STRING;
            try {
                byte[] bytes = this.body(objRequest);
                if (null != bytes)
                    sBody = new String(bytes, ENCODING_UTF8);
                sblog.append(sBody);
            } catch (UnsupportedEncodingException e) {
                logger.error("读取请求Body遇到非法编码：", e);
                ret.setResult(Result.FAILURE_UNSUPPORTED_ENC);
                return ret;
            } finally {
                logger.debug(sblog.toString());
            }
            if (sBody.length() <= 0) {
                ret.setResult(Result.FAILURE_EMPTY);
                return ret;
            }
            return JsonUtils.json(sBody, type);
        } else {
            /**
             * 对于嵌套属性通常会异常，因为BeanUtils.populate方法仅能支持普通类型
             * 属性，对于FORM URLEncoded编码，spring确实可以自动转换，但尚不明确
             * spring实现的方法具体所在，按理说，spring可以实现，我们也应该可以实现
             * TODO 分析spring实现Form格式到Bean转换原理，应用到此处
             */
            ret.setData(BeanUtils.copy(objMapParams, type));
            return ret;
        }
    }
}
