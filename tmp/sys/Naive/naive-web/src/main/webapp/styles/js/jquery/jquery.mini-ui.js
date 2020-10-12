/*
jQuery Blink Wiggle and Bob
Author: WonderGroup, Jordan Thomas
URL: http://labs.wondergroup.com/demos/mini-ui/index.html
License: MIT (http://en.wikipedia.org/wiki/MIT_License)
*/
jQuery.fn.blink = function(o) {
	var d = { speed: 200, blinks: 3, callback: null };
	var o = jQuery.extend(d, o);
	return this.each( function() {
		var calls = 0;
		for (i=1;i<=o.blinks;i++) {
			$(this).animate({
				opacity: 0
			}, o.speed).animate({
				opacity: 1
			}, o.speed, function() {
				calls++;
				if (calls == o.blinks && jQuery.isFunction(o.callback)) { o.callback(); }
			});
		}
	});
};
jQuery.fn.wiggle = function(o) {
	var d = { speed: 50, wiggles: 3, travel: 5, callback: null };
	var o = jQuery.extend(d, o);
	
	return this.each( function() {
		var cache = this;
		var wrap = jQuery(this).wrap('<div class="wiggle-wrap"></div>').css("position","absolute");
		var calls = 0;
		for (i=1;i<=o.wiggles;i++) {
			jQuery(this).animate({
				left: "-=" + o.travel
			}, o.speed).animate({
				left: "+=" + o.travel*2
			}, o.speed*2).animate({
				left: "-=" + o.travel
			}, o.speed, function() {
				calls++;
				if (jQuery(cache).parent().hasClass('wiggle-wrap')) {
					jQuery(cache).parent().replaceWith(cache);
				}
				if (calls == o.wiggles && jQuery.isFunction(o.callback)) { o.callback(); }
			});
		}
	});
};
jQuery.fn.bob = function(o) {
	var d = { speed: 50, bobs: 3, travel: 5, callback: null };
	var o = jQuery.extend(d, o);
	
	return this.each( function() {
		var cache = this;
		var wrap = jQuery(this).wrap('<div class="bob-wrap"></div>').css("position","relative");
		var calls = 0;
		for (i=1;i<=o.bobs;i++) {
			jQuery(this).animate({
				top: "-=" + o.travel
			}, o.speed).animate({
				top: "+=" + o.travel*2
			}, o.speed*2).animate({
				top: "-=" + o.travel
			}, o.speed, function() {
				calls++;
				if (jQuery(cache).parent().hasClass('bob-wrap')) {
					jQuery(cache).parent().replaceWith(cache);
				}
				if (calls == o.bobs && jQuery.isFunction(o.callback)) { o.callback(); }
			});
		}
	});
};
