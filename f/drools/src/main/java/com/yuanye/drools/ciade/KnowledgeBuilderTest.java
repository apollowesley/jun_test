/**
 * @author cj 2016年9月21日下午4:43:59
 */
package com.yuanye.drools.ciade;

import java.util.Collection;

import org.kie.api.io.ResourceType;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderError;
import org.kie.internal.builder.KnowledgeBuilderErrors;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.definition.KnowledgePackage;
import org.kie.internal.io.ResourceFactory;
/**
 * http://www.cnblogs.com/ciade/p/5234794.html
 * KnowledgeBuilder 的作用就是用来在业务代码当中收集已经编写好的规则，然后对这些规则文件进行编译，最终产生一批编译好的规则包（KnowledgePackage）给其它的应用程序使用。
 * 创建 KnowledgeBuilder 对象使用的是 KnowledgeBuilderFactory的 newKnowledgeBuilder方法
 * KnowledgeBuilder在编译规则的时候可以通过其提供的 hasErrors()方法得到编译规则过程中发现规则是否有错误，如果有的话通过其提供的 getErrors()方法将错误打印出来，以帮助我们找到规则当中的错误信息。
 */
public class KnowledgeBuilderTest {
	public static void main(String[] args) {
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		kbuilder.add(ResourceFactory.newClassPathResource("test.drl", KnowledgeBuilderTest.class), ResourceType.DRL);
		KnowledgeBuilderErrors errors = kbuilder.getErrors();
		if (errors.size() > 0) {
			for (KnowledgeBuilderError error : errors) {
				System.err.println(error);
			}
			throw new IllegalArgumentException("Could not parse knowledge.");
		}
		Collection<KnowledgePackage> kpackage = kbuilder.getKnowledgePackages();// 产生规则包的集合
		
		kpackageDetails(kpackage);
	}
	public static void kpackageDetails(Collection<KnowledgePackage> kpackage) {
		for(KnowledgePackage tmp:kpackage){
			print("KnowledgePackage.calss", tmp.getClass());
			print("KnowledgePackage.FactTypes", tmp.getFactTypes());
			print("KnowledgePackage.FunctionNames", tmp.getFunctionNames());
			print("KnowledgePackage.Name", tmp.getName());
			print("KnowledgePackage.Rules", tmp.getRules());
			print("KnowledgePackage.Processes", tmp.getProcesses());
		}
	}
	public static void print(String name,Object value){
		System.out.println(name+":"+value);
	}
}