package com.flowable.demo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.flowable.engine.DynamicBpmnService;
import org.flowable.engine.FormService;
import org.flowable.engine.HistoryService;
import org.flowable.engine.IdentityService;
import org.flowable.engine.ManagementService;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.ProcessEngines;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.form.FormProperty;
import org.flowable.engine.form.FormType;
import org.flowable.engine.form.StartFormData;
import org.flowable.engine.form.TaskFormData;
import org.flowable.engine.impl.form.DateFormType;
import org.flowable.engine.impl.form.EnumFormType;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.DeploymentBuilder;
import org.flowable.engine.runtime.ProcessInstance;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:flowable-context.xml")
public class Demo {

	private ProcessEngine processEngine;
	private TaskService taskService;
	private RuntimeService runtimeService;
	private RepositoryService repositoryService;
	private HistoryService historyService;
	private DynamicBpmnService dynamicBpmnService;
	private FormService formService;
	private IdentityService identityService;
	private ManagementService managementService;
	private ProcessEngineConfiguration processEngineConfiguration;

	@Before
	public void testProcessEngine() {
		processEngine = ProcessEngines.getDefaultProcessEngine();
		System.out.println("流程引擎类：" + processEngine);

		taskService = processEngine.getTaskService();
		System.out.println(taskService);

		runtimeService = processEngine.getRuntimeService();
		System.out.println(taskService);

		repositoryService = processEngine.getRepositoryService();
		System.out.println(repositoryService);

		historyService = processEngine.getHistoryService();
		System.out.println(historyService);

		dynamicBpmnService = processEngine.getDynamicBpmnService();
		System.out.println(dynamicBpmnService);

		formService = processEngine.getFormService();
		System.out.println(formService);

		identityService = processEngine.getIdentityService();
		System.out.println(identityService);

		managementService = processEngine.getManagementService();
		System.out.println(managementService);

		String name = processEngine.getName();
		System.out.println("流程引擎的名称： " + name);

		processEngineConfiguration = processEngine.getProcessEngineConfiguration();
		System.out.println(processEngineConfiguration);

	}

	/**
	 * 关闭流程引擎
	 */
	@After
	public void close() {
		processEngine.close();
	}

	/**
	 * classpath方式部署 涉及三张表：ACT_RE_PROCDEF,ACT_RE_DEPLOYMENT,ACT_GE_BYTEARRAY
	 */
	@Test
	public void deploy() {
		DeploymentBuilder deploymentBuilder = repositoryService.createDeployment().category("formTask").key("formTask")
				.name("动态表单").addClasspathResource("动态表单.bpmn20.xml");
		Deployment deploy = deploymentBuilder.deploy();

		System.out.println("请假流程部署,流程ID: " + deploy.getId());
	}

	/**
	 * 获取开始节点表单信息
	 */
	@Test
	public void getStartFormData() {
		String processDefinitionId = "dynamicform:1:4";
		StartFormData startFormData = formService.getStartFormData(processDefinitionId);
		System.out.println(startFormData.getProcessDefinition());
		System.out.println(startFormData.getDeploymentId());
		System.out.println(startFormData.getFormKey());

		List<FormProperty> formProperties = startFormData.getFormProperties();
		for (FormProperty fm : formProperties) {
			System.out.println("############################");
			System.out.println(fm.getId());
			System.out.println(fm.getName());
			FormType formType = fm.getType();
			System.out.println(formType);
			String key = "";
			if (formType instanceof EnumFormType) {
				key = "values";
			} else if (formType instanceof DateFormType) {
				key = "datePattern";
			}
			Object information = formType.getInformation(key);
			System.out.println("information:" + information);
			System.out.println(fm.getValue());
			System.out.println("############################");
		}

	}

	/**
	 * 启动流程实例：表单流程也可以用这种方式启动
	 */
	@Test
	public void startProcessInstanceByKey() {
		String processDefinitionKey = "dynamicform";
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey);
		System.out.println(processInstance.getId() + "," + processInstance.getActivityId());
	}

	/**
	 * 启动表单流程
	 */
	@Test
	public void submitStartFormData() {
		String processDefinitionId = "dynamicform:1:4";
		Map<String, String> properties = new HashMap<String, String>();
		properties.put("start_time",getCurrentDate());
		properties.put("end_time", getCurrentDate());
		properties.put("days", "6");
		properties.put("reason", "旅游");
		formService.submitStartFormData(processDefinitionId, properties);
	}
	/**
	 * 保存并数据，但不会完成任务
	 */
	@Test
	public void saveFormData() {
		String taskId = "5013";
		Map<String, String> properties = new HashMap<String, String>();
		properties.put("start_time",getCurrentDate());
		properties.put("end_time", getCurrentDate());
		properties.put("day", "3");
		properties.put("reason", "去玩玩1111");
		formService.saveFormData(taskId, properties);
	}
	/**
	 * 保存并完成任务
	 */
	@Test
	public void submitTaskFormData() {
		String taskId = "10006";
		Map<String, String> properties = new HashMap<String, String>();
		properties.put("start_time",getCurrentDate());
		properties.put("end_time", getCurrentDate());
		properties.put("day", "20");
		properties.put("reason", "去玩玩");
		formService.submitTaskFormData(taskId, properties);
	}
	/**
	 * 获取任务表单信息
	 */
	@Test
	public void getTaskFormData() {
		String taskId = "5013";
		TaskFormData taskFormData = formService.getTaskFormData(taskId);
		
		List<FormProperty> formProperties =		taskFormData.getFormProperties();

		for (FormProperty fm : formProperties) {
			System.out.println("############################");
			System.out.println(fm.getId());
			System.out.println(fm.getName());
			FormType formType = fm.getType();
			System.out.println(formType);
			String key = "";
			if (formType instanceof EnumFormType) {
				key = "values";
			} else if (formType instanceof DateFormType) {
				key = "datePattern";
			}
			Object information = formType.getInformation(key);
			System.out.println("information:" + information);
			System.out.println(fm.getValue());
			System.out.println("############################");
		}
		System.out.println(taskFormData.getTask().getName());
	}
	
	public  String getCurrentDate(){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        String s = simpleDateFormat.format(new Date());

        return  s;
    }

}