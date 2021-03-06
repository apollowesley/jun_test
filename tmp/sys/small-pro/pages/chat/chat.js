// pages/chat/chat.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    waitSend:false,  //发送按钮显示/隐藏
    voiceBtn:true,   //语音按钮
    inp_width:'60%',  //输入框样式变化
    iconWidth: '23%',  //输入框右侧图标
    voiceText:"按住 说话",  //语音标题
    voiceBg:"#fff",   //语音按钮颜色
    scrollto:0,   //聊天界面滑动
    chatPageH:0,   //聊天界面高度
    isload:false,  //加载按钮显示/隐藏
    chatContent:[
      {
        identity:"other",
        currtime: "9月2号 下午3:00",
        istime:true,
        contentType:"text",
        content:"你好！"
      },{
        identity: "self",
        currtime: "9月2号 下午3:00",
        istime: false,
        contentType: "text",
        content: "你好!"
      },{
        identity: "other",
        currtime: "9月2号 下午3:00",
        istime: false,
        contentType: "text",
        content: "你叫什么名字"
      },{
        identity: "self",
        currtime: "9月2号 下午3:00",
        istime: false,
        contentType: "text",
        content: "我是张三，你呢？"
      },{
        identity: "other",
        currtime: "9月2号 下午3:00",
        istime: false,
        contentType: "text",
        content: "我是李四"
      },{
        identity: "self",
        currtime: "9月2号 下午3:00",
        istime: false,
        contentType: "text",
        content: "很高兴认识你！"
      },{
        identity: "other",
        currtime: "9月2号 下午3:00",
        istime: false,
        contentType: "text",
        content: "我也是"
      },
      {
        identity: "other",
        currtime: "9月2号 下午3:00",
        istime: false,
        contentType: "text",
        content: "你好！"
      }, {
        identity: "self",
        currtime: "9月2号 下午3:00",
        istime: false,
        contentType: "text",
        content: "你好!"
      }, {
        identity: "other",
        currtime: "9月2号 下午3:00",
        istime: false,
        contentType: "text",
        content: "你叫什么名字"
      }, {
        identity: "self",
        currtime: "9月2号 下午3:00",
        istime: false,
        contentType: "text",
        content: "我是张三，你呢？"
      }, {
        identity: "other",
        currtime: "9月2号 下午3:00",
        istime: false,
        contentType: "text",
        content: "我是李四"
      }, {
        identity: "self",
        currtime: "9月2号 下午3:00",
        istime: false,
        contentType: "text",
        content: "很高兴认识你！"
      }, {
        identity: "other",
        currtime: "9月2号 下午3:00",
        istime: false,
        contentType: "text",
        content: "我也是"
      }
    ]
  },
  // 切换按钮
  // 发送
  inputTyping:function(event){
    if (event.detail.value.length!==0){
         this.setData({
           waitSend:true,
           inp_width: '55%',
           iconWidth: '25%'
         })
     }
     if(event.detail.value.length === 0){
      this.setData({
        waitSend: false,
         inp_width: '60%',
        iconWidth: '23%'
      })
     }
  },
  // 上拉加载
  loadRecord:function(){
    //  console.log("top");
    // 向后台请求数据，有则加载，无则不显示
      this.setData({
        isload:true
      });
      // 测试代码
      if(this.data.chatContent.length<21){
        this.setData({
          isload: true
        });
        var that = this;
        setTimeout(function(){
          let chatContentArr = that.data.chatContent;
          let newlist = [...chatContentArr];
          that.setData({
            chatContent: that.data.chatContent.concat(newlist)
          });

        },800)
      }else{
        this.setData({
          isload: false
        });
      }
  },
  // 语音
  switchVoice:function(){
     if(this.data.voiceBtn){
       this.setData({
         voiceBtn: false
       })
     }else{
       this.setData({
         voiceBtn: true
       })
     }
  },
  // 打开表情
  emojiBtn:function(){
   
  },
  // 按住说话
  holdVoice:function(){
      this.setData({
        voiceText: "松开 结束",
        voiceBg:"#ddd"
      })
  },
  // 松开结束
  releaseVoice:function(){
    this.setData({
      voiceText: "按住 说话",
      voiceBg:"#FFF"
    })
  },
  // 个人信息
  touserinfo:function(){
    wx.navigateTo({
      url: '../userinfo/userinfo',
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
      // 动态设置导航栏标题
      // let name = options.name;
      let name = "小明"
      wx.setNavigationBarTitle({
        title:name,
      });

      // 获取当前窗口高度
    let windowHeight = wx.getSystemInfoSync().windowHeight;
    // 聊天记录滚动到底部
    this.setData({
      windowHeight:windowHeight+'px',
      scrollto: windowHeight
    })
  }
})