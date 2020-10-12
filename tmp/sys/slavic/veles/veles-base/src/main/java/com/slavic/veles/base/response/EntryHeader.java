package com.slavic.veles.base.response;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class  EntryHeader {
    private static final String APPLY_CODE = "300";
    private String code;
    private String message;

    public static EntryHeader HEADER_OK = new EntryHeader("200","请求成功");
    public static EntryHeader HEADER_TOKEN_ERROR = new EntryHeader("300","账户异常");
    public static EntryHeader HEADER_TOKEN_INVALID = new EntryHeader("305","登录态失效");
    public static EntryHeader HEADER_AUTH_ERROR = new EntryHeader("400","权限异常");
    public static EntryHeader HEADER_APPLY_ERROR = new EntryHeader("450","应用异常");
    public static EntryHeader HEADER_ERROR = new EntryHeader("500","服务器异常");

    public static EntryHeader APPLY_ERROR(String message) {
        return new EntryHeader(APPLY_CODE,message);
    }

}
