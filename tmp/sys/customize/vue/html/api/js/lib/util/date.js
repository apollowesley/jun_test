/**
 * Created by guozhongqiang on 16/6/23.
 */
var dateUtil = {};
(function () {
    var type = {
        'yyyy': 1,
        'yyy': 1,
        'yy': 1,
        'y': 1,
        'MM': 2,
        'M': 2,
        'dd': 3,
        'd': 4,
        'hh': 5,
        'h': 5,
        'mm': 6,
        'm': 6,
        'mi': 6,
        'ss': 7,
        's': 7,
        'SSS': 8
    };
    /**
     * 解析日期
     * @param {Date} date
     * @param {String} format
     * @returns {string}
     */
    var format = function (date, format) {


        return '';
    };
    /**
     * 解析日期字符串
     * @param {String} dateStr
     * @param {String} format
     * @returns {Date}
     */
    var parse = function (dateStr, format) {
        var nums = dateStr.match(/\d+/g);
        if (format) {
            var ap = 'yyyy-MM-dd'.match(/[a-zA-Z]+/g);
        } else {

        }
        return new Date();
    };
    /**
     *
     * @param date
     * @param dayNum
     * @returns {Date}
     */
    var getFirstDayToWeek=function(date, dayNum){
        var firstDate=new Date();


        return firstDate;
    }
})();