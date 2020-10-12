package com.handy.param.init;

import lombok.Data;

/**
 * @author handy
 * @Description: {服务端清理缓存接口地址，为空则不请求}
 * @date 2019/8/21 15:19
 */
@Data
public class ClearInfo {
    private String clearUrl;
}