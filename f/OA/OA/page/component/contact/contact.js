
Page({
  data:{
    // text:"这是一个页面"
    isHiddenToast:true,
    departments:[]
  },

  isShowToast:function(){
    this.setData({
      isHiddenToast:false
    })
  },
  toastChange:function(){
    this.setData({
      isHiddenToast:true
    })
  },
  onLoad: function (options){
    // 页面初始化 options为页面跳转所带来的参数
    
    var _this = this;
    wx.request({
      //url: 'http://localhost:8080/procePlatform/employee/employessByDept.do',
      url: 'http://192.169.0.239:8080/procePlatform/employee/employessByDept.do',
      header: { 'Content-Type': 'application/x-www-form-urlencoded' },
      method: "POST",
      dataType: "json",
      success: function (res) {
         _this.setData({
           departments: res.data.result
         });
        console.log(res.data);
      },
      fail: function (res) {

      }
    });
    showView: (options.showView == "true" ? true : false)
  },
  onReady:function(){
    // 页面渲染完成
  },
  onShow:function(){
    // 页面显示
  },
  onHide:function(){
    // 页面隐藏
  },
  onUnload:function(){
    // 页面关闭
  },

  bindtap:function(event){
wx.navigateTo({
  url: "/page/message/search/search"
})
  },

})
