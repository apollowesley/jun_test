'use strict';

var express = require('express');
var router = express.Router();
var fs = require('fs');
var path = require('path');

var baseDir = './public/data';

router.get('/travel',function(req,res,next){
  console.log('qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq');
  //travel(basePath,callback);
  var files = loopDir(baseDir);
  console.dir(files);
});
router.post('/createFile',function(req,res){
  //console.dir(req.body);
  var filetype = req.body.filetype;
  var filename = req.body.filename;
  var filedata = req.body.filedata;
  console.log('filetype>>>'+filetype);
  fs.writeFile(baseDir+'/'+filetype+'/'+filename,filedata,function(err){
    if(err){
      console.error(err);
    }else{
      res.json({success:1});
    }
  });
});
router.post('/createCode',function(req,res){
  var dbName = req.body.dbName;
  var tblName = req.body.tblName;
  console.log(dbName+''+tblName);
});
router.post('/read',function(req,res,next){
  //console.dir(req.body);
  var filetype = req.body.filetype;
  var filename = req.body.filename;
  fs.readFile(baseDir+'/'+filetype+'/'+filename,{encoding:'utf8'},function(err,data){
    if(err){
      console.error(err);
      return;
    }
    res.json({content:data});
  });
});
router.get('/loopDir',function(req,res,next){
  res.json({fileArray:loopDir(baseDir+'/model')});
});
function travel(dir,callback){
  fs.readdirSync(dir).forEach(function(file){
    //console.log('dir:'+dir);
    //console.log('file:'+file)
    var pathname = path.join(dir,file);
    if(fs.statSync(pathname).isDirectory()){
      travel(pathname,callback);
    }else{
      callback(pathname);
    }
  });
}
function loopDir(dir){
  var files = [];
  fs.readdirSync(dir).forEach(function(file){
    files.push(file);
  });
  return files;  
};


module.exports = router;