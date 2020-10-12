(function(exports){
	//http://git.oschina.net/diqye/route.js -> /diqye/route.js
	function url2path(url){
		var x = R.head(url);
		var xx = R.take(2,url);
		return R.isEmpty(url)  ? '/'
		: xx =='//'            ? url2path(R.drop(2,url))
		: x== '/'              ? url
		: url2path(R.tail(url));
	}
	function bindEvent(el,type,handler){
		if(el.addEventListener){
			el.addEventListener(type,handler,false)
		}else if(el.attachEvent){
			el.attachEvent('on'+type,handler)
		}
	}
	function h5routeMilldam(){
		function cbk(fn){
			bindEvent(document,'click',function(e){
				var target=e.srcElement||e.target;
				if(target.nodeName=='A'){
					var href=target.getAttribute('href');
					var x=R.head(href);
					if(x=='#'){
						redir(R.tail(href));
						fn(R.tail(href));
						e.stopPropagation();
		                window.event.returnValue = false;
		                return false;
					}
				}
			})
		}
		return J.milldam(function(fn){
			fn(url2path(window.location.href));
			cbk(fn);
			bindEvent(window,"popstate",function(){
				fn(url2path(window.location.href));
			})
		});
	}

	function redir(url){
		exports.history.pushState(null,"h5",url);
	}
	exports.h5r={
		h5routeMilldam:h5routeMilldam,
		redir:redir	
	};
}(this))