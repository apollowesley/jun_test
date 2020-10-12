(function ($) {
    function DatePicker(options) {

        var $el = $(this);
        var pos = $el.offset();

        var defaults = {
            weekStart: 1,
            yearRange: [-6, 6],
            format: 'yyyy-mm-dd',
            lang: 'zh_CN'
        };
        $.extend(defaults, options);

        $.extend(defaults, {
            langPack: DatePicker.lang[defaults.lang],
            startYear: new Date().getFullYear() + defaults.yearRange[0],
            endYear: new Date().getFullYear() + defaults.yearRange[1],
            positionTop: pos.top + $el.outerHeight() + 'px',
            positionLeft: pos.left + 'px'
        }, getValue());

        var tplBase = '<div class="datepicker" style="top:{{=$data.positionTop}};left:{{=$data.positionLeft}}" >' +
            '<div class="top">' +
            '<div class="datepicker-select datepicker-select-year">' +
            '<div class="datepicker-select-selected">{{=$data.year}}</div>' +
            '<ul class="datepicker-select-options">' +
            '{{ for(var i=$data.startYear;i<=$data.endYear;i++){ }}' +
            '<li data-year="{{=i }}" {{if(i==$data.year){ }}class="selected"{{ } }}>{{=i}}</li>' +
            '{{ } }}</ul>' +
            '</div>' +
            '<div class="datepicker-select datepicker-select-month">' +
            '<div class="datepicker-select-selected">{{=$data.month}}</div>' +
            '<ul class="datepicker-select-options">' +
            '{{ for(var i=1;i<=12;i++){ }}' +
            '<li data-month="{{=i }}" {{if(i==$data.month){ }}class="selected"{{ } }}>{{=i}}</li>' +
            '{{ } }}</ul>' +
            '</ul>' +
            '</div>' +
            '</div>' +
            '<table class="body">' +
            '<thead>' +
            '<tr>' +
            '{{ for(var i=$data.weekStart;i<$data.weekStart + 7;i++){ }}' +
            '<td class="cell week-cell">{{=$data.langPack.week[i % 7]}}</td>' +
            '{{ } }}' +
            '</tr>' +
            '</thead>' +
            '<tbody>' +
            '</tbody>' +
            '</table>' +
            '<div class="bottom">' +
            '<div class="btn-clear">{{=$data.langPack.clearText}}</div>' +
            '</div>' +
            '</div>';

        var tplDate = '<tr>' +
            '{{var list=$data.dates;for(var i=0,j=list.length;i<j;i++){ }}' +
            '<td class="cell {{= list[i].cls }} {{ if($data.date==list[i].date){  }}selected{{ } }}" data-date="{{= list[i].date }}">{{= list[i].day }}</td>' +
            '{{ if ((i + 1) % 7 == 0 && i != j - 1) { }}' +
            '</tr><tr>' +
            '{{ } }}' +
            '{{ } }}' +
            '</tr>';

        var baseCls = '.datepicker';
        var selectCls = baseCls + '-select';
        var selectOptionsCls = selectCls + '-options';
        var selectOptionCls = selectCls + '-options li';
        var selectSelectedCls = selectCls + '-selected';
        var $datepicker = $(template(tplBase, defaults)).appendTo('body');
        var $year = $datepicker.find('.datepicker-select-year');
        var $month = $datepicker.find('.datepicker-select-month');
        var $dateArea = $datepicker.find('.body tbody');
        $datepicker.defaults = defaults;

        $datepicker.on("click", selectCls, function () {
            var options = $(this).find(selectOptionsCls);
            $(this).siblings(selectCls).find(selectOptionsCls).fadeOut(150);
            if (options.is(':hidden')) {
                options.fadeIn(50);
            } else {
                options.fadeOut(150);
            }

        }).on("click", selectOptionCls, function () {
            // 点击选择年份
            $(this).addClass('selected').siblings('.selected').removeClass('selected');
            defaults.year = $year.find('.selected').data('year');
            defaults.month = $month.find('.selected').data('month');
            $year.find(selectSelectedCls).text(defaults.year);
            $month.find(selectSelectedCls).text(defaults.month);
            getDatesOfMonth();

        }).on("click", '.curr-month,.prev-month,.next-month', function () {
            // 鼠标移出年份，月份选择区域
            $dateArea.find('.selected').removeClass('selected');
            $(this).addClass('selected');
            var value = $(this).data('date');
            defaults.date = value;
            $el.val(value);
            hide();

        }).on("click", '.btn-clear', function () {
            $el.val('');
            hide();
        });

        $el.on('focus', function () {
            show();
        });

        $(document).on('mousedown', function (e) {
            var target = $(e.target);
            if (target.closest(baseCls).length == 0 && !target.is($el)) {
                hide();
            }
        });

        function show() {
            getDatesOfMonth();
            $datepicker.find(selectOptionsCls).hide();
            $datepicker.show();
            $el.trigger('show');
        }

        function hide() {
            $datepicker.hide();
            $el.trigger('hide');
        }


        function getDatesOfMonth() {
            var dates = [];
            // 初始化为month第一天的日期
            var date = new Date(defaults.year, defaults.month - 1, 1);
            var dayOfWeek = date.getDay();

            var lastMonthDays = 0;
            if (dayOfWeek != defaults.weekStart) {
                date.setDate(0);  // 上月最后一天
                var prevDate = date.getDate();
                var lastMonthDays = dayOfWeek - defaults.weekStart;
                if (lastMonthDays < 0) {
                    lastMonthDays += 7;
                }
                // -1是因为i可以等于0
                for (var i = lastMonthDays - 1; i >= 0; i--) {
                    dates.push({
                        'date': formatDate(date.getFullYear(), date.getMonth() + 1, prevDate - i),
                        'day': prevDate - i,
                        'cls': 'prev-month'
                    });
                }
            }
            date.setFullYear(defaults.year, defaults.month, 0);

            for (var i = 1, j = date.getDate(); i <= j; i++) {
                dates.push({
                    'date': formatDate(date.getFullYear(), date.getMonth() + 1, i),
                    'day': i,
                    'cls': 'curr-month'
                });
            }

            // 上月日期总数+当月日期总数之和不为7的倍数才会显示下月的部分日期
            if (dates.length % 7 > 0) {
                date.setFullYear(defaults.year, defaults.month, 1);
                for (var i = 1, j = 7 - dates.length % 7; i <= j; i++) {
                    dates.push({
                        'date': formatDate(date.getFullYear(), date.getMonth() + 1, i),
                        'day': i,
                        'cls': 'next-month'
                    });
                }
            }

            defaults.dates = dates;
            $dateArea.html(template(tplDate, defaults));
        }


        function template(source, data) {
            var code = "var $out='" + source.replace(/[\r\n]/g, '').replace(/^(.+?)\{\{|\}\}(.+?)\{\{|\}\}(.+?)$/g, function (a) {
                    return a.replace(/(['"])/g, '\\\$1');
                }).replace(/\{\{\s*=\s*(.+?)\}\}/g, "';$out+=$1;$out+='").replace(/\{\{(.+?)\}\}/g, "';$1$out+='") + "';return new String($out);";
            var Render = new Function('$data', code);
            return new Render(data).toString();
        }


        function formatDate(year, month, day) {
            if (arguments.length != 3) {
                month = year.month;
                day = year.day;
                year = year.year;
            }
            return defaults.format.replace(/[ymd]+/gi, function (m) {
                m = m.toLowerCase();
                if (m == 'yyyy') {
                    return year;
                } else if (m == 'mm') {
                    return month < 10 ? '0' + month : month;
                } else {
                    return day < 10 ? '0' + day : day;
                }
            });
        }

        function getValue() {
            var date = {};
            var v = $el.val();
            var yearStartIndex = 0;
            var monthStartIndex = 0;
            var dayStartIndex = 0;
            var formatRegStr = defaults.format.replace(/[ymd]+|[^ymd]/gi, function (m, i) {
                m = m.toLowerCase();
                if (m == 'yyyy') {
                    yearStartIndex = i;
                    return '\\d{4}';
                } else if (m == 'mm') {
                    monthStartIndex = i;
                    return '\\d{2}';
                } else if (m == 'dd') {
                    dayStartIndex = i;
                    return '\\d{2}';
                } else {
                    return '\\' + m;
                }
            });
            var formatReg = new RegExp(formatRegStr);
            if (formatReg.test(v)) {
                date = {
                    year: v.substr(yearStartIndex, 4) - 0,
                    month: v.substr(monthStartIndex, 2) - 0,
                    day: v.substr(dayStartIndex, 2) - 0
                };
            } else {
                var now = new Date();
                date = {
                    year: now.getFullYear(),
                    month: now.getMonth() + 1,
                    day: now.getDate()
                };
            }
            date.date = formatDate(date);
            return date;
        }

        return $datepicker;
    }

    DatePicker.lang = {
        zh_CN: {
            clearText: '清除',
            week: ['日', '一', '二', '三', '四', '五', '六']
        },
        en_US: {
            clearText: 'Clear',
            week: ['Su', 'Mo', 'Tu', 'We', 'Th', 'Fr', 'Sa']
        }
    };

    $.fn.datepicker = DatePicker;
    $.fn.datepicker.lang = DatePicker.lang;
})
(jQuery);

