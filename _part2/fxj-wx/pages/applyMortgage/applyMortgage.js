var app = getApp();
const pathUrl = app.globalData.pathUrl;
Page({
  data: {
    pathUrl: pathUrl,
    openId: '',
    proNo: '',
    orderNo: '',
    proIsdb: '', //是否有担保人1是
    proAttList: [], //上传附件列表
    array: ['6', '12', '24', '36'],
    // array: ['36个月'],
    flag: true,
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
    zone: '2',
    mile: '',
    holder1: '', //驾驶证所有人
    dateIssue1: '', //驾驶证注册/发证日期
    nature1: '', //驾驶证使用性质
    address1: '', //驾驶证住址
    engineNumber: '', //发动机号
    residentialAddress: '', //居住地址
    telephone: '', //联系电话
    checkedValue: '', //婚姻情况
    spouseName: '', //配偶姓名
    spouseNumber: '',//配偶证件号
    spouseCompanyName: '',//配偶公司
    spouseCompanyAddress: '',//配偶公司地址
    spouseMonthlyIncome: '',//配偶收入
    spouseChildren: '',//配偶供养人数
    spouseTelephone: '', //联系电话
    urgent: '', //紧急联系人
    urgentTelephone: '', //紧急联系人联系电话
    urgent2: '', //紧急联系人
    urgentTelephone2: '', //紧急联系人联系电话
    isSubmit: true, //防止重复提交
    guaranteeName: '', //担保人姓名
    guaranteeIdCard: '', //担保人身份照
    guaranteePhone: '', //担保人电话
    orderAmount: '', //金额
    pickerIndex: '3',//选择期数index
    orderPeriod: '36',//期数
    orderBy6: '',
    orderBy7: '',
    orderBy8: '',
    orderBy4: '',
    orderBy5: '',
    orderBy9: '', //储蓄卡
    orderBy10: '',//开户行
    orderBy11: '',//银行预留电话
    orderBy14: '', //押金或利率
    orderBy15: '', //GPS费或月前租金
    orderBy16: '', //服务费或月租金
    orderBy17: '', //其他
    orderBy18: '', //月供
    orderBy19: '', //首付金额
    orderBy20: '', //车型
    orderBy21: '', //指导价
    orderBy37: '', //颜色
    orderBy23: '', //配置
    orderBy24: '',
    orderBy25: '',
    orderBy26: '',
    orderBy27: '',
    orderBy28: '',
    orderBy30: '', //服务密码
    orderBy34: '',
    orderBy48: '',
  },

  onLoad: function (e) {
    var openId = wx.getStorageSync('openId');
    var proNo = e.proNo;
    var proName = e.proName;
    console.log(e)
    this.setData({
      proNo: proNo,
      openId: openId,
    })
    wx.setNavigationBarTitle({
      title: proName
    })

    console.log("proNo:" + proNo)
    this.getno();
    this.getProAttList();
  },
  //ocr名字赋值
  ocrNameInput: function (e) {
    this.setData({
      ocrName: e.detail.value
    })
  },
  // 增加共借人，担保人
  goGuarantee: function (e) {
    console.log(e)
    var that = this;
    wx.navigateTo({
      url: '/pages/guarantee/guarantee?proNo=' + that.data.proNo + '&proName=' + e.currentTarget.dataset.tit + '&orderNo=' + e.currentTarget.dataset.orderno,
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
  //地区
  zoneInput: function (e) {
    this.setData({
      zone: e.detail.value
    })
  },
  //公里数
  mileInput: function (e) {
    this.setData({
      mile: e.detail.value
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
  //车身颜色
  orderBy37Input: function (e) {
    this.setData({
      orderBy37: e.detail.value
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
  //车身价
  orderAmountInput: function (e) {
    // console.log(this.data.proFinanceSum)
    this.setData({
      // orderAmount: this.data.proFinanceSum
      orderAmount: e.detail.value
    })
  },


  //品牌/车型
  brandInput: function (e) {
    this.setData({
      brand: e.detail.value
    })
  },
  //车牌号
  plateNumberInput: function (e) {
    this.setData({
      plateNumber: e.detail.value
    })
  },
  //所有人
  holderInput: function (e) {
    this.setData({
      holder: e.detail.value
    })
  },
  //驾驶证所有人
  holder1Input: function (e) {
    this.setData({
      holder1: e.detail.value
    })
  },
  //注册/发证日期
  dateIssueInput: function (e) {
    this.setData({
      dateIssue: e.detail.value
    })
  },
  //驾驶证注册/发证日期
  dateIssue1Input: function (e) {
    this.setData({
      dateIssue1: e.detail.value
    })
  },
  //使用性质
  natureInput: function (e) {
    this.setData({
      nature: e.detail.value
    })
  },
  //驾驶证使用性质
  nature1Input: function (e) {
    this.setData({
      nature1: e.detail.value
    })
  },
  //住址
  addressInput: function (e) {
    this.setData({
      address: e.detail.value
    })
  },
  //驾驶证住址
  address1Input: function (e) {
    this.setData({
      address1: e.detail.value
    })
  },
  //车架号
  vinnoInput: function (e) {
    this.setData({
      vinno: e.detail.value
    })
  },
  //发动机号
  engineNumberInput: function (e) {
    this.setData({
      engineNumber: e.detail.value
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
    var that = this;
    console.log(e)
    var checkedValue = that.data.checkedValue;
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
  //配偶证件号码
  spouseNumber: function (e) {
    this.setData({
      spouseNumber: e.detail.value
    })
  },
  //配偶单位名称
  spouseCompanyName: function (e) {
    this.setData({
      spouseCompanyName: e.detail.value
    })
  },
  //配偶单位地址
  spouseCompanyAddress: function (e) {
    this.setData({
      spouseCompanyAddress: e.detail.value
    })
  },
  //配偶月收入
  spouseMonthlyIncome: function (e) {
    this.setData({
      spouseMonthlyIncome: e.detail.value
    })
  },
  //配偶供养人数
  spouseChildren: function (e) {
    this.setData({
      spouseChildren: e.detail.value
    })
  },
  //联系电话
  spouseTelephoneInput: function (e) {
    this.setData({
      spouseTelephone: e.detail.value
    })
  },
  //紧急联系人
  urgentInput: function (e) {
    this.setData({
      urgent: e.detail.value
    })
  },
  //紧急联系人联系电话
  urgentTelephoneInput: function (e) {
    this.setData({
      urgentTelephone: e.detail.value
    })
  },
  //紧急联系人2
  urgent2Input: function (e) {
    this.setData({
      urgent2: e.detail.value
    })
  },
  //紧急联系人联系电话2
  urgentTelephone2Input: function (e) {
    this.setData({
      urgentTelephone2: e.detail.value
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
  //
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
  },
  //储蓄卡
  orderBy9Input: function (e) {
    this.setData({
      orderBy9: e.detail.value
    })
  },
  //开户行
  orderBy10Input: function (e) {
    this.setData({
      orderBy10: e.detail.value
    })
  },
  //银行预留电话
  orderBy11Input: function (e) {
    this.setData({
      orderBy11: e.detail.value
    })
  },
  //银行预留电话
  orderBy30Input: function (e) {
    this.setData({
      orderBy30: e.detail.value
    })
  },
  //   利率或押金
  orderBy14Input: function (e) {
    this.setData({
      orderBy14: e.detail.value
    })
  },
  //   GPS费或月前租金
  orderBy15Input: function (e) {
    this.setData({
      orderBy15: e.detail.value
    })
  },
  //   服务费或月租金
  orderBy16Input: function (e) {
    this.setData({
      orderBy16: e.detail.value
    })
  },
  //   其他
  orderBy17Input: function (e) {
    this.setData({
      orderBy17: e.detail.value
    })
  },

  //   计息金额
  orderBy51Input: function (e) {
    this.setData({
      orderBy51: e.detail.value
    })
  },

  goXycx: function (e) {
    var that = this;
    console.log(e)
    wx.navigateTo({
      // url: '/pages/goCredit/goCredit?name=' + that.data.ocrName + '&ocrIdNumber=' + that.data.ocrIdNumber
      url: '/pages/goyfz/goyfz?name=' + that.data.ocrName + '&ocrIdNumber=' + that.data.ocrIdNumber

    })
  },
  //   指导价
  orderBy49Input: function (e) {
    var newList23 = this.data.newList23;
    console.log(newList23)
    this.setData({
      orderBy49: newList23
    })
  },
  // //   月供
  // orderBy18Input: function (e) {
  //   this.setData({
  //     orderBy18: e.detail.value
  //   })
  // },
  //   合计费用
  orderBy32Input: function (e) {
    var that = this;
    var newList20 = that.data.newList20;
    this.setData({
      orderBy32: newList20
    })
  },

  //选择期限
  bindRelationship: function (e) {
    var that = this;
    var index = e.detail.value;
    var orderPeriod = that.data.array[index];
    this.setData({
      pickerIndex: index,
      orderPeriod: orderPeriod
    })

    var orderBy32 = that.data.orderBy32;
    var orderBy33 = that.data.orderBy33;
    var orderBy34 = Math.round(Number(orderBy32) - Number(orderBy33))
    var orderBy18 = Math.round(Number(orderBy34) / Number(orderPeriod) + Number(orderBy34) * 0.003)
    var orderBy48 = Math.round(Number(orderBy18) * Number(orderPeriod) + Number(orderBy33))
    var orderBy51 = Math.round((Number(orderBy34) * Number(orderPeriod) * 0.003) + Number(orderBy34))
    console.log(orderBy51)
    this.setData({
      orderBy32: orderBy32,
      orderBy34: orderBy34,
      orderBy18: orderBy18,
      orderBy48: orderBy48,
      orderBy51: orderBy51,
    })
  },
  //   首付
  orderBy33Input: function (e) {
    var that = this;
    this.setData({
      orderBy33: e.detail.value
    })
    var orderPeriod = that.data.orderPeriod;
    var orderBy33 = that.data.orderBy33;
    var orderBy32 = that.data.orderBy32;
    var orderBy34 = Math.round(Number(orderBy32) - Number(orderBy33))
    var orderBy18 = Math.round(Number(orderBy34) / Number(orderPeriod) + Number(orderBy34 * 0.003));
    var orderBy48 = Math.round(Number(orderBy18) * Number(orderPeriod) + Number(orderBy33));
    var orderBy51 = Math.round((Number(orderBy34) * Number(orderPeriod) * 0.003) + Number(orderBy34))
    console.log(orderBy51)
    this.setData({
      orderBy34: orderBy34,
      orderBy18: orderBy18,
      orderBy48: orderBy48,
      orderBy51: orderBy51,
    })
  },
  // //   贷款
  // orderBy34Input: function (e) {
  //   var that = this;
  //   this.setData({
  //     orderBy34: e.detail.value
  //   })
  // },

  radioChangeOne: function (e) {
    this.setData({
      orderBy4: e.detail.value
    })
    console.log(this.data.orderBy9)
  },

  orderBy5Input: function (e) {
    this.setData({
      orderBy5: e.detail.value
    })
  },
  orderBy35Input: function (e) {
    var orderBy35 = this.data.orderBy35;
    this.setData({
      orderBy35: this.data.proClass
    })
    console.log(orderBy35)
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
        console.log(res)
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
              console.log(res)
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
                console.log("sbtype == 6:" + JSON.stringify(items))
              } else if (sbtype == 5) {
                var data = JSON.parse(res.data);
                data = JSON.parse(data[0].piccontent);
                var items = data.result.cardsinfo[0].items;
                that.setData({
                  holder1: items[2].content,
                  dateIssue1: items[8].content,
                  nature1: items[7].content,
                  address1: items[4].content,

                })
                console.log(data);
                console.log(items);
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

  bindChange: function (e) {
    console.log('picker发送选择改变，索引值为', e.detail.value)
    console.log("选中的id值:" + e.target.dataset.id)
    console.log(e);
    this.setData({
      index: e.detail.value,
    })
  },
  goche300: function () {
    var that = this;
    var vinno = that.data.vinno;
    var index = that.data.index;
    var dateIssue = that.data.dateIssue;
    var mile = that.data.mile;
    var i = that.data.index;
    var zone = that.data.type[i].id;
    console.log(vinno)
    console.log(zone)
    console.log(mile)
    console.log(dateIssue)
    wx.request({
      url: pathUrl + '/rest/fk/doche300gujiabyvin/',
      data: {
        keyword1: vinno,
        keyword2: zone,
        keyword3: dateIssue,
        keyword4: mile,

      },
      method: 'POST',
      success: function (res) {
        console.log(res)
        var str = res.data.data.rmContent;
        if (str == null) {
          wx.showModal({
            title: '提示',
            content: "车架号有误，请重新输入",
            showCancel: false,
          })
        };
        arr = str.split(",");
        console.log(str);
        console.log(arr);
        console.log(arr[0]);
        var str0 = arr[0];
        var arr0 = str0.split(':');
        console.log(arr0[1]);

        if (arr0[1] == '"0"') {
          console.log('2313')
          console.log(arr[2])
          if (arr[2] == undefined) {
            console.log('123123')

            var str1 = arr[1];
            var arr1 = str1.split(':');
            var msg = arr1[1];
            wx.showModal({
              title: '提示',
              content: msg,
              showCancel: false,
            })
            return false;
          } else {
            var str2 = arr[2];
            var arr2 = str2.split(':');
            var msg = arr2[1];
            wx.showModal({
              title: '提示',
              content: msg,
              showCancel: false,
            })
            return false;
          }
        }
        var str1 = arr[1];
        var arr1 = str1.split(':');
        var str2 = arr[2];
        var arr2 = str2.split(':');
        var str3 = arr[3];
        var arr3 = str3.split(':');
        var str4 = arr[4];
        var arr4 = str4.split(':');
        var str5 = arr[5];
        var arr5 = str5.split(':');
        var str6 = arr[6];
        var arr6 = str6.split(':');
        var str7 = arr[7];
        var arr7 = str7.split(':');
        var str9 = arr[9];
        var arr9 = str9.split(':');
        var str10 = arr[10];
        var arr10 = str10.split(':');
        var str11 = arr[11];
        var arr11 = str11.split(':');
        console.log(arr1)
        console.log(arr1[1])
        if (res.data.ok) {
          that.setData({
            arr1: arr1[1],
            arr2: arr2[1],
            arr3: arr3[1],
            arr4: arr4[1],
            arr5: arr5[1],
            arr6: arr6[1],
            arr7: arr7[1],
            arr9: arr9[1],
            arr10: arr10[1],
            arr11: arr11[1],

          })
        }
        else {
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
        orderCartype: that.data.brand, //车型 如果是房子就是面积
        orderCarno: that.data.plateNumber, // 车牌  如果是房子就是小区名称
        cusRemark: that.data.residentialAddress, // 个人资料的居住地址
        cusMobile: that.data.telephone, //电话
        cusMaritalStatus: that.data.checkedValue, //婚姻状况
        cusDeu: that.data.orderBy5, //学历
        cusAge: '',
        cusQqid: '', //qq号
        orderBy1: that.data.arr7,
        orderBy2: that.data.arr9,
        orderBy3: that.data.arr10,
        orderBy4: that.data.arr11,
        orderBy5: that.data.orderBy5,
        orderBy6: that.data.orderBy6, //担保人电话
        orderBy7: that.data.orderBy7, //担保人姓名
        orderBy8: that.data.orderBy8, //担保人身份证号
        orderBy9: that.data.orderBy9, //储蓄卡
        orderBy10: that.data.orderBy10,//开户行
        orderBy11: that.data.orderBy11,//银行预留电话
        orderBy14: that.data.orderBy14, //利率或押金
        orderBy15: that.data.orderBy15, //GPS费或月前租金
        orderBy16: that.data.orderBy16, //服务费或月租金
        orderBy17: that.data.orderBy17, //其他
        orderBy18: that.data.orderBy18, //邮箱
        orderBy19: that.data.spouseName,//配偶姓名
        orderBy20: that.data.spouseNumber,//配偶证件号码
        orderBy21: that.data.spouseCompanyName,//配偶公司
        orderBy22: that.data.spouseCompanyAddress,//配偶地址
        orderBy23: that.data.spouseTelephone,//配偶电话号码
        orderBy24: that.data.spouseMonthlyIncome,//配偶收入
        orderBy25: that.data.spouseChildren,//供养人数
        orderBy26: that.data.holder1,//驾驶证所有人
        orderBy27: that.data.dateIssue1,//注册与发证日期
        orderBy28: that.data.nature1,//使用性质
        orderBy29: that.data.address1,//住址
        orderBy30: that.data.orderBy30,//银行服务密码
        orderBy53: that.data.arr3,
        orderAmount: that.data.orderAmount,// 金额
        orderPeriod: that.data.orderPeriod, // 期数
        orderBy51: that.data.arr5,//车辆估值
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
      wx.request({
        url: pathUrl + '/rest/fxjOrderMainController/' + that.data.openId,
        data: data,
        method: 'POST',
        success: function (res) {
          console.log(res.data)
          if (res.data.ok) {
            that.setData({
              isSubmit: true
            });
            wx.showToast({
              title: '提交成功',
              duration: 1000,
              mask: true,
              success: function () {
                wx.navigateTo({
                  url: "/pages/orderManagement/orderManagement",
                })
              }
            })
          } else {
            that.setData({
              isSubmit: true
            });
            wx.showToast({
              title: res.data.message,
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
  downFile: function () {
    wx.downloadFile({
      url: 'https://zhaodui.oss-cn-shenzhen.aliyuncs.com/doc/zxsqs.docx',
      header: {},
      success: function (res) {
        console.log(res)
        var filePath = res.tempFilePath;
        wx.openDocument({
          filePath: filePath,
          fileType: 'docx',
          success: function (res) {
            console.log('打开文档成功')
          }
        })
      },
      fail: function (res) {
        wx.showToast({
          title: '下载出错',
          icon: 'none',
          mask: true,
          duration: 10000
        })
      }
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