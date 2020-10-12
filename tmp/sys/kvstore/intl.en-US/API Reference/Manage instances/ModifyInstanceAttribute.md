# ModifyInstanceAttribute {#reference_jsv_ntx_wdb .reference}

## Description {#section_z1z_2kw_wbb .section}

This API is used to modify the attributes of an instance, including the name or password.

## Request parameters {#section_fn4_gkw_wbb .section}

|Name|Type|Required|Description|
|----|----|--------|-----------|
|<Common request parameters\>|-|-|For more information, see [Public parameters](intl.en-US/API Reference/Common Parameters.md#section_hph_dhp_wbb).|
|Action|String|Yes|The API name. Value: ModifyInstanceAttribute.|
|InstanceId|String|Yes|Instance id, globally unique.|
|InstanceName|String|No|Modified instance name. The name is a string of 2 to 128 characters and must start with a letter \(uppercase or lowercase\) or a Chinese character. Special characters, such as the at sign \(@\), slash \(/\), colon \(:\), equal sign \(=\), quotation mark \(“\), angle brackets \(<\>\), braces \(\{\}\), and square brackets \(\[\]\) and space, are not supported.|
|NewPassword|String|No|Instance password. The password is a string of 8 to 30 characters and must contain uppercase letters, lowercase letters, and numbers.**Note:** The following special characters are not supported now: The following special characters are not supported now: Exclamation mark \(!\), angle brackets \(<\>\), parentheses \(\(\)\), square brackets \(\[\]\), braces \(\{\}\), comma \(,\), backquote \(\`\), tilde \(~\), period \(.\), hyphen \(-\), underscore \(\_\), at sign \(@\), number sign \(\#\), dollar sign \($\), percent sign \(%\), caret \(^\), ampersand \(&\), and asterisk \(\*\).

|

## Response parameters {#section_e4w_jkw_wbb .section}

|Name|Type|Description|
|----|----|-----------|
|<Common response parameters\>|-|For more information, see [Public return parameters](intl.en-US/API Reference/Common Parameters.md#section_rjr_zgp_wbb).|

## Request example {#section_d3l_4kw_wbb .section}

```
https://r-kvstore.aliyuncs.com
<Common request parameters>
&Action= ModifyInstanceAttribute
&InstanceId=657e361a074646d5
&NewPassword=Qa12345678
&InstanceName=TestInstance
```

## Response example {#section_hjp_tkw_wbb .section}

```

"RequestId" : "E3B35BEA-9EB0-402C-88CF-C46CCCC1EE59" 

```

