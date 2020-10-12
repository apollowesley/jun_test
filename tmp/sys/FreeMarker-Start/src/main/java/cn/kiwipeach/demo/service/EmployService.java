package cn.kiwipeach.demo.service;


import cn.kiwipeach.demo.domain.Employ;
import cn.kiwipeach.demo.response.PageResultResponse;

import java.math.BigDecimal;
import java.util.List;

/**
 * Create Date: 2017/11/01
 * Description: 员工管理相关接口服务
 *
 * @author kiwipeach [1099501218@qq.com]
 */
public interface EmployService {
    /**
     * 查询员工信息
     * @param empno 员工编号
     * @return 员工信息
     */
    Employ queryEmploy(BigDecimal empno);

    PageResultResponse<Employ> queryEmployInfo(String job,int pageNo,int pageSize);

}
