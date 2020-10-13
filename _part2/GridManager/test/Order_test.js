'use strict';
import jTool from '../src/js/jTool';
import Order from '../src/js/Order';
import testData from '../src/data/testData';
describe('Order', function() {
	let table = null;
	let $table = null;
	let gmName = 'test-order';

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
			,columnData: [
				{
					key: 'name',
					remind: 'the name',
					width: '100px',
					text: '名称',
					sorting: ''
				},{
					key: 'info',
					remind: 'the info',
					text: '使用说明'
				},{
					key: 'url',
					remind: 'the url',
					text: 'url'
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

	it('验证自动生成列[序号,全选]宽度', function(){
		let autoCreateCheckbox = jTool('th[th-name="gm_checkbox"]').width();
		let autoCreateOrder = jTool('th[th-name="gm_order"]').width();
		expect(autoCreateCheckbox).toBe(50);
		expect(autoCreateOrder).toBe(50);
		autoCreateCheckbox = null;
		autoCreateOrder = null;
	});
});
