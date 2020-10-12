var express = require('express');
var fs = require('fs');
var path = require('path');

var fileUtil = function(){
	/*
		此方法基本废弃
		创建文件夹
		dirName:文件夹路径
	*/
	function createDir(dirName){
		fs.exists(dirName,function(flag){
			if(!flag){
				fs.mkdir(dirName,function(err){
					if(err){
						throw err;
					}else{
						console.log('create done');
					}
				});
			}
		});
	}
	/* 
		创建文件 
		path:文件存放目录
		fileName:文件名
		content:文件内容
	*/
	function createFile(path,fileName,content){
		if(mkdirs(path)){
			fs.writeFile(path+'\\'+fileName,content,function(err){
		    if(err){
		      throw err;
		    }else{
		      return '';
		    }
		  });
		}
		return fileName;
	}
	/* 获取项目根目录 */
	function projectRootPath(){
		return __dirname.substring(0,__dirname.lastIndexOf('\\'));
	}
	/*
		递归创建文件夹
		dirpath:文件夹路径
	*/
	function mkdirs(dirpath){
		if (!fs.existsSync(dirpath)) {
      var pathtmp;
      dirpath.split(path.sep).forEach(function(dirname) {
        if(pathtmp){
          pathtmp = path.join(pathtmp, dirname);
        }else{
          pathtmp = dirname;
        }
        if(!fs.existsSync(pathtmp)){
          if (!fs.mkdirSync(pathtmp)){
            return false;
          }
        }
      });
    }
    return true; 
	}
	return {
		mkdirs:mkdirs,
		createDir:createDir,
		projectRootPath:projectRootPath,
		createFile:createFile
	}
}

module.exports = fileUtil;
