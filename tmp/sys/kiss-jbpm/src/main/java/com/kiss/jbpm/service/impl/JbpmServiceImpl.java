package com.kiss.jbpm.service.impl;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.drools.KnowledgeBase;
import org.drools.SystemEventListenerFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.compiler.BPMN2ProcessFactory;
import org.drools.compiler.ProcessBuilderFactory;
import org.drools.container.spring.beans.persistence.DroolsSpringJpaManager;
import org.drools.container.spring.beans.persistence.DroolsSpringTransactionManager;
import org.drools.definition.process.Process;
import org.drools.impl.EnvironmentFactory;
import org.drools.io.ResourceFactory;
import org.drools.logger.KnowledgeRuntimeLogger;
import org.drools.logger.KnowledgeRuntimeLoggerFactory;
import org.drools.marshalling.impl.ProcessMarshallerFactory;
import org.drools.persistence.PersistenceContextManager;
import org.drools.persistence.TransactionManager;
import org.drools.persistence.jpa.JPAKnowledgeService;
import org.drools.runtime.Environment;
import org.drools.runtime.EnvironmentName;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.process.ProcessInstance;
import org.drools.runtime.process.ProcessRuntimeFactory;
import org.jbpm.bpmn2.BPMN2ProcessProviderImpl;
import org.jbpm.marshalling.impl.ProcessMarshallerFactoryServiceImpl;
import org.jbpm.process.audit.JPAProcessInstanceDbLog;
import org.jbpm.process.audit.JPAWorkingMemoryDbLogger;
import org.jbpm.process.audit.ProcessInstanceLog;
import org.jbpm.process.builder.ProcessBuilderFactoryServiceImpl;
import org.jbpm.process.instance.ProcessRuntimeFactoryServiceImpl;
import org.jbpm.process.workitem.wsht.SyncWSHumanTaskHandler;
import org.jbpm.task.Group;
import org.jbpm.task.Status;
import org.jbpm.task.User;
import org.jbpm.task.query.TaskSummary;
import org.jbpm.task.service.ContentData;
import org.jbpm.task.service.TaskClient;
import org.jbpm.task.service.TaskService;
import org.jbpm.task.service.TaskServiceSession;
import org.jbpm.task.service.local.LocalTaskService;
import org.jbpm.task.service.mina.MinaTaskClientConnector;
import org.jbpm.task.service.mina.MinaTaskClientHandler;
import org.jbpm.task.service.mina.MinaTaskServer;
import org.jbpm.task.service.persistence.TaskSessionSpringFactoryImpl;
import org.jbpm.task.service.responsehandlers.BlockingTaskOperationResponseHandler;
import org.jbpm.task.service.responsehandlers.BlockingTaskSummaryResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;
import com.kiss.constant.SystemConfig;
import com.kiss.jbpm.dao.IJbpmInfoDao;
import com.kiss.jbpm.entity.JbpmInfo;
import com.kiss.jbpm.exception.JBPMException;
import com.kiss.jbpm.service.IJbpmService;
import com.kiss.user.dao.IRoleDao;
import com.kiss.user.dao.IUserDao;
import com.kiss.user.entity.Role;
import com.kiss.user.service.ISystemService;

@Service("jbpmService")
@SuppressWarnings("deprecation")
public class JbpmServiceImpl implements IJbpmService{
	private static final Logger log = Logger.getLogger(JbpmServiceImpl.class);
	@Autowired
	private  EntityManager jbpmEM;
	@Autowired
	private  AbstractPlatformTransactionManager jbpmTxManager;
	@Autowired
	private LocalTaskService localTaskService;
	@Autowired
	private TaskSessionSpringFactoryImpl springTaskSessionFactory;
	@Autowired
    private TaskService taskService;
	private KnowledgeBase kbase;
	private static String ipAddress = "127.0.0.1";
	private static int port = 9123;
	private static TaskClient client;
	@Autowired
	private IUserDao userDao;
	@Autowired
	private IRoleDao roleDao;
	@Autowired
	private IJbpmInfoDao jbpmInfoDao;
	private TaskServiceSession taskSession;
	
