package com.sise.school.teach.bussiness.review.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 记录点赞和差评的历史记录
 * @author idea
 * @data 2018/11/30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodBadLog implements Serializable {

    private String code;

}
