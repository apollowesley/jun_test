var app = getApp();
const pathUrl = app.globalData.pathUrl;
Page({

  /**
   * 页面的初始数据
   */
  data: {
      pathUrl: pathUrl,
      openId: '',
      queryResults:'',
      violationTimes:'',
      totalDeduction: '',
      totalFine:'',//总罚款
      list:' ',
      array: ['同盾', '百融', '中诚信'],
      objectArray: [
          {
              id: 0,
              name: '同盾'
          },
          {
              id: 1,
              name: '百融'
          },
          {
              id: 2,
              name: '中诚信'
          }
      ],
      index: 0,
  },

    bindPickerChange: function (e) {
        console.log('picker发送选择改变，携带值为', e.detail.value)
        this.setData({
            index: e.detail.value
        })
    },
    realname: function (e) {
        this.setData({
            realname: e.detail.value
        })
    },
    cardid: function (e) {
        this.setData({
            cardid: e.detail.value
        })
    },
    bankcardno: function (e) {
        this.setData({
            bankcardno: e.detail.value
        })
    },
    mobile: function (e) {
        this.setData({
            mobile: e.detail.value
        })
    },
    check:function(){
        var that = this;
        console.log("name" + that.data.realname)
        console.log("cardid" + that.data.cardid)
        if(that.data.index == 0){
            console.log("adsasdasdasd")
            wx.request({
                url: pathUrl + '/rest/fk/dotongdun',
                data: {
                    realname: that.data.realname,  
                    cardno: that.data.cardId,
                    bankcardno: that.data.bankcardno,
                    mobile: that.data.mobile,

                },
                method:'POST',
                success: function (res) {
                    console.log(res)
                    if (res.data.ok) {
                        that.setData({
                            // queryResults: res.data.message,//查询结果
                            // violationTimes: res.data.data.result.wzts,//违章次数
                            // totalDeduction: res.data.data.result.wzjfhj,//总扣分
                            // totalFine: res.data.data.result.wzfkhj,//总罚款
                            // list: res.data.data.result.data
                        })
                    } else {
                        wx.showModal({
                            title: '提示',
                            content: '获取数据失败',
                            confirmColor: '#7aa6d1',
                            showCancel: false
                        })
                    }
                }
            })
        }else{
            console.log("123146546546")
            wx.request({
                url: pathUrl + '/rest/fk/dobairongstr',
                data: {
                    RealName: that.data.name,
                    CardNo: that.data.cardId,
                    BankCardNo: that.data.BankCardNo,
                    Mobile: that.data.Mobile,

                },
                method: 'POST',
                success: function (res) {
                    console.log(res)
                    if (res.data.ok) {
                        // that.setData({
                        //     queryResults: res.data.message,//查询结果
                        //     violationTimes: res.data.data.result.wzts,//违章次数
                        //     totalDeduction: res.data.data.result.wzjfhj,//总扣分
                        //     totalFine: res.data.data.result.wzfkhj,//总罚款
                        //     list: res.data.data.result.data
                        // })
                    } else {
                        wx.showModal({
                            title: '提示',
                            content: '获取数据失败',
                            confirmColor: '#7aa6d1',
                            showCancel: false
                        })
                    }
                }
            })
        }
      
    },
  /**
   * 生命周期函数--监听页面加载
   */
    onLoad: function (e) {
        var openId = wx.getStorageSync('openId');
        this.setData({
            openId: openId
        })
    },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {
    
  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {
    
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
    
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
    
  }
})