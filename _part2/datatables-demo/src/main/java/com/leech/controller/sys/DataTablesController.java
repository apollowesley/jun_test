package com.leech.controller.sys;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.leech.controller.base.BaseController;
import com.leech.model.DatatablesBean;
import com.leech.pagemodel.base.datatables.DataSet;
import com.leech.pagemodel.base.datatables.DatatablesResponse;
import com.leech.pagemodel.base.datatables.PageCriterias;
import com.leech.service.DatatablesService;

@Controller
@RequestMapping("/dataTables")
public class DataTablesController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger LOG = Logger.getLogger(DataTablesController.class);
	
	@Autowired
	private DatatablesService datatablesService;
	
	@RequestMapping("/manager.do")
	public String manager(){
		return "/modules/sys/testdatables_01";
	}
	
	@RequestMapping("/datatables.do")
	@ResponseBody
	public DatatablesResponse<DatatablesBean> list(PageCriterias pcs) {
		//LOG.info(JSON.toJSONString(pcs));
		DataSet<DatatablesBean> dataSet = datatablesService.findDatatablesWithPageCriterias(pcs);
		DatatablesResponse<DatatablesBean> resp = DatatablesResponse.build(dataSet, pcs);
		LOG.info(JSON.toJSONString(resp));
		return resp;
	}
}
