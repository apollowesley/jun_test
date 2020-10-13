window.onload = function() {
<<<<<<< HEAD
        search();
        scrollPic();
        secondKill();
       console.log("王昆伦");
       console.log("陈亮");
=======
    search();
    scrollPic();
    secondKill();
    console.log("马玉茹");
    console.log("王昆伦");
    console.log("张路强");
    console.log("陈亮");
    console.log("吉全龙");
    console.log("宋涛");
    console.log("王峰");
    console.log("刘杰");
    console.log("李希沛");
    console.log("吴裕超");
    console.log("朱若男");
    console.log("徐旭阳");
>>>>>>> f88f5f58e30dde510b2e7047f55b3d483a01540e
        console.log("吉全龙");
        consoloe.log("宋涛");
        console.log("王峰");
        console.log("刘杰");
       console.log("李希沛");
        console.log("吴裕超");
<<<<<<< HEAD
    };
    // 头部搜索；
=======
        console.log("朱若男");

    console.log("你好吗？");
};
// 头部搜索；
>>>>>>> f88f5f58e30dde510b2e7047f55b3d483a01540e
var search = function() {
    // 搜索框对象
    var search = document.getElementsByClassName("jdheader-box")[0];
    // banner
    var banner = document.getElementsByClassName("jd_banner")[0];
    var height = banner.offsetHeight;
    window.onscroll = function() {
        var top = document.body.scrollTop;
        // 当滚动高度大于banner高度的时候颜色不变
        if (top > height) {
            search.style.background = "rgba(201,21,35,.85)";
        } else {
            var op = top / height * 0.85;
            search.style.background = "rgba(201,21,35," + op + ")";
        }
    }
}

/**
 * 轮播图
 */
var scrollPic = function() {
    /*banner*/
    var banner = document.getElementsByClassName("jd_banner")[0];

    /*获取图片的宽度*/
    var width = banner.offsetWidth;
    /*图片盒子*/
    var imgBox = banner.getElementsByTagName("ul")[0];
    /*点盒子*/
    var pointBox = banner.getElementsByTagName("ul")[1];

    /*获取里边的每一个点*/
    var pointList = pointBox.getElementsByTagName("li");

    /*加过渡*/
    var addTransition = function() {
            imgBox.style.transition = "all .3s ease";
            imgBox.style.webkitTransition = "all .3s ease";
        }
        /*删除过渡*/
    var removeTransition = function() {
<<<<<<< HEAD
        imgBox.style.transition = "none";
        imgBox.style.webkitTransition = "none";
    }
    /*改变位置*/
=======
            imgBox.style.transition = "none";
            imgBox.style.webkitTransition = "none";
        }
        /*改变位置*/
>>>>>>> f88f5f58e30dde510b2e7047f55b3d483a01540e
    var changePosition = function(position) {
        imgBox.style.transform = "translateX(" + position + "px)";
        imgBox.style.webkitTransform = "translateX(" + position + "px)";
    };
    var changePointWithIndex = function(index) {
        // console.log(pointList);
        // console.log(index);
        for (var i = 0; i < pointList.length; i++) {
            if (index == 9) {
                index = 1;
            }
            if (i === index - 1) {
                pointList[index - 1].className = "now";
            } else {
                pointList[i].className = "";
            }
        }

    };
    /*当前图片的索引号*/
    var index = 1;
    var timer = null;
    timer = setInterval(function() {
        index++;
        addTransition();
        changePosition(-index * width);
        changePointWithIndex(index);
    }, 3000);
    /*实现无限循环滚动*/
    imgBox.addEventListener("webkitTransitionEnd", function() {
        if (index >= 9) {
            index = 1;
            removeTransition();
            changePosition(-index * width);
        } else if (index <= 0) {
            index = 8;
            removeTransition();
            changePosition(-index * width);
        }
    });
    /*过渡结束transitionEnd 会调用*/
    imgBox.addEventListener("transitionEnd", function() {
        if (index >= 9) {
            index = 1;
            removeTransition();
            changePosition(-index * width);
            changePointWithIndex(index);
        } else if (index <= 0) {
            index = 8;
            removeTransition();
            changePosition(-index * width);
            changePointWithIndex(index);
        }
    });
    /*手势*/

    /*手势开始*/
