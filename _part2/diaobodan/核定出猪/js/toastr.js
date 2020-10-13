/**
 * 提醒
 */
var _ToastrPosition = {
	TopRight		:	'TopRight',
	BottomRight		:	'BottomRight',
	BottomLeft		:	'BottomLeft',
	TopLeft			:	'TopLeft',
	TopFullWidth	:	'TopFullWidth',
	BottomFullWidth :	'BottomFullWidth',
}

var _toastr = new Toastr();

function Toastr()
{
	if($('link[href$="toastr.min.css"]').length == 0) 
		$('<link href="./css/toastr.min.css" rel="stylesheet"/>').appendTo($('head'));
	if($('script[src$="toastr.min.js"]').length == 0)
		$('script[src$="toastr.js"]').before($('<script src="./js/toastr.min.js"/>'));
	
	var _position;
	if(arguments.length == 0 || !arguments[0]) _position = _ToastrPosition.BottomRight;
	else _position = _ToastrPosition.BottomRight;
	
	switch (_position) {
		case _ToastrPosition.TopRight:
			toastr.options = {
				  "closeButton": true,
				  "debug": false,
				  "positionClass": "toast-top-right",
				  "onclick": null,
				  "showDuration": "300",
				  "hideDuration": "1000",
				  "timeOut": "3000",
				  "extendedTimeOut": "1000",
				  "showEasing": "swing",
				  "hideEasing": "linear",
				  "showMethod": "fadeIn",
				  "hideMethod": "fadeOut"
				}
			break;
		case _ToastrPosition.BottomRight:
			toastr.options = {
				  "closeButton": true,
				  "debug": false,
				  "positionClass": "toast-bottom-right",
				  "onclick": null,
				  "showDuration": "300",
				  "hideDuration": "1000",
				  "timeOut": "1500",
				  "extendedTimeOut": "1000",
				  "showEasing": "swing",
				  "hideEasing": "linear",
				  "showMethod": "fadeIn",
				  "hideMethod": "fadeOut"
				}
			break;
		case _ToastrPosition.BottomLeft:
			toastr.options = {
				  "closeButton": true,
				  "debug": false,
				  "positionClass": "toast-bottom-left",
				  "onclick": null,
				  "showDuration": "300",
				  "hideDuration": "1000",
				  "timeOut": "3000",
				  "extendedTimeOut": "1000",
				  "showEasing": "swing",
				  "hideEasing": "linear",
				  "showMethod": "fadeIn",
				  "hideMethod": "fadeOut"
				}
			break;
		case _ToastrPosition.TopLeft:
			toastr.options = {
				  "closeButton": true,
				  "debug": false,
				  "positionClass": "toast-top-left",
				  "onclick": null,
				  "showDuration": "300",
				  "hideDuration": "1000",
				  "timeOut": "3000",
				  "extendedTimeOut": "1000",
				  "showEasing": "swing",
				  "hideEasing": "linear",
				  "showMethod": "fadeIn",
				  "hideMethod": "fadeOut"
				}
			break;
		case _ToastrPosition.TopFullWidth:
			toastr.options = {
				  "closeButton": true,
				  "debug": false,
				  "positionClass": "toast-top-full-width",
				  "onclick": null,
				  "showDuration": "300",
				  "hideDuration": "1000",
				  "timeOut": "3000",
				  "extendedTimeOut": "1000",
				  "showEasing": "swing",
				  "hideEasing": "linear",
				  "showMethod": "fadeIn",
				  "hideMethod": "fadeOut"
				}
			break;
		case _ToastrPosition.BottomFullWidth:
			toastr.options = {
				  "closeButton": true,
				  "debug": false,
				  "positionClass": "toast-bottom-full-width",
				  "onclick": null,
				  "showDuration": "300",
				  "hideDuration": "1000",
				  "timeOut": "3000",
				  "extendedTimeOut": "1000",
				  "showEasing": "swing",
				  "hideEasing": "linear",
				  "showMethod": "fadeIn",
				  "hideMethod": "fadeOut"
				}
			break;
		default:
			break;
		
	}
	this.warning = function(msg){toastr.warning(msg);}
	this.success = function(msg){toastr.success(msg);}
	this.error 	 = function(msg){toastr.error(msg);}
	this.info	 = function(msg){toastr.info(msg);}
	this.clear	 = function(){toastr.clear();}
}
