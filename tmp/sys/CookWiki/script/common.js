function openWin(name,url,pageParam){
	var systemType = api. systemType;
	
	if(name && url){
        if(systemType=="ios"){
        	var animationType=["none","fade","flip","ripple","suck","cube"];
        	var num=rd(0,4);
	        api.openWin({
	            name: name,
	            url: url,
	            pageParam:pageParam,
	            bgColor:"#F2EEE9",
	            bounces:false,
	            vScrollBarEnabled:false,
	            hScrollBarEnabled:false,
	            animation:{
	            	type:"push",    //动画类型（详见动画类型常量）
				    subType:"from_right",       //动画子类型（详见动画子类型常量）
				    duration:300,                //动画过渡时间，默认300毫秒
	            },
	            showProgress:true
	        });
        }
        else{
	        api.openWin({
	            name: name,
	            url: url,
	            pageParam:pageParam,
	            bgColor:"#F2EEE9",
	            bounces:false,
	            vScrollBarEnabled:false,
	            hScrollBarEnabled:false,
	            animation:{
	            	type:"push",                //动画类型（详见动画类型常量）
				    subType:"from_right",       //动画子类型（详见动画子类型常量）
				    duration:300,                //动画过渡时间，默认300毫秒
	            },
	            showProgress:true
	        });
        }
    }
    
    api.closeSlidPane();
}

//获取n到m随机整数
function rd(n,m){
    var c = m-n+1;  
    return Math.floor(Math.random() * c + n);
}


function openFrame(name,url,pageParam){
	var header = $api.byId('header');
	$api.fixIos7Bar(header);
	var headerPos = $api.offset(header);
	
	api.openFrame({
        name: name,
        url: url,
        pageParam:pageParam,
        bgColor:"#F2EEE9",
        bounces:false,
        vScrollBarEnabled:true,
        hScrollBarEnabled:false,
        rect:{
            x:0,
            y:headerPos.h,
            w:'auto',
            h:'auto'
        }
    });
}


function openSearchBar(){
	var searchBar = api.require('searchBar');
	searchBar.open({
		placeholder:"请输入菜谱关键词进行搜索",
		bgImg:"widget://res/searchBar_bg.png"
	},function(ret,err){
	    if(ret.isRecord){
	    api.toast({
		    msg: "暂未上线",
		    duration:2000,
		    location: 'bottom'
		});
	        //录音功能
//	        var obj = api.require('speechRecognizer');
//			obj.record({
//			},function(ret,err){
//			    if(ret.status){
//				    searchBar.setText({
//					     text:ret.wordStr
//					 });
//			    }else{
////			        api.toast({
////					    msg: err.msg,
////					    duration:2000,
////					    location: 'bottom'
////					});
//			    }
//			});
	    }else{
	        var pageParam = {key:ret.text};
	        
	        openWin("searchlist","./html/searchlist.html",pageParam);
	    }
	});
}

function exitApp(){
	api.addEventListener({
	    name: 'keyback'
	}, function(ret, err){
	    api.toast({
		    msg: '再按一次返回键退出'+api.appName,
		    duration:2000,
		    location: 'bottom'
		});
		
		api.addEventListener({
		    name: 'keyback'
		}, function(ret, err){
		    api.closeWidget({
			    id: 'A6960480793365',
			    retData: {name:'closeWidget'},
			    silent:true
			});
		});
		
		setTimeout(function(){
			exitApp();
		},3000)
	});
}

function loginQQ(){
	var obj = api.require('qq');
	obj.login(function(ret,err){
		if(ret.status){
			$api.setStorage('shareQQ',{status:ret.status,openId:ret.openId,accessToken:ret.accessToken});
			api.execScript({
			    name: 'root',
			    frameName:'setting',
			    script: '$api.html($api.byId("QQnum"),"已绑定");'
			});
			api.toast({
			    msg: '绑定成功',
			    duration:2000,
			    location: 'bottom'
			});
		}
		else{
			api.toast({
			    msg: '绑定失败',
			    duration:2000,
			    location: 'bottom'
			});
		}
	});
}

