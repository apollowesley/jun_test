/**
*数据验证（表单验证）
*来自 grace.hcoder.net 
*作者 hcoder tan
* var validate; var check=validate.check({username:"13"},[{checkType:'int',name:'username',error:'用户名错误'}]);if(check){ alert('验证失败错误提示'+check.error)}
*/


var Validate={
	error:'',
	check : function (data, rule){
		this.error='';
		for(var i = 0; i < rule.length; i++){
			if (!rule[i].checkType){return true;}
			if (!rule[i].name) {return true;}
			// if (!rule[i].error) {return true;}
			if (!data[rule[i].name]) {this.error = rule[i].error || '不能为空'; return false;}
			switch (rule[i].checkType){
				case 'string':
					var reg = new RegExp('^.{' + rule[i].checkRule + '}$');
					if(!reg.test(data[rule[i].name])) {this.error = rule[i].error; return false;}
				break;
				case 'int':
					var reg = new RegExp('^(-[1-9]|[1-9])[0-9]{' + rule[i].checkRule + '}$');
					if(!reg.test(data[rule[i].name])) {this.error = rule[i].error; return false;}
					break;
				break;
				case 'between':
					if (!this.isNumber(data[rule[i].name])){
						this.error = rule[i].error;
						return false;
					}
					var minMax = rule[i].checkRule.split(',');
					minMax[0] = Number(minMax[0]);
					minMax[1] = Number(minMax[1]);
					if (data[rule[i].name] > minMax[1] || data[rule[i].name] < minMax[0]) {
						this.error = rule[i].error;
						return false;
					}
				break;
				case 'betweenD':
					var reg = /^-?[1-9][0-9]?$/;
					if (!reg.test(data[rule[i].name])) { this.error = rule[i].error; return false; }
					var minMax = rule[i].checkRule.split(',');
					minMax[0] = Number(minMax[0]);
					minMax[1] = Number(minMax[1]);
					if (data[rule[i].name] > minMax[1] || data[rule[i].name] < minMax[0]) {
						this.error = rule[i].error;
						return false;
					}
				break;
				case 'betweenF': 
					var reg = /^-?[0-9][0-9]?.+[0-9]+$/;
					if (!reg.test(data[rule[i].name])){this.error = rule[i].error; return false;}
					var minMax = rule[i].checkRule.split(',');
					minMax[0] = Number(minMax[0]);
					minMax[1] = Number(minMax[1]);
					if (data[rule[i].name] > minMax[1] || data[rule[i].name] < minMax[0]) {
						this.error = rule[i].error;
						return false;
					}
				break;
				case 'same':
					if (data[rule[i].name] != rule[i].checkRule) { this.error = rule[i].error; return false;}
				break;
				case 'notsame':
					if (data[rule[i].name] == rule[i].checkRule) { this.error = rule[i].error; return false; }
				break;
				case 'email':
					var reg = /^\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
					if (!reg.test(data[rule[i].name])) { this.error = rule[i].error; return false; }
				break;
				case 'phoneno':
					var reg = /^1[0-9]{10,10}$/;
					if (!reg.test(data[rule[i].name])) { this.error = rule[i].error; return false; }
				break;
				case 'zipcode':
					var reg = /^[0-9]{6}$/;
					if (!reg.test(data[rule[i].name])) { this.error = rule[i].error; return false; }
				break;
				case 'reg':
					var reg = new RegExp(rule[i].checkRule);
					if (!reg.test(data[rule[i].name])) { this.error = rule[i].error; return false; }
				break;
				case 'in':
					if(rule[i].checkRule.indexOf(data[rule[i].name]) == -1){
						this.error = rule[i].error; return false;
					}
				break;
				case 'notnull':
					if(data[rule[i].name] == null || data[rule[i].name].length < 1){this.error = rule[i].error; return false;}
				break;
				case 'password':
					var PdZf=new RegExp("^[a-zA-Z0-9_,.?`@]+$","i").test(data[rule[i].name]);//判断是字母或数组或下横线,.@等字符
					var p = /[a-z]/i; 
					var PdZm = p.test(data[rule[i].name].substr(0,1));//判断首位是字母
			
					if (data[rule[i].name].length<8){
						this.error=rule[i].error || '请输入至少8位字符';
						return false;
					}else if(!PdZm){
						this.error=rule[i].error || '首字符只能是字母';
						return false;
					}else if(!PdZf) {
					  this.error=rule[i].error || '请使用(字母)+(数字)+常用英文字符组合';
					  return false;
					} 
				break;
				
			}
		}
		return true;
	},
	isNumber : function (checkVal){
		var reg = /^-?[1-9][0-9]?.?[0-9]*$/;
		return reg.test(checkVal);
	}
}
export {Validate}