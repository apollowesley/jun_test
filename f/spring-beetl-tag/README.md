#### 项目介绍
Beetl是一款6倍于Freemarker的超高性能的java模板引擎,来自于中国!本项目是基于beetl、Spring 5.X、Jdk8、Spring boot 2.X,的类似Spring的前端标签！
#### maven
```
<dependency>
	<groupId>cn.jeeweb</groupId>
	<artifactId>spring-beetl-tag</artifactId>
	<version>1.0</version>
</dependency>

<dependency>
	<groupId>com.ibeetl</groupId>
	<artifactId>beetl-framework-starter</artifactId>
	<version>${beetl.version}</version>
</dependency>
```
#### Spring Boot 使用方式

```
@Bean
public BeetlTagFactoryManager beetlTagFactoryManager(){
	BeetlTagFactoryManager beetlTagFactoryManager = new BeetlTagFactoryManager();
	return beetlTagFactoryManager;
}

BeetlGroupUtilConfiguration beetlGroupUtilConfiguration = new BeetlGroupUtilConfiguration();
//其他配置
beetlGroupUtilConfiguration.setTagFactorys(beetlTagFactoryManager.loadFactorys());  // 添加标签

```

#### 标签使用模板（类似Spring form），主要标签签名需要加入符号“#”；如：#form：form、#spring:message，
[详细的标签文档地址](https://gitee.com/dataact/spring-beetl-tag/wikis)
[这里是Spring boot版本的测试代码](https://gitee.com/dataact/jeeweb-beetl-tag-test)
```
<#spring:message code="sys.site.description" arguments="${platformName}"/>

<#form:form id="testForm" modelAttribute="table" method="post" class="form-horizontal">
    <#form:checkboxes path="tableName" items="${tableList}" itemValue="id" itemLabel="tableName" /><br/>
    <#form:radiobuttons path="tableName" items="${tableList}" itemValue="id" itemLabel="tableName"/><br/>
    <#form:select path="tableName"><br/>
        <#form:option  value="1">财务部</#form:option>
        <#form:option value="1">财务部</#form:option>
        <#form:option value="2">开发部</#form:option>
        <#form:option value="3">销售部</#form:option>
        <#form:options items="${tableList}" itemValue="id" itemLabel="tableName" />
    </#form:select>
    <#form:input path="tableName" nested="true" /><br />
    <#form:input path="tableName" nested="false"  defaultValue="你好斯蒂芬斯蒂芬"/><br />
    <#form:checkboxes path="tableName" dict="sf" /><br/>
    <#form:radiobuttons path="tableName" dict="sf" /><br/>
</#form:form>

```
#### 使用字典（需要使用了ehcache2.*）


```

import cn.jeeweb.beetl.tags.dict.Dict;
import cn.jeeweb.beetl.tags.dict.DictUtils;
import cn.jeeweb.beetl.tags.dict.InitDictable;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Configuration
public class DictConfig implements InitDictable{
    @Override
    public Map<String, List<Dict>> initDict() {
        //初始化页面数据字典
        List<Dict> sfList = new ArrayList<>();
        sfList.add(new Dict("是", "1"));
        sfList.add(new Dict("否", "0"));
        Map<String, List<Dict>> dictMap = new HashMap<>();
        dictMap.put("sf",sfList);
        return dictMap;
    }
}

```