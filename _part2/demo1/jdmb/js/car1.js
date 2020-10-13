/**
 * Created by JackWong on 2016/9/21.
 */
window.onload = function () {
    alertView();

}

/*模态弹出视图*/
function alertView() {
    var win = document.getElementsByClassName("jd_win")[0];
    var winBox = win.getElementsByClassName("jd_win_box")[0];

    var deleBtns = document.getElementsByClassName("delete_box");
    for (var i = 0; i < deleBtns.length; i++) {
        deleBtns[i].onclick = function () {
            win.style.display = "block";
            // var top = document.body.scrollTop + (window.innerHeight - winBox.offsetHeight)/2;
            var top = (window.innerHeight - winBox.offsetHeight) / 2;
            // document.body.style.position = "absolute";

            winBox.style.transition = "all .5s ease 0s";
            winBox.style.webkitTransition = "all .5s ease 0s";
            winBox.style.transform = "translateY(" + top + "px)";
            winBox.style.webkitTransform = "translateY(" + top + "px)";
            winBox.style.opacity = 1;
            /*动画*/
            var deleteTop = this.getElementsByTagName("div")[0];
            deleteTop.style.transition = "all .5s ease 0s";
            deleteTop.style.webkitTransition = "all .5s ease 0s";
            deleteTop.style.transform = "translateX(-4px) translateY(-4px) rotate(-35deg)";
            deleteTop.style.webkitTransform = "translateX(-4px) translateY(-4px) rotate(-35deg)";


            var cancleBtn = winBox.getElementsByClassName("cancle")[0];
            cancleBtn.onclick = function () {

                winBox.style.opacity = 0;
                winBox.style.transform = "translateY("+0+"px)";
                winBox.style.webkitTransform = "translateY("+0+"px)";
                function trans() {
                    win.style.display = "none";
                    winBox.removeEventListener("transitionend",trans);
                }
                winBox.addEventListener("transitionend",trans);

                deleteTop.style.transition = "all .5s ease 0s";
                deleteTop.style.webkitTransition = "all .5s ease 0s";
                deleteTop.style.transform = "translateX(0) translateY(0) rotate(0deg)";
                deleteTop.style.webkitTransform = "translateX(0) translateY(0) rotate(0deg)";


            };

            var deleteBtn = winBox.getElementsByClassName("submit")[0];
            deleteBtn.onclick = function () {

            };


        }
    }



}