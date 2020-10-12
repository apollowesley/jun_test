# SwitchNetwork {#doc_api_R-kvstore_SwitchNetwork .reference}

You can call SwitchNetwork to switch the network type of an ApsaraDB for Redis instance. from classic network to VPC.

For more information about how to perform the corresponding operation in the console, see [Set the network type](../../../../reseller.en-US/User Guide/Manage instances/Set the network type.md#).

## Debugging {#apiExplorer .section}

Alibaba Cloud provides OpenAPI Explorer to simplify API usage. You can use [OpenAPI Explorer](https://api.aliyun.com/#product=R-kvstore&api=SwitchNetwork) to search for APIs, call APIs, and dynamically generate SDK example code.

## Request parameters {#parameters .section}

|Parameter|Type|Required|Example|Description|
|---------|----|--------|-------|-----------|
|Action|String|Yes|SwitchNetwork| The operation that you want to perform. Set this parameter to SwitchNetwork.

 |
|InstanceId|String|Yes|r-j6cxxxxxxxxxxxxxx| The ID of the instance for which you want to switch the network type.

 |
|AccessKeyId|String|No|Lxxxxxxxxxxxxxxw| The AccessKey ID that Alibaba Cloud provides for you to access services.

 |
|ClassicExpiredDays|String|No|30| The retention period of the connection address of the classic network. Unit: days. Valid values: 14, 30, 60, and 120. This parameter must be specified if RetainClassic is set to True.

 **Note:** For more information about how to modify the retention period after the network type is switched to VPC, see [EN-US\_TP\_3196.md\#](reseller.en-US/API Reference/Manage instances/ModifyInstanceNetExpireTime.md#).

 |
|RetainClassic|String|No|True| Specifies whether to retain the connection address of the classic network.

 -   True
-   False

 **Note:** Default value: False.

 |
|TargetNetworkType|String|No|VPC| The network type to which you want to switch. Currently, you can only switch from classic network to VPC. Set this parameter to VPC.

 |
|VSwitchId|String|No|vsw-sdrxxxxxxxxxxxxxxxxxx| The ID of the VSwitch in the VPC after you switch the network type of the instance to VPC. This parameter must be specified if the VpcId parameter is set.

 |
|VpcId|String|No|vpc-bp1xxxxxxxxxxxxxxxxxx| The ID of the VPC where the instance is located after you switch the network type of the instance to VPC.

 |

## Response parameters {#resultMapping .section}

|Parameter|Type|Example|Description|
|---------|----|-------|-----------|
|RequestId|String|F0997EE8-F4C2-4503-9168-85177ED78C70| The ID of the request.

 |

## Examples {#demo .section}

Sample request

``` {#request_demo}

https://r-kvstore.aliyuncs.com
? Action=SwitchNetwork
&InstanceId=r-j6cxxxxxxxxxxxxx
&TargetNetworkType=VPC
&VpcId=vpc-bp1xxxxxxxxxxxxxxxxxx
&VSwitchId=vsw-sdrxxxxxxxxxxxxxxxxxx
&RetainClassic=True
&ClassicExpiredDays=30
&<Common request parameters>

```

Sample success response

`XML` format

``` {#xml_return_success_demo}
<SwitchNetworkResponse>
  <requestId>F0997EE8-F4C2-4503-9168-85177ED78C70</requestId>
</SwitchNetworkResponse>

```

`JSON` format

``` {#json_return_success_demo}
{
	"requestId":"F0997EE8-F4C2-4503-9168-85177ED78C70"
}
```

## Error codes {#section_8h1_5al_5wf .section}

[View error codes.](https://error-center.alibabacloud.com/status/product/R-kvstore)

