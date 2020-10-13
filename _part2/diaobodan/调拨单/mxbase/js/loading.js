/**
 * 加载
 */
var _loading = new Loading();
$(function(){
	if($('link[href$="loading.css"]').length == 0)
		$('<link href="../mxbase/css/loading.css" rel="stylesheet"/>').appendTo($('head'));
})

function Loading()
{
	/**
	 * 加载完毕
	 */
	this.end   = function end(){
		$('.loading').remove();
	}
	/**
	 * 开始加载
	 */
	this.start = function start(){
		$('<div class="loading">' +
			'<div class="spinner">' +
				'<div class="bounce1"></div>' +
				'<div class="bounce2"></div>' +
				'<div class="bounce3"></div>' +
			'</div>' +
		 '</div>').appendTo($('body'));
	}
}
