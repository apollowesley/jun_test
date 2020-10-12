/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(".del").click(function (e) {
    if (confirm("真的要删除吗，删除后没法恢复( ⊙ o ⊙ )啊！")) {

    } else {
        e.preventDefault();
    }
});
$(".upd").click(function () {
    $("#upd").show();
    var message = $(this).parent().find(".mes").html();
    var id = $(this).parent().find(".id").html();
    $("#mes").val(message);
    $("#id").val(id);
});
$(".deupd").click(function () {
    $("#upd").hide();
});



