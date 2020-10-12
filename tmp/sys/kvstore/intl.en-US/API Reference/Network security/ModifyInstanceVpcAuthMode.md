# ModifyInstanceVpcAuthMode {#doc_api_R-kvstore_ModifyInstanceVpcAuthMode .reference}

You can call this operation to enable or disable password-free access for an ApsaraDB for Redis instance. When the password-free access feature is enabled, Elastic Compute Service \(ECS\) instances in the same Virtual Private Cloud \(VPC\) can access the ApsaraDB for Redis instance without the password. You can also use the username and password to access the ApsaraDB for Redis instance.

For more information about how to perform the corresponding operation in the console, see [Enable password-free access](../../../../reseller.en-US/User Guide/Manage instances/Enable password-free access.md#).

## Debugging {#apiExplorer .section}

Alibaba Cloud provides OpenAPI Explorer to simplify API usage. You can use [OpenAPI Explorer](https://api.aliyun.com/#product=R-kvstore&api=ModifyInstanceVpcAuthMode) to search for APIs, call APIs, and dynamically generate SDK example code.

## Request parameters {#parameters .section}

|Parameter|Type|Required|Example|Description|
|---------|----|--------|-------|-----------|
|Action|String|Yes|ModifyInstanceVpcAuthMode| The operation that you want to perform. Set this parameter to ModifyInstanceVpcAuthMode.

 |
|InstanceId|String|Yes|r-bp1xxxxxxxxxxxxx| The ID of the instance for which you want to enable or disable password-free access.

 |
|VpcAuthMode|String|Yes|Close| Specifies whether to enable password authentication for access within the VPC. Valid values:

 -   Open: enables password authentication.
-   Close: disables password authentication.

 **Note:** Default value: Open.

 |
|AccessKeyId|String|No|Lxxxxxxxxxxxxxxw| The AccessKey ID that Alibaba Cloud provides for you to access services.

 |

## Response parameters {#resultMapping .section}

|Parameter|Type|Example|Description|
|---------|----|-------|-----------|
|RequestId|String|ABAF95F6-35C1-4177-AF3A-70969EBDF623| The ID of the request.

 |

## Examples {#demo .section}

Sample request

``` {#request_demo}

https://r-kvstore.aliyuncs.com/
? Action=ModifyInstanceVpcAuthMode
&InstanceId=r-bp1xxxxxxxxxxxxx
&VpcAuthMode=Close
&<Common request parameters>

```

Sample success response

`XML` format

``` {#xml_return_success_demo}
<ModifyInstanceVpcAuthModeResponse>
  <RequestId>ABAF95F6-35C1-4177-AF3A-70969EBDF623</RequestId>
</ModifyInstanceVpcAuthModeResponse>

```

`JSON` format

``` {#json_return_success_demo}
{
	"RequestId":"ABAF95F6-35C1-4177-AF3A-70969EBDF623"
}
```

## Error codes {#section_llf_mvh_eyz .section}

[View error codes.](https://error-center.aliyun.com/status/product/R-kvstore)

