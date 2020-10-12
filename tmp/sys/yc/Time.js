const Utils = {
     formatTime: (time) => {
         if (typeof time !== 'number' || time < 0) {
             return time
         }

         var hour = parseInt(time / 3600)
         time = time % 3600
         var minute = parseInt(time / 60)
         time = time % 60
         var second = time

         return ([hour, minute, second]).map(function(n) {
             n = n.toString()
             return n[1] ? n : '0' + n
         }).join(':')
     },
     toTimestamp: (num) => {
         var n = 10;
         if (num || num < 14) {
             n = num;
         }
         return parseInt((Date.now() + '').substr(0, n));
     },
     /**var time=new Date(parseInt(1420184730) * 1000).format('yyyy年M月d日');
      * 月(M)、日(d)、小时(h)、分(m)、秒(s) 毫秒(S)、季度(q) 可以用 1-2 个占位符，
      * 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
      * 例子：
      * (new Date()).format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
      * */
     formatDate: (timep, timetype) => {
             if (timep && parseInt(timep) != timep) {
                 // 判断时间戳是否是数字，不是数字原数据返回
                 return timep;
             } else if (!timep) {
                 var timep = Date.parse(new Date()) / 1000;
             } else if (timep.length == 10) {
                 var timep = parseInt(timep)
             } else if (timep.length > 10) {
                 var timep = timep.substring(0, 10);
             }
             // return '收到'+timep;

             var timetype = timetype || "yyyy-M-d hh:mm:ss";
             Date.prototype.format = function(fmt) { //author: meizz
                 var o = {
                     "M+": this.getMonth() + 1, //月份
                     "d+": this.getDate(), //日
                     "h+": this.getHours(), //小时
                     "m+": this.getMinutes(), //分
                     "s+": this.getSeconds(), //秒
                     "q+": Math.floor((this.getMonth() + 3) / 3), //季度
                     "S": this.getMilliseconds() //毫秒
                 };
                 if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp
                     .$1.length));
                 for (var k in o)
                     if (new RegExp("(" + k + ")").test(fmt))
                         fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(
                             ("" + o[k]).length)));
                 return fmt;
             }
             var time = new Date(parseInt(timep) * 1000).format(timetype);
             return time;
         }

         /**
          * 获取时间戳 默认10位,
          */
         ,
     getTime: (num) => {
         var num = parseInt(num);
         if (isNaN(num)) {
             num = 1000;
         }

         return Date.parse(new Date()) / num;
     }
 }
 var dateUtils = {
     UNITS: {
         '年': 31557600000,
         '月': 2629800000,
         '天': 86400000,
         '小时': 3600000,
         '分钟': 60000,
         '秒': 1000
     },
     humanize: (milliseconds) => {
         var humanize = '';
         for (var key in this.UNITS) {
             if (milliseconds >= this.UNITS[key]) {
                 humanize = Math.floor(milliseconds / this.UNITS[key]) + key + '前';
                 break;
             }
         }
         return humanize || '刚刚';
     },
     format: (dateStr) => {
         var date = this.parse(dateStr)
         var diff = Date.now() - date.getTime();
         if (diff < this.UNITS['天']) {
             return this.humanize(diff);
         }
         var _format = (number) => {
             return (number < 10 ? ('0' + number) : number);
         };
         return date.getFullYear() + '/' + _format(date.getMonth() + 1) + '/' + _format(date.getDay()) + '-' +
             _format(date.getHours()) + ':' + _format(date.getMinutes());
     },
     parse: (str) => { //将"yyyy-mm-dd HH:MM:ss"格式的字符串，转化为一个Date对象
         var a = str.split(/[^0-9]/);
         return new Date(a[0], a[1] - 1, a[2], a[3], a[4], a[5]);
     }
 }

 var toTimestamp = Utils.toTimestamp; //日期时间转成时间戳
 var getTime = Utils.getTime; //当前时间戳
 var formatDate = Utils.formatDate; //时间戳转日期
 var dateUtils = dateUtils; //距离时间

 export {
     toTimestamp,
     getTime,
     formatDate,
     dateUtils
 }
