# ModifyInstanceMaintainTime {#doc_api_R-kvstore_ModifyInstanceMaintainTime .reference}

You can call this operation to modify the maintenance window of an ApsaraDB for Redis instance. Alibaba Cloud maintains the ApsaraDB for Redis instance in the specified maintenance window.

For more information about how to perform the corresponding operation in the console, see [Set a maintenance window](~~55252~~).

## Debugging {#apiExplorer .section}

Alibaba Cloud provides OpenAPI Explorer to simplify API usage. You can use [OpenAPI Explorer](https://api.aliyun.com/#product=R-kvstore&api=ModifyInstanceMaintainTime) to search for APIs, call APIs, and dynamically generate SDK example code.

## Request parameters {#parameters .section}

|Parameter|Type|Required|Example|Description|
|---------|----|--------|-------|-----------|
|Action|String|Yes|ModifyInstanceMaintainTime|The operation that you want to perform. Set this parameter to ModifyInstanceMaintainTime.

 |
|InstanceId|String|Yes|r-bp1xxxxxxxxxxxxx|The ID of the instance for which you want to modify the maintenance window.

 |
|MaintainStartTime|String|Yes|03:00Z|The start time of the maintenance window. The time must be in the `HH:mmZ` format.

 |
|MaintainEndTime|String|Yes|02:00Z|The end time of the maintenance window. The time must be in the `HH:mmZ` format.

 **Note:** The interval between the start time and the end time must be one hour. For example, if the value of the MaintainStartTime parameter is `01:00Z`, the value of the MaintainEndTime parameter must be `02:00Z`.

 |
|AccessKeyId|String|No|Lxxxxxxxxxxxxxxw|The AccessKey ID that Alibaba Cloud provides for you to access services.

 |

## Response parameters {#resultMapping .section}

|Parameter|Type|Example|Description|
|---------|----|-------|-----------|
|RequestId|String|8D0C0AFC-E9CD-47A4-8395-5C31BF9B3E76|The ID of the request.

 |

## Examples {#demo .section}

Sample request

``` {#request_demo}

https://r-kvstore.aliyuncs.com/
? Action=ModifyInstanceMaintainTime
&InstanceId=r-bp1xxxxxxxxxxxxx
&MaintainStartTime=02:00Z
&MaintainEndTime=03:00Z
&<Common request parameters>

```

Sample success response

`XML` format

``` {#xml_return_success_demo}
<ModifyInstanceMaintainTimeResponse>
  <RequestId>8D0C0AFC-E9CD-47A4-8395-5C31BF9B3E76</RequestId>
</ModifyInstanceMaintainTimeResponse>

```

`JSON` format

``` {#json_return_success_demo}
{
	"RequestId":"8D0C0AFC-E9CD-47A4-8395-5C31BF9B3E76"
}
```

## Error codes { .section}

|HTTP status code|Error code|Error message|Description|
|----------------|----------|-------------|-----------|
|400|InvalidEndTime.Format|Specified end time is not valid.|The error message returned because the verification of the end time failed.|

[View error codes.](https://error-center.aliyun.com/status/product/R-kvstore)

