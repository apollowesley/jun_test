package pers.man.quickdevcore.system.persistence.user;

import org.springframework.stereotype.Repository;
import pers.man.quickdevcore.system.entity.user.User;
import pers.man.quickdevcore.system.persistence.BaseMapper;

/**
 * @author MAN
 * @version 1.0
 * @date 2020-04-12 17:32
 * @project quick-dev
 */
@Repository
public interface UserMapper extends BaseMapper<User,String>{

}
