var mysql = require('mysql');
var connection = mysql.createConnection({
	host : 'localhost',
	user : 'root',
	password : 'root',
	port: '3306',
	database: 'watchDB'
});
connection.connect();
var sql = 'SELECT * FROM res_mall';
// 查
connection.query(sql,function (err, result) {
	if(err){
		console.log('[SELECT ERROR] - ',err.message);
		return;
	}
	console.log('--------------------------SELECT----------------------------');
	console.log(result);
	console.log('------------------------------------------------------------\n\n');
});
connection.end();