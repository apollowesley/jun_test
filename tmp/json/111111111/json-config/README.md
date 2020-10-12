#json-config

oschina maven镜像构件：
<dependency>
  <groupId>com.tcshuo</groupId>
  <artifactId>json-config</artifactId>
  <version>1.0.0</version>
</dependency>

 * json输出配置
 *
 * {ignore:'c'}忽略复杂字段 {ignore:'n'} 不忽略字段 {ignore:'na'} 忽略不在allows允许列表的字段
 * {ignores:['dept','post']}凡在ignores的一律不输出 保证不输出dept、post字段
 * {allows:['id','name']}凡在allows的一律输出 保证输出字段id、name
 * {allows:['id','name'],ignore:'na'}只输出字段id、name {}默认：不输出复杂字段、集合或数组字段
 * {ignore:'c'，allows:['dept']}除dept的复杂对象都不输出
 * {alias:{id:'value'}}字段别名输出，id字段输出为value字段
 * --------------------------------------------------------------------------------
 * {    allows:['id','name','date','dept']
 *      ,childs :{
 *          date:{format:'yyyy-MM-dd'} 
 *          ,dept:{allows:['name']}
 *          }
 * }输出id、name、date、dept、字段，其中date字段使用yyyy-MM-dd格式化
 * 
 * --------------------------------------------------------------------------------


 public void test1() {

        HashMap<Object, Object> map = new HashMap<Object, Object>();
        map.put("maptype", BeanType.A);
        Bean beana = new Bean("bean a", BeanType.A);
        Bean beanb = new Bean("bean b", BeanType.B);
        map.put("beana", beana);
        map.put("beanb", beanb);

        ArrayList<Bean> beanList = new ArrayList<Bean>();
        beanList.add(beana);
        beanList.add(beanb);
        map.put("beanList", beanList);

        int[] intArray = new int[]{1, 2, 3};
        map.put("intArray", intArray);

        Bean[] beanArray = new Bean[]{beana, beanb};
        map.put("beanArray", beanArray);
        
        
        

     
        toJson(null, map);
        toJson("{ignore:'n'}", map);
        toJson("{ignore:'n'}", beanList);
        toJson("{ignore:'na',allows:['name','date'],childs:{date:{format:'yyyy年MM月'}}}", beanList);
        toJson("{ignore:'na',allows:['name','type'],childs:{type:{ignore:'na',allows:['javaEnumName']}}}", beanList);
        toJson("{ignore:'na',allows:['name','type'],childs:{type:{alias:{javaEnumName:'NAME'},ignore:'na',allows:['javaEnumName']}}}", beanList);


    }

    private void toJson(String config, Object target) {
        System.out.println(JsonConfigParser.parser(config, target).toJson());
    }



------------------------------------------------------------------------------------------
Running com.tcshuo.json.config.SimpleTestCase
{"intArray":[1,2,3]}
{"beanb":{"name":"bean b"},"beanArray":[{"name":"bean a"},{"name":"bean b"}],"beana":{"name":"bean a"},"beanList":[{"name":"bean a"},{"name":"bean b"}],"intArray":[1,2,3],"maptype":{"declaringClass":"com.tcshuo.json.config.BeanType","name":"a","value":0,"javaEnumName":"A"}}
[{"name":"bean a","type":{"declaringClass":"com.tcshuo.json.config.BeanType","name":"a","value":0,"javaEnumName":"A"},"date":"2015-05-06 18:57:15"},{"name":"bean b","type":{"declaringClass":"com.tcshuo.json.config.BeanType","name":"b","value":1,"javaEnumName":"B"},"date":"2015-05-06 18:57:15"}]
[{"name":"bean a","date":"2015年05月"},{"name":"bean b","date":"2015年05月"}]
[{"name":"bean a","type":{"javaEnumName":"A"}},{"name":"bean b","type":{"javaEnumName":"B"}}]
[{"name":"bean a","type":{"NAME":"A"}},{"name":"bean b","type":{"NAME":"B"}}]