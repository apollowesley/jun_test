# TransformToPrePaid {#doc_api_R-kvstore_TransformToPrePaid .reference}

You can call this operation to change the billing method of an ApsaraDB for Redis instance from Pay-As-You-Go to subscription.

For more information about how to perform the corresponding operation in the console, see [Switch to subscription](../../../../reseller.en-US/User Guide/Manage instances/Switch to subscription.md#).

**Note:** Currently, you cannot change the billing method of an instance from subscription to Pay-As-You-Go.

## Debugging {#apiExplorer .section}

Alibaba Cloud provides OpenAPI Explorer to simplify API usage. You can use [OpenAPI Explorer](https://api.aliyun.com/#product=R-kvstore&api=TransformToPrePaid) to search for APIs, call APIs, and dynamically generate SDK example code.

## Request parameters {#parameters .section}

|Parameter|Type|Required|Example|Description|
|---------|----|--------|-------|-----------|
|Action|String|Yes|TransformToPrePaid| The operation that you want to perform. Set this parameter to TransformToPrePaid.

 |
|InstanceId|String|Yes|r-bp1xxxxxxxxxxxxx| The ID of the instance for which you want to change the billing method.

 |
|Period|Long|Yes|12| The subscription period of the instance. Unit: months. Valid values: 1, 2, 3, 4, 5, 6, 7, 8, 9, 12, 24, 36.

 |
|AccessKeyId|String|No|Lxxxxxxxxxxxxxxw| The AccessKey ID that Alibaba Cloud provides for you to access services.

 |
|AutoPay|Boolean|No|true| Specifies whether to enable auto renewal. Valid values:

 -   true
-   false

 **Note:** Default value: true. If you set this parameter to false, please [manually renew the instance](../../../../reseller.en-US/User Guide/Manage instances/Renew an instance.md#section_p2y_lll_vdb) before the instance expires.

 |

## Response parameters {#resultMapping .section}

|Parameter|Type|Example|Description|
|---------|----|-------|-----------|
|EndTime|String|2019-01-18T16:00:00Z| The expiration time of the instance after the billing method was changed from Pay-As-You-Go to subscription.

 |
|OrderId|String|111111111111111| The ID of the order.

 |
|RequestId|String|426F1356-B6EF-4DAD-A1C3-DE53B9DAF586| The ID of the request.

 |

## Examples {#demo .section}

Sample request

``` {#request_demo}

https://r-kvstore.aliyuncs.com/
? Action=TransformToPrePaid
&InstanceId=r-bp1xxxxxxxxxxxxx
&Period=12
&<Common request parameters>

```

Sample success response

`XML` format

``` {#xml_return_success_demo}
<TransformToPrePaidResponse>
  <OrderId>111111111111111</OrderId>
  <RequestId>426F1356-B6EF-4DAD-A1C3-DE53B9DAF586</RequestId>
  <EndTime>2019-01-18T16:00:00Z</EndTime>
</TransformToPrePaidResponse>

```

`JSON` format

``` {#json_return_success_demo}
{
	"RequestId":"426F1356-B6EF-4DAD-A1C3-DE53B9DAF586",
	"OrderId":"111111111111111",
	"EndTime":"2019-01-18T16:00:00Z"
}
```

## Error codes {#section_j5e_p50_esq .section}

[View error codes.](https://error-center.aliyun.com/status/product/R-kvstore)

