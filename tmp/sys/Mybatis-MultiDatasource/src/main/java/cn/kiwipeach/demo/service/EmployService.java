package cn.kiwipeach.demo.service;


import cn.kiwipeach.demo.domain.Employ;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Create Date: 2017/11/01
 * Description: 员工管理相关接口服务
 *
 * @author kiwipeach [1099501218@qq.com]
 */
public interface EmployService {
    /**
     * 查询员工信息
     *
     * @param empno 员工编号
     * @return 员工信息
     */
    Employ queryEmploy(BigDecimal empno);

    /**
     * 测试多数据源切换
     *
     * @param empno 员工编号
     * @return 返回员工信息
     */
    Map<String, Employ> testMulityDatasource(String empno);


    /**
     * 测试多数据源环境的事务
     *
     * @param empno 员工编号
     * @param name  员工姓名
     * @return 返回修改结果
     */
    int testMulityDatasourceTransation(String empno, String name);

}
