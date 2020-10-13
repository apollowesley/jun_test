var app = getApp();
const pathUrl = app.globalData.pathUrl;
Page({
  data: {
    // pathUrl: pathUrl,
    // openId: '',
    // proNo: '',
    // orderNo: '',
    // proIsdb: '', //是否有担保人1是
    // proAttList: [], //上传附件列表
    // array: ['1', '3', '6', '12'],
    // // array: ['36个月'],
    // flag: true,
    // ocrName: '游志金', //ocr姓名
    // ocrSex: '男', //ocr性别
    // ocrNation: '汉', //ocr民族
    // ocrBirth: '1995-11-03', //ocr出生
    // ocrAddress: '福建省厦门市集美区银盛里82号302室', //ocr地址
    // ocrIdNumber: '350822199511033916', //ocr身份证号
    // ocrOffice: '厦门市公安局集美分局', //ocr签发机关
    // ocrValidityPeriod: '2013-08-01', //ocr有效期限
    // cusIdValidfrom: '', //证件开始日期
    // cusIdValidto: '', //证件结束日期
    // cusRemark:'厦门市集美区海上五月花三期',
    // cusMobile:'13850034890',
    // brand: '', //品牌/车型
    // plateNumber: '', //车牌号
    // holder: '', //所有人
    // dateIssue: '', //注册/发证日期
    // nature: '', //使用性质
    // address: '', //住址
    // holder1: '游志金', //所有人
    // dateIssue1: '2017-01-04', //注册/发证日期
    // nature1: 'C1', //使用性质
    // address1: '福建省厦门市集美区银盛里82号302室', //住址
    // vinno: '', //车架号
    // engineNumber: '', //发动机号
    // residentialAddress: '厦门市集美区海上五月花三期', //居住地址
    // telephone: '13850034890', //联系电话
    // checkedValue: '', //婚姻情况
    // spouseName: 'yqq', //配偶姓名
    // spouseTelephone: '13859923638', //联系电话
    // emergencyResAddr:'厦门市集美区海上五月花',
    // emergencyRelation:'父子',
    // urgent: 'zxq', //紧急联系人
    // urgentTelephone: '15759078718', //紧急联系人联系电话
    // urgent2: '', //紧急联系人
    // urgentTelephone2: '', //紧急联系人联系电话
    // isSubmit: true, //防止重复提交
    // guaranteeName: '', //担保人姓名
    // guaranteeIdCard: '', //担保人身份照
    // guaranteePhone: '', //担保人电话
    // orderAmount: '100000', //金额
    // pickerIndex: '3',//选择期数index
    // orderPeriod: '36',//期数
    // orderBy6: '厦门找对科技',
    // orderBy7: '厦门市湖里区万翔商务中心',
    // orderBy8: '1w',
    // orderBy4: '',
    // orderBy5: '',
    // orderBy9: '6232111870000773692', //储蓄卡
    // orderBy10: '厦门湖里支行',//开户行
    // orderBy11: '13850034890',//银行预留电话
    // orderBy14: '0.68', //押金或利率
    // orderBy15: '500', //GPS费或月前租金
    // orderBy16: '500', //服务费或月租金
    // orderBy17: '0', //其他
    // orderBy18: '458094551@qq.com', //邮箱
    pathUrl: pathUrl,
    openId: '',
    proNo: '',
    orderNo: '',
    proIsdb: '', //是否有担保人1是
    proAttList: [], //上传附件列表
    array: ['12', '24', '36', '12'],
    array1: ['自有', '租赁', '按揭', '亲戚楼宇', '集体宿舍', '其他'],
    array2: ['一般员工', '管理员工'],
    array3: ['群众', '团员','党员'],
    array4: ['初中', '高中', '中专', '专科', '本科', '硕士研究生'],
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
    cusRemark: '',
    cusMobile: '',
    brand: '', //品牌/车型
    plateNumber: '', //车牌号
    holder: '', //所有人
    dateIssue: '', //注册/发证日期
    nature: '', //使用性质
    address: '', //住址
    holder1: '', //所有人
    dateIssue1: '', //注册/发证日期
    nature1: '', //使用性质
    address1: '', //住址
    vinno: '', //车架号
    engineNumber: '', //发动机号
    residentialAddress: '', //居住地址
    telephone: '', //联系电话
    checkedValue: '', //婚姻情况
    spouseName: '', //配偶姓名
    spouseTelephone: '', //联系电话
    emergencyResAddr: '',
    emergencyRelation: '',
    urgent: '', //紧急联系人
    urgentTelephone: '', //紧急联系人联系电话
    urgent2: '', //紧急联系人
    urgentTelephone2: '', //紧急联系人联系电话
    isSubmit: true, //防止重复提交
    guaranteeName: '', //担保人姓名
    guaranteeIdCard: '', //担保人身份照
    guaranteePhone: '', //担保人电话
    orderAmount: '', //金额
    pickerIndex: '2',//选择期数index
    orderPeriod: '36',//期数
    pickerIndex1: '0',
    orderPeriod1: '自有',
    pickerIndex2: '0',
    orderPeriod2: '一般员工',
    pickerIndex3: '0',
    orderPeriod3: '群众',
    pickerIndex4: '4',
    orderPeriod4: '本科',
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
    orderBy18: '', //邮箱

  },
  onLoad: function (e) {
   
      var openId = wx.getStorageSync('openId');
      console.log(e);
      this.setData({
        firstin: '1',
        orderNo: e.orderNo,
        proNo: e.proNo,
        id: e.id,
        openId: openId,
        proName:e.proName
      })
      // this.getno();
      // this.getProAttList();
      this.orderMainController();
    wx.setNavigationBarTitle({
      title: e.proName
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
    console.log(orderPeriod)

  },
  //选择房产性质
  bindRelationship1: function (e) {
    var index = e.detail.value;
    var orderPeriod1 = this.data.array1[index];
    this.setData({
      pickerIndex1: index,
      orderPeriod1: orderPeriod1
    })
    console.log(orderPeriod1)

  },
  //选择公司职务
  bindRelationship2: function (e) {
    var index = e.detail.value;
    var orderPeriod2 = this.data.array2[index];
    this.setData({
      pickerIndex2: index,
      orderPeriod2: orderPeriod2
    })
    console.log(orderPeriod2)

  },
  //选择政治面貌
  bindRelationship3: function (e) {
    var index = e.detail.value;
    var orderPeriod3 = this.data.array3[index];
    this.setData({
      pickerIndex3: index,
      orderPeriod3: orderPeriod3
    })
    console.log(orderPeriod3)

  },
  //选择最高学历
  bindRelationship4: function (e) {
    var index = e.detail.value;
    var orderPeriod4 = this.data.array4[index];
    this.setData({
      pickerIndex4: index,
      orderPeriod4: orderPeriod4
    })
    console.log(orderPeriod4)
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
  //行驶证所有人
  holderInput1: function (e) {
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
  //行驶证注册/发证日期
  dateIssueInput1: function (e) {
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
  //行驶证使用性质
  natureInput1: function (e) {
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
  //行驶证住址
  addressInput1: function (e) {
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
    this.setData({
      checkedValue: e.detail.value
    })
    console.log(this.data.checkedValue)
  },
  //驾照
  radioChange1: function (e) {
    this.setData({
      orderBy11: e.detail.value
    })
    console.log(this.data.orderBy11)
  },
  //是否有医社保
  radioChange2: function (e) {
    this.setData({
      orderBy13: e.detail.value
    })
  },
  //是否有公积金
  radioChange3: function (e) {
    this.setData({
      orderBy14: e.detail.value
    })
  },
  //是否有房产
  radioChange4: function (e) {
    this.setData({
      orderBy15: e.detail.value
    })
  },
  //是否有私家车
  radioChange5: function (e) {
    this.setData({
      orderBy16: e.detail.value
    })
  },
  //是否挂靠
  radioChange6: function (e) {
    this.setData({
      orderBy17: e.detail.value
    })
  },
  //是否有担保人
  radioChange7: function (e) {
    this.setData({
      orderBy29: e.detail.value
    })
    console.log(this.data.orderBy29)

  },
  //配偶是否为法人
  radioChange8: function (e) {
    this.setData({
      spouseIffr: e.detail.value
    })
  },
  //担保人是否为法人
  radioChange9: function (e) {
    this.setData({
      emergencyIffr: e.detail.value
    })
  },
  //配偶姓名
  spouseNameInput: function (e) {
    this.setData({
      spouseName: e.detail.value
    })
  },
  //配偶姓名
  spouseIdInput: function (e) {
    this.setData({
      spouseId: e.detail.value
    })
  },
  //联系电话
  spouseTelephoneInput: function (e) {
    this.setData({
      spouseTelephone: e.detail.value
    })
  },
  //配偶公司
  spouseCompanyInput: function (e) {
    this.setData({
      spouseCompany: e.detail.value
    })
  },
  //配偶公司职位
  spousePositionInput: function (e) {
    this.setData({
      spousePosition: e.detail.value
    })
  },
  //配偶年收入
  spouseIncomeInput: function (e) {
    this.setData({
      spouseIncome: e.detail.value
    })
  },
  //担保人与借款人姓名
  emergencyRelationInput: function (e) {
    this.setData({
      emergencyRelation: e.detail.value
    })
  },
  //配偶姓名
 emergencyNameInput: function (e) {
    this.setData({
     emergencyName: e.detail.value
    })
  },
  //担保人姓名
  emergencyIdInput: function (e) {
    this.setData({
      emergencyId: e.detail.value
    })
  },
  //担保人联系电话
  emergencyTelephoneInput: function (e) {
    this.setData({
      emergencyTelephone: e.detail.value
    })
  },
  //担保人公司
  emergencyCompanyInput: function (e) {
    this.setData({
      emergencyCompany: e.detail.value
    })
  },
  //担保人公司职位
  emergencyPositionInput: function (e) {
    this.setData({
      emergencyPosition: e.detail.value
    })
  },
  //担保人年收入
  emergencyIncomeInput: function (e) {
    this.setData({
     emergencyIncome: e.detail.value
    })
  },
  //联系人1
  urgent1Input: function (e) {
    this.setData({
      urgent1: e.detail.value
    })
  },
  //联系人1关系
  urgentRelation1Input: function (e) {
    this.setData({
      urgentRelation1: e.detail.value
    })
  },
  //联系人1电话
 urgentTelephone1Input: function (e) {
    this.setData({
      urgentTelephone1: e.detail.value
    })
  },
  //联系人2
  urgent2Input: function (e) {
    this.setData({
      urgent2: e.detail.value
    })
  },
  //联系人2关系
  urgentRelation2Input: function (e) {
    this.setData({
      urgentRelation2: e.detail.value
    })
  },
  //联系人2电话
  urgentTelephone2Input: function (e) {
    this.setData({
      urgentTelephone2: e.detail.value
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
  },

  orderBy10Input: function (e) {
    this.setData({
      orderBy10: e.detail.value
    })
  },
  orderBy11Input: function (e) {
    this.setData({
      orderBy11: e.detail.value
    })
  },
  orderBy12Input: function (e) {
    this.setData({
      orderBy12: e.detail.value
    })
  },
  orderBy19Input: function (e) {
    this.setData({
      orderBy19: e.detail.value
    })
  },
  orderBy20Input: function (e) {
    this.setData({
      orderBy20: e.detail.value
    })
  },
  orderBy21Input: function (e) {
    this.setData({
      orderBy21: e.detail.value
    })
  },
  //是否单位法人或私营业主
  radioChange8: function (e) {
    this.setData({
      orderBy22: e.detail.value
    })
  },
  orderBy24Input: function (e) {
    this.setData({
      orderBy24: e.detail.value
    })
  },
  orderBy25Input: function (e) {
    this.setData({
      orderBy25: e.detail.value
    })
  },
  orderBy26Input: function (e) {
    this.setData({
      orderBy26: e.detail.value
    })
  },
  orderBy27Input: function (e) {
    this.setData({
      orderBy27: e.detail.value
    })
  },
  orderBy28Input: function (e) {
    this.setData({
      orderBy28: e.detail.value
    })
  },
  orderBy30Input: function (e) {
    this.setData({
      orderBy30: e.detail.value
    })
  },
  orderBy31Input: function (e) {
    this.setData({
      orderBy31: e.detail.value
    })
  },
  orderBy32Input: function (e) {
    this.setData({
      orderBy32: e.detail.value
    })
  },
  orderBy33Input: function (e) {
    this.setData({
      orderBy33: e.detail.value
    })
  },
  orderBy34Input: function (e) {
    this.setData({
      orderBy34: e.detail.value
    })
  },
  orderBy35Input: function (e) {
    this.setData({
      orderBy35: e.detail.value
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
                console.log(data);
                console.log(items);
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
  orderMainController: function () {
    var that = this;
    wx.request({
      url: pathUrl + '/rest/fxjOrderMainController/' + that.data.orderNo,
      data: {

      },
      success: function (res) {
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
            orderEmerList: data.fxjOrderEmerList,
            village: data.orderCarno,
            acreage: data.orderCartype,
            orderAmount: data.orderAmount,
            howLong: data.orderPeriod,
            proIsdb: data.proIsdb,
            urgent: data.urgent,
            orderBy1: data.orderBy1,
            orderBy2: data.orderBy2,
            orderBy3: data.orderBy3,
            orderBy4: data.orderBy4,
            orderBy5: data.orderBy5,
            orderBy6: data.orderBy6,
            orderBy7: data.orderBy7,
            orderBy8: data.orderBy8,
            orderBy9: data.orderBy9,
            orderBy10: data.orderBy10,
            orderBy11: data.orderBy11,
            orderBy12: data.orderBy12,
            orderBy13: data.orderBy13,
            orderBy14: data.orderBy14,
            orderBy15: data.orderBy15,
            orderBy16: data.orderBy16,
            orderBy17: data.orderBy17,
            orderBy18: data.orderBy18,
            orderBy19: data.orderBy19,
            orderBy20: data.orderBy20,
            orderBy21: data.orderBy21,
            orderBy22: data.orderBy22,
            orderBy23: data.orderBy23,
            orderBy24: data.orderBy24,
            orderBy25: data.orderBy25,
            orderBy26: data.orderBy26,
            orderBy27: data.orderBy27,
            orderBy28: data.orderBy28,
            orderBy29: data.orderBy29,
            orderBy30: data.orderBy30,
            orderBy31: data.orderBy31,
            // orderBy32: data.orderBy32,
            // orderBy33: data.orderBy33,
            // orderBy34: data.orderBy34,
            // orderBy35: data.orderBy35,
            // orderBy36: data.orderBy36,
            // orderBy37: data.orderBy37,
            // orderBy38: data.orderBy38,
            // orderBy39: data.orderBy39,
            // orderBy40: data.orderBy40,
            fxjProAttList: data.fxjProAttList
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
  
  submitBtn: function (e) {
    var no = e.currentTarget.dataset.prono;
    var that = this;
    var checkedValue = that.data.checkedValue;
    var orderBy29 = that.data.orderBy29;

    if (that.data.checkedValue == '1') {
      this.setData({
        checkedValue: "已婚"
      })
    };
    if (that.data.orderBy29 == '1') {
      this.setData({
        orderBy29: "是"
      })
    };
    var data = {
      orderOper: 'Y',
      id: that.data.id,
      updateBy: that.data.openId,

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
      orderPeriod: that.data.orderPeriod, // 期数
      cusRemark: that.data.residentialAddress, // 个人资料的居住地址
      cusMobile: that.data.mobile, //电话
      cusMaritalStatus: that.data.checkedValue, //婚姻状况
      cusDeu: '', //学历
      cusAge: '',
      cusQqid: '', //qq号
      orderBy1: that.data.orderBy1,//担保人姓名
      orderBy2: that.data.orderBy2,//担保人身份证号
      orderBy3: that.data.orderBy3,//担保人电话
      orderBy4: that.data.queryResults,
      orderBy5: that.data.violationTimes,
      orderBy6: that.data.orderPeriod4,
      orderBy7: that.data.orderBy7,
      orderBy8: that.data.orderPeriod2,
      orderBy9: that.data.orderPeriod1,
      orderBy10: that.data.orderBy10,
      orderBy11: that.data.orderBy11,
      orderBy12: that.data.orderBy12,
      orderBy13: that.data.orderBy13,
      orderBy14: that.data.orderBy14,
      orderBy15: that.data.orderBy15,
      orderBy16: that.data.orderBy16,
      orderBy17: that.data.orderBy17,
      orderBy18: that.data.orderBy18,
      orderBy19: that.data.orderBy19,
      orderBy20: that.data.orderBy20,
      orderBy21: that.data.orderBy21,
      orderBy22: that.data.orderBy22,
      orderBy23: that.data.orderPeriod3,
      orderBy24: that.data.orderBy24,
      orderBy25: that.data.orderBy25,
      orderBy26: that.data.orderBy26,
      orderBy27: that.data.orderBy27,
      orderBy28: that.data.orderBy28,
      orderBy29: that.data.orderBy29,
      orderBy30: that.data.orderBy30,
      orderBy31: that.data.orderBy31,
      orderBy32: that.data.orderBy32,
      orderBy33: that.data.orderBy33,
      orderBy34: that.data.orderBy34,
      orderBy35: that.data.orderBy35,//暂时到35
      orderBy36: that.data.orderBy36,
      orderBy37: that.data.orderBy37,
      orderBy38: that.data.orderBy38,
      orderBy39: that.data.orderBy39,
      orderBy40: that.data.orderBy40,
      orderBy41: that.data.orderBy41,
      orderBy42: that.data.orderBy42,
      orderBy43: that.data.orderBy43,
      orderBy44: that.data.orderBy44,
      orderBy45: that.data.orderBy45,
      orderBy46: that.data.orderBy46,
      orderBy47: that.data.orderBy47,
      orderBy48: that.data.orderBy48,
      orderBy49: that.data.orderBy49,
      orderBy50: that.data.orderBy50,
      orderBy51: that.data.orderBy51,
      orderBy52: that.data.orderBy52,
      orderBy53: that.data.orderBy53,
      orderBy54: that.data.orderBy54,
      orderBy55: that.data.orderBy55,
      orderBy56: that.data.orderBy56,
      orderBy57: that.data.orderBy57,
      orderBy58: that.data.orderBy58,
      orderBy59: that.data.orderBy59,
      orderBy60: that.data.orderBy60,
      orderBy61: that.data.orderBy61,
      orderBy62: that.data.orderBy62,
      orderBy63: that.data.orderBy63,
      orderBy64: that.data.orderBy64,
      orderBy65: that.data.orderBy65,
      orderBy66: that.data.orderBy66,
      orderBy67: that.data.orderBy67,
      orderBy68: that.data.orderBy68,
      orderBy69: that.data.orderBy69,
      orderBy70: that.data.orderBy70,
      orderBy71: that.data.orderBy71,
      orderBy72: that.data.orderBy72,
      orderBy73: that.data.orderBy73,
      orderBy74: that.data.orderBy74,
      orderBy75: that.data.orderBy75,
      orderBy76: that.data.orderBy76,
      orderBy77: that.data.orderBy77,
      orderBy78: that.data.orderBy78,
      orderBy79: that.data.orderBy79,
      orderBy80: that.data.orderBy80,

      orderAmount: that.data.orderAmount, // 金额
      orderPeriod: that.data.orderPeriod, // 期数
      fxjOrderEmerList: [{
        emergencyRelation:'配偶', //关系
        emergencyContact: that.data.spouseName, //姓名
        emergencyMobile: that.data.spouseTelephone, //电话
        emergencyOffice: that.data.spouseCompany,
        emergencyResAddr: '', //地址
        emergencyBy01: that.data.spouseId,
        emergencyBy02: that.data.spouseIffr,
        emergencyBy03: that.data.spousePosition,
        emergencyBy04: that.data.spouseIncome,
        emergencyBy05: '',
      }, {
          emergencyRelation: that.data.emergencyRelation, //关系
          emergencyContact: that.data.emergencyName, //姓名
          emergencyMobile: that.data.emergencyTelephone, //电话
          emergencyOffice: that.data.emergencyCompany,
          emergencyResAddr: '', //地址
          emergencyBy01: that.data.emergencyId,
          emergencyBy02: that.data.emergencyIffr,
          emergencyBy03: that.data.emergencyPosition,
          emergencyBy04: that.data.emergencyIncome,
          emergencyBy05: '',
        }, {
          emergencyRelation: that.data.urgentRelation1, //关系
          emergencyContact: that.data.urgent1, //姓名
          emergencyMobile: that.data.urgentTelephone1, //电话
          emergencyOffice: '',
          emergencyResAddr: '', //地址
          emergencyBy01: '',
          emergencyBy02: '',
          emergencyBy03: '',
          emergencyBy04: '',
          emergencyBy05: '',
        },
        {
          emergencyRelation: that.data.urgentRelation2, //关系
          emergencyContact: that.data.urgent2, //姓名
          emergencyMobile: that.data.urgentTelephone2, //电话
          emergencyOffice: '',
          emergencyResAddr: '', //地址
          emergencyBy01: '',
          emergencyBy02: '',
          emergencyBy03: '',
          emergencyBy04: '',
          emergencyBy05: '',
        },
        ],
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