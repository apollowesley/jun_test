chrome.tabs.onUpdated.addListener(function(tabId, changeInfo, tab){
    var data = localStorage.data;
    if(! data){
        return;
    }
    
    data = JSON.parse(data);
    if(data[tab.url] != undefined){
        setBadge(data[tab.url].length, tabId);
    }
});

function setBadge(count, tabId){
    chrome.browserAction.setBadgeText({text : String(count), tabId: tabId});
    chrome.browserAction.setBadgeBackgroundColor({color : [0,32,42,130]});
}

function setFormData(i){
    chrome.tabs.getSelected(null, function(tab){
        var data = JSON.parse(localStorage.data);
        var obj = {
            "action": "setFormData",
            "params": data[tab.url][i].params
        };
        chrome.tabs.sendRequest(tab.id, obj);
    });
}

function saveForm(pageName){
    chrome.tabs.getSelected(null, function(tab){
        chrome.tabs.sendRequest(tab.id,
            {"action": "saveForm", "pageName": pageName},
            function(resp){
				console.log(resp);

                var data = localStorage.data;
                if(data){
                    data = JSON.parse(data);
                } else {
                    data = {};
                }

                var option = JSON.parse(localStorage.option);
                if(option.dontSavePasswd){
                    for(var key in resp.params){
                        if(resp.params[key].type == 'password'){
                            resp.params[key].value = '';
                        }
                    }
                }

                if(data[tab.url] == undefined){       
                    data[tab.url] = new Array(resp);
                } else {
                    data[tab.url].push(resp);
                }
                localStorage.data = JSON.stringify(data);
            });
    });
}

