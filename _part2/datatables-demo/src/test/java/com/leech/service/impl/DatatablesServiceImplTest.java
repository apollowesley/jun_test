package com.leech.service.impl;

import org.apache.log4j.Logger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.leech.model.DatatablesBean;
import com.leech.pagemodel.base.datatables.DataSet;
import com.leech.pagemodel.base.datatables.PageCriterias;
import com.leech.service.DatatablesService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml", "classpath:spring-mybatis.xml" })
public class DatatablesServiceImplTest {
	private static final Logger LOG = Logger.getLogger(DatatablesServiceImplTest.class);

	@Autowired
	private DatatablesService datatablesService;

	@Test
	public void testFindPayTransBeansWithPageCriterias() {
		PageCriterias pcs = new PageCriterias();
		pcs.setDraw(1);
		pcs.setStart(0);
		pcs.setLength(10);
		pcs.setOrderField("0");
		pcs.setSearchValue("");
		pcs.setOrderValue("DESC");
		
		DataSet<DatatablesBean> ds = datatablesService.findDatatablesWithPageCriterias(pcs);
		
		LOG.info(ds.getRows().size()+";"+ds.getTotalRecords());
	}

}
