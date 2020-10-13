package org.iartisan.admin.template.controller.bpm.rest;


import org.flowable.engine.repository.Model;
import org.iartisan.admin.template.service.bpm.ModelManagement;
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
 * @since 2018/6/28
 */
@RestController
@RequestMapping("bpm/model")
public class BpmModelRestController extends BaseController implements ISupportRestController<Model> {

    @Autowired
    private ModelManagement modelManagement;

    @PostMapping(ReqContants.REQ_QUERY_PAGE_DATA)
    public WebR queryPageData(Pagination page) {
        try {
            PageWrapper<Model> pageData = modelManagement.queryModelPage(page);
            WebR webR = new WebR();
            webR.setData(pageData.getRows());
            return webR;
        } catch (Exception e) {
            logger.error("", e);
            WebR r = new WebR();
            r.isError(e.getMessage());
            return r;
        }
    }

    @Override
    public WebR deleteData(String keyId) {
        return null;
    }

    @Override
    public WebR modifyData(Model model) {
        return null;
    }

    @Override
    public WebR addData(Model model) {
        return null;
    }
}
