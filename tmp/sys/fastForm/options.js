var data;
var option;
var now;

$(function(){
    getAllRecord();
    setOptionValue();

	// event handler
	$("#addDelete").click(function(){
		allDelete();
	});
	$("#op_password").click(function(){
		saveOpPasswd();
	});
	$("#dontSave").click(function(){
		toggleCheck();
	});
});

function getAllRecord(){
    $("#all_data").hide().empty();
    if(!localStorage.data){
        $("#all_data").html('no data...');
        return;
    }

    data = JSON.parse(localStorage.data);
    //sort data
    var ary = new Array();
    for(var i in data){
        ary.push({key:i, value:data[i]});
    }

    ary = ary.sort(dataSort);
    for(var i = 0; i < ary.length; i++){
        $("#all_data").append('<div id="expandUrl' + i + '" class="title">'+ary[i].key+'</div>');
		var ii  = i;
		$("#expandUrl" + i).click(function(){
			expandURL(ary[ii].key);
		});
    }
    
    $("#all_data").animate({height:"toggle"});
}

function setOptionValue(){
    if(localStorage.option == undefined){
        option = {};
        option.dontSavePasswd = false;
        localStorage.option = JSON.stringify(option);
    }

    option = JSON.parse(localStorage.option);

    if(option.dontSavePasswd){
        $("#op_password").attr("checked", true);
    } else {
        $("#op_password").attr("checked", false);
    }
}

function dataSort(a, b){
    return a.key < b.key ? -1 : a.key > b.key ? 1 : 0; 
}

function expandURL(url){
    now = url;
    $("#form_data").hide().empty().append('<div class="url">'+url+'</div>');

    for(var i = 0; i < data[url].length; i++){
        viewTitle(url, data[url][i], i);
    }

    $("#form_data").animate({height:"toggle"});
}

function viewTitle(url, form, i){

    var html = '<div id="viewData" class="detail" title="'+i+'" >'+
        form.title+'</div>';


    $("#form_data").append(html);
	$("#viewData").click(function(){
		viewData(url, i);
	});
}



function viewData(url, i){
    $("#form_data").hide().empty().append('<div class="url">'+url+'</div>');
    var form = data[url][i];
    var html = '<pre><div id="dir1">'+
        'Title: <input type="text" name="title" value="'+form.title+'">\n'+
        'URL: <input type="text" name="url" value="'+url+'">\n'+
        'Parameter:\n'+
        '</div>'+
        '<div id="dir2">';

    for(var key in form.params){
        var type = form.params[key].type;
        if(type == 'textarea'){
            html += key+':<textarea type="text" name="'+key+'" title="'+type+'">'+
                    form.params[key].value+'</textarea>\n';
        } else if(type == 'password'){
            html += key+':<input style="width:150px" type="password" name="'+key+'"'+
                    ' value="'+form.params[key].value+'" title="'+type+'"';
            if(option.dontSavePasswd){
                html += ' readonly="readonly"';
            }
            html += '><span class="viewPasswd" data-key="' + key + '" '+
                    'class="button1" style="padding: 0px 5px 0px 5px">view</span>\n';
        } else {
            html += key+':<input type="text" name="'+key+'"'+
                    ' value="'+form.params[key].value+'" title="'+type+'">\n';
        }
    }

    html += '</div></pre>';
    html += '<div id="back" class="button1" style="margin-left:20px; width:70px; float:left">'+
            '<i class="icon-arrow-left icon12"></i>  <span>back</span></div>';
    html += '<div id="editData" class="button1" style="margin-left:10px;width:70px; float:left">'+
            '<i class="icon-retweet icon12"></i> <span>save</span></div>';
    html += '<div id="deleteData" class="button1" style="margin-left:10px;width:70px;float:left">'+
            '<i class="icon-remove icon12"></i> <span>delete</span></div><br />';
    html += '<div id="edit_err" class="err"></div>';

    $("#form_data").append(html).animate({height:"toggle"});

	$(".viewPasswd").each(function(){
		$(this).click(function(){
            viewPasswd($(this).attr("data-key"), this);
		});
	});
	$("#back").click(function(){
		back();
	});
	$("#editData").click(function(){
		editData(i);
	});
	$("#deleteData").click(function(){
		deleteData(i);
	});
}

function viewPasswd(target, obj){
    $(obj).html($("#dir2 > :input[name='"+target+"']").val());
}

function editData(i){
    var changeUrl = $("#dir1 > input[name='url']").val();
    var obj = {
        title: $("#dir1 > input[name='title']").val(),
        params: {}
    };

    if(! obj.title){
        $("#edit_err").html('Title is required.').hide().fadeIn();
        $("#dir1 > input[name='title']").focus();
        return false;
    }

    //search all input tag
    $("#dir2 > :input").each(function(){
        var type = $(this).attr("title");
        var key = $(this).attr("name");
        var value = $(this).val();

        obj.params[key] = {
            "type": type,
            "value": value
        };
    });

    if(now == changeUrl){
        data[now].splice(i--, 1, obj);
    } else {
        if(data[changeUrl] == undefined){
            data[changeUrl] = [];
        }
        data[changeUrl].push(obj);
        data[now].splice(i--, 1);
    }
    saveStorage();
    getAllRecord();
    back();
}

function deleteData(i){
    data[now].splice(i--, 1);
    if(data[now].length == 0){
        delete data[now];
    }

    saveStorage();
    getAllRecord();
    back();
}

function saveStorage(){
    localStorage.data = JSON.stringify(data);
}

function back(){
    expandURL(now);
}

function toggleAddArea(){
    $("#form_data").animate({height:"toggle"});
}

function allDelete(){
    var checkMessage = 'really?';
    if($("#del_message").html() != checkMessage){
        $("#del_message").html(checkMessage);
        return;
    }

    localStorage.removeItem('data');
    $("#del_message").html("DELETE");
    getAllRecord();
    $("#form_data").hide();
}

function toggleCheck(){
    if($("#op_password").attr("checked")){
        $("#op_password").attr("checked",false);
    } else {
        $("#op_password").attr("checked",true);
    }

    saveOpPasswd();
}

function saveOpPasswd(){
    if($("#op_password").attr("checked")){
        option.dontSavePasswd = true;
    } else {
        option.dontSavePasswd = false;
    }
    localStorage.option = JSON.stringify(option);
}

