package com.bodsite.demo.consumer;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bodsite.common.logger.Logger;
import com.bodsite.common.logger.LoggerFactory;
import com.bodsite.demo.service.HellowService;
import com.bodsite.demo.vo.DemoVo;
import com.bodsite.search.service.SearchService;
import com.bodsite.search.vo.DemoResultVo;
import com.bodsite.search.vo.DemoSearchVo;
import com.bodsite.search.vo.SolrPage;

public class DemoConsumer {
	private static Logger logger = LoggerFactory.getLogger(DemoConsumer.class);
	private ClassPathXmlApplicationContext context;

	public void setUp() {
		String[] files = { "classpath*:/spring/application-context.xml" };
		context = new ClassPathXmlApplicationContext(files);
	}
	
	public void searchHandler(){
		SearchService searchService = (SearchService) context.getBean("searchService");
		DemoSearchVo searchVo = new DemoSearchVo();
		searchVo.initDefultPage();
		searchVo.setTitle("天");
		SolrPage<DemoResultVo> solrPage = searchService.findDemoSolr(searchVo);
		for(DemoResultVo demoResultVo:solrPage){
			System.out.println(demoResultVo.getSkuId()+" "+demoResultVo.getTitle());
		}
		//DemoVo demoVo = hellowService.findDemo(1);
		//logger.info(demoVo.toString());
	}
	
	public void handler(){
		HellowService hellowService = (HellowService) context.getBean("hellowService");
		DemoVo demoVo = hellowService.findDemo(1);
		logger.info(demoVo.toString());
	}
	
	public void insertHandler(){
		HellowService hellowService = (HellowService) context.getBean("hellowService");
		DemoVo demoVo = new DemoVo();
		demoVo.setAge(18);
		demoVo.setName("依依");
		demoVo.setSex(1);
		int k = hellowService.insertDemo(demoVo);
		logger.info(String.valueOf(k));
	}
	
    public void setDown() {
    	context.stop();
    	context.close();
    }

	public static void main(String[] args) {
		DemoConsumer demoConsumer = new DemoConsumer();
		demoConsumer.setUp();
		demoConsumer.insertHandler();
		demoConsumer.setDown();
	}
}
