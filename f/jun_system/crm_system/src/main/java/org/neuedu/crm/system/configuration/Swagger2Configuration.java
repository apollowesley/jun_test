package org.neuedu.crm.system.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @description
 * @auther: CDHONG.IT
 * @date: 2019/12/13-19:33
 **/
@Configuration
@EnableSwagger2
public class Swagger2Configuration {

    @Bean
    public Docket createRestFulApi() { //api文档实例
        return new Docket(DocumentationType.SWAGGER_2) //文档类型
                .apiInfo(this.apiInfo())//api 信息
                .select()//构建api选择器
                // @ApiIgnore 标注不在接口文档中显示
                .apis(RequestHandlerSelectors.basePackage("org.neuedu.crm.system.controller")) //api 选择器选择 api 包
                .paths(PathSelectors.any())//api选择器选择包路劲下的任何api显示在文档中
                .build();//创建文档
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("团队名", "www.my.com", "my@my.com");
        return new ApiInfoBuilder()
                .title("CRM 客户关系管理系统")
                .description("文档描述")
                .contact(contact)   // 联系方式
                .version("1.0.0")  // 版本
                .build();
    }

}