function logoutQQ(){
	api.confirm({
	    msg: '确认退出关联的QQ号码？',
	    buttons:[ '确定', '取消']
	},function(ret,err){
	    if(ret.buttonIndex == 1){
	        var obj = api.require('qq');
			obj.logout(function(ret,err) {
			    if (ret.status) {
			    	$api.setStorage('shareQQ',"");
			    	api.execScript({
					    name: 'root',
					    frameName:'setting',
					    script: '$api.html($api.byId("QQnum"),"未绑定");'
					});
			        api.toast({
					    msg: '解除绑定成功',
					    duration:2000,
					    location: 'bottom'
					});
			    }else{
			        api.toast({
					    msg: err.msg,
					    duration:2000,
					    location: 'bottom'
					});
			    }
			});
	    }
	});
}

function shareToQQ(itemId){
	
	var shareQQ = $api.getStorage('shareQQ');
	
	if(shareQQ && shareQQ.status){
		dbapi.getDetailData({
			appKey:appKey,
			id:itemId
		},function(ret){

			api.download({
			    url: ret.albums
			},function(downloadRet,downloadErr){
			    if (downloadRet) {
			        var title = ret.title+"做法";
			        var tags = ret.tags;
			    	var ingredients = ret.ingredients;
			    	var burden = ret.burden;
					var description = "";
					$.each(ret.steps,function(index,item){
						description = description + item.step +"\n";
					});
					var imgUrl = downloadRet.savePath;
					
					var extText="\n————详情请下载菜谱百科查看 http://cookwiki.bmob.cn";
					var shareText = "【" + title + "】\n原料：" + ingredients + "\n配料：" + burden + "\n" + extText;

					var qq = api.require('qq');
					qq.shareText({
						text:shareText
					},function(ret,err){
						if(ret && ret.status){
							api.toast({
							    msg: '请在QQ客户端中完成分享',
							    duration:2000,
							    location: 'bottom'
							});
						} else {
							api.toast({
							    msg: '提交QQ客户端失败',
							    duration:2000,
							    location: 'bottom'
							});
						}
					});
			    } else{
			    
			    };
			});
			
		})
	}
	else{
		api.confirm({
		    msg: '您还未绑定QQ账号，是否立即绑定',
		    buttons:[ '立即绑定', '稍候再说']
		},function(ret,err){
		    if(ret.buttonIndex == 1){
		        loginQQ();
		    }
		});
	}
	
}

function loginWB(){
	var obj = api.require('sinaWeiBo');
	obj.auth(function(ret,err){
		if(ret.status){
			$api.setStorage('shareWB',{status:ret.status,userID:ret.userID,accessToken:ret.accessToken});
			api.execScript({
			    name: 'root',
			    frameName:'setting',
			    script: '$api.html($api.byId("WBnum"),"已绑定");'
			});
			api.toast({
			    msg: '绑定成功',
			    duration:2000,
			    location: 'bottom'
			});
		}
		else{
			api.toast({
			    msg: '绑定失败',
			    duration:2000,
			    location: 'bottom'
			});
		}
	});
}

function logoutWB(){
	api.confirm({
	    msg: '确认退出关联的新浪微博？',
	    buttons:[ '确定', '取消']
	},function(ret,err){
	    if(ret.buttonIndex == 1){
	        var obj = api.require('sinaWeiBo');
			obj.cancelAuth(function(ret,err) {
			    if (ret.status) {
			    	$api.setStorage('shareWB',"");
			    	api.execScript({
					    name: 'root',
					    frameName:'setting',
					    script: '$api.html($api.byId("WBnum"),"未绑定");'
					});
			        api.toast({
					    msg: '解除绑定成功',
					    duration:2000,
					    location: 'bottom'
					});
			    }else{
			        api.toast({
					    msg: err.msg,
					    duration:2000,
					    location: 'bottom'
					});
			    }
			});
	    }
	});
}

