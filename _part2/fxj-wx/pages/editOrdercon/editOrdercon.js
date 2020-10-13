var app = getApp();
var dateTimePicker = require('../../utils/dateTimePicker.js');
const pathUrl = app.globalData.pathUrl;
Page({
    data: {
      date: '2018-10-01',
      time: '12:00',
      dateTimeArray: null,
      dateTime: null,
      gpsInsDataArray1: null,
      dateTime1: null,
      startYear: 2000,
      endYear: 2050,

        pathUrl: pathUrl,
        openId: '',
        orderNo: '',
        proNo: '',
        id: '',
        proIsdb: '', //是否有担保人
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
        items: [{
                value: '已婚',
                checked: ''
            },
            {
                value: '未婚',
                checked: ''
            }
        ],
        checkedValue: '', //婚姻情况
        spouseName: '', //配偶姓名
        spouseTelephone: '', //联系电话
        urgent: '', //紧急联系人
        urgentTelephone: '', //紧急联系人联系电话
        urgent2: '', //紧急联系人
        urgentTelephone2: '', //紧急联系人联系电话
        orderEmerList: [], //紧急联系人列表
        village: '', //小区
        acreage: '', //面积
        money: '', //金额
        array: ['12', '18', '24', '36', '48'],
        pickerIndex: '', //选择期数index
        orderPeriod: '', //期数
        howLong: '', //借多久
        guaranteeName: '', //担保人姓名
        guaranteeIdCard: '', //担保人身份照
        guaranteePhone: '', //担保人电话
        fxjProAttList: [],
        flag: true, //删除图片防止重复提交
        isSubmit: true, //防止重复提交
      orderBy4:'', //担保人姓名
      orderBy5: '', //担保人身份证号
      orderBy6: '', //担保人电话
      orderBy7: '', //担保人姓名
      orderBy8:'', //担保人身份证号
      conUrl: '', 
      gpsInsData: '', 
      gpsInsAddr: '', 
      gpsInsUser: '', 
      gpsInsMobile: '', 
      fangkBank: '', 
      fangkName: '', 
      fangkId: '', 
    },
    onLoad: function(e) {
      // 获取完整的年月日 时分秒，以及默认显示的数组
      var obj = dateTimePicker.dateTimePicker(this.data.startYear, this.data.endYear);
      var obj1 = dateTimePicker.dateTimePicker(this.data.startYear, this.data.endYear);
      // 精确到分的处理，将数组的秒去掉
      var lastArray = obj1.dateTimeArray.pop();
      var lastTime = obj1.dateTime.pop();

      this.setData({
        dateTime: obj.dateTime,
        dateTimeArray: obj.dateTimeArray,
        gpsInsDataArray1: obj1.dateTimeArray,
        gpsInsData: obj1.dateTime
      });

        console.log(e);
        var openId = wx.getStorageSync('openId');
        this.setData({
            orderNo: e.orderNo,
            proNo: e.proNo,
            id: e.id,
            openId: openId
        })
        this.orderMainController();
    },

  changeDate(e) {
    this.setData({ date: e.detail.value });
  },
  changeTime(e) {
    this.setData({ time: e.detail.value });
  },
  changeDateTime(e) {
    this.setData({ dateTime: e.detail.value });
  },
  changeDateTime1(e) {
    this.setData({ gpsInsData: e.detail.value });
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
    var arr = this.data.gpsInsData, dateArr = this.data.gpsInsDataArray1;

    arr[e.detail.column] = e.detail.value;
    dateArr[2] = dateTimePicker.getMonthDay(dateArr[0][arr[0]], dateArr[1][arr[1]]);

    this.setData({
      gpsInsDataArray1: dateArr
    });
  },

    //ocr名字赋值
    ocrNameInput: function(e) {
        this.setData({
            ocrName: e.detail.value
        })
    },
    //ocr性别赋值
    ocrSexInput: function(e) {
        this.setData({
            ocrSex: e.detail.value
        })
    },
    //ocr民族赋值
    ocrNationInput: function(e) {
        this.setData({
            ocrNation: e.detail.value
        })
    },
    //ocr出生赋值
    ocrBirthInput: function(e) {
        this.setData({
            ocrBirth: e.detail.value
        })
    },
    //ocr地址赋值
    ocrAddressInput: function(e) {
        this.setData({
            ocrAddress: e.detail.value
        })
    },
    //ocr身份证号
    ocrIdNumberInput: function(e) {
        this.setData({
            ocrIdNumber: e.detail.value
        })
    },
    //ocr发证机关
    ocrOfficeInput: function(e) {
        this.setData({
            ocrOffice: e.detail.value
        })
    },
    //ocr有效期限
    ocrValidityPeriodInput: function(e) {
        this.setData({
            ocrValidityPeriod: e.detail.value
        })
    },
    //品牌/车型
    brandInput: function(e) {
        this.setData({
            brand: e.detail.value
        })
    },
    //车牌号
    plateNumberInput: function(e) {
        this.setData({
            plateNumber: e.detail.value
        })
    },
    //所有人
    holderInput: function(e) {
        this.setData({
            holder: e.detail.value
        })
    },
    //注册/发证日期
    dateIssueInput: function(e) {
        this.setData({
            dateIssue: e.detail.value
        })
    },
    //使用性质
    natureInput: function(e) {
        this.setData({
            nature: e.detail.value
        })
    },
    //住址
    addressInput: function(e) {
        this.setData({
            address: e.detail.value
        })
    },
    //车架号
    vinnoInput: function(e) {
        this.setData({
            vinno: e.detail.value
        })
    },
    //发动机号
    engineNumberInput: function(e) {
        this.setData({
            engineNumber: e.detail.value
        })
    },
    //居住地址
    residentialAddressInput: function(e) {
        this.setData({
            residentialAddress: e.detail.value
        })
    },
    //联系电话
    telephoneInput: function(e) {
        this.setData({
            telephone: e.detail.value
        })
    },
    //婚姻状况
    radioChange: function(e) {
        this.setData({
            checkedValue: e.detail.value
        })
    },
    //配偶姓名
    spouseNameInput: function(e) {
        this.setData({
            spouseName: e.detail.value
        })
    },
    //联系电话
    spouseTelephoneInput: function(e) {
        this.setData({
            spouseTelephone: e.detail.value
        })
    },
    //紧急联系人
    urgentInput: function(e) {
        this.setData({
            urgent: e.detail.value
        })
    },
    //紧急联系人联系电话
    urgentTelephoneInput: function(e) {
        this.setData({
            urgentTelephone: e.detail.value
        })
    },
    //紧急联系人2
    urgent2Input: function(e) {
        this.setData({
            urgent2: e.detail.value
        })
    },
    //紧急联系人联系电话2
    urgentTelephone2Input: function(e) {
        this.setData({
            urgentTelephone2: e.detail.value
        })
    },
    //担保人姓名
    guaranteeNameInput: function(e) {
        this.setData({
            guaranteeName: e.detail.value
        })
    },
    //担保人身份证号
    guaranteeIdCardInput: function(e) {
        this.setData({
            guaranteeIdCard: e.detail.value
        })
    },
    //担保人电话
    guaranteePhoneInput: function(e) {
        this.setData({
            guaranteePhone: e.detail.value
        })
    },
    //小区
    villageInput: function(e) {
        this.setData({
            village: e.detail.value
        })
    },
    //面积
    acreageInput: function(e) {
        this.setData({
            acreage: e.detail.value
        })
    },
    //点选金额
    chooesAmount: function(e) {
        var chooesamount = e.currentTarget.dataset.amount;
        this.setData({
            money: chooesamount
        })
    },
    //金额
    moneyInput: function(e) {
        this.setData({
            money: e.detail.value
        })
    },
    //选择期限
    bindRelationship: function(e) {
        var index = e.detail.value;
        var orderPeriod = this.data.array[index];
        this.setData({
            pickerIndex: index,
            orderPeriod: orderPeriod
        })
    },
  orderBy6Input: function (e) {
    this.setData({
      orderBy6: e.detail.value
    })
  },
  //
  orderBy7Input: function (e) {
    this.setData({
      orderBy7: e.detail.value
    })
  },  //
  orderBy8Input: function (e) {
    this.setData({
      orderBy8: e.detail.value
    })
  },  //
  orderBy4Input: function (e) {
    this.setData({
      orderBy4: e.detail.value
    })
  },  //
  orderBy5Input: function (e) {
    this.setData({
      orderBy5: e.detail.value
    })
  },
    //上传附件图片
    getEnclosurePic: function(e) {
        var picIndex = e.currentTarget.dataset.index;
        var attType = e.currentTarget.dataset.atttype;
        var that = this;
        wx.chooseImage({
            sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有  
            sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有  
            success: function(res) {
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
                        success: function(res) {
                            uploadImgCount++;
                            var data = JSON.parse(res.data);
                            var proAttListInfo = that.data.fxjProAttList;
                            proAttListInfo[picIndex].fxjOrderAttaList.push({
                                attaUrl: data[0].fileUrl,
                                id: data[0].fileId
                            });
                            that.setData({
                                fxjProAttList: proAttListInfo
                            });

                            if (uploadImgCount == tempFilePaths.length) {
                                wx.hideToast();
                            }
                        },
                        fail: function(res) {
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
    deletePic: function(e) {
        var that = this;
        var index = e.currentTarget.dataset.index;
        var imgIdx = e.currentTarget.dataset.imgIdx;
        var deletPicId = that.data.fxjProAttList[index].fxjOrderAttaList[imgIdx].id;
        if (that.data.flag) {
            that.data.fxjProAttList[index].fxjOrderAttaList.splice(imgIdx, 1);
            that.setData({
                fxjProAttList: that.data.fxjProAttList,
                flag: false
            })
        }
        wx.request({
            url: pathUrl + '/rest/base/delfile/' + deletPicId,
            success: function(res) {
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
    upEnclosure: function(e) {
        var that = this;
        var sbtype = e.currentTarget.dataset.sbtype
        wx.chooseImage({
            count: 1, //最多可以选择的图片总数  
            sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有  
            sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有  
            success: function(res) {
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
                        success: function(res) {
                            uploadImgCount++;
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
                            } else if (sbtype == 6) {
                                var data = JSON.parse(res.data);
                                data = JSON.parse(data[0].piccontent);
                                var items = data.result.cardsinfo[0].items;
                                that.setData({
                                    brand: items[5].content,
                                    plateNumber: items[1].content,
                                    holder: items[3].content,
                                    dateIssue: items[8].content,
                                    nature: items[10].content,
                                    address: items[4].content,
                                    vinno: items[6].content,
                                    engineNumber: items[7].content,
                                })
                            }
                            if (uploadImgCount == tempFilePaths.length) {
                                wx.hideToast();
                            }
                        },
                        fail: function(res) {
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
    orderMainController: function() {
        var that = this;
        wx.request({
            url: pathUrl + '/rest/fxjOrderMainController/' + that.data.orderNo,
            data: {

            },
            success: function(res) {
                console.log(res.data)
                if (res.data.ok) {
                    var data = res.data.data;
                    var fxjOrderLoanList = res.data.data.fxjOrderLoanList;
                    var ocrValidityPeriod = res.data.data.cusIdValidfrom + ' - ' + res.data.data.cusIdValidto;
                    if (fxjOrderLoanList && fxjOrderLoanList.length > 0) {
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
                      conUrl: data.conUrl,
                      // gpsInsData: data.gpsInsData,
                      gpsInsAddr: data.gpsInsAddr,
                      gpsInsUser: data.gpsInsUser,
                      gpsInsMobile: data.gpsInsMobile,
                      fangkBank: data.fangkBank,
                      fangkName: data.fangkName,
                      fangkId: data.fangkId,
                        ocrName: data.cusName,
                        ocrSex: data.cusGender,
                        ocrNation: data.cusEthnical,
                        ocrBirth: data.cusBirthday,
                        ocrAddress: data.cusResAddr,
                        ocrIdNumber: data.cusIncard,
                        ocrOffice: data.createSysorg,
                        cusIdValidfrom: data.cusIdValidfrom,
                        cusIdValidto: data.cusIdValidto,
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
                        guaranteeName: data.orderBy1,
                        guaranteeIdCard: data.orderBy2,
                        guaranteePhone: data.orderBy3,
                      orderBy4: data.orderBy4,
                      orderBy5: data.orderBy5,
                      orderBy6: data.orderBy6,
                      orderBy7: data.orderBy7,
                      orderBy8: data.orderBy8,
                        fxjProAttList: data.fxjProAttList
                    })
                    if (data.cusMaritalStatus == '已婚') {
                        that.setData({
                            ['items[0].checked']: true
                        })
                    } else if (data.cusMaritalStatus == '未婚') {
                        that.setData({
                            ['items[1].checked']: true
                        })
                    }
                    if (that.data.howLong == '12') {
                        that.setData({
                            pickerIndex: 0,
                          orderPeriod:12
                        })
                    } else if (that.data.howLong == '18') {
                        that.setData({
                            pickerIndex: 1,
                          orderPeriod: 18
                        })
                    } else if (that.data.howLong == '24') {
                        that.setData({
                            pickerIndex: 2,
                          orderPeriod: 24
                        })
                    } else if (that.data.howLong == '36') {
                        that.setData({
                            pickerIndex: 3,
                          orderPeriod: 36
                        })
                    } else if (that.data.howLong == '48') {
                        that.setData({
                            pickerIndex: 4,
                          orderPeriod: 48
                        })
                    }
                    if (that.data.proNo == 10) {
                        that.setData({
                            spouseName: that.data.orderEmerList[0].emergencyContact,
                            spouseTelephone: that.data.orderEmerList[0].emergencyMobile,
                            urgent: that.data.orderEmerList[1].emergencyContact,
                            urgentTelephone: that.data.orderEmerList[1].emergencyMobile,
                            urgent2: that.data.orderEmerList[2].emergencyContact,
                            urgentTelephone2: that.data.orderEmerList[2].emergencyMobile
                        })
                    } else {
                        that.setData({
                            spouseName: that.data.orderEmerList[0].emergencyContact,
                            spouseTelephone: that.data.orderEmerList[0].emergencyMobile
                        })
                    }
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
  //保存
  saveBtn: function (e) {
    var no = e.currentTarget.dataset.prono;
    var that = this;
    if (no == 10) {
      var data = {
        conUrl: that.data.conUrl,
        gpsInsData: that.data.gpsInsData,
        gpsInsAddr: that.data.gpsInsAddr,
        gpsInsUser: that.data.gpsInsUser,
        gpsInsMobile: that.data.gpsInsMobile,
        fangkBank: that.data.fangkBank,
        fangkName: that.data.fangkName,
        fangkId: that.data.fangkId,
        orderOper: 'S',
        id: that.data.id,
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
        orderCartype: that.data.brand, //车型 如果是房子就是面积
        orderCarno: that.data.plateNumber, // 车牌  如果是房子就是小区名称
        cusRemark: that.data.residentialAddress, // 个人资料的居住地址
        cusMobile: that.data.telephone, //电话
        cusMaritalStatus: that.data.checkedValue, //婚姻状况
        cusDeu: '', //学历
        cusAge: '',
        cusQqid: '', //qq号
        orderBy1: that.data.guaranteeName, //担保人姓名
        orderBy2: that.data.guaranteeIdCard, //担保人身份证号
        orderBy3: that.data.guaranteePhone, //担保人电话
        orderBy4: that.data.orderBy4, //担保人姓名
        orderBy5: that.data.orderBy5, //担保人身份证号
        orderBy6: that.data.orderBy6, //担保人电话
        orderBy7: that.data.orderBy7, //担保人姓名
        orderBy8: that.data.orderBy8, //担保人身份证号
        fxjOrderEmerList: [{
          emergencyRelation: '', //关系
          emergencyContact: that.data.spouseName, //姓名
          emergencyMobile: that.data.spouseTelephone, //电话
          emergencyOffice: '',
          emergencyResAddr: '' //地址
        }, {
          emergencyRelation: '', //关系
          emergencyContact: that.data.urgent, //姓名
          emergencyMobile: that.data.urgentTelephone, //电话
          emergencyOffice: '',
          emergencyResAddr: '' //地址
        }, {
          emergencyRelation: '', //关系
          emergencyContact: that.data.urgent2, //姓名
          emergencyMobile: that.data.urgentTelephone2, //电话
          emergencyOffice: '',
          emergencyResAddr: '' //地址
        }],
        fxjOrderLoanList: [{
          elementNo: 10,
          elementContent: that.data.brand
        }, {
          elementNo: 20,
          elementContent: that.data.plateNumber
        }, {
          elementNo: 30,
          elementContent: that.data.holder
        }, {
          elementNo: 40,
          elementContent: that.data.dateIssue
        }, {
          elementNo: 50,
          elementContent: that.data.nature
        }, {
          elementNo: 60,
          elementContent: that.data.address
        }, {
          elementNo: 70,
          elementContent: that.data.vinno
        }, {
          elementNo: 80,
          elementContent: that.data.engineNumber
        }]
      };
      console.log("data:" + JSON.stringify(data));
    } else if (no == 20) {
      var data = {
        orderOper: 'S',
        id: that.data.id,
        conUrl: that.data.conUrl,
        gpsInsData: that.data.gpsInsData,
        gpsInsAddr: that.data.gpsInsAddr,
        gpsInsUser: that.data.gpsInsUser,
        gpsInsMobile: that.data.gpsInsMobile,
        fangkBank: that.data.fangkBank,
        fangkName: that.data.fangkName,
        fangkId: that.data.fangkId,
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
        orderCartype: that.data.brand, //车型 如果是房子就是面积
        orderCarno: that.data.plateNumber, // 车牌  如果是房子就是小区名称
        cusRemark: that.data.residentialAddress, // 个人资料的居住地址
        cusMobile: that.data.telephone, //电话
        cusMaritalStatus: that.data.checkedValue, //婚姻状况
        cusDeu: '', //学历
        cusAge: '',
        cusQqid: '', //qq号
        orderAmount: that.data.money, // 金额
        orderPeriod: that.data.orderPeriod, // 期数
        orderBy1: that.data.guaranteeName,//担保人姓名
        orderBy2: that.data.guaranteeIdCard,//担保人身份证号
        orderBy3: that.data.guaranteePhone,//担保人电话
        orderBy4: that.data.orderBy4, //担保人姓名
        orderBy5: that.data.orderBy5, //担保人身份证号
        orderBy6: that.data.orderBy6, //担保人电话
        orderBy7: that.data.orderBy7, //担保人姓名
        orderBy8: that.data.orderBy8, //担保人身份证号
        fxjOrderEmerList: [{
          emergencyRelation: '', //关系
          emergencyContact: that.data.spouseName, //姓名
          emergencyMobile: that.data.spouseTelephone, //电话
          emergencyOffice: '',
          emergencyResAddr: '' //地址
        }],
        fxjOrderLoanList: [
          {
            elementNo: 10,
            elementContent: that.data.brand
          }, {
            elementNo: 20,
            elementContent: that.data.plateNumber
          }, {
            elementNo: 30,
            elementContent: that.data.holder
          }, {
            elementNo: 40,
            elementContent: that.data.dateIssue
          }, {
            elementNo: 50,
            elementContent: that.data.nature
          }, {
            elementNo: 60,
            elementContent: that.data.address
          }, {
            elementNo: 70,
            elementContent: that.data.vinno
          }, {
            elementNo: 80,
            elementContent: that.data.engineNumber
          }
        ]
      };
      console.log("data:" + JSON.stringify(data));
    } else if (no == 30 || no == 40) {
      var data = {
        orderOper: 'S',
        id: that.data.id,
        conUrl: that.data.conUrl,
        gpsInsData: that.data.gpsInsData,
        gpsInsAddr: that.data.gpsInsAddr,
        gpsInsUser: that.data.gpsInsUser,
        gpsInsMobile: that.data.gpsInsMobile,
        fangkBank: that.data.fangkBank,
        fangkName: that.data.fangkName,
        fangkId: that.data.fangkId,
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
        orderCartype: that.data.acreage, //房子面积
        orderCarno: that.data.village, // 小区名称
        orderAmount: that.data.money, // 金额
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
        orderBy4: that.data.orderBy4, //担保人姓名
        orderBy5: that.data.orderBy5, //担保人身份证号
        orderBy6: that.data.orderBy6, //担保人电话
        orderBy7: that.data.orderBy7, //担保人姓名
        orderBy8: that.data.orderBy8, //担保人身份证号
        fxjOrderEmerList: [{
          emergencyRelation: '', //关系
          emergencyContact: that.data.spouseName, //姓名
          emergencyMobile: that.data.spouseTelephone, //电话
          emergencyOffice: '',
          emergencyResAddr: '' //地址
        }]
      };
      console.log("data:" + JSON.stringify(data));
    }
    if (that.data.isSubmit) {
      that.setData({
        isSubmit: false
      });
      wx.request({

        url: pathUrl + '/rest/fxjOrderMainController/' + that.data.id,
        data: data,
        method: 'PUT',
        success: function (res) {
          console.log(res.data)
          if (res.data.ok) {
            that.setData({
              isSubmit: true
            });
            wx.showToast({
              title: '保存成功',
              duration: 500,
              mask: true,
              success: function () {
                setTimeout(function () {
                  wx.navigateBack({
                    delta: 1
                  })
                }, 500);
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
  //提交
  submitBtn: function (e) {
    var no = e.currentTarget.dataset.prono;
    var that = this;
    if (no == 10) {
      var data = {
        orderOper: 'Y',
        id: that.data.id,
        conUrl: that.data.conUrl,
        gpsInsData: that.data.gpsInsData,
        gpsInsAddr: that.data.gpsInsAddr,
        gpsInsUser: that.data.gpsInsUser,
        gpsInsMobile: that.data.gpsInsMobile,
        fangkBank: that.data.fangkBank,
        fangkName: that.data.fangkName,
        fangkId: that.data.fangkId,
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
        orderCartype: that.data.brand, //车型 如果是房子就是面积
        orderCarno: that.data.plateNumber, // 车牌  如果是房子就是小区名称
        cusRemark: that.data.residentialAddress, // 个人资料的居住地址
        cusMobile: that.data.telephone, //电话
        cusMaritalStatus: that.data.checkedValue, //婚姻状况
        cusDeu: '', //学历
        cusAge: '',
        cusQqid: '', //qq号
        orderAmount: that.data.money, // 金额
        orderPeriod: that.data.orderPeriod, // 期数
        orderBy1: that.data.guaranteeName, //担保人姓名
        orderBy2: that.data.guaranteeIdCard, //担保人身份证号
        orderBy3: that.data.guaranteePhone, //担保人电话
        orderBy4: that.data.orderBy4, //担保人姓名
        orderBy5: that.data.orderBy5, //担保人身份证号
        orderBy6: that.data.orderBy6, //担保人电话
        orderBy7: that.data.orderBy7, //担保人姓名
        orderBy8: that.data.orderBy8, //担保人身份证号
        fxjOrderEmerList: [{
          emergencyRelation: '', //关系
          emergencyContact: that.data.spouseName, //姓名
          emergencyMobile: that.data.spouseTelephone, //电话
          emergencyOffice: '',
          emergencyResAddr: '' //地址
        }, {
          emergencyRelation: '', //关系
          emergencyContact: that.data.urgent, //姓名
          emergencyMobile: that.data.urgentTelephone, //电话
          emergencyOffice: '',
          emergencyResAddr: '' //地址
        }, {
          emergencyRelation: '', //关系
          emergencyContact: that.data.urgent2, //姓名
          emergencyMobile: that.data.urgentTelephone2, //电话
          emergencyOffice: '',
          emergencyResAddr: '' //地址
        }],
        fxjOrderLoanList: [{
          elementNo: 10,
          elementContent: that.data.brand
        }, {
          elementNo: 20,
          elementContent: that.data.plateNumber
        }, {
          elementNo: 30,
          elementContent: that.data.holder
        }, {
          elementNo: 40,
          elementContent: that.data.dateIssue
        }, {
          elementNo: 50,
          elementContent: that.data.nature
        }, {
          elementNo: 60,
          elementContent: that.data.address
        }, {
          elementNo: 70,
          elementContent: that.data.vinno
        }, {
          elementNo: 80,
          elementContent: that.data.engineNumber
        }]
      };
      console.log("data:" + JSON.stringify(data));
    } else if (no == 20) {
      var data = {
        orderOper: 'Y',
        id: that.data.id,
        conUrl: that.data.conUrl,
        gpsInsData: that.data.gpsInsData,
        gpsInsAddr: that.data.gpsInsAddr,
        gpsInsUser: that.data.gpsInsUser,
        gpsInsMobile: that.data.gpsInsMobile,
        fangkBank: that.data.fangkBank,
        fangkName: that.data.fangkName,
        fangkId: that.data.fangkId,
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
        orderCartype: that.data.brand, //车型 如果是房子就是面积
        orderCarno: that.data.plateNumber, // 车牌  如果是房子就是小区名称
        cusRemark: that.data.residentialAddress, // 个人资料的居住地址
        cusMobile: that.data.telephone, //电话
        cusMaritalStatus: that.data.checkedValue, //婚姻状况
        cusDeu: '', //学历
        cusAge: '',
        cusQqid: '', //qq号
        orderAmount: that.data.money, // 金额
        orderPeriod: that.data.orderPeriod, // 期数
        orderBy1: that.data.guaranteeName,//担保人姓名
        orderBy2: that.data.guaranteeIdCard,//担保人身份证号
        orderBy3: that.data.guaranteePhone,//担保人电话
        orderBy4: that.data.orderBy4, //担保人姓名
        orderBy5: that.data.orderBy5, //担保人身份证号
        orderBy6: that.data.orderBy6, //担保人电话
        orderBy7: that.data.orderBy7, //担保人姓名
        orderBy8: that.data.orderBy8, //担保人身份证号
        fxjOrderEmerList: [{
          emergencyRelation: '', //关系
          emergencyContact: that.data.spouseName, //姓名
          emergencyMobile: that.data.spouseTelephone, //电话
          emergencyOffice: '',
          emergencyResAddr: '' //地址
        }],
        fxjOrderLoanList: [
          {
            elementNo: 10,
            elementContent: that.data.brand
          }, {
            elementNo: 20,
            elementContent: that.data.plateNumber
          }, {
            elementNo: 30,
            elementContent: that.data.holder
          }, {
            elementNo: 40,
            elementContent: that.data.dateIssue
          }, {
            elementNo: 50,
            elementContent: that.data.nature
          }, {
            elementNo: 60,
            elementContent: that.data.address
          }, {
            elementNo: 70,
            elementContent: that.data.vinno
          }, {
            elementNo: 80,
            elementContent: that.data.engineNumber
          }
        ]
      };
      console.log("data:" + JSON.stringify(data));
    } else if (no == 30 || no == 40) {
      var data = {
        orderOper: 'Y',
        id: that.data.id,
        conUrl: that.data.conUrl,
        gpsInsData: that.data.gpsInsData,
        gpsInsAddr: that.data.gpsInsAddr,
        gpsInsUser: that.data.gpsInsUser,
        gpsInsMobile: that.data.gpsInsMobile,
        fangkBank: that.data.fangkBank,
        fangkName: that.data.fangkName,
        fangkId: that.data.fangkId,
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
        orderCartype: that.data.acreage, //房子面积
        orderCarno: that.data.village, // 小区名称
        orderAmount: that.data.money, // 金额
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
        orderBy4: that.data.orderBy4, //担保人姓名
        orderBy5: that.data.orderBy5, //担保人身份证号
        orderBy6: that.data.orderBy6, //担保人电话
        orderBy7: that.data.orderBy7, //担保人姓名
        orderBy8: that.data.orderBy8, //担保人身份证号
        fxjOrderEmerList: [{
          emergencyRelation: '', //关系
          emergencyContact: that.data.spouseName, //姓名
          emergencyMobile: that.data.spouseTelephone, //电话
          emergencyOffice: '',
          emergencyResAddr: '' //地址
        }]
      };
      console.log("data:" + JSON.stringify(data));
    }
    if (that.data.isSubmit) {
      that.setData({
        isSubmit: false
      });
      wx.request({
        url: pathUrl + '/rest/fxjOrderMainController/' + that.data.id,
        data: data,
        method: 'PUT',
        success: function (res) {
          console.log(res.data)
          if (res.data.ok) {
            that.setData({
              isSubmit: true
            });
            wx.showToast({
              title: '提交成功',
              duration: 500,
              mask: true,
              success: function () {
                setTimeout(function () {
                  wx.navigateBack({
                    delta: 1
                  })
                }, 500);
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
    onReady: function() {

    },

    /**
     * 生命周期函数--监听页面显示
     */
    onShow: function() {

    },

    /**
     * 生命周期函数--监听页面隐藏
     */
    onHide: function() {

    },

    /**
     * 生命周期函数--监听页面卸载
     */
    onUnload: function() {

    },

    /**
     * 页面相关事件处理函数--监听用户下拉动作
     */
    onPullDownRefresh: function() {

    },

    /**
     * 页面上拉触底事件的处理函数
     */
    onReachBottom: function() {

    },

    /**
     * 用户点击右上角分享
     */
    onShareAppMessage: function() {

    }
})