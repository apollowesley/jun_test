# ModifySecurityIps {#doc_api_R-kvstore_ModifySecurityIps .reference}

调用ModifySecurityIps设置Redis实例的IP白名单。

该API对应的控制台操作请参见[设置IP白名单](~~56464~~)。

## 调试 {#apiExplorer .section}

前往【[API Explorer](https://api.aliyun.com/#product=R-kvstore&api=ModifySecurityIps)】在线调试，API Explorer 提供在线调用 API、动态生成 SDK Example 代码和快速检索接口等能力，能显著降低使用云 API 的难度，强烈推荐使用。

## 请求参数 {#parameters .section}

|名称|类型|是否必选|示例值|描述|
|--|--|----|---|--|
|Action|String|是|ModifySecurityIps|系统规定参数，取值：ModifySecurityIps。

 |
|InstanceId|String|是|r-bp1xxxxxxxxxxxxx|需要修改设置的实例的ID。

 |
|SecurityIps|String|是|100.xxx.xxx.xxx/24,10.xxx.xxx.xxx|IP白名单分组下的IP列表，最多1000个。IP之间以逗号隔开，格式如下：0.0.0.0/0,10.23.12.24，或者10.23.12.24/24（CIDR模式，无类域间路由，/24表示地址中前缀的长度，范围1-32）。

 |
|SecurityIpGroupAttribute|String|否|hidden|默认为空。用于区分不同的属性值，控制台将不显示该值为hidden的白名单分组。

 |
|ModifyMode|String|否|Append|修改方式：

 -   Cover（覆盖原白名单）
-   Append（追加白名单）
-   Delete（删除该白名单 ）

 |
|SecurityIpGroupName|String|否|default|IP白名单分组的名称。

 |
|AccessKeyId|String|否|Lxxxxxxxxxxxxxxw|阿里云颁发给用户的访问服务所用的密钥ID。

 |

## 返回参数 {#resultMapping .section}

|名称|类型|示例值|描述|
|--|--|---|--|
|RequestId|String|AAAF99B1-69ED-4E80-8CD5-272C09E46ACF|请求ID。

 |

## 示例 {#demo .section}

请求示例

``` {#request_demo}

https://r-kvstore.aliyuncs.com/
?Action=ModifySecurityIps
&InstanceId=r-bp1xxxxxxxxxxxxx
&SecurityIps=100.xxx.xxx.xxx/24,10.xxx.xxx.xxx
&<公共请求参数>

```

正常返回示例

`XML` 格式

``` {#xml_return_success_demo}
<ModifySecurityIpsResponse>
  <RequestId>AAAF99B1-69ED-4E80-8CD5-272C09E46ACF</RequestId>
</ModifySecurityIpsResponse>

```

`JSON` 格式

``` {#json_return_success_demo}
{
	"RequestId":"AAAF99B1-69ED-4E80-8CD5-272C09E46ACF"
}
```

## 错误码 { .section}

[查看本产品错误码](https://error-center.aliyun.com/status/product/R-kvstore)

