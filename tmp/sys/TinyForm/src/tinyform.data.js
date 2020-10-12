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