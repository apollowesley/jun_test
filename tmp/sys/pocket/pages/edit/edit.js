var app = getApp()
var dateTimePicker = require('../../utils/dateTimePicker.js');
Page({
  data: {
    date: '',
    time: '',
    dateTimeArray: null,
    dateTime: null,
    nowDate: '',
    id:null,
    dateTimeArray1: null,
    dateTime1: null,
    startYear: 2000,
    endYear: 2050,
    navbar: ['支出', '收入'],
    currentTab: 0,
    pay_method: ['现金', '支付宝', '微信', '信用卡', '美团', '其他'],
    index: 0,
    maney: '',
    types: '',
    remark: '',
    multiArray: [[1, 2, 3, 4, 5, 6, 7, 8, 9], [1, 2, 3, 4, 5, 6, 7, 8, 9]],
    multiIndex: [3, 5],
    types:null,
    money:null,
    remark:null
  },
  navbarTap: function (e) {
    this.setData({
      currentTab: e.currentTarget.dataset.idx
    })
  },
  onLoad: function (options) {
    var datetime = new Date();
    this.setData({
      currentTab: options.id == undefined ? 0 : 1,
      id: options.pid
    })
    var that = this;
    wx.request({
      url: app.url.url + '/pocketDetail/info/?id=' + that.data.id,
      method: 'GET',
      header: {
        'Authorization': 'Bearer ' + app.user.token
      },
      success: function (res) {
        if (res.data.code == 200) {
          var datatime = res.data.data.pocketDetail.time;
          var year = datatime.split(" ");
          var s = 0
          console.log(res.data.data.pocketDetail.payMethod+'测试')
          for (var i = 0; i < 6;i++){
            if (that.data.pay_method == res.data.data.pocketDetail.payMethod){
              s= i;
              console.log(s + '测试后')
            }
          }
          that.setData({
            nowDate: datetime.getFullYear() + '-' + ((datetime.getMonth() + 1) < 10 ? "0" + (datetime.getMonth() + 1) : (datetime.getMonth() + 1)) + '-' + (datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate()),
            date:year[0],
            time: res.data.data.pocketDetail.house,
            index:3,
            types: res.data.data.pocketDetail.type,
            money: res.data.data.pocketDetail.money,
            remark: res.data.data.pocketDetail.remark
          })
        }
      }
    })    
    // 获取完整的年月日 时分秒，以及默认显示的数组
    var obj = dateTimePicker.dateTimePicker(this.data.startYear, this.data.endYear);
    var obj1 = dateTimePicker.dateTimePicker(this.data.startYear, this.data.endYear);
    // 精确到分的处理，将数组的秒去掉
    var lastArray = obj1.dateTimeArray.pop();
    var lastTime = obj1.dateTime.pop();
    this.setData({
      dateTime: obj.dateTime,
      dateTimeArray: obj.dateTimeArray,
      dateTimeArray1: obj1.dateTimeArray,
      dateTime1: obj1.dateTime
    });
  },
  changeDate(e) {
    this.setData({ date: e.detail.value });
  },
  changeTime(e) {
    this.setData({ time: e.detail.value });
  },
  // 选择国家函数
  changeCountry: function (e) {
    this.setData({ index: e.detail.value });
  },
  changeDateTime(e) {
    this.setData({ dateTime: e.detail.value });
  },
  changeDateTime1(e) {
    this.setData({ dateTime1: e.detail.value });
  },
  changeDateTimeColumn(e) {
    var arr = this.data.dateTime, dateArr = this.data.dateTimeArray;

    arr[e.detail.column] = e.detail.value;
    dateArr[2] = dateTimePicker.getMonthDay(dateArr[0][arr[0]], dateArr[1][arr[1]]);

    this.setData({
      dateTimeArray: dateArr
    });
  },
  changeDateTimeColumn1(e) {
    var arr = this.data.dateTime1, dateArr = this.data.dateTimeArray1;

    arr[e.detail.column] = e.detail.value;
    dateArr[2] = dateTimePicker.getMonthDay(dateArr[0][arr[0]], dateArr[1][arr[1]]);

    this.setData({
      dateTimeArray1: dateArr
    });
  },
  getmoney: function (e) {
    this.setData({
      money: e.detail.value
    })
  },
  gettype: function (e) {
    this.setData({
      types: e.detail.value
    })
  },
  getremark: function (e) {
    this.setData({
      remark: e.detail.value
    })
  },
  bzcg: function (e) {
    console.log('点击了保存草稿');
  },
  sc: function (e) {
    var that = this;
    var id = this.data.id
    wx.showModal({
      title: '提示',
      content: '确定要删除此记录吗？',
      success: function (res) {
        if (res.confirm) {
          wx.request({
            url: app.url.url + '/pocketDetail/?id=' + id + '&userID=' + app.user.id,
            method: 'DELETE',
            header: {
              'Authorization': 'Bearer ' + app.user.token
            },
            success: function (res) {
              if (res.data.code == 200) {
                wx.reLaunch({
                  url: '../index/index'
                }) 
              } else {
                wx.showToast({
                  title: '删除失败，请联系管理员',
                  icon: 'none',
                  duration: 1000
                })
              }
            }
          })
        } else if (res.cancel) {
          return false;
        }
      }
    })
  },
  //保存
  bc:function(e){
    var that = this;
    var money = this.data.money;
    var id = this.data.id
    var t = money / 10;
    t = t + "1"
    if (t == (NaN + '1')) {
      wx.showToast({
        title: '请输入正确的金额',
        icon: 'none',
        duration: 2000
      })
    } else {
      var pocketType = this.data.currentTab == 0 ? "支出" : "收入"
      wx.request({
        url: app.url.url + '/pocketDetail?id=' + id,
        method: 'PUT',
        data: {
          money: money,
          pocketType: pocketType,
          type: this.data.types,
          remark: this.data.remark,
          time: this.data.date + " " + this.data.time + ":00",
          payMethod: this.data.pay_method[this.data.index]
        },
        header: {
          'Authorization': 'Bearer ' + app.user.token
        },
        success: function (res) {
          if (res.data.code == 200) {
            wx.showToast({
              title: '更新成功',
              icon: 'success',
              duration: 1000
            })
            setTimeout(function () {
              wx.reLaunch({
                url: '../index/index'
              })
            }, 1000)
          } else {
            wx.showToast({
              title: '金额、类别为必填项，请检查！',
              icon: 'none',
              duration: 1000
            })
          }
        }
      })
    }
  }
})