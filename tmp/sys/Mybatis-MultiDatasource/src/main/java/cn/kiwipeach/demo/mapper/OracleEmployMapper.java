package cn.kiwipeach.demo.mapper;


import cn.kiwipeach.demo.annotation.Datasource;
import cn.kiwipeach.demo.domain.Employ;

import java.math.BigDecimal;

/**
 * Create Date: 2017/10/31
 * Description: 员工操作接口
 *
 * @author kiwipeach [1099501218@qq.com]
 */
@Datasource("oracleDataSource")
public interface OracleEmployMapper {

    Employ selectByPrimaryKey(BigDecimal empno);

}