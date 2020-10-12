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
package cn.kiwipeach.demo;

import cn.kiwipeach.demo.global.enums.BusinessEnums;

/**
 * Create Date: 2018/01/16
 * Description: 业务异常
 *
 * @author kiwipeach [1099501218@qq.com]
 */
public class BusinessException extends RuntimeException{
    private String code;
    private String message;
    public BusinessException(String message) {
        this.message = message;
    }

    public BusinessException(String code,String message) {
        this.code = code;
        this.message = message;
    }


    public BusinessException(BusinessEnums codeEnum) {
        this.code = codeEnum.getCode();
        this.message = codeEnum.getMessage();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
