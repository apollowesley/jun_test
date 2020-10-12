# TagResources {#doc_api_R-kvstore_TagResources .reference}

调用TagResources为一个或多个Redis实例绑定标签。

在实例数量较多的情况下，您可以创建多个标签，为实例绑定不同的标签对其进行分类，之后通过标签进行实例筛选。

-   标签由一对键（key）值（value）组成，键在同账号同地域下唯一，值无此限制。
-   若设置的标签不存在，则自动创建该标签并绑定到目标实例。
-   若实例已经绑定了有相同键的标签，则进行覆盖绑定。
-   每个实例最多可以绑定20个标签。
-   每次调用最多设置50个实例进行批量标签绑定。

该API对应的控制台操作请参见[新建标签](~~118779~~)。

## 调试 {#api_explorer .section}

[您可以在OpenAPI Explorer中直接运行该接口，免去您计算签名的困扰。运行成功后，OpenAPI Explorer可以自动生成SDK代码示例。](https://api.aliyun.com/#product=R-kvstore&api=TagResources&type=RPC&version=2015-01-01)

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
|Action|String|否|TagResources|系统规定参数，取值：TagResources。

 |
|AccessKeyId|String|否|Lxxxxxxxxxxxxxxw|阿里云颁发给用户的访问服务所用的密钥ID。

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
|RequestId|String|4BD4E308-A3D8-4CD1-98B3-0ADAEE38B01B|请求ID。

 |

## 示例 {#demo .section}

请求示例

``` {#request_demo}

https://r-kvstore.aliyuncs.com/
?Action=TagResources
&RegionId=cn-huhehaote
&ResourceId.1=r-hp1xxxxxxxxxxxxx
&ResourceId.2=r-hp2xxxxxxxxxxxxx
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
<TagResourcesResponse>
      <RequestId>4BD4E308-A3D8-4CD1-98B3-0ADAEE38B01B</RequestId>
</TagResourcesResponse>
```

`JSON` 格式

``` {#json_return_success_demo}
{
	"RequestId":"4BD4E308-A3D8-4CD1-98B3-0ADAEE38B01B"
}
```

## 错误码 { .section}

访问[错误中心](https://error-center.aliyun.com/status/product/R-kvstore)查看更多错误码。