<<<<<<< HEAD
    var startX = 0,moveX = 0;
=======
    var startX = 0,
        moveX = 0;
>>>>>>> f88f5f58e30dde510b2e7047f55b3d483a01540e
    imgBox.addEventListener("touchstart", function(e) {
        console.log("touchstart");
        startX = e.touches[0].clientX;
    });

    // 手在屏幕上移动
    imgBox.addEventListener("touchmove", function(e) {
        console.log("touchmove");
        clearInterval(timer);
        /*清除默认事件*/
        e.preventDefault();
        endX = e.touches[0].clientX;
        /*记录一下移动的距离*/
        moveX = endX - startX;
        removeTransition();
        changePosition(-index * width + moveX);

    });

    /*触屏结束*/
<<<<<<< HEAD
    imgBox.addEventListener("touchend",function(e){
    	console.log("touchend");
    	if (Math.abs(moveX) > (1/3*width) && endX > 0) {

    		if (moveX > 0) {
    			index--;
    		}else{
    			index++;
    		}
    		changePosition(-index*width);
    		changePointWithIndex(index);
    	}
    	/*如果没有超过3分之一执行的是这部分代码*/
    	addTransition();
    	changePosition(-index*width);
    	changePointWithIndex(index);
    	/*初始化*/
    	startX = 0;
    	endX = 0;
    	clearInterval(timer);
    	timer = setInterval(function(){
    		index++;
    		addTransition();
    		changePosition(-index*width);
    		changePointWithIndex(index);
    	},3000);
=======
    imgBox.addEventListener("touchend", function(e) {
        console.log("touchend");
        if (Math.abs(moveX) > (1 / 3 * width) && endX > 0) {

            if (moveX > 0) {
                index--;
            } else {
                index++;
            }
            changePosition(-index * width);
            changePointWithIndex(index);
        }
        /*如果没有超过3分之一执行的是这部分代码*/
        addTransition();
        changePosition(-index * width);
        changePointWithIndex(index);
        /*初始化*/
        startX = 0;
        endX = 0;
        clearInterval(timer);
        timer = setInterval(function() {
            index++;
            addTransition();
            changePosition(-index * width);
            changePointWithIndex(index);
        }, 3000);
>>>>>>> f88f5f58e30dde510b2e7047f55b3d483a01540e
    });

};

/*掌上秒杀倒计时*/

<<<<<<< HEAD
var secondKill = function(){

/*父盒子*/
var sk_time = document.getElementsByClassName("sk_time")[0];
var timeList = sk_time.getElementsByClassName("sk_num");
var times = 10000;

var timer = setInterval(function(){
    times--;
    var h = Math.floor(times/(60*60));
    var m = Math.floor(times/60%60);
    var s = Math.floor(times%60);

    timeList[0].innerText = h >= 10 ? Math.floor(h/10) : 0;
    timeList[1].innerText = h%10;

    timeList[2].innerText = m >= 10 ? Math.floor(m/10) : 0;
    timeList[3].innerText = m%10;

    timeList[4].innerText = s >= 10 ? Math.floor(s/10) : 0;
    timeList[5].innerText = s%10;
    if (times <= 0) {
        clearInterval(timer);
    }
},1000);
=======
var secondKill = function() {

    /*父盒子*/
    var sk_time = document.getElementsByClassName("sk_time")[0];
    var timeList = sk_time.getElementsByClassName("sk_num");
    var times = 10000;

    var timer = setInterval(function() {
        times--;
        var h = Math.floor(times / (60 * 60));
        var m = Math.floor(times / 60 % 60);
        var s = Math.floor(times % 60);

        timeList[0].innerText = h >= 10 ? Math.floor(h / 10) : 0;
        timeList[1].innerText = h % 10;

        timeList[2].innerText = m >= 10 ? Math.floor(m / 10) : 0;
        timeList[3].innerText = m % 10;

        timeList[4].innerText = s >= 10 ? Math.floor(s / 10) : 0;
        timeList[5].innerText = s % 10;
        if (times <= 0) {
            clearInterval(timer);
        }
    }, 1000);
>>>>>>> f88f5f58e30dde510b2e7047f55b3d483a01540e
}
