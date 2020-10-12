select
LTRIM(RTRIM(dev.DevID)) as '设备编号(不能重复)' ,
dev.DevName as '设备名称' ,
(dvg.DevGroupMac+'.'+dev.DevMac) as '设备地址' ,
'门禁' as '设备类型' ,
'启用' as '设备状态(启用/不启用)',
LTRIM(RTRIM(dvg.DevGroupID)) as '设备组编号(不能重复)' ,
dvg.DevGroupName as '设备组名称' ,
'TCP' as '设备组连接类型(TCP/COM)',
dvg.DevServerAddr as '设备组地址',
dvg.DevConnPort as '设备组(波特率/端口号)' ,
'192.168.1.1' as '主机地址(ip)',
'启用' as '设备组状态(启用/禁用)',
'' as '处理状态' from dbo.PubDeviceInfo dev left join dbo.PubDeviceGroupInfo dvg on dev.DevGroupID = dvg.DevGroupID;