package org.neuedu.fly.dao.impl;


import org.neuedu.fly.util.JdbcUtil;

import java.lang.reflect.Field;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @description
 * @auther: CDHONG.IT
 * @date: 2019/8/18-12:57
 **/
public class BaseDao {
    protected Connection con;
    protected PreparedStatement ps;
    protected ResultSet rs;

    protected boolean update(String sql,Object ... params){
        boolean flg = false;
        try {
            con = JdbcUtil.getConnection();
            ps = con.prepareStatement(sql);
            //设置传递过来的参数
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i+1,params[i]);
            }

            flg = ps.executeUpdate()>0;

        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            JdbcUtil.closeAll(rs,ps,con);
        }
        return  flg;
    }

    protected int insert(String sql,Object ... params){
        int key = -1;
        try {
            con = JdbcUtil.getConnection();
            //STATEMENT.RETURN_GENERATED_KEYS，指定返回生成的主键
            ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //设置传递过来的参数
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i+1,params[i]);
            }
            int count = ps.executeUpdate();
            //如果插入成功，则获取数据库自动生成的主键 Key
            if(count>0){
                rs = ps.getGeneratedKeys();
                key = rs.next()?rs.getInt(1):-1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            JdbcUtil.closeAll(rs,ps,con);
        }
        return key;
    }

    protected <T> T findOne(String sql, Class<T> clazz, Object ... params){
        List<T> list = this.list(sql, clazz, params);
        if(list.isEmpty()){
           return null;
        }
        return list.get(0);
    }

    protected <T> List<T> list(String sql, Class<T> clazz, Object ... params){
        List<T> list = new ArrayList<>();
        con = JdbcUtil.getConnection();
        try {
            ps = con.prepareStatement(sql);
            for(int i=0;i<params.length;i++){  //参数传递
                ps.setObject(i+1,params[i]);
            }
            rs = ps.executeQuery();  //SQL执行
            ResultSetMetaData data = rs.getMetaData(); //获取SQL列表信息：字段名称,类型...
            T obj = null; //组装对象声明
            while(rs.next()) {
                obj = clazz.newInstance(); //使用反射给要组装的对象实例化
                for (int i = 1; i <= data.getColumnCount(); i++) {
                    String fieldAlias = data.getColumnLabel(i); //获取查询字段的别名
                    //根据字段名称获取对象中的File对象
                    Field field = clazz.getDeclaredField(fieldAlias);
                    field.setAccessible(true); //打破Java限制，强制操作对象私有字段
                    String fieldType = field.getType().getName(); //获取当前字段的类型
                    //如果数据库中声明的类型与Java中封装对象的类型不一致，需要转换一下
                    if(fieldType.equals(LocalDate.class.getName())){
                        field.set(obj,rs.getDate(fieldAlias).toLocalDate());
                    }else if(fieldType.equals(LocalDateTime.class.getName())){
                        field.set(obj,rs.getTimestamp(fieldAlias).toLocalDateTime());
                    }else{ //如果类型一致，则都用Object接收
                        field.set(obj,rs.getObject(fieldAlias));
                    }
                }
                list.add(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JdbcUtil.closeAll(rs,ps,con);
        }
        return list;
    }


}
