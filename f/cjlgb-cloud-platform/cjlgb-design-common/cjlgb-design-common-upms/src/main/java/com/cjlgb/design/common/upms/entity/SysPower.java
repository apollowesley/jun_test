package com.cjlgb.design.common.upms.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cjlgb.design.common.core.bean.BaseEntity;
import com.cjlgb.design.common.core.util.TreeUtils;
import com.cjlgb.design.common.mybatis.handler.NumberEnumTypeHandler;
import com.cjlgb.design.common.upms.enums.PowerType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author WFT
 * @date 2020/7/13
 * description:系统权限
 */
@Setter
@Getter
@NoArgsConstructor
@TableName(autoResultMap = true)
public class SysPower extends BaseEntity implements TreeUtils.TreeEntity<SysPower> {

    /**
     * 权限名称
     */
    @NotBlank(message = "权限名称不能为空")
    private String name;

    /**
     * 权限标识
     */
    @NotBlank(message = "权限标识不能为空")
    private String permission;

    /**
     * 路径
     */
    private String path;

    /**
     * 父级Id
     */
    private Long pid;

    /**
     * 权限类型
     */
    @NotNull(message = "权限类型不能为空")
    @TableField(typeHandler = NumberEnumTypeHandler.class)
    private PowerType type;

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
    private List<SysPower> children;

    /**
     * 排序值
     */
    @TableField(exist = false)
    private Integer sort = 0;
}
