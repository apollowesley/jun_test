/* * * * * * * * * * * * 
 * 
 * 统一地图api api按照百度的设计来
 * 目前支持地图
 * BMap AMap 不该变原有api 附加百度api实现
 * 刘璐制作
 * * * * * * * * * * * */

// 统一地图api
+function(window){
  var LMap = new Object();
  LMap.currentMap = '-';
  LMap.currentMapVersion = '0';
  LMap.detect = function(){
    if(typeof BMap == 'object'){
      LMap.currentMap = 'BMap';
      LMap.currentMapVersion = BMAP_API_VERSION;
      LMap.baiduInit();
      // 解决鼠标漂移bug
      new BMap.Polyline([new LMap.Point(116.404, 39.905),new LMap.Point(116.414, 39.905)]).hide();
    }else if(typeof AMap == 'object'){
      LMap.currentMap = 'AMap';
      LMap.gaodeInit();
    }
  }
  
  /**
   * 将源对象的所有属性拷贝到目标对象中
   * @name baidu.extend
   * @function
   * @grammar baidu.extend(target, source)
   * @param {Object} target 目标对象
   * @param {Object} source 源对象
   * @returns {Object} 目标对象
   */
  LMap.__extend = function (target, source) {
    for (var p in source) {
      if (source.hasOwnProperty(p)) {
        target[p] = source[p];
      }
    }    
    return target;
  };
  
  //百度api转接 常量需要重新使用LMAP代替BMAP
  // 需要增加的功能 测距 侧面 拉框放大 区域查询 画区域（可以吸附的区域）
  LMap.baiduInit = function(){
    
    /*工具准备*/
    var baidu = baidu || {guid:"$BAIDU$"};
    +function(){
      // 一些页面级别的唯一属性 需要挂载到window[baidu.guid]上
      window[baidu.guid] = {};
      
      
      /**
       * @ignore
       * @namespace
       * @baidu.lang 对语言层面的封装，包括类型判断、模块扩展、继承基类以及对象自定义事件的支持。
       * @property guid 对象的唯一标识
       */
      baidu.lang = baidu.lang || {};

      /**
       * 返回一个当前页面的唯一标识字符串。
       * @function
       * @grammar baidu.lang.guid()
       * @returns {String} 当前页面的唯一标识字符串
       */
      baidu.lang.guid = function() {
          return "TANGRAM__" + (window[baidu.guid]._counter ++).toString(36);
      };

      window[baidu.guid]._counter = window[baidu.guid]._counter || 1;

      /**
       * 所有类的实例的容器
       * key为每个实例的guid
       */
      window[baidu.guid]._instances = window[baidu.guid]._instances || {};

      /**
       * Tangram继承机制提供的一个基类，用户可以通过继承baidu.lang.Class来获取它的属性及方法。
       * @function
       * @name baidu.lang.Class
       * @grammar baidu.lang.Class(guid)
       * @param {string} guid 对象的唯一标识
       * @meta standard
       * @remark baidu.lang.Class和它的子类的实例均包含一个全局唯一的标识guid。
       * guid是在构造函数中生成的，因此，继承自baidu.lang.Class的类应该直接或者间接调用它的构造函数。<br>
       * baidu.lang.Class的构造函数中产生guid的方式可以保证guid的唯一性，及每个实例都有一个全局唯一的guid。
       */
      baidu.lang.Class = function(guid) {
          this.guid = guid || baidu.lang.guid();
          window[baidu.guid]._instances[this.guid] = this;
      };

      //window[baidu.guid]._instances = window[baidu.guid]._instances || {};

      /**
       * 判断目标参数是否string类型或String对象
       * @name baidu.lang.isString
       * @function
       * @grammar baidu.lang.isString(source)
       * @param {Any} source 目标参数
       * @shortcut isString
       * @meta standard
       *             
       * @returns {boolean} 类型判断结果
       */
      baidu.lang.isString = function (source) {
          return '[object String]' == Object.prototype.toString.call(source);
      };

      /**
       * 判断目标参数是否为function或Function实例
       * @name baidu.lang.isFunction
       * @function
       * @grammar baidu.lang.isFunction(source)
       * @param {Any} source 目标参数
       * @returns {boolean} 类型判断结果
       */
      baidu.lang.isFunction = function (source) {
          return '[object Function]' == Object.prototype.toString.call(source);
      };

      /**
       * 重载了默认的toString方法，使得返回信息更加准确一些。
       * @return {string} 对象的String表示形式
       */
      baidu.lang.Class.prototype.toString = function(){
          return "[object " + (this._className || "Object" ) + "]";
      };

      /**
       * 释放对象所持有的资源，主要是自定义事件。
       * @name dispose
       * @grammar obj.dispose()
       */
      baidu.lang.Class.prototype.dispose = function(){
          delete window[baidu.guid]._instances[this.guid];
          for(var property in this){
              if (!baidu.lang.isFunction(this[property])) {
                  delete this[property];
              }
          }
          this.disposed = true;
      };

      /**
       * 自定义的事件对象。
       * @function
       * @name baidu.lang.Event
       * @grammar baidu.lang.Event(type[, target])
       * @param {string} type  事件类型名称。为了方便区分事件和一个普通的方法，事件类型名称必须以"on"(小写)开头。
       * @param {Object} [target]触发事件的对象
       * @meta standard
       * @remark 引入该模块，会自动为Class引入3个事件扩展方法：addEventListener、removeEventListener和dispatchEvent。
       * @see baidu.lang.Class
       */
      baidu.lang.Event = function (type, target) {
          this.type = type;
          this.returnValue = true;
          this.target = target || null;
          this.currentTarget = null;
      };

      /**
       * 注册对象的事件监听器。引入baidu.lang.Event后，Class的子类实例才会获得该方法。
       * @grammar obj.addEventListener(type, handler[, key])
       * @param   {string}   type         自定义事件的名称
       * @param   {Function} handler      自定义事件被触发时应该调用的回调函数
       * @param   {string}   [key]        为事件监听函数指定的名称，可在移除时使用。如果不提供，方法会默认为它生成一个全局唯一的key。
       * @remark  事件类型区分大小写。如果自定义事件名称不是以小写"on"开头，该方法会给它加上"on"再进行判断，即"click"和"onclick"会被认为是同一种事件。 
       */
      baidu.lang.Class.prototype.addEventListener = function (type, handler, key) {
          if (!baidu.lang.isFunction(handler)) {
              return;
          }
          !this.__listeners && (this.__listeners = {});
          var t = this.__listeners, id;
          if (typeof key == "string" && key) {
              if (/[^\w\-]/.test(key)) {
                  throw("nonstandard key:" + key);
              } else {
                  handler.hashCode = key; 
                  id = key;
              }
          }
          type.indexOf("on") != 0 && (type = "on" + type);
          typeof t[type] != "object" && (t[type] = {});
          id = id || baidu.lang.guid();
          handler.hashCode = id;
          t[type][id] = handler;
      };

      /**
       * 移除对象的事件监听器。引入baidu.lang.Event后，Class的子类实例才会获得该方法。
       * @grammar obj.removeEventListener(type, handler)
       * @param {string}   type     事件类型
       * @param {Function|string} handler  要移除的事件监听函数或者监听函数的key
       * @remark  如果第二个参数handler没有被绑定到对应的自定义事件中，什么也不做。
       */
      baidu.lang.Class.prototype.removeEventListener = function (type, handler) {
          if (baidu.lang.isFunction(handler)) {
              handler = handler.hashCode;
          } else if (!baidu.lang.isString(handler)) {
              return;
          }
          !this.__listeners && (this.__listeners = {});
          type.indexOf("on") != 0 && (type = "on" + type);
          var t = this.__listeners;
          if (!t[type]) {
              return;
          }
          t[type][handler] && delete t[type][handler];
      };

      /**
       * 派发自定义事件，使得绑定到自定义事件上面的函数都会被执行。引入baidu.lang.Event后，Class的子类实例才会获得该方法。
       * @grammar obj.dispatchEvent(event, options)
       * @param {baidu.lang.Event|String} event   Event对象，或事件名称(1.1.1起支持)
       * @param {Object} options 扩展参数,所含属性键值会扩展到Event对象上(1.2起支持)
       * @remark 处理会调用通过addEventListenr绑定的自定义事件回调函数之外，还会调用直接绑定到对象上面的自定义事件。
       * 例如：<br>
       * myobj.onMyEvent = function(){}<br>
       * myobj.addEventListener("onMyEvent", function(){});
       */
      baidu.lang.Class.prototype.dispatchEvent = function (event, options) {
          if (baidu.lang.isString(event)) {
              event = new baidu.lang.Event(event);
          }
          !this.__listeners && (this.__listeners = {});
          options = options || {};
          for (var i in options) {
              event[i] = options[i];
          }
          var i, t = this.__listeners, p = event.type;
          event.target = event.target || this;
          event.currentTarget = this;
          p.indexOf("on") != 0 && (p = "on" + p);
          baidu.lang.isFunction(this[p]) && this[p].apply(this, arguments);
          if (typeof t[p] == "object") {
              for (i in t[p]) {
                  t[p][i].apply(this, arguments);
              }
          }
          return event.returnValue;
      };

      /**
       * 为类型构造器建立继承关系
       * @name baidu.lang.inherits
       * @function
       * @grammar baidu.lang.inherits(subClass, superClass[, className])
       * @param {Function} subClass 子类构造器
       * @param {Function} superClass 父类构造器
       * @param {string} className 类名标识
       * @remark 使subClass继承superClass的prototype，
       * 因此subClass的实例能够使用superClass的prototype中定义的所有属性和方法。<br>
       * 这个函数实际上是建立了subClass和superClass的原型链集成，并对subClass进行了constructor修正。<br>
       * <strong>注意：如果要继承构造函数，需要在subClass里面call一下，具体见下面的demo例子</strong>
       * @shortcut inherits
       * @meta standard
       * @see baidu.lang.Class
       */
      baidu.lang.inherits = function (subClass, superClass, className) {
          var key, proto, 
              selfProps = subClass.prototype, 
              clazz = new Function();        
          clazz.prototype = superClass.prototype;
          proto = subClass.prototype = new clazz();
          for (key in selfProps) {
              proto[key] = selfProps[key];
          }
          subClass.prototype.constructor = subClass;
          subClass.superClass = superClass.prototype;

          if ("string" == typeof className) {
              proto._className = className;
          }
      };

      /**
       * @ignore
       * @namespace baidu.dom 操作dom的方法。
       */
      baidu.dom = baidu.dom || {};

      /**
       * 从文档中获取指定的DOM元素
       * 
       * @param {string|HTMLElement} id 元素的id或DOM元素
       * @meta standard
       * @return {HTMLElement} DOM元素，如果不存在，返回null，如果参数不合法，直接返回参数
       */
      baidu._g = baidu.dom._g = function (id) {
          if (baidu.lang.isString(id)) {
              return document.getElementById(id);
          }
          return id;
      };

      /**
       * 从文档中获取指定的DOM元素
       * @name baidu.dom.g
       * @function
       * @grammar baidu.dom.g(id)
       * @param {string|HTMLElement} id 元素的id或DOM元素
       * @meta standard
       *             
       * @returns {HTMLElement|null} 获取的元素，查找不到时返回null,如果参数不合法，直接返回参数
       */
      baidu.g = baidu.dom.g = function (id) {
          if ('string' == typeof id || id instanceof String) {
              return document.getElementById(id);
          } else if (id && id.nodeName && (id.nodeType == 1 || id.nodeType == 9)) {
              return id;
          }
          return null;
      };

      /**
       * 在目标元素的指定位置插入HTML代码
       * @name baidu.dom.insertHTML
       * @function
       * @grammar baidu.dom.insertHTML(element, position, html)
       * @param {HTMLElement|string} element 目标元素或目标元素的id
       * @param {string} position 插入html的位置信息，取值为beforeBegin,afterBegin,beforeEnd,afterEnd
       * @param {string} html 要插入的html
       * @remark
       * 
       * 对于position参数，大小写不敏感<br>
       * 参数的意思：beforeBegin&lt;span&gt;afterBegin   this is span! beforeEnd&lt;/span&gt; afterEnd <br />
       * 此外，如果使用本函数插入带有script标签的HTML字符串，script标签对应的脚本将不会被执行。
       * 
       * @shortcut insertHTML
       * @meta standard
       *             
       * @returns {HTMLElement} 目标元素
       */
      baidu.insertHTML = baidu.dom.insertHTML = function (element, position, html) {
          element = baidu.dom.g(element);
          var range,begin;

          if (element.insertAdjacentHTML) {
              element.insertAdjacentHTML(position, html);
          } else {
              // 这里不做"undefined" != typeof(HTMLElement) && !window.opera判断，其它浏览器将出错？！
              // 但是其实做了判断，其它浏览器下等于这个函数就不能执行了
              range = element.ownerDocument.createRange();
              // FF下range的位置设置错误可能导致创建出来的fragment在插入dom树之后html结构乱掉
              // 改用range.insertNode来插入html, by wenyuxiang @ 2010-12-14.
              position = position.toUpperCase();
              if (position == 'AFTERBEGIN' || position == 'BEFOREEND') {
                  range.selectNodeContents(element);
                  range.collapse(position == 'AFTERBEGIN');
              } else {
                  begin = position == 'BEFOREBEGIN';
                  range[begin ? 'setStartBefore' : 'setEndAfter'](element);
                  range.collapse(begin);
              }
              range.insertNode(range.createContextualFragment(html));
          }
          return element;
      };

      /**
       * 为目标元素添加className
       * @name baidu.dom.addClass
       * @function
       * @grammar baidu.dom.addClass(element, className)
       * @param {HTMLElement|string} element 目标元素或目标元素的id
       * @param {string} className 要添加的className，允许同时添加多个class，中间使用空白符分隔
       * @remark
       * 使用者应保证提供的className合法性，不应包含不合法字符，className合法字符参考：http://www.w3.org/TR/CSS2/syndata.html。
       * @shortcut addClass
       * @meta standard
       *              
       * @returns {HTMLElement} 目标元素
       */
      baidu.ac = baidu.dom.addClass = function (element, className) {
          element = baidu.dom.g(element);
          var classArray = className.split(/\s+/),
              result = element.className,
              classMatch = " " + result + " ",
              i = 0,
              l = classArray.length;

          for (; i < l; i++){
               if ( classMatch.indexOf( " " + classArray[i] + " " ) < 0 ) {
                   result += (result ? ' ' : '') + classArray[i];
               }
          }

          element.className = result;
          return element;
      };

      /**
       * @ignore
       * @namespace baidu.event 屏蔽浏览器差异性的事件封装。
       * @property target     事件的触发元素
       * @property pageX      鼠标事件的鼠标x坐标
       * @property pageY      鼠标事件的鼠标y坐标
       * @property keyCode    键盘事件的键值
       */
      baidu.event = baidu.event || {};

      /**
       * 事件监听器的存储表
       * @private
       * @meta standard
       */
      baidu.event._listeners = baidu.event._listeners || [];

      /**
       * 为目标元素添加事件监听器
       * @name baidu.event.on
       * @function
       * @grammar baidu.event.on(element, type, listener)
       * @param {HTMLElement|string|window} element 目标元素或目标元素id
       * @param {string} type 事件类型
       * @param {Function} listener 需要添加的监听器
       * @remark
       *  1. 不支持跨浏览器的鼠标滚轮事件监听器添加<br>
       *  2. 改方法不为监听器灌入事件对象，以防止跨iframe事件挂载的事件对象获取失败            
       * @shortcut on
       * @meta standard
       * @see baidu.event.un
       *             
       * @returns {HTMLElement|window} 目标元素
       */
      baidu.on = baidu.event.on = function (element, type, listener) {
          type = type.replace(/^on/i, '');
          element = baidu._g(element);
          var realListener = function (ev) {
              // 1. 这里不支持EventArgument,  原因是跨frame的事件挂载
              // 2. element是为了修正this
              listener.call(element, ev);
          },
          lis = baidu.event._listeners,
          filter = baidu.event._eventFilter,
          afterFilter,
          realType = type;
          type = type.toLowerCase();
          // filter过滤
          if(filter && filter[type]){
              afterFilter = filter[type](element, type, realListener);
              realType = afterFilter.type;
              realListener = afterFilter.listener;
          }
          // 事件监听器挂载
          if (element.addEventListener) {
              element.addEventListener(realType, realListener, false);
          } else if (element.attachEvent) {
              element.attachEvent('on' + realType, realListener);
          }

          // 将监听器存储到数组中
          lis[lis.length] = [element, type, listener, realListener, realType];
          return element;
      };

      /**
       * 为目标元素移除事件监听器
       * @name baidu.event.un
       * @function
       * @grammar baidu.event.un(element, type, listener)
       * @param {HTMLElement|string|window} element 目标元素或目标元素id
       * @param {string} type 事件类型
       * @param {Function} listener 需要移除的监听器
       * @shortcut un
       * @meta standard
       *             
       * @returns {HTMLElement|window} 目标元素
       */
      baidu.un = baidu.event.un = function (element, type, listener) {
          element = baidu._g(element);
          type = type.replace(/^on/i, '').toLowerCase();

          var lis = baidu.event._listeners, 
              len = lis.length,
              isRemoveAll = !listener,
              item,
              realType, realListener;

          //如果将listener的结构改成json
          //可以节省掉这个循环，优化性能
          //但是由于un的使用频率并不高，同时在listener不多的时候
          //遍历数组的性能消耗不会对代码产生影响
          //暂不考虑此优化
          while (len--) {
              item = lis[len];

              // listener存在时，移除element的所有以listener监听的type类型事件
              // listener不存在时，移除element的所有type类型事件
              if (item[1] === type
                  && item[0] === element
                  && (isRemoveAll || item[2] === listener)) {
                  realType = item[4];
                  realListener = item[3];
                  if (element.removeEventListener) {
                      element.removeEventListener(realType, realListener, false);
                  } else if (element.detachEvent) {
                      element.detachEvent('on' + realType, realListener);
                  }
                  lis.splice(len, 1);
              }
          }            
          return element;
      };

      /**
       * 获取event事件,解决不同浏览器兼容问题
       * @param {Event}
       * @return {Event}
       */
      baidu.getEvent = baidu.event.getEvent = function (event) {
          return window.event || event;
      }

      /**
       * 获取event.target,解决不同浏览器兼容问题
       * @param {Event}
       * @return {Target}
       */
      baidu.getTarget = baidu.event.getTarget = function (event) {
          var event = baidu.getEvent(event);
          return event.target || event.srcElement;
      }

      /**
       * 阻止事件的默认行为
       * @name baidu.event.preventDefault
       * @function
       * @grammar baidu.event.preventDefault(event)
       * @param {Event} event 事件对象
       * @meta standard
       */
      baidu.preventDefault = baidu.event.preventDefault = function (event) {
         var event = baidu.getEvent(event);
         if (event.preventDefault) {
             event.preventDefault();
         } else {
             event.returnValue = false;
         }
      };

      /**
       * 停止事件冒泡传播
       * @param {Event}
       */
      baidu.stopBubble = baidu.event.stopBubble = function (event) {
          event = baidu.getEvent(event);
          event.stopPropagation ? event.stopPropagation() : event.cancelBubble = true;
      }

      baidu.browser = baidu.browser || {};

      if (/msie (\d+\.\d)/i.test(navigator.userAgent)) {
        //IE 8下，以documentMode为准
        //在百度模板中，可能会有$，防止冲突，将$1 写成 \x241
        /**
         * 判断是否为ie浏览器
         * @property ie ie版本号
         * @grammar baidu.browser.ie
         * @meta standard
         * @shortcut ie
         * @see baidu.browser.firefox,baidu.browser.safari,baidu.browser.opera,baidu.browser.chrome,baidu.browser.maxthon 
         */
         baidu.browser.ie = baidu.ie = document.documentMode || + RegExp['\x241'];
      }
       
      
    }();
    
    /*常量*/
    // 常量 地球半径
    LMap.LMAP_EARTH_RADIUS = 6370996.81;
    /*类*/
    /*GeoTool开始*/
    function GeoTool(){ }
    /**
     * 判断点是否在矩形内
     * @param {Point} point 点对象
     * @param {Bounds} bounds 矩形边界对象
     * @returns {Boolean} 点在矩形内返回true,否则返回false
     */
    GeoTool.isPointInRect = function(point, bounds){
        //检查类型是否正确
        if (!(point instanceof BMap.Point) || 
            !(bounds instanceof BMap.Bounds)) {
            return false;
        }
        var sw = bounds.getSouthWest(); //西南脚点
        var ne = bounds.getNorthEast(); //东北脚点
        return (point.lng >= sw.lng && point.lng <= ne.lng && point.lat >= sw.lat && point.lat <= ne.lat);
    };
    /**
     * 判断点是否在圆形内
     * @param {Point} point 点对象
     * @param {Circle} circle 圆形对象
     * @returns {Boolean} 点在圆形内返回true,否则返回false
     */
    GeoTool.isPointInCircle = function(point, circle){
        //检查类型是否正确
        if (!(point instanceof BMap.Point) || 
            !(circle instanceof BMap.Circle)) {
            return false;
        }

        //point与圆心距离小于圆形半径，则点在圆内，否则在圆外
        var c = circle.getCenter();
        var r = circle.getRadius();

        var dis = GeoTool.getDistance(point, c);
        if(dis <= r){
            return true;
        } else {
            return false;
        }
    }
    /**
     * 判断点是否在折线上
     * @param {Point} point 点对象
     * @param {Polyline} polyline 折线对象
     * @returns {Boolean} 点在折线上返回true,否则返回false
     */
    GeoTool.isPointOnPolyline = function(point, polyline){
        //检查类型
        if(!(point instanceof BMap.Point) ||
            !(polyline instanceof BMap.Polyline)){
            return false;
        }

        //首先判断点是否在线的外包矩形内，如果在，则进一步判断，否则返回false
        var lineBounds = polyline.getBounds();
        if(!this.isPointInRect(point, lineBounds)){
            return false;
        }

        //判断点是否在线段上，设点为Q，线段为P1P2 ，
        //判断点Q在该线段上的依据是：( Q - P1 ) × ( P2 - P1 ) = 0，且 Q 在以 P1，P2为对角顶点的矩形内
        var pts = polyline.getPath();
        for(var i = 0; i < pts.length - 1; i++){
            var curPt = pts[i];
            var nextPt = pts[i + 1];
            //首先判断point是否在curPt和nextPt之间，即：此判断该点是否在该线段的外包矩形内
            if (point.lng >= Math.min(curPt.lng, nextPt.lng) && point.lng <= Math.max(curPt.lng, nextPt.lng) &&
                point.lat >= Math.min(curPt.lat, nextPt.lat) && point.lat <= Math.max(curPt.lat, nextPt.lat)){
                //判断点是否在直线上公式
                var precision = (curPt.lng - point.lng) * (nextPt.lat - point.lat) - 
                    (nextPt.lng - point.lng) * (curPt.lat - point.lat);                
                if(precision < 2e-10 && precision > -2e-10){//实质判断是否接近0
                    return true;
                }                
            }
        }
        
        return false;
    }
    
    /**
     * 判断点是否多边形内
     * @param {Point} point 点对象
     * @param {Polyline} polygon 多边形对象
     * @returns {Boolean} 点在多边形内返回true,否则返回false
     */
    GeoTool.isPointInPolygon = function(point, polygon){
        //检查类型
        if(!(point instanceof BMap.Point) ||
            !(polygon instanceof BMap.Polygon)){
            return false;
        }

        //首先判断点是否在多边形的外包矩形内，如果在，则进一步判断，否则返回false
        var polygonBounds = polygon.getBounds();
        if(!this.isPointInRect(point, polygonBounds)){
            return false;
        }

        var pts = polygon.getPath();//获取多边形点
        
        //下述代码来源：http://paulbourke.net/geometry/insidepoly/，进行了部分修改
        //基本思想是利用射线法，计算射线与多边形各边的交点，如果是偶数，则点在多边形外，否则
        //在多边形内。还会考虑一些特殊情况，如点在多边形顶点上，点在多边形边上等特殊情况。
        
        var N = pts.length;
        var boundOrVertex = true; //如果点位于多边形的顶点或边上，也算做点在多边形内，直接返回true
        var intersectCount = 0;//cross points count of x 
        var precision = 2e-10; //浮点类型计算时候与0比较时候的容差
        var p1, p2;//neighbour bound vertices
        var p = point; //测试点
        
        p1 = pts[0];//left vertex        
        for(var i = 1; i <= N; ++i){//check all rays            
            if(p.equals(p1)){
                return boundOrVertex;//p is an vertex
            }
            
            p2 = pts[i % N];//right vertex            
            if(p.lat < Math.min(p1.lat, p2.lat) || p.lat > Math.max(p1.lat, p2.lat)){//ray is outside of our interests                
                p1 = p2; 
                continue;//next ray left point
            }
            
            if(p.lat > Math.min(p1.lat, p2.lat) && p.lat < Math.max(p1.lat, p2.lat)){//ray is crossing over by the algorithm (common part of)
                if(p.lng <= Math.max(p1.lng, p2.lng)){//x is before of ray                    
                    if(p1.lat == p2.lat && p.lng >= Math.min(p1.lng, p2.lng)){//overlies on a horizontal ray
                        return boundOrVertex;
                    }
                    
                    if(p1.lng == p2.lng){//ray is vertical                        
                        if(p1.lng == p.lng){//overlies on a vertical ray
                            return boundOrVertex;
                        }else{//before ray
                            ++intersectCount;
                        } 
                    }else{//cross point on the left side                        
                        var xinters = (p.lat - p1.lat) * (p2.lng - p1.lng) / (p2.lat - p1.lat) + p1.lng;//cross point of lng                        
                        if(Math.abs(p.lng - xinters) < precision){//overlies on a ray
                            return boundOrVertex;
                        }
                        
                        if(p.lng < xinters){//before ray
                            ++intersectCount;
                        } 
                    }
                }
            }else{//special case when ray is crossing through the vertex                
                if(p.lat == p2.lat && p.lng <= p2.lng){//p crossing over p2                    
                    var p3 = pts[(i+1) % N]; //next vertex                    
                    if(p.lat >= Math.min(p1.lat, p3.lat) && p.lat <= Math.max(p1.lat, p3.lat)){//p.lat lies between p1.lat & p3.lat
                        ++intersectCount;
                    }else{
                        intersectCount += 2;
                    }
                }
            }            
            p1 = p2;//next ray left point
        }
        
        if(intersectCount % 2 == 0){//偶数在多边形外
            return false;
        } else { //奇数在多边形内
            return true;
        }            
    }

    /**
     * 将度转化为弧度
     * @param {degree} Number 度     
     * @returns {Number} 弧度
     */
    GeoTool.degreeToRad =  function(degree){
        return Math.PI * degree/180;    
    }
    
    /**
     * 将弧度转化为度
     * @param {radian} Number 弧度     
     * @returns {Number} 度
     */
    GeoTool.radToDegree = function(rad){
        return (180 * rad) / Math.PI;       
    }
    
    /**
     * 将v值限定在a,b之间，纬度使用
     */
    function _getRange(v, a, b){
        if(a != null){
          v = Math.max(v, a);
        }
        if(b != null){
          v = Math.min(v, b);
        }
        return v;
    }
    
    /**
     * 将v值限定在a,b之间，经度使用
     */
    function _getLoop(v, a, b){
        while( v > b){
          v -= b - a
        }
        while(v < a){
          v += b - a
        }
        return v;
    }

    /**
     * 计算两点之间的距离,两点坐标必须为经纬度
     * @param {point1} Point 点对象
     * @param {point2} Point 点对象
     * @returns {Number} 两点之间距离，单位为米
     */
    GeoTool.getDistance = function(point1, point2){
        //判断类型
        if(!(point1 instanceof BMap.Point) ||
            !(point2 instanceof BMap.Point)){
            return 0;
        }

        point1.lng = _getLoop(point1.lng, -180, 180);
        point1.lat = _getRange(point1.lat, -74, 74);
        point2.lng = _getLoop(point2.lng, -180, 180);
        point2.lat = _getRange(point2.lat, -74, 74);
        
        var x1, x2, y1, y2;
        x1 = GeoTool.degreeToRad(point1.lng);
        y1 = GeoTool.degreeToRad(point1.lat);
        x2 = GeoTool.degreeToRad(point2.lng);
        y2 = GeoTool.degreeToRad(point2.lat);

        return LMap.LMAP_EARTH_RADIUS * Math.acos((Math.sin(y1) * Math.sin(y2) + Math.cos(y1) * Math.cos(y2) * Math.cos(x2 - x1)));    
    }
    
    /**
     * 计算两点之间的距离,两点坐标必须为经纬度
     * @param {pixel1} Pixel 点对象
     * @param {pixel2} Pixel 点对象
     * @returns {Number} 两点之间距离，单位为像素
     */
    GeoTool.getPixelDistance = function(point1, point2){
        //判断类型
        if(!(point1 instanceof BMap.Pixel) ||
            !(point2 instanceof BMap.Pixel)){
            return 0;
        }
        
        var xdiff = point2.x - point1.x;
        var ydiff = point2.y - point1.y;
      
        return Math.pow((xdiff * xdiff + ydiff * ydiff), 0.5);
    }
    
    /**
     * 计算折线或者点数组的长度
     * @param {Polyline|Array<Point>} polyline 折线对象或者点数组
     * @returns {Number} 折线或点数组对应的长度
     */
    GeoTool.getPolylineDistance = function(polyline){
        //检查类型
        if(polyline instanceof BMap.Polyline || 
            polyline instanceof Array){
            //将polyline统一为数组
            var pts;
            if(polyline instanceof BMap.Polyline){
                pts = polyline.getPath();
            } else {
                pts = polyline;
            }
            
            if(pts.length < 2){//小于2个点，返回0
                return 0;
            }

            //遍历所有线段将其相加，计算整条线段的长度
            var totalDis = 0;
            for(var i =0; i < pts.length - 1; i++){
                var curPt = pts[i];
                var nextPt = pts[i + 1]
                var dis = GeoTool.getDistance(curPt, nextPt);
                totalDis += dis;
            }

            return totalDis;
            
        } else {
            return 0;
        }
    }
    
    /**
     * 计算多边形面或点数组构建图形的面积,注意：坐标类型只能是经纬度，且不适合计算自相交多边形的面积
     * @param {Polygon|Array<Point>} polygon 多边形面对象或者点数组
     * @returns {Number} 多边形面或点数组构成图形的面积
     */
    GeoTool.getPolygonArea = function(polygon){
        //检查类型
        if(!(polygon instanceof BMap.Polygon) &&
            !(polygon instanceof Array)){
            return 0;
        }
        var pts;
        if(polygon instanceof BMap.Polygon){
            pts = polygon.getPath();
        }else{
            pts = polygon;    
        }
        
        if(pts.length < 3){//小于3个顶点，不能构建面
            return 0;
        }
        
        var totalArea = 0;//初始化总面积
        var LowX = 0.0;
        var LowY = 0.0;
        var MiddleX = 0.0;
        var MiddleY = 0.0;
        var HighX = 0.0;
        var HighY = 0.0;
        var AM = 0.0;
        var BM = 0.0;
        var CM = 0.0;
        var AL = 0.0;
        var BL = 0.0;
        var CL = 0.0;
        var AH = 0.0;
        var BH = 0.0;
        var CH = 0.0;
        var CoefficientL = 0.0;
        var CoefficientH = 0.0;
        var ALtangent = 0.0;
        var BLtangent = 0.0;
        var CLtangent = 0.0;
        var AHtangent = 0.0;
        var BHtangent = 0.0;
        var CHtangent = 0.0;
        var ANormalLine = 0.0;
        var BNormalLine = 0.0;
        var CNormalLine = 0.0;
        var OrientationValue = 0.0;
        var AngleCos = 0.0;
        var Sum1 = 0.0;
        var Sum2 = 0.0;
        var Count2 = 0;
        var Count1 = 0;
        var Sum = 0.0;
        var Radius = LMap.LMAP_EARTH_RADIUS; //6378137.0,WGS84椭球半径 
        var Count = pts.length;        
        for (var i = 0; i < Count; i++) {
            if (i == 0) {
                LowX = pts[Count - 1].lng * Math.PI / 180;
                LowY = pts[Count - 1].lat * Math.PI / 180;
                MiddleX = pts[0].lng * Math.PI / 180;
                MiddleY = pts[0].lat * Math.PI / 180;
                HighX = pts[1].lng * Math.PI / 180;
                HighY = pts[1].lat * Math.PI / 180;
            }
            else if (i == Count - 1) {
                LowX = pts[Count - 2].lng * Math.PI / 180;
                LowY = pts[Count - 2].lat * Math.PI / 180;
                MiddleX = pts[Count - 1].lng * Math.PI / 180;
                MiddleY = pts[Count - 1].lat * Math.PI / 180;
                HighX = pts[0].lng * Math.PI / 180;
                HighY = pts[0].lat * Math.PI / 180;
            }
            else {
                LowX = pts[i - 1].lng * Math.PI / 180;
                LowY = pts[i - 1].lat * Math.PI / 180;
                MiddleX = pts[i].lng * Math.PI / 180;
                MiddleY = pts[i].lat * Math.PI / 180;
                HighX = pts[i + 1].lng * Math.PI / 180;
                HighY = pts[i + 1].lat * Math.PI / 180;
            }
            AM = Math.cos(MiddleY) * Math.cos(MiddleX);
            BM = Math.cos(MiddleY) * Math.sin(MiddleX);
            CM = Math.sin(MiddleY);
            AL = Math.cos(LowY) * Math.cos(LowX);
            BL = Math.cos(LowY) * Math.sin(LowX);
            CL = Math.sin(LowY);
            AH = Math.cos(HighY) * Math.cos(HighX);
            BH = Math.cos(HighY) * Math.sin(HighX);
            CH = Math.sin(HighY);
            CoefficientL = (AM * AM + BM * BM + CM * CM) / (AM * AL + BM * BL + CM * CL);
            CoefficientH = (AM * AM + BM * BM + CM * CM) / (AM * AH + BM * BH + CM * CH);
            ALtangent = CoefficientL * AL - AM;
            BLtangent = CoefficientL * BL - BM;
            CLtangent = CoefficientL * CL - CM;
            AHtangent = CoefficientH * AH - AM;
            BHtangent = CoefficientH * BH - BM;
            CHtangent = CoefficientH * CH - CM;
            AngleCos = (AHtangent * ALtangent + BHtangent * BLtangent + CHtangent * CLtangent) / (Math.sqrt(AHtangent * AHtangent + BHtangent * BHtangent + CHtangent * CHtangent) * Math.sqrt(ALtangent * ALtangent + BLtangent * BLtangent + CLtangent * CLtangent));
            AngleCos = Math.acos(AngleCos);            
            ANormalLine = BHtangent * CLtangent - CHtangent * BLtangent;
            BNormalLine = 0 - (AHtangent * CLtangent - CHtangent * ALtangent);
            CNormalLine = AHtangent * BLtangent - BHtangent * ALtangent;
            if (AM != 0)
                OrientationValue = ANormalLine / AM;
            else if (BM != 0)
                OrientationValue = BNormalLine / BM;
            else
                OrientationValue = CNormalLine / CM;
            if (OrientationValue > 0) {
                Sum1 += AngleCos;
                Count1++;
            }
            else {
                Sum2 += AngleCos;
                Count2++;
            }
        }        
        var tempSum1, tempSum2;
        tempSum1 = Sum1 + (2 * Math.PI * Count2 - Sum2);
        tempSum2 = (2 * Math.PI * Count1 - Sum1) + Sum2;
        if (Sum1 > Sum2) {
            if ((tempSum1 - (Count - 2) * Math.PI) < 1)
                Sum = tempSum1;
            else
                Sum = tempSum2;
        }
        else {
            if ((tempSum2 - (Count - 2) * Math.PI) < 1)
                Sum = tempSum2;
            else
                Sum = tempSum1;
        }
        totalArea = (Sum - (Count - 2) * Math.PI) * Radius * Radius;

        return totalArea; //返回总面积
    }
    /*GeoTool结束*/
    
    /*挂载*/
    //静态
    // ControlAnchor
    LMap.LMAP_ANCHOR_LEFT = BMAP_ANCHOR_TOP_LEFT;
    LMap.LMAP_ANCHOR_TOP_RIGHT = BMAP_ANCHOR_TOP_RIGHT;
    LMap.LMAP_ANCHOR_BOTTOM_LEFT = BMAP_ANCHOR_BOTTOM_LEFT;
    LMap.LMAP_ANCHOR_BOTTOM_RIGHT = BMAP_ANCHOR_BOTTOM_RIGHT;
    // LengthUnit
    LMap.LMAP_UNIT_METRIC = BMAP_UNIT_METRIC;
    LMap.LMAP_UNIT_IMPERIAL = BMAP_UNIT_IMPERIAL;
    // NavigationControlType
    LMap.LMAP_NAVIGATION_CONTROL_LARGE = BMAP_NAVIGATION_CONTROL_LARGE;
    LMap.LMAP_NAVIGATION_CONTROL_SMALL = BMAP_NAVIGATION_CONTROL_SMALL;
    LMap.LMAP_NAVIGATION_CONTROL_PAN = BMAP_NAVIGATION_CONTROL_PAN;
    LMap.LMAP_NAVIGATION_CONTROL_ZOOM = BMAP_NAVIGATION_CONTROL_ZOOM;
    // MapTypeControlType
    LMap.LMAP_MAPTYPE_CONTROL_HORIZONTAL = BMAP_MAPTYPE_CONTROL_HORIZONTAL;
    LMap.LMAP_MAPTYPE_CONTROL_DROPDOWN = BMAP_MAPTYPE_CONTROL_DROPDOWN;
    LMap.LMAP_MAPTYPE_CONTROL_MAP = BMAP_MAPTYPE_CONTROL_MAP;
    // SymbolShapeType
    LMap.LMap_Symbol_SHAPE_CIRCLE = BMap_Symbol_SHAPE_CIRCLE;
    LMap.LMap_Symbol_SHAPE_RECTANGLE = BMap_Symbol_SHAPE_RECTANGLE;
    LMap.LMap_Symbol_SHAPE_RHOMBUS = BMap_Symbol_SHAPE_RHOMBUS;
    LMap.LMap_Symbol_SHAPE_STAR = BMap_Symbol_SHAPE_STAR;
    LMap.LMap_Symbol_SHAPE_BACKWARD_CLOSED_ARROW = BMap_Symbol_SHAPE_BACKWARD_CLOSED_ARROW;
    LMap.LMap_Symbol_SHAPE_FORWARD_CLOSED_ARROW = BMap_Symbol_SHAPE_FORWARD_CLOSED_ARROW;
    LMap.LMap_Symbol_SHAPE_BACKWARD_OPEN_ARROW = BMap_Symbol_SHAPE_BACKWARD_OPEN_ARROW;
    LMap.LMap_Symbol_SHAPE_FORWARD_OPEN_ARROW = BMap_Symbol_SHAPE_FORWARD_OPEN_ARROW;
    LMap.LMap_Symbol_SHAPE_POINT = BMap_Symbol_SHAPE_POINT;
    LMap.LMap_Symbol_SHAPE_PLANE = BMap_Symbol_SHAPE_PLANE;
    LMap.LMap_Symbol_SHAPE_CAMERA = BMap_Symbol_SHAPE_CAMERA;
    LMap.LMap_Symbol_SHAPE_WARNING = BMap_Symbol_SHAPE_WARNING;
    LMap.LMap_Symbol_SHAPE_SMILE = BMap_Symbol_SHAPE_SMILE;
    LMap.LMap_Symbol_SHAPE_CLOCK = BMap_Symbol_SHAPE_CLOCK;
    // Animation
    LMap.LMAP_ANIMATION_DROP = BMAP_ANIMATION_DROP;
    LMap.LMAP_ANIMATION_BOUNCE = BMAP_ANIMATION_BOUNCE;
    // ShapeType
    LMap.LMAP_POINT_SHAPE_CIRCLE = BMAP_POINT_SHAPE_CIRCLE;
    LMap.LMAP_POINT_SHAPE_STAR = BMAP_POINT_SHAPE_STAR;
    LMap.LMAP_POINT_SHAPE_SQUARE = BMAP_POINT_SHAPE_SQUARE;
    LMap.LMAP_POINT_SHAPE_RHOMBUS = BMAP_POINT_SHAPE_RHOMBUS;
    LMap.LMAP_POINT_SHAPE_WATERDROP = BMAP_POINT_SHAPE_WATERDROP;
    // SizeType
    LMap.LMAP_POINT_SIZE_TINY = BMAP_POINT_SIZE_TINY;
    LMap.LMAP_POINT_SIZE_SMALLER = BMAP_POINT_SIZE_SMALLER;
    LMap.LMAP_POINT_SIZE_SMALL = BMAP_POINT_SIZE_SMALL;
    LMap.LMAP_POINT_SIZE_NORMAL = BMAP_POINT_SIZE_NORMAL;
    LMap.LMAP_POINT_SIZE_BIG = BMAP_POINT_SIZE_BIG;
    LMap.LMAP_POINT_SIZE_BIGGER = BMAP_POINT_SIZE_BIGGER;
    LMap.LMAP_POINT_SIZE_HUGE = BMAP_POINT_SIZE_HUGE;
    // ContextMenuIcon
    LMap.LMAP_CONTEXT_MENU_ICON_ZOOMIN = BMAP_CONTEXT_MENU_ICON_ZOOMIN;
    LMap.LMAP_CONTEXT_MENU_ICON_ZOOMOUT = BMAP_CONTEXT_MENU_ICON_ZOOMOUT;
    // PointDensityType
    LMap.LMAP_POINT_DENSITY_HIGH = BMAP_POINT_DENSITY_HIGH;
    LMap.LMAP_POINT_DENSITY_MEDIUM = BMAP_POINT_DENSITY_MEDIUM;
    LMap.LMAP_POINT_DENSITY_LOW = BMAP_POINT_DENSITY_LOW;
    // LineType
    LMap.LMAP_LINE_TYPE_BUS = BMAP_LINE_TYPE_BUS;
    LMap.LMAP_LINE_TYPE_SUBWAY = BMAP_LINE_TYPE_SUBWAY;
    LMap.LMAP_LINE_TYPE_FERRY = BMAP_LINE_TYPE_FERRY;
    // DrivingPolicy
    LMap.LMAP_DRIVING_POLICY_LEAST_TIME = BMAP_DRIVING_POLICY_LEAST_TIME;
    LMap.LMAP_DRIVING_POLICY_LEAST_DISTANCE = BMAP_DRIVING_POLICY_LEAST_DISTANCE;
    LMap.LMAP_DRIVING_POLICY_AVOID_HIGHWAYS = BMAP_DRIVING_POLICY_AVOID_HIGHWAYS;
    // PoiType
    LMap.LMAP_POI_TYPE_NORMAL = BMAP_POI_TYPE_NORMAL;
    LMap.LMAP_POI_TYPE_BUSSTOP = BMAP_POI_TYPE_BUSSTOP;
    LMap.LMAP_POI_TYPE_SUBSTOP = BMAP_POI_TYPE_SUBSTOP;
    // TransitPolicy
    LMap.LMAP_TRANSIT_POLICY_LEAST_TIME = BMAP_TRANSIT_POLICY_LEAST_TIME;
    LMap.LMAP_TRANSIT_POLICY_LEAST_TRANSFER = BMAP_TRANSIT_POLICY_LEAST_TRANSFER;
    LMap.LMAP_TRANSIT_POLICY_LEAST_WALKING = BMAP_TRANSIT_POLICY_LEAST_WALKING;
    LMap.LMAP_TRANSIT_POLICY_AVOID_SUBWAYS = BMAP_TRANSIT_POLICY_AVOID_SUBWAYS;
    // RouteType
    LMap.LMAP_ROUTE_TYPE_DRIVING = BMAP_ROUTE_TYPE_DRIVING;
    LMap.LMAP_ROUTE_TYPE_WALKING = BMAP_ROUTE_TYPE_WALKING;
    // HighlightModes
    LMap.LMAP_HIGHLIGHT_STEP = BMAP_HIGHLIGHT_STEP;
    LMap.LMAP_HIGHLIGHT_ROUTE = BMAP_HIGHLIGHT_ROUTE;
    // StatusCode
    LMap.LMAP_STATUS_SUCCESS = BMAP_STATUS_SUCCESS;
    LMap.LMAP_STATUS_CITY_LIST = BMAP_STATUS_CITY_LIST;
    LMap.LMAP_STATUS_UNKNOWN_LOCATION = BMAP_STATUS_UNKNOWN_LOCATION;
    LMap.LMAP_STATUS_UNKNOWN_ROUTE = BMAP_STATUS_UNKNOWN_ROUTE;
    LMap.LMAP_STATUS_INVALID_KEY = BMAP_STATUS_INVALID_KEY;
    LMap.LMAP_STATUS_INVALID_REQUEST = BMAP_STATUS_INVALID_REQUEST;
    LMap.LMAP_STATUS_PERMISSION_DENIED = BMAP_STATUS_PERMISSION_DENIED;
    LMap.LMAP_STATUS_SERVICE_UNAVAILABLE = BMAP_STATUS_SERVICE_UNAVAILABLE;
    LMap.LMAP_STATUS_TIMEOUT = BMAP_STATUS_TIMEOUT;
    // PanoramaSceneType
    LMap.LMAP_PANORAMA_INDOOR_SCENE = BMAP_PANORAMA_INDOOR_SCENE;
    LMap.LMAP_PANORAMA_STREET_SCENE = BMAP_PANORAMA_STREET_SCENE;
    // PanoramaPOIType
    LMap.LMAP_PANORAMA_POI_HOTEL = BMAP_PANORAMA_POI_HOTEL;
    LMap.LMAP_PANORAMA_POI_CATERING = BMAP_PANORAMA_POI_CATERING;
    LMap.LMAP_PANORAMA_POI_MOVIE = BMAP_PANORAMA_POI_MOVIE;
    LMap.LMAP_PANORAMA_POI_TRANSIT = BMAP_PANORAMA_POI_TRANSIT;
    LMap.LMAP_PANORAMA_POI_INDOOR_SCENE = BMAP_PANORAMA_POI_INDOOR_SCENE;
    LMap.LMAP_PANORAMA_POI_NONE = BMAP_PANORAMA_POI_NONE;
    
    // 核心
    LMap.Map = BMap.Map;  // 地图类
    LMap.Map.prototype.bind = LMap.Map.prototype.addEventListener;
    LMap.Map.prototype.unbind = LMap.Map.prototype.removeEventListener;
    // 基础
    LMap.Point = BMap.Point; // 点
    LMap.Pixel = BMap.Pixel; // 像素点
    LMap.Bounds = BMap.Bounds; // 边界
    LMap.Size = BMap.Size; //尺寸
    // 控件
    LMap.Control = BMap.Control; // 控件原型
    LMap.OverviewMapControl = BMap.OverviewMapControl; // 鹰眼图
    LMap.MapTypeControl = BMap.MapTypeControl;
    LMap.NavigationControl = BMap.NavigationControl;
    LMap.CopyrightControl = BMap.CopyrightControl;
    LMap.ScaleControl = BMap.ScaleControl;
    LMap.GeolocationControl = BMap.GeolocationControl; // html5
    LMap.PanoramaControl = BMap.PanoramaControl;
    //覆盖物类
    // var Overlay 抽象类 overlay需要继承该抽象类
    LMap.Overlay = BMap.Overlay;
    LMap.Marker = BMap.Marker;
    LMap.Marker.prototype.bind = LMap.Marker.prototype.addEventListener;
    LMap.Marker.prototype.unbind = LMap.Marker.prototype.removeEventListener;
    LMap.IconSequence = BMap.IconSequence;
    LMap.PointCollection = BMap.PointCollection; // html5
    LMap.InfoWindow = BMap.InfoWindow;
    LMap.Polygon = BMap.Polygon;
    LMap.Polygon.prototype.bind = LMap.Polygon.prototype.addEventListener;
    LMap.Polygon.prototype.unbind = LMap.Polygon.prototype.removeEventListener;
    LMap.Icon = BMap.Icon;
    LMap.Label = BMap.Label;
    LMap.Circle = BMap.Circle;
    LMap.Circle.prototype.bind = LMap.Circle.prototype.addEventListener;
    LMap.Circle.prototype.unbind = LMap.Circle.prototype.removeEventListener;
    LMap.Hotspot = BMap.Hotspot;
    LMap.Symbol = BMap.Symbol;
    LMap.Polyline = BMap.Polyline;
    LMap.Polyline.prototype.bind = LMap.Polyline.prototype.addEventListener;
    LMap.Polyline.prototype.unbind = LMap.Polyline.prototype.removeEventListener;
    LMap.GroundOverlay = BMap.GroundOverlay;
    //工具类 百度放在第三方了 自己实现 以Tool结尾
    LMap.GeoTool = GeoTool; // 计算点线面关系的工具
//    LMap.MouseTool = MouseTool;
    //右键菜单类
    LMap.ContextMenu = BMap.ContextMenu;
    LMap.MenuItem = BMap.MenuItem;
    //地图类型类
    LMap.MapType = BMap.MapType;
    //地图图层类
    LMap.TileLayer = BMap.TileLayer;
    LMap.TrafficLayer  = BMap.TrafficLayer ;
    LMap.CustomLayer = BMap.CustomLayer; //主要为LBS云麻点功能展现服务
    LMap.PanoramaCoverageLayer = BMap.PanoramaCoverageLayer;
    //服务类
    LMap.LocalSearch = BMap.LocalSearch;
    LMap.BusLineSearch = BMap.BusLineSearch;
    LMap.DrivingRoute = BMap.DrivingRoute;
    LMap.Geocoder = BMap.Geocoder;
    LMap.LocalCity = BMap.LocalCity;
    LMap.Autocomplete = BMap.Autocomplete;
    LMap.TransitRoute = BMap.TransitRoute;
    LMap.TrafficControl = BMap.TrafficControl; //该控件的停靠位置常量仅支持BMAP_CONTROL_ANCHOR_TOP_RIGHT
    LMap.Geolocation = BMap.Geolocation;
    LMap.Boundary = BMap.Boundary;
    LMap.WalkingRoute = BMap.WalkingRoute;
    //全景类
    LMap.Panorama = BMap.Panorama;
    LMap.PanoramaService = BMap.PanoramaService;
    LMap.PanoramaLabel = BMap.PanoramaLabel;
    // 图形挂载
    LMap.Raphael = Raphael;
    /**
     * 自定义 点 线 面 api
     * 样式不可以自定义 不是用来显示 而是用来控制的
     */
    // 起始点 和 svgPath
    // 改成 起始点 commandlist(命令列表) 和设置参数
    function CurveCommand(c,params){
      this._c = c;// 命令
      this._params = params; // 命令参数
    }
    LMap.CurveCommand = CurveCommand;
    function Curve(commandList,opts){
      this._cl = commandList; // 命令列表
      this._opts = opts; // 样式
//      this._show = false; // 显示标识
      var me = this;
      function removeEvent(){
        me._map = null;
        this.removeEventListener('remove',removeEvent)
      }
      this.addEventListener('remove',removeEvent);
    }
    Curve.prototype = new LMap.Overlay();
    Curve.prototype.__getSvgPath = function(){
      var buf = '';
      for(var i = 0;i<this._cl.length;i++){
        var cc = this._cl[i];
        buf += cc._c;
        for(var j=0;j<cc._params.length;j++){
          var pbuf = this._map.pointToOverlayPixel(cc._params[j]);
          buf += pbuf.x+" "+pbuf.y;
          if(j!=cc._params.length-1){
            buf += " ";
          }
        }
      }
      return buf;
    };
    Curve.prototype.initialize = function(map){
      this._map = map; // 地图引用
      var svgP = this.__getSvgPath();
      var div = this.container = document.createElement('div');
      div.style.cssText = 'position:absolute;top:0px;left:0px;z-index:199;';
      
      this._paper = LMap.Raphael(div,1800,1600);
      this._paper.setViewBox(-500,-500,1800,1600);
      
      this.obj = this._paper.path(svgP);
      if(this._opts){
        this.obj.attr(this._opts);
      }else{
        this.obj.attr({
          "stroke-width":4,
          "cursor":"pointer",
          "stroke":"#3a6bdb",
          "stroke-opacity":0.7
        });
      }
      
      
      this._map.getPanes().markerPane.appendChild(div);
      div.firstChild.style.cssText='position:absolute;top:-500px;left:-500px;width:1800px;height:1600px;';
      return div;
    };
    Curve.prototype.draw = function(){
      var map   = this._map,
          point = map.pixelToPoint(new BMap.Pixel(0, 0)),
          pixel = map.pointToOverlayPixel(point);
      this.container.firstChild.style.left = (pixel.x-500) + "px";
      this.container.firstChild.style.top  = (pixel.y-500) + "px"; 
      this._paper.setViewBox(pixel.x-500,pixel.y-500,1800,1600);
      var svgPath = this.__getSvgPath();
      this.obj.attr({
        path:svgPath
      });
    };
    Curve.prototype.getMap = function(){
      return this._map;
    };
    Curve.prototype.getCommandList = function(){
      return this._cl;
    };
    Curve.prototype.setCommandList = function(cl){
      this._cl = cl;
      var svgPath = this.__getSvgPath();
      this.obj[0].setAttribute('d',svgPath);
    };
    LMap.Curve = Curve;
    
    /*
      图形编辑 边界前图形必须在地图上
      1 - 先一次可以编辑一个单独的图形
      2 - 一次编辑多个图形
      3 - 控制点吸附
      4 - 预添加控制点 特别是多边形
    */
    function ControlPoint(obj,type,map,num,count,cp,ref){// center,point 是圆形控制器点
      this._obj = obj; // 所属对象
      this._type = type;
      this._map = map; // 所属地图对象
      this._key = num; // 这个值和数组里的位置对应
      this._keyNum = count;//总数
      if(cp){
        this._entity = cp; // 控制点
        this._map.addOverlay(this._entity);
        this._entity.hide();
      }
      if(ref){
        this._ref = ref; // 参考
        this._map.addOverlay(this._ref);
        this._ref.hide();
      }
      var me = this;
      if(me._ref&&me._entity){
        function __ddd(e){
          var path = me._ref.getPath();
          path[me._key] = me._entity.getPosition();
          me._ref.setPath(path);
        }
        me._entity.bind('dragstart',__ddd);
        me._entity.bind('dragging',__ddd);
        me._entity.bind('dragend',__ddd);
      }
    }
    function ControlCircle(obj,type,map,num,count,ref,center,point){
      this._obj = obj; // 所属对象
      this._type = type;
      this._map = map; // 所属地图对象
      this._key = num; // 这个值和数组里的位置对应
      this._keyNum = count;//总数
      if(ref){
        this._ref = ref; // 参考
        this._map.addOverlay(this._ref);
        this._ref.hide();
      }
      if(center){
        this._center = center;
        this._map.addOverlay(this._center);
        this._center.hide();
      }
      if(point){
        this._point = point;
        this._map.addOverlay(this._point);
        this._point.hide();
      }
      var me = this;
      if(center&&point&&ref){
        function __cdd(e){
          // 移动中心 获取中心位置 获取两点距离 重设圆心半径
          var buf = me._center.getPosition();
          var dis = LMap.GeoTool.getDistance(me._center.getPosition(),me._point.getPosition());
          me._ref.setCenter(buf);
          me._ref.setRadius(dis);
        }
        function __pdd(e){
          // 移动边界 获取距离 重设半径即可
          var dis = LMap.GeoTool.getDistance(me._center.getPosition(),me._point.getPosition());
          me._ref.setRadius(dis);
        }
        me._center.bind('dragstart',__cdd);
        me._center.bind('dragging',__cdd);
        me._center.bind('dragend',__cdd);
        me._point.bind('dragstart',__pdd);
        me._point.bind('dragging',__pdd);
        me._point.bind('dragend',__pdd);
      }
    }
    function ControlCurve(obj,type,map,num,count,ref,mainPoint,cp1,cp2){
      this._obj = obj; // 所属对象
      this._type = type;
      this._map = map; // 所属地图对象
      this._key = num; // 这个值和数组里的位置对应
      this._keyNum = count;//总数
      this._lineOpt = {
        strokeColor:'#f00',
        strokeWeight:2,
        strokeOpacity:0.6,
        strokeStyle:'dashed'
      };
      if(ref){
        this._ref = ref; // 参考
        this._map.addOverlay(this._ref);
        this._ref.hide();
      }
      if(mainPoint){
        this._mp = mainPoint;
        this._map.addOverlay(this._mp);
        this._mp.hide();
      }
      if(cp1){
        this._cp1 = cp1;
        this._cp1_mp = new LMap.Polyline([this._mp.getPosition(),this._cp1.getPosition()],this._lineOpt);
        this._map.addOverlay(this._cp1);
        this._map.addOverlay(this._cp1_mp);
        this._cp1_mp.hide();
        this._cp1.hide();
      }
      if(cp2){
        this._cp2 = cp2;
        this._cp2_mp = new LMap.Polyline([this._mp.getPosition(),this._cp2.getPosition()],this._lineOpt);
        this._map.addOverlay(this._cp2);
        this._map.addOverlay(this._cp2_mp);
        this._cp2_mp.hide();
        this._cp2.hide();
      }
      var me = this;
      var preMP = me._mp.getPosition();
      var preCP1,preCP2;
      if(me._mp){
         // 前一个位置和下一个位置
        if(me._cp1) preCP1 = me._cp1.getPosition();
        if(me._cp2) preCP2 = me._cp2.getPosition();
        function __mds(e){
          // 获取到点所在的位置 
          var posi = me._mp.getPosition();
          var offsetx = posi.lng - preMP.lng;
          var offsety = posi.lat - preMP.lat;
          // 如果有cp1 重置cp1的位置 （和点mp联动） 设置cp1_mp的path 设置曲线的path
          if(me._cp1) {
            me._cp1.setPosition(new LMap.Point(preCP1.lng+offsetx,preCP1.lat+offsety));
            me._cp1_mp.setPath([me._mp.getPosition(),me._cp1.getPosition()]);
            preCP1 = me._cp1.getPosition();
          }
          // cp2 同理
          if(me._cp2) {
            me._cp2.setPosition(new LMap.Point(preCP2.lng+offsetx,preCP2.lat+offsety));
            me._cp2_mp.setPath([me._mp.getPosition(),me._cp2.getPosition()]);
            preCP2 = me._cp2.getPosition();
          }
          var cl = me._obj.getCommandList();
          if(cl[me._key]._c=='M') cl[me._key]._params[0] = me._mp.getPosition();
          if(cl[me._key]._c=='C') cl[me._key]._params[2] = me._mp.getPosition();
          if(me._cp1) cl[me._key]._params[1] = me._cp1.getPosition();
          if(me._cp2) cl[me._key+1]._params[0] = me._cp2.getPosition();
          me._ref.setCommandList(cl);
          preMP = me._mp.getPosition();
        }
        me._mp.bind('dragstart',__mds);
        me._mp.bind('dragging',__mds);
        me._mp.bind('dragend',__mds);
      }
      if(me._cp1){
        function __cds1(e){
          // 获取点的位置 获取命令列表 修改变动部分
          me._cp1_mp.setPath([me._mp.getPosition(),me._cp1.getPosition()]);
          preCP1 = me._cp1.getPosition();
          var cl = me._obj.getCommandList();
          cl[me._key]._params[1] = me._cp1.getPosition();
          me._ref.setCommandList(cl);
        }
        me._cp1.bind('dragstart',__cds1);
        me._cp1.bind('dragging',__cds1);
        me._cp1.bind('dragend',__cds1);
      }
      if(me._cp2){
        function __cds2(e){
          me._cp2_mp.setPath([me._mp.getPosition(),me._cp2.getPosition()]);
          preCP2 = me._cp2.getPosition();
          var cl = me._obj.getCommandList();
          cl[me._key+1]._params[0] = me._cp2.getPosition();
          me._ref.setCommandList(cl);
        }
        me._cp2.bind('dragstart',__cds2);
        me._cp2.bind('dragging',__cds2);
        me._cp2.bind('dragend',__cds2);
      }
      
    }
    function Editor(){
      this._cp =  new Array();// 控制点
      this._defaultIcon = new BMap.Icon('../imgs/lmap_mousetool_point.png',new BMap.Size(10,10));
      this._blankIcon = new BMap.Icon('../imgs/blank.gif',new BMap.Size(1,1));
      this._markerOpt = {
        icon:this._defaultIcon,
        enableDragging:true
      };
      this._lineOpt = {
        strokeColor:'#f00',
        strokeWeight:2,
        strokeOpacity:0.8,
        strokeStyle:'solid'
      };
      this._circleflag = 0; //圆形的编辑模式 默认是圆形
    }
    // 添加需要编辑的图形
    Editor.prototype.edit = function(obj){
      if(obj instanceof LMap.Marker){
        var type = "Marker";
        var map = obj.getMap();
        if(map==null) return ;
        var num = 0; // 因为是一个点所以只有一个点
        var count = 1;
        
        var controlPoint = new LMap.Marker(obj.getPosition(),this._markerOpt);
        controlPoint.setShadow(this._blankIcon);
        var cp = new ControlPoint(obj,type,map,num,count,controlPoint);
        this._cp.push(cp);
      }else if(obj instanceof LMap.Polyline){
        var type = "Polyline";
        var map = obj.getMap();
        if(map==null) return ;
        var path = obj.getPath();
        var count = path.length;
        var num = 0;
        var ref = new LMap.Polyline(path,this._lineOpt);
        for( ; num<count ; num++ ){
          var controlPoint = new LMap.Marker(path[num],this._markerOpt);
          controlPoint.setShadow(this._blankIcon);
          var cp = new ControlPoint(obj,type,map,num,count,controlPoint,ref);
          this._cp.push(cp);
        }
      }else if(obj instanceof LMap.Polygon &&　!(obj instanceof LMap.Circle)){
        var type = "Polygon";
        var map = obj.getMap();
        if(map==null) return ;
        var path = obj.getPath();
        var count = path.length;
        var num = 0;
        var ref = new LMap.Polygon(path,this._lineOpt);
        for( ; num<count ; num++ ){
          var controlPoint = new LMap.Marker(path[num],this._markerOpt);
          controlPoint.setShadow(this._blankIcon);
          var cp = new ControlPoint(obj,type,map,num,count,controlPoint,ref);
          this._cp.push(cp);
        }
      }else if(obj instanceof LMap.Circle){
        if(this._circleflag==0){
          var type = "Circle";
          var map = obj.getMap();
          if(map==null) return ;
          var point = obj.getPath()[30];
          var center = obj.getCenter();
          var radius = obj.getRadius();
          var ref = new LMap.Circle(center,radius,this._lineOpt);
          var count = 0;
          var num = 0;
          var cp1 = new LMap.Marker(center,this._markerOpt);
          var cp2 = new LMap.Marker(point,this._markerOpt);
          var cp = new ControlCircle(obj,type,map,num,count,ref,cp1,cp2);
          this._cp.push(cp);
        }else if(this._circleflag==1){
          var type = "Polygon";
          var map = obj.getMap();
          if(map==null) return ;
          var path = obj.getPath();
          var count = path.length;
          var num = 0;
          var ref = new LMap.Polygon(path,this._lineOpt);
          for( ; num<count ; num++ ){
            var controlPoint = new LMap.Marker(path[num],this._markerOpt);
            controlPoint.setShadow(this._blankIcon);
            var cp = new ControlPoint(obj,type,map,num,count,controlPoint,ref);
            this._cp.push(cp);
          }
        }
      }else if(obj instanceof LMap.Curve){
        var type = "Curve";
        var map = obj.getMap();
        if(map==null) return ;
        var cl = obj.getCommandList(); // 命令点
        var num = 0;
        var count = cl.length;
        var ref = new LMap.Curve(cl,{
          "stroke-width":2,
          "cursor":"pointer",
          "stroke":"#f00",
          "stroke-opacity":1
        });
        for(;num<count;num++){
          var c = cl[num]; //控制点 
          // 判断命令
          if(c._c=='M'){
            var mp = new LMap.Marker(c._params[0],this._markerOpt);// 主要点
            
            if(num+1<count&&cl[num+1]._c=='C'){ // 下一个是C的时候
              var cp2 = new LMap.Marker(cl[num+1]._params[0],this._markerOpt);
              var cc = new ControlCurve(obj,type,map,num,count,ref,mp,undefined,cp2);
              this._cp.push(cc);
            }else{ // 下一个不是C的时候 目前只支持M C
              var cc = new ControlCurve(obj,type,map,num,count,ref,mp);
              this._cp.push(cc);
            }
          }else if(c._c=='C'){
            var mp = new LMap.Marker(c._params[2],this._markerOpt);
            var cp1 = new LMap.Marker(c._params[1],this._markerOpt);
            if(num+1<count&&cl[num+1]._c=='C'){ // 有下一个并且是C的时候
              var cp2 = new LMap.Marker(cl[num+1]._params[0],this._markerOpt);
              var cc = new ControlCurve(obj,type,map,num,count,ref,mp,cp1,cp2);
              this._cp.push(cc);
            }else{
              var cc = new ControlCurve(obj,type,map,num,count,ref,mp,cp1);
              this._cp.push(cc);
            }
          }
        }
      }
    };
    // 隐藏元图形 以编辑模式显示新的图形
    Editor.prototype.open = function(){
      for(var i=0;i<this._cp.length;i++){
        var cp = this._cp[i];
        if(cp._type == 'Marker'){
          // 隐藏图形
          cp._obj.hide();
          //添加控制图形
          cp._entity.show();
        }else if(cp._type == 'Polyline'){
          cp._obj.hide();
          cp._entity.show();
          cp._ref.show();
        }else if(cp._type == 'Polygon'){
          cp._obj.hide();
          cp._entity.show();
          cp._ref.show();
        }else if(cp._type == 'Circle'){
          cp._obj.hide();
          cp._center.show();
          cp._point.show();
          cp._ref.show();
        }else if(cp._type == 'Curve'){
          cp._obj.hide();
          cp._mp.show();
          if(cp._cp1){
            cp._cp1.show();
            cp._cp1_mp.show();
          }
          if(cp._cp2){
            cp._cp2.show();
            cp._cp2_mp.show();
          }
          cp._ref.show();
        }
      }
    };
    // 将编辑好的图形 赋值并重新显示
    Editor.prototype.close = function(){
      for(var i=0;i<this._cp.length;i++){
        var cp = this._cp[i];
        if(cp._type == 'Marker'){
          cp._obj.setPosition(cp._entity.getPosition());
          cp._entity.hide();
          cp._obj.show();
        }else if(cp._type == 'Polyline'){
          cp._obj.setPath(cp._ref.getPath());
          cp._entity.hide();
          cp._ref.hide();
          cp._obj.show();
        }else if(cp._type == 'Polygon'){
          cp._obj.setPath(cp._ref.getPath());
          cp._entity.hide();
          cp._ref.hide();
          cp._obj.show();
        }else if(cp._type == 'Circle'){
          cp._obj.setPath(cp._ref.getPath());
          cp._center.hide();
          cp._point.hide();
          cp._ref.hide();
          cp._obj.show();
        }else if(cp._type == 'Curve'){
          cp._obj.setCommandList(cp._ref.getCommandList());
          cp._mp.hide();
          if(cp._cp1){
            cp._cp1.hide();
            cp._cp1_mp.hide();
          }
          if(cp._cp2){
            cp._cp2.hide();
            cp._cp2_mp.hide();
          }
          cp._ref.hide();
          cp._obj.show();
        }
      }
    };
    // 编辑完成的图形 赋值并不现实元图形 还是显示可编辑图形
    Editor.prototype.over = function(){};
    // 清理缓存删除属性
    Editor.prototype.distory = function(){};
    // 计算目前添加进来的图形 重新组合后 把新的图形返回
    Editor.prototype.clac = function(){};
    // 圆形的编辑模式
    Editor.prototype.circleEditStyle = function(flag){
      this._circleflag = flag;
    };
    // 需要在编辑之前设置
    LMap.LMAP_EDITOR_CIRCLE_EDIT_TYPE_1 = 0;
    LMap.LMAP_EDITOR_CIRCLE_EDIT_TYPE_2 = 1;
    LMap.Editor = Editor;
    
    /**
     * 自定义Control
     */
    function ToolBarControl(){
      this.defaultOffset = new LMap.Size(0, 0);
    }
    ToolBarControl.prototype = new LMap.Control();
    ToolBarControl.prototype.initialize = function(map){ 
      var div = document.createElement("div");
      div.style.width='100%';
      div.style.height="28px";
      div.style.backgroundColor='#aaa';
      div.style.fontWeight="bolder";
      div.style.color="white";
      div.style.overflow = 'hidden';
      
      //需要添加的工具 
      //拉框放大  测距  测量面积  保存视野 区域查车
      var buf = '';
      buf += '<span style="display:inline-block;padding:6px;cursor:pointer;" title="拉框放大">拉框放大</span>';
      buf += '<span style="display:inline-block;padding:6px;cursor:pointer;" title="测量距离">测量距离</span>';
      buf += '<span style="display:inline-block;padding:6px;cursor:pointer;" title="测量面积">测量面积</span>';
      buf += '<span style="display:inline-block;padding:6px;cursor:pointer;" title="保存视野">保存视野</span>';
      buf += '<span style="display:inline-block;padding:6px;cursor:pointer;" title="区域查车">区域查车</span>';
      
      div.innerHTML = buf;
      
      map.getContainer().appendChild(div);
      return div;
    }
    
    LMap.ToolBarControl = ToolBarControl;
  }
  
  
  //高德api转接
  LMap.gaodeInit = function(){
    
    function Map(p1,opts){
      var minZoom,maxZoom,mapType,
          enableHighResolution,
          enableAutoResize,enableMapClick;
      if(opts.minZoom) minZoom = opts.minZoom; else minZoom = 3;
      if(opts.maxZoom) maxZoom = opts.maxZoom; else maxZoom = 16;
      if(opts.mapType && opts.mapType instanceof LMap.MapType)
        mapType = opts.mapType;
      else mapType = LMap.LMAP_NORMAL_MAP;
//      if(opts.enableHighResolution) console.log('高德api不支持enableHighResolution地图配置属性');
//      if(opts.enableAutoResize) console.log('高德api不支持enableAutoResize地图配置属性');
//      if(opts.enableMapClick) enableMapClick=true; else enableMapClick=false;
      var buf = {};
      LMap.__extend(buf , opts);
      buf.zooms = [minZoom,maxZoom];
      buf.lang = 'zh_cn';
      buf.layers = [mapType];
      return new AMap(pl,buf);
    }
//    LMap.LMAP_NORMAL_MAP = ;
//    LMap.LMAP_PERSPECTIVE_MAP = ;
//    LMap.LMAP_SATELLITE_MAP = ;
//    LMap.LMAP_HYBRID_MAP = ;
    
    /*挂载*/
    //静态
    // ControlAnchor
    /*LMap.LMAP_ANCHOR_LEFT = BMAP_ANCHOR_TOP_LEFT;
    LMap.LMAP_ANCHOR_TOP_RIGHT = BMAP_ANCHOR_TOP_RIGHT;
    LMap.LMAP_ANCHOR_BOTTOM_LEFT = BMAP_ANCHOR_BOTTOM_LEFT;
    LMap.LMAP_ANCHOR_BOTTOM_RIGHT = BMAP_ANCHOR_BOTTOM_RIGHT;
    // LengthUnit
    LMap.LMAP_UNIT_METRIC = BMAP_UNIT_METRIC;
    LMap.LMAP_UNIT_IMPERIAL = BMAP_UNIT_IMPERIAL;
    // NavigationControlType
    LMap.LMAP_NAVIGATION_CONTROL_LARGE = BMAP_NAVIGATION_CONTROL_LARGE;
    LMap.LMAP_NAVIGATION_CONTROL_SMALL = BMAP_NAVIGATION_CONTROL_SMALL;
    LMap.LMAP_NAVIGATION_CONTROL_PAN = BMAP_NAVIGATION_CONTROL_PAN;
    LMap.LMAP_NAVIGATION_CONTROL_ZOOM = BMAP_NAVIGATION_CONTROL_ZOOM;
    // MapTypeControlType
    LMap.LMAP_MAPTYPE_CONTROL_HORIZONTAL = BMAP_MAPTYPE_CONTROL_HORIZONTAL;
    LMap.LMAP_MAPTYPE_CONTROL_DROPDOWN = BMAP_MAPTYPE_CONTROL_DROPDOWN;
    LMap.LMAP_MAPTYPE_CONTROL_MAP = BMAP_MAPTYPE_CONTROL_MAP;
    // SymbolShapeType
    LMap.LMap_Symbol_SHAPE_CIRCLE = BMap_Symbol_SHAPE_CIRCLE;
    LMap.LMap_Symbol_SHAPE_RECTANGLE = BMap_Symbol_SHAPE_RECTANGLE;
    LMap.LMap_Symbol_SHAPE_RHOMBUS = BMap_Symbol_SHAPE_RHOMBUS;
    LMap.LMap_Symbol_SHAPE_STAR = BMap_Symbol_SHAPE_STAR;
    LMap.LMap_Symbol_SHAPE_BACKWARD_CLOSED_ARROW = BMap_Symbol_SHAPE_BACKWARD_CLOSED_ARROW;
    LMap.LMap_Symbol_SHAPE_FORWARD_CLOSED_ARROW = BMap_Symbol_SHAPE_FORWARD_CLOSED_ARROW;
    LMap.LMap_Symbol_SHAPE_BACKWARD_OPEN_ARROW = BMap_Symbol_SHAPE_BACKWARD_OPEN_ARROW;
    LMap.LMap_Symbol_SHAPE_FORWARD_OPEN_ARROW = BMap_Symbol_SHAPE_FORWARD_OPEN_ARROW;
    LMap.LMap_Symbol_SHAPE_POINT = BMap_Symbol_SHAPE_POINT;
    LMap.LMap_Symbol_SHAPE_PLANE = BMap_Symbol_SHAPE_PLANE;
    LMap.LMap_Symbol_SHAPE_CAMERA = BMap_Symbol_SHAPE_CAMERA;
    LMap.LMap_Symbol_SHAPE_WARNING = BMap_Symbol_SHAPE_WARNING;
    LMap.LMap_Symbol_SHAPE_SMILE = BMap_Symbol_SHAPE_SMILE;
    LMap.LMap_Symbol_SHAPE_CLOCK = BMap_Symbol_SHAPE_CLOCK;
    // Animation
    LMap.LMAP_ANIMATION_DROP = BMAP_ANIMATION_DROP;
    LMap.LMAP_ANIMATION_BOUNCE = BMAP_ANIMATION_BOUNCE;
    // ShapeType
    LMap.LMAP_POINT_SHAPE_CIRCLE = BMAP_POINT_SHAPE_CIRCLE;
    LMap.LMAP_POINT_SHAPE_STAR = BMAP_POINT_SHAPE_STAR;
    LMap.LMAP_POINT_SHAPE_SQUARE = BMAP_POINT_SHAPE_SQUARE;
    LMap.LMAP_POINT_SHAPE_RHOMBUS = BMAP_POINT_SHAPE_RHOMBUS;
    LMap.LMAP_POINT_SHAPE_WATERDROP = BMAP_POINT_SHAPE_WATERDROP;
    // SizeType
    LMap.LMAP_POINT_SIZE_TINY = BMAP_POINT_SIZE_TINY;
    LMap.LMAP_POINT_SIZE_SMALLER = BMAP_POINT_SIZE_SMALLER;
    LMap.LMAP_POINT_SIZE_SMALL = BMAP_POINT_SIZE_SMALL;
    LMap.LMAP_POINT_SIZE_NORMAL = BMAP_POINT_SIZE_NORMAL;
    LMap.LMAP_POINT_SIZE_BIG = BMAP_POINT_SIZE_BIG;
    LMap.LMAP_POINT_SIZE_BIGGER = BMAP_POINT_SIZE_BIGGER;
    LMap.LMAP_POINT_SIZE_HUGE = BMAP_POINT_SIZE_HUGE;
    // ContextMenuIcon
    LMap.LMAP_CONTEXT_MENU_ICON_ZOOMIN = BMAP_CONTEXT_MENU_ICON_ZOOMIN;
    LMap.LMAP_CONTEXT_MENU_ICON_ZOOMOUT = BMAP_CONTEXT_MENU_ICON_ZOOMOUT;
    // PointDensityType
    LMap.LMAP_POINT_DENSITY_HIGH = BMAP_POINT_DENSITY_HIGH;
    LMap.LMAP_POINT_DENSITY_MEDIUM = BMAP_POINT_DENSITY_MEDIUM;
    LMap.LMAP_POINT_DENSITY_LOW = BMAP_POINT_DENSITY_LOW;
    // LineType
    LMap.LMAP_LINE_TYPE_BUS = BMAP_LINE_TYPE_BUS;
    LMap.LMAP_LINE_TYPE_SUBWAY = BMAP_LINE_TYPE_SUBWAY;
    LMap.LMAP_LINE_TYPE_FERRY = BMAP_LINE_TYPE_FERRY;
    // DrivingPolicy
    LMap.LMAP_DRIVING_POLICY_LEAST_TIME = BMAP_DRIVING_POLICY_LEAST_TIME;
    LMap.LMAP_DRIVING_POLICY_LEAST_DISTANCE = BMAP_DRIVING_POLICY_LEAST_DISTANCE;
    LMap.LMAP_DRIVING_POLICY_AVOID_HIGHWAYS = BMAP_DRIVING_POLICY_AVOID_HIGHWAYS;
    // PoiType
    LMap.LMAP_POI_TYPE_NORMAL = BMAP_POI_TYPE_NORMAL;
    LMap.LMAP_POI_TYPE_BUSSTOP = BMAP_POI_TYPE_BUSSTOP;
    LMap.LMAP_POI_TYPE_SUBSTOP = BMAP_POI_TYPE_SUBSTOP;
    // TransitPolicy
    LMap.LMAP_TRANSIT_POLICY_LEAST_TIME = BMAP_TRANSIT_POLICY_LEAST_TIME;
    LMap.LMAP_TRANSIT_POLICY_LEAST_TRANSFER = BMAP_TRANSIT_POLICY_LEAST_TRANSFER;
    LMap.LMAP_TRANSIT_POLICY_LEAST_WALKING = BMAP_TRANSIT_POLICY_LEAST_WALKING;
    LMap.LMAP_TRANSIT_POLICY_AVOID_SUBWAYS = BMAP_TRANSIT_POLICY_AVOID_SUBWAYS;
    // RouteType
    LMap.LMAP_ROUTE_TYPE_DRIVING = BMAP_ROUTE_TYPE_DRIVING;
    LMap.LMAP_ROUTE_TYPE_WALKING = BMAP_ROUTE_TYPE_WALKING;
    // HighlightModes
    LMap.LMAP_HIGHLIGHT_STEP = BMAP_HIGHLIGHT_STEP;
    LMap.LMAP_HIGHLIGHT_ROUTE = BMAP_HIGHLIGHT_ROUTE;
    // StatusCode
    LMap.LMAP_STATUS_SUCCESS = BMAP_STATUS_SUCCESS;
    LMap.LMAP_STATUS_CITY_LIST = BMAP_STATUS_CITY_LIST;
    LMap.LMAP_STATUS_UNKNOWN_LOCATION = BMAP_STATUS_UNKNOWN_LOCATION;
    LMap.LMAP_STATUS_UNKNOWN_ROUTE = BMAP_STATUS_UNKNOWN_ROUTE;
    LMap.LMAP_STATUS_INVALID_KEY = BMAP_STATUS_INVALID_KEY;
    LMap.LMAP_STATUS_INVALID_REQUEST = BMAP_STATUS_INVALID_REQUEST;
    LMap.LMAP_STATUS_PERMISSION_DENIED = BMAP_STATUS_PERMISSION_DENIED;
    LMap.LMAP_STATUS_SERVICE_UNAVAILABLE = BMAP_STATUS_SERVICE_UNAVAILABLE;
    LMap.LMAP_STATUS_TIMEOUT = BMAP_STATUS_TIMEOUT;
    // PanoramaSceneType
    LMap.LMAP_PANORAMA_INDOOR_SCENE = BMAP_PANORAMA_INDOOR_SCENE;
    LMap.LMAP_PANORAMA_STREET_SCENE = BMAP_PANORAMA_STREET_SCENE;
    // PanoramaPOIType
    LMap.LMAP_PANORAMA_POI_HOTEL = BMAP_PANORAMA_POI_HOTEL;
    LMap.LMAP_PANORAMA_POI_CATERING = BMAP_PANORAMA_POI_CATERING;
    LMap.LMAP_PANORAMA_POI_MOVIE = BMAP_PANORAMA_POI_MOVIE;
    LMap.LMAP_PANORAMA_POI_TRANSIT = BMAP_PANORAMA_POI_TRANSIT;
    LMap.LMAP_PANORAMA_POI_INDOOR_SCENE = BMAP_PANORAMA_POI_INDOOR_SCENE;
    LMap.LMAP_PANORAMA_POI_NONE = BMAP_PANORAMA_POI_NONE;*/
    
    
    
    // 核心
    LMap.Map = Map;  // 地图类
    
    LMap.Map.prototype.centerAndZoom = function(point,zoom){
      this.setZoomAndCenter(zoom,point);
    };
//    LMap.Map.prototype.bind = LMap.Map.prototype.addEventListener;
//    LMap.Map.prototype.unbind = LMap.Map.prototype.removeEventListener;
    // 基础 
    LMap.Point = AMap.LngLat; // 点
    LMap.Pixel = AMap.Pixel; // 像素点
    LMap.Bounds = AMap.Bounds; // 边界
    LMap.Bounds.prototype.containsPoint = function(point){
      return this.contains(point);
    };
    LMap.Size = AMap.Size; //尺寸
    LMap.Size.prototype.equals = function(size){
      return size.height==this.height&&size.width==this.width;
    };
    
//    // 控件
//    LMap.Control = BMap.Control; // 控件原型
//    LMap.OverviewMapControl = BMap.OverviewMapControl; // 鹰眼图
//    LMap.MapTypeControl = BMap.MapTypeControl;
//    LMap.NavigationControl = BMap.NavigationControl;
//    LMap.CopyrightControl = BMap.CopyrightControl;
//    LMap.ScaleControl = BMap.ScaleControl;
//    LMap.GeolocationControl = BMap.GeolocationControl; // html5
//    LMap.PanoramaControl = BMap.PanoramaControl;
//    //覆盖物类
//    // var Overlay 抽象类 overlay需要继承该抽象类
//    LMap.Overlay = BMap.Overlay;
//    LMap.Marker = BMap.Marker;
//    LMap.Marker.prototype.bind = LMap.Marker.prototype.addEventListener;
//    LMap.Marker.prototype.unbind = LMap.Marker.prototype.removeEventListener;
//    LMap.IconSequence = BMap.IconSequence;
//    LMap.PointCollection = BMap.PointCollection; // html5
//    LMap.InfoWindow = BMap.InfoWindow;
//    LMap.Polygon = BMap.Polygon;
//    LMap.Polygon.prototype.bind = LMap.Polygon.prototype.addEventListener;
//    LMap.Polygon.prototype.unbind = LMap.Polygon.prototype.removeEventListener;
//    LMap.Icon = BMap.Icon;
//    LMap.Label = BMap.Label;
//    LMap.Circle = BMap.Circle;
//    LMap.Circle.prototype.bind = LMap.Circle.prototype.addEventListener;
//    LMap.Circle.prototype.unbind = LMap.Circle.prototype.removeEventListener;
//    LMap.Hotspot = BMap.Hotspot;
//    LMap.Symbol = BMap.Symbol;
//    LMap.Polyline = BMap.Polyline;
//    LMap.Polyline.prototype.bind = LMap.Polyline.prototype.addEventListener;
//    LMap.Polyline.prototype.unbind = LMap.Polyline.prototype.removeEventListener;
//    LMap.GroundOverlay = BMap.GroundOverlay;
//    //工具类 百度放在第三方了 自己实现 以Tool结尾
//    LMap.GeoTool = GeoTool; // 计算点线面关系的工具
//    LMap.MouseTool = MouseTool;
//    //右键菜单类
//    LMap.ContextMenu = BMap.ContextMenu;
//    LMap.MenuItem = BMap.MenuItem;
//    //地图类型类
//    LMap.MapType = BMap.MapType;
//    //地图图层类
//    LMap.TileLayer = BMap.TileLayer;
//    LMap.TrafficLayer  = BMap.TrafficLayer ;
//    LMap.CustomLayer = BMap.CustomLayer; //主要为LBS云麻点功能展现服务
//    LMap.PanoramaCoverageLayer = BMap.PanoramaCoverageLayer;
//    //服务类
//    LMap.LocalSearch = BMap.LocalSearch;
//    LMap.BusLineSearch = BMap.BusLineSearch;
//    LMap.DrivingRoute = BMap.DrivingRoute;
//    LMap.Geocoder = BMap.Geocoder;
//    LMap.LocalCity = BMap.LocalCity;
//    LMap.Autocomplete = BMap.Autocomplete;
//    LMap.TransitRoute = BMap.TransitRoute;
//    LMap.TrafficControl = BMap.TrafficControl; //该控件的停靠位置常量仅支持BMAP_CONTROL_ANCHOR_TOP_RIGHT
//    LMap.Geolocation = BMap.Geolocation;
//    LMap.Boundary = BMap.Boundary;
//    LMap.WalkingRoute = BMap.WalkingRoute;
//    //全景类
//    LMap.Panorama = BMap.Panorama;
//    LMap.PanoramaService = BMap.PanoramaService;
//    LMap.PanoramaLabel = BMap.PanoramaLabel;
//    // 图形挂载
//    LMap.Raphael = Raphael;Map.Pixel; // 像素点
//    LMap.Bounds = BMap.Bounds; // 边界
//    LMap.Size = BMap.Size; //尺寸
//    // 控件
//    LMap.Control = BMap.Control; // 控件原型
//    LMap.OverviewMapControl = BMap.OverviewMapControl; // 鹰眼图
//    LMap.MapTypeControl = BMap.MapTypeControl;
//    LMap.NavigationControl = BMap.NavigationControl;
//    LMap.CopyrightControl = BMap.CopyrightControl;
//    LMap.ScaleControl = BMap.ScaleControl;
//    LMap.GeolocationControl = BMap.GeolocationControl; // html5
//    LMap.PanoramaControl = BMap.PanoramaControl;
//    //覆盖物类
//    // var Overlay 抽象类 overlay需要继承该抽象类
//    LMap.Overlay = BMap.Overlay;
//    LMap.Marker = BMap.Marker;
//    LMap.Marker.prototype.bind = LMap.Marker.prototype.addEventListener;
//    LMap.Marker.prototype.unbind = LMap.Marker.prototype.removeEventListener;
//    LMap.IconSequence = BMap.IconSequence;
//    LMap.PointCollection = BMap.PointCollection; // html5
//    LMap.InfoWindow = BMap.InfoWindow;
//    LMap.Polygon = BMap.Polygon;
//    LMap.Polygon.prototype.bind = LMap.Polygon.prototype.addEventListener;
//    LMap.Polygon.prototype.unbind = LMap.Polygon.prototype.removeEventListener;
//    LMap.Icon = BMap.Icon;
//    LMap.Label = BMap.Label;
//    LMap.Circle = BMap.Circle;
//    LMap.Circle.prototype.bind = LMap.Circle.prototype.addEventListener;
//    LMap.Circle.prototype.unbind = LMap.Circle.prototype.removeEventListener;
//    LMap.Hotspot = BMap.Hotspot;
//    LMap.Symbol = BMap.Symbol;
//    LMap.Polyline = BMap.Polyline;
//    LMap.Polyline.prototype.bind = LMap.Polyline.prototype.addEventListener;
//    LMap.Polyline.prototype.unbind = LMap.Polyline.prototype.removeEventListener;
//    LMap.GroundOverlay = BMap.GroundOverlay;
//    //工具类 百度放在第三方了 自己实现 以Tool结尾
//    LMap.GeoTool = GeoTool; // 计算点线面关系的工具
//    LMap.MouseTool = MouseTool;
//    //右键菜单类
//    LMap.ContextMenu = BMap.ContextMenu;
//    LMap.MenuItem = BMap.MenuItem;
//    //地图类型类
    LMap.MapType = MapType;
//    //地图图层类
//    LMap.TileLayer = BMap.TileLayer;
//    LMap.TrafficLayer  = BMap.TrafficLayer ;
//    LMap.CustomLayer = BMap.CustomLayer; //主要为LBS云麻点功能展现服务
//    LMap.PanoramaCoverageLayer = BMap.PanoramaCoverageLayer;
//    //服务类
//    LMap.LocalSearch = BMap.LocalSearch;
//    LMap.BusLineSearch = BMap.BusLineSearch;
//    LMap.DrivingRoute = BMap.DrivingRoute;
//    LMap.Geocoder = BMap.Geocoder;
//    LMap.LocalCity = BMap.LocalCity;
//    LMap.Autocomplete = BMap.Autocomplete;
//    LMap.TransitRoute = BMap.TransitRoute;
//    LMap.TrafficControl = BMap.TrafficControl; //该控件的停靠位置常量仅支持BMAP_CONTROL_ANCHOR_TOP_RIGHT
//    LMap.Geolocation = BMap.Geolocation;
//    LMap.Boundary = BMap.Boundary;
//    LMap.WalkingRoute = BMap.WalkingRoute;
//    //全景类
//    LMap.Panorama = BMap.Panorama;
//    LMap.PanoramaService = BMap.PanoramaService;
//    LMap.PanoramaLabel = BMap.PanoramaLabel;
//    // 图形挂载
//    LMap.Raphael = Raphael;
  }
  window.LMap = LMap;
}(window);
LMap.detect();













