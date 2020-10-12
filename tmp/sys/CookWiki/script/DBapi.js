//定义数据API接口的appKey
var appKey = "5ed1ca988555463cae6707d46309322e";

//定义相关变量
var page = 1;

//加载首页推荐内容
function loadRecommendData(type) {

	var pn = rd(0, 99);
	var network = api.connectionType;
	var recommendCache = $api.getStorage('recommendCache');

	if (recommendCache && type == "load") {
		var pageHtml = "";
		$.each(recommendCache, function(index, item) {
			if (network == "wifi") {
				var getTpl = $api.html($api.byId("pageTpl"));
				laytpl(getTpl).render(item, function(html) {
					pageHtml = pageHtml + html;
				});
			} else {
				if ($api.getStorage('showImgOn3G') == "true") {
					var getTpl = $api.html($api.byId("pageTpl"));
					laytpl(getTpl).render(item, function(html) {
						pageHtml = pageHtml + html;
					});
				} else {
					var getTpl = $api.html($api.byId("pageTplNoImg"));
					laytpl(getTpl).render(item, function(html) {
						pageHtml = pageHtml + html;
					});
				}
			}

			$api.html($api.byId("pageListCont"), pageHtml);
			laterLoadImg();
		})
	} else {
		api.showProgress({
			title : '加载中...',
			modal : false
		});
		
		dbapi.getDataByMenu({
			appKey : appKey,
			menu : "家常",
			rn : 10,
			pn : pn
		}, function(ret) {
			$api.setStorage('recommendCache', ret);
			var pageHtml = "";
			$.each(ret, function(index, item) {
				if (network == "wifi") {
					var getTpl = $api.html($api.byId("pageTpl"));
					laytpl(getTpl).render(item, function(html) {
						pageHtml = pageHtml + html;
					});
				} else {
					if ($api.getStorage('showImgOn3G') == "true") {
						var getTpl = $api.html($api.byId("pageTpl"));
						laytpl(getTpl).render(item, function(html) {
							pageHtml = pageHtml + html;
						});
					} else {
						var getTpl = $api.html($api.byId("pageTplNoImg"));
						laytpl(getTpl).render(item, function(html) {
							pageHtml = pageHtml + html;
						});
					}
				}

				$api.html($api.byId("pageListCont"), pageHtml);
				api.refreshHeaderLoadDone();
				api.hideProgress();

			})
		});
	}
}

//加载分类列表内容
function loadListData(cid) {
	api.showProgress({
		title : '加载中...',
		modal : false
	});

	var network = api.connectionType;

	dbapi.getDataByCid({
		appKey : appKey,
		cid : cid,
		rn : 10,
		pn : page
	}, function(ret) {
		var pageHtml = "";
		$.each(ret, function(index, item) {
			if (network == "wifi") {
				var getTpl = $api.html($api.byId("pageTpl"));
				laytpl(getTpl).render(item, function(html) {
					pageHtml = pageHtml + html;
				});
			} else {
				if ($api.getStorage('showImgOn3G') == "true") {
					var getTpl = $api.html($api.byId("pageTpl"));
					laytpl(getTpl).render(item, function(html) {
						pageHtml = pageHtml + html;
					});
				} else {
					var getTpl = $api.html($api.byId("pageTplNoImg"));
					laytpl(getTpl).render(item, function(html) {
						pageHtml = pageHtml + html;
					});
				}
			}
		});

		$api.append($api.byId("pageListCont"), pageHtml);
		page++;
		api.hideProgress();
	});
}

//加载搜索结果列表
function loadSearchListData(key) {
	api.showProgress({
		title : '加载中...',
		modal : false
	});

	var network = api.connectionType;

	dbapi.getDataByMenu({
		appKey : appKey,
		menu : key,
		rn : 10,
		pn : page
	}, function(ret) {
		var pageHtml = "";
		$.each(ret, function(index, item) {
			if (network == "wifi") {
				var getTpl = $api.html($api.byId("pageTpl"));
				laytpl(getTpl).render(item, function(html) {
					pageHtml = pageHtml + html;
				});
			} else {
				if ($api.getStorage('showImgOn3G') == "true") {
					var getTpl = $api.html($api.byId("pageTpl"));
					laytpl(getTpl).render(item, function(html) {
						pageHtml = pageHtml + html;
					});
				} else {
					var getTpl = $api.html($api.byId("pageTplNoImg"));
					laytpl(getTpl).render(item, function(html) {
						pageHtml = pageHtml + html;
					});
				}
			}
		});

		$api.append($api.byId("pageListCont"), pageHtml);
		page++;
		api.hideProgress();

	});
}

