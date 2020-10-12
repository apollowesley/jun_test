package org.neuedu.his.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @apiNote : 
 * @author : CDHONG.IT
 * @date : 2020/3/31
 * @version 1.0
 */
@Slf4j
@SpringBootConfiguration
public class JacksonSerializableConfig {

    @Value("${spring.jackson.date-format:yyyy-MM-dd HH:mm:ss}")
    private String pattern;

    @Bean
    public ObjectMapper objectMapper(){
        log.debug("全局格式化日期pattern:{}",pattern);
        ObjectMapper objectMapper = new ObjectMapper();
        JavaTimeModule module = new JavaTimeModule();
        //序列化： 日期--->前端JSON
        module.addSerializer(LocalDateTime.class,new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(pattern)));
        //反序列化: 前端JSON -->日期
        module.addDeserializer(LocalDateTime.class,new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(pattern)));
        //注册
        objectMapper.registerModule(module);
        return objectMapper;
    }

}
