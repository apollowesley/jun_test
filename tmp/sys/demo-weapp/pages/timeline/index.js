/**
 *
 * 在线体验，微信-发现-小程序-Mina组件库
 * 关注订阅号【黄秀杰】，第一时间收到教程推送
 *
 * @link http://www.it577.net
 * @author 黄秀杰
 */
const utils = require('./utils/utils')

Page({
  data: {
    newsList: [],
  },
  onLoad() {
    let newsList = [{
      "content": "【新加坡风投公司Golden Gate Ventures发起规模1000万美元的加密基金】 位于新加坡的风险投资公司Golden Gate Ventures周五宣布，将发起名为LuneX Ventures、规模1000万美元的基金，投资加密货币和区块链技术初创企业。（路透）",
      "postTime": 1533905187022,
    }, {
      "content": "【OKEx发布《关于执行隐藏和下线交易对的项目名单》】据OKEx官方消息，OKEx公布隐藏和下线交易对的项目名单，其中46个项目的Token被隐藏，29个交易对将下线，其中涉及20个项目的部分交易对。这些项目未下线的其他交易对在OKEx平台可以正常交易。 OKEx表示会保持对所有已上线该平台项目的持续关注，若发现存在符合隐藏或下线交易对标准的将会严格执行，同时也会根据项目后续发展情况对隐藏项目进行取消隐藏或下线交易兑的调整。",
      "postTime": 1533904126947,
    }, {
      "content": "【浙江省整治网络传销 重点查处对象包括数字货币传销案件】据浙江在线消息，自今年3月份，浙江省开展网络传销违法犯罪活动联合整治工作，重点查处对象包括以“虚拟货币”为幌子的违法犯罪活动。截至6月底，全省工商（市场监管）部门、公安机关共立案查处传销案件132起，涉案金额11亿多元。",
      "postTime": 1533803036885,
    }]
    this.setData({
      newsList: utils.formatNews(newsList)
    })
  }
})