# ModifyDBInstanceConnectionString {#doc_api_R-kvstore_ModifyDBInstanceConnectionString .reference}

You can call this operation to modify an endpoint of an ApsaraDB for Redis instance.

For more information about how to perform the corresponding operation in the console, see [Modify connection addresses](~~85683~~).

## Debugging {#apiExplorer .section}

Alibaba Cloud provides [OpenAPI Explorer](https://api.aliyun.com/#product=R-kvstore&api=ModifyDBInstanceConnectionString) to simplify API usage. You can use OpenAPI Explorer to search for APIs, call APIs, and dynamically generate SDK example code.

## Request parameters {#parameters .section}

|Parameter|Type|Required|Example|Description|
|---------|----|--------|-------|-----------|
|DBInstanceId|String|Yes|r-bp1xxxxxxxxxxxxx|The ID of the instance for which you want to modify an endpoint.

 |
|CurrentConnectionString|String|Yes|r-bp1xxxxxxxxxxxxx.redis.rds.aliyuncs.com|The current endpoint of the ApsaraDB for Redis instance.

 |
|NewConnectionString|String|Yes|standardredis|The prefix of the new endpoint.

 The new endpoint of the ApsaraDB for Redis instance is in the `<Prefix>.redis.rds.aliyuncs.com` format. The prefix of the endpoint must start with a lowercase letter and can contain lowercase letters and digits. The prefix can be 8 to 64 characters in length.

 |
|Action|String|No|ModifyDBInstanceConnectionString|The operation that you want to perform. Set this parameter to ModifyDBInstanceConnectionString.

 |
|IPType|String|No|Public|The network type of the new endpoint. Value values:

 -   Private: internal network.
-   Public: public network.

 |
|Port|String|No|6379|The service port of the instance. If the **IPType** parameter is set to `Public`, you can specify a custom port by using this parameter. Valid values: 1024 to 65535.

 |
|AccessKeyId|String|No|Lxxxxxxxxxxxxxxw|The AccessKey ID that Alibaba Cloud provides for you to access services.

 |

## Response parameters {#resultMapping .section}

|Parameter|Type|Example|Description|
|---------|----|-------|-----------|
|RequestId|String|1790D68A-465C-44E3-BC24-9732652961F9|The ID of the request.

 |

## Examples {#demo .section}

Sample request

``` {#request_demo}

https://r-kvstore.aliyuncs.com/
? Action=ModifyDBInstanceConnectionString
&DBInstanceId=r-bp1xxxxxxxxxxxxx
&CurrentConnectionString=r-bp1xxxxxxxxxxxxx.redis.rds.aliyuncs.com
&NewConnectionString=standardredis
&<Common request parameters>

```

Sample success response

`XML` format

``` {#xml_return_success_demo}
<ModifyDBInstanceConnectionStringResponse>
  <RequestId>1790D68A-465C-44E3-BC24-9732652961F9</RequestId>
</ModifyDBInstanceConnectionStringResponse>

```

`JSON` format

``` {#json_return_success_demo}
{
	"RequestId":"1790D68A-465C-44E3-BC24-9732652961F9"
}
```

## Error codes { .section}

