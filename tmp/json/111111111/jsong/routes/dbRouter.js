var express = require('express');
var router = express.Router();
var path = require('path');
//
var MysqlUtil = require('../service/mysqlUtil');
var mysql = new MysqlUtil();
//
var StringUtil = require('../service/stringUtil');
var stringUtil = new StringUtil();
//
var FileUtil = require('../service/fileUtil');
var fileUtil = new FileUtil();
//
var Template = require('../service/template');
var template = new Template();

var dbName;//存储数据库名称 
/* 页面 */
router.get('/', function(req, res, next) {
    res.render('dbIndex', { title: 'code template' });
});
/* 查询指定数据库的表 */
router.post('/selectTables',function(req,res){
    dbName = req.body.dbName;
    mysql.tableList(dbName,function(rows){
        res.json({tables:rows});//输出到客户端
    });

});
/* 显示指定表的 create 信息 */
router.post('/showTableInfo',function(req,res){
    var tblName = req.body.tblName;
    mysql.tableInfo(dbName,tblName,function(rows){
        res.json({tableInfo:rows});
    });
});
/* 生成代码 */
router.post('/createCode',function(req,res){
    var projectName = req.body.projectName;
    var dbName = req.body.dbName;
    var tblName = req.body.tblName;
    
    var tblNameArray = tblName.split(',');
    for (var i=0,len=tblNameArray.length;i<len;i++) {
        template.ceateCode(projectName,dbName,tblNameArray[i]);
    };
});

module.exports = router;
