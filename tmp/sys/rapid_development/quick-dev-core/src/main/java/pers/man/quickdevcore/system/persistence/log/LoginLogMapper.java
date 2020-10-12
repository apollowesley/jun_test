package pers.man.quickdevcore.system.persistence.log;

import org.springframework.stereotype.Repository;
import pers.man.quickdevcore.system.entity.log.LoginLog;
import pers.man.quickdevcore.system.persistence.BaseMapper;

/**
 * @author MAN
 * @version 1.0
 * @date 2020-04-02 20:07
 * @project quick-dev
 */
@Repository
public interface LoginLogMapper extends BaseMapper<LoginLog, String> {

}
