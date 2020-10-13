package org.iartisan.admin.template.service.bpm;

import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Model;
import org.iartisan.runtime.bean.PageWrapper;
import org.iartisan.runtime.bean.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 流程部署
 *
 * @author King
 * @since 2018/6/28
 */
@Service
public class ModelManagement {

    @Autowired
    private RepositoryService repositoryService;

    public PageWrapper<Model> queryModelPage(Pagination page) {
        long total = repositoryService.createModelQuery().count();
        List<Model> result = repositoryService.createModelQuery().listPage(
                (page.getPageIndex() - 1) * page.getPageSize()
                , page.getPageSize());
        page.setTotalRecords((int) total);
        page.setPageIndex(page.getPageIndex() + 1);
        PageWrapper<Model> resultPage = new PageWrapper<>(page);
        resultPage.setRows(result);
        return resultPage;
    }
}