	@Autowired
	private ISystemService systemService;
	public JbpmServiceImpl(){
		
	}
	/**
	 * @param processNames 流程配置文件名称
	 * @return
	 */
	public StatefulKnowledgeSession  getStatefulKnowledgeSession(List<String> processNames){
		StatefulKnowledgeSession ksession = null;
		try {
			Environment env = EnvironmentFactory.newEnvironment();  
			env.set(EnvironmentName.APP_SCOPED_ENTITY_MANAGER, jbpmEM);     
			env.set(EnvironmentName.CMD_SCOPED_ENTITY_MANAGER, jbpmEM);
			env.set("IS_JTA_TRANSACTION", false);
			env.set("IS_SHARED_ENTITY_MANAGER", true);
			readKnowledgeBase(processNames);
			TransactionManager transactionManager = new DroolsSpringTransactionManager(jbpmTxManager);
			env.set(EnvironmentName.TRANSACTION_MANAGER, transactionManager);
			PersistenceContextManager persistenceContextManager = new DroolsSpringJpaManager(env);
			env.set(EnvironmentName.PERSISTENCE_CONTEXT_MANAGER, persistenceContextManager);
			ksession = JPAKnowledgeService.newStatefulKnowledgeSession(kbase, null, env );
		} catch (Exception e) {
			throw new JBPMException("ksession创建失败",e);
		}
		return ksession;
	}
	
	/**
	 * @param sid
	 * @return
	 *//*
	public StatefulKnowledgeSession  getStatefulKnowledgeSessionById(int sid){
		StatefulKnowledgeSession ksession = null;
		try {
			Environment env = EnvironmentFactory.newEnvironment();  
			env.set(EnvironmentName.APP_SCOPED_ENTITY_MANAGER, jbpmEM);     
			env.set(EnvironmentName.CMD_SCOPED_ENTITY_MANAGER, jbpmEM);
			env.set("IS_JTA_TRANSACTION", false);
			env.set("IS_SHARED_ENTITY_MANAGER", true);
			TransactionManager transactionManager = new DroolsSpringTransactionManager(jbpmTxManager);
			env.set(EnvironmentName.TRANSACTION_MANAGER, transactionManager);
			PersistenceContextManager persistenceContextManager = new DroolsSpringJpaManager(env);
			env.set(EnvironmentName.PERSISTENCE_CONTEXT_MANAGER, persistenceContextManager);
			ksession = JPAKnowledgeService.loadStatefulKnowledgeSession(sid,kbase, null, env );
		} catch (Exception e) {
			throw new JBPMException("ksession创建失败",e);
		}
		return ksession;
	}*/
	
	/**
	 * KnowledgeBase
	 * @param processNames  流程配置文件名称
	 * @throws Exception
	 */
	public void readKnowledgeBase(List<String> processNames) throws Exception{
		log.info("Value of processNames : "+processNames);
		try{
			if(kbase == null){
				ProcessBuilderFactory.setProcessBuilderFactoryService(new ProcessBuilderFactoryServiceImpl());
				ProcessMarshallerFactory.setProcessMarshallerFactoryService(new ProcessMarshallerFactoryServiceImpl());
				ProcessRuntimeFactory.setProcessRuntimeFactoryService(new ProcessRuntimeFactoryServiceImpl());
				BPMN2ProcessFactory.setBPMN2ProcessProvider(new BPMN2ProcessProviderImpl());
				KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
				for(String processName : processNames){
					kbuilder.add(ResourceFactory.newClassPathResource(processName), ResourceType.BPMN2);
				}
				kbase = kbuilder.newKnowledgeBase();
			}
		}catch(Exception e){
			throw new JBPMException("kbase创建失败",e);
		}
	}
	
	public void connect(){
		try{
			if (client == null){
				client = new TaskClient(new MinaTaskClientConnector("org.drools.process.workitem.wsht.WSHumanTaskHandler", new MinaTaskClientHandler(SystemEventListenerFactory.getSystemEventListener())));
				boolean connected = client.connect(ipAddress, port);
				if (!connected){
					throw new IllegalArgumentException("task client 连接失败");
				}
			}
		}catch(Exception e){
			throw new JBPMException("client连接失败",e);
		}
	}

