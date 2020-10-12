'use strict';

var dbName ;
document.getElementById('lookDb').addEventListener('click',function(){
	var url = '/db/selectTables';
	dbName = document.getElementById('dbName').value;

	post(url,'dbName='+dbName,function(response){
		//console.dir(response);
		var retData = JSON.parse(response);
		//console.log(tables)
		var tableHTML = `<table class="table table-condensed table-bordered table-hover">
						<thead>
							<tr class="danger">
								<th>全选</th>
								<th>表名</th>
								<th colspan="2">操作</th>
							</tr>
						</thead>
						<tbody>`;
		for(var e of retData.tables){
			tableHTML += `<tr>
				<td><input type="checkbox" id="${e['Tables_in_medEnt']}" name="tableList" value="${e['Tables_in_medEnt']}"></td>
				<td><label for="${e['Tables_in_medEnt']}">${e['Tables_in_medEnt']}</label></td>
				<td><button onclick="tableFieldInfo('${e['Tables_in_medEnt']}');">查看</button></td>
				<td><button onclick="code('${e['Tables_in_medEnt']}');">生成代码</button></td>
				</tr>`
		}
		tableHTML += `</tbody></table>`;
		document.getElementById('tableDiv').innerHTML = tableHTML;
	});
});

function tableFieldInfo(tblName){
	var url = '/db/showTableInfo';
	post(url,'tblName='+tblName,function(response){
		var retData = JSON.parse(response);
		//console.dir(retData.tableInfo);
		document.getElementById('infoDiv').innerHTML = `<pre>${formatJson(retData.tableInfo)}</pre>`;
	});
}
function code(tblName){
	var url = '/db/createCode';
	var projectName = document.getElementById('projectName').value;
	console.log('dbIndex.js >>>',dbName+'>>>'+tblName);
	post(url,'projectName='+projectName+'&dbName='+dbName+'&tblName='+tblName,function(response){
		console.log(response);
	});
}