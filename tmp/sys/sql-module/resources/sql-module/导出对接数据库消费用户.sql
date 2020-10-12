select
'1' as '序号', cu.CustomerCode as '用户编号',cu.CustomerName as '姓名',
'' as '性别' , '' as '民族', cu.CustomerCode as '身份证' ,  '' as '劳动合同编号', ''  as '出生日期(1989-08-15)' ,
'' as '移动电话' , '' as '家庭电话' , '' as '地址', cu.CustomerCode as '卡片编号' ,c.PhysicalCode as '卡片内码' , '('+d.DeptCode+')'+d.DeptName ,'' as '状态说明',c.Balance as '金额'
from Card c left join Customer cu on c.CustomerID = cu.CustomerID left join Department d on cu.DeptID = d.DeptID where c.State = 1
