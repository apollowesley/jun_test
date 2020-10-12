package com.jfast.web.common.core.base;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageRowBounds;
import com.jfast.web.common.core.model.Result;
import com.jfast.web.common.core.utils.ObjectUtils;
import com.jfast.web.common.core.utils.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 业务层父类
 * @author zengjintao
 * @version 1.0
 * @create_at 2019/10/2 16:20
 */
public abstract class BaseService<M extends BaseMapper> {

    @Autowired
    protected M mapper;
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 分页查询
     * @param params
     * @return
     */
    public Result<Map> pagination(Map params) {
        Result result = null;
        try {
            Integer offset = PageRowBounds.NO_ROW_OFFSET;
            Integer limit = PageRowBounds.NO_ROW_LIMIT;

            if (ObjectUtils.isNotEmpty(params.get("offset"))) {
                offset = Integer.valueOf((String) params.get("offset"));
            }
            if (ObjectUtils.isNotEmpty(params.get("limit"))) {
                limit = Integer.valueOf((String) params.get("limit"));
            }
            Page<M> page = PageHelper.offsetPage(offset, limit);
            List<M> dataList = mapper.queryList(params);
            Map dataMap = new HashMap<>();
            dataMap.put("dataList", dataList);
            dataMap.put("total", page.getTotal());
            result = new Result(ResultCode.SUCCESS, dataMap);
        } catch (Exception e) {
            result = new Result(ResultCode.FAIL);
            logger.error("获取数据异常", e);
        }
        return result;
    }


    /**
     * 添加数据
     * @param modelMap
     * @return
     */
    public int save(Map modelMap) {
        return mapper.save(modelMap);
    }

    /**
     * 更新数据
     * @param modelMap
     * @return
     */
    public int update(Map modelMap) {
        return mapper.update(modelMap);
    }

    public List<Map> queryList(Map params) {
        return mapper.queryList(params);
    }
}
