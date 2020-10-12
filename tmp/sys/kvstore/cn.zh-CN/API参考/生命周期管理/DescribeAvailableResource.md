# DescribeAvailableResource {#doc_api_R-kvstore_DescribeAvailableResource .reference}

调用DescribeAvailableResource查询指定可用区内可创建的实例。

## 调试 {#api_explorer .section}

[您可以在OpenAPI Explorer中直接运行该接口，免去您计算签名的困扰。运行成功后，OpenAPI Explorer可以自动生成SDK代码示例。](https://api.aliyun.com/#product=R-kvstore&api=DescribeAvailableResource&type=RPC&version=2015-01-01)

## 请求参数 {#parameters .section}

|名称|类型|是否必选|示例值|描述|
|--|--|----|---|--|
|Action|String|是|DescribeAvailableResource|系统规定参数，取值：DescribeAvailableResource。

 |
|RegionId|String|是|cn-beijing|地域ID，可调用[DescribeRegions](~~61012~~)查询。

 |
|ZoneId|String|是|cn-beijing-c|可用区ID，可调用[DescribeZones](~~94527~~)查询。

 |
|InstanceChargeType|String|否|PrePaid|付费类型，取值：

 -   PrePaid（包年包月）
-   PostPaid（按量付费）

 **说明：** 默认值：PrePaid。

 |
|AccessKeyId|String|否|Lxxxxxxxxxxxxxxw|阿里云颁发给用户的访问服务所用的密钥ID。

 |

## 返回数据 {#resultMapping .section}

|名称|类型|示例值|描述|
|--|--|---|--|
|AvailableZones| | |可用区详情。

 |
|RegionId|String|cn-beijing|地域ID。

 |
|SupportedEngines| | |引擎类型。

 |
|Engine|String|Redis|实例的引擎类型。

 |
|SupportedEngineVersions| | |引擎版本。

 |
|SupportedArchitectureTypes| | |架构类型。

 |
|Architecture|String|standard|实例架构：

 -   standard（标准版）
-   cluster（集群版）
-   rwsplit（读写分离版）

 |
|SupportedPerformanceTypes| | |性能类型。

 |
|PerformanceType|String|standard\_performance\_type|性能类型：

 -   standard\_performance\_type（标准性能）
-   enhance\_performance\_type（增强性能）

 |
|SupportedStorageTypes| | |存储类型。

 |
|StorageType|String|inmemory|存储类型：

 -   inmemory（高性能内存型）
-   hybrid（混合存储型）

 |
|SupportedNodeTypes| | |节点类型。

 |
|NodeType|String|double|节点类型：

 -   double（双副本）
-   single（单副本）
-   readone（1个只读节点）
-   readthree（3个只读节点）
-   readfive（5个只读节点）

 |
|SupportedPackageTypes| | |套餐类型。

 |
|AvailableResources| | |实例规格。

 |
|InstanceClass|String|redis.master.small.default|实例的规格。

 |
|PackageType|String|standard|套餐类型：

 -   standard（标准套餐）
-   customized（定制套餐）

 |
|Version|String|4.0|引擎版本。

 |
|ZoneId|String|cn-beijing-c|可用区ID。

 |
|RequestId|String|128BD75D-A423-4235-B777-811429BB6E4D|请求ID。

 |

## 示例 {#demo .section}

请求示例

``` {#request_demo}

https://r-kvstore.aliyuncs.com
?Action=DescribeAvailableResource
&RegionId=cn-beijing
&ZoneId=cn-beijing-c
&<公共请求参数>

```

正常返回示例

`XML` 格式

``` {#xml_return_success_demo}
<DescribeAvailableResourceResponse>
	  <RequestId>128BD75D-A423-4235-B777-811429BB6E4D</RequestId>
	  <AvailableZones>
		    <AvailableZone>
			      <ZoneId>cn-beijing-c</ZoneId>
			      <SupportedEngines>
				        <SupportedEngine>
					          <SupportedEngineVersions>
						            <SupportedEngineVersion>
							              <SupportedArchitectureTypes>
								                <SupportedArchitectureType>
									                  <SupportedPerformanceTypes>
										                    <SupportedPerformanceType>
											                      <PerformanceType>standard_performance_type</PerformanceType>
											                      <SupportedStorageTypes>
												                        <SupportedStorageType>
													                          <SupportedNodeTypes>
														                            <SupportedNodeType>
															                              <NodeType>double</NodeType>
															                              <SupportedPackageTypes>
																                                <SupportedPackageType>
																	                                  <PackageType>standard</PackageType>
																	                                  <AvailableResources>
																		                                    <AvailableResource>
																			                                      <InstanceClass>redis.logic.sharding.2g.8db.0rodb.8proxy.default</InstanceClass>
																		                                    </AvailableResource>
																		                                    <AvailableResource>
																			                                      <InstanceClass>redis.logic.sharding.2g.2db.0rodb.4proxy.default</InstanceClass>
																		                                    </AvailableResource>
																		                                    <AvailableResource>
																			                                      <InstanceClass>redis.logic.sharding.4g.2db.0rodb.4proxy.default</InstanceClass>
																		                                    </AvailableResource>
																		                                    <AvailableResource>
																			                                      <InstanceClass>redis.logic.sharding.4g.8db.0rodb.8proxy.default</InstanceClass>
																		                                    </AvailableResource>
																		                                    <AvailableResource>
																			                                      <InstanceClass>redis.logic.sharding.8g.8db.0rodb.8proxy.default</InstanceClass>
																		                                    </AvailableResource>
																		                                    <AvailableResource>
																			                                      <InstanceClass>redis.logic.sharding.8g.16db.0rodb.16proxy.default</InstanceClass>
																		                                    </AvailableResource>
																		                                    <AvailableResource>
																			                                      <InstanceClass>redis.logic.sharding.16g.16db.0rodb.16proxy.default</InstanceClass>
																		                                    </AvailableResource>
																		                                    <AvailableResource>
																			                                      <InstanceClass>redis.logic.sharding.16g.32db.0rodb.32proxy.default</InstanceClass>
																		                                    </AvailableResource>
																		                                    <AvailableResource>
																			                                      <InstanceClass>redis.sharding.16xlarge.default</InstanceClass>
																		                                    </AvailableResource>
																		                                    <AvailableResource>
																			                                      <InstanceClass>redis.sharding.32xlarge.default</InstanceClass>
																		                                    </AvailableResource>
																		                                    <AvailableResource>
																			                                      <InstanceClass>redis.logic.sharding.16g.256db.0rodb.256proxy.default</InstanceClass>
																		                                    </AvailableResource>
																	                                  </AvailableResources>
																                                </SupportedPackageType>
															                              </SupportedPackageTypes>
														                            </SupportedNodeType>
													                          </SupportedNodeTypes>
													                          <StorageType>inmemory</StorageType>
												                        </SupportedStorageType>
											                      </SupportedStorageTypes>
										                    </SupportedPerformanceType>
									                  </SupportedPerformanceTypes>
									                  <Architecture>cluster</Architecture>
								                </SupportedArchitectureType>
							              </SupportedArchitectureTypes>
							              <Version>4.0</Version>
						            </SupportedEngineVersion>
					            <Engine>redis</Engine>
					          </SupportedEngineVersions>
				        </SupportedEngine>
			      </SupportedEngines>
		    </AvailableZone>
	  </AvailableZones>
</DescribeAvailableResourceResponse>
```

`JSON` 格式

``` {#json_return_success_demo}
{
	"DescribeAvailableResourceResponse":{
		"RequestId":"128BD75D-A423-4235-B777-811429BB6E4D",
		"AvailableZones":{
			"AvailableZone":{
				"ZoneId":"cn-beijing-c",
				"SupportedEngines":{
					"SupportedEngine":{
						"SupportedEngineVersions":{
							"Engine":"redis",
							"SupportedEngineVersion":{
								"SupportedArchitectureTypes":{
									"SupportedArchitectureType":{
										"Architecture":"cluster",
										"SupportedPerformanceTypes":{
											"SupportedPerformanceType":{
												"PerformanceType":"standard_performance_type",
												"SupportedStorageTypes":{
													"SupportedStorageType":{
														"SupportedNodeTypes":{
															"SupportedNodeType":{
																"NodeType":"double",
																"SupportedPackageTypes":{
																	"SupportedPackageType":{
																		"PackageType":"standard",
																		"AvailableResources":{
																			"AvailableResource":[
																				{
																					"InstanceClass":"redis.logic.sharding.2g.8db.0rodb.8proxy.default"
																				},
																				{
																					"InstanceClass":"redis.logic.sharding.2g.2db.0rodb.4proxy.default"
																				},
																				{
																					"InstanceClass":"redis.logic.sharding.4g.2db.0rodb.4proxy.default"
																				},
																				{
																					"InstanceClass":"redis.logic.sharding.4g.8db.0rodb.8proxy.default"
																				},
																				{
																					"InstanceClass":"redis.logic.sharding.8g.8db.0rodb.8proxy.default"
																				},
																				{
																					"InstanceClass":"redis.logic.sharding.8g.16db.0rodb.16proxy.default"
																				},
																				{
																					"InstanceClass":"redis.logic.sharding.16g.16db.0rodb.16proxy.default"
																				},
																				{
																					"InstanceClass":"redis.logic.sharding.16g.32db.0rodb.32proxy.default"
																				},
																				{
																					"InstanceClass":"redis.sharding.16xlarge.default"
																				},
																				{
																					"InstanceClass":"redis.sharding.32xlarge.default"
																				},
																				{
																					"InstanceClass":"redis.logic.sharding.16g.256db.0rodb.256proxy.default"
																				}
																			]
																		}
																	}
																}
															}
														},
														"StorageType":"inmemory"
													}
												}
											}
										}
									}
								},
								"Version":"4.0"
							}
						}
					}
				}
			}
		}
	}
}
```

## 错误码 { .section}

访问[错误中心](https://error-center.aliyun.com/status/product/R-kvstore)查看更多错误码。

