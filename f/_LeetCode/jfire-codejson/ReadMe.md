# Jfire-codejson���

��ǩ�� ������� Json

---
[TOC]
##���˵��
Jfier-codejson��ĿǰΪֹ**������ǿ��json���**�����л�����**��Խfastjson50%**�������л�����**������**fastjson.
������ǿ����ͬʱ�߱��ǳ��򵥵�API�ӿڡ����ڳ�����������л��ͷ����л�����ֻ��һ���ӿڵ��ã��ǳ������á�ͬʱ��ܻ��߱�����ģʽ�����ͬһ����������������ݵ������ʽ
##��������
����������¼�����
```java
public class Person
{
    private String name;
    private int age;
    private boolean boy;
}
public class Home
{
    privaet String name;
    private Person host;
    private float height;
    private float weidth;
}
public static void main(String args[])
{
    Home home = new Home();
    home.setPerson(new Person());
    //����������˽�homeת��Ϊjson�ַ����Ķ���
    String json = JsonTool.write(home);
    //����������˽�json�ַ���ת��Ϊjson����Ķ���
    JsonObject jsonObject =(JsonObject)jsonTool.fromString(json);
    //����������˽�json�ַ���ת��Ϊjava����Ķ���
    Home result = JsonTool.read(Home.class,json);
	
	WriteStrategy strategy = new WriteStrategy();
    //ָ��һ��������ԣ���name����������������ʱ���滻��hello
	strategy.addRenameField("Home.name", "hello");
	json = strategy.write(home);
}

```
##����˵��
�����й���osc:[��ַ](http://git.oschina.net/eric_ds/jfire-codejson)
��������������ӣ�[����](http://120.25.220.15:8085/#nexus-search;quick~codejson)
�������������������ӣ�[����](http://120.25.220.15:8085/#nexus-search;quick~baseutil)
##���ܷ���
Jfire-codejson�������֮ǿԭ�����ڲ��������ڲ�ͬ���㷨��
**���л�**
��ͳ�����л���ܣ�����˵��ԱȽ���������л���ܴ󲿷ֶ������˷����������ݣ�����ͨ��������÷������߷���ȡ������ֵ�ķ�ʽ������������л�������˼������Ŀ������ƿ���ڷ��䵼�µ��������ġ�**Jfire-codejson���ص�ʹ����Ϊ���л�����̬����һ���������**�����������ȫ���ǵ������л������get�������õ�����ֵ��������ƴ��json�е�key����ʱ�����ڶ�̬������룬���Ա��������������֪������д��ģ������ּ����˻�ȡ�����������Ƶ���һ���衣**ʹ��Jfire-codejson�����л������ƽ���������**��Ϊÿ�������д������������۷�ʽ��
**�����л�**
�����л������ǽ���json�ַ���������һ������������һ���޻��˵ĵ����ַ���ȡ��ʽ������ԭ�������ζ�ȡÿһ���ַ����������һЩ����ı�ʾ�ַ�������`{`,`}`,`:`,`[`,`]`�ȵȡ���������Щ�ַ���ʱ������jsonObject����jsonArray��������Ӧ�Ķ�ȡ����ʹ��������ջ��һ����ջ�洢jsonKey��һ����ջ�洢��ǰ�������json����jsonObject����jsonArray�����������ڴ���Ĺ����оͿ��Դﵽ˳�����Ч����**�������ٶȷǳ��죬���Դﵽ������fastjson������**��
��һ��json�������л���pojo��ԭ���Ϻ����л�������ͬ��ͨ�������������set�����õ�����̬����һ�������࣬�����ж���ÿһ��set����������������`if(jsonObject.containsKey("name")){entity.setName(jsonObject.getString("name"))}`�����Ĵ��롣�����Ƕ�̬���룬��������֪����Ҫ������Щ�жϣ������ж���֮����ԭ�������set��������ʡ���ж�ʱ��͵��÷���ʱ�䣬�ʶ������л�������Ҳ�Ƿǳ������㡣**������fastjson**��

###���л�
���Ҫ��һ���������л������ķ�ʽ����������������д��һ���ض��Ĵ��롣���뵱��ͨ�����ö����get�������õ���������ֵ���ο�����Ĵ���
```java
public class User
{
    private String name;
    private int age;
}
//���������࣬�������л�json�Ĵ���Ӧ����
public static void main(String args[])
{
    User user = new User();
    StringBuilder str = new StringBuilder();
    str.append("{");
    str.append("\"name\":");//����������ֵ�����ù̶�ֵ��ʡ�˱�����ȡ��ʱ�仹������jvm�Ż�
    str.append("\"").append(user.getName()).append("\",");
    str.append("\"age\":").append(user.getAge());
    str.append("}");
}
```
�������ʾ�������У�û�з��䣬û���κη�������Զ�������ض������л����롣���˵���ǵĿ��Ҳ�ܹ�ģ�������ķ�ʽ���Ϳ��Դﵽ��󻯵������ٶȡ����ҿ��Աƽ����۵����ޡ�Ϊ�˴ﵽ������Ч���������˶�̬�������ķ�ʽ����ɡ�

1. ���Ȼ��Ŀ�����Class���󡣻�ȡ�ö��������get������������is���������������javabean�淶��
2. ���붯̬���룬��������һ��StringBuilder�����洢json�ַ�������̬�����л���Ҫ��Ҫ���л��Ķ���ʵ��
3. ��Ե�1����õ�ÿһ������������javabean�淶��ȡ���������ơ��������Ƶ�`builder.append("name").append(":\"").append(entity.getName()).append("\",")`���롣
4. ����get������ɺ�Ϳ��Ա�����ݴ����γ�һ���ض������л��ࡣ����һ��Map�ṹ�������洢��������л���֮���ӳ���ϵ������ڵ�3���Ĵ�������з��ַǻ������͵����ԣ����������`builder.append("anotherObject").append(":");WriteContext.write(entity.getAnotherObject(),builder)`�Ĵ��룬�γ�Ƕ�׵ķ������̡���Ƕ�׽���ʱ�������Ҳ�ͳ��׷������.

ͨ�����������Ĳ��裬������ÿһ�����󣬶�������һ����Ըøö�����ض�����࣬��������Ϊԭ��������룬��ȡ���������Լ��������ƶ���ԭ��������ã����ܷǳ��ߡ�
��Ȼ����̬�����������кܶ���Ҫ�жϵĵط�����������Ƕ��������£�Ҫ���ַ��������`{}`�������ַ�����Ҫʹ��`"`��Χ���ݣ������ֺͲ���ֵ����Ҫ����������������Ҫ`[]`�ȵȡ����Ǵ����˼�����ͨ����̬������룬�����е�ʱ�����һ���ض��Ķ���������Ըö��������ࡣ���Ҷ��������б�������Class�ļ�����̬����������ֲ���Javassist����ɡ�
���ɵĴ�����������
���л�һ�����¶���
```java
public class com.jfire.codejson.Home
{
    private String name   = "home";
    private int    length = 113;
    private int    wdith  = 89;
    //ʡ��get set����
}
```
����������������ɵĶ�̬���������
```java
public class JsonWriter_Home_231313131
{
    StringCache cache = (StringCache)$2;
    com.jfire.codejson.Home entity =(com.jfire.codejson.Home )$1;
    cache.append('{');
    cache.append("\"length\":").append(entity.getLength()).append(',');
    String name = entity.getName();
    if(name!=null)
    {
        cache.append("\"name\":\"").append(name).append("\",");
    }
    cache.append("\"wdith\":").append(entity.getWdith()).append(',');
    if(cache.isCommaLast())
    {
        cache.deleteLast();
    }
    cache.append('}');
} 
```
���л����㷨˼·��������
![](http://7xo5sq.com1.z0.glb.clouddn.com/����ר�ŵ����л������.png)

###�����л�
####����json�ַ���
��Ϊjson��һ��kv�ṹ�������㷨�����������ջ�ṹ����ջ�ṹ��������˳���Ϻ��㷨Ҫ���*һ���ȡ�޻���*�����Ǻϵġ�

1. ����ջ����ѹ��������jsonkey
2. ֵ��ջ����ѹ��ÿ�η��ֵ�jsonObject����jsonArray

����json�ַ������㷨��˼·���¿�������Ϊ

1. ����������ջ�ṹ�����洢����
2. ����ַ��Ķ�ȡ���ݡ����������־�ַ�������`{`,`}`,`:`,`[`,`]`,`,`,'"'�Ƚ����ر�����¼����λ�ã����ݷ���λ�þ����Ƿ񴴽��µ�jsonObject����jsonArray����Ƕ�׵ȡ�
3. �ظ�����2��ֱ���ַ���ȡ��ϡ����ȫ����ȡ����򷵻�ֵ��ջ���Ϸ���json����ʱ����ջӦ��Ϊ�գ�ֵ��ջֻ��һ�����ݣ������json�ַ��������Ϲ淶�����ڽ��������лᱻ���֡������׳��쳣��

�����ǽ������̵���ϸ����ͼ
![](http://7xo5sq.com1.z0.glb.clouddn.com/json_read%20.png)

####�����л�
��һ��json�������л���Pojo�������л����������ơ��㷨�Ĵ���˼·����

1. ��ȡpojo������set������
2. ���붯̬���롣�ڶ�̬����Ŀ�ʼ�����������ƴ���`EntityClass entity = new EntityClass();`��ʹ��pojo���ഴ��һ��Pojo���󹩺���ʹ��
3. ��Ե�һ����õ�ÿһ��set����������javabean�淶ȡ�ø�set������Ӧ����������������������`if(json.containsKey("name")){entity.setName(json.getString("name"))}`�Ĵ��롣����`name`���Ǹ���set���������������������
4. ����set������ɺ�ͽ���ݴ����������һ������ض������ת���࣬�������ȡ�����һ��Map�й���keyΪ��Ҫת�����࣬valueΪ���ɵ�ת���ࡣ����ڵ�3�������Ĺ����У������˷ǻ������ԣ�Ҳ����Ƕ�׶�����Ƕ��ִ��1-4�Ĳ��衣�������������ԣ���������`if(json.containsKey("anotherObject")){entity.setAnotherObject(readContext.read(AnotherObject.class,json.getString("name")))}`�Ĵ��롣����readContext����һ������pojo��ת����ӳ���ϵ��Map������

�������ϵ��ĸ����裬�������һ�������Ĵ������ɡ�ʹ������������ض��Ķ�����з����л��Ϳ��Դﵽ�ǳ��ߵ����ܡ�


