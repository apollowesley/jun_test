//index.js
//获取应用实例
var app = getApp()

var arrName=["考勤","日历","请假","调休","出差","报销","调岗","离职","邮件","公告"]
var arr_link = [1, 10, 15, 52, 62, 68, 75, 82, 98, 112, 147, 161, 218, 166, 182,
                188, 192, 197, 202, 205, 212, 227, 132]
//var file = "../../pages/list/list"
Page({
    data: {
        items: [{
            id: "1",
            src: "/image/kaoqin.png",
            text: arrName[0],
            url:"/page/component/applys/attence/attence"
        }, {
            id: "10",
            src: "/image/rili.png",
            text: arrName[1],
            url: "/page/component/applys/attence/attence"
        }, {
            id: "15",
            src: "/image/qingjia.png",
            text: arrName[2],
            url: "/page/component/applys/attence/attence"
        }, {
            id: "52",
            src: "/image/tiaoxiu.png",
            text: arrName[3],
            url: "/page/component/applys/attence/attence"
        }, {
            id: "62",
            src: "/image/chuchai.png",
            text: arrName[4],
            url: "/page/component/applys/attence/attence"
        }, {
            id: "68",
            src: "/image/baoxiao.png",
            text: arrName[5],
            url: "/page/component/applys/attence/attence"
        }, {
            id: "75",
            src: "/image/diaodong.png",
            text: arrName[6],
            url: "/page/component/applys/attence/attence"
        }, {
            id: "82",
            src: "/image/lizhi.png",
            text: arrName[7],
            url: "/page/component/applys/attence/attence"
        }, {
            id: "98",
            src: "/image/youjian.png",
            text: arrName[8],
            url: "/page/component/applys/attence/attence"
        }, {
          id: "112",
          src: "/image/gonggao.png",
          text: arrName[9],
          url: "/page/component/applys/attence/attence"
        }]
       // url:file,
    },
})
