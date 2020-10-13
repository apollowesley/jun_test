package com.myapp.common.bean;

import java.lang.reflect.Field;
import java.util.UUID;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import net.oschina.durcframework.easymybatis.handler.FillHandler;
import net.oschina.durcframework.easymybatis.handler.FillType;

public class UuidFill extends FillHandler<String> {
    
    @Override
    public boolean match(Class<?> entityClass, Field field, String columnName) {
        boolean isPk = field.getAnnotation(Id.class) != null;
        
        GeneratedValue gv = field.getAnnotation(GeneratedValue.class);
        boolean isAuto = gv != null && gv.strategy() == GenerationType.AUTO;
        
        return isPk && isAuto;
    }

    @Override
    public String getColumnName() {
        return "id";
    }

    @Override
    public FillType getFillType() {
        return FillType.INSERT;
    }

    @Override
    protected Object getFillValue(String defaultValue) {
        return UUID.randomUUID().toString();
    }

}