'use strict';
import jTool from '../src/js/jTool';
import Base from '../src/js/Base';
import testData from '../src/data/testData';
describe('Base: 验证方法总数', function() {
	var getPropertyCount = null;
	beforeEach(function() {
		getPropertyCount = function(o){
			var n, count = 0;
			for(n in o){
				if(o.hasOwnProperty(n)){
					count++;
				}
			}
			return count;
		}
	});
	afterEach(function(){
		getPropertyCount = null;
	});
	it('Function count', function() {
		expect(getPropertyCount(Base)).toBe(8);
	});
});
describe('Base.js', function() {
	let table = null;
	let $table = null;
	let gmName = 'test-base';
	beforeAll(function(){
		// 引入组件, 实例化 Element.prototype.GM
		require('../src/js/GridManager').default;

		table = document.createElement('table');
		table.setAttribute('grid-manager', gmName);
		document.querySelector('body').appendChild(table);
		$table = jTool('table[grid-manager="'+ gmName +'"]');
		document.querySelector('table[grid-manager="'+ gmName +'"]').GM({
			ajax_data: testData
			,disableCache: true
			,i18n: 'en-us'
			,columnData: [
				{
					key: 'name',
					width: '100px',
					text: '名称'
				},{
					key: 'info',
					text: '使用说明'
				},{
					key: 'url',
					text: 'url'
				},{
					key: 'createDate',
					text: '创建时间'
				},{
					key: 'lastDate',
					text: '最后修改时间'
				},{
					key: 'action',
					text: '操作',
					template: function(action, rowObject){
						return '<span class="plugin-action edit-action" learnLink-id="'+rowObject.id+'">编辑</span>'
							+'<span class="plugin-action del-action" learnLink-id="'+rowObject.id+'">删除</span>';
					}
				}
			]
		});
	});
	afterAll(function () {
		table = null;
		$table = null;
		gmName = null;
		Element.prototype.GM = Element.prototype.GridManager = null;
		document.body.innerHTML = '';
	});

	it('Base.getColTd($th)', function(){
		let $th = jTool('th[th-name="name"]', $table);
		expect(Base.getColTd($th).length).toBe(8);
	});

	// TODO getTextWidth() jasmine 无法通过测试
	// it('Base.getTextWidth(th)', function(){
	// 	let $th = jTool('th[th-name="name"]', $table);
	// 	expect(Base.getTextWidth($th)).toBe(100);
	// });

	it('Base.showLoading(dom ,cb)', function(){
		jasmine.clock().install();
		let tableWrap = $table.closest('.table-wrap');
		let callbackFN = jasmine.createSpy('callback');
		expect(jTool('.load-area', tableWrap).length).toBe(0);
		Base.showLoading(tableWrap, callbackFN);
		expect(jTool('.load-area', tableWrap).length).toBe(1);
		jasmine.clock().tick(101);
		expect(callbackFN).toHaveBeenCalled();

		jasmine.clock().uninstall();
		tableWrap = null;
		callbackFN = null;
	});

	it('Base.hideLoading(dom ,cb)', function(){
		jasmine.clock().install();
		let tableWrap = $table.closest('.table-wrap');
		let callbackFN = jasmine.createSpy('callback');
		expect(jTool('.load-area', tableWrap).length).toBe(1);
		Base.hideLoading(tableWrap, callbackFN);
		jasmine.clock().tick(501);
		expect(jTool('.load-area', tableWrap).length).toBe(0);
		expect(callbackFN).toHaveBeenCalled();

		jasmine.clock().uninstall();
		tableWrap = null;
		callbackFN = null;
	});

	it('Base.updateInteractive($table, interactive)', function(){
		let	tableWrap = $table.closest('.table-wrap');
		expect(tableWrap.attr('user-interactive') === 'undefined');
		Base.updateInteractive($table, 'Drag');
		expect(tableWrap.attr('user-interactive') === 'Drag');

		Base.updateInteractive($table, 'Adjust');
		expect(tableWrap.attr('user-interactive') === 'Adjust');

		Base.updateInteractive($table);
		expect(tableWrap.attr('user-interactive') === 'undefined');
		tableWrap = null;
	});


});
