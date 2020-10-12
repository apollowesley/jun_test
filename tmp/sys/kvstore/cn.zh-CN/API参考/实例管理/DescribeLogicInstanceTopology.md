# DescribeLogicInstanceTopology {#doc_api_R-kvstore_DescribeLogicInstanceTopology .reference}

调用DescribeLogicInstanceTopology查询Redis实例的逻辑拓扑结构。

## 调试 {#api_explorer .section}

[您可以在OpenAPI Explorer中直接运行该接口，免去您计算签名的困扰。运行成功后，OpenAPI Explorer可以自动生成SDK代码示例。](https://api.aliyun.com/#product=R-kvstore&api=DescribeLogicInstanceTopology&type=RPC&version=2015-01-01)

## 请求参数 {#parameters .section}

|名称|类型|是否必选|示例值|描述|
|--|--|----|---|--|
|InstanceId|String|是|r-bp1xxxxxxxxxxxxx|目的实例的ID。

 |
|Action|String|否|DescribeLogicInstanceTopology|系统规定参数，取值：DescribeLogicInstanceTopology。

 |
|AccessKeyId|String|否|Lxxxxxxxxxxxxxxw|阿里云颁发给用户的访问服务所用的密钥ID。

 |

## 返回数据 {#resultMapping .section}

|名称|类型|示例值|描述|
|--|--|---|--|
|InstanceId|String|r-bp1xxxxxxxxxxxxx|实例的ID。

 |
|RedisProxyList| | |代理详情，包含代理节点信息。

 |
|Bandwidth|String|96|节点的带宽限制，单位为MB/s。

 |
|Capacity|String|5120|节点的容量，单位为MB。

 |
|Connection|String|320000|连接数限制。

 |
|NodeId|String|r-xxxxxxxxxxxxxxx-proxy-3\#5425423|节点ID。

 |
|NodeType|String|proxy|节点类型，取值：

 -   proxy（代理节点）
-   db（数据节点）

 |
|RedisShardList| | |分片详情，包含NodeInfo等子节点信息 。

 |
|Bandwidth|String|96|节点的带宽限制，单位为MB/s。

 |
|Capacity|String|2048|节点的容量，单位为MB。

 |
|Connection|String|10000|连接数限制。

 |
|NodeId|String|r-bp1xxxxxxxxxxxxx-db-0\#6889415|节点ID。

 |
|NodeType|String|db|节点类型，取值：

 -   proxy（代理节点）
-   db（数据节点）

 |
|RequestId|String|5F749626-2B6A-4DC2-A010-B51F7C33084E|请求ID。

 |

## 示例 {#demo .section}

请求示例

``` {#request_demo}

https://r-kvstore.aliyuncs.com/
?Action=DescribeLogicInstanceTopology
&InstanceId=r-bp1xxxxxxxxxxxxx
&<公共请求参数>

```

正常返回示例

`XML` 格式

``` {#xml_return_success_demo}
<DescribeLogicInstanceTopologyResponse>
      <RedisShardList>
        <NodeInfo>
              <NodeId>r-xxxxxxxxxxxxxxx-proxy-3#5425423</NodeId>
              <NodeType>proxy</NodeType>
              <Capacity>5120</Capacity>
              <Connection>320000</Connection>
              <Bandwidth>96</Bandwidth>
        </NodeInfo>
      </RedisShardList>
      <InstanceId>r-xxxxxxxxxxxxxxx</InstanceId>
      <RequestId>5F749626-2B6A-4DC2-A010-B51F7C33084E</RequestId>
</DescribeLogicInstanceTopologyResponse>
```

`JSON` 格式

``` {#json_return_success_demo}
{
	"RedisShardList":{
		"NodeInfo":[
			{
				"NodeId":"r-xxxxxxxxxxxxxxx-proxy-3#5425423",
				"NodeType":"proxy",
				"Capacity":"5120",
				"Connection":"320000",
				"Bandwidth":96
			}
		]
	},
	"RequestId":"5F749626-2B6A-4DC2-A010-B51F7C33084E",
	"InstanceId":"r-xxxxxxxxxxxxxxx"
}
```

## 错误码 { .section}

访问[错误中心](https://error-center.aliyun.com/status/product/R-kvstore)查看更多错误码。

