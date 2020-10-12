package com.handy.service.entity.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author handy
 * @Description: {}
 * @date 2019/8/21 10:54
 */
@Data
@Accessors(chain = true)
public class SysResource {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long category;
    private String superId;
    private String title;
    private String icon;
    private String href;
    private String target;
    private Long ordinal;
}
