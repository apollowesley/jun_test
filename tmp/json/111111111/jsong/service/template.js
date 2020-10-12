var MysqlUtil = require('../service/mysqlUtil');
var mysql = new MysqlUtil();

var StringUtil = require('../service/stringUtil');
var stringUtil = new StringUtil();

var FileUtil = require('../service/fileUtil');
var fileUtil = new FileUtil();

var config = require('../service/config');



var template = function(){
	var _projectName ;
	var _className ;
	var _packagePath;
	var _javaFileDir ;
	var _classDir ;//package

	/*
		生成代码
		projectName:项目名称
		dbName:数据库名称
		tblName:数据表名称
	*/
	function ceateCode (projectName,dbName,tblName){
		//项目名称
		_projectName = projectName;
		//处理数据表名后等到的首字母小写的类名
		_className = stringUtil.formatTableName(tblName);
		//config.js配置文件中记录的java文件公共包路径
		_packagePath = config.packageRootName;
		//java文件存放路径
		_javaFileDir = fileUtil.projectRootPath()+'\\'+_projectName+'\\java\\'+_packagePath.split('.').join('\\')+'\\'+_projectName+'\\'+_className;
		//java文件具体的包路径
		_classDir = _packagePath+'.'+_projectName+'.'+_className;//package
		//从数据库中获取表信息,生成pojo和hbm文件
		mysql.tableInfo(dbName,tblName,function(rows){
			_pojo(rows);
		});
		_dao();
	}
	function _daoImpl(){

	}
	function _dao(){
		//文件存放路径
		var fileDir = _javaFileDir+'\\dao';
		//java类的包路径
		var classDir = _classDir+'.dao;'//package
		//类名(首字母大写,后面不带Dao)
		var className = stringUtil.firstUpperCase(_className);
		//需要引用的java文件
		var packages = config.publicPackageArray.concat();
		packages[packages.length] = 'import '+_classDir+'.bean.'+stringUtil.firstUpperCase(_className)+';';
		//java文件中的方法
		var methods = [];
		methods[0] = 'public void save('+className+' '+_className+') throws Exception;';
		methods[1] = 'public void delete(String id) throws Exception;';
		methods[2] = 'public void update('+className+' '+_className+') throws Exception;';
		methods[3] = 'public OperateEnterprise search(String id) throws Exception;';
		methods[4] = 'public List<OperateEnterprise> searchList('+className+' '+_className+', PageModel pageModel) throws Exception;';
		return _javaFile (fileDir,classDir,className+'Dao','interface',null,null,packages,null,methods);
	}
	//create pojo
	function _pojo(tableInfo){
		//文件存放路径
		var fileDir = _javaFileDir+'\\bean';
		//包路径
		var classDir = _classDir+'.bean;'//package
		//首字母大写的类名
		var className = stringUtil.firstUpperCase(_className);
		//java文件的父类
		var extend = 'BeanSupport';
		//java文件实现的接口
		var implement = '';
		//需要引入的java
		var importPackages = ['import com.sxkj.frame.utils.BeanSupport;'];
		var packageHash = {};
		var fields = [];
		var methods = [];
		for (var f of tableInfo) {
			var dbType = stringUtil.formatFieldDataType(f.Type);//字段数据库数据类型
			var javaType = config.dataTypeMap[dbType];//字段java数据类型
			//添加依赖的jar
			if(!packageHash[javaType]){
				importPackages.push(config.dataTypePackageMap[javaType]);
				packageHash[javaType] = true;
			}
			//添加字段
			fields.push('private '+javaType+' '+f.Field+';');
			//getter setter方法
			var method = 'public '+javaType+' get'+stringUtil.firstUpperCase(f.Field)+'(){'
							+' return '+f.Field+' ;'
							+' }'
							+'\n'
							+ 'public void set'+stringUtil.firstUpperCase(f.Field)+'('+javaType+' '+f.Field+'){'
							+' this.'+f.Field+'='+f.Field+';'
							+' }';
			methods.push(method);
		};
		return _javaFile (fileDir,classDir,className,'class',extend,implement,importPackages,fields,methods);
	}
	/*
		创建java文件模板方法
		fileDir:文件存放路径
		classDir:文件包路径
		className:java类名,同时也是文件名
		InterfaceOrClass:java文件类型,Interface||class
		extend:java文件的父类
		implement:java文件要实现的接口,用逗号(,)分隔
		importPackages:引用java文件的数值
		fields:java属性名称数组
		methods:java方法(体)数组
	*/
	function _javaFile (fileDir,classDir,className,interfaceOrClass,extend,implement,importPackages,fields,methods){
		var content = 'package '+classDir;//package
		content += '\n';
		content += importPackages.join('\n');//imports
		content += '\n';
		content += 'public '+interfaceOrClass+' '+className+' ';
		if(extend){
			content += 'extends '+extend+' ';
		}
		if(implement){
			content += 'implements '+implement+' ';
		}
		content += '{'//class
		content += '\n';
		if(fields&&fields.length>0){
			content += fields.join('\n');//fields
			content += '\n';
		}
		if(methods&&methods.length>0){
			content += methods.join('\n');//getter setter
			content += '\n';
		}
		
		content +='}'//end
		//console.log(content);
		return fileUtil.createFile(fileDir,className+'.java',content);
	}
	return {
		ceateCode:ceateCode
	}
}

module.exports = template;