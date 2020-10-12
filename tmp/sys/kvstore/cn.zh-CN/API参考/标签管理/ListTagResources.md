# ListTagResources {#doc_api_R-kvstore_ListTagResources .reference}

调用ListTagResources通过标签筛选Redis实例或者查询实例绑定的标签。

## 调试 {#api_explorer .section}

[您可以在OpenAPI Explorer中直接运行该接口，免去您计算签名的困扰。运行成功后，OpenAPI Explorer可以自动生成SDK代码示例。](https://api.aliyun.com/#product=R-kvstore&api=ListTagResources&type=RPC&version=2015-01-01)

## 请求参数 {#parameters .section}

|名称|类型|是否必选|示例值|描述|
|--|--|----|---|--|
|RegionId|String|是|cn-hangzhou|地域ID，可调用[DescribeRegions](~~61012~~)查询，使用此参数指定要创建实例的地域。

 |
|ResourceType|String|是|INSTANCE|资源类型，唯一值：INSTANCE。

 |
|AccessKeyId|String|否|Lxxxxxxxxxxxxxxw|阿里云颁发给用户的访问服务所用的密钥ID。

 |
|Action|String|否|ListTagResources|系统规定参数，取值：ListTagResources。

 |
|NextToken|String|否|212db86sca4384811e0b5e8707ec21345|用来返回更多结果。第一次查询不需要提供这个参数，如果一次查询没有返回全部结果，则可在后续查询中传入前一次返回的token继续查询。

 |
|ResourceId.N|RepeatList|否|r-hp3xxxxxxxxxxxxx|实例ID，可以设置多个。

 **说明：** 设置多个实例ID的格式为`&ResourceId.1=r-hp1xxxxxxxxxxxxx&ResourceId.2=r-hp2xxxxxxxxxxxxx`。

 |
|Tag.N.Key|String|否|demokey|标签N的键。

 **说明：** 设置方法请参见请求示例。

 |
|Tag.N.Value|String|否|demovalue|标签N的值。

 **说明：** 设置方法请参见请求示例。

 |

## 返回数据 {#resultMapping .section}

|名称|类型|示例值|描述|
|--|--|---|--|
|NextToken|String|212db86sca4384811e0b5e8707ec21345|如果一次查询没有返回全部结果，则可在后续查询中传入前一次返回的token继续查询。

 |
|RequestId|String|47A514A1-4B77-4E30-B4C5-2A880650B3FD|请求ID。

 |
|TagResources| | |查询到的资源列表。

 |
|ResourceId|String|r-hp3xxxxxxxxxxxxx|资源ID，此处为Redis实例的ID。

 |
|ResourceType|String|INSTANCE|资源类型。

 |
|TagKey|String|demokey|标签的键。

 |
|TagValue|String|demovalue|标签的值。

 |

## 示例 {#demo .section}

请求示例

``` {#request_demo}

https://r-kvstore.aliyuncs.com/
?Action=ListTagResources
&RegionId=cn-huhehaote
&ResourceType=INSTANCE
&Tag.1.Key=demokey1
&Tag.1.Value=demovalue1
&Tag.2.Key=demokey2
&Tag.2.Value=demovalue2
&<公共请求参数>

```

正常返回示例

`XML` 格式

``` {#xml_return_success_demo}
<ListTagResourcesResponse>
	  <TagResources>
		    <TagResource>
			      <ResourceType>ALIYUN::KVSTORE::INSTANCE</ResourceType>
			      <TagValue>demovalue1</TagValue>
			      <ResourceId>r-hp3xxxxxxxxxxxxx</ResourceId>
			      <TagKey>demokey1</TagKey>
		    </TagResource>
		    <TagResource>
			      <ResourceType>ALIYUN::KVSTORE::INSTANCE</ResourceType>
			      <TagValue>demovalue1</TagValue>
			      <ResourceId>r-hp2xxxxxxxxxxxxx</ResourceId>
			      <TagKey>demokey1</TagKey>
		    </TagResource>
		    <TagResource>
			      <ResourceType>ALIYUN::KVSTORE::INSTANCE</ResourceType>
			      <TagValue>demovalue2</TagValue>
			      <ResourceId>r-hp3xxxxxxxxxxxxx</ResourceId>
			      <TagKey>demokey2</TagKey>
		    </TagResource>
		    <TagResource>
			      <ResourceType>ALIYUN::KVSTORE::INSTANCE</ResourceType>
			      <TagValue>demovalue2</TagValue>
			      <ResourceId>r-hp2xxxxxxxxxxxxx</ResourceId>
			      <TagKey>demokey2</TagKey>
		    </TagResource>
	  </TagResources>
	  <RequestId>47A514A1-4B77-4E30-B4C5-2A880650B3FD</RequestId>
</ListTagResourcesResponse>
```

`JSON` 格式

``` {#json_return_success_demo}
{
	"TagResources":{
		"TagResource":[
			{
				"ResourceType":"ALIYUN::KVSTORE::INSTANCE",
				"TagValue":"demovalue1",
				"ResourceId":"r-hp3xxxxxxxxxxxxx",
				"TagKey":"demokey1"
			},
			{
				"ResourceType":"ALIYUN::KVSTORE::INSTANCE",
				"TagValue":"demovalue1",
				"ResourceId":"r-hp2xxxxxxxxxxxxx",
				"TagKey":"demokey1"
			},
			{
				"ResourceType":"ALIYUN::KVSTORE::INSTANCE",
				"TagValue":"demovalue2",
				"ResourceId":"r-hp3xxxxxxxxxxxxx",
				"TagKey":"demokey2"
			},
			{
				"ResourceType":"ALIYUN::KVSTORE::INSTANCE",
				"TagValue":"demovalue2",
				"ResourceId":"r-hp2xxxxxxxxxxxxx",
				"TagKey":"demokey2"
			}
		]
	},
	"RequestId":"47A514A1-4B77-4E30-B4C5-2A880650B3FD"
}
```

## 错误码 { .section}

访问[错误中心](https://error-center.aliyun.com/status/product/R-kvstore)查看更多错误码。

