package cn.iutils.down.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.iutils.common.service.CrudService;
import cn.iutils.down.dao.DownDao;
import cn.iutils.down.entity.Down;

/**
* 反对表 Service层
* @author iutils.cn
* @version 1.0
*/
@Service
@Transactional(readOnly = true)
public class DownService extends CrudService<DownDao, Down> {

}
