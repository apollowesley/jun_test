/*
 * Copyright 2017 KiWiPeach.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.kiwipeach.demo.response;

import cn.kiwipeach.demo.global.enums.BusinessEnums;

/**
 * Create Date: 2018/01/16
 * Description: 单个返回实体对象类
 *
 * @author kiwipeach [1099501218@qq.com]
 */
public class ResultResponse<T> {

    private static final String DEFAULT_FAIL_CODE_VALUE="SYS_99999";
    private static final String DEFAULT_FAIL_CODE_MESSAGE="系统默认异常信息";
    private static final String DEFAULT_SUCCESS_VALUE="SYS_10000";
    private static final String DEFAULT_SUCCESS_MESSAGE="系统 restful 接口请求成功";

    private boolean isSuccess;
    private T data;
    private String code;
    private String message;

    public ResultResponse() {
        isSuccess = false;
        data = null;
        this.code = DEFAULT_FAIL_CODE_VALUE;
        this.message =  DEFAULT_FAIL_CODE_MESSAGE;
    }

    /**
     * 成功构造函数
     *
     * @param data 返回数据
     */
    public ResultResponse(T data) {
        this(true, data);
    }

    /**
     * 成功构造
     *
     * @param isSuccess 成功标志
     * @param data      返回数据
     */
    public ResultResponse(boolean isSuccess, T data) {
        this.isSuccess = isSuccess;
        this.data = data;
        this.code = DEFAULT_SUCCESS_VALUE;
        this.message =  DEFAULT_SUCCESS_MESSAGE;
    }

    /**
     * 失败构造
     * @param isSuccess 是否失败
     * @param codeEnum 失败枚举信息
     */
    public ResultResponse(boolean isSuccess, BusinessEnums codeEnum) {
        this.isSuccess = isSuccess;
        this.code = codeEnum.getCode();
        this.message = codeEnum.getMessage();
    }

    /**
     * 失败构造
     * @param isSuccess 是否失败
     * @param code 失败code
     * @param message 失败message
     */
    public ResultResponse(boolean isSuccess, String code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }

    /**
     * 静态成功方法
     * @param data 数据报文
     * @return 返回信息
     */
    public static <T> ResultResponse<T> success(T data) {
        ResultResponse<T> result = new ResultResponse<T>(true,data);
        result.setCode(DEFAULT_SUCCESS_VALUE);
        result.setMessage(DEFAULT_SUCCESS_MESSAGE);
        return result;
    }

    /**
     * 静态成功方法
     * @param code 数据报文
     * @param message 数据报文
     * @return 返回信息
     */
    public static <T> ResultResponse<T> fail(String code, String message) {
        ResultResponse<T> result = new ResultResponse<T>(false,code,message);
        return result;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
