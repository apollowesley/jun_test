package cn.kiwipeach.demo.mapper;


import cn.kiwipeach.demo.domain.Employ;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * Create Date: 2017/10/31
 * Description: 员工操作接口
 *
 * @author kiwipeach [1099501218@qq.com]
 */
public interface EmployMapper {
    int deleteByPrimaryKey(BigDecimal empno);

    int insert(Employ record);

    int insertSelective(Employ record);

    Employ selectByPrimaryKey(BigDecimal empno);

    int updateByPrimaryKeySelective(Employ record);

    int updateByPrimaryKey(Employ record);

    List<Employ> selectByJob(String job);


    List<Employ> selectByJobWithAnnotation(
            @Param("employ") Employ employ,
            @Param("pageNumKey") int pageNum,
            @Param("pageSizeKey") int pageSize
    );
}