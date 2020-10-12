package org.neuedu.his.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 非药品收费项目
 * </p>
 *
 * @author CDHong
 * @since 2020-03-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("his_drugs")
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value="Drugs对象", description="非药品收费项目")
public class Drugs implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键编号",example = "0")
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "药品编码 唯一")
    private String drugsCode;

    @ApiModelProperty(value = "药品名称")
    private String drugsName;

    @ApiModelProperty(value = "药品规格")
    private String drugsFormat;

    @ApiModelProperty(value = "药品剂型 针剂,片剂,胶囊",example = "0")
    @TableField("drugs_dosage_Id")
    private Integer drugsDosageId;

    @ApiModelProperty(value = "药品类型 西药,中草药",example = "0")
    private Integer drugsTypeId;

    @ApiModelProperty(value = "药品单价")
    private Double drugsPrice;

    @ApiModelProperty(value = "拼音助记码")
    private String mnemonicCode;

    @ApiModelProperty(value = "包装单位")
    private String drugsUnit;

    @ApiModelProperty(value = "删除标记 废除",example = "0")
    @TableField(fill = FieldFill.INSERT)
    private Integer delMark;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "操作时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;


}
