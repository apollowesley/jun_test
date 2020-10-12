package com.kiss.jbpm.service;

import java.util.ArrayList;
import java.util.List;

import org.drools.definition.process.Process;
import org.jbpm.process.audit.ProcessInstanceLog;
import org.jbpm.task.Status;
import org.jbpm.task.query.TaskSummary;

import com.kiss.jbpm.entity.JbpmInfo;

public interface IJbpmService {
	
	
	/**
	 * create process
	 * @param processNames
	 * @param startProcessId
	 */
	void startProcess(List<String> processNames,String startProcessId);
	 
	void connect();
	
	/**
	 * 初始化服务器以及添加用户和组
	 */
	void initMinaServer();
	
	/**
	 * 新增组
	 * @param groupId
	 */
	void addGroup(String groupId);
	
	/**
	 * 新增用户
	 * @param userId
	 */
	void addUser(String userId);
	
	/**
	 * 获取指定用户的流程信息
	 * @param userId
	 * @return
	 */
	List <TaskSummary> getAssignedPersonalTasks(String userId);
	
	/**
	 * 获取指定用户以及状态的流程信息
	 * @param userId
	 * @return
	 */
	List <TaskSummary> getAssignedPersonalTasks(String userId,List<Status> status);
	
	/**
	 * 获取指定用户 指定组以及指定状态的流程信息
	 * @param userId
	 * @param group
	 * @return
	 */
	ArrayList<Object> getAssignedGroupTasks(String userId,String group,List<Status> status);
	
	
	/**
	 * startTask
	 * @param taskId
	 * @param userId
	 */
	void startTask(long taskId, String userId);
	/**
	 * stopTask
	 * @param taskId
	 * @param userId
	 */
	void stopTask(long taskId, String userId);
	/**
	 * completeTask
	 * @param taskId
	 * @param userId
	 */
	void completeTask(long taskId, String userId);
	/**
	 * assignTask
	 * @param taskId
	 * @param newUserId
	 * @param userId
	 */
	void assignTask(long taskId, String newUserId, String userId);
	/**
	 * claimTask
	 * @param taskId
	 * @param userId
	 * @param group
	 */
	void claimTask(long taskId, String userId,String group);
	/**
	 * getProcessStatus
	 * @return
	 */
	List<ProcessInstanceLog> getProcessStatus();
	/**
	 * getProcessObject
	 * @param processDefId
	 * @param processName
	 * @return
	 */
	Process getProcessObject(String processDefId, String processName);
	
	/**
	 * abortProcessInstance
	 * @param processInstanceId
	 */
	void abortProcessInstance(long processInstanceId);
	
	 /**
	  * getJbpmInfo
	 * @param processDefId
	 * @return
	 */
	JbpmInfo getJbpmInfoByFileId(String processDefId);
}
