package org.neuedu.his.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @description
 * @auther: CDHONG.IT
 * @date: 2019/12/13-19:33
 **/
@SpringBootConfiguration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket createRestFulApi() { //api文档实例
        return new Docket(DocumentationType.SWAGGER_2) //文档类型
                .apiInfo(this.apiInfo())//api 信息
                .select()//构建api选择器
                // @ApiIgnore 标注不在接口文档中显示
                .apis(RequestHandlerSelectors.basePackage("org.neuedu.his.controller")) //api 选择器选择 api 包
                .paths(PathSelectors.any())//api选择器选择包路劲下的任何api显示在文档中
                .build();//创建文档
    }

    private ApiInfo apiInfo() {
        //Contact contact = new Contact("团队名", "www.my.com", "my@my.com");
        return new ApiInfoBuilder()
                .title("HIS 医疗信息系统")
                .description("医院信息系统，主要功能按照数据流量、流向及处理过程分为临床诊疗、药品管理、经济管理、统计分析等。")
                //.contact(contact)   // 联系方式
                .version("1.0.0")  // 版本
                .build();
    }

}
