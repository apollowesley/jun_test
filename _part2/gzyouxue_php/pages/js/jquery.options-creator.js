(function ($) {
    'use strict';
    $.fn.createOptions = function (options) {
		var o = $.fn.createOptions.settings = $.extend({}, $.fn.createOptions.defaults, options), $this;
        // Get element
		$this = $(this);
        if ($this.length > 0) {
			var $select = $($this[0]);
			// Define vars
			// var $option = $('<option/>');
			// $select.append($option);
			if (o.data == '') {
				$option = $('<option/>');
				$option.attr("value", "None");
				$option.html("None");
				$select.append($option);
			} else {
				if (!o.optgroup) {
					$.each(o.data, function(idx, optionItem) {
						$option = $('<option/>');
						var value = optionItem.value;
						$option.attr("value", value);
						alert(value);
						if (o.selected_value_array && o.selected_value_array.length > 0) {
							alert("array");
							if (value.inArray(o.selected_value_array) > -1) {
								$option.attr("selected", "selected");
							}
						} else if (o.selected_value_str != "") {
							alert("string");
							if (o.selected_value_str.indexOf(value) > -1) {
								$option.attr("selected", "selected");
							}
						}
						$option.html(optionItem.text);
						$select.append($option);
					});	
				} else {
					$.each(o.data, function(idx, optgroupItem) {
						var $optgroup = $('<optgroup/>');
						$optgroup.attr('label', optgroupItem.label);
						$.each(optgroupItem._options, function(idx1, optionItem) {
							var $option = $("<option/>");
							var value = optionItem.value;
						$option.attr("value", value);
						if (o.selected_value_array && o.selected_value_array.length > 0) {
							alert("array");
							if (value.inArray(o.selected_value_array) > -1) {
								$option.attr("selected", "selected");
							}
						} else if (o.selected_value_str != "") {
							alert("string");
							if (o.selected_value_str.indexOf(value) > -1) {
								$option.attr("selected", "selected");
							}
						}
							$option.html(optionItem.text);
							$optgroup.append($option);
							$select.append($optgroup);
						});
					});
				}
			}
		}
    };

    // Defaults
    $.fn.createOptions.defaults = {
		data:"",
		optgroup:false,
		selected_value_array:new Array(),
		selected_value_str:""
    };

    $.createOptions = $.fn.createOptions;

})(jQuery);
