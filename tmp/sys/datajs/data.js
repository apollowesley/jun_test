"use strict";

(function(global, namespace) {
  /**
   * @param {(string|string[])} name
   * @returns {*}
   *
   * @param {(string|string[])} name
   * @param {*} value
   * @param {boolean} [defaultNull]
   */
  var $$data = global.$$data = function() {
    return $$data.value.bind(null, document).apply(null, arguments);
  }

  var plugins = $$data.plugins = {};
  var settings = $$data.settings = {};
  settings.nameAttributeName = 'data-name';
  settings.typeAttributeName = 'data-type';

  /**
   * 返回与指定的 name 匹配的元素的值
   * @param {Element} baseElement
   * @param {(string|string[])} name
   * @returns {*}
   *
   * 设置与指定的 name 匹配的元素的值
   * @param {Element} baseElement
   * @param {(string|string[])} name
   * @param {*} value
   * @param {boolean} [defaultNull]
   */
  $$data.value = function(baseElement, name, value, defaultNull) {
    if (baseElement == null) {
      throw new Error('argument "baseElement" is ' + baseElement);
    }

    if (name == null) {
      throw new Error('argument "name" is ' + name);
    }

    // 获取/设置一组元素的值
    if (name instanceof Array ||
        (name.length > 0 && name.charAt(name.length - 1) === '*')) {
      var elements = $$data.queryNameAll(baseElement, name);

      return arguments.length <= 2
        ? $$data.getValues(elements)
        : $$data.setValues(elements, value, defaultNull);
    }

    var element = $$data.queryName(baseElement, name);

    if (element == null) {
      return undefined;
    }

    // 获取/设置数据的值
    return arguments.length <= 2
      ? $$data.getValue(element)
      : $$data.setValue(element, value);
  };

  /**
   * 获取一组元素 elements 的值
   * @param {element[]} elements
   * @returns {Object}
   */
  $$data.getValues = function(elements) {
    if (elements == null) {
      throw new Error('argument "elements" is ' + elements);
    }

    var values = {};

    for (var i = 0; i < elements.length; i++) {
      var element = elements[i];
      var name = element.getAttribute(settings.nameAttributeName);

      values[name] = $$data.getValue(element);
    }

    return values;
  };

  /**
   * 设置一组元素 elements 的值
   * @param {element[]} elements 
   * @param {*} value
   * @param {*} value
   * @param {boolean} [defaultNull]
   */
  $$data.setValues = function(elements, value, defaultNull) {
    if (elements == null) {
      throw new Error('argument "elements" is ' + elements);
    }

    for (var i = 0; i < elements.length; i++) {
      var element = elements[i];

      // 参数 value 是函数
      if (typeof value === 'function') {
        $$data.setValue(element, value());
        continue;
      }

      // 参数 value 不是纯粹对象
      if (!$$data.isPlainObject(value)) {
        $$data.setValue(element, value);
        continue;
      }

      //
      // 参数 value 是纯粹对象，也就是 JSON 对象
      //
      var elementName = element.getAttribute(settings.nameAttributeName);
      var elementValue = value[elementName];

      if (elementValue === undefined) {
        // 是否清空没有给定值的元素的值
        if (defaultNull === true) {
          $$data.setValue(element, null);
        }
      } else {
        $$data.setValue(element, elementValue);
      }
    }
  };

  /**
   * 获取元素 element 的值
   * @param {Element} element
   * @returns {*}
   */
  $$data.getValue = function(element) {
    if (element == null) {
      throw new Error('argument "element" is ' + element);
    }

    var dataType = element.getAttribute(settings.typeAttributeName);
    var tagName = element.tagName.toLowerCase();

    if (dataType) {
      return plugins[dataType].getValue(element);
    } 

    if (plugins['$' + tagName]) {
      return plugins['$' + tagName].getValue(element);
    } 

    // 不是表单元素
    if ('input,select,textarea,button'.indexOf(tagName) < 0) {
      return element.innerHTML;
    } 

    // 下拉框
    if (tagName === 'select') {
      return $$data.getSelectValue(element);
    }

    var inputType = element.getAttribute('type');

    return (inputType == 'radio' || inputType == 'checkbox') 
      ? element.checked 
      : element.value;
  };

  /**
   * 设置元素 element 的值
   * @param {Element} element
   * @param {*} value
   */
  $$data.setValue = function(element, value) {
    if (element == null) {
      throw new Error('argument "element" is ' + element);
    }

    var dataType = element.getAttribute(settings.typeAttributeName);
    var tagName = element.tagName.toLowerCase();

    if (dataType) {
      plugins[dataType].setValue(element, value);
      return;
    } 

    if (plugins['$'+tagName]) {
      plugins['$'+tagName].setValue(element, value);
      return;
    }

    // 不是表单元素
    if ('input,select,textarea,button'.indexOf(tagName) < 0) {
      element.innerHTML = value;
      return;
    }

    // 下拉框
    if (tagName === 'select') {
      $$data.setSelectValue(element, value);
      return;
    }

    var inputType = element.getAttribute('type');

    if (inputType == 'radio' || inputType == 'checkbox') {
      element.checked= value;
    } else {
      element.value = value;
    }
  };

  /**
   * 获取下拉框 element 的值
   * @param {Element} element
   * @returns {*}
   */
  $$data.getSelectValue = function(element) {
    if (element == null) {
      throw new Error('argument "element" is ' + element);
    }

    // 单选下拉框
    if  (!element.multiple) {
      var selectedIndex = element.selectedIndex;

      return (selectedIndex !== -1) 
        ? element.item(selectedIndex).value 
        : null;
    }

    // 多选下拉框
    var selectedValues = [];

    for (var i = 0; i < element.length; i++) {
      var option = element.item(i);

      if (option.selected) {
        selectedValues.push(option.value);
      }
    }

    return selectedValues;
  }

  /**
   * 设置下拉框 element 的值
   * @param {Element} element
   * @param {*} value
   */
  $$data.setSelectValue = function(element, value) {
    if (element == null) {
      throw new Error('argument "element" is ' + element);
    }

    // 单选下拉框
    if (!element.multiple) {
      element.value = value;
      return;
    }

    // 多选下拉框
    for (var i = 0; i < element.length; i++) {
      var option = element.item(i);
      var selected = false;

      if (value instanceof Array) {
        for (var j = 0; j < value.length; j++) {
          if (value[j] == option.value) {
            selected = true;
            break;
          }
        }
      }

      option.selected = selected;
    }
  };

  /**
   * 返回文档中匹配指定的 name 的第一个元素
   * @param {Element} baseElement
   * @param {string} name
   * @returns {Element}
   */
  $$data.queryName = function(baseElement, name) {
    if (baseElement == null) {
      throw new Error('argument "baseElement" is ' + baseElement);
    }

    if (name == null) {
      throw new Error('argument "name" is ' + name);
    }

    var selector = $$data.parseName(name);
    return baseElement.querySelector(selector);
  };

  /**
   * 返回与指定的 name 匹配的文档中的元素列表
   * @param {Element} baseElement
   * @param {(string|string[])} name
   * @returns {Element[]}
   */
  $$data.queryNameAll = function(baseElement, name) {
    if (baseElement == null) {
      throw new Error('argument "baseElement" is ' + baseElement);
    }

    if (name == null) {
      throw new Error('argument "name" is ' + name);
    }

    var selector = $$data.parseName(name);
    return baseElement.querySelectorAll(selector);
  };

  /**
   * 把名称 name 转换成 CSS 选择器
   * @param {string|string[]} name
   * @returns {string}
   *
   * 转换的规则
   * "a"              => "[data-name="a"]"
   * "a*"             => "[data-name^="a"]"
   * "*"              => "[data-name]"
   * ["a", "a*", "*"] => "[data-name="a"],[data-name^="a"],[data-name]"
   */
  $$data.parseName = function(name) {
    if (name == null) {
      throw new Error('argument "name" is ' + name);
    }

    var selectors = [];
    var expressions = (name instanceof Array) 
      ? name : [name];

    for (var i = 0; i < expressions.length; i++) {
      var expression = expressions[i];
      var selector;

      if (expression === '*') { // "*"
        selector = '[' + settings.nameAttributeName + ']';
      } else if (expression.length > 1 && expression.charAt(expression.length - 1) === '*') { // "a*"
        selector = '[' + settings.nameAttributeName + '^="' + expression.slice(0, -1) + '"]';
      } else { // "a"
        selector = '[' + settings.nameAttributeName + '="' + expression + '"]';
      }

      selectors.push(selector);
    }

    return selectors.join(',');
  };

  /**
   * 判断指定参数是否是一个纯粹的对象
   * @param {*} obj
   * @returns {boolean}
   */
  $$data.isPlainObject = function(obj) {
    if (!obj || Object.prototype.toString.call(obj) !== '[object Object]' 
      || obj.constructor !== Object || hasOwnProperty.call(obj, 'constructor')) {
      return false;
    }

    var key;
    for (key in obj) {};

    return key === undefined || hasOwnProperty.call(obj, key);
  }

  /**
   * 把 value 当作基本数据类型
   * @param {*} value
   * @returns {Function}
   */
  $$data.primitive = function(value) {
    return function() {
      return value;
    };
  };

  /**
   * 给参数 obj 所有属性名加上 prefix 前缀
   * @param {Object} obj
   * @param {string} prefix
   * @returns {Object}
   */
  $$data.prefix = function(obj, prefix) {
    if (prefix == null) {
      throw new Error('argument "prefix" is ' + prefix);
    }

    if (obj === null || typeof obj !== 'object') {
      return obj;
    }

    var newObj = {};

    for (var key in obj) {
      newObj[prefix + key] = obj[key];
    }

    return newObj;
  };

  /**
   * 去掉参数 obj 所有属性名的 prefix 前缀
   * @param {Object} obj
   * @param {string} prefix
   * @returns {Object}
   */
  $$data.unprefix = function(obj, prefix) {
    if (prefix == null) {
      throw new Error('argument "prefix" is ' + prefix);
    }

    if (obj === null || typeof obj !== 'object') {
      return obj;
    }

    var newKey;
    var newObj = {};

    for (var key in obj) {
      newKey = key.indexOf(prefix) === 0 
        ? key.substring(prefix.length) 
        : key;

      newObj[newKey] = obj[key];
    }

    return newObj;
  };

  global[namespace] = $$data; // 绑定命名空间
})(window, '_');