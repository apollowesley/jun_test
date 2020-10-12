# UntagResources {#doc_api_R-kvstore_UntagResources .reference}

调用UntagResources将标签从Redis实例解绑。

-   每次解绑的标签数量不能超过20个；
-   标签从一个实例解绑后，如果没有绑定到其它实例，则该标签自动被删除。

该API对应的控制台操作请参见[解绑标签](~~119157~~)。

## 调试 {#api_explorer .section}

[您可以在OpenAPI Explorer中直接运行该接口，免去您计算签名的困扰。运行成功后，OpenAPI Explorer可以自动生成SDK代码示例。](https://api.aliyun.com/#product=R-kvstore&api=UntagResources&type=RPC&version=2015-01-01)

## 请求参数 {#parameters .section}

|名称|类型|是否必选|示例值|描述|
|--|--|----|---|--|
|RegionId|String|是|cn-hangzhou|地域ID，可调用[DescribeRegions](~~61012~~)查询。

 |
|ResourceId.N|RepeatList|是|r-hp3xxxxxxxxxxxxx|实例ID，可以设置多个。

 **说明：** 设置方法请参见请求示例。

 |
|ResourceType|String|是|INSTANCE|资源类型，唯一值：INSTANCE。

 |
|Action|String|否|UntagResources|系统规定参数，取值：UntagResources。

 |
|TagKey.N|RepeatList|否|demokey|标签N的键。

 **说明：** 设置方法请参见请求示例。

 |
|All|Boolean|否|false|解绑实例上的所有标签。可选值：

 -   true
-   false

 **说明：** 

-   默认值：false。
-   如果同时设置了**TagKey.N**和该参数，该参数不生效。

 |
|AccessKeyId|String|否|Lxxxxxxxxxxxxxxw|阿里云颁发给用户的访问服务所用的密钥ID。

 |

## 返回数据 {#resultMapping .section}

|名称|类型|示例值|描述|
|--|--|---|--|
|RequestId|String|601B6F25-21E7-4484-99D5-3EF2625C0088|请求ID。

 |

## 示例 {#demo .section}

请求示例

``` {#request_demo}

https://r-kvstore.aliyuncs.com/
?Action=UntagResources
&RegionId=cn-huhehaote
&ResourceId.1=r-hp1xxxxxxxxxxxxx
&ResourceId.2=r-hp2xxxxxxxxxxxxx
&ResourceType=INSTANCE
&Tag.1.Key=demokey1
&Tag.2.Key=demokey2
&<公共请求参数>

```

正常返回示例

`XML` 格式

``` {#xml_return_success_demo}
<UntagResourcesResponse>
      <RequestId>601B6F25-21E7-4484-99D5-3EF2625C0088</RequestId>
</UntagResourcesResponse>
```

`JSON` 格式

``` {#json_return_success_demo}
{
	"RequestId":"601B6F25-21E7-4484-99D5-3EF2625C0088"
}
```

## 错误码 { .section}

访问[错误中心](https://error-center.aliyun.com/status/product/R-kvstore)查看更多错误码。

