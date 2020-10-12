(function(exports,R){
    var cond=R.cond;
    var head=R.head;
    var tail=R.tail;
    var map=R.map;
    var pipe=R.pipe;
    var isEmpty=R.isEmpty;
    //util
    function argumentsToArray(argsObj){
        var arr=[];
        for(var i=0;i<argsObj.length;i++){
            arr.push(argsObj[i]);
        }
        return arr;
    }
    //package
    function makePackage(type,data){
        return {
            "@type@":type,
            data:data
        }
    }
    function packageType(pkg){
        if(pkg==null) return null;
        return pkg["@type@"];
    }
    function packageData(pkg){
        return pkg.data;
    }
    //left
    function left(a){
        return makePackage("i_left",a);
    }
    function isLeft(pkg){
        return packageType(pkg)=="i_left";
    }
    function getLeft(pkg){
        return packageData(pkg);
    }
    function leftStream(pkg){
        return function(fn){
            return pkg;
        }
    }
    function onLeft(fn,pkg){
        if(isLeft(pkg)) return fn(getLeft(pkg));
        if(isMilldam(pkg))return onLeftMilldam(fn,pkg);
    }
    //ctx
    function ctx(name){
        return function(a,context){
            context[name]=a;
            return a;
        }
    }
    function debug(name,enable){
        return function(a,context){
            console.log("%cdebugger::start================="+name+"=================","color:#999900");
            console.log(a,context);
            console.log("%cdebugger::end==================="+name+"=================","color:#999900");
            if(enable){
                debugger;
            }
            return a;
        }
    }
    //水闸
    function milldam(fn){
        return makePackage("i_milldam",fn);
    }
    function getMilldam(pkg){
        return packageData(pkg);
    }
    function isMilldam(pkg){
        return packageType(pkg)=="i_milldam";
    }
    function overMilldam(fn,pkg){
        var pfn=getMilldam(pkg);
        pfn(function(data){
            if(isMilldam(data)){
                return overMilldam(fn,data);
            }else{
                  return fn(data);
            }
        });
        return pkg;
    }
    function onLeftMilldam(fn,pkg){
        return overMilldam(function(a){
            if(isLeft(a)) onLeft(fn,a);
        },pkg);
    }
    function milldamStream(pkg){
        return function(fn,context){
            var pfn=getMilldam(pkg);
            var flag={"@flag-empty@":"@flag-empty@"};
            var state1=flag;
            var state2=flag;
            pfn(function(data){
                var fn1=streamCond(data);
                var ndata=fn1(fn,context);
                state2=ndata;
                if(state1!==flag){
                    state1(ndata);
                }
            });
            return milldam(function(fn1){
                if(state2!=flag){
                    fn1(state2);
                    state1=fn1;
                }else{
                    state1=fn1;
                }
            });
        }
    }

    function streamCommon(pkg){
          return function(fn,context){
                return fn(pkg,context);
        }
    }
    var condConf=[
        [isLeft,leftStream]
        ,[isMilldam,milldamStream]
        ,[R.T,streamCommon]
    ];
    function streamCond(a){
        return cond(condConf)(a);
    }

    function stream(){
        var args1=argumentsToArray(arguments);
        args1.push(R.identity);
        return function(a1,context){
            context=context||{};
            return R.reduce(function(acc,fn){
                var fn1=streamCond(acc);
                return fn1(fn,context);
            },a1,args1);
        }
    }

    function dostream(){
        var args1=argumentsToArray(arguments);
        return stream.apply(null,R.tail(args1))(R.head(args1));
    }
    function addStream(pre,astream){
        condConf.unshift([pre,astream]);
    }
    function match(arr) {
        function dofn(arr, arg, context,r) {
            if (isEmpty(arr)) return r;
            var a = head(arr);
            var r = a(arg, context);
            if(isMilldam(r)){
                return milldam(function(fn){
                    overMilldam(function(data){
                        if(isLeft(data)){
                            fn(dofn(tail(arr),arg,context,r));
                        }else{
                            fn(data);
                        }
                    },r);
                });
            }else if(isLeft(r)){
                return dofn(tail(arr),arg,context,r);
            }else {
                return r;
            }
        }
        return function(a,context) {
            return J.streamCond(dofn(arr, a, context))(R.identity);
        }
    }
    exports.J={
        match:match,
        addStream:addStream,
        overMilldam:overMilldam,
        stream:stream,
        dostream:dostream,
        streamCond:streamCond,
        makePackage:makePackage,
        packageType:packageType,
        packageData:packageData,
        left:left,
        isLeft:isLeft,
        getLeft:getLeft,
        onLeft:onLeft,
        ctx:ctx,
        debug:debug,
        milldam:milldam
    }
}(this,R));
