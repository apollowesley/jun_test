package com.quick.develop.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Date;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${swagger.show}")
    private boolean swaggerShow;

    @Value("${swagger.packagePath}")
    private String packagePath;

    @Value("${swagger.title}")
    private String swaggerTitle;

    @Value("${swagger.description}")
    private String swaggerDescription;

    @Value("${swagger.version}")
    private String swaggerVersion;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(swaggerShow)
                .ignoredParameterTypes(HttpServletRequest.class)
                .select()
                .apis(RequestHandlerSelectors.basePackage(packagePath))
                .paths(PathSelectors.any())
                .build().directModelSubstitute(Timestamp.class, Date.class);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(swaggerTitle)
                .description(swaggerDescription)
                .version(swaggerVersion)
                .build();
    }
}
