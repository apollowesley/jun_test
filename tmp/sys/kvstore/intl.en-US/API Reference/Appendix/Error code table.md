# Error code table {#reference_mdv_55x_wdb .reference}

|Error code description|Code|message|httpStatusCode|
|----------------------|----|-------|--------------|
|The API called by the subaccount is unauthorized.|Forbidden.RAM|User not authorized to operate on the specified resource, or this API doesn’t support RAM.|403|
|The operation is unavailable in RAM mode.|Forbedden.NotSupportRAM|This action does not support accessed by RAM mode.|403|
|An exception or error occurs on the server.|ServiceUnavailable|The request has failed due to a temporary failure of the server.|503|
|The input instance status does not exist.|InvalidStatus.NotFound|The Status provided does not exist in our records.|404|
|The input parameter is invalid.|InvalidParameter|The specified parameter InstanceName is not valid.|400|
|A common user calls on management APIs.|Forbbiden.NotAdminUser|User not authorized to operate on the specified API as you are not the administrator.|403|
|The parameter is missing.|MissingParameter|Specified parameter is missing.|400|
|At least one of the InstanceName and NewPassword is included.|MissingParameter|InstanceName/NewPassword at least one is mandatory for this action.|400|
|No OwnerId is specified when this API is called.|MissingParameter|The input parameter OwnerId, OwnerAccount that is mandatory for processing this request is not supplied.|403|
|The specified Token is invalid.|InvalidToken.Malformed|The Specified parameter "Token" is not valid.|400|
|The specified InstanceName is invalid.|InvalidInstanceName.Malformed|The Specified parameter "InstanceName" is not valid.|400|
|The specified Password is invalid.|InvalidPassword.Malformed|The Specified parameter "Password" is not valid."|400|
|The specified Instances is invalid.|InvalidInstances.Malformed|The Specified parameter "Instances" is not valid.|400|
|The specified StartTime is invalid.|InvalidStartTime.Malformed|The Specified parameter "StartTime" is not valid.|400|
|The specified EndTime is invalid.|InvalidEndTime.Malformed|The Specified parameter "EndTime" is not valid.|400|
|The specified InstanceIds is invalid.|InvalidInstanceIds.Malformed|The Specified parameter "InstanceIds" is not valid.|400|
|The balance is insufficient.|InsufficientBalance|Your account does not have enough balance.|400|
|You have not performed real-name authentication.|RealNameAuthenticationError|Your account has not passed the real-name authentication yet.|403|
|The purchase quantity has exceeded the limit.|QuotaExcee|Living afterpay instances quota exceeded.|400|
|The capacity configuration is invalid.|InvalidCapacity.NotFound|The Capacity provided does not exist in our records.|400|
|A used client token is used for the request. However, the content of the request is different from that of the previous request with the used token.|IdempotentParameterMismatch|Request uses a client token in a previous request but is not identical to that request.|400|
|The storage in the specified zone is insufficient.|QuotaNotEnough|Quota not enough in this zone.|400|
|You are not authorized to call the order-class APIs.|Forbbiden.SubUser|The specified action is not available for you.|403|
|Access is denied by the Alibaba Cloud risk control system.|Forbidden.RiskControl|This operation is forbidden by Aliyun Risk Control system.|403|
|The specified Region does not exist.|InvalidRegion.NotFound|The RegionId or ZoneId provided does not exist in our records.|404|
|The specified ZoneId is invalid.|InvalidZoneId.NotFound|The ZoneId provided is invalid.|400|
|The instance ID does not exist.|InvalidInstanceId.NotFound|The InstanceId provided does not exist in our records.|404|
|The password is incorrect.|IncorrectPassword|The Password provided is not correct.|400|
|The service is unavailable.|ServiceNotSupported|The specified service is not supported.|400|
|A renewal and configuration change order does not take effect.|HasRenewChangeOrder|This instance has a renewChange order.|400|
|An internal error occurs.|InternalError|The request processing has failed due to some unknown error.|500|
|The backup time is invalid.|InvalidPreferredBackupTime|Specified preferred backup time is not valid.|400|
|The input backup type is invalid.|InvalidBackupType.Format|Specified backup type is not valid.|400|
|The input backup method is invalid.|InvalidBackupMethod.Format|Specified backup method is not valid.|400|
|A backup job already exists, which is not supported.|BackupJobExists|A backup job already exists in the specified DB instance.|400|
|The number of backup times exceeds the limit.|BackupTimesExceeded|Exceeding the daily backup times of this DB instance.|400|
|Input at least one parameter.|ParameterLeastAssociate|Must input at least one optional parameter.|400|
|The input backup retention period is invalid.|InvalidBackupRetentionPeriod.Malformed|Specified backup retention period is not valid.|400|
|The input next backup time is invalid.|InvalidPreferredBackupTime.Format|Specified preferred backup time is not valid.|400|
|The input backup cycle is invalid.|InvalidPreferredBackupPeriod.Malformed|Specified backup period is not valid.|400|
|The current instance type does not support this operation.|IncorrectDBInstanceType|Current DB instance type does not support this operation|400|
|The input key is invalid.|InvalidKey.Malformed|Specified key is not valid.|400|
|The signature is used.|SignatureNonceUsed|Specified signature nonce was used already.|400|
|No virtual IP address can be assigned.|AllocateVpcIp.NotEnough|Ip resource is not enough|400|
|Instances of the specified type cannot be created in the zone.|Zone.NotSupport|Specified zone does not support creating with instance class.|400|
|The specification code is invalid.|MissingClassCode|Capacity or InstanceClass is mandatory for this action.|400|
|The specified instance type is not supported.|InvalidDBInstanceClass.NotFound|Specified DB instance class is not found.|404|
|The instance is locked.|IncorrectDBInstanceLockMode|Current DB instance lock mode does not support this operation.|400|
|The backup set ID does not exist.|InvalidBackupSetID.NotFound|Specified backup set ID does not exist.|400|
|The instance status does not support this operation.|IncorrectDBInstanceState|Current DB instance state does not support this operation.|400|
|The resources are insufficient.|InsufficientResourceCapacity|There is insufficient capacity available for the requested instance.|400|
|The input end time is invalid.|InvalidEndTime.Format|Specified end time is not valid.|400|
|The input duration for reserving a classic IP address in invalid.|InvalidClassicExpiredDays.Format|The specified classicExpiredDays format is not valid.|400|
|The backup set status does not support this operation.|IncorrectBackupSetState|Current backup set state does not support operations.|400|
|The whitelist format is invalid.|InvalidSecurityIPList.Format|Specified security IP list format is not valid.|400|

