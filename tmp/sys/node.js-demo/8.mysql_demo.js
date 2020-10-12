var mysql = require('mysql');
var connection = mysql.createConnection({
	host : 'localhost',
	user : 'root',
	password : 'root',
	port: '3306',
	database: 'watchDB'
});
connection.connect();
var modSql = 'UPDATE res_mall SET name = ? WHERE Id = ?';
var modSqlParams = ['南昌军事装备中心', 10];
// 增删改
connection.query(modSql,modSqlParams,function (err, result) {
	if(err){
		console.log('[UPDATE ERROR] - ',err.message);
		return;
	}
	console.log('--------------------------UPDATE----------------------------');
	console.log('UPDATE affectedRows',result.affectedRows);
	console.log('-----------------------------------------------------------------\n\n');
});
connection.end();