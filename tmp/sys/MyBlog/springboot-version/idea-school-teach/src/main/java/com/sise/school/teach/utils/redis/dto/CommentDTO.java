package com.sise.school.teach.utils.redis.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * 存储点赞，差评的dto
 * @author idea
 * @data 2018/12/2
 */
@Getter
@Setter
@NoArgsConstructor
public class CommentDTO {

    private String unionCode;

    private List<String> reviewGoodCodeList;

    private List<String> reviewBadCodeList;

    private List<String> dialogGoodCodeList;

    private List<String> dialogBadCodeList;
}
