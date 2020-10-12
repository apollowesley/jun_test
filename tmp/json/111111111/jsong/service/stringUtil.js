
var stringUtil = function(){
	/*
		格式化数据表的表名,去除表名之间的'_'
	*/
	function formatTableName(tblName){
		var tblNames = tblName.split('_');
		var tblNameArray = [];
		for (var i = 1,len = tblNames.length; i < len; i++) {
			if(i==1){
				tblNameArray.push(tblNames[i]);
			}else{
				tblNameArray.push(firstUpperCase(tblNames[i]));
			}
		}
		return tblNameArray.join('');
	}
	/*
		格式化获取的数据表字段数据类型,去掉括号及其中的内容
	*/
	function formatFieldDataType(fieldDataType){
		if(fieldDataType.indexOf('(')>0){
			return fieldDataType.substring(0,fieldDataType.indexOf('('));
		}
		return fieldDataType;
	}
	/* 字符串首字母大写 */
	function firstUpperCase(s){
		return s.replace(/(\w)/,function(v){return v.toUpperCase()});
	}
	return {
		firstUpperCase:firstUpperCase,
		formatTableName:formatTableName,
		formatFieldDataType:formatFieldDataType
	}
}

module.exports = stringUtil;