/*查询某个拥有某个角色*/
select u.name,r.name
from users u inner join roleuser ru on u.id=ru.uid
             inner join roles r on ru.rid=r.id;
             
/*某角色拥有某菜单*/
select r.name,m.name
from roles r inner join rolemenu rm on r.id=rm.rid
             inner join menus m on rm.mid=m.id;

             
/*查询某人拥有某个菜单*/
select m.*
from users u inner join roleuser ru on u.id=ru.uid
             inner join roles r on ru.rid=r.id
             inner join rolemenu rm on r.id=rm.rid
             inner join menus m on rm.mid=m.id;
             
             
             根据url和用户id断定这个用户 是否拥有某个菜单的权限
SELECT COUNT(1)
FROM menus m INNER JOIN rolemenu rm ON m.id=rm.mid
             INNER JOIN roles r ON r.id=rm.rid
             INNER JOIN roleuser ru ON r.id=ru.rid
WHERE ru.uid='U002' AND url='/jsps/role/role.jsp' ;