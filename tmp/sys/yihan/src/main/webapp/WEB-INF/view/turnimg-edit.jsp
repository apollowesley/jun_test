<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>
  <meta charset="utf-8">
  <title>layui</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <link rel="stylesheet" href="../layui/css/layui.css" media="all">
  <link rel="stylesheet" href="../layui/css/admin.css" media="all">
  <!-- 注意：如果你直接复制所有代码到本地，上述css路径需要改成你本地的 -->
</head>

<body>
<div class="layui-fluid" id="LAY-component-progress">
  <div class="layui-row layui-col-space15">
    <div class="layui-col-md12">
      <div class="layui-card">
        <div class="layui-card-body">
          <form class="layui-form" action="" lay-filter="form1">
            <div class="layui-form-item">
              <label class="layui-form-label">ID</label>
              <div class="layui-input-inline">
                <input readonly="true" type="text" value="${turnimg.id}" name="id" class="layui-input layui-bg-gray">
              </div>
            </div>
            <div class="layui-form-item">
              <label class="layui-form-label">图片名</label>
              <div class="layui-input-inline">
                <input value="${turnimg.src}" readonly="true" type="text" value="" name="src" required
                       lay-verify="required" id="src"
                       autocomplete="off" class="layui-input layui-bg-gray">
              </div>
              <div class="layui-form-mid layui-word-aux">上传图片后自动生成</div>
            </div>

            <div class="layui-form-item">
              <label class="layui-form-label">描述</label>
              <div class="layui-input-inline">
                <input type="text" value="${turnimg.text}" name="text" class="layui-input">
              </div>
              <div class="layui-form-mid layui-word-aux">可不填</div>
            </div>

            <div class="layui-form-item">
              <label class="layui-form-label">图片</label>
              <div class="layui-input-inline">
                <img
                  <c:if test="${turnimg!=null}">src="src/${turnimg.src}"</c:if> class="layui-upload-img" id="img-load"
                  style="max-width: 190px;max-height: 190px">
              </div>
<%--              <button type="button" class="layui-btn" id="img-upload">上传图片</button>--%>
              <div class="layui-upload-drag" id="img-upload">
                <i class="layui-icon"></i>
                <p>点击上传，或将文件拖拽到此处</p>
              </div>
              <div class="layui-input-block">
                <div class="layui-form-mid layui-word-aux">图片固定大小为 1200 x 350</div>
              </div>
            </div>

            <div class="layui-form-item">
              <label class="layui-form-label">权重</label>
              <div class="layui-input-inline">
                <input type="text"
                       value="<c:choose><c:when test="${turnimg.level==null}">50</c:when><c:otherwise>${turnimg.level}</c:otherwise></c:choose>"
                       name="level" required lay-verify="required" autocomplete="off" class="layui-input">
              </div>
              <div class="layui-form-mid layui-word-aux">(0-100),调整排序,数字越小越靠前</div>
            </div>

            <div class="layui-form-item">
              <label class="layui-form-label">链接</label>
              <div class="layui-input-block">
                <input type="text" value="${turnimg.href}" name="href" class="layui-input">
              </div>
              <div class="layui-input-block">
                <div class="layui-form-mid layui-word-aux">可空,点击图片时跳转的连接,例如'https://www.bilibili.com/'</div>
              </div>
            </div>

            <div class="layui-form-item">
              <label class="layui-form-label">启用</label>
              <div class="layui-input-inline">
                <input type="checkbox" name="used" title="启用"
                       <c:if test="${turnimg.used==1}">checked</c:if> <c:if test="${turnimg==null}">checked</c:if>>
              </div>
            </div>


            <div class="layui-form-item">
              <div class="layui-input-block">
                <button class="layui-btn" lay-submit lay-filter="formDemo" style="width: 40%">提交</button>
                <span style="margin-left: 10px">上传图片之后,必须要提交才能生效</span>
              </div>

            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</div>

<script src="../layui/layui.js" charset="utf-8"></script>
<!-- 注意：如果你直接复制所有代码到本地，上述js路径需要改成你本地的 -->
<script>
    layui.use('form', function () {
        var form = layui.form;
        var $ = layui.jquery;

        form.on('submit(formDemo)', function (data) {
            var data = form.val('form1');
            if (data.used == 'on') {
                data.used = 1;
            } else {
                data.used = 0;
            }

            // layer.msg(JSON.stringify(data));
            $.ajax({
                url: '/turnimgWrite.action',
                type: 'post',
                data: data,
                dataType: 'json',
                success: function (data) {
                    layer.msg(data.msg);
                    if (data.success) {
                        var parentWin = window.parent;
                        parentWin.refresh();
                    }
                }
            });
            return false;
        });
    })
</script>
<script>
    layui.use('upload', function () {
        var $ = layui.jquery
            , upload = layui.upload;

        //普通图片上传
        var uploadInst = upload.render({
            elem: '#img-upload'
            , url: '/upload.action'
            , size: 2048
            , before: function (obj) {
                layer.msg('图片上传中,请等待上传成功后再提交');
                obj.preview(function (index, file, result) {
                    $('#img-load').attr('src', result); //图片链接（base64）

                });
            }
            , done: function (res) {
                if (res.success) {
                    layer.msg(res.msg + '<br>' + res.src);
                    var obj = document.getElementById("src");
                    obj.value = res.src;
                } else {
                    layer.msg(res.msg);
                }
            }
        });
    });
</script>
</body>

</html>