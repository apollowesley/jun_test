require('../css/index.less');

var $ = require("./jquery-3.1.1.js");
var showdown = require("./showdown.js");
var Clipboard = require("./clipboard.min.js");
var CodeTheme = require("./theme/code-theme");
var PageTheme = require("./theme/page-theme");

require("./showdown-plugins/showdown-prettify-for-wechat.js");
require("./showdown-plugins/showdown-github-task-list.js");
require("./showdown-plugins/showdown-footnote.js");

require("./google-code-prettify/run_prettify.js");


var kv = location.href.split('?')[1];
kv = kv && kv.split('&') || [];
var params = {};
$.each(kv, function (index, item) {
    var m = (item || '').split('=');
    if (m && m[0] && m[1]) {
        params[m[0]] = m[1];
    }
});

// 方便跨域加载资源
if (/\.zhyd\.me$/.test(location.hostname)) {
    document.domain = 'zhyd.me';
}


var converter = new showdown.Converter({
    extensions: ['prettify', 'tasklist', 'footnote'],
    tables: true
});
/**
 * [OnlineMarkdown description]
 * @type {Object}
 */
var OnlineMarkdown = {
    currentState: 'edit',
    cacheInputKey: 'input-wx-markdown-cache',
    cacheOutputKey: 'output-wx-markdown-cache',
    autoSaveKey: 'auto-save-cache',
    init: function () {
        var self = this;
        self.load().then(function () {
            self.start()
        }).fail(function () {
            self.start();
        }).then(function () {
            var autoSave = localStorage.getItem(self.autoSaveKey);
            $("#autoSave").prop("checked", autoSave == 1);

            var cacheInput = localStorage.getItem(self.cacheInputKey);
            var cacheOutput = localStorage.getItem(self.cacheOutputKey);
            if(cacheInput || cacheOutput) {
                if(confirm("您有未处理的草稿，是否重新编辑草稿？取消则自动删除草稿")) {
                    $("#outputCtt").html(cacheOutput);
                    $("#input").val(cacheInput);
                } else {
                    localStorage.removeItem(self.cacheInputKey);
                    localStorage.removeItem(self.cacheOutputKey);
                }
            }
        });

    },
    start: function () {
        this.bindEvt();
        this.updateOutput();
        new CodeTheme();
        new PageTheme();
        new Clipboard('.copy-button');
        this.bindSaveEvent();
    },
    load: function () {
        return $.ajax({
            type: 'GET',
            url: params.path || './demo.md',
            dateType: 'text',
            data: {
                _t: new Date() * 1
            },
            timeout: 2000
        }).then(function (data) {
            $('#input').val(data);
        });
    },
    bindEvt: function () {
        var self = this;
        $('#input').on('input keydown paste', self.updateOutput);
        var $copy = $('.copy-button');
        var $convert = $('.convert-button');
        $convert.on('click', function () {
            var $this = $(this);
            if (self.currentState === 'preview') {
                self.currentState = 'edit';
                $this.text('预览');
                $copy.hide();
                $('#input').fadeIn();
                $('#output').hide();
            } else {
                self.currentState = 'preview';
                $this.text('编辑');
                $copy.show();
                $('#input').fadeOut();
                $('#output').show();
            }
        });
        if (params.preview) {
            $convert.trigger('click');
        }
    },
    updateOutput: function () {
        var val = converter.makeHtml($('#input').val());
        $('#output .wrapper').html(val);
        PR.prettyPrint();
        $('#outputCtt li').each(function () {
            $(this).html('<span><span>' + $(this).html() + '</span></span>');
        });
    },
    bindSaveEvent: function () {
        var self = this;
        function save(self){
          localStorage.setItem(self.cacheInputKey, $("#input").val());
          localStorage.setItem(self.cacheOutputKey, $("#outputCtt").html());
          var $msgBox = $("span.msg-box");
          $msgBox.fadeIn().delay (2000).fadeOut ();
        }
        $(".btn-save").on('click', function () {
          save(self);
        });
        var timetask = null;
        var autoSave = localStorage.getItem(self.autoSaveKey);
        if(autoSave == 1) {
          timetask = setInterval(function () {
            save(self);
          }, 5000);
        }


        $("#autoSave").on('click', function () {
          var $this = $(this);
          if($this.prop("checked")) {
            localStorage.setItem(self.autoSaveKey, 1);
            timetask = setInterval(function () {
              save(self);
            }, 5000);
          } else {
            localStorage.setItem(self.autoSaveKey, 0);
            if(timetask) {
              window.clearInterval(timetask);
            }
          }
        });
    }
};

OnlineMarkdown.init();
