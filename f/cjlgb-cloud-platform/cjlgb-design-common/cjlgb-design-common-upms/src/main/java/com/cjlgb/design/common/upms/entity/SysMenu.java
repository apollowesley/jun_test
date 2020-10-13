package com.cjlgb.design.common.upms.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.cjlgb.design.common.core.bean.BaseEntity;
import com.cjlgb.design.common.core.bean.Validation;
import com.cjlgb.design.common.core.util.TreeUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author WFT
 * @date 2020/7/19
 * description:系统菜单
 */
@Getter
@Setter
@NoArgsConstructor
public class SysMenu extends BaseEntity implements TreeUtils.TreeEntity<SysMenu> {

    /**
     * 菜单名称
     */
    @NotBlank(message = "菜单名称不能为空",groups = { Validation.Add.class, Validation.Edit.class })
    private String name;

    /**
     * 权限标识
     */
    private String permission;

    /**
     * 索引
     */
    @JsonIgnore
    private String indexes;

    /**
     * 路径
     */
    private String path;

    /**
     * 图标
     */
    private String icon;

    /**
     * 父级Id
     */
    private Long pid;

    /**
     * 排序值
     */
    private Integer sort;

    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 最后修改时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime lastModifyTime;

    /**
     * 子节点列表
     */
    @TableField(exist = false)
    private List<SysMenu> children;

}
