package com.hong.generate.common;

import lombok.*;
import org.apache.ibatis.annotations.ConstructorArgs;

/**
 * @description:
 * @author: YJH
 * @time: 2020/4/5 10:47
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Result {

    @NonNull
    private Integer code;

    @NonNull
    private String message;

    private Object data;


}
