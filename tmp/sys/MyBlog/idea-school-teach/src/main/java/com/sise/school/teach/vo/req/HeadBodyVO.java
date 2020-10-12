package com.sise.school.teach.vo.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author idea
 * @data 2018/9/22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HeadBodyVO<H,V> {

    /**
     * 请求头
     */
    private H head;

    /**
     * 请求体
     */
    private V body;
}
