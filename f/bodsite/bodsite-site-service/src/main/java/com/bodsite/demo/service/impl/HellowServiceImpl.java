package com.bodsite.demo.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bodsite.common.logger.Logger;
import com.bodsite.common.logger.LoggerFactory;
import com.bodsite.demo.dao.DemoMapper;
import com.bodsite.demo.entity.Demo;
import com.bodsite.demo.service.HellowService;
import com.bodsite.demo.vo.DemoVo;
/**
 * 
* @Description: dome类
* @author bod
* @date 2016年12月9日 下午5:19:12 
*
 */
@Service("hellowService")
public class HellowServiceImpl implements HellowService {
	Logger logger = LoggerFactory.getLogger(HellowServiceImpl.class);
	@Autowired
	private DemoMapper demoMapper;
	@Override
	public String sayHellow(String name) {
		logger.info("receive name:"+name);
		return "Hellow "+name;
	}
	
	/**
	 * 
	* @Description: 根据id查找
	* @param demoId
	* @author bod
	 */
	@Override
	//@Lock(key="findDemo",lockType=LOCK_TYPE.NX_LOCK_TIME)
	public DemoVo findDemo(Integer demoId){
		//HellowService h = (HellowService) SpringUtil.getBean("hellowService");
		//h.LockTwo();
		Demo demo = demoMapper.selectByPrimaryKey(demoId);
		if(demo==null){
			return null;
		}
		DemoVo d = new DemoVo();
		BeanUtils.copyProperties(demo, d);
		return d;
	}
	
	//@Lock(key="findDemo",lockType=LOCK_TYPE.NX_LOCK_TIME)
	public void LockTwo(){
		System.out.println(Thread.currentThread().getName()+"locktwo");
	}

	/**
	 * 
	* @Description: 插入实体
	* @param demoVo
	* @author bod
	 */
	@Transactional(readOnly=false,timeout=3000,propagation=Propagation.REQUIRED)
	public int insertDemo(DemoVo demoVo){
		Demo demo1 = demoMapper.selectByPrimaryKey(1);//测试读写分离事务
		Demo demo = new Demo();
		BeanUtils.copyProperties(demoVo, demo);
		return demoMapper.insert(demo);
	}

}
