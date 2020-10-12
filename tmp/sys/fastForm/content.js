chrome.extension.onRequest.addListener(
    function(req, sender, sendResponse){
        if(req.action == 'setFormData'){
            setFormData(req.params);
        } else if(req.action == 'saveForm'){
            var obj = saveForm(req.pageName);
            sendResponse(obj);
        }
    }
);

function saveForm(pageName){
    var obj = {"title": pageName, "params": {}};

    //search all input tag
    $(":input").each(function(){
        var type = this.type;
        var key = this.name;
        var value = '';

        //excluded submit and hidden and button or nameless tag
        if(type == 'submit' || type == 'hidden' || type == 'button' || ! key){
            return true; // equal continue
        }

        //get value
        if(type == 'radio' || type == 'checkbox'){
            if($(this).attr("checked")){
                value = $(this).val();
            }
        } else if(type == 'select-multiple' && $(this).val() != null){
            value = $(this).val().join(",");
        } else if($(this).val() != null){
            value = $(this).val();
        }

        //crate data set
        if(obj["params"][key] == undefined){
            obj["params"][key] = {
                "type"  : type,
                "value" : value
            };
        } else {
            if(obj["params"][key]["value"] && value){
                obj["params"][key]["value"] = 
                    obj["params"][key]["value"] + "," + value;
            } else if(value) {
                obj["params"][key]["value"] = value;
            }
        }
    });

    return obj;
}


function setFormData(req){
    for(var key in req){
        if(req[key].type == 'text' || req[key].type == 'password' ){
            if($("input[name='"+key+"']").attr("name") == undefined){
                errParam('input', key);
                continue;
            }
            $("input[name='"+key+"']").val(req[key].value);
        } else if (req[key].type == 'checkbox'){
            if($("input[name='"+key+"']").attr("name") == undefined){
                errParam('input', key);
                continue;
            }
            $("input[name='"+key+"']").val(req[key].value.split(","));
        } else if(req[key].type == 'radio'){
            if($("input[name='"+key+"']").attr("name") == undefined){
                errParam('input', key);
                continue;
            }
            $("input[name='"+key+"']").val([req[key].value]);
        } else if(req[key].type == 'select-one'){
            if($("select[name='"+key+"']").attr("name") == undefined){
                errParam('select', key);
                continue;
            }
            $("select[name='"+key+"']").val([req[key].value]);
        } else if(req[key].type == 'select-multiple'){
            if($("select[name='"+key+"']").attr("name") == undefined){
                errParam('select', key);
                continue;
            }
            $("select[name='"+key+"']").val(req[key].value.split(","));
        } else if(req[key].type == 'textarea'){
            if($("textarea[name='"+key+"']").attr("name") == undefined){
                errParam('textarea', key);
                continue;
            }
            $("textarea[name='"+key+"']").val(req[key].value);
        }
    }
}

function errParam(type, key){
    $("form").append('<div style="color:white;background:-webkit-gradient(linear,left top, left bottom,'+
        'from(red), to(#ffd8d8));font-size:12px;font-weight:bold;padding:5px;">'+
        'AutoForm Error: '+
        type + ' name=' + key + ' does not exist.</div>');
}

