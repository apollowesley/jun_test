var app = getApp();
const pathUrl = app.globalData.pathUrl;
Page({
    data: {
        pathUrl: pathUrl,
        orderNo: '',
        proNo:'',
        proIsdb:'',//是否有担保人
        ocrName: '', //ocr姓名
        ocrSex: '', //ocr性别
        ocrNation: '', //ocr民族
        ocrBirth: '', //ocr出生
        ocrAddress: '', //ocr地址
        ocrIdNumber: '', //ocr身份证号
        ocrOffice: '', //ocr签发机关
        ocrValidityPeriod: '', //ocr有效期限
        cusIdValidfrom: '', //证件开始日期
        cusIdValidto: '', //证件结束日期
        brand: '', //品牌/车型
        plateNumber: '', //车牌号
        holder: '', //所有人
        dateIssue: '', //注册/发证日期
        nature: '', //使用性质
        address: '', //住址
        vinno: '', //车架号
        engineNumber: '', //发动机号
        residentialAddress: '', //居住地址
        telephone: '', //联系电话
        checkedValue: '', //婚姻情况
        orderEmerList:[], //紧急联系人
        village:'',//小区
        acreage:'',//面积
        money: '',//金额
        howLong: '',//借多久
        guaranteeName: '',//担保人姓名
        guaranteeIdCard: '',//担保人身份照
        guaranteePhone: '',//担保人电话
        fxjProAttList:[],
        orderBy14:'',//利率
        orderBy15: '',//GPS费
        orderBy16: '',//服务费
        orderBy17: '',//其他
        orderBy4:'',//是否有子女
        orderBy5:'',//学历
        orderBy6:'',//工作单位
        orderBy7:'',//单位地址
        orderBy8: '',//月收入
        orderBy9: '',//储蓄卡，
        proName:''
    },
    onLoad: function (e) {
        this.setData({
            orderNo: e.orderNo,
            proNo: e.proNo,
            proName: e.proName
        })
        wx.setNavigationBarTitle({
            title: e.proName
        })
        // console.log(this.data.orderNo)
        this.orderMainController();
    },
    orderMainController: function () {
        var that = this;
        wx.request({
            url: pathUrl + '/rest/fxjOrderMainController/' + that.data.orderNo,
            data: {

            },
            success: function (res) {
                // console.log(res)
                if (res.data.ok) {
                    var data = res.data.data;
                    var fxjOrderLoanList = res.data.data.fxjOrderLoanList;
                    var ocrValidityPeriod = res.data.data.cusIdValidfrom + ' - ' +  res.data.data.cusIdValidto;
                    if (fxjOrderLoanList && fxjOrderLoanList.length > 0){
                            that.setData({
                                brand: fxjOrderLoanList[0].elementContent,
                                plateNumber: fxjOrderLoanList[1].elementContent,
                                holder: fxjOrderLoanList[2].elementContent,
                                dateIssue: fxjOrderLoanList[3].elementContent,
                                nature: fxjOrderLoanList[4].elementContent,
                                address: fxjOrderLoanList[5].elementContent,
                                vinno: fxjOrderLoanList[6].elementContent,
                                engineNumber: fxjOrderLoanList[7].elementContent,
                            })
                    }
                    that.setData({
                        ocrName: data.cusName,
                        ocrSex: data.cusGender, 
                        ocrNation: data.cusEthnical,
                        ocrBirth: data.cusBirthday, 
                        ocrAddress: data.cusResAddr, 
                        ocrIdNumber: data.cusIncard, 
                        ocrOffice: data.createSysorg, 
                        ocrValidityPeriod: ocrValidityPeriod,
                        residentialAddress: data.cusRemark,
                        telephone: data.cusMobile,
                        checkedValue: data.cusMaritalStatus,
                        orderEmerList: data.fxjOrderEmerList,
                        village: data.orderCarno,
                        acreage: data.orderCartype,
                        money: data.orderAmount,
                        howLong: data.orderPeriod,
                        proIsdb: data.proIsdb,
                        guaranteeName:data.orderBy1 ,
                        guaranteeIdCard:data.orderBy2,
                        guaranteePhone:data.orderBy3,
                        fxjProAttList: data.fxjProAttList,
                        orderBy14: data.orderBy14,
                        orderBy15: data.orderBy15,
                        orderBy16: data.orderBy16,
                        orderBy17: data.orderBy17,
                        
                        orderBy4: data.orderBy4,
                        orderBy5: data.orderBy5,
                        orderBy6: data.orderBy6,
                        orderBy7: data.orderBy7,
                        orderBy8: data.orderBy8,
                        orderBy9: data.orderBy9,
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
    },
    previewImage:function(e){
        var listIdx = e.currentTarget.dataset.listIdx;
        var imgIdx = e.currentTarget.dataset.imgIdx;
        var imgArr = this.data.fxjProAttList[listIdx].fxjOrderAttaList;
        var all = [];
        for (var index in imgArr) {
            all = all.concat(imgArr[index].attaUrl);
        }
        console.log(all)
        wx.previewImage({
            current: all[imgIdx], // 当前显示图片的http链接
            urls: all // 需要预览的图片http链接列表
        })
    },
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