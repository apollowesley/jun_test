/**
 * 获取当前网址名称
 * @returns
 */
function getCurrentPath(){
	return window.document.location.href;
}

function getContextPath(){
	var pathName=window.document.location.pathname;
	var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
	return projectName;
}
