// pages/live/ceshi/ceshi.js
Page({

  data: {

  },

  onLoad(options) {
    const ctx = wx.createCanvasContext('canvas')
    ctx.setFillStyle('rgba(255, 255, 255, 0.3)')
    ctx.fillRect(0, 0, 250, 120)
    ctx.setFontSize(14)
    ctx.fillText('欧皇', 0, 14)
    let metrics = ctx.measureText('欧皇')
    console.log(metrics)
    ctx.draw()
  }
})