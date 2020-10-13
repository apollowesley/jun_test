window.onload = function () {

    initLeft();
    initRight();
}
// window.onresize = function () {
//     location.reload(true);
// }
/*左侧分类*/
function initLeft() {
    /*找到父容器*/
    var parentDom = document.getElementsByClassName("jd_category_left")[0];
    var leftDom = parentDom.getElementsByClassName("jd_category_left_box")[0];
    /*找到ul*/
    var childDom = leftDom.getElementsByTagName("ul")[0];
    /*找到所有的li*/
    var liDom = childDom.getElementsByTagName("li");

    /*取出父容器的高度*/
    //先取出盒子的高度
    var parentH = parentDom.offsetHeight;
    /*获得内容高度*/
    parentH -= 45;
//    获取子容器的高度
    var childH = childDom.offsetHeight;

    /*添加过渡*/
    var addTransition = function () {
        childDom.style.webkitTransition = "all .3s ease 0s";
        childDom.style.transition = "all .3s ease 0s";
    }

    /*删除过渡*/
    var removeTransition = function () {
        childDom.style.webkitTransition = "none";
        childDom.style.transition = "none";
    }

    /*滑动*/
    var startY = 0;/*开始的坐标Y*/
    var endY = 0; /*结束的坐标Y*/
    var moveY = 0; /*滑动的距离*/
    var currY = 0;/*当前的translateY*/

    /*最大的移动距离*/
    var maxY = 150;
    /*最小的移动距离*/
    var minY = -(childH - parentH+150);

    /*点击时间*/
    var startTime = 0,endTime = 0;
    childDom.addEventListener("touchstart",function (e) {
        startY = e.touches[0].clientY;   /*相对于父容器的*/
        console.log("touchstart");
        startTime = new Date().getTime();

    });
    childDom.addEventListener("touchmove",function (e) {
        e.preventDefault();
        endY = e.touches[0].clientY;
        moveY = endY - startY;
        if((currY+moveY)<=maxY && (currY+moveY) >= minY){
            removeTransition();
            childDom.style.transform = "translateY("+(currY+moveY)+"px)";
            childDom.style.webkitTransform = "translateY("+(currY+moveY)+"px)";
        }
    });

    childDom.addEventListener("touchend",function (e) {
        e.preventDefault();
        /*滑动结束之后记录下当前的translateY*/
        // if((currY+moveY)<=maxY && (currY+moveY) >= minY) {
        //     // console.log("currY ----sss-"+currY);
        //     currY = currY + moveY;
        // }

        if((currY+moveY) <= 0 && (currY+moveY) >= -(childH - parentH)){
            currY = currY + moveY;
        }else if ((currY+moveY)>0){
            console.log("上边有空白了");
            currY = 0;
            addTransition();
            childDom.style.transform = "translateY("+(currY)+"px)";
            childDom.style.webkitTransform = "translateY("+(currY)+"px)";
        }else if ((currY+moveY) < -(childH - parentH)){
            console.log("底边有空白了");
            currY = -(childH - parentH);
            addTransition();
            childDom.style.transform = "translateY("+(currY)+"px)";
            childDom.style.webkitTransform = "translateY("+(currY)+"px)";

        }
        endTime = new Date().getTime();

        /*点击事件*/
      if (moveY==0 && (endTime-startTime) < 200 ){
            console.log("点击事件");
          var currentLi = e.target.parentNode;
          for(var i = 0; i < liDom.length;i++){
              liDom[i].index = i;
              liDom[i].className = "";
          }
          currentLi.className = "now";
          var top = currentLi.index*50;
         if (top < (childH - parentH)){
             addTransition();
             childDom.style.transform = "translateY("+(-top)+"px)";
             childDom.style.webkitTransform = "translateY("+(-top)+"px)";
             /*设置当前的translateY*/
             currY = -top;
         }else {
             addTransition();
             childDom.style.transform = "translateY("+(-(childH - parentH))+"px)";
             childDom.style.webkitTransform = "translateY("+(-(childH - parentH))+"px)";
             currY = -(childH - parentH);
         }

         /*模拟右边页面切换*/
         var rightDom = document.getElementsByClassName("jd_category_right_box")[0];
          rightDom.style.webkitTransition = "all .2s ease 0s";
          rightDom.style.transition = "all .2s ease 0s";
          rightDom.style.opacity = 0;
          setTimeout(function () {
              rightDom.style.opacity = 1;
          },300);
      }
      /*把参数请0*/
      startY = 0;
        endY = 0;
        moveY = 0;
    });

    // var aList = childDom.getElementsByTagName("a");
    // for (var i = 0; i < aList.length;i++){
    //     aList[i].onclick = function () {
    //         console.log("aaaaaaaaaaaaaaaaaaaaaaaaaaa");
    //     }
    // }
}

