select
'1' as '序号',
kqj.ygh as '用户编号',
kqj.name as '姓名',
'' as '性别' ,
'' as '民族',
'' as '身份证' ,
'' as '有效期限',
'' as '签发机关' ,
'' as '劳动合同编号',
'' as '出生日期(1989-08-15)' ,
'' as '移动电话' ,
'' as '家庭电话' ,
'' as '地址',
kqj.card_no as '卡片编号' ,
right('00000000' + KQSF.dbo.BigInt2HexStr(kqj.sn),8) as '卡片内码' ,
(select '('+cug.code+')'+ cug.name from onecard.dbo.CardUserGroup cug where cug.name = kqj.bm) as '分组编码',
kqj.kye as '初始存款' ,
'' as '状态说明'
from kqj AS kqj
where kqj.zt = 1
order by kqj.ygh asc