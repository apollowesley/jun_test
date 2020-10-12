function isEmpty(field){
	if(field.value.length < 1){
		return true;
	}else{
		return false;
	}
}

function isEmail(field){
	if(field.value.indexOf("@") == -1 || field.value.indexOf(".") == -1){
		return true;
	}else{
		return false;
	}
}

function prepare(){
	for(var i = 0 ; i < document.forms.length ; i++){
		var whichform = document.forms[i];
		whichform.onsubmit = function(){
			return validate(this);
		};
	}
}

function validate(whichform){
	for(var i = 0 ; i < whichform.elements.length ; i++){
		var element = whichform.elements[i];
		if(element.name == "submit" || element.name == "reset"){
			continue;
		}
		if(element.name == "email"){
			if(isEmpty(element)){
				alert("请填写你的邮箱");
				return false;
			}else if(isEmail(element)){
				alert("邮箱格式不正确");
				return false;
			}
		}else{
			if(isEmpty(element)){
				alert("请填写你的" + element.name);
				return false;
			}
		}
	}
	return true;
}

function addonload(newFunction){
	var oldFunction = window.onload;
	if(typeof oldFunction != "function"){
		window.onload = newFunction;
	}else{
		window.onload = function(){
			oldFunction();
			newFunction();
		};
	}
}

addonload(prepare);