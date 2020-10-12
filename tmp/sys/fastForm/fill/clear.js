$('input[type=text],input[type=password],textarea').each(function() {
    $(this).val('');
});
$('input,textarea').each(function() {
    $(this).attr("checked",false);
});
$('select').each(function() {
    $(this).find('option').eq(0).attr("selected", "selected");
});