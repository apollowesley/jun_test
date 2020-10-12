# ModifyDBInstanceConnectionString {#reference_id4_qwz_lfb .reference}

修改实例的连接地址。

## 请求参数 {#section_wjs_twz_lfb .section}

|**名称**|**类型**|**是否必须**|**描述**|
|------|------|--------|------|
|<公共请求参数\>|-|是|参见[公共请求参数](cn.zh-CN/API 参考/公共参数.md#section_hph_dhp_wbb)。|
|Action|String|是|系统规定参数，取值ModifyDBInstanceConnectionString。|
|DBInstanceId|String|是|实例ID。|
|NewConnectionString|String|是|实例的新地址。只能修改连接地址的前缀部分。连接地址以小写字母开头，由字母、数字组成，长度为8~64个字符。|
|CurrentConnectionString|String|是|实例当前连接地址。|

## 返回参数 {#section_w2z_5wz_lfb .section}

|名称|类型|描述|
|--|--|--|
|<公共返回参数\>|-|参见[公共返回参数](cn.zh-CN/API 参考/公共参数.md#section_rjr_zgp_wbb)。|

## 请求示例 {#section_jcd_wwz_lfb .section}

```
   // 创建DefaultAcsClient实例并初始化
           DefaultProfile profile = DefaultProfile.getProfile(
 				        "cn-hangzhou",  //地域名称
   			   "L***************",  //您的AK
    		"o*********************"); // 您的SK
           DefaultProfile.addEndpoint("cn-hangzhou","cn-hangzhou","R-kvstore","r-kvstore-share.aliyuncs.com");
           DefaultAcsClient client = new DefaultAcsClient(profile);
           // 创建API请求并设置参数
           CommonRequest request = new CommonRequest();
           request.setDomain("r-kvstore-share.aliyuncs.com");
           request.setVersion("2015-01-01");
           request.setAction("ModifyDBInstanceConnectionString");
           request.putQueryParameter("DBInstanceId","r-bp1881b06d6052a4");
           request.putQueryParameter("NewConnectionString","r-bp1881b06d6052a4");
           request.putQueryParameter("CurrentConnectionString"," r-bp1881b06d6052a4-1.redis.rds.aliyuncs.com");
           try {
               CommonResponse response = client.getCommonResponse(request);
               System.out.println(response.getData());} catch (ServerException e){
               // TODO Auto-generated catch block
               e.printStackTrace();} catch (ClientException e){
               // TODO Auto-generated catch block
               e.printStackTrace();}
```

## 返回示例 {#section_osq_wwz_lfb .section}

```

{
"code":"200",
"data":{"RequestId":"A7CCD05D-74FE-44DE-A4F0-C3EE58A252BE"},
"requestId":"A7CCD05D-74FE-44DE-A4F0-C3EE58A252BE",
"successResponse":true
}
```

