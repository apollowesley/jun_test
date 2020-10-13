var app = getApp();
const pathUrl = app.globalData.pathUrl;
Page({
    data: {
        pathUrl: pathUrl,
        openId: '',
        proNo: '',
        orderNo: '',
        proIsdb: '',//是否有担保人1是
        proAttList: [],//上传附件列表
        flag: true,
        array: ['12', '18', '24', '36', '48'],
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
        orderCarno: '', //小区
        orderCartype: '', //面积
        orderAmount: '', //金额
        pickerIndex: '3',//选择期数index
        orderPeriod: '36',//期数
        residentialAddress: '', //居住地址
        telephone: '', //联系电话
        checkedValue: '', //婚姻情况
        spouseName: '', //配偶姓名
        spouseTelephone: '', //联系电话
        isSubmit: true, //防止重复提交
        guaranteeName: '',//担保人姓名
        guaranteeIdCard: '',//担保人身份照
        guaranteePhone: '',//担保人电话
    },
    onLoad: function (e) {
        var openId = wx.getStorageSync('openId');
        var proNo = e.proNo;
        var proName = e.proName;
        this.setData({
            proNo: proNo,
            openId: openId
        })
        wx.setNavigationBarTitle({
            title: proName
        })
        this.getno();
        this.getProAttList();
    },
    //ocr名字赋值
    ocrNameInput: function (e) {
        this.setData({
            ocrName: e.detail.value
        })
    },
    //ocr性别赋值
    ocrSexInput: function (e) {
        this.setData({
            ocrSex: e.detail.value
        })
    },
    //ocr民族赋值
    ocrNationInput: function (e) {
        this.setData({
            ocrNation: e.detail.value
        })
    },
    //ocr出生赋值
    ocrBirthInput: function (e) {
        this.setData({
            ocrBirth: e.detail.value
        })
    },
    //ocr地址赋值
    ocrAddressInput: function (e) {
        this.setData({
            ocrAddress: e.detail.value
        })
    },
    //ocr身份证号
    ocrIdNumberInput: function (e) {
        this.setData({
            ocrIdNumber: e.detail.value
        })
    },
    //ocr发证机关
    ocrOfficeInput: function (e) {
        this.setData({
            ocrOffice: e.detail.value
        })
    },
    //ocr有效期限
    ocrValidityPeriodInput: function (e) {
        this.setData({
            ocrValidityPeriod: e.detail.value
        })
    },
    //小区
    orderCarnoInput: function (e) {
        this.setData({
            orderCarno: e.detail.value
        })
    },
    //面积
    orderCartypeInput: function (e) {
        this.setData({
            orderCartype: e.detail.value
        })
    },
    //点选金额
    chooesAmount: function (e) {
        console.log(e);
        var chooesamount = e.currentTarget.dataset.amount;
        this.setData({
            orderAmount: chooesamount
        })
    },
    //金额
    orderAmountInput: function (e) {
        this.setData({
            orderAmount: e.detail.value
        })
    },
    //选择期限
    bindRelationship: function (e) {
        var index = e.detail.value;
        var orderPeriod = this.data.array[index];
        this.setData({
            pickerIndex: index,
            orderPeriod: orderPeriod
        })
    },
    //居住地址
    residentialAddressInput: function (e) {
        this.setData({
            residentialAddress: e.detail.value
        })
    },
    //联系电话
    telephoneInput: function (e) {
        this.setData({
            telephone: e.detail.value
        })
    },
    //婚姻状况
    radioChange: function (e) {
        this.setData({
            checkedValue: e.detail.value
        })
        console.log(this.data.checkedValue)
    },
    //配偶姓名
    spouseNameInput: function (e) {
        this.setData({
            spouseName: e.detail.value
        })
    },
    //联系电话
    spouseTelephoneInput: function (e) {
        this.setData({
            spouseTelephone: e.detail.value
        })
    },
    //担保人姓名
    guaranteeNameInput: function (e) {
        this.setData({
            guaranteeName: e.detail.value
        })
    },
    //担保人身份证号
    guaranteeIdCardInput: function (e) {
        this.setData({
            guaranteeIdCard: e.detail.value
        })
    },
    //担保人电话
    guaranteePhoneInput: function (e) {
        this.setData({
            guaranteePhone: e.detail.value
        })
    },
    //获取orderNo
    getno: function () {
        var that = this;
        wx.request({
            url: pathUrl + '/rest/base/getno/' + that.data.proNo,
            success: function (res) {
                if (res.data.ok || res.data.data) {
                    that.setData({
                        orderNo: res.data.data
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
    //获取上传附件列表
    getProAttList: function () {
        var that = this;
        wx.request({
            url: pathUrl + '/rest/fxjProMainController/' + that.data.proNo,
            success: function (res) {
                console.log(res.data)
                if (res.data.ok || res.data.data) {
                    var newProAttList = res.data.data.fxjProAttList;
                    for (var i = 0; i < newProAttList.length; i++) {
                        newProAttList[i].picList = [];
                    }
                    that.setData({
                        proAttList: newProAttList,
                        proIsdb: res.data.data.proIsdb
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
    //上传附件图片
    getEnclosurePic: function (e) {
        console.log(e);
        var picIndex = e.currentTarget.dataset.index;
        var attType = e.currentTarget.dataset.atttype;
        var that = this;
        wx.chooseImage({
            sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有  
            sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有  
            success: function (res) {
                // 返回选定照片的本地文件路径列表，tempFilePath可以作为img标签的src属性显示图片  
                var tempFilePaths = res.tempFilePaths;
                //启动上传等待中...  
                wx.showToast({
                    title: '正在上传...',
                    icon: 'loading',
                    mask: true,
                    duration: 10000
                })
                var uploadImgCount = 0;
                for (var i = 0, h = tempFilePaths.length; i < h; i++) {
                    wx.uploadFile({
                        url: pathUrl + '/rest/base/upload',
                        filePath: tempFilePaths[i],
                        name: 'uploadfile_ant',
                        formData: {
                            sbtype: 'NO',
                            filetype: attType,
                            orderNo: that.data.orderNo
                        },
                        header: {
                            "Content-Type": "multipart/form-data"
                        },
                        success: function (res) {
                            uploadImgCount++;
                            var data = JSON.parse(res.data);
                            var proAttListInfo = that.data.proAttList;

                            proAttListInfo[picIndex].picList.push({
                                picUrl: data[0].fileUrl,
                                picId: data[0].fileId
                            });
                            that.setData({
                                proAttList: proAttListInfo
                            });
                            if (uploadImgCount == tempFilePaths.length) {
                                wx.hideToast();
                            }
                        },
                        fail: function (res) {
                            wx.hideToast();
                            wx.showModal({
                                title: '错误提示',
                                content: '上传图片失败',
                                showCancel: false
                            })
                        }
                    });
                }
            }
        });
    },
    //删除上传附件图片
    deletePic: function (e) {
        var that = this;
        var index = e.currentTarget.dataset.index;
        var imgIdx = e.currentTarget.dataset.imgIdx;
        var deletPicId = that.data.proAttList[index].picList[imgIdx].picId;
        if (that.data.flag) {
            that.data.proAttList[index].picList.splice(imgIdx, 1);
            that.setData({
                proAttList: that.data.proAttList,
                flag: false
            })
        }
        wx.request({
            url: pathUrl + '/rest/base/delfile/' + deletPicId,
            success: function (res) {
                if (res.data.ok || res.data.data) {
                    that.setData({
                        flag: true
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
    //上传OCR图片
    upEnclosure: function (e) {
        var that = this;
        var sbtype = e.currentTarget.dataset.sbtype
        wx.chooseImage({
            count: 1, //最多可以选择的图片总数  
            sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有  
            sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有  
            success: function (res) {
                // 返回选定照片的本地文件路径列表，tempFilePath可以作为img标签的src属性显示图片  
                var tempFilePaths = res.tempFilePaths;
                //启动上传等待中...  
                wx.showToast({
                    title: '正在上传...',
                    icon: 'loading',
                    mask: true,
                    duration: 20000
                })
                var uploadImgCount = 0;
                for (var i = 0, h = tempFilePaths.length; i < h; i++) {
                    wx.uploadFile({
                        url: pathUrl + '/rest/base/uploadsb',
                        filePath: tempFilePaths[i],
                        name: 'uploadfile_ant',
                        formData: {
                            sbtype: sbtype,
                            filetype: 'sb',
                            orderNo: that.data.orderNo
                        },
                        header: {
                            "Content-Type": "multipart/form-data"
                        },
                        success: function (res) {
                            uploadImgCount++;
                            console.log(res);
                            if (sbtype == 2) {
                                var data = JSON.parse(res.data);
                                data = JSON.parse(data[0].piccontent);
                                var items = data.result.cardsinfo[0].items;
                                that.setData({
                                    ocrName: items[1].content,
                                    ocrSex: items[2].content,
                                    ocrNation: items[3].content,
                                    ocrBirth: items[4].content,
                                    ocrAddress: items[5].content,
                                    ocrIdNumber: items[6].content,
                                })
                                console.log("sbtype == 2:" + JSON.stringify(items))
                            } else if (sbtype == 3) {
                                var data = JSON.parse(res.data);
                                data = JSON.parse(data[0].piccontent);
                                var items = data.result.cardsinfo[0].items;
                                that.setData({
                                    ocrOffice: items[1].content,
                                    ocrValidityPeriod: items[2].content,
                                    cusIdValidfrom: items[3].content,
                                    cusIdValidto: items[4].content
                                })
                                console.log("sbtype == 3:" + JSON.stringify(items))

                            }
                            if (uploadImgCount == tempFilePaths.length) {
                                wx.hideToast();
                            }
                        },
                        fail: function (res) {
                            wx.hideToast();
                            wx.showModal({
                                title: '错误提示',
                                content: '上传图片失败',
                                showCancel: false
                            })
                        }
                    });
                }
            }
        });
    },
    submitBtn: function () {
        var that = this;
        if (that.data.isSubmit) {
            that.setData({
                isSubmit: false
            });
            var data = {
                bpmStatus: 10,
                createBy: that.data.openId,
                sysOrgCode: '',
                orderNo: that.data.orderNo, //订单编号
                proNo: that.data.proNo, //产品编号
                cusName: that.data.ocrName, //姓名
                cusGender: that.data.ocrSex, //性别
                cusEthnical: that.data.ocrNation, //民族
                cusBirthday: that.data.ocrBirth, //出生日期
                cusResAddr: that.data.ocrAddress, //地址
                cusrIdtype: '身份证', //身份证为固定值
                cusIncard: that.data.ocrIdNumber, // 身份证号
                createSysorg: that.data.ocrOffice, //签发机关
                cusIdValidfrom: that.data.cusIdValidfrom, //证件开始日期
                cusIdValidto: that.data.cusIdValidto, //证件结束日期
                orderCartype: that.data.orderCartype, //车型 如果是房子就是面积
                orderCarno: that.data.orderCarno, // 车牌  如果是房子就是小区名称
                orderAmount: that.data.orderAmount, // 金额
                orderPeriod: that.data.orderPeriod, // 期数
                cusRemark: that.data.residentialAddress, // 个人资料的居住地址
                cusMobile: that.data.telephone, //电话
                cusMaritalStatus: that.data.checkedValue, //婚姻状况
                cusDeu: '', //学历
                cusAge: '',
                cusQqid: '', //qq号
                orderBy1: that.data.guaranteeName,//担保人姓名
                orderBy2: that.data.guaranteeIdCard,//担保人身份证号
                orderBy3: that.data.guaranteePhone,//担保人电话
                fxjOrderEmerList: [{
                    emergencyRelation: '', //关系
                    emergencyContact: that.data.spouseName, //姓名
                    emergencyMobile: that.data.spouseTelephone, //电话
                    emergencyOffice: '',
                    emergencyResAddr: '' //地址
                }]
            };
            console.log("data:" + JSON.stringify(data));
            wx.request({
                url: pathUrl + '/rest/fxjOrderMainController/' + that.data.openId,
                data: data,
                method: 'POST',
                success: function (res) {
                    if (res.data.ok) {
                        that.setData({
                            isSubmit: true
                        });
                        wx.showToast({
                            title: '提交成功',
                            duration: 1000,
                            mask: true,
                            success: function () {
                                setTimeout(function () {
                                    wx.switchTab({
                                        url: "/pages/index/index"
                                    })
                                }, 1000);
                            }
                        })
                    } else {
                        that.setData({
                            isSubmit: true
                        });
                        wx.showToast({
                            title: '提交失败',
                            icon: 'none',
                            mask: true,
                            duration: 1000
                        })
                    }
                }
            })
        } else {
            wx.showToast({
                title: '请勿重复提交',
                icon: 'none',
                mask: true,
                duration: 1000
            })
        }
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