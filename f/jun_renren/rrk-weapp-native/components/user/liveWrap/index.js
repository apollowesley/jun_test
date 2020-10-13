// components/user/liveWrap/index.js
Component({
  /**
   * 组件的属性列表
   */
  externalClasses: ['custom-class'],
  options: {
    multipleSlots: true // 在组件定义时的选项中启用多slot支持
  },

  properties: {
    liveItem: {
      type: Object
    }
  },

  /**
   * 组件的初始数据
   */
  data: {
    live: ''
  },

  attached() {
    this.data.live = this.properties.liveItem
  },

  /**
   * 组件的方法列表
   */
  methods: {
    jumpToLive() {
      // console.log(this.data.live)
      wx.reLaunch({
        url: '/pages/live/index/index?liveUserId='+this.data.live.userId
      })
    }
  }
})
