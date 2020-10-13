package org.iartisan.admin.template.controller.bpm.rest;


import org.flowable.idm.engine.impl.persistence.entity.UserEntityImpl;
import org.flowable.ui.common.model.UserRepresentation;
import org.flowable.ui.common.security.SecurityUtils;
import org.iartisan.runtime.bean.Pagination;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * BPM account
 *
 * @author King
 * @since 2018/6/28
 */
@RestController
@RequestMapping("bpm/account")
public class BpmAccountRestController {

    @GetMapping("get")
    public UserRepresentation get(Pagination page) {
        UserEntityImpl fUser = new UserEntityImpl();
        fUser.setId("admin");
        fUser.setFirstName("Test");
        fUser.setLastName("Administrator");
        fUser.setEmail("admin@flowable.org");
        UserRepresentation userRepresentation = new UserRepresentation(fUser);
        SecurityUtils.assumeUser(fUser);
        return userRepresentation;
    }

}
