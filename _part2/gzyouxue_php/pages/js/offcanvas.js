$(document).ready(function () {
  $('[data-toggle="offcanvas"]').click(function () {
    $('.row-offcanvas').toggleClass('active');
	if($(this).attr("data-to-show") === 'y') {
		$(this).text("收起左侧列表");
		$(this).attr("data-to-show", "n");
	} else if ($(this).attr("data-to-show") === 'n') {
		$(this).text("展开左侧列表");
		$(this).attr("data-to-show", "y");
	}
  });
});