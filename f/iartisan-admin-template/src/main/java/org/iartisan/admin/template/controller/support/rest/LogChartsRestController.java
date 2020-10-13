package org.iartisan.admin.template.controller.support.rest;

import org.iartisan.admin.template.service.entity.EChartEntity;
import org.iartisan.admin.template.service.query.LogQueryService;
import org.iartisan.runtime.web.WebR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *
 * @author King
 * @since 2018/4/17
 */
@RestController
@RequestMapping("logCharts")
public class LogChartsRestController {

    @Autowired
    private LogQueryService logQueryService;

    @PostMapping("getCharts")
    public WebR getCharts() {
        WebR r = new WebR();
        List<EChartEntity> data = logQueryService.getLogCharts();
        r.setData(data);
        return r;
    }
}
