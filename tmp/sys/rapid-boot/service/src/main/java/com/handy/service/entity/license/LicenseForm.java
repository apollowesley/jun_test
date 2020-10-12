package com.handy.service.entity.license;

import com.handy.service.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author handy
 * @since 2019-09-12
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class LicenseForm extends BaseEntity<LicenseForm> {
    @ApiModelProperty("开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;
    @ApiModelProperty("结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;
    @ApiModelProperty("备注")
    private String note;
}
