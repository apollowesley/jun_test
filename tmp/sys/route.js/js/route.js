(function(exports, J, R) {
    var head = R.head;
    var tail = R.tail;
    var isEmpty = R.isEmpty;
    //不匹配标记函数
    function nextp() {
        return nextp;
    }

    function isRoute(a) {
        if (a === nextp) return true;

        return false;
    }

    function routeStream(a) {
        return function(fn) {
            return J.left(a);
        }
    }
    J.addStream(isRoute, routeStream);

    function route(arr) {
        return function(url,context) {
            context=(context&&context.url)||{url:url};
            return J.match(arr)(url,context);
        }
    }

    function keyvalfn(str) {
        var a = str.split("=");
        return [head(a), tail(a).join("=")]
    }

    function isNull(a) {
        if (a == null) return J.left({});
        return a;
    }
    var querystring = J.stream(isNull,R.split("?"), R.nth(1), isNull, R.split("&"), R.map(keyvalfn), R.fromPairs);
    var queryStringG = R.pipe(R.nthArg(1),R.prop("url"), querystring, R.ifElse(J.isLeft, J.getLeft, R.identity));

    function nameP(p) {
        return function(url) {
            var ourl = url.split("?")[0];
            if (ourl == p) return p;
            return nextp;
        }
    }

    function paramP(p) {
        var toArr = R.pipe(R.split("/"), tail);
        return function(url) {
            var ourl = url.split("?")[0];
            var parr = toArr(p);
            var oarr = toArr(ourl);
            if (parr.length != oarr.length) return nextp;
            return R.zipObj(R.map(R.tail, parr), oarr);
        }
    }

    function startP(p) {
        var dofn = R.pipe(R.take(p.length), R.equals(p));
        function dourl(url){
            return url == ""  ? "/"
            :R.head(url)!="/" ? "/"+url
            :url;
        }
        return function(url) {
            if (dofn(url)) return R.pipe(R.drop(p.length),dourl)(url);
            return nextp;
        }
    }

    function hashMilldam() {
        function getUrl(url) {
            return isEmpty(url)   ? "/"
            : R.take(2,url)=="#/" ? tail(url)
            : head(url)=="#"      ? "/"+tail(url)
            : getUrl(tail(url));
        }
        function addDocumentEvent(fn){
            $(document).on("click","[r-hash]",function(e){
                var a = $(this);
                var url = getUrl(a.attr("href"));
                if(getUrl(window.location.href)==url){
                    fn(url);
                    e.stopPropagation();
                    window.event.returnValue = false;
                    return false;
                }
            });
        }
        return J.milldam(function(fn) {
            if(typeof $ == "function"){
                addDocumentEvent(fn);
            }
            window.onhashchange = function() {
                var url = getUrl(window.location.href);
                fn(url);
            }
            var url = getUrl(window.location.href);
            fn(url);
        });
    }
    exports.route = {
        hashMilldam: hashMilldam,
        route: route,
        nextp: nextp,
        nameP: nameP,
        paramP: paramP,
        startP: startP,
        queryStringG: queryStringG
    }
}(this, J, R));