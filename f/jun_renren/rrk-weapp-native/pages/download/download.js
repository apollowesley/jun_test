const util = require('../../utils/util.js')

Page({

  data: {
    ios: false,
    android: false
  },

  onLoad(options) {
    let sys = (wx.getStorageSync('systemInfo')) || ''
    let ios_reg = sys.system.search('iOS')
    let android_reg = sys.system.search('Android')
    console.log(ios_reg, android_reg)
    if (ios_reg > -1) this.setData({ ios: true })
    if (android_reg > -1) this.setData({ android: true })
  },

  // 保存
  save() {
    // const ctx = wx.createCanvasContext("canvas")
    // //填充背景色
    // ctx.fillStyle = '#fff';
    // ctx.fillRect(0, 0, 200, 200)

    wx.getImageInfo({
      src: '/assets/common/qrcode@3x.png',
      success (res) {
        let path = res.path
        wx.saveImageToPhotosAlbum({
          filePath: path,
          success (res) {
            util.success('图片已保存')
          }
        })
      }
    })

    console.log(0)
  }
})