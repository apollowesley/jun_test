package ${basepackage}.api.properties;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date; 

<#include "java_author.include">
public class SearchFilter<T> implements Serializable {

    private static final long serialVersionUID = -1L;
	
    /**
     * 更多参数
     */
    @ApiModelProperty(value = "更多参数")
    public T entity;

    /**
     * 每页显示数量
     */
    @ApiModelProperty(value = "每页显示数量")
    public Integer pageSize;

    /**
     * 当前页码
     */
    @ApiModelProperty(value = "当前页码")
    public Integer pageNo;

    /**
     * 关键词
     */
    @ApiModelProperty(value = "关键词")
    public String keyword;

    /**
     * 搜索字段
     */
    @ApiModelProperty(value = "搜索字段")
    public String keyType;

    /**
     * 分类筛选
     */
    @ApiModelProperty(value = "分类筛选")
    public String classType;

    /**
     * 起始时间
     */
    @ApiModelProperty(value = "起始时间")
    public Date startTime;

    /**
     * 截止时间
     */
    @ApiModelProperty(value = "截止时间")
    public Date endTime;

    /**
     * 筛选状态
     */
    @ApiModelProperty(value = "筛选状态")
    public Integer stateType;

    /**
     * 审核状态
     */
    @ApiModelProperty(value = "审核状态")
    public Integer auditType;

    /**
     * 排序字段
     */
    @ApiModelProperty(value = "排序字段")
    public String sortType;

    /**
     * 排序方向：ASC,DESC
     */
    @ApiModelProperty(value = "排序方向：ASC,DESC")
    public String direction;

    public SearchFilter() {
        this.pageNo = 1;
        this.pageSize = 10;
    }

    public SearchFilter(int pageSize, int pageNo) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public SearchFilter(int pageSize, int pageNo, String keyword) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.keyword = keyword;
    }

    @Override
    public String toString() {
        return "SearchFilter{" +
                "pageSize=" + pageSize +
                ", pageNo=" + pageNo +
                ", keyword='" + keyword + '\'' +
                ", keyType='" + keyType + '\'' +
                ", classType='" + classType + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", stateType=" + stateType +
                ", auditType=" + auditType +
                ", sortType='" + sortType + '\'' +
                ", direction='" + direction + '\'' +
                '}';
    }
}