var JG = (function(){
  var _div,_fileDiv,_tableDiv,_table,_filename,_metaArea,_dataArea;
  function init(divId){
    // 获取页面div,作为装载容器
    _div = document.getElementById(divId);
    if(!_div){
      _div = document.createElement('div');
      _div.setAttribute('id',divId);
      document.appendChild(_div);
    }
    // 创建装载文件列表的左侧div
    _fileDiv = document.createElement('div');
    _fileDiv.setAttribute('id','fileDiv');
    _fileDiv.className = 'left';
    _div.appendChild(_fileDiv);
    // 创建装载table能元素的右侧div
    _tableDiv = document.createElement('div');
    _tableDiv = document.createElement('div');
    _tableDiv.className = 'right';
    _div.appendChild(_tableDiv);

    // 创建命名文件和定义示例数据长度的文本框
    var filenameLabel = document.createElement('label');
    filenameLabel.innerHTML = '文件名:';
    _tableDiv.appendChild(filenameLabel);

    var filename = document.createElement('input');
    filename.setAttribute('type','text');
    filename.setAttribute('id','filename');
    _tableDiv.appendChild(filename);
    
    var listsizeLabel = document.createElement('label');
    listsizeLabel.innerHTML = '示例数据条数:';
    _tableDiv.appendChild(listsizeLabel);

    var listsize = document.createElement('input');
    listsize.setAttribute('type','text');
    listsize.setAttribute('id','listsize');
    _tableDiv.appendChild(listsize);

    var crtBtn = document.createElement('input');
    crtBtn.setAttribute('id','crtBtn');
    crtBtn.setAttribute('type','button');
    crtBtn.setAttribute('value','生成数据');
    crtBtn.addEventListener('click',function(){
      generator();
    });
    _tableDiv.appendChild(crtBtn);

    var br = document.createElement('br');
	_tableDiv.appendChild(br);
    // 添加table
    tableInit();

    showFile();
  }
  //生成元数据及json示例数据
  function generator(){
    _filename = document.getElementById('filename').value;
    listsize = document.getElementById('listsize').value;
    if(!_filename){
      alert('请填写文件名');
      return;
    }
    if(!listsize||isNaN(listsize)){
      alert('请填写数字,确定需要生成json数据的长度');
      return;
    }

    var md = jsonMetaData();
    var datas = generateJsons(md,listsize);
    showData();
    _metaArea.innerHTML = formatJson(md);
    _dataArea.innerHTML = formatJson(datas);
  }
  //数据展示
  function showData(){
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
            showFile();
          }
        });
      });
      div.appendChild(btn);
      _div.appendChild(div);
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
      _div.appendChild(div);
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
  // 显示model下的文件
  function showFile(){
    while(_fileDiv.hasChildNodes()){
      _fileDiv.removeChild(_fileDiv.lastChild);
    }
    $.ajax({
      url:'/myfs/loopDir',
      type:'get',
      success:function(ret){
        var ul = document.createElement('ul');
        var files = ret.fileArray;

        for(var i in files){
          var filename = files[i];
          var li = document.createElement('li');
          li.innerHTML = '<a href="javascript:;">'+filename+'</a>';
          // 好用的代码
//           li.num = i;
//           li.addEventListener('click',function(){
//             alert(files[this.num]);

//           });
          // 好用的代码
          li.addEventListener('click',(function(num){
            return function(){
              editJson(files[num]);
            }
          })(i));

          ul.appendChild(li);
        }
        _fileDiv.appendChild(ul);
      }
    });
  }
  function editJson(filename){
    $.ajax({
      url:'/myfs/read',
      data:'filetype=model&filename='+filename,
      type:'post',
      success:function(ret){
        var content = JSON.parse(ret.content);
        edit(content);
      }
    });
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
  // 初始化table
  function tableInit(){
    _table = document.createElement('table');
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
    _tableDiv.appendChild(_table);
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
  //删除表格除表头的其他部分
  function empty(){
    var rowLen = _table.rows.length;
    for(var i=rowLen-1;i>0;i--){
      _table.deleteRow(i);
    }
  }
  return {
    init:init
  };
})();