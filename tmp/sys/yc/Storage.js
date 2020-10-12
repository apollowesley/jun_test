// // console.log(urlEncode(arr||obj).slice(1))//调用
var cacheExpires =  172800;//默认缓存周期秒=48小时
var maxExpires=31104000;//最长缓存时间1年
// 判断是数字
function checkRate(nubmer) {
　　var re = /^[0-9]+.?[0-9]*$/; //判断字符串是否为数字 //判断正整数 /^[1-9]+[0-9]*]*$/ 
　　return re.test(nubmer);
}
//计算合法缓存时间
function computeExpires(expires){
        var expires = expires || cacheExpires;
        var nowTime=Date.parse(new Date()) / 1000;
        if(checkRate(expires) ){
            if(expires>nowTime){
               return expires
            }else if(expires<maxExpires){
                return expires+nowTime
            }
        }
           return cacheExpires+nowTime
}
var Sync = {
    /**
     * 异步存入缓存 可对象可数组
     * k 		string 				键
     * val 		array|object|string	缓存的内容
     * expires	int					有效期
     */
    set(k, val, expires) {
        var type = typeof val;
        return localStorage.setItem(k, JSON.stringify({
            data: val,
            expires: computeExpires(expires),
            type: type
        }));
    },
    get(k) {
        try {
            var data = localStorage.getItem(k) || '{}';
            data = JSON.parse(data)
            if (data.expires) {
                if (data.expires > (Date.parse(new Date()) / 1000)) {
                    return data.data;
                }
                localStorage.removeItem(k);
                try {
                    localStorage.removeItem(k);
                } catch (e) {
                    // error
                }
            }
        } catch (e) {
            return false;
            //TODO handle the exception
        }

        return false;

    },
    remove(k) {
        localStorage.removeItem(k);
    },
    clear() {
        localStorage.clear();
    }
}

function clear() {
    sessionStorage.clear();
    localStorage.clear();
}
var Session = {
    clear() {
        sessionStorage.clear();
    },
    set(k, val, expires) {
        var type = typeof val;
        return sessionStorage[k] = JSON.stringify({
            data: val,
            expires: computeExpires(expires),
            type: type
        });
    },
    get(k) {
        try {
            var data = sessionStorage[k] || '{}';
            data = JSON.parse(data)
            if (data.expires) {
                if (data.expires > (Date.parse(new Date()) / 1000)) {
                    return data.data;
                }
                sessionStorage.removeItem(k);
                try {
                    sessionStorage.removeItem(k);
                } catch (e) {
                    // error
                }
            }
        } catch (e) {
            return false;
        }

        return false;

    },
    remove(k) {
        sessionStorage.removeItem(k);

    }
}

export {
    Sync,
    Session,
    clear
}
