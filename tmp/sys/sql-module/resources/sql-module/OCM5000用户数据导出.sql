select
'1' as '序号', pui.UserID as '用户编号',pui.UserName as '姓名',
'' as '性别' , '' as '民族', '' as '身份证' , '' as '有效期', '' as '签发机关', '' as '劳动合同编号', ''  as '出生日期(1989-08-15)' ,
'' as '移动电话' , '' as '家庭电话' , '' as '地址', puc.CardID as '卡片编号' ,pci.CardInnerID as '卡片内码' , '('+LTRIM(RTRIM(pdi.DeptID))+')'+LTRIM(RTRIM(pdi.DeptName)) as '用户分组' ,blan.MONEYREMAINED as '金额','' as '状态说明'
from dbo.PubUserCard puc
left join PubCardInfo pci on puc.CardID = pci.CardID
left join PubUserInfo pui on pui.UserID = puc.UserID
left join PubDepartInfo pdi on pui.DeptID = pdi.DeptID
left join RESCUSTOMERBALANCE blan on puc.UserID = blan.USERID
where puc.RelationStatus = 1
