package org.iartisan.admin.template.controller.support.rest;

import org.iartisan.admin.template.service.entity.LogEntity;
import org.iartisan.admin.template.service.query.LogQueryService;
import org.iartisan.runtime.bean.PageWrapper;
import org.iartisan.runtime.bean.Pagination;
import org.iartisan.runtime.web.WebR;
import org.iartisan.runtime.web.contants.ReqContants;
import org.iartisan.runtime.web.controller.BaseController;
import org.iartisan.runtime.web.controller.ISupportRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *
 * @author King
 * @since 2018/4/17
 */
@RestController
@RequestMapping("log")
public class LogRestController extends BaseController implements ISupportRestController<LogEntity> {

    @Autowired
    private LogQueryService logQueryService;

    @Override
    public WebR deleteData(String keyId) {
        return null;
    }

    @Override
    public WebR modifyData(LogEntity logEntity) {
        return null;
    }

    @Override
    public WebR addData(LogEntity logEntity) {
        return null;
    }


    @PostMapping(ReqContants.REQ_QUERY_PAGE_DATA)
    public WebR queryPageData(Pagination page) {
        PageWrapper<LogEntity> pageData = logQueryService.getAllPageData(page);
        WebR webR = new WebR(pageData.getPage());
        webR.setData(pageData.getRows());
        return webR;
    }
}
