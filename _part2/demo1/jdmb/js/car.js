/**
 * Created by JackWong on 2016/9/21.
 */

// 引用外部js
// document.write("<script src='js/check.js'></script>");
window.onload = function () {
    alertView();
    checkFun();
    /*复选框*/
    check();
    var str = "&yen;3000";
    // console.log(parseInt(str.split(";")[1]));

}


/*模态弹出视图*/
function alertView() {
    var win = document.getElementsByClassName("jd_win")[0];
    var winBox = win.getElementsByClassName("jd_win_box")[0];

    var deleBtns = document.getElementsByClassName("delete_box");

    /*表示当前点击的是第几行删除按钮*/
    var touchRow = -1;

    /*遍历所有的btn*/
    function eachDeleBtn() {
        for (var i = 0; i < deleBtns.length; i++) {
            deleBtns[i].index = i;
            deleBtns[i].onclick = function () {
                touchRow = this.index;
                win.style.display = "block";
                // var top = document.body.scrollTop + (window.innerHeight - winBox.offsetHeight)/2;
                var top = (window.innerHeight - winBox.offsetHeight) / 2;
                // document.body.style.position = "absolute";

                winBox.style.transition = "all .5s ease 0s";
                winBox.style.webkitTransition = "all .5s ease 0s";
                winBox.style.transform = "translateY(" + top + "px)";
                winBox.style.webkitTransform = "translateY(" + top + "px)";
                winBox.style.opacity = 1;
                // winBox.className = "jd_win_box viewDown";
                /*动画*/
                var deleteTop = this.getElementsByTagName("div")[0];
                deleteTop.style.transition = "all .5s ease 0s";
                deleteTop.style.webkitTransition = "all .5s ease 0s";
                deleteTop.style.transform = "translateX(-4px) translateY(-4px) rotate(-35deg)";
                deleteTop.style.webkitTransform = "translateX(-4px) translateY(-4px) rotate(-35deg)";


            }
        }

    }

    eachDeleBtn();

    function dissView() {

        winBox.style.opacity = 0;
        winBox.style.transform = "translateY(" + 0 + "px)";
        winBox.style.webkitTransform = "translateY(" + 0 + "px)";
        console.log(winBox);
        function trans() {
            win.style.display = "none";
            winBox.removeEventListener("transitionend", trans);
        }

        winBox.addEventListener("transitionend", trans);

        var deleteTop = deleBtns[touchRow].getElementsByTagName("div")[0];
        deleteTop.style.transition = "all .5s ease 0s";
        deleteTop.style.webkitTransition = "all .5s ease 0s";
        deleteTop.style.transform = "translateX(0) translateY(0) rotate(0deg)";
        deleteTop.style.webkitTransform = "translateX(0) translateY(0) rotate(0deg)";

    }

    var cancleBtn = winBox.getElementsByClassName("cancle")[0];
    cancleBtn.onclick = function () {
        // alert(touchRow);
        dissView();
    };

    var deleteBtn = winBox.getElementsByClassName("submit")[0];
    deleteBtn.onclick = function () {
        dissView();
        var shop = document.getElementsByClassName("jd_shop")[0];
        var produucts = shop.getElementsByClassName("jd_shop_con");

        var deleteProduct = produucts[touchRow];
        deleteProduct.parentNode.removeChild(deleteProduct);

        eachDeleBtn();
        touchRow = -1;
        /*修改touchrow*/
    };


}

var checkFun = function () {
    var amountCheck = document.getElementsByClassName("amount_check")[0];
    var checkBox = amountCheck.getElementsByClassName("jd_check_box")[0];
    checkBox.onclick = function () {
        var checked = this.getAttribute("checked");
        var isCheked = false;
        if (checked != null) {
            this.removeAttribute("checked");
            // isCheked = false;
        } else {
            isCheked = true;
            this.setAttribute("checked", "");
        }

        var checkBoxs = document.getElementsByClassName("jd_check_box");
        for (var i = 0; i < checkBoxs.length; i++) {
            if (checkBoxs[i] == this) {
                console.log("你和我相等");
            } else {
                isCheked ? checkBoxs[i].setAttribute("checked", ""):checkBoxs[i].removeAttribute("checked");
                // var hasChecked = checkBoxs[i].getAttribute("checked");
                // if (hasChecked == null) {
                //     checkBoxs[i].setAttribute("checked", "");
                // }
            }
        }
        sumMoney();

    }
}