package com.sise.school.teach.constants;

import lombok.Getter;

/**
 * 唯一主键前缀
 *
 * @author idea
 * @data 2018/11/18
 */
@Getter
public class UnionCodePrefix {

    private UnionCodePrefix(){}

    /**
     * 视频code
     */
    public static String VIDEO_PREFIX="video_code_";

    /**
     * 留言code
     */
    public static String REVIEW_PREFIX="review_code_";

    /**
     * 对话code
     */
    public static String DIALOG_PREFIX="dialog_code_";

    /**
     * 学生code
     */
    public static final String STU_PREFIS="stu_code_";

    /**
     * token的前缀部分
     */
    public static final int TOKEN_LENGTH=10;

    public static final String TOKEN_KEY="token_key_";

}