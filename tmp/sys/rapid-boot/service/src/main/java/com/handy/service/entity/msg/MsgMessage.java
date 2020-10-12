package com.handy.service.entity.msg;

import com.baomidou.mybatisplus.annotation.IdType;
import com.handy.service.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 消息通知
 * </p>
 *
 * @author handy
 * @since 2019-09-06
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class MsgMessage extends BaseEntity<MsgMessage> {

    /**
     * 用户id
     */
    private Long accountId;

    /**
     * 类型
     */
    private Long category;

    /**
     * 类型名称
     */
    private String categoryName;

    /**
     * 消息标题
     */
    private String title;

    /**
     * 消息内容
     */
    private String content;

}
