package org.neuedu.his.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 诊断目录管理
 * </p>
 *
 * @author CDHong
 * @since 2020-03-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("his_disease_directory")
@ApiModel(value="DiseaseDirectory对象", description="诊断目录管理")
public class DiseaseDirectory implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "疾病编码 拼音助记码")
    private String diseaseCode;

    @ApiModelProperty(value = "疾病名称")
    private String diseaseName;

    @ApiModelProperty(value = "国际ICD编码")
    private String diseaseIcd;

    @ApiModelProperty(value = "疾病所属分类")
    private Integer diseaseCategoryId;

    @ApiModelProperty(value = "删除标记 废除 0:保留(默认),1:废除")
    private Integer delMark;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "操作时间")
    private LocalDateTime createTime;


}
