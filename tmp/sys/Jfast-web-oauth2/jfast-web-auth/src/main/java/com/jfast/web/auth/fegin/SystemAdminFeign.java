package com.jfast.web.auth.fegin;

import com.jfast.web.common.core.constants.Constants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2019/12/16 20:43
 */
@FeignClient(value = Constants.JFAST_WEB_API_ADMIN_SERVER, fallback = SystemAdminFeignImpl.class)
public interface SystemAdminFeign {

    @GetMapping("/admin/findByName")
    Map findByName(@RequestParam String userName);

    @GetMapping("/admin/test")
    Map test();
}
