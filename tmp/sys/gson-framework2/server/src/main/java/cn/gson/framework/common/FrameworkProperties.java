package cn.gson.framework.common;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>****************************************************************************</p>
 * <p><b>Copyright © 2010-2019 soho team All Rights Reserved<b></p>
 * <ul style="margin:15px;">
 * <li>Description : cn.gson.framework.common</li>
 * <li>Version     : 1.0</li>
 * <li>Creation    : 2019年01月26日</li>
 * <li>@author     : ____′↘夏悸</li>
 * </ul>
 * <p>****************************************************************************</p>
 */
@Data
@Component
@ConfigurationProperties(prefix = "framework")
public class FrameworkProperties {

    /**
     * 超级管理员 id
     */
    private Long superId;
}
