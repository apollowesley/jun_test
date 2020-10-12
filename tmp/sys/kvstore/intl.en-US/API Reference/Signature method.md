# Signature method {#reference_xnf_5tx_wdb .reference}

ApsaraDB for Redis performs authentication on the sender of each access request. Therefore, no matter whether HTTP or HTTPS is used to submit a request, the request must contain the signature information. ApsaraDB for Redis performs symmetric encryption using the AccessKey ID and AccessKey Secret to verify the identity of request senders.  

The AccessKey ID and AccessKey Secret are officially issued to visitors by Alibaba Cloud \(visitors can apply for and manage them on the Alibaba Cloud official website\). The Access Key ID indicates the identity of the visitor. The Access Key Secret is the secret key to encrypt the signature string and verify the signature string on the server. It must be kept strictly confidential and must be made only available to Alibaba Cloud and the authenticated visitor.  

## Sign an access request {#section_nzb_r3p_wbb .section}

Follow these steps to sign an access request:

1.  Use request parameters to construct a canonicalized query string.
    1.  Sort all request parameters alphabetically by parameter names. \(The request parameters include the “public request parameters” and the custom parameters for the given request APIs described in this document, but do not include the Signature parameter mentioned in the “public request parameters”.\)

        **Note:** When a request is submitted using the GET method, these parameters constitute the parameter section of the request URI \(that is, the section in the URI following “?” and connected by “&”\).    

    2.  Encode the name and value of each request parameter.

        Perform URL encoding on the names and values using the UTF-8 character set. The URL encoding rules are as follows:  

        -   Do not encode uppercase letters A-Z, lowercase letters a-z, numbers 0-9, and characters including the hyphen \(-\), underscore \(\_\), period \(.\), and tilde \(~\).
        -   Encode other characters in the format of  `%XY`, with `XY` representing the ASCII code in hexadecimal notation of the characters. For example, encode the English double quotes \(“\) as   `%22`.
        -   Encode extended UTF-8 characters in the format of `%XY%ZA…`.  
        -   Encode the space \( \) as  `%20` rather than the plus sign \(+\).
        **Note:** Generally, libraries supporting the URL encoding \(for example, java.net.URLEncoder in Java\) are all encoded according to the rules for `application/x-www-form-urlencoded` of the MIME type.   If this encoding method is used, replace the plus sign \(+\) in the encoded strings with `%20`, the asterisk \(\*\) with `%2A`, and change `%7E` back to the tilde \(~\) to generate the encoding strings specified by the preceding rules.

    3.  Connect the encoded parameter names and values with the equal sign \(=\).
    4.  Then, sort the parameter name and value pairs connected by equal signs in alphabetical order, and connect them with an ampersand \(&\) to produce the canonicalized query string.
2.  Use the canonicalized query string obtained in the preceding step to construct the string for signature calculation according to the following rules:

    ```
     StringToSign=
     HTTPMethod + “&” +
     percentEncode(“/”) + ”&” +
     percentEncode(CanonicalizedQueryString)
    ```

    Where,

    -   `HTTPMethod`  indicates the HTTP method used to submit a request, for example, GET.
    -   `percentEncode(“/”)` indicates the encoded value for the character “/“ according to the URL encoding rules described in Step 1.ii, namely `%2F`.
    -   `percentEncode(CanonicalizedQueryString)` indicates the encoded string of the canonicalized query string constructed in Step 1, produced by following the URL encoding rules described in Step 1.ii.  
3.  Based on the RFC2104 definition, use the preceding string used for the signature to calculate the signature HMAC value.

    **Note:**    When the signature is calculated, the key is the Access Key Secret you hold adding the & character \(ASCII:38\), and the SHA1 hashing algorithm is used.

4.  According to the Base64 encoding rules, encode the preceding HMAC value into a string to obtain the signature value.
5.  Add the obtained signature value to the request parameters as the Signature parameter. The request signing process is complete.

    **Note:** When the obtained signature value is submitted to the ApsaraDB for Redis server as the final request parameter value, the URL encoding must be performed for the value in compliance with the RFC3986 rules, which is the same as that for other parameter values.  


## Example {#section_yw2_wjp_wbb .section}

If DescribeDBInstances is used as an example, the request URL before signing is:

```
http://r-kvstore.aliyuncs.com/?Timestamp=2013-06-01T10:33:56Z&Format=XML&AccessKeyId=testid&Action=DescribeInstances&SignatureMethod=HMAC-SHA1&RegionId=region1&SignatureNonce=NwDAxvLU6tFE0DVb&Version=2015-01-01&SignatureVersion=1.0
```

The calculated string to be signed StringToSign is as follows:  

```
GET&%2F&AccessKeyId%3Dtestid&Action%3DDescribeInstances&Format%3DXML&RegionId%3Dregion1&SignatureMethod%3DHMAC-SHA1&SignatureNonce%3DNwDAxvLU6tFE0DVb&SignatureVersion%3D1.0&Timestamp%3D2013-06-01T10%253A33%253A56Z&Version%3D2015-01-01
```

It is assumed that the Access Key ID is `testid`, the Access Key Secret is `testsecret`, and the Key used for HMAC calculation is `testsecret&`. The calculated signature value is `BIPOMlu8LXBeZtLQkJTw6iFvw1E=`.

The signed request URL is \(the Signature parameter added\):

```
http://r-kvstore.aliyuncs.com/?Timestamp=2013-06-01T10%3A33%3A56Z&Format=XML&AccessKeyId=testid&Action=DescribeInstances&SignatureMethod=HMAC-SHA1&RegionId=region1&SignatureNonce=NwDAxvLU6tFE0DVb&SignatureVersion=1.0&Version=2015-01-01&Signature=BIPOMlu8LXBeZtLQkJTw6iFvw1E%3D
```

