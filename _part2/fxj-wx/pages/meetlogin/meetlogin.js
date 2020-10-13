var app = getApp();
const Utils = require('../../utils/util.js')

const pathUrl = app.globalData.pathUrl;
Page({
    data: {

      
      // used to store user info like portrait & nickname
      userInfo: {},
      hasUserInfo: false,
      // whether to disable join btn or not
      disableJoin: false,
      
        pathUrl: pathUrl,
        openId: '',
        proNo: '',
        orderNo: '',
        proIsdb: '',//是否有担保人1是
        proAttList: [], //上传附件列表
      array: [ '36'],

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
        engineNumber: '', //发动机号
        residentialAddress: '', //居住地址
        telephone: '', //联系电话
        checkedValue: '', //婚姻情况
        spouseName: '', //配偶姓名
        spouseTelephone: '', //联系电话
        isSubmit: true, //防止重复提交
        guaranteeName: '',//担保人姓名
        guaranteeIdCard: '',//担保人身份照
        guaranteePhone: '',//担保人电话
      orderAmount: '', //金额
      pickerIndex: '3',//选择期数index
      orderPeriod: '',//期数
      rldbxsd:'',//人脸对比结果
      yinhangUser:'admin',
    },
    onLoad: function (e) {

      this.uid = Utils.getUid();
      this.lock = false;
      let userInfo = wx.getStorageSync("userInfo");
      if (userInfo) {
        this.setData({
          hasUserInfo: true,
          userInfo: userInfo
        });
      }

        var openId = wx.getStorageSync('openId');
      
        this.setData({
            openId: openId
        })
        wx.setNavigationBarTitle({
            title: "视频面签"
        })
      this.getno();

    },
  //ocr名字赋值
  yinhangUserInput: function (e) {
    this.setData({
      yinhangUser: e.detail.value
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
    //注册/发证日期
    dateIssueInput: function (e) {
        this.setData({
            dateIssue: e.detail.value
        })
    },
    //使用性质
    natureInput: function (e) {
        this.setData({
            nature: e.detail.value
        })
    },
    //住址
    addressInput: function (e) {
        this.setData({
            address: e.detail.value
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
    //人脸对比
  rldbxsdInput: function (e) {
    this.setData({
      rldbxsd: e.detail.value
    })
  },
    //获取orderNo
    getno: function () {
        var that = this;
        wx.request({
            url: pathUrl + '/rest/base/getno/99',
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

    creatorder:function(){

      var that = this;
      if (1==1) {
        
        var data = {
          bpmStatus: 10,
          createBy: that.data.openId,
          sysOrgCode: '',
          orderNo: that.data.orderNo, //订单编号
          proNo: "99", //产品编号
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
          orderAmount: that.data.orderAmount, // 金额
          yinhangUser: that.data.yinhangUser, // 面签人
          orderPeriod: that.data.orderPeriod  // 期数
           
        };
        console.log("data:" + JSON.stringify(data));
        wx.request({
          url: pathUrl + '/rest/fxjOrderMainController/' + that.data.openId,
          data: data,
          method: 'POST',
          success: function (res) {
            console.log(res.data)
            if (res.data.ok) {
               
            }
          }
        })
     
      }

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
                    title: '正在识别...',
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
                            }
                            if (uploadImgCount == tempFilePaths.length) {
                                wx.hideToast();
                            }
                        },
                        fail: function (res) {
                            wx.hideToast();
                            wx.showModal({
                                title: '错误提示',
                                content: '识别失败',
                                showCancel: false
                            })
                        }
                    });
                }
            }
        });
    },

  //上传OCR图片
  upEnclosurerldb: function (e) {
    var that = this;


    let value = that.data.ocrIdNumber;
    let ocrName = that.data.ocrName;

    if (!value) {
      wx.showToast({
        title: '请识别或输入身份证号',
        icon: 'none',
        duration: 2000
      })
    }else   if (!ocrName) {
        wx.showToast({
          title: '请识别或输入姓名',
          icon: 'none',
          duration: 2000
        })
      
    }else{
  
 
    var that = this;
    var sbtype = e.currentTarget.dataset.sbtype
    wx.chooseImage({
      count: 1, //最多可以选择的图片总数  
      sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有  
      sourceType: ['camera'], // 可以指定来源是相册还是相机，默认二者都有  
      success: function (res) {
        // 返回选定照片的本地文件路径列表，tempFilePath可以作为img标签的src属性显示图片  
        var tempFilePaths = res.tempFilePaths;
        //启动上传等待中...  
        wx.showToast({
          title: '正在对比计算...',
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
              orderNo: that.data.orderNo,
              cardid: that.data.ocrIdNumber,
              realname: that.data.ocrName
            },
            header: {
              "Content-Type": "multipart/form-data"
            },
            success: function (res) {
              uploadImgCount++;
              console.log(res);
              if (sbtype == 99) {
                var data = JSON.parse(res.data);
                data = JSON.parse(data[0].piccontent);
                var items = data.result.result.Similarity;
                that.setData({
                  rldbxsd: items,
                })
                console.log("sbtype == 99:" + JSON.stringify(items))
              }
              if (uploadImgCount == tempFilePaths.length) {
                wx.hideToast();
              }
            },
            fail: function (res) {
              wx.hideToast();
              wx.showModal({
                title: '错误提示',
                content: '对比失败',
                showCancel: false
              })
            }
          });
        }
      }
    });
    }
  },


  checkJoinLock: function () {
    return !(this.lock || false);
  },

  lockJoin: function () {
    this.lock = true;
  },

  unlockJoin: function () {
    this.lock = false;
  },
  submitBtn: function () {
    this.creatorder();
    var that = this;
 
      let value = that.data.orderNo;
   
      let uid = this.uid;
      if (!value) {
        wx.showToast({
          title: '请输入身份证号',
          icon: 'none',
          duration: 2000
        })
      } else {
        if (value.length > 16) {
          value = value.slice(0, 16);
        }
        if (this.checkJoinLock()) {
          this.lockJoin();
          
            wx.showModal({
              title: '是否推流',
              content: '选择取消则作为观众加入，观众模式不推流',
              showCancel: true,
              success: function (res) {
                let role = "audience";
                if (res.confirm) {
                  role = "broadcaster";
                }

                wx.navigateTo({
                  url: `../meeting/meeting?channel=${value}&uid=${uid}&role=${role}`
                });
              }
            })
    
        }
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