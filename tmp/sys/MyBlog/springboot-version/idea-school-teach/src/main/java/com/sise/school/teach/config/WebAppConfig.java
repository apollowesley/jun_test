package com.sise.school.teach.config;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.MultipartConfigElement;

/**
 * 作者：idea
 * 时间：2017/10/30
 * 版本：
 * 使用说明：web端项目启动的时候进行文件的访问
 */
@Configuration
@Getter
@Setter //需要有gett和sett才可以访问
@Slf4j
@ConfigurationProperties(prefix = "file.upload")
public class WebAppConfig extends WebMvcConfigurerAdapter {

    /**
     * 存放文件的文件夹名称
     */
    private String dir;

    /**
     * 网络访问路径
     */
    private String url;

    /**
     * 项目的格式类型（已经打包完成后）
     */
    private final String PACKAGE_TYPE = ".jar";

    /**
     * 还处于开发阶段
     */
    private final String CLASS_TYPE = "classes";

    /**
     * 如果项目部署在window操作系统，需要添加前缀引导
     */
    private final String FILE_ADDRESS_HEAD = "file:";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String path;
        if (!"".equals(dir)) {
            path = WebAppConfig.class.getClassLoader().getResource("").getPath();
            if (path.contains(PACKAGE_TYPE)) {
                path = path.substring(0, path.indexOf(PACKAGE_TYPE));
            } else if (path.contains(CLASS_TYPE)) {
                path = FILE_ADDRESS_HEAD + path.substring(0, path.indexOf(CLASS_TYPE));
            }
            path = path.substring(0, path.lastIndexOf("/")) + "/" + dir + "/";
        } else {
            throw new RuntimeException("[文件资源配置]配置文件属性出错，注入失败！");
        }
        log.info("[文件资源管理路径]：" + path);
        registry.addResourceHandler("/" + dir + "/**").addResourceLocations(path);
        super.addResourceHandlers(registry);
    }

    /**
     * 文件上传配置
     *
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //  单个数据大小
        factory.setMaxFileSize("10240MB"); // KB,MB
        /// 总上传数据大小
        factory.setMaxRequestSize("102400MB");
        return factory.createMultipartConfig();
    }


}
