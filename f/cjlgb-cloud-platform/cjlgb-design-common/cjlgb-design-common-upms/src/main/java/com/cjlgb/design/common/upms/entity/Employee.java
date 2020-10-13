package com.cjlgb.design.common.upms.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cjlgb.design.common.core.bean.BaseEntity;
import com.cjlgb.design.common.mybatis.enums.LockFlag;
import com.cjlgb.design.common.mybatis.handler.NumberEnumTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author WFT
 * @date 2020/7/12
 * description:员工
 */
@Getter
@Setter
@NoArgsConstructor
@TableName(autoResultMap = true)
public class Employee extends BaseEntity {

    /**
     * 用户Id
     */
    private Long userId;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 登陆密码：md5(明文 + 随机盐)
     */
    @JsonIgnore
    private String password;

    /**
     * 手机号码
     */
    private String userMobile;

    /**
     * 用户头像
     */
    private String portrait;

    /**
     * 部门Id
     */
    @NotNull(message = "部门Id不能为空")
    private Long deptId;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 等级标识,值越大其级别越高
     */
    private Integer gradeFlag;

    /**
     * 锁定标记：{ -1:锁定,0:正常 }
     */
    @TableField(fill = FieldFill.INSERT,typeHandler = NumberEnumTypeHandler.class)
    private LockFlag lockFlag;

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
     * 角色Id列表
     */
    @NotEmpty(message = "角色Id列表不能为空")
    @TableField(exist = false)
    private List<Long> roleIdList;

}
