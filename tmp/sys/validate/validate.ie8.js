!function($) {
    $.fn.extend({
    	validate:function(options){
			//fixed IE8 不支持 数组的 indexOf 方法
			Array.prototype.indexOf||(Array.prototype.indexOf=function(a){var b=this.length>>>0,c=Number(arguments[1])||0;for(c=0>c?Math.ceil(c):Math.floor(c),0>c&&(c+=b);b>c;c++)if(c in this&&this[c]===a)return c;return-1});
			var opt = $.extend({
				all:true,//是否验证所有的表单元素（遇到验证不通过的继续验证）
				allrule:false,//表单上设置的规则是否全部验证（默认失败一个规则就跳过剩余的验证）
				valid:null,//单个表单元素验证成功的回调函数（可在回调内设置验证成功的元素的样式或提示，this指向验证成功的表单元素的jq对象）
				invalid:null,//单个表单元素验证失败的回调函数（可在回调内设置失败元素的样式，this指向验证失败的表单元素的jq对象）
				allvalid:null//表单内所有元素都验证成功的回调函数（可用于表单提交）
			},options),
			pass = true,
			result = true,
			fn={
				required:function(msg){
					var r = false;
					if(this.is('[type=checkbox],[type=radio]')){
						r = this.prop('checked');
						r || msg.push('此项为必选');
					}else{
						r = this.val().length>0;
						r || msg.push('此项为必填');
					}
					return r;
				},
				alpha:function(msg){
					var r = /^[a-zA-Z]*$/.test(this.val());
					r || msg.push('只能填写字母');
					return r;
				},
				numeric:function(msg){
					var r = /^[0-9]*$/.test(this.val());
					r || msg.push('只能填写数字'); 
					return r
				},
				alphanumeric:function(msg){
					var r = /^[a-zA-Z0-9]*$/.test(this.val());
					r || msg.push('只能填写数字和字母');
					return r;
				},
				alphabegin:function(msg){
					var r = /^[a-zA-Z]+.*$/.test(this.val());
					r || msg.push('只能以字母开头');
					return r;
				},
				mobile:function(msg){
					var r = /^1(3|4|5|7|8|9)\d{9}$/.test(this.val());
					r || msg.push('手机号格式不正确');
					return r;
				},
				url:function(msg){
					var r = /^https?:\/\/(\w+:{0,1}\w*@)?(\S+)(:[0-9]+)?(\/|\/([\w#!:.?+=&%@!\-\/]))?$/.test(this.val());
					r || msg.push('URL格式不正确');
					return r;
				},
				email:function(msg){
					var r = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(this.val());
					r || msg.push('邮箱格式不正确');
					return r;
				},
				pattern:function(msg){
					var exp = /pattern\[(.*?)(?:\]$|\]\|)/.exec(this.data('validate'));
					exp = exp?exp[1]:(this.attr('pattern')||'.*');
					var r = new RegExp(exp).test(this.val());
					r || msg.push('格式不正确');
					return r;
				},
				max:function(msg){
					var max = /max\[(\d+)\]/.exec(this.data('validate'));
					max = max?max[1]:this.attr('max');
					var r = parseFloat(this.val())<=parseFloat(max);
					r || msg.push('最大值为'+max);
					return r;
				},
				min:function(msg){
					var min = /min\[(\d+)\]/.exec(this.data('validate'));
					min = min?min[1]:this.attr('min');
					var r = parseFloat(this.val())>=parseFloat(min);
					r || msg.push('最小值为'+min);
					return r;
				},
				maxlength:function(msg){
					var max = /maxlength\[(\d+)\]/.exec(this.data('validate'));
					max = parseInt(max?max[1]:this.attr('maxlength'));
					var r = this.val().length<=max*1;
					r || msg.push('最多输入'+max+'个字符');
					return r;
				},
				minlength:function(msg){
					var min = /minlength\[(\d+)\]/.exec(this.data('validate'));
					min = min?min[1]:0;
					var r = this.val().length>=min*1;
					r || msg.push('最少输入'+min+'个字符');
					return r;
				},
				groupRequired:function(msg){
					var r = this.find(':checked').length?true:false;
					r || msg.push('请勾选一项');
					return r;
				},
				groupMax:function(msg){
					var len = this.find(':checked').length,max = /groupMax\[(\d+)\]/.exec(this.data('validate'));
					max = max?max[1]:len;
					var r = len <= max;
					r || msg.push('最多选择'+max+'项');
					return r;
				},
				groupMin:function(msg){
					var len = this.find(':checked').length,min = /groupMin\[(\d+)\]/.exec(this.data('validate'));
					min = min?min[1]:0;
					var r = len >= min;
					r || msg.push('最少选择'+min+'项');
					return r;
				},
				fn:function(msg){
					var fn = /fn\[(\w+)\]/.exec(this.data('validate')),r=true;
					if(fn!=null){
						fn = fn[1];
						if(typeof opt[fn] =='function'){
							r = opt[fn].call(this,msg);
							r = typeof r == 'boolean'?r:true;
						}
					}
					return r;
				},
                equal:function(msg){
                    var el = /equal\[(.*?)(?:\]$|\]\|)/.exec(this.data('validate'));
                    el = el?$(el[1]):this;
                    var r = el.val() == this.val();
                    r || msg.push('两次输入的不一致');
                    return r;
                }
			}
			var fields=$('[data-validate],[min],[max],[pattern],[required]',this).filter(':visible');
			fields.each(function(){
    			var field = $(this);
				for(var rules=field.data('validate')||'',exp=/\[([\\].|[^\[\]\\])*\]/;rules.match(exp);)rules=rules.replace(exp,'');
				rules=rules.split('|');
				field.attr('required')!=null&&rules.indexOf('required')==-1&&rules.push('required');
				field.attr('min')!=null&&rules.indexOf('min')==-1&&rules.push('min');
				field.attr('max')!=null&&rules.indexOf('max')==-1&&rules.push('max');
				field.attr('pattern')!=null&&rules.indexOf('pattern')==-1&&rules.push('pattern');
				var msg = [];
				pass = true;
				$.each(rules,function(i,r){
					var flag = typeof fn[r] =='function'?fn[r].call(field,msg):true;
					pass = pass?flag:pass;
					result = result?flag:result;
					return pass||opt.allrule?!0:!1;
				});
				pass && typeof opt.valid=='function'&&opt.valid.call(field);
				pass || (field.data('msg',msg),typeof opt.invalid=='function'&&opt.invalid.call(field));
				return pass||opt.all?!0:!1;
			})
			result && typeof opt.allvalid == 'function' && opt.allvalid.call(this);
			return result;
		}
	})
}($)