select * from t_auth_user;
select * from t_auth_role;
select * from t_auth_user_role;
select * from t_auth_permission;
select * from t_auth_role_permission;

delete from t_auth_user;
delete from t_auth_role;
delete from t_auth_user_role;
delete from t_auth_permission;
delete from t_auth_role_permission;

drop table t_auth_user;
drop table t_auth_role;
drop table t_auth_user_role;
drop table t_auth_permission;
drop table t_auth_role_permission;