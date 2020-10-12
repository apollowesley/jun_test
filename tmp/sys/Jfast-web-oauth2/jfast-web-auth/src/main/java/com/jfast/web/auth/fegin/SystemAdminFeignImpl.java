package com.jfast.web.auth.fegin;

import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2019/12/16 20:46
 */
@Component
public class SystemAdminFeignImpl implements SystemAdminFeign {

    @Override
    public Map findByName(String userName) {
        return null;
    }

    @Override
    public Map test() {
        return null;
    }
}
