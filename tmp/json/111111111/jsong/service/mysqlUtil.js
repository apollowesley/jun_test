var mysql = require('mysql');

var mysqlUtil = function(){
	/* 创建数据库连接 */
	var connect = mysql.createConnection({
	  host:'192.168.187.209',
	  user:'root',
	  password:'sxkj0818web',
	  database:'cylhfj_zmy',
	  port:'3306'
	});
	/* 获取数据表的字段信息 */
	var tableInfo = function(dbName,tblName,callback){
		connect.query("show columns from "+dbName+'.'+tblName,function(err,rows,fields){
        if(err){
            throw err;
        }else{
          callback(rows);
        }
    });
	};
	/* 获取数据库的数据表  */
	var tableList = function(dbName,callback){
		connect.query("show tables from "+dbName,function(err,rows,fields){
        if (err) {
            throw err;
        }else{
          callback(rows);//输出到客户端
        }
    });
	}
	return {
		tableList:tableList,
		tableInfo:tableInfo
	}
}
module.exports = mysqlUtil;