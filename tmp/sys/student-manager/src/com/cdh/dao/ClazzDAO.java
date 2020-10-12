package com.cdh.dao;

import com.cdh.model.Clazz;
import com.cdh.util.DBUtil;
import org.apache.commons.lang3.StringUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClazzDAO extends BaseDAO {

    public boolean save(Clazz clazz){
        String sql = "insert into clazz(cla_name,cla_info) values(?,?)";
        return this.add(sql,clazz);
    }

    public boolean edit(Clazz clazz){
        String sql = "update clazz set cla_name=?,cla_info=? where cla_id=?";
        return this.update(sql,clazz);
    }

    public boolean del(Integer id){
        String sql = "delete from clazz where cla_id=?";
        return this.delById(sql,id);
    }

    public List<Clazz> findAll(){
        String sql = "select cla_id,cla_name,cla_info,create_time from clazz ";
        return this.queryList(sql,Clazz.class);
    }

    /**
     * 总页数
     * @param search 查询条件
     * @return
     */
    public Integer pageCount(String search,Integer pageSize){
        Integer pageCount = 0;
        try {
            con = DBUtil.getConnection();
            String sql = " select count(1) pageCount from clazz ";
            if(StringUtils.isNotEmpty(search)){
                sql += " where cla_id like ? or cla_name like ? or cla_info like ? ";
            }
            ps = con.prepareStatement(sql);
            if(StringUtils.isNotEmpty(search)){
                ps.setString(1,'%'+search+'%');
                ps.setString(2,'%'+search+'%');
                ps.setString(3,'%'+search+'%');
            }
            rs = ps.executeQuery();
            if(rs.next()){
                pageCount = rs.getInt("pageCount");
                pageCount = (int)Math.ceil((double)pageCount/(double) pageSize);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.closeAll(rs,ps,con);
        }
        return pageCount;
    }

    public List<Clazz> page(String search,Integer pageIndex,Integer pageSize){
        List<Clazz> list = new ArrayList<>();
        try {
            con = DBUtil.getConnection();
            String sql = " select cla_id,cla_name,cla_info,create_time from clazz ";
            if(StringUtils.isNotEmpty(search)){
                sql += " where cla_id like ? or cla_name like ? or cla_info like ? ";
            }
            sql += " limit ?,? ";
            ps = con.prepareStatement(sql);
            if(StringUtils.isNotEmpty(search)){
                ps.setString(1,'%'+search+'%');
                ps.setString(2,'%'+search+'%');
                ps.setString(3,'%'+search+'%');
                ps.setInt(4,(pageIndex-1)*pageSize);
                ps.setInt(5,pageSize);
            }else{
                ps.setInt(1,(pageIndex-1)*pageSize);
                ps.setInt(2,pageSize);
            }
            rs = ps.executeQuery();
            Clazz clazz = null;
            while (rs.next()){
                clazz = Clazz.builder().claId(rs.getInt("cla_id")).claName(rs.getString("cla_name"))
                        .claInfo(rs.getString("cla_info")).createTime(rs.getTimestamp("create_time").toLocalDateTime())
                        .build();
                list.add(clazz);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.closeAll(rs,ps,con);
        }
        return list;
    }

    public Clazz findById(Integer id){
        String sql = "select cla_id,cla_name,cla_info,create_time from clazz where cla_id=?";
        return this.queryOne(sql,Clazz.class,id);
    }

    public Clazz findClassByName(String claName) {
        String sql = "select cla_id,cla_name,cla_info,create_time from clazz where cla_name=?";
        return this.queryOne(sql,Clazz.class,claName);
    }
}
