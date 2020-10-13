var os = function() {
    var ua = navigator.userAgent,
        isWindowsPhone = /(?:Windows Phone)/.test(ua),
        isSymbian = /(?:SymbianOS)/.test(ua) || isWindowsPhone,
        isAndroid = /(?:Android)/.test(ua),
        isFireFox = /(?:Firefox)/.test(ua),
        isChrome = /(?:Chrome|CriOS)/.test(ua),
        isTablet = /(?:iPad|PlayBook)/.test(ua) || (isAndroid && !/(?:Mobile)/.test(ua)) || (isFireFox && /(?:Tablet)/.test(ua)),
        isPhone = /(?:iPhone)/.test(ua) && !isTablet,
        isPc = !isPhone && !isAndroid && !isSymbian;
    return {
        isTablet: isTablet,
        isPhone: isPhone,
        isAndroid : isAndroid,
        isPc : isPc
    };
}();
if(os.isAndroid || os.isPhone){
    var mobile_content=document.querySelector('meta[name="mobile-agent"]');
    if(mobile_content){
        var mobileUrl=mobile_content.getAttribute('content').split(';');
        var url=mobileUrl[1].substr(4);
        console.log(url);
        var bool=/^(http|https):\/\/www\.bjpowernode\.com/.test(window.location.href);
        if(bool){
            window.location.href=url;
        }
    }
}

(function(){
    var bp = document.createElement('script');
    var curProtocol = window.location.protocol.split(':')[0];
    if (curProtocol === 'https') {
        bp.src = 'https://zz.bdstatic.com/linksubmit/push.js';
    }
    else {
        bp.src = 'http://push.zhanzhang.baidu.com/push.js';
    }
    var s = document.getElementsByTagName("script")[0];
    s.parentNode.insertBefore(bp, s);
})();


(function(){
    var src = (document.location.protocol == "http:") ? "http://js.passport.qihucdn.com/11.0.1.js?80dcc74d1424a5fe6314fec1903b1fc4":"https://jspassport.ssl.qhimg.com/11.0.1.js?80dcc74d1424a5fe6314fec1903b1fc4";
    document.write('<script src="' + src + '" id="sozz"><\/script>');
})();