function shareToWB(itemId){

	var shareWB = $api.getStorage('shareWB');
	
	if(shareWB && shareWB.status){
		dbapi.getDetailData({
			appKey:appKey,
			id:itemId
		},function(ret){
			
			api.download({
			    url: ret.albums
			},function(downloadRet,downloadErr){
			    if (downloadRet) {
			    	var title = ret.title + "做法";
			    	var tags = ret.tags;
			    	var ingredients = ret.ingredients;
			    	var burden = ret.burden;
					var description = "";
					$.each(ret.steps,function(index,item){
						description = description + item.step +"\n";
					});
					var imgUrl = downloadRet.savePath;
					
					var extText="\n————详情请下载菜谱百科查看 http://cookwiki.bmob.cn";
					var shareText = "【" + title + "】\n原料：" + ingredients + "\n配料：" + burden + "\n" + extText;
					
					var sinaWeiBo = api.require('sinaWeiBo');
					sinaWeiBo.sendRequest({
					    contentType:"image",
					    text:shareText,
					    imageUrl:imgUrl,
					    media:{
					    	title:title,
					    	description:shareText,
					    	thumbUrl:imgUrl
					    }
					},function(ret,err){
					    if (ret.status) {
//					        api.toast({
//							    msg: '请在跳转窗口中进行发送',
//							    duration:2000,
//							    location: 'bottom'
//							});
					    }else{
					        api.toast({
							    msg: '提交微博客户端失败',
							    duration:2000,
							    location: 'bottom'
							});
					    };
					});
					
			    } else{
			    
			    };
			});
			
		})
	}
	else{
		api.confirm({
		    msg: '您还未绑定新浪微博账号，是否立即绑定',
		    buttons:[ '立即绑定', '稍候再说']
		},function(ret,err){
		    if(ret.buttonIndex == 1){
		        loginWB();
		    }
		});
	}
}

function regWX(){
	var weiXin = api.require('weiXin');
	weiXin.registerApp(
	    function(ret,err){
	        if (ret.status) {
//				api.toast({
//				    msg: '微信授权成功',
//				    duration:2000,
//				    location: 'bottom'
//				});
	        } else{
	            api.toast({
				    msg: '微信授权失败',
				    duration:2000,
				    location: 'bottom'
				});
	        }
	    }
	);
}

function shareToWX(param){
	regWX();
	
	dbapi.getDetailData({
		appKey:appKey,
		id:param.itemId
	},function(ret){
		api.download({
			url:ret.albums
		},function(downloadRet,downloadErr){
			var scene = param.type;
			var title = ret.title + "做法";
			var tags = ret.tags;
	    	var ingredients = ret.ingredients;
	    	var burden = ret.burden;
			var description;
			$.each(ret.steps,function(index,item){
				description = description + item.step +"\n";
			});
			var imgUrl = downloadRet.savePath;
			var contentUrl = "http://cookwiki.bmob.cn";
			
			var extText="\n————详情请下载菜谱百科查看 http://cookwiki.bmob.cn";
			var shareText = "【" + title + "】\n原料：" + ingredients + "\n配料：" + burden + "\n" + extText;
			
			var weiXin = api.require('weiXin');
			weiXin.sendRequest({
			    scene:scene,
			    contentType:'text',
			    title:title,
			    description:shareText,
			    thumbUrl:imgUrl,
			    contentUrl:imgUrl
			},function(ret,err){
			    if(ret.status){

			    }else{
			        api.toast({
					    msg: '提交微信客户端失败',
					    duration:2000,
					    location: 'bottom'
					});
			    }
			});
		});
	});
}

function openShareMenu(el){
	var itemId = $api.attr(el,'itemId');
	
	api.actionSheet({
	    cancelTitle: '取消分享',
	    buttons: ['分享给微信好友','分享到微信朋友圈','分享到新浪微博']
	},function(ret,err){
	    if(ret.buttonIndex == 1){
	    	shareToWX({
	    		type:"session",
	    		itemId:itemId
	    	});
	    }
	    else if(ret.buttonIndex == 2){
	    	shareToWX({
	    		type:"timeline",
	    		itemId:itemId
	    	});
	    }
	    else if(ret.buttonIndex == 3){
	    	shareToWB(itemId);
	    }
	});
}

//设置图片延迟加载
function laterLoadImg(){
	echo.init({
		offset:0,
		throttle:150,
		unload:true,
		debounce:false,
	    callback: function (element, op) {
	      if(op === 'load') {
		  	element.classList.add('loaded');
		  } else {
		  	element.classList.remove('loaded');
		  }
	    }
	  });
}
