// //获取小程序实例
// var app=getApp();

// Page({
//   //页面数据
//   data:{
//     name:"",
//     message:"",
//   },
//   onLoad:function(options){
//     // 页面初始化 options为页面跳转所带来的参数
//     console.log(options);
//     var that=this;
//      that.setData({
//        name: options.name
//      })
//   },

//   onShow:function(){
//     // 页面显示
//     //动态改变页面标题
//     wx.setNavigationBarTitle({
//       title: this.data.name,
//     })
//   },
//   //获取用户输入的消息
//   message:function(e){
//     this.setData({
//       message:e.detail.value
//     })
//   },
//   //消息发送事件
//   send:function(){
//     var that=this;
//     var user = app.globalData.user.sap;
//     var reqData="{'user':'"+user+"','message':'"+that.data.message+"','touser':'"+that.data.name+"'}";
//     console.log(reqData);
//     wx.request({
//       url: '',
//       header:{},
//       method:"POST",
//       dataType:"json",
//       data:{reqData:redData},
//       success:function(res){
//         //将发送的信息显示到聊天区
//         var sendMessageView="";

//       },
//       fail:function(res){
//         //提示发送失败的原因
//       }
//     });
//   }
// })
//index.js
//获取应用实例
var app = getApp()
Page({
  data: {
    name: "",
    userInfo: {},
    inputValue: "",
    mesArray: []
  },

  onLoad: function (options) {
    var that=this;
     that.setData({
       name: options.name
     })
    //调用应用实例的方法获取全局数据
    app.getUserInfo(function (userInfo) {
      //更新数据
      that.setData({
        userInfo: userInfo
      })
    })
  },
  onShow:function(){
    // 页面显示
    //动态改变页面标题
    wx.setNavigationBarTitle({
      title: this.data.name,
    })
  },
  bindKeyInput: function (e) {
    this.setData({
      inputValue: e.detail.value,
      inputSign: true
    });

  },

  sendMes: function () {
    var oriMesArr = this.data.mesArray;
    var newMes = this.data.inputValue;
    if (newMes != "") {
      var myNewMes = {
        mesType: "myItem",
        mesitem: {
          userInfo: this.data.userInfo,
          mes: newMes
        }
      };
      oriMesArr.push(myNewMes);
      this.setData({ mesArray: oriMesArr });
      this.setData({ inputValue: "" });

    }
  }

})

