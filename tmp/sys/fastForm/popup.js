var data = {};
var option = {};
var url;
var tabID;
var count = 0;

$(function(){
    $("#pageName").focus();
    $("#pageName").val('default');

    chrome.tabs.getSelected(null, function(tab){
        url = tab.url;
        tabID = tab.id;
        getOption();
        getAllData();
    });

	$("#saveForm").click(function(){
		saveForm();
	});

	$("#moveOption").click(function(){
		chrome.tabs.create({url: 'options.html'});
	});

    $(".fill").click( function() {
        chrome.tabs.executeScript(null, {file: 'fill/fill_form_' + $(this).attr('id') + '.js'});
        chrome.tabs.executeScript(null, {file: 'fill/fill_form.js'});
    });
    
    $("#clear").click( function() {
        chrome.tabs.executeScript(null, {file: 'fill/clear.js'});
    });
});

function getOption(){
    if(localStorage.option == undefined){
        option.dontSavePasswd = false;
        localStorage.option = JSON.stringify(option);
    }

    option = JSON.parse(localStorage.option);
}

function getAllData(){
    count = 0;
    $("#data").empty();

    data = localStorage.data;
    if(data){
        data = JSON.parse(data);
    } else {
        data = {};
    }

    if(data && data[url] != undefined){
        for(var i=0; i < data[url].length; i++){
            createRecordedHtml(i, data[url][i].title);
            count++;
        }
    }

    if(count == 0){
        $("#data").append('<span>unrecorded</span>');
    }
}

function getData(i){
    if(data[url] == undefined){
        data = JSON.parse(localStorage.data);
    } else if(data[url][i] == undefined){
        data = JSON.parse(localStorage.data);
    }

    return data[url][i];
}

function createRecordedHtml(i, title){
    if(count == 0){
        $("#data").empty();
        count++;
    }
    $("#data").append('<div class="record">'+
            '<div id="setFormData' + i + '" class="record_title" >'+title+
            '</div>' +
			'<div id="viewData' + i + '" title="info" class="record_icon">'+
            ' <i class="icon-th-list icon12"></i>'+
            '</div><div id="deleteData' + i + '" title="deleteData" class="record_icon">'+
            '<i class="icon-remove icon12"></i>'
            +'</div></div>');

	$("#setFormData" + i).click(function(){
		setFormData(i);
	});

	$("#viewData" + i).click(function(){
        viewData(i);
    });
    $("#deleteData" + i).click(function(){
		deleteData(i);
	});
}


function setFormData(i){
    chrome.extension.getBackgroundPage().setFormData(i);
}

function saveForm(){
    getAllData();
    if($("#pageName").val() == ''){
        $("#new_err").html('Title is required.').hide().fadeIn();
        $("#pageName").focus();
        return false;
    }

	console.log('saveform');
    chrome.extension.getBackgroundPage().saveForm($("#pageName").val());

    if(data[url] != undefined){
        createRecordedHtml(data[url].length, $("#pageName").val());
    } else {
        createRecordedHtml(0, $("#pageName").val());
    }
    countBadge('+');
}

function countBadge(op){
    var badgeNum = 0;

    if(data[url] != undefined){
        if(op == '+'){
            badgeNum = data[url].length + 1;
        } else {
            badgeNum = data[url].length;
        }
    } else {
        if(op == '+'){
            badgeNum += 1;
        } else {
            badgeNum = 0;
            chrome.browserAction.setBadgeBackgroundColor({color : [0,32,42,130]});
        }
    }
    chrome.browserAction.setBadgeText({text : String(badgeNum), tabId: tabID});
}



function viewData(i){
    var d = getData(i);
    $("#data").empty();
    toggleAddArea();
    var html = '<pre><div id="dir1">'+
        'URL: <input type="text" name="url" value="'+url+'">\n'+
        'Title: <input type="text" name="title" value="'+d.title+'">\n'+
        'Parameter:\n'+
        '</div>'+
        '<div id="dir2">';

    for(var key in d.params){
        var type = d.params[key].type;
        if(type == 'textarea'){
            html += key+':<textarea type="text" name="'+key+'" title="'+type+'">'+
                    d.params[key].value+'</textarea>\n';
        } else if(type == 'password') {
            html += key+':<input type="password" name="'+key+'"'+
                    ' value="'+d.params[key].value+'" title="'+type+'"';
            if(option.dontSavePasswd){
                html += ' readonly="readonly"';
            }
            html += '>\n';
        } else {
            html += key+':<input type="text" name="'+key+'"'+
                    ' value="'+d.params[key].value+'" title="'+type+'">\n';
        }
    }

    html += '</div></pre>';
    html += '<div id="back" class="button1" style="width:70px; float:left">'+
        ' <i class="icon-arrow-left icon12"></i> <span>back</span></div>';
    html += '<div id="editData" class="button1" style="margin-left:10px;width:70px; float:left">'+
        '<i class="icon-retweet icon12"></i> <span>save</span></div>';
    html += '<div id="deleteData" class="button1" style="margin-left:10px;width:70px;float:left">'+
        '<i class="icon-remove icon12"></i> <span>delete</span></div><br />';
    html += '<div id="edit_err" class="err"></div>';

    $("#data").html(html);

	$("#back").click(function(){
		 back();
	});
	$("#editData").click(function(){
		 editData(i);
	});
	$("#deleteData").click(function(){
		deleteData(i);
        toggleAddArea();
	});

}

function deleteData(i){
    var d = getData(i);
    if(d == undefined){
        return false;
    }

    data[url].splice(i--, 1);
    countBadge('-');
    saveStorage();
    getAllData();
    
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

    if(url == changeUrl){
        data[url].splice(i--, 1, obj);
    } else {
        if(data[changeUrl] == undefined){
            data[changeUrl] = [];
        }
        data[changeUrl].push(obj);
        data[url].splice(i--, 1);
    }
    saveStorage();
    getAllData();
    toggleAddArea(); 
}

function back(){
    toggleAddArea(); 
    getAllData();
}

function saveStorage(){
    localStorage.data = JSON.stringify(data);
}

function toggleAddArea(){
    $("#add_area").animate({height:"toggle"},"fast");
}
