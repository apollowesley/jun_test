/*
 * Base: 基础方法
 * */
import $ from './jTool';
const Base = {
	/*
	 @输出日志
	 type: 输出分类[info,warn,error]
	 */
	outLog: function(msg, type){
		if(!type){
			console.log('GridManager:', msg);
		}
		else if(type === 'info'){
			console.info('GridManager Info: ', msg);
		}
		else if(type === 'warn'){
			console.warn('GridManager Warn: ', msg);
		}
		else if(type === 'error'){
			console.error('GridManager Error: ', msg);
		}
		return msg;
	}
	/*
	 * @获取与 th 同列的 td jTool 对象, 该方法的调用者只允许为 Th
	 * $th: jTool th
	 * */
	,getColTd: function($th) {
		const tableWrap = $th.closest('.table-wrap'),
			    thIndex = $th.index(),
			     trList = $('tbody tr', tableWrap);
		let tdList= [];
		let _td = null;
		$.each(trList, (i, v) => {
			_td = $('td', v).get(thIndex);
			if(_td){
				tdList.push(_td);
			}
		});
		return $(tdList);
	}
	/*
	* @初始化列显示\隐藏
	* */
	,initVisible: function($table){
		// 所有的th
		const _thList = $('thead th', $table);

		// tbody下的tr
		const _trList = $('tbody tr', $table);
		let	_td = null;
		$.each(_thList, function(i, v){
			v = $(v);
			$.each(_trList, function(i2, v2){
				_td = $('td', v2).eq(v.index());
				_td.attr('td-visible', v.attr('th-visible'));
			});
		});
	}
	/*
	 @设置列是否可见
	 $._thList_	： 即将配置的列所对应的th[jTool object，可以是多个]
	 $._visible_: 是否可见[Boolean]
	 $.cb		: 回调函数
	 */
	,setAreVisible: function(_thList_, _visible_ ,cb){
		let _table,			//当前所在的table
			_tableWarp, 	//当前所在的容器
			_th,			//当前操作的th
			_trList, 		//当前tbody下所有的tr
			_tdList = [], 	//所对应的td
			_checkLi,		//所对应的显示隐藏所在的li
			_checkbox;		//所对应的显示隐藏事件
		$.each(_thList_, (i, v) => {
			_th = $(v);
			_table = _th.closest('table');
			_tableWarp = _table.closest('.table-wrap');
			_trList = $('tbody tr', _table);
			_checkLi = $('.config-area li[th-name="'+ _th.attr('th-name') +'"]', _tableWarp);
			_checkbox = _checkLi.find('input[type="checkbox"]');
			if(_checkbox.length == 0){
				return;
			}
			$.each(_trList, (i2, v2) => {
				_tdList.push($(v2).find('td').get(_th.index()));
			});
			//显示
			if(_visible_){
				_th.attr('th-visible', 'visible');
				$.each(_tdList, (i2, v2) => {
					// $(v2).show();
					v2.setAttribute('td-visible', 'visible');
				});
				_checkLi.addClass('checked-li');
				_checkbox.prop('checked', true);
			}
			//隐藏
			else{
				_th.attr('th-visible','none');
				$.each(_tdList, (i2, v2) => {
					// $(v2).hide();
					v2.setAttribute('td-visible', 'none');
				});
				_checkLi.removeClass('checked-li');
				_checkbox.prop('checked', false);
			}
			typeof(cb) == 'function' ? cb() : '';
		});
	}

	/*
	 @获取TH宽度
	 @th: th
	 */
	,getTextWidth: function(th){
		const $th = $(th);
		const thWarp = $('.th-wrap', $th); // th下的GridManager包裹容器
		const thText = $('.th-text', $th); // 文本所在容器

		//文本镜象 用于处理实时获取文本长度
		const tableWrap = $th.closest('.table-wrap');
		const textDreamland	= $('.text-dreamland', tableWrap);

		//将th文本嵌入文本镜象 用于获取文本实时宽度
		textDreamland.text(thText.text());
		textDreamland.css({
			fontSize 	: thText.css('font-size'),
			fontWeight	: thText.css('font-weight'),
			fontFamily	: thText.css('font-family')
		});
		const thPaddingLeft = thWarp.css('padding-left'),
			thPaddingRight = thWarp.css('padding-right');
		const thWidth = textDreamland.width()
						+ (thPaddingLeft ? thPaddingLeft : 0)
						+ (thPaddingRight ? thPaddingRight : 0);
		return thWidth;
	}
	/*
	* 显示加载中动画
	* @dom
	* */
	,showLoading: function (dom ,cb) {
		if (!dom || dom.length === 0) {
			return;
		}
		const loading = dom.find('.load-area');
		if (loading.length > 0) {
			loading.remove();
		}
		const loadingDom= $('<div class="load-area loading"><div class="loadInner kernel"></div></div>');
		dom.append(loadingDom);

		//进行loading图标居中显示
		const loadInner = dom.find('.load-area').find('.loadInner');
		const domHeight = dom.height(),
			loadInnerHeight = loadInner.height();
		loadInner.css('margin-top', (domHeight - loadInnerHeight) / 2);
		window.setTimeout(function(){
			typeof(cb) === 'function' ? cb() : '';
		}, 100);
	},
	/*
	 * 隐藏加载中动画
	 * @dom
	 * */
	hideLoading: function (dom, cb) {
		if (!dom || dom.length === 0) {
			return;
		}
		window.setTimeout(function(){
			$('.load-area', dom).remove();
			typeof(cb) === 'function' ? cb() : '';
		}, 500);
	}
	/**
	 * 更新当前用户交互状态, 用于优化置顶状态下进行[拖拽, 宽度调整]操作时,页面出现滚动的问题
	 * @param $table
	 * @param interactive: 如果不存在于interactiveList内, 将删除属性[user-interactive]
     */
	,updateInteractive: function ($table, interactive) {
		const interactiveList = ['Adjust', 'Drag'];
		// 事件源所在的容器
		let	tableWrap = $table.closest('.table-wrap');
		if(!interactive || interactiveList.indexOf(interactive) === -1){
			tableWrap.removeAttr('user-interactive');
		}
		else{
			tableWrap.attr('user-interactive', interactive);
		}
	}
};
export default Base;
