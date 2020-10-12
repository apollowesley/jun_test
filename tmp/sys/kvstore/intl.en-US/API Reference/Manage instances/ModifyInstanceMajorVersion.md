# ModifyInstanceMajorVersion {#doc_api_R-kvstore_ModifyInstanceMajorVersion .reference}

You can call this operation to upgrade the major version of an ApsaraDB for Redis instance.

For more information about how to perform the corresponding operation in the console, see [Upgrade the major version](~~101764~~).

## Debugging {#apiExplorer .section}

Alibaba Cloud provides OpenAPI Explorer to simplify API usage. You can use [OpenAPI Explorer](https://api.aliyun.com/#product=R-kvstore&api=ModifyInstanceMajorVersion) to search for APIs, call APIs, and dynamically generate SDK example code.

## Request parameters {#parameters .section}

|Parameter|Type|Required|Example|Description|
|---------|----|--------|-------|-----------|
|InstanceId|String|Yes|r-bp1xxxxxxxxxxxxx|The ID of the instance for which you want to upgrade the major version.

 |
|MajorVersion|String|Yes|4.0|The target major version. Currently, `4.0` is supported.

 |
|Action|String|No|ModifyInstanceMajorVersion|The operation that you want to perform. Set this parameter to ModifyInstanceMajorVersion.

 |
|AccessKeyId|String|No|Lxxxxxxxxxxxxxxw|The AccessKey ID that Alibaba Cloud provides for you to access services.

 |
|EffectTime|String|No|0|The time when the major version is upgraded. Valid values:

 -   0: upgrades the major version immediately.
-   1: upgrades the major version in the maintenance window.

 **Note:** Default value: 0.

 |

## Response parameters {#resultMapping .section}

|Parameter|Type|Example|Description|
|---------|----|-------|-----------|
|RequestId|String|AA587FB2-2593-4DFE-BE13-2494C2DF0CBF|The ID of the request.

 |

## Examples {#demo .section}

Sample request

``` {#request_demo}

https://r-kvstore.aliyuncs.com/
? Action=ModifyInstanceMajorVersion
&InstanceId=r-bp1xxxxxxxxxxxxx
&MajorVersion=4.0
&EffectTime=0
&<Common request parameters>

```

Sample success response

`XML` format

``` {#xml_return_success_demo}
<ModifyInstanceMajorVersionResponse>
  <RequestId>AA587FB2-2593-4DFE-BE13-2494C2DF0CBF</RequestId>
</ModifyInstanceMajorVersionResponse>

```

`JSON` format

``` {#json_return_success_demo}
{
	"RequestId":"AA587FB2-2593-4DFE-BE13-2494C2DF0CBF"
}
```

## Error codes { .section}

[View error codes.](https://error-center.aliyun.com/status/product/R-kvstore)

