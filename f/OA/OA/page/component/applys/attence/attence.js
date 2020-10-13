

Page({
  data: {
    tomonth: '',      //当前月
    love: false,
    monthly: false,//旷工，false不是选中状态，true选中状态
    danger: false,
    list: [],         //日期列表
    ymd: "",
    modalHidden: "hidden",
    selected_ymd: "",
    selected_action: "",
  },
  //页面加载
  onLoad: function (options) {
    var that = this
    this.title = options.title
    var tomonth = this.this_month()//调用函数获得当前日期
    that.setData({
      tomonth: tomonth
    })
    that.draw_calendar(tomonth);//显示日历
  },
  prev: function (e) {
    var that = this
    var now = that.data.tomonth;
    var arr = now.split('-');
    var year, month
    if (arr[1] - 1 == 0) {//如果是1月份，则取上一年的12月份
      year = arr[0] - 1;
      month = 12;
    } else {
      year = arr[0];
      month = arr[1] - 1;
    }
    month = (month < 10 ? "0" + month : month);
    var tomonth = year + "-" + month;
    that.setData({
      tomonth: tomonth
    })
    that.draw_calendar(tomonth);//显示日历

  },
  next: function (e) {
    var that = this
    var now = that.data.tomonth;
    var arr = now.split('-');
    var year, month
    if (arr[1] - 0 + 1 == 13) {//如果是1月份，则取上一年的12月份
      year = arr[0] - 0 + 1;
      month = 1;
    } else {
      year = arr[0];
      month = arr[1] - 0 + 1;
    }
    month = (month < 10 ? "0" + month : month);
    var tomonth = year + "-" + month;
    that.setData({
      tomonth: tomonth
    })
    that.draw_calendar(tomonth);//显示日历
  },
  this_month: function (e) {
    var that = this;
    var date = new Date;
    var month = date.getMonth() + 1;
    month = (month < 10 ? "0" + month : month);
    var year = date.getFullYear();
    var tomonth = year + "-" + month;
    console.log(tomonth);
    return tomonth;
  },
  //传递参数当前月进行日历渲染显示
  draw_calendar: function (now) {
    var arr = now.split('-');//根据间隔符分割字符串now
    var year = arr[0];//年
    var month = arr[1];//月
    var that = this;
    var list = [];
    var d = new Date(year, month - 1, 1, 1, 1, 1);//天数
    console.log(d);
    var firstDay = d.getDay();
    var allDate = new Date(d.getFullYear(), (d.getMonth() + 1), 0).getDate();
    var y, m, d, ymd;
    for (var i = 0; i < firstDay; i++) {
      list.push({
        ymd: "",
        date: "",
        love: false,
        danger: false,
        monthly: false,
        today: false,
      })
    }
    var j = 1;
    var k = i;
    for (i; i < allDate + k; i++) {
      var dd = new Date();
      y = dd.getFullYear();
      m = dd.getMonth() + 1;//获取当前月份的日期 ,因为js默认月份从0开始，所以要加一
      d = dd.getDate();
      ymd = year + "-" + month + "-" + j;
      if (year == y && month == m && d == j) {
        var today = true;
        that.setData({
          ymd: ymd
        })
      } else {
        var today = false;
      }

      var action = wx.getStorageSync(ymd);
      list.push({
        ymd: ymd,
        date: j,
        love: action == "love" ? true : false,
        danger: false,
        monthly: action == "monthly" ? true : false,
        today: today
      })
      j++;
    }

    var lastDay = new Date(year, month - 1, allDate, 1, 1, 1).getDay();
    console.log(lastDay);
    var k = i;
    for (i; i < (6 - lastDay + k); i++) {
      list.push({
        ymd: "",
        date: "",
        love: false,
        danger: false,
        monthly: false,
        today: 0,
      })
    }
    that.setData({
      list: list
    })
  },
  love: function (e) {
    var action = wx.getStorageSync(this.data.ymd);
    if (action == "monthly") {
      this.setData({
        modalHidden: ""
      })
      return false;
    }
    var selected = e.currentTarget.dataset.selected
    if (selected == 1) {
      this.setData({
        love: false,
      })
      wx.setStorageSync(this.data.ymd, "")
    } else {
      this.setData({
        love: true,
      })
      wx.setStorageSync(this.data.ymd, "love")
    }
    this.draw_calendar(this.data.tomonth)
  },
  //
  modalChange: function (e) {
    this.setData({
      modalHidden: "hidden"
    })
  }


})
