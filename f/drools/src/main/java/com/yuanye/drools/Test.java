/**
 * @author cj 2016年9月21日上午10:39:41
 */
package com.yuanye.drools;

import java.util.ArrayList;

import org.drools.examples.helloworld.HelloWorldExample.Message;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class Test {
	public static void main(String[] args) {
		action();
	}

	public static void action() {
		//生成KieSession
		KieServices ks = KieServices.Factory.get();
		KieContainer kc = ks.getKieClasspathContainer();
		KieSession ksession = kc.newKieSession("TestKS");
		//KieSession配置
		ksession.setGlobal("list", new ArrayList<Object>());
//		ksession.addEventListener(new DebugAgendaEventListener());
//		ksession.addEventListener(new DebugRuleRuntimeEventListener());
		//生成Fact对象
		final Message message = new Message();
		message.setMessage("Hello World");
		message.setStatus(Message.HELLO);
		//启动规则引擎
		ksession.insert(message);
		ksession.fireAllRules();
		System.out.println(ksession.getFactHandle(message));
		ksession.dispose();
		System.out.println(message.getMessage());
	}
}