

------

环境：

[jkd8+]()

[mysql5.6+]()

**动态表单是通过Form properties来创建表单字段。**

## 一、流程表单实例启动

启动方式有两种：

- 使用runtimeService方式启动：

```java
runtimeService.startProcessInstanceByKey(processDefinitionKey)
```

- 使用formService方式启动

```
String processDefinitionId = "dynamicForm:1:5004";

Map<String, String> properties = new HashMap<String, String>();
properties.put("start_time",getCurrentDate());
properties.put("end_time", getCurrentDate());
properties.put("days", "6");
properties.put("reason", "旅游");
formService.submitStartFormData(processDefinitionId, properties);
```



## 二、修改数据配置

[将flowable-context.xml中的mysql改为自己的相关配置]()

## 三、审批

- 使用formService的submitTaskFormData方法完成审批

```java
String taskId = "12513";
Map<String, String> properties = new HashMap<String, String>();
properties.put("start_time",getCurrentDate());
properties.put("end_time", getCurrentDate());
properties.put("days", "10");
properties.put("reason", "去玩玩");
formService.submitTaskFormData(taskId, properties);
```



 

## 四、实践测试



- 运行demo
- 查看数据库表



