(function($) {
	$.fn.template = function(option) {
		var opts = $.extend({}, $.fn.template.defaults, option);
		var t=0;
		return this.each(function() {
			var $this=$(this),
			$template = $this.prop('outerHTML');
			if (opts.data.length) {
			 for(var i=0;i<opts.data.length;i++){
			 	if(opts.innerdataTag!=""){
			 		var $innertemplate=$this.find(opts.innerTemplateTag).prop('outerHTML');
					$(opts.templateTag).append(replaceTemp(opts.data[i],$template,opts.innerdataTag,$innertemplate,opts.innerTemplateTag)).children().removeClass("hidden");
			 
			 	}else{
			 		$(opts.templateTag).append(replaceTemp(opts.data[i],$template)).children().removeClass("hidden");
			 
			 	}
			 	}
			}
		});
		 function replaceTemp(data,temp,innerFlag,tempIner,innerTag) {
		 	for (var key in data) {
		 		var re = new RegExp("{" + key + "}", "g");
				temp = temp.replace(re, data[key]);
				if(key==innerFlag){
					var arry=new Array();
					if(data[key].length==0){
						return false;
					}
					for(var i=0;i<data[key].length;i++){
						arry.push(replaceTemp(data[key][i],tempIner,null));
					}
					if(opts.pageFlag){
						temp=$(temp).find(innerTag).parent().attr("id","bodyT"+t).html(arry.join("")).end().end().find("."+opts.pageTemplate).addClass(opts.pageTemplate+t).end().prop('outerHTML');
						t=t+1;
					}else{
						temp=$(temp).find(innerTag).parent().html(arry.join("")).end().end().prop('outerHTML');
					}
					
				}
			}
			return temp;
		};
		
		
	};

})(jQuery);
