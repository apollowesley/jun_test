/**
 * TinyForm-core@0.7.7  2017-05-25
 * @作者: hyjiacan
 * @源码: https://git.oschina.net/hyjiacan/TinyForm.git
 * @示例: http://hyjiacan.oschina.io/tinyform
 * @许可协议: MIT
 * @依赖: jQuery 1.8.0及更高版本
 * @浏览器支持: 不支持IE8及更低版本
 * @QQ群: 187786345 (Javascript爱好者)
 */
/**
 * TinyForm 核心组件，提供form实例化以及表单字段获取功能
 */
(function (win, $) {
    /**
     * 附加到元素上的表单实例的id属性
     */
    var INSTANCE_ATTR = 'data-tinyform';

    /**
     * 插件添加方法时，方法已经存在的提示，这个提示会在控制台输出
     */
    var METHOD_EXISTS = '插件添加方法失败: 此方法已经存在';

    /**
     * 插件的扩展方法集合
     */
    var extfn = {
        /**
         * 扩展的初始化方法数组，每个插件的初始化方法都会注册到这个数组中
         */
        setup: [],
        /**
         * 扩展的刷新方法数组，每个插件的刷新方法都会注册到这个数组中
         */
        refresh: []
    };

    /**
     * 存放所有表单的实例的集合
     * 表单的id为键，表单实例为值
     */
    var instanceSet = {};

    /**
     * 表单实例的字段集合
     */
    var fieldSet = {};

    /**
     * 生成一个新的表单实例id
     * 结构为： tiny+ 时间戳 + 随机数
     */
    function idGenerator() {
        // 时间戳是时间的数字形式
        // 随机数是将生成的float转成字符串，再去掉前面的字符“0”和"."，使用剩下的字符串
        return 'tiny' + (new Date().getTime()) + Math.random().toString().substring(2);
    }

    /**
     * 表单构造函数
     * @param {String|Object} selector 表单选择器
     * @param {Object} option 参数，可选
     * @returns {Object} 表单实例
     */
    function TinyForm(selector, option) {
        // 只将第一个元素实例化成TinyForm
        var $me = $(selector).first();
        // 假设这个元素已经实例化，先取一下id试试
        var id = $me.attr(INSTANCE_ATTR);
        // 如果取到就表示真的已经实例化了，取不到表示还没有实例化
        // 要注意：取到后还有个条件，那就是在实例对象集合  instanceSet 中存在这个id的实例
        // 以防止有小人故意在标签上加个假冒的属性
        if (!id || !instanceSet.hasOwnProperty(id)) {
            // 搞一个新的id
            id = idGenerator();
            // 把这个新id弄到标签的属性上面
            $me.attr(INSTANCE_ATTR, id);
            // 创建实例 TinyForm 的实例
            instanceSet[id] = new TinyForm.prototype.setup($me, option, id);
        }

        // 返回表单实例
        return instanceSet[id];
    }

    /**
     * 表单实例
     */
    TinyForm.prototype = {
        /**
         * 构造
         */
        constructor: TinyForm,
        /**
         * 初始化表单实例
         * @param {Object} formContainer 表单容器的JQ对象
         * @param {Object} option 参数，可选
         * @param {String} id TinyForm实例id
         * @returns {Object}  表单实例
         */
        setup: function (formContainer, option, id) {
            // 保存实例对象到变量里面
            var me = this;
            // 设置实例的id
            me.id = id;

            // 合并选项参数
            me.option = $.extend(true, {}, TinyForm.defaults, option);
            // 表单的DOM上下文
            me.context = formContainer;

            // 获取所有字段
            getAllFields(me);

            // 调用插件的初始化方法  这个调用要放到最后，
            // 确保所有需要的资源都就位
            $.each(extfn.setup, function () {
                // 调用插件的初始化，并且设置插件的初始化方法的上下文this
                this.call(me);
            });

            // 返回实例对象
            return me;
        },
        /**
         * 根据name属性获取字段 返回数组，因为可能存在同name情况
         * @param {String} fieldName 要获取的字段的name值，如果不指定这个属性，那么返回所有字段
         * @returns {Array|Object|void}  范围内所有name为指定值的字段数组或获取到的所有域对象
         */
        getField: function (fieldName) {
            // 获取到所有字段，然后创建一个副本，以避免字段集合被修改
            var all = $.extend(true, {}, fieldSet[this.id]);

            // 不传参数表示获取所有的字段
            if (arguments.length === 0) {
                // 返回所的字段集合的副本
                return all;
            }

            // 如果传的参数不是字符串，那就是不合法的
            if (typeof fieldName !== 'string') {
                // 参数错误，此时返回空
                return;
            }
            // 尝试获取字段对象（这里得到的是jQuery对象）
            var field = all[fieldName];
            // 判断是否存在这个名字的字段
            if (!all.hasOwnProperty(fieldName) || field.length === 0) {
                // 如不存在，返回空
                return;
            }

            // 返回字段的jQuery对象
            return field;
        },

        /**
         * 重新获取表单的字段，此操作将更新缓存
         * @returns {Object} 表单实例
         */
        refresh: function () {
            // 因为要在下面的回调里面使用表单实例，所以弄个变量把实例保存一下
            var me = this;

            // 在这个核心组件中，刷新仅仅是重新获取所有字段
            getAllFields(me);

            // 调用插件的刷新方法
            $.each(extfn.refresh, function () {
                // 并且设置插件的refresh方法的上下文this
                this.call(me);
            });

            // 刷新后，返回表单实例
            return me;
        }
    };

    /**
     * 给TinyForm添加一个静态的函数 extend，用于插件扩展
     */
    Object.defineProperty(TinyForm, 'extend', {
        // 一旦定义了就不准改了
        configurable: false,
        /**
         * extend 配置为函数，通过 TinyForm.extend 调用
         * @param {Object} extension 扩展，这是一个对象，如：
         * TinyForm.extend({
     *     xxxx: function(){
     *
     *     }
     * });
         * 这样就给TinyForm的实例添加了一个方法  xxxx，
         * 然后就可以通过  form.xxxx() 来调用
         */
        value: function (extension) {
            var me = this;

            // 添加扩展
            $.each(extension, function (name, fn) {
                // 插件方法
                if (extfn.hasOwnProperty(name)) {
                    extfn[name].push(fn);
                    return;
                }

                // 扩展方法
                // 检查方法是否存在
                if (me.hasOwnProperty(name)) {
                    // 方法存在，插件不能添加这个方法
                    console.error(METHOD_EXISTS);
                    // 既然不能添加，那就直接返回吧
                    return;
                }

                // 添加扩展方法到原型对象上头
                TinyForm.prototype[name] = fn;
            });
        }
    });

    /**
     * 获取所有的字段
     * @param {Object} fm 表单实例
     */
    function getAllFields(fm) {
        // 清空原有的数据
        var fields = fieldSet[fm.id] = {};

        // 取出在实例化时传入的需要ignore的参数，然后始终搞成数组
        // 后面会用这个数组去判断某个字段是否需要加载
        var ignoreFields = $.makeArray(fm.option.ignore);

        // 根据配置的选择器来查找字段
        fm.context.find(fm.option.selector).each(function () {
            // 尝试取出name属性，顺便trim一下，要是有人喜欢搞怪，给弄点空白呢
            var name = $.trim($(this).attr('name'));

            // 如果name为空，则跳过
            if (name === '') {
                // 没有name属性，那就对不起了
                return;
            }

            // 如果这个name是被ignore的，就跳过这个
            if (ignoreFields.indexOf(name) !== -1) {
                return;
            }

            // 字段缓存集合中还不存在这个name的字段
            // 不存在，可能是还没有这个name的属性或者长度为0，感觉这个判断有点冗余，先不管了
            if (typeof fields[name] === 'undefined' || fields[name].length === 0) {
                // 结果中还不存在name，搞个数组出来
                // 这里搞数组，就是为了将相同name的字段集中起来
                fields[name] = $(this);
                // 可以取下一个字段了
                return;
            }

            // 存在name，如果是radio的话就追加到jQuery数组后头
            if ($(this).is(':radio')) {
                // 将DOM字段对象（非jQuery对象）添加到jQuery数组后头
                // 这里可以肯定只有一个字段，所以直接使用  this
                fields[name].push(this);
                // 添加进去后，就可以取下一个字段了
                return;
            }

            //如果不是radio，那整相同的name就有毛病
            console.error('字段的name属性"' + name + '"出现多次，这不对吧');
        });
    }

    // 提供默认的配置修改入口
    // 扩展通过  TinyForm.defaults.xxx 来设置默认参数
    TinyForm.defaults = {
        /**
         * 字段选择器，选择带有name属性的input select和textarea，排除input按钮
         */
        selector: 'input[name]:not(:button,:submit,:reset,[data-ignore]' + (!!window.FormData ? '' : ',[type=file]') + '), select[name]:not([data-ignore]), textarea[name]:not([data-ignore])',
        // 在表单内查找字段时，要忽略的字段或字段集合
        // 值可以为false、字符串或数组：
        // boolean: 仅设置false有效，表示没有需要忽略的
        // array: 要忽略的字段的name组成的数组
        // 要注意的是：这里的优先级应该比标签上设置的优先级更低
        // 也就是说，即使这里设置的是false，只在要标签上有属性 data-ignore
        ignore: false,
        // 是否要支持 jQuery3
        // 在使用 jQuery3时，请将这项设置为 true
        // 因为在 jQuery3的一此行为发生了不兼容的变化
        jquery3: false
    };

    // 搞懂，因为真正创建实例是通过 setup ，
    // 所以需要把 TinyForm 的原型交给 setup，
    // 以通过 setup 来产生一个 TinyForm 的实例
    TinyForm.prototype.setup.prototype = TinyForm.prototype;

    /**
     * 搞一个全局的 TinyForm
     */
    win.TinyForm = TinyForm;

    /**
     * 在任意位置获取表单实例的接口
     * 即可以不记住表单实例，只需要通过表单的id就可以获取到指定的表单
     * @param {String} id 表单的id，这个id可以在实例对象的id属性上找到，也可以在创建表单标签的data-tinyform属性上找到
     */
    TinyForm.get = function (id) {
        return !!id ?
            instanceSet[id] :
            // 返回copy，防止实例集合被胡乱搞
            $.extend(true, {}, instanceSet);
    };
})(window, jQuery);
/**
 * TinyForm 数据读写组件，负责从表单字段读取值以及向其写入值
 */(function (win, $) {

    /**
     * 存放表单初始数据的集合
     */
    var originalData = {};

    // 默认配置
    // 因为 data 是核心组件，所以配置项就不单独放到一个对象中
    $.extend(true, TinyForm.defaults, {
        // 自定义 checkbox 选中(第0个元素)和未选中(第1个元素)状态的值，默认为 ['on', 'off']
        checkbox: ['on', 'off'],
        // 调用ajax前的数据处理
        beforeSubmit: false
    });

    /**
     * 这个是数据读写（获取和设置）的组件
     */
    TinyForm.extend({
        /**
         * 初始化
         */
        setup: function () {
            // 保存初始数据，用于重置
            originalData[this.id] = this.getData();
        },
        /**
         * 获取所有字段的值，返回对象
         * @param {String} [fieldName] 字段的name名称，如果指定了此参数，则只获取name=此值的字段的值
         * @returns {Object|void} 字段的name和值对象
         */
        getData: function (fieldName) {
            // 没有参数，要获取所有字段的数据
            if (arguments.length === 0) {
                // 返回所有字段的数据
                return getAllData(this);
            }
            // 参数需要字段的name字符串，类型不对
            if (typeof fieldName !== 'string') {
                // 返回空
                return;
            }

            // 返回指定字段的值
            return getFieldData(this, fieldName);
        },

        /**
         * 设置字段的值
         * @param {String|Object} data 要设置的值
         * @param {String} [fieldName] 字段的name名称，如果指定了此参数，则只设置name=此值的字段的值
         * @returns {Object}  表单实例
         */
        setData: function (data, fieldName) {
            // 后头要在回调函数里面用这个实例对象，所以先弄个变量存起来
            var me = this;

            // 这个函数需要至少一个参数，你一个都不传，这是想造反么？
            if (arguments.length === 0) {
                // 这属性开发错误，我要在控制台给你报个错
                console.error('setData 需要至少1个参数');
                // 还是返回个实例给你
                return me;
            }

            // 如果传的参数>=2个，就是要设置指定name的字段的值，后面多余的参数直接忽略
            if (arguments.length >= 2) {
                //  第二个参数还是要个字符串，格式不对没法玩
                if (typeof fieldName !== 'string') {
                    // 返回给你个实例对象
                    return me;
                }

                // 设置指定name字段的值
                setFieldData(me, data, me.getField(fieldName));
                // 始终返回实例对象
                return me;
            }

            // 未指定name参数，设置表单所有项
            $.each(me.getField(), function (name, field) {
                // 从传入数据对象里面取出这个name的值
                var val = data[name];
                // 如果数据对象里面没有指定这个name，或值为null
                if (typeof val === 'undefined' || val === null) {
                    // 那就把值设置成空字符串
                    val = '';
                }

                // 设置字段的值
                setFieldData(me, val, field);
            });

            // 继续返回实例对象
            return me;
        },
        /**
         * 使用jQuery提交表单（默认异步: async=true）
         * @param {Object} option Ajax参数项
         * @returns {Object|void}  表单实例
         */
        submit: function (option) {
            // 到处都要写this，加个变量保存起来，在压缩的时候说不定能小好几十个字节
            var me = this;

            // 合并提交数据的参数，这些参数都是给ajax用的
            option = $.extend({
                // 提交的url，默认读取dom元素的action属性
                url: me.context.attr('action'),
                // 提交的类型（post/get），默认读取dom元素的method属性
                type: me.context.attr('method') || 'post',
                // 默认异步提交
                async: true,
                // 数据则使用表单的所有数据
                data: me.getData(),
                // 默认不使用数据缓存
                cache: false
            }, option);

            // option 构建完了，这里看看有没有设置提交前的回调函数
            if ($.isFunction(me.option.beforeSubmit)) {
                // 设置了提交前的回调函数，就调用一下
                // 回调函数的上下文this是表单实例对象，有个参数option，可以直接进行改动
                if (me.option.beforeSubmit.call(me, option) === false) {
                    return;
                }
            }

            // 发送ajax请求
            return $.ajax(option);
        },

        /**
         * 重置表单所有项
         * @returns {Object} 表单实例
         */
        reset: function () {
            // 看一下表单dom元素对象上有没有一个叫做reset的方法
            // 如果有，那就说明这个表单的DOM元素是form标签
            // 这时就有浏览器内置的reset能用
            if ($.isFunction(this.context.get(0).reset)) {
                // 调用浏览器内置的表单reset方法
                this.context.get(0).reset();
            } else {
                // 不是form标签，只能自己去设置初始值了(初始化是在表单实例化的时候获取到，保存起来的)
                this.setData(originalData[this.id]);
            }

            // 返回实例对象
            return this;
        }
    });

    /**
     * 设置某个字段的值
     * @param {Object} fm 表单实例
     * @param {String|Number|Boolean|Array} data 要设置的值，可以是任意类型，除了select其它类型都会自动搞成string
     * @param {Array} field 字段对象数组
     */
    function setFieldData(fm, data, field) {
        // 如果字段不存在（长度为0），那么啥都不做
        if (!field || field.length === 0) {
            // 返回吧
            return;
        }

        // 如果是 null 或者 undefined ，那么在生成选择器表达式的时候，
        // 就会变成  [value="null"]:first 或者 [value="undefined"]:first
        // 这明显是不对的哇
        // 所以给搞成空字符串，这样就可以得到正确的选择器 [value=""]:first，查找值为空的元素
        if (data === null || typeof data === 'undefined') {
            data = '';
        }

        // 字段是radio，那么可能有多个
        if (field.is(':radio')) {
            // 所有radio先置为未选中的状态，这样来避免设置了不存在的值时，还有radio是选中的状态
            field.prop('checked', false).each(function () {
                // 找出value与数据相等的字段设置选中
                if ($(this).val() === data) {
                    $(this).prop('checked', true);
                    return false;
                }
            });
            // 可以返回了
            return;
        }

        // 如果是checkbox，那么直接字段选中
        if (field.is(':checkbox')) {
            // 强制数据转换成字符串来比较，以控制字段的选中状态
            field.prop('checked', data.toString() === fm.option.checkbox[0].toString());
            // 可以返回了
            return;
        }

        // 如果是select字段，那就触发一下change事件
        if (field.is('select')) {
            field.val(data);
            field.change();
        } else {
            // 其它类型的input和非input字段，直接设置值
            field.val(data.toString());
        }
    }

    /**
     * 获取表单的所有数据
     * @param {Object} fm
     */
    function getAllData(fm) {
        // 创建一个对象来存放数据
        var data = {};
        // 遍历字段取值
        $.each(fm.getField(), function (name) {
            // 获取某个name的字段的值(在radio时可能是多个)
            data[name] = getFieldData(fm, name);
        });
        // 返回所有数据
        return data;
    }

    /**
     * 设置某个字段的值
     * @param {Object} fm 表单实例
     * @param {String} fieldName 字段的name名称
     * @return {Object|void} 字段的值
     */
    function getFieldData(fm, fieldName) {
        // 根据字段的name找到字段
        var field = fm.getField(fieldName);

        // field 不存在，即此时在请求不存在
        if (!field) {
            console.error('cannot found field "' + fieldName + '"');
            return;
        }

        // 如果字段是input标签的元素，使用独特的取值技巧
        if (field.is('input')) {
            // 返回获取到的值
            return getInputValue(fm, field);
        }

        // 如果是select的多选，在 jQuery3下，没有选中项时，获取到的是空数组
        // 这里让行为保持一致，若是空数组，也返回一个 null (null 是jquery3以前的版本得到的值)
        if (fm.option.jquery3 && field.is('select[multiple]')) {
            var data = field.val();
			return data.length === 0 ? null : data;
        }

        // 其它的字段直接取值并返回
        return field.val();
    }

    /**
     * 获取input字段的值
     * @param {Object} fm 表单实例
     * @param {Array} field 字段数组
     * @return {String|Boolean|Number} 字段的值
     */
    function getInputValue(fm, field) {
        // 取radio的值
        if (field.is(':radio')) {
            // 取选中的radio的值就行了
            return field.filter(':checked').val();
        }

        // checkbox 的值返回是根据 option.checkbox定义，默认返回 true和false
        if (field.is(':checkbox')) {
            return field.is(':checked') ? fm.option.checkbox[0] : fm.option.checkbox[1];
        }

        // 其它的直接返回值
        return field.val();
    }
})(window, jQuery);
TinyForm.version = "0.7.7"