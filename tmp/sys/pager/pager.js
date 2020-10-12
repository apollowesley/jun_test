/**
 * 使用说明： 将页面内容放入一个元素中，这个元素需要设置 id,class="page",data-init,data-show<br>
 * id：（必需）页面的ID；注意：id为全局唯一标识，如果页面出现相同id的page时仅会保留后出现的那个；<br>
 * class="page"：（必需）表示页面这是一个页面，如果没有该class，页面将不能被pager管理；<br>
 * data-init：（非必需）页面显示时调用的方法，默认与id同名,当需要页面显示时执行该方法，请使用open(id,data)函数来打开一个页面；<br>
 * data-show：（非必需）默认是否显示，一次只能显示一个，如果指定多个，将会显示最后指定的那个；<br>
 * 函数说明：<br>
 * load(url,data)：加载一个远程页面，返回需要显示的页面的ID；<br>
 * 参数：url，ajax请求的数据<br>
 * open(id,data)：打开一个页面；<br>
 * 参数：id:页面ID;data:传给页面的数据；<br>
 * 注意：参数二非必需，如果选择使用参数二，将会将在调用 data-init 方法的时候将这个参数传入；<br>
 * 如果未指定data-init 且也不存在和page的id 同名的函数，传入参数 data将不会有任何作用;<br>
 * back()：返回到上一页面<br>
 * data-lazy='true'：在img标签上设置该属性可以用来标识图片需要懒加载，<br>
 * pager会自动使用 data-src 属性内容来加载图片<br>
 * data-src:设置懒加载的资源的路径
 */
var Pager = (function ($) {
	/** 默认设置 */
	var defaultSetting = {
		/** 控制区域（css选择器） */
		view: "body",
		/** 标识页面的class（默认为 page） */
		pageClass: "page"
	};
	/** 初始化用户设置 */
	var initSetting = function (setting) {
		for (var i in defaultSetting) {
			if (setting[i] == null) {
				setting[i] = defaultSetting[i];
			}
		}
		return setting;
	};
	return function (setting) {
		initSetting(setting);
		var pages = [];
		var pageStack = [];
		var registerPage = function (dom) {
			var id = $(dom).attr("id");
			// 删除掉以前注册的page或者其它页面上注册的同名page
			if (pages[id] != null) {
				$("#" + id).remove();
			}
			pages[id] = dom;
			// 处理ref 功能
			ref(dom);
			$(dom).hide();
			return id;
		};
		var ref = function (dom) {
			var refs = $(dom).find("[data-ref]");
			var refId;
			for (var i = 0; i < refs.length; i++) {
				refId = $(refs[i]).attr("data-ref");
				if (refId == null || refId == "") {
					continue;
				}
				$(refs[i]).html($(refId).html());
			}
		};
		var init = function () {
			var doms = $("." + setting.pageClass);
			var defaultPage = "";
			for (var i = 0; i < doms.length; i++) {
				registerPage(doms[i]);
				if ($(doms[i]).attr("data-show") === "true") {
					defaultPage = doms[i];
				}
			}
			$("." + setting.pageClass).hide();
			if (doms.length != 0) {
				if (defaultPage === "") {
					_pager.open(doms[0].id, {});
				} else {
					_pager.open(defaultPage.id, {});
				}
			}
		};
		var _pager = {
			load: function (url, data) {
				var id;

				$.ajax({
					type: "POST",
					url: url,
					async: false,
					data: data,
					dataType: "html",
					success: function (html) {
						var divid = "area_" + new Date().getTime();
						var div = "<div id='" + divid + "' class='pageArea'></div>";
						$(setting.view).append(div);

						var items = $(html);
						for (var i = 0; i < items.length; i++) {
							if ($(items[i]).hasClass(setting.pageClass)) {
								var pid = registerPage(items[i]);
								if ($(items[i]).attr("data-show") === "true") {
									id = pid;
								}
								// 记录下第一个出现的page或者data-show="true"的page
								if (id == null) {
									id = pid;
								}
							}
						}
						$("#" + divid).html(html);
					}
				});
				this.gc();
				return id;
			},
			gc: function () {
				// 垃圾回收，去掉无用的pageArea
				var areas = $(".pageArea");
				for (var i = 0; i < areas.length; i++) {
					if ($(areas[i]).find("." + setting.pageClass).length === 0) {
						$(areas[i]).remove();
					}
				}
			},
			open: function (pageId, data) {
				if (pages[pageId] == null) {
					alert("页面不存在");
					return;
				}
				$("#" + pageStack[pageStack.length - 1]).hide();
				pageStack.push(pageId);
				$("#" + pageId).show();
				if (data != null) {
					var constructor = $("#" + pageId).attr("data-init");
					if (constructor == null || constructor === "") {
						constructor = pageId;
					}
					var fun;
					eval("fun=" + constructor);
					if (typeof (fun) === "function") {
						fun(data);
					}
				}
			},
			back: function () {
				if (pageStack.length === 1) {
					window.history.go(-1);
					return;
				}
				$("#" + pageStack.pop()).hide();
				$("#" + pageStack[pageStack.length - 1]).show();
			}
		};
		init();
		return _pager;
	}
})(jQuery);