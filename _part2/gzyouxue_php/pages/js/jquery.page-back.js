(function ($, document) {
    'use strict';
    $.fn.pageBack = function (options) {
		var o = $.fn.pageBack.settings = $.extend({}, $.fn.pageBack.defaults, options), $self;
        // Get element
		$self = $("#" + o.pageBackName);
        if ($self.length > 0 && !$.data(document.body, "page-back")) {
			$.data(document.body, "page-back", true);
			// Define vars
			$self.attr("href", 'javascript:;');
			$self.bind("click", function(){
				window.history.go(-1);
			});
			// Set pageBackTitle if there is one
			if (o.pageBackTitle) {
				$self.attr('title', o.pageBackTitle);
			}
			$self.html(o.pageBackText);
			// Minimum CSS to make the magic happen
			$self.css({
				position: 'fixed',
				zIndex: o.zIndex
			});
		}
    };

    // Defaults
    $.fn.pageBack.defaults = {
        pageBackName: 'page-back',      // Element ID
        pageBackText: 'Previous Page', // Text for element, can contain HTML
        pageBackTitle: false,          // Set a custom <a> title if required. Defaults to pageBackText
        pageBackImg: false,            // Set true to use image
        zIndex: 1234567           	   // Z-Index for the overlay
    };

    $.pageBack = $.fn.pageBack;

})(jQuery, document);
