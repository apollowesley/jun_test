var strophe = require('../../../utils/strophe.js')
var WebIM = require('../../../utils/WebIM.js').default
const util = require('../../../utils/util.js')
const api = require('../../../config/api.js')
const app = getApp()

Page({
  // 数据
  data: {
    order: '', // 排序值
    liveUserId: '', // 直播人用户ID
    liveTitle: '', // 直播标题
    userName: '', // 直播用户名
    isLive: false, // 是否直播
    nextOrUp: '', // 直播播放顺序
    video: '', // 直播源地址
    isLike: '0', // 是否关注
    liveInfo: {}, // 直播信息
    list: [], // 商品列表
    goodsShow: false, // 商品弹窗开启状态
    select: 0, // 当前选择商品key
    goodsInfo: {}, // 选中商品信息
    num: 1, // 购买商品数量
    selected: 0, // 选中商品规格,
    isShow: true, // 是否是当前页面
    msg: [], // 环信消息列表
    msgTop: 0, // 环信消息刷新
    chatShow: false, // 消息输入弹窗
    chatLogin: false, // 登录环信
    enterRoom: false // 用户进入聊天室
  },

  // 加载页面
  onLoad(query) {
    // console.log('onLoad')
    util.loading()
    this.initIm() // 加载IM
    this.data.liveUserId = query.liveUserId
    if (wx.getStorageSync('userInfo')) {
      app.globalData.userInfo = wx.getStorageSync('userInfo')
    }

  },

  // 页面显示
  onShow() {
    this.data.isShow = true
    // 用户登录之后进入聊天室使用  此时已有直播信息
    if (!this.data.enterRoom && JSON.stringify(app.globalData.userInfo) !== '{}' && this.data.isLive) {
      this.joinRoom()
    }
  },

  // 页面渲染完成
  onReady(res) {
    // console.log('onReady')
    this.live = wx.createLivePlayerContext('live')
    this.video = wx.createVideoContext('video')
    if (!app.globalData.userInfo.UserId) {
      app.loginCallback = res => {
        this.initPage()
      }
    } else {
      this.initPage()
    }
  },

  // 页面分享
  onShareAppMessage() {
    return {
      title: this.data.userName + '的直播-' + this.data.liveTitle,
      path: '/pages/live/index/index?liveUserId=' + this.data.liveUserId
    }
  },

  // 环信监听事件
  initIm() {
    //集成官方微信小程序sdk
    var that = this
    WebIM.conn.listen({
      onOpened: function(message) {
        console.log(message)
        that.data.chatLogin = true
        that.joinRoom()
      },
      onClosed: function(message) {
        console.log(message)
      },
      onOffline: function() {
        console.log('本机网络掉线')
      },
      onPresence: function(message) {
        console.log(message)
        let jidArr = message.fromJid.split("/")
        switch (message.type) {
          case "memberJoinChatRoomSuccess":
            if (jidArr[1] === message.to) {
              util.toast('已加入直播间聊天室')
              that.data.enterRoom = true
            }
            break;
          case "memberLeaveChatRoomSuccess":
            if (jidArr[1] === message.to) {
              console.log('退出房间')
              that.data.enterRoom = false
            }
            break;
        }
      },
      //接收文字信息
      onTextMessage: function(message) {
        // console.log(message)
        if (message.data) {
          that.messageHandle(message)
        }
      },
      // 各种异常
      onError: function(error) {
        console.log(error)
        // 16: server-side close the websocket connection
        if (error.type == WebIM.statusCode.WEBIM_CONNCTION_DISCONNECTED) {
          if (WebIM.conn.autoReconnectNumTotal < WebIM.conn.autoReconnectNumMax) {
            return;
          }
          wx.showToast({
            title: '服务端断开连接',
            duration: 1000
          });
          return;
        }
        // 8: offline by multi login
        if (error.type == WebIM.statusCode.WEBIM_CONNCTION_SERVER_ERROR) {
          wx.showToast({
            title: '您的账号已在其他地方登录',
            duration: 1000
          })
          return;
        }
      },
    })
  },

  // 环信登录
  loginIM() {
    let that = this
    if (app.globalData.userInfo.MobilePhone !== '') {
      let options = {}
      options.apiUrl = WebIM.config.apiURL
      options.user = app.globalData.userInfo.UserId.toString()
      options.pwd = '111111'
      options.grant_type = 'password'
      options.appKey = WebIM.config.appkey
      WebIM.conn.open(options)
    }
  },

  // 加入房间
  joinRoom() {
    if (app.globalData.userInfo.MobilePhone !== '' && !this.data.enterRoom) {
      this.data.enterRoom = true
      WebIM.conn.joinChatRoom({
        roomId: this.data.liveInfo.hxId
      })
    }
  },

  // 退出房间
  quitRoom() {
    if (this.data.enterRoom) {
      this.setData({
        msg: []
      })
      WebIM.conn.quitChatRoom({
        roomId: this.data.liveInfo.hxId
      })
    }
  },

  // 关闭链接
  closeIM() {
    console.log('关闭链接')
    WebIM.conn.close()
  },

  // 处理消息数据
  messageHandle(message) {
    let data = JSON.parse(message.data)
    let enterRoom = util.throttle(this.enterMsgHandle, 1000)
    switch (parseInt(data.showType)) {
      case 2:
        if (parseInt(data.type) === 3) {
          // 送礼物
          console.log('data')
        }
        break;
      case 10:
        // 接收消息
        this.chatMsgHandle(data)
        break;
      case 12:
        // 用户进入队列消息提醒
        enterRoom(data)
        break;
    }
    switch (parseInt(data.type)) {
      case 11:
        // 退出直播间
        util.toast('直播已结束，正在为您切换下一个直播', this.next)
        break;
      case 12:
        // 用户购买提示
        break;
      case 13:
        // 用户购买提示
        break;
      case 14:
        this.goodsMsgHandle(data)
        // 商品更换提示
        break;
      case 15:
        // 更新余额
        break;
      case 21:
        // 更新咖币
        break;
    }
  },

  // 处理聊天消息
  chatMsgHandle(data) {
    let msgaArr = this.data.msg // 赋值到新数组
    let msg = msgaArr.concat(data) // 增加数据
    if (msg.length > 50) {
      msg.shift() // 消息截取最后50条数据
    }
    this.setData({
      msg: msg
    })
    this.setData({
      msgTop: 10000
    })
  },

  // 处理进入房间消息
  enterMsgHandle(data) {
    // console.log(data)
  },

  // 处理商品消息
  goodsMsgHandle(data) {
    let liveInfo = this.data.liveInfo
    if (data.productId) {
      liveInfo.product = data
    } else {
      liveInfo.product = ''
    }
    this.setData({
      liveInfo: liveInfo,
      select: 0
    })
  },

  // 用户聊天窗口
  userChat() {
    if (this.checkMobile()) {
      if (this.data.isLive) {
        console.log('开启用户聊天窗口')
        this.setData({
          chatShow: true
        })
      } else {
        util.toast('主播尚未开启直播')
      }
    }
  },

  // 提交聊天内容
  chatSubmit(e) {
    var that = this
    let msgInfo = e.detail.value.msg
    if (msgInfo === '') {
      util.toast('请输入聊天内容')
      return false
    }
    let id = WebIM.conn.getUniqueId()
    const msg = new WebIM.message('txt', id)
    let message = {
      userId: app.globalData.userInfo.UserId.toString(),
      message: msgInfo,
      showType: 10,
      name: app.globalData.userInfo.UserName,
      type: 1,
      rank: app.globalData.userInfo.Grade ? app.globalData.userInfo.Grade : 1 // 最低一级
    }
    that.chatMsgHandle(message) // 提前显示到自己的界面
    const option = {
      msg: JSON.stringify(message), // 消息内容
      to: this.data.liveInfo.hxId, // 接收消息对象(聊天室id)
      roomType: true,
      chatType: 'chatRoom',
      success: function() {
        that.chatReset()
      },
      error: function(error) {
        util.alert(error)
        console.log(error)
      }
    }
    msg.set(option)
    msg.setGroup('groupchat')
    WebIM.conn.send(msg.body)
  },

  // 重置聊天窗口
  chatReset() {
    this.setData({
      chatShow: false
    })
  },

  // 重置数据，重新请求
  initPage() {
    console.log(this.data.enterRoom)
    this.quitRoom()
    this.setData({
      select: 0,
      selected: 0,
      goodsShow: false,
      goodsCount: 0,
      isLike: '0',
      isLive: false,
      list: [],
      goodsInfo: {},
      num: 1
    })
    this.getInfo(res => {
      this.getGoodsInfo()
      if (this.data.chatLogin) {
        this.joinRoom()
      } else if (this.data.isLive && !this.data.enterRoom) {
        this.loginIM()
      }
    })
  },

  // 获取直播信息
  getInfo(callback) {
    let params = {}
    params.userId = app.globalData.userInfo.UserId // 当前看直播的用户ID 必须存在
    params.order = this.data.order || ''
    params.liveUserId = this.data.liveUserId || '' // 当前直播ID
    params.nextOrUp = this.data.nextOrUp || '' // 下一条还是上一条
    util.request(api.getLiveInfo, params).then(res => {
      wx.hideLoading()
      if (res.code !== '0') {
        util.confirm("当前暂无用户直播", false).then(this.userPage)
        return false
      } else {
        let liveInfo = res.body
        this.data.order = liveInfo.order
        wx.setNavigationBarTitle({
          title: liveInfo.liveName
        })
        this.setData({
          video: '',
          liveTitle: liveInfo.liveName,
          userName: liveInfo.ShortUser.userName
        })
        this.setData({
          liveInfo: liveInfo,
          liveUserId: liveInfo.userId,
          video: liveInfo.rtmp || liveInfo.lookBack,
          isLive: !!liveInfo.rtmp,
          isLike: liveInfo.isLike
        })
        // this.data.isLive = !!liveInfo.rtmp
        // TODO:测试开启
        // if (!this.data.isLive) {
        //   this.setData({
        //     video: 'http://v.fted.net/FvciXafb7qhQpOnf4bsKfav7MA8m'
        //   })
        // }
        wx.setStorageSync('outLiveId', liveInfo.userId)
      }
      if (callback) callback()
    })
  },

  // 获取商品信息
  getGoodsInfo() {
    let params = {}
    params.isCompany = 1
    params.userId = this.data.liveUserId
    params.type = 1
    util.request(api.getLiveGoods, params).then(res => {
      if (res.code === '0') {
        this.setData({
          list: res.body
        })
      }
    })
  },

  // 打开咖店
  openShop() {
    if (this.checkMobile()) {
      if (this.data.list.length !== 0) {
        this.setData({
          goodsShow: !this.data.goodsShow,
          goodsInfo: this.data.list[this.data.select]
        })
      } else {
        util.error('暂无商品')
      }
    }
  },

  // 显示商品
  openGoods() {
    if (this.data.liveInfo && this.data.liveInfo.product !== '') {
      let id = this.data.liveInfo.product.productId.toString()
      let i = this.data.list.findIndex(e => e.id === id)
      this.data.select = i
      this.openShop()
    }
  },

  // 商品切换
  goodsChange(e) {
    let key = e.target.dataset.idx
    if (key === 0) {
      this.data.select = this.data.select - 1
      if (this.data.select < 0) {
        this.data.select = this.data.list.length - 1
      }
    } else {
      this.data.select = this.data.select + 1
      if (this.data.select === this.data.list.length) {
        this.data.select = 0
      }
    }
    this.setData({
      goodsInfo: this.data.list[this.data.select]
    })
  },

  // 减少数量
  minus() {
    if (this.data.num > 1) {
      this.setData({
        num: this.data.num - 1
      })
    }
  },
  // 增加数量
  plus() {
    if (this.data.num < 99) {
      this.setData({
        num: this.data.num + 1
      })
    }
  },

  // 多规格商品选择
  selectNorms(e) {
    let key = e.target.dataset.idx
    let goodsInfo = this.data.goodsInfo
    goodsInfo.price = goodsInfo.pInfo[key].sellPrice
    this.setData({
      selected: key,
      goodsInfo: goodsInfo
    })
  },

  // 加入购物车
  addCart() {
    if (this.checkMobile()) {
      util.loading()
      let params = {}
      params.userId = this.data.goodsInfo.merId
      params.productId = this.data.goodsInfo.pInfo[this.data.selected].id
      params.buyUserId = app.globalData.userInfo.UserId.toString()
      params.distributionId = wx.getStorageSync('shareUserId') || ''
      params.number = this.data.num
      util.request(api.addCart, params).then(res => {
        wx.hideLoading()
        if (res.code !== '0') {
          util.error(res.msg)
          return false
        } else {
          util.success(res.msg)
        }
      })
    }
  },

  // 立刻购买
  buyNow() {
    if (this.checkMobile()) {
      switch (this.data.goodsInfo.productType) {
        case '0':
          this.goodsBuy()
          break
        case '1':
          this.grouponBuy()
          break
        default:
          break
      }
    }
  },

  // 购买商品
  goodsBuy() {
    wx.setStorageSync('goodsInfo', this.data.goodsInfo)
    wx.setStorageSync('selectNorms', this.data.selected)
    wx.setStorageSync('selectNum', this.data.num)
    wx.navigateTo({
      url: '/pages/goods/confirm'
    })
  },

  // 购买团购
  grouponBuy() {
    wx.setStorageSync('grouponInfo', this.data.goodsInfo)
    wx.setStorageSync('selectNum', this.data.num)
    wx.navigateTo({
      url: '/pages/groupon/confirm'
    })
  },

  // 跳转手机绑定
  bindMobile() {
    wx.navigateTo({
      url: '/pages/login/bind-mobile'
    })
  },

  // 跳转关注列表
  userFollow() {
    if (this.checkMobile()) {
      wx.navigateTo({
        url: '/pages/live/follow/follow'
      })
    }
  },

  // 跳转购物车
  userCart() {
    if (this.checkMobile()) {
      wx.navigateTo({
        url: '/pages/cart/index/index'
      })
    }
  },

  // 跳转用户中心
  userPage() {
    if (this.checkMobile()) {
      wx.navigateTo({
        url: '/pages/user/home/home'
      })
    }
  },

  // 关注/取消关注用户
  setAttention() {
    if (this.checkMobile()) {
      this.setAttentionMethod()
    }
  },

  // 用户关注操作
  setAttentionMethod() {
    let params = {}
    params.userId = app.globalData.userInfo.UserId // 当前用户ID
    params.attentionId = this.data.liveUserId // 当前直播用户ID
    if (this.data.isLike === '0') {
      util.request(api.setUserAttention, params).then(res => {
        if (res.code === '0') {
          this.setData({
            isLike: '1'
          })
        }
      })
    } else {
      util.request(api.cancelUserAttention, params).then(res => {
        if (res.code === '0') {
          this.setData({
            isLike: '0'
          })
        }
      })
    }
  },

  // 检查用户登录状态
  checkMobile() {
    let mobile = app.globalData.userInfo.MobilePhone || ''
    if (mobile === '') {
      util.confirm("请先登录").then(this.bindMobile).catch(res => {
        return false
      })
    } else {
      return true
    }
  },

  // 请求上一条直播数据
  prev() {
    util.loading()
    this.data.nextOrUp = '0'
    this.initPage()
    console.log(this.data.isLive)
  },

  // 请求下一条直播数据
  next() {
    util.loading()
    this.data.nextOrUp = '1'
    this.initPage()
    console.log(this.data.isLive)
  },

  onPlay() {
    let live = wx.createLivePlayerContext('live', this)
    live.stop()
    console.log('video开始')
  },

  // 直播状态判断
  liveChange(e) {
    // console.log('live-player code:', e.detail.code)
    if (this.data.isShow) {
      if (e.detail.code === 2001) {
        util.loading()
        console.log('已经连接服务器')
        let video = wx.createVideoContext('video', this)
        video.stop()
      }
      if (e.detail.code === 2004) {
        wx.hideLoading()
        console.log('直播开始播放')
      }
      if (e.detail.code === 3005) {
        wx.hideLoading()
        console.log('RTMP 读/写失败')
      }
      if (e.detail.code === -2301) {
        util.toast('直播已断开，正在为您切换下一个直播', this.next)
      }
    }
  },

  // 视频播放完成
  videoEnd(e) {
    if (this.data.isShow) {
      util.confirm("播放已结束，是否为您切换下一个").then(this.next).catch()
    }
  },

  // 直播/回放错误
  error(e) {
    // console.error('player error:', e)
  },

  // 页面隐藏事件
  onHide() {
    this.data.isShow = false
  },

  // 页面卸载事件
  onUnload() {
    this.data.isShow = false
  }
})