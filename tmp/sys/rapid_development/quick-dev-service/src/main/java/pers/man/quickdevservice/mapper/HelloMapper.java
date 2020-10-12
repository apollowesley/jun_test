package pers.man.quickdevservice.mapper;

import org.springframework.stereotype.Repository;
import pers.man.quickdevcore.system.persistence.BaseMapper;
import pers.man.quickdevservice.entity.DO.Hello;

/**
 * @author MAN
 * @version 1.0
 * @date 2020-04-03 15:15
 * @project quick-dev
 */
@Repository
public interface HelloMapper extends BaseMapper<Hello,String> {
}