//地图操作
//var map = new LMap.Map('mapDiv');
//var ppp = new LMap.Point(116.404, 39.915);
//var px = new LMap.Pixel(1,3);
//var sizse = new LMap.Size(2,3); 
//var sizse2 = new LMap.Size(2,3); 
//console.log(ppp.lng+' '+ppp.lat);
//console.log(px.x+' '+px.y);
//console.log(sizse.height+' '+sizse.width);
//console.log(sizse.equals(sizse2));
//map.centerAndZoom(new LMap.Point(116.404, 39.915),15);
//map.enableScrollWheelZoom();
//map.addControl(new LMap.ToolBarControl());

//var mouse = new LMap.MouseTool(map);
//var p = new LMap.Point(116.404, 39.905);
//var p2 = new LMap.Point(116.414, 39.905);
//var p3 = new LMap.Point(116.404, 39.915);
//var marker = new LMap.Marker(p);
////map.addOverlay(new LMap.Polyline([p,p2]));
////var a = new LMap.Curve(p,"M0 0C0 100 100 100 100 0");
//var a = new LMap.Curve([
//  new LMap.CurveCommand('M',[
//    new LMap.Point(116.404,39.915)
//  ]),
//  new LMap.CurveCommand('C',[
//    new LMap.Point(116.404, 39.905),
//    new LMap.Point(116.414, 39.905),
//    new LMap.Point(116.414, 39.915)
//  ]),
//  new LMap.CurveCommand('C',[
//    new LMap.Point(116.414, 39.905),
//    new LMap.Point(116.424, 39.905),
//    new LMap.Point(116.424, 39.915)
//  ])
//]);
//map.addOverlay(a);

//var ed = new LMap.Editor();
//var line = new LMap.Polyline([p,p2,p3]);
//var c = new LMap.Overlay();
//for(var i in a){
//  console.log(i+" "+a[i]);
//}
//var gon = new LMap.Polygon([p,p2,p3]);
//var cir = new LMap.Circle(p,100);
//map.addOverlay(line);
//map.addOverlay(cir);
//ed.edit(cir);
//ed.edit(a);
//var p2 = new LMap.Point(116.414, 39.905);
//p.key = 'point_1'; // 可以自定义实体的名称
//mouse.addPoint(p);
//var l = [
//  new LMap.Point(116.424, 39.905),
//  new LMap.Point(116.414, 39.925),
//  new LMap.Point(116.424, 39.915)
//];
//var g = [
//  new LMap.Point(116.404, 39.905),
//  new LMap.Point(116.414, 39.905),
//  new LMap.Point(116.404, 39.915)
//];
//l.key = 'line_1';
//g.key = 'gon_1';
//mouse.addLine(l);
//mouse.addGon(g);
//alert(LMap.GeoTool.getDistance(p,p2));