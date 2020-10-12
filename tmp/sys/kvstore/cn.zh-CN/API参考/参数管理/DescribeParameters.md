# DescribeParameters {#doc_api_R-kvstore_DescribeParameters .reference}

调用DescribeParameters查询实例的配置参数和运行参数。

## 调试 {#apiExplorer .section}

前往【[API Explorer](https://api.aliyun.com/#product=R-kvstore&api=DescribeParameters)】在线调试，API Explorer 提供在线调用 API、动态生成 SDK Example 代码和快速检索接口等能力，能显著降低使用云 API 的难度，强烈推荐使用。

## 请求参数 {#parameters .section}

|名称|类型|是否必选|示例值|描述|
|--|--|----|---|--|
|Action|String|是|DescribeParameters|系统规定参数，取值：DescribeParameters。

 |
|DBInstanceId|String|是|r-bp1xxxxxxxxxxxxx|需要查询的实例的ID。

 |
|NodeId|String|否|r-bp1xxxxxxxxxxxxx-db-0\#6889415|查询集群实例中单个节点配置时需要传入该节点的ID。

 |
|AccessKeyId|String|否|Lxxxxxxxxxxxxxxw|阿里云颁发给用户的访问服务所用的密钥ID。

 |

## 返回参数 {#resultMapping .section}

|名称|类型|示例值|描述|
|--|--|---|--|
|ConfigParameters| | |配置参数列表。

 |
|└CheckingCode|String|\[0|1\]|校验代码，参数的可选范围。

 |
|└ForceRestart|Boolean|true|是否需要重启生效：

 -   True（重启生效）
-   False（无需重启，提交后即生效）

 |
|└ModifiableStatus|Boolean|true|参数是否可修改:

 -   False（不可修改）
-   True（可修改）

 |
|└ParameterDescription|String|Check all keys passed in the KEYS array map to the same slot.|参数的描述。

 |
|└ParameterName|String|script\_check\_enable|参数名。

 |
|└ParameterValue|String|1|参数的值。

 |
|Engine|String|redis|数据库类型。

 |
|EngineVersion|String|4.0|数据库版本号。

 |
|RunningParameters| | |运行参数列表。

 |
|└CheckingCode|String|\[0|1\]|参数的可选范围。

 |
|└ForceRestart|String|true|是否需要重启生效：

 -   True（重启生效）
-   False（无需重启，提交后即生效）

 |
|└ModifiableStatus|String|true|参数是否可修改:

 -   False（不可修改）
-   True（可修改）

 |
|└ParameterDescription|String|You can disable some dangerous commands, for example \\"keys,flushdb,flushall\\", the commands must be in \[flushall,flushdb,keys,hgetall,eval,evalsha,script\].|参数的描述。

 |
|└ParameterName|String|\#no\_loose\_disabled-commands|参数名。

 |
|└ParameterValue|String|keys,flushall,flushdb|参数的值。

 |
|RequestId|String|9C1338BE-8DE8-4890-A900-E1BC06BF7E1A|请求ID。

 |

## 示例 {#demo .section}

请求示例

``` {#request_demo}

http(s)://[Endpoint]/?Action=DescribeParameters
&DBInstanceId=r-bp1xxxxxxxxxxxxx
&<公共请求参数>

```

正常返回示例

`XML` 格式

``` {#xml_return_success_demo}
<DescribeParametersResponse>
  <ConfigParameters/>
  <RequestId>9C1338BE-8DE8-4890-A900-E1BC06BF7E1A</RequestId>
  <RunningParameters>
    <Parameter>
      <ParameterDescription>check whitelist always</ParameterDescription>
      <ParameterValue>no</ParameterValue>
      <CheckingCode>[yes|no]</CheckingCode>
      <ForceRestart>false</ForceRestart>
      <ModifiableStatus>true</ModifiableStatus>
      <ParameterName>#no_loose_check-whitelist-always</ParameterName>
    </Parameter>
    <Parameter>
      <ParameterDescription>You can disable some dangerous commands, for example "keys,flushdb,flushall", the commands must be in [flushall,flushdb,keys,hgetall,eval,evalsha,script].</ParameterDescription>
      <ParameterValue>keys,flushall,flushdb</ParameterValue>
      <CheckingCode>.*</CheckingCode>
      <ForceRestart>false</ForceRestart>
      <ModifiableStatus>true</ModifiableStatus>
      <ParameterName>#no_loose_disabled-commands</ParameterName>
    </Parameter>
    <Parameter>
      <ParameterDescription>开启关闭SSL</ParameterDescription>
      <ParameterValue>yes</ParameterValue>
      <CheckingCode>[yes|no]</CheckingCode>
      <ForceRestart>false</ForceRestart>
      <ModifiableStatus>true</ModifiableStatus>
      <ParameterName>#no_loose_ssl-enabled</ParameterName>
    </Parameter>
    <Parameter>
      <ParameterDescription>Be compatible with cluster mode.</ParameterDescription>
      <ParameterValue>0</ParameterValue>
      <CheckingCode>[0|1]</CheckingCode>
      <ForceRestart>false</ForceRestart>
      <ModifiableStatus>true</ModifiableStatus>
      <ParameterName>cluster_compat_enable</ParameterName>
    </Parameter>
    <Parameter>
      <ParameterDescription>Check all keys passed in the KEYS array map to the same slot.</ParameterDescription>
      <ParameterValue>1</ParameterValue>
      <CheckingCode>[0|1]</CheckingCode>
      <ForceRestart>false</ForceRestart>
      <ModifiableStatus>true</ModifiableStatus>
      <ParameterName>script_check_enable</ParameterName>
    </Parameter>
    <Parameter>
      <ParameterDescription>If hash fields are less than this value and hash value sizes are less than hash-max-ziplist-value, the ziplist will be used.</ParameterDescription>
      <ParameterValue>512</ParameterValue>
      <CheckingCode>[0-999999999999999]</CheckingCode>
      <ForceRestart>false</ForceRestart>
      <ModifiableStatus>true</ModifiableStatus>
      <ParameterName>hash-max-ziplist-entries</ParameterName>
    </Parameter>
    <Parameter>
      <ParameterDescription>If hash value sizes are than less this value and hash fields are less than hash-max-ziplist-entries, the ziplist will be used.</ParameterDescription>
      <ParameterValue>64</ParameterValue>
      <CheckingCode>[0-999999999999999]</CheckingCode>
      <ForceRestart>false</ForceRestart>
      <ModifiableStatus>true</ModifiableStatus>
      <ParameterName>hash-max-ziplist-value</ParameterName>
    </Parameter>
    <Parameter>
      <ParameterDescription>lazyfree switch on eviction.</ParameterDescription>
      <ParameterValue>yes</ParameterValue>
      <CheckingCode>[yes|no]</CheckingCode>
      <ForceRestart>false</ForceRestart>
      <ModifiableStatus>true</ModifiableStatus>
      <ParameterName>lazyfree-lazy-eviction</ParameterName>
    </Parameter>
    <Parameter>
      <ParameterDescription>lazyfree switch on expire.</ParameterDescription>
      <ParameterValue>yes</ParameterValue>
      <CheckingCode>[yes|no]</CheckingCode>
      <ForceRestart>false</ForceRestart>
      <ModifiableStatus>true</ModifiableStatus>
      <ParameterName>lazyfree-lazy-expire</ParameterName>
    </Parameter>
    <Parameter>
      <ParameterDescription>lazyfree switch on server implicit deletion.</ParameterDescription>
      <ParameterValue>yes</ParameterValue>
      <CheckingCode>[yes|no]</CheckingCode>
      <ForceRestart>false</ForceRestart>
      <ModifiableStatus>true</ModifiableStatus>
      <ParameterName>lazyfree-lazy-server-del</ParameterName>
    </Parameter>
    <Parameter>
      <ParameterDescription>The number of entries on the list that are not compressed at both ends.</ParameterDescription>
      <ParameterValue>0</ParameterValue>
      <CheckingCode>[0-65535]</CheckingCode>
      <ForceRestart>false</ForceRestart>
      <ModifiableStatus>true</ModifiableStatus>
      <ParameterName>list-compress-depth</ParameterName>
    </Parameter>
    <Parameter>
      <ParameterDescription>Threadhold of ziplist size on quicklist.</ParameterDescription>
      <ParameterValue>-2</ParameterValue>
      <CheckingCode>[-5-99999999]</CheckingCode>
      <ForceRestart>false</ForceRestart>
      <ModifiableStatus>true</ModifiableStatus>
      <ParameterName>list-max-ziplist-size</ParameterName>
    </Parameter>
    <Parameter>
      <ParameterDescription>To config how Redis will select what to remove when maxmemory is reached. There are eight behaviors that can be chose : volatile-lru,allkeys-lru,volatile-lfu,allkeys-lfu,volatile-random,allkeys-random,volatile-ttl,noeviction</ParameterDescription>
      <ParameterValue>volatile-lfu</ParameterValue>
      <CheckingCode>[volatile-lru|allkeys-lru|volatile-random|allkeys-random|volatile-ttl|volatile-lfu|allkeys-lfu|noeviction]</CheckingCode>
      <ForceRestart>false</ForceRestart>
      <ModifiableStatus>true</ModifiableStatus>
      <ParameterName>maxmemory-policy</ParameterName>
    </Parameter>
    <Parameter>
      <ParameterDescription>
				Sets have a special encoding in just one case: when a set is composed
				of just strings that happen to be integers in radix 10 in the range
				of 64 bit signed integers.
				The following configuration setting sets the limit in the size of the
			set in order to use this special memory saving encoding.</ParameterDescription>
      <ParameterValue>512</ParameterValue>
      <CheckingCode>[0-999999999999999]</CheckingCode>
      <ForceRestart>false</ForceRestart>
      <ModifiableStatus>true</ModifiableStatus>
      <ParameterName>set-max-intset-entries</ParameterName>
    </Parameter>
    <Parameter>
      <ParameterDescription>
				The time is expressed in microseconds, so 1000000 is equivalent
				to one second. Note that a negative number disables the slow log,
			while a value of zero forces the logging of every command.</ParameterDescription>
      <ParameterValue>10000</ParameterValue>
      <CheckingCode>[0-10000000]</CheckingCode>
      <ForceRestart>false</ForceRestart>
      <ModifiableStatus>true</ModifiableStatus>
      <ParameterName>slowlog-log-slower-than</ParameterName>
    </Parameter>
    <Parameter>
      <ParameterDescription>The max slowlog count.</ParameterDescription>
      <ParameterValue>1024</ParameterValue>
      <CheckingCode>[100-10000]</CheckingCode>
      <ForceRestart>false</ForceRestart>
      <ModifiableStatus>true</ModifiableStatus>
      <ParameterName>slowlog-max-len</ParameterName>
    </Parameter>
    <Parameter>
      <ParameterDescription>
				Similarly to hashes and lists, sorted sets are also specially encoded in
			order to save a lot of space.</ParameterDescription>
      <ParameterValue>128</ParameterValue>
      <CheckingCode>[0-999999999999999]</CheckingCode>
      <ForceRestart>false</ForceRestart>
      <ModifiableStatus>true</ModifiableStatus>
      <ParameterName>zset-max-ziplist-entries</ParameterName>
    </Parameter>
    <Parameter>
      <ParameterDescription>
				Similarly to hashes and lists, sorted sets are also specially encoded in
			order to save a lot of space.</ParameterDescription>
      <ParameterValue>64</ParameterValue>
      <CheckingCode>[0-999999999999999]</CheckingCode>
      <ForceRestart>false</ForceRestart>
      <ModifiableStatus>true</ModifiableStatus>
      <ParameterName>zset-max-ziplist-value</ParameterName>
    </Parameter>
  </RunningParameters>
  <EngineVersion>4.0</EngineVersion>
  <Engine>redis</Engine>
</DescribeParametersResponse>

```

`JSON` 格式

``` {#json_return_success_demo}
{
	"ConfigParameters":{
		"Parameter":[]
	},
	"RequestId":"9C1338BE-8DE8-4890-A900-E1BC06BF7E1A",
	"RunningParameters":{
		"Parameter":[
			{
				"ParameterDescription":"check whitelist always",
				"ParameterValue":"no",
				"ForceRestart":"false",
				"CheckingCode":"[yes|no]",
				"ModifiableStatus":"true",
				"ParameterName":"#no_loose_check-whitelist-always"
			},
			{
				"ParameterDescription":"You can disable some dangerous commands, for example \"keys,flushdb,flushall\", the commands must be in [flushall,flushdb,keys,hgetall,eval,evalsha,script].",
				"ParameterValue":"keys,flushall,flushdb",
				"ForceRestart":"false",
				"CheckingCode":".*",
				"ModifiableStatus":"true",
				"ParameterName":"#no_loose_disabled-commands"
			},
			{
				"ParameterDescription":"开启关闭SSL",
				"ParameterValue":"yes",
				"ForceRestart":"false",
				"CheckingCode":"[yes|no]",
				"ModifiableStatus":"true",
				"ParameterName":"#no_loose_ssl-enabled"
			},
			{
				"ParameterDescription":"Be compatible with cluster mode.",
				"ParameterValue":"0",
				"ForceRestart":"false",
				"CheckingCode":"[0|1]",
				"ModifiableStatus":"true",
				"ParameterName":"cluster_compat_enable"
			},
			{
				"ParameterDescription":"Check all keys passed in the KEYS array map to the same slot.",
				"ParameterValue":"1",
				"ForceRestart":"false",
				"CheckingCode":"[0|1]",
				"ModifiableStatus":"true",
				"ParameterName":"script_check_enable"
			},
			{
				"ParameterDescription":"If hash fields are less than this value and hash value sizes are less than hash-max-ziplist-value, the ziplist will be used.",
				"ParameterValue":"512",
				"ForceRestart":"false",
				"CheckingCode":"[0-999999999999999]",
				"ModifiableStatus":"true",
				"ParameterName":"hash-max-ziplist-entries"
			},
			{
				"ParameterDescription":"If hash value sizes are than less this value and hash fields are less than hash-max-ziplist-entries, the ziplist will be used.",
				"ParameterValue":"64",
				"ForceRestart":"false",
				"CheckingCode":"[0-999999999999999]",
				"ModifiableStatus":"true",
				"ParameterName":"hash-max-ziplist-value"
			},
			{
				"ParameterDescription":"lazyfree switch on eviction.",
				"ParameterValue":"yes",
				"ForceRestart":"false",
				"CheckingCode":"[yes|no]",
				"ModifiableStatus":"true",
				"ParameterName":"lazyfree-lazy-eviction"
			},
			{
				"ParameterDescription":"lazyfree switch on expire.",
				"ParameterValue":"yes",
				"ForceRestart":"false",
				"CheckingCode":"[yes|no]",
				"ModifiableStatus":"true",
				"ParameterName":"lazyfree-lazy-expire"
			},
			{
				"ParameterDescription":"lazyfree switch on server implicit deletion.",
				"ParameterValue":"yes",
				"ForceRestart":"false",
				"CheckingCode":"[yes|no]",
				"ModifiableStatus":"true",
				"ParameterName":"lazyfree-lazy-server-del"
			},
			{
				"ParameterDescription":"The number of entries on the list that are not compressed at both ends.",
				"ParameterValue":"0",
				"ForceRestart":"false",
				"CheckingCode":"[0-65535]",
				"ModifiableStatus":"true",
				"ParameterName":"list-compress-depth"
			},
			{
				"ParameterDescription":"Threadhold of ziplist size on quicklist.",
				"ParameterValue":"-2",
				"ForceRestart":"false",
				"CheckingCode":"[-5-99999999]",
				"ModifiableStatus":"true",
				"ParameterName":"list-max-ziplist-size"
			},
			{
				"ParameterDescription":"To config how Redis will select what to remove when maxmemory is reached. There are eight behaviors that can be chose : volatile-lru,allkeys-lru,volatile-lfu,allkeys-lfu,volatile-random,allkeys-random,volatile-ttl,noeviction",
				"ParameterValue":"volatile-lfu",
				"ForceRestart":"false",
				"CheckingCode":"[volatile-lru|allkeys-lru|volatile-random|allkeys-random|volatile-ttl|volatile-lfu|allkeys-lfu|noeviction]",
				"ModifiableStatus":"true",
				"ParameterName":"maxmemory-policy"
			},
			{
				"ParameterDescription":"Sets have a special encoding in just one case: when a set is composed\nof just strings that happen to be integers in radix 10 in the range\nof 64 bit signed integers.\nThe following configuration setting sets the limit in the size of the\nset in order to use this special memory saving encoding.",
				"ParameterValue":"512",
				"ForceRestart":"false",
				"CheckingCode":"[0-999999999999999]",
				"ModifiableStatus":"true",
				"ParameterName":"set-max-intset-entries"
			},
			{
				"ParameterDescription":"The time is expressed in microseconds, so 1000000 is equivalent\nto one second. Note that a negative number disables the slow log,\nwhile a value of zero forces the logging of every command.",
				"ParameterValue":"10000",
				"ForceRestart":"false",
				"CheckingCode":"[0-10000000]",
				"ModifiableStatus":"true",
				"ParameterName":"slowlog-log-slower-than"
			},
			{
				"ParameterDescription":"The max slowlog count.",
				"ParameterValue":"1024",
				"ForceRestart":"false",
				"CheckingCode":"[100-10000]",
				"ModifiableStatus":"true",
				"ParameterName":"slowlog-max-len"
			},
			{
				"ParameterDescription":"Similarly to hashes and lists, sorted sets are also specially encoded in\norder to save a lot of space.",
				"ParameterValue":"128",
				"ForceRestart":"false",
				"CheckingCode":"[0-999999999999999]",
				"ModifiableStatus":"true",
				"ParameterName":"zset-max-ziplist-entries"
			},
			{
				"ParameterDescription":"Similarly to hashes and lists, sorted sets are also specially encoded in\norder to save a lot of space.",
				"ParameterValue":"64",
				"ForceRestart":"false",
				"CheckingCode":"[0-999999999999999]",
				"ModifiableStatus":"true",
				"ParameterName":"zset-max-ziplist-value"
			}
		]
	},
	"EngineVersion":"4.0",
	"Engine":"redis"
}
```

## 错误码 { .section}

|HttpCode|错误码|错误信息|描述|
|--------|---|----|--|
|404|InvalidDBInstanceClass.NotFound|Specified DB instance class is not found.|实例规格无效，请检查该参数是否正确。|
|403|IncorrectDBInstanceType|Current DB instance type does not support this operation.|由于实例类型不允许该操作，只读实例允许克隆和备份实例|

[查看本产品错误码](https://error-center.aliyun.com/status/product/R-kvstore)

