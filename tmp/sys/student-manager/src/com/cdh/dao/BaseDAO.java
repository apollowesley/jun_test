package com.cdh.dao;

import com.cdh.util.DBUtil;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BaseDAO {
    protected PreparedStatement ps = null;
    protected ResultSet rs = null;
    protected Connection con = null;

    /**
     * 根据ID删除对应行数据
     * @param sql 删除的SQL语句
     * @param obj 删除的条件ID
     * @return 执行结果
     */
    protected boolean delById(String sql,Object obj){
        return this.updateExecute(sql,obj,"del");
    }

    /**
     * 修改通用方法
     * @param sql 修改的sql语句
     * @param obj 改执行的结果
     */
    protected boolean update(String sql,Object obj){
        String fieldInfo = sql.substring(sql.indexOf("set") + 3).replace("where", ",").replaceAll("=\\?","");
        return this.updateExecute(sql,obj,fieldInfo);
    }

    /**
     * 添加通用方法
     * @param sql  要执行的SQL语句
     * @param obj 注入的参数信息
     * @return  返回执行结果
     */
    protected boolean add(String sql ,Object obj){
        //获取SQL中要操作的字段列表
        String fieldInfo = sql.substring(sql.indexOf("(")+1, sql.indexOf(")") );
        return updateExecute(sql, obj, fieldInfo);
    }

    /**
     * 更新通过操作
     * @param sql  要执行的SQL语句
     * @param obj  要注入的参数对象
     * @param fieldInfo SQL语句参数信息
     * @return 执行结果
     */
    private boolean updateExecute(String sql, Object obj, String fieldInfo) {
        con = DBUtil.getConnection();
        try {
            ps = con.prepareStatement(sql);
            if(Objects.equals(fieldInfo,"del")){
                ps.setObject(1,obj);
            }else{
                //把SQL中获取的所有字段去前后空格，并驼峰转换
                List<String> fieldNames = Arrays.stream(fieldInfo.split(",")).map(field->this.underlineToHump(field.trim())).collect(Collectors.toList());

                Class<?> clazz = obj.getClass();
                for(int i = 0; i < fieldNames.size(); i ++) {
                    //根据名称获取对应的字段
                    Field field = clazz.getDeclaredField(fieldNames.get(i));
                    //打开私有属性无法调用的权限
                    field.setAccessible(true);
                    //设置参数值
                    ps.setObject(i+1,field.get(obj));
                }
            }
            return ps.executeUpdate()>0;
        } catch (Exception e) {
            e.printStackTrace();
        }  finally{
            DBUtil.closeAll(rs,ps,con);
        }
        return false;
    }

    /**
     * 查询所有
     * @param sql 查询SQL语句
     * @param <T> 返回对象的类型
     * @return 返回List列表
     */
    protected  <T> List<T>  queryList(String sql,Class<?> clazz){
        con = DBUtil.getConnection();
        List<T> list = new ArrayList<>();
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            Object instance = clazz.newInstance();
            while(rs.next()){
                list.add(resultSetHandler(clazz, rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            DBUtil.closeAll(rs,ps,con);
        }
        return list;
    }

    protected  <T> List queryList(String sql, Class<?> clazz, Function<ResultSet ,List<T>> fn){
        con = DBUtil.getConnection();
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            return fn.apply(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            DBUtil.closeAll(rs,ps,con);
        }
        return Collections.EMPTY_LIST;
    }

    /**
     * 结果集映射封装为对象
     * @param clazz
     * @param rs
     * @param <T>
     * @return
     * @throws Exception
     */
    private <T> T resultSetHandler(Class<?> clazz, ResultSet rs) throws Exception {
        Object instance = clazz.newInstance();
        for (int i=1;i<=rs.getMetaData().getColumnCount();i++){
            String columnName = rs.getMetaData().getColumnName(i);
            Object columnValue = rs.getObject(columnName);
            if(columnValue instanceof Timestamp){
                columnValue = ((Timestamp)columnValue).toLocalDateTime();
            }
            Field field = clazz.getDeclaredField(this.underlineToHump(columnName));
            field.setAccessible(true);
            field.set(instance,columnValue);
        }
        return (T) instance;
    }

    /**
     * 根据Id查询对象
     * @param sql 查询SQL语句
     * @param <T>
     * @return
     */
    protected  <T> T  queryOne(String sql,Class<?> clazz,Object id){
        con = DBUtil.getConnection();
        try {
            ps = con.prepareStatement(sql);
            ps.setObject(1,id);
            rs = ps.executeQuery();
            if(rs.next()){
                return this.resultSetHandler(clazz, rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            DBUtil.closeAll(rs,ps,con);
        }
        return null;
    }
    /**
     * 下划线转驼峰命名
     * @param para
     * @return
     */
    private String underlineToHump(String para){
        StringBuilder result=new StringBuilder();
        String a[]=para.split("_");
        for(String s:a){
            if(result.length()==0){
                result.append(s.toLowerCase());
            }else{
                result.append(s.substring(0, 1).toUpperCase());
                result.append(s.substring(1).toLowerCase());
            }
        }
        return result.toString();
    }


}
