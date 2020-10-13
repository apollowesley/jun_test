/*
 * Order: 序号
 * */
import $ from './jTool';
import I18n from './I18n';
const Order = {
	/*
	 @生成序号DOM
	 $.table: table [jTool object]
	 */
	initDOM: function($table) {
		const orderHtml = `<th th-name="gm_order" gm-order="true" gm-create="true">${ I18n.i18nText($table, 'order-text') }</th>`;
		$('thead tr', $table).prepend(orderHtml);
	}
};
export default Order;
