package cn.iutils.up.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.iutils.common.service.CrudService;
import cn.iutils.up.dao.UpDao;
import cn.iutils.up.entity.Up;

/**
* 支持表 Service层
* @author iutils.cn
* @version 1.0
*/
@Service
@Transactional(readOnly = true)
public class UpService extends CrudService<UpDao, Up> {

}
