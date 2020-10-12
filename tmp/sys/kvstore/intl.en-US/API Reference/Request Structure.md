# Request Structure {#reference_hxw_htx_wdb .reference}

## Endpoint {#section_q33_k2p_wbb .section}

The service access address of ApsaraDB for Redis APIs is `r-kvstore.aliyuncs.com`.

**Note:** example.com is the project customized access domain address,  connect to the Administrator for the domain address.

## Communication protocol {#section_h2m_m2p_wbb .section}

Request communication through HTTP or HTTPS is supported. We recommend that requests be sent through HTTPS for enhanced security.

## Request method {#section_cck_n2p_wbb .section}

The system supports sending of HTTP GET requests. In this mode, the request parameters must be included in the request URL.

## Request parameters {#section_m1m_n2p_wbb .section}

You must specify the operation to be executed, namely, the Action parameter \(for example, CreateInstance\), for each request, and the public request parameters required for each operation and specific request parameters of the specified operation.

## Character encoding {#section_trq_q2p_wbb .section}

Requests and responses are encoded using the UTF-8 character set.

