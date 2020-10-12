/**
 * Created by guozhongqiang on 16/6/16.
 */
if (typeof tools === 'undefined') {
    var tools = {};
}
(function () {
    'use strict';

    /**
     * 返回类型字符串
     * @param obj
     * @returns {string}
     * @private
     */
    var _callString = function (obj) {
        return Object.prototype.toString.call(obj);
    };
    /**
     * 判断是否是对象Object
     * @param obj
     * @returns {*|boolean}
     */
    var isObject = function (obj) {
        return obj && _callString(obj) === '[object Object]';
    };
    /**
     * 判断是否是字符串String
     * @param str
     * @returns {boolean}
     */
    var isString = function (str) {
        return _callString(str) === '[object String]';
    };
    /**
     * 判断是否是数组Array
     * @param arr
     * @returns {boolean}
     */
    var isArray = function (arr) {
        return _callString(arr) === '[object Array]';
    };
    /**
     * 判断是否是数字Number
     * @param num
     * @returns {boolean}
     */
    var isNumber = function (num) {
        return _callString(num) === '[object Number]';
    };
    /**
     * 判断是否是函数Function
     * @param fn
     * @returns {boolean}
     */
    var isFunction = function (fn) {
        return _callString(fn) === '[object Function]';
    };
    /**
     * 判断是否是日期对象
     * @param date
     * @returns {boolean}
     */
    var isDate = function (date) {
        return _callString(date) === '[object Date]';
    };
    /**
     * 判断是否是jQuery对象
     * @param $dom
     * @returns {boolean}
     */
    var isJQuery = function ($dom) {
        return $dom instanceof jQuery;
    };
    /**
     * 判断是否是数值
     * @param num
     * @returns {boolean}
     */
    var isNumeric = function (num) {
        return /^-?\d*.?\d+$/.test(num);
    };
    /**
     * 判断是否是整数
     * @param num
     * @returns {boolean}
     */
    var isInteger = function (num) {
        return /^-?\d*$/.test(num);
    };
    /**
     * 判断是否是浮点数,小数
     * @param num
     * @returns {boolean}
     */
    var isDouble = function (num) {
        return /^-?\d*.\d+$/.test(num);
    };
    /**
     * 判断是否是负数
     * @param num
     * @returns {boolean}
     */
    var isNegative = function (num) {
        return /^-\d*.\d+$/.test(num);
    };
    /**
     * 判断是否是null对象
     * @param obj
     * @returns {boolean}
     */
    var isNull = function (obj) {
        return _callString(obj) === '[object Null]';
    };
    /**
     * 判断对象是否已定义
     * @param obj
     * @returns {boolean}
     */
    var isUndefined = function (obj) {
        return _callString(obj) === '[object Undefined]';
    };
    /**
     * 克隆对象,默认浅克隆
     * @param obj
     * @param isDeep 是否是深克隆
     * @returns {*}
     * @private
     */
    var clone = function (obj, isDeep) {
        if (isArray(obj) || isObject(obj)) {
            var newObj = (isObject(obj) ? {} : []);
            each(obj, function (v, k) {
                if (isDeep) {
                    newObj[k] = clone(v);
                } else {
                    newObj[k] = v;
                }
            });
        } else if (isFunction(obj)) {
            return function () {
                var context = this,
                    args = arguments;
                obj.apply(context, args);
            };
        }
        return obj;
    };
    /**
     * 深克隆 Deep Clone
     * @param obj
     * @returns {*}
     */
    var deepClone = function (obj) {
        return clone(obj, true);
    };

    //浅克隆
    var shallowClone = function (obj) {
        return clone(obj, false);
    };
    /**
     * 遍历数组和对象
     * @param obj
     * @param callBack
     */
    var each = function (obj, callBack) {
        if (isFunction(callBack)) {
            if (isArray(obj) || isJQuery(obj)) {
                var len = obj.length,
                    i = 0;
                for (; i < len; i++) {
                    if (callBack(obj[i], i, obj) === false) {
                        break;
                    }
                }
            } else if (isObject(obj)) {
                var keys = Object.keys(obj),
                    len = keys.length,
                    i = 0,
                    key = '';
                for (; i < len; i++) {
                    key = keys[i];
                    if (callBack(obj[key], key, obj) === false) {
                        break;
                    }
                }
            }
        }
    };
    tools = {
        isObject: isObject,
        isString: isString,
        isNumber: isNumber,
        isFunction: isFunction,
        isNumeric: isNumeric,
        isInteger: isInteger,
        isDouble: isDouble,
        isNegative: isNegative,
        isNull: isNull,
        isUndefined: isUndefined,
        isJQuery: isJQuery,
        clone: clone,
        deepClone: deepClone,
        shallowClone: shallowClone,
        each: each
    };
})();