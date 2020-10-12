/*
在div中创建动态的table元素,每行记录对象的一个属性,
最终把所有的属性组成json,同时可以生成以这些属性为key的json数据
*/
var JG = function(divId){
	var _div = document.getElementById(divId),
		_table = document.createElement('table'),
		_metaArea,_dataArea,_filename;
	(function (){
		_table.setAttribute('width','100%');
		var thead = _table.insertRow();
		thead.insertCell().innerHTML = '字段名称';
		thead.insertCell().innerHTML = '中文名称';
		thead.insertCell().innerHTML = '数据类型';
		thead.insertCell().innerHTML = '数据长度';
		var addBtn = document.createElement('button');
		addBtn.innerHTML = '新增';
		addBtn.addEventListener('click',function(){
		  addRow();
		});
		thead.insertCell().appendChild(addBtn);
		thead.setAttribute('bgColor','#ccc');
		_div.appendChild(_table);
	}());
	//公用方法,生成元数据及json示例数据
	function generator(filename,listsize,callback){
		_filename = filename;
		var md = jsonMetaData();
		var datas = generateJsons(md,listsize);
		showData(callback);
		_metaArea.innerHTML = formatJson(md);
		_dataArea.innerHTML = formatJson(datas);
	}
	//公用方法,编辑已有的元数据,并生成新的示例数据
	function edit (jsonObj){
		empty();
		if(jsonObj&&jsonObj.model&&jsonObj.model.length>0){
			for(var i=0,len=jsonObj.model.length;i<len;i++){
				var rowLen = _table.rows.length;
				var newRow = _table.insertRow();
				newRow.insertCell().innerHTML = '<input type="text" id="name_'+rowLen+'">';
				newRow.insertCell().innerHTML = '<input type="text" id="cnName_'+rowLen+'">';
				newRow.insertCell().innerHTML = '<select id="dataType_'+rowLen+'"><option value="Integer">Integer<option value="Double">Double<option value="String">String<option value="Date">Date<option value="Boolean">Boolean</select>';
				newRow.insertCell().innerHTML = '<input type="text" id="dataSize_'+rowLen+'">';

				var remBtn = document.createElement('button');
				remBtn.innerHTML = '删除';
				remBtn.addEventListener('click',function(){
					removeRow(this);
				});
				newRow.insertCell().appendChild(remBtn);
				newRow.setAttribute('customIndex',rowLen);

				var rowData = jsonObj.model[i];
				document.getElementById('name_'+rowLen).value = rowData.key;
				document.getElementById('cnName_'+rowLen).value = rowData.cnName;
				document.getElementById('dataType_'+rowLen).value = rowData.dataType;
				document.getElementById('dataSize_'+rowLen).value = rowData.dataSize?rowData.dataSize:0;
			}
		}
	}
	//动态添加table的tr
	function addRow (){
      var rowLen = _table.rows.length;
      var newRow = _table.insertRow();
      newRow.insertCell().innerHTML = '<input type="text" id="name_'+rowLen+'">';
      newRow.insertCell().innerHTML = '<input type="text" id="cnName_'+rowLen+'">';
      newRow.insertCell().innerHTML = '<select id="dataType_'+rowLen+'"><option value="Integer">Integer<option value="Double">Double<option value="String">String<option value="Date">Date<option value="Boolean">Boolean</select>';
      newRow.insertCell().innerHTML = '<input type="text" id="dataSize_'+rowLen+'">';

      var remBtn = document.createElement('button');
      remBtn.innerHTML = '删除';
      remBtn.addEventListener('click',function(){
        removeRow(this);
      });
      newRow.insertCell().appendChild(remBtn);
      newRow.setAttribute('customIndex',rowLen);
	}
	//删除行
	function removeRow(obj){
      var row = obj.parentNode.parentNode;
      var table = row.parentNode;
      table.removeChild(row);
    }
    //生成单条json数据
	function generateJson(modelJson){
	  modelJson = JSON.parse(modelJson);//eval('('+modelJson+')');
	  var data = {};  
	  if(modelJson&&modelJson.model&&modelJson.model.length>0){
		for(var i=0,len=modelJson.model.length;i<len;i++){
		  var tmp = modelJson.model[i];
		  var key = tmp['key'];
		  if(key=='id'){
			if(tmp['dataType']=='Integer'){
			  data.id = counter();
			}else{
			  data.id = uuid(tmp['dataSize']||20);
			}
		  }else{
			var tmpData = randomData(tmp['dataType'],tmp['dataSize']);
			eval('data.'+key+'="'+tmpData+'"');
		  }
		}
	  }

	  return data;  
	}
	//生成多条json数据
	function generateJsons(modelJson,dataSize){
	  var list = [];
	  dataSize = dataSize||10;
	  for(var i=0;i<dataSize;i++){
		var data = generateJson(modelJson);
		list.push(data);
	  }
	  var result = {
		list : list
	  };
	  return obj2str(result);
	}
	//把table中的数据组织成json对象
	function jsonMetaData(){
      var models = [];
      for(var i=1,rowLen = _table.rows.length;i<rowLen;i++){
      	var index = _table.rows[i].getAttribute('customIndex');
        var model = {};
        model.key = document.getElementById('name_'+index).value;
        model.cnName = document.getElementById('cnName_'+index).value;
        model.dataType = document.getElementById('dataType_'+index).value;
        model.dataSize = document.getElementById('dataSize_'+index).value;
        models.push(model);
      }
      if(models){
        var result = {
            model:models
        };
        return obj2str(result);
      }
      return;
	}
	//数据展示
    function showData(callback){
		if(!_metaArea){
			_metaArea = document.createElement('textarea');
			_metaArea.setAttribute('id','metaArea');
			_metaArea.setAttribute('cols',50);
			_metaArea.setAttribute('rows',20);

			var div = document.createElement('div');
			div.className = 'left';

			div.appendChild(_metaArea);

			var br = document.createElement('br');
			div.appendChild(br);

			var btn = document.createElement('input');
			btn.setAttribute('type','button');
			btn.setAttribute('value','保存元数据');
			btn.addEventListener('click',function(){
				var metaData = document.getElementById('metaArea').value;
			
				saveMetaData('/myfs/createFile',"filetype=model&filename="+_filename+'&filedata='+metaData,function(ret){
					ret = eval('('+ret+')');
					if(ret.success==1){
						alert('file save success');
						callback();
					}
				});
			});
			div.appendChild(btn);
			document.body.appendChild(div);
		}
		if(!_dataArea){
			_dataArea = document.createElement('textarea');
			_dataArea.setAttribute('id','dataArea');
			_dataArea.setAttribute('cols',50);
			_dataArea.setAttribute('rows',20);

			var div = document.createElement('div');
			div.className = 'right';
			div.appendChild(_dataArea);

			var br = document.createElement('br');
			div.appendChild(br);
			
			var btn = document.createElement('input');
			btn.setAttribute('type','button');
			btn.setAttribute('value','保存数据');
			btn.addEventListener('click',function(){
				var metaData = document.getElementById('dataArea').value;
			
				saveMetaData('/myfs/createFile',"filetype=list&filename="+_filename+'&filedata='+metaData,function(ret){
					if(ret.success==1){
						alert('file save success');
					}
				});
			});
			div.appendChild(btn);
			document.body.appendChild(div);
		}
    }
    function saveMetaData(url,postData,callback){
		if(!postData){
			alert('please input data');
			return;
		}
		if(typeof postData =='object'){
			postData = (function(obj){ // 转成post需要的字符串.
    			var str = "";
    			for(var prop in obj){
        			str += prop + "=" + obj[prop] + "&"
    			}
    			return str;
			})(postData);
		}
		var xmlhttp;
		if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
			xmlhttp=new XMLHttpRequest();
		}else  {// code for IE6, IE5
			xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
		}
		xmlhttp.onreadystatechange=function()  {
			if (xmlhttp.readyState==4 && xmlhttp.status==200){
				callback(xmlhttp.responseText);
			}
		}
		xmlhttp.open("POST",url,true);//"model2.json"   
		xmlhttp.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
		xmlhttp.send(postData);
    }
    //json转字符串
    function obj2str(o){   
		var r = [];   
		if(typeof o =="string") return "\""+o.replace(/([\'\"\\])/g,"\\$1").replace(/(\n)/g,"\\n").replace(/(\r)/g,"\\r").replace(/(\t)/g,"\\t")+"\"";   
		if(typeof o =="undefined") return "";   
		if(typeof o == "object"){   
			if(o===null){
				return "null";
			}else if(!o.sort){   
				for(var i in o){
					r.push('"'+i+'":'+obj2str(o[i]));
				}   
				r="{"+r.join()+"}";  
			}else{   
				for(var i =0;i<o.length;i++){   
					r.push(obj2str(o[i]));
				}   
				r="["+r.join()+"]";  
			}   
			return r;   
		}   
		return o.toString();   
	}
	//生成uuid
	function uuid(len, radix) {
		var chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
		var uuid = [], i;
		radix = radix || chars.length;
		if (len) {
		  for (i = 0; i < len; i++) uuid[i] = chars[0 | Math.random()*radix];
		} else {
		  var r;
		  uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
		  uuid[14] = '4';
		  for (i = 0; i < 36; i++) {
			if (!uuid[i]) {
			  r = 0 | Math.random()*16;
			  uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
			}
		  }
		}
		return uuid.join('');
	}
	//指定范围生成随机数
	function rd(n,m){
		var c = m-n+1;  
		return Math.floor(Math.random() * c + n);
	}
	//随机日期
	function randomDate(){
		var current = new Date();
		var year = rd(1900,current.getFullYear());
		var month = rd(0,11);
		var day = rd(1,28);
		switch(month){
			case 1:case 3:case 5:case 7: case 8: case 10: case 12:
				day = rd(1,31);
			break;
			case 4:case 6: case 9: case 11:
				day = rd(1,30);
			break;
			case 2:
				if (((year % 4)===0) && ((year % 100)!==0) || ((year % 400)===0)) {
					day = rd(1,29);
				} else { 
					day = rd(1,28); 
				}
			break;
		}
		return year+'-'+(month+1)+'-'+day;
	}
	//计数器
	var counter = (function(){
		var count = 0;
		return function(){
			return ++count;
		}
	})();
	//格式化json
	var formatJson = function(json, options) {
		var reg = null,
			formatted = '',
			pad = 0,
			PADDING = '    '; 
		options = options || {};
		options.newlineAfterColonIfBeforeBraceOrBracket = (options.newlineAfterColonIfBeforeBraceOrBracket === true) ? true : false;
		options.spaceAfterColon = (options.spaceAfterColon === false) ? false : true;

		if (typeof json !== 'string') {
			json = JSON.stringify(json);
		} else {
			json = JSON.parse(json);
			json = JSON.stringify(json);
		}

		reg = /([\{\}])/g;
		json = json.replace(reg, '\r\n$1\r\n');

		reg = /([\[\]])/g;
		json = json.replace(reg, '\r\n$1\r\n');

		reg = /(\,)/g;
		json = json.replace(reg, '$1\r\n');

		reg = /(\r\n\r\n)/g;
		json = json.replace(reg, '\r\n');

		reg = /\r\n\,/g;
		json = json.replace(reg, ',');

		if (!options.newlineAfterColonIfBeforeBraceOrBracket) {         
			reg = /\:\r\n\{/g;
			json = json.replace(reg, ':{');
			reg = /\:\r\n\[/g;
			json = json.replace(reg, ':[');
		}
		if (options.spaceAfterColon) {          
			reg = /\:/g;
			json = json.replace(reg, ': ');
		}
		var jsonArray = json.split('\r\n');
		for(var j=0,len=jsonArray.length;j<len;j++){
			var i = 0,
				indent = 0,
				padding = '';
			var node = jsonArray[j];

			if (node.match(/\{$/) || node.match(/\[$/)) {
				indent = 1;
			} else if (node.match(/\}/) || node.match(/\]/)) {
				if (pad !== 0) {
					pad -= 1;
				}
			} else {
				indent = 0;
			}

			for (i = 0; i < pad; i++) {
				padding += PADDING;
			}

			formatted += padding + node + '\r\n';
			pad += indent;
		}
		return formatted;
	};   
	//随机数据
	function randomData(dataType,dataSize){
	  var data ;
	  switch(dataType){
		case 'String':
		  var dataSize = dataSize||20;
		  data = uuid(dataSize);
		  break;
		case 'Integer':
		  var tmp = 1;
		  var dataSize = dataSize||2;
		  for(var i=0;i<dataSize;i++){
			tmp = tmp * 10 ;
		  }
		  data = Math.ceil(Math.random()*tmp);
		  break;
		case 'Double':
		  var tmp = 1;
		  var dataSize = dataSize||2;
		  for(var i=0;i<dataSize;i++){
			tmp = tmp * 10 ;
		  }
		  data = Math.ceil((Math.random()*tmp)*tmp)/tmp;
		  break;
		case 'Date':
		  data = randomDate();
		  break;
		case 'Boolean':
		  data = Math.ceil(Math.random()*10)%2;
		  break;            
		default:
		  data = 'unknow dataType';
		  break;
	  }
	  return data ;
	}
	//删除表格除表头的其他部分
	function empty(){
	  var rowLen = _table.rows.length;
	  for(var i=rowLen-1;i>0;i--){
		_table.deleteRow(i);
	  }
	}
	return {
		generator:generator,
		edit:edit
	}
};
