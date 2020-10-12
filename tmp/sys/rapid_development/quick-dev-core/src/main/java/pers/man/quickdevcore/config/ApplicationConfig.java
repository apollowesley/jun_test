package pers.man.quickdevcore.config;

import lombok.Getter;
import lombok.Setter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * 应用配置
 *
 * @author MAN
 * @version 1.0
 * @date 2020-04-03 17:59
 * @project quick-dev
 */
@MapperScan({"pers.man.quickdevcore.system.persistence", "pers.man.quickdevservice.mapper"})
@Configuration
@Getter
@Setter
public class ApplicationConfig {
    /**
     * 项目名称
     */
    private String name;
    /**
     * 项目版本
     */
    private String version;
    /**
     * 版权信息
     */
    private String copyRight;
    /**
     * 文件上传路径
     */
    private String profile;
}
