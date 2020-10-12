$(function () {
    /**
     * 一级菜单点击事件
     */
    $(".f-menu").bind("click", function () {
        var $sub_menu = $(this).find("ul");

        if ($sub_menu.is(":visible")) {
            $sub_menu.hide();
            $(".click-menu-item").removeClass("click-menu-item");
        } else {
            $(".click-menu-item").removeClass("click-menu-item");
            $(this).addClass("click-menu-item")
            $(".f-menu ul").hide();
            $sub_menu.show();
        }
    });

    /**
     * 下拉菜单点击事件
     */
    $(".f-menu li").bind("click", function () {
        var $parent_menu = $(this).parents(".f-menu");
        $(".current-menu-item", $parent_menu).removeClass("current-menu-item");
        $(this).addClass("current-menu-item");

        var menu_text = $(this).find("a").text();
        $parent_menu.find("a").eq(0).text(menu_text);
    });
});