layui.define(['jquery', 'layer'], function (exports) {
    var $ = layui.jquery, layer = layui.layer;
    var tips = {
        show: function () {
            $('*[lay-tips]').on('mouseenter', function () {
                var content = $(this).attr('lay-tips');
                this.index = layer.tips('<div style="padding: 10px; font-size: 14px; color: #eee;">' + content + '</div>', this, {
                    time: -1, maxWidth: 280, tips: [2, '#3A3D49']
                });
            }).on('mouseleave', function () {
                layer.close(this.index);
            });
        }

    };
    exports('iartisntips', tips);
});