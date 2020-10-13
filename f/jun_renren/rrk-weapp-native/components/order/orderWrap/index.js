// components/order/orderWrap/index.js
const util = require('../../../utils/util.js')
const app = getApp()

Component({
  externalClasses: ['custom-class'],
  options: {
    multipleSlots: true // 在组件定义时的选项中启用多slot支持
  },

  properties: {
    orderInfo: {
      type: Object
    },
    idx: {
      type: Number
    }
  },

  /**
   * 组件的初始数据
   */
  data: {

  },

  /**
   * 组件的方法列表
   */
  methods: {
    jumpToDetail({currentTarget}) {
      let [num, type] = [currentTarget.dataset.num, currentTarget.dataset.type]
      wx.navigateTo({
        url: '/pages/order/goodsDetail/goodsDetail?orderNum=' + num + '&orderType=' + type
      })
    },
    onEval({target}) {
      let ev = target.dataset
      util.confirm('请下载人人咖APP，以获得更好的用户体验！', false)
    },
    onReFund({target}) {
      let ev = target.dataset
      util.confirm('请下载人人咖APP，以获得更好的用户体验！', false)
    },
    onCannel({target}) {
      let ev = target.dataset
      this.triggerEvent('onCannel', {
        num: ev.num,
        type: ev.type,
        index: this.data.idx
      })
    },
    onDel({target}) {
      let ev = target.dataset
      this.triggerEvent('onDelete', {
        num: ev.num,
        type: ev.type,
        index: this.data.idx
      })
    },
    onPay({target}) {
      let ev = target.dataset
      wx.navigateTo({
        url: '/pages/pay/index/index?totalMoney=' + ev.price + '&orderNum=' + ev.num
      })
    }
  }
})