//加载菜谱详细内容
function loadDetail(id) {

	api.showProgress({
		title : '加载中...',
		modal : false
	});

	dbapi.getDetailData({
		appKey : appKey,
		id : id
	}, function(ret) {
		var getTpl = $api.html($api.byId("pageTpl"));
		laytpl(getTpl).render(ret, function(html) {
			$api.html($api.byId("detailCont"), html);
		});
		
		api.hideProgress();
	});
}

//加载收藏列表
function loadFavoritesData(type) {
	api.showProgress({
		title : '加载中...',
		modal : false
	});

	var userId = api.deviceId;
	var network = api.connectionType;

	var model = api.require('model');
	model.config({
		appId : 'A6960480793365',
		appKey : 'D243D208-BE23-8FFC-54D2-57BB1E6BD5CF',
		host : 'https://d.apicloud.com'
	});

	var query = api.require('query');
	query.createQuery(function(ret, err) {
		if (ret && ret.qid) {
			var queryId = ret.qid;
			if (type == "load") {
				query.skip({
					qid : queryId,
					value : (page - 1) * 10
				});
			}
			query.whereEqual({
				qid : queryId,
				column : "uid",
				value : userId
			})
			query.limit({
				qid : queryId,
				value : 10
			});
			query.desc({
				qid : queryId,
				column : "createdAt"
			});
			model.findAll({
				class : "favorites",
				qid : queryId
			}, function(ret, err) {
				if (ret) {
					var pageHtml = "";

					if (ret.length <= 0) {
						api.toast({
							msg : '没有更多内容了',
							duration : 2000,
							location : 'bottom'
						});
					} else {
						$.each(ret, function(index, item) {
							if (network == "wifi") {
								var getTpl = $api.html($api.byId("pageTpl"));
								laytpl(getTpl).render(item, function(html) {
									pageHtml = pageHtml + html;
								});
							} else {
								if ($api.getStorage('showImgOn3G') == "true") {
									var getTpl = $api.html($api.byId("pageTpl"));
									laytpl(getTpl).render(item, function(html) {
										pageHtml = pageHtml + html;
									});
								} else {
									var getTpl = $api.html($api.byId("pageTplNoImg"));
									laytpl(getTpl).render(item, function(html) {
										pageHtml = pageHtml + html;
									});
								}
							}
						});

						//$api.append($api.byId("personalCenter"),pageHtml);

						if (type == "load") {
							page++
							$api.append($api.byId("personalCenter"), pageHtml);
						} else {
							page = 2;
							$api.html($api.byId("personalCenter"), pageHtml);
						}

					}

					api.refreshHeaderLoadDone();
					api.hideProgress();
				} else {
				}
			});
		}
	});
}

//刷新收藏列表
function refreshFavoritesData() {
	//$api.html($api.byId("personalCenter"),"");
	loadFavoritesData();
}

//添加收藏
function createFavorites(el) {

	api.showProgress({
		title : '收藏中...',
		modal : false
	});

	var itemId = $api.attr(el, 'itemId');
	var userId = api.deviceId;

	var model = api.require('model');
	model.config({
		appId : 'A6960480793365',
		appKey : 'D243D208-BE23-8FFC-54D2-57BB1E6BD5CF',
		host : 'https://d.apicloud.com'
	});

	var query = api.require('query');
	query.createQuery(function(ret, err) {
		if (ret && ret.qid) {
			var queryId = ret.qid;
			query.whereEqual({
				qid : queryId,
				column : "tid",
				value : itemId
			});
			query.whereEqual({
				qid : queryId,
				column : "uid",
				value : userId
			});
			model.findAll({
				class : "favorites",
				qid : queryId
			}, function(ret, err) {
				if (ret.length <= 0) {
					dbapi.getDetailData({
						appKey : appKey,
						id : itemId
					}, function(ret) {
						model.insert({
							class : "favorites",
							value : {
								tid : ret.id,
								title : ret.title,
								tags : ret.tags,
								albums : ret.albums,
								uid : userId
							}
						}, function(ret, err) {
							if (ret && ret.id) {
								api.toast({
									msg : '收藏成功，请到收藏页面查看。',
									duration : 2000,
									location : 'bottom'
								});

								if ($api.getStorage('favShare') == 'true') {
									openShareMenu(el);
								}
							} else {
								api.toast({
									msg : '收藏失败',
									duration : 2000,
									location : 'bottom'
								});
							}
						});
					});
				} else {
					api.toast({
						msg : '您已收藏过该信息',
						duration : 2000,
						location : 'bottom'
					});
				}
			});
			api.hideProgress();
		}
	});
}