/*右侧分类*/
function initRight() {
    /*找到父容器*/
    var parentDom = document.getElementsByClassName("jd_category_right")[0];
    var childDom = parentDom.getElementsByClassName("jd_category_right_box")[0];
    /*取出父容器的高度*/
    //先取出盒子的高度
    var parentH = parentDom.offsetHeight;
//    获取子容器的高度
    var childH = childDom.offsetHeight;

    /*添加过渡*/
    var addTransition = function () {
        childDom.style.webkitTransition = "all .3s ease 0s";
        childDom.style.transition = "all .3s ease 0s";
    }

    /*删除过渡*/
    var removeTransition = function () {
        childDom.style.webkitTransition = "none";
        childDom.style.transition = "none";
    }

    /*滑动*/
    var startY = 0;/*开始的坐标Y*/
    var endY = 0; /*结束的坐标Y*/
    var moveY = 0; /*滑动的距离*/
    var currY = 0;/*当前的translateY*/

    /*最大的移动距离*/
    var maxY = 150;
    /*最小的移动距离*/
    var minY = -(childH - parentH+150);

    childDom.addEventListener("touchstart",function (e) {
        console.log("touchstart");
        /*拿到y的坐标*/
        startY = e.touches[0].clientY;

    });
    childDom.addEventListener("touchmove",function (e) {
        e.preventDefault();
        console.log("touchmove");
        /*取得结束时候的Y坐标*/
        endY = e.touches[0].clientY;
        moveY = endY - startY;
        if((currY+moveY)<=maxY && (currY+moveY) >= minY){
            removeTransition();
            childDom.style.transform = "translateY("+(currY+moveY)+"px)";
            childDom.style.webkitTransform = "translateY("+(currY+moveY)+"px)";
        }
    });
    childDom.addEventListener("touchend",function (e) {
        console.log("touchend");
        // e.preventDefault();

        if((currY+moveY) <= 0 && (currY+moveY) >= -(childH - parentH)){
            currY = currY + moveY;
        }else if ((currY+moveY)>0){
            console.log("上边有空白了");
            currY = 0;
            addTransition();
            childDom.style.transform = "translateY("+(currY)+"px)";
            childDom.style.webkitTransform = "translateY("+(currY)+"px)";
        }else if ((currY+moveY) < -(childH - parentH)){
            console.log("底边有空白了");
            currY = -(childH - parentH);
            addTransition();
            childDom.style.transform = "translateY("+(currY)+"px)";
            childDom.style.webkitTransform = "translateY("+(currY)+"px)";
        }

        /*把参数请0*/
        startY = 0;
        endY = 0;
        moveY = 0;

    });

    var ulBox = childDom.getElementsByClassName("r_product")[0].getElementsByTagName("ul")[0];
    var aList = ulBox.getElementsByTagName("a");
    for (var i = 0; i < aList.length;i++){
        aList[i].index = i;
        aList[i].onclick = function () {
            alert(this.nextElementSibling.innerText);
            // alert(this.index);

        }
    }

}