	@Override
	public void startProcess(List<String> processNames,
			String startProcessId) {
		StatefulKnowledgeSession ksession = getStatefulKnowledgeSession(processNames);
		SyncWSHumanTaskHandler humanTaskHandler = new SyncWSHumanTaskHandler(localTaskService, ksession);
		KnowledgeRuntimeLogger logger1 = KnowledgeRuntimeLoggerFactory.newThreadedFileLogger(ksession, "jbpm", 1000);
		humanTaskHandler.setLocal(true);
		humanTaskHandler.connect();
		ksession.getWorkItemManager().registerWorkItemHandler("Human Task", humanTaskHandler);
    	JPAWorkingMemoryDbLogger logger2 = new JPAWorkingMemoryDbLogger(ksession);
      	ProcessInstance processInstance = ksession.startProcess(startProcessId);
      	long processInstanceId = processInstance.getId();
      	log.info("Value of processInstanceId : "+processInstanceId);
		ksession.fireAllRules();
		logger2.dispose();
		logger1.close();
	}

	@Override
	public void initMinaServer() {
		taskSession =  springTaskSessionFactory.createTaskServiceSession();
	    List<Role> roles = roleDao.find("from Role where enable=0");
	    for(Role r:roles){
	    	 taskSession.addGroup(new Group(r.getRoleId()));
	    }
	    List<com.kiss.user.entity.User> users = userDao.find("from User where enable=0");
	    for(com.kiss.user.entity.User u:users){
	    	 taskSession.addUser(new User(u.getUserId()));
	    }
	    taskSession.addUser(new User("Administrator"));
        // Start Mina server for HT
        MinaTaskServer server = new MinaTaskServer(taskService);
        Thread thread = new Thread(server);
        thread.start();
        log.info("Mina Server started ..."); 
	}
	
	@Override
	public void addGroup(String groupId) {
		taskSession.addGroup(new Group(groupId));
	}
	
	@Override
	public void addUser(String userId) {
   	 	taskSession.addUser(new User(userId));
	}
	@Override
	public List<TaskSummary> getAssignedPersonalTasks(String userId) {
		connect();
		List<TaskSummary> tasks = null;
		try{
			BlockingTaskSummaryResponseHandler responseHandler = new BlockingTaskSummaryResponseHandler();
			client.getTasksOwned(userId,systemService.getMySystemValueByCode(SystemConfig.TASK_LAN_SUPPORT), responseHandler);
	        tasks = responseHandler.getResults();
		}
		catch(Exception e){
			new JBPMException("获取指定用户流程信息失败", e);
		}
		return tasks;
	}
	@Override
	public List<TaskSummary> getAssignedPersonalTasks(String userId,
			List<Status> status) {
		connect();
		List<TaskSummary> tasks = null;
		try{
			BlockingTaskSummaryResponseHandler responseHandler = new BlockingTaskSummaryResponseHandler();
			client.getTasksOwned(userId,status,getLan(),responseHandler);
	        tasks = responseHandler.getResults();
		}
		catch(Exception e){
			new JBPMException("获取指定用户及状态的流程信息失败", e);
		}
		return tasks;
	}
	
	public String getLan(){
		return systemService.getMySystemValueByCode(SystemConfig.TASK_LAN_SUPPORT);
	}
	
	@Override
	public ArrayList<Object> getAssignedGroupTasks(String userId, String group,List<Status> status) {
		connect();
		BlockingTaskSummaryResponseHandler summaryHandler = new BlockingTaskSummaryResponseHandler();
	    ArrayList<Object> groupTaskDetail = new ArrayList<Object>();
		List<TaskSummary> tasks = null; 
	    List<String> groups = new ArrayList<String>();
	    groups.add(group);
	    try{
		    client.getTasksAssignedAsPotentialOwner(userId, groups,getLan(), summaryHandler);
		    tasks = summaryHandler.getResults();
		    for(TaskSummary task : tasks){
		    	for(Status st:status){
		    		if(task.getStatus() == st){
				    	groupTaskDetail.add(task.getId());
				    	groupTaskDetail.add(task.getName());
				    	groupTaskDetail.add(task.getDescription());
				    	groupTaskDetail.add(group);
				    	groupTaskDetail.add(task.getCreatedOn());
				    	groupTaskDetail.add(task.getStatus());
				    	groupTaskDetail.add(task.getProcessInstanceId());
			    	}
		    	}	
		    }
	    }catch(Exception e){
			throw new JBPMException("获取指定用户以及组的流程失败", e);
		}
		return groupTaskDetail;
	}
	@Override
	public void startTask(long taskId, String userId){
		connect();
		BlockingTaskOperationResponseHandler responseHandler = new BlockingTaskOperationResponseHandler();
		try{
			client.start(taskId, userId, responseHandler);
			responseHandler.waitTillDone(5000);
		}catch(Exception e){
			throw new JBPMException("流程开始失败", e);
		}
	}
	@Override
	public void stopTask(long taskId, String userId){
		connect();
		BlockingTaskOperationResponseHandler responseHandler = new BlockingTaskOperationResponseHandler();
		try{
			client.stop(taskId, userId, responseHandler);
			responseHandler.waitTillDone(5000);
		}catch(Exception e){
			throw new JBPMException("流程停止失败", e);
		}
	}
	@Override
	public void completeTask(long taskId, String userId){
		connect();
		BlockingTaskOperationResponseHandler responseHandler = new BlockingTaskOperationResponseHandler();
		ContentData contentData = null;
		try{
			client.complete(taskId, userId, contentData, responseHandler);
			responseHandler.waitTillDone(5000);
		}catch(Exception e){
			throw new JBPMException("流程完成失败", e);
		}
	}
	@Override
	public void assignTask(long taskId, String newUserId, String userId){
		connect();
		BlockingTaskOperationResponseHandler responseHandler = new BlockingTaskOperationResponseHandler();
		try{
			if (StringUtils.isBlank(newUserId)){
				client.release(taskId, userId, responseHandler);
			}else{
				client.delegate(taskId, userId, newUserId, responseHandler);
			}
			responseHandler.waitTillDone(5000);
		}catch(Exception e){
			throw new JBPMException("流程分配失败", e);
		}
	}
	@Override
	public void claimTask(long taskId, String userId,String group){
		connect();
		List<String> groups = new ArrayList<String>();
		groups.add(group);
		BlockingTaskOperationResponseHandler responseHandler = new BlockingTaskOperationResponseHandler();
		try{
			client.claim(taskId, userId, groups, responseHandler);
			responseHandler.waitTillDone(5000);
		}catch(Exception e)
		{
			throw new JBPMException("流程认领失败", e);
		}
	}
	@Override
	public List<ProcessInstanceLog> getProcessStatus(){
		List<ProcessInstanceLog> processDetail = null;
		try{
			processDetail = JPAProcessInstanceDbLog.findProcessInstances();
		}catch(Exception e)
		{
			throw new JBPMException("获取所有流程状态失败", e);
		}
		return processDetail;
	}
	
	@Override
	public Process getProcessObject(String processDefId, String processName){
		ArrayList<String> processNameList = new ArrayList<String>();
		Process process = null;
		try{
			processNameList.add(processName);
			readKnowledgeBase(processNameList);
			process = kbase.getProcess(processDefId);
		}catch(Exception e){
			throw new JBPMException("获取process失败", e);
		}	
		return process;
	}
	@Override
	public void abortProcessInstance(long processInstanceId){
		try{
			String processDefId = JPAProcessInstanceDbLog.findProcessInstance(processInstanceId).getProcessId();
			JbpmInfo info = getJbpmInfoByFileId(processDefId);
			if(info==null){
				throw new JBPMException("流程相关信息不存在");
			}
			String processName = info.getJbpmFileName();
			ArrayList<String> processNames = new ArrayList<String>();
			processNames.add(processName);
			StatefulKnowledgeSession ksession = getStatefulKnowledgeSession(processNames);
			ksession.abortProcessInstance(processInstanceId);
		}catch(Exception e){
			throw new JBPMException("停止process失败", e);
		}
	}
	
	@Override
	public JbpmInfo getJbpmInfoByFileId(String processDefId){
		List<JbpmInfo> infos = jbpmInfoDao.find("from JbpmInfo where jbpmFileId = ?",processDefId);
		return infos.isEmpty()?null:infos.get(0);
	}
}