//取消收藏
function delFavorites(el) {
	api.showProgress({
		title : '取消收藏中...',
		modal : false
	});

	var itemId = $api.attr(el, 'itemId');

	var model = api.require('model');
	model.config({
		appId : 'A6960480793365',
		appKey : 'D243D208-BE23-8FFC-54D2-57BB1E6BD5CF',
		host : 'https://d.apicloud.com'
	});

	var query = api.require('query');
	query.createQuery(function(ret, err) {
		if (ret && ret.qid) {
			var queryId = ret.qid;
			model.deleteById({
				class : "favorites",
				id : itemId
			}, function(ret, err) {
				if (!err) {
					api.toast({
						msg : '取消收藏成功',
						duration : 2000,
						location : 'bottom'
					});
				} else {
					api.toast({
						msg : '取消收藏失败',
						duration : 2000,
						location : 'bottom'
					});
				}
			});

			api.hideProgress();
			refreshFavoritesData();
		}
	});
}

//跳转详细内容页面
function openDetail(el) {
	var id = $api.attr(el, 'itemId');

	if (id) {
		openWin("detail", "detail.html", {
			id : id
		});
	}
}

//跳转分类列表页面
function openList(cid) {
	//var cid = $api.attr(el,'cid');

	if (cid) {
		openWin("list", "list.html", {
			cid : cid
		});
	}
}

//访问数据接口的回调函数
var dbapi = {
	//根据关键词获取列表
	getDataByMenu : function(param, callback) {

		if (api.connectionType != 'none') {
			api.ajax({
				url : 'http://apis.haoservice.com/lifeservice/cook/query',
				method : 'post',
				cache : true,
				timeout : 30,
				dataType : 'json',
				data : {
					values : {
						"key" : param.appKey,
						"menu" : param.menu,
						"rn" : param.rn,
						"pn" : param.pn
					}
				}
			}, function(ret, err) {
				if (ret) {
					if (ret.error_code == 0) {
						if (ret.result.length <= 0) {
							api.toast({
								msg : '没有更多内容了',
								duration : 2000,
								location : 'bottom'
							});
						} else {
							callback(ret.result);
							laterLoadImg();
						}
					} else {
						api.toast({
							msg : '数据引擎异常，请稍候再试。',
							duration : 2000,
							location : 'bottom'
						});
					}
				} else {
					api.toast({
						msg : '数据引擎异常，请稍候再试。',
						duration : 2000,
						location : 'bottom'
					});
				};
			});
		} else {
			api.toast({
				msg : '无网络，请链接网络使用。',
				duration : 2000,
				location : 'bottom'
			});
		}
	},

	//根据分类获取列表
	getDataByCid : function(param, callback) {

		api.ajax({
			url : 'http://apis.haoservice.com/lifeservice/cook/index',
			method : 'post',
			cache : true,
			timeout : 30,
			dataType : 'json',
			data : {
				values : {
					"key" : param.appKey,
					"cid" : param.cid,
					"rn" : param.rn,
					"pn" : param.pn
				}
			}
		}, function(ret, err) {
			if (ret) {
				if (ret.error_code == 0) {
					if (ret.result.length <= 0) {
						api.toast({
							msg : '没有更多内容了',
							duration : 2000,
							location : 'bottom'
						});
					} else {
						callback(ret.result);
						laterLoadImg();
					}
				} else {
					api.toast({
						msg : '数据引擎异常，请稍候再试。',
						duration : 2000,
						location : 'bottom'
					});
				}
			} else {
				api.toast({
					msg : '数据引擎异常，请稍候再试。',
					duration : 2000,
					location : 'bottom'
				});
			}
		});
	},

	//获取菜谱详细内容
	getDetailData : function(param, callback) {

		api.ajax({
			url : 'http://apis.haoservice.com/lifeservice/cook/queryid',
			method : 'post',
			cache : true,
			timeout : 30,
			dataType : 'json',
			data : {
				values : {
					"key" : param.appKey,
					"id" : param.id
				}
			}
		}, function(ret, err) {
			if (ret) {
				if (ret.error_code == 0) {

					callback(ret.result);
					laterLoadImg();

				} else {
					api.toast({
						msg : '数据引擎异常，请稍候再试。',
						duration : 2000,
						location : 'bottom'
					});
				}
			} else {
				api.toast({
					msg : '数据引擎异常，请稍候再试。',
					duration : 2000,
					location : 'bottom'
				});
			}
		});
	}
}