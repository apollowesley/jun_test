//置顶1
$("#back-to-top").click(function(){
	$('body,html').animate({scrollTop:0},1000);
	return false;
});
$(window).scroll(function () {
	if($(window).scrollTop()>=1200){
		$('#backBtn').show();}else{$('#backBtn').hide();}
});

//灯笼置顶2
$(".ten-return").click(function(){
    $('body,html').animate({scrollTop:0},1000);
    return false;
});
$(window).scroll(function () {
    if($(window).scrollTop()>=1200){
        $('.p2').addClass("ten-return-block");
        $('.p1').removeClass("ten-return-block");
    }else{
        $('.p1').addClass("ten-return-block");
        $('.p2').removeClass("ten-return-block");
    }
});







