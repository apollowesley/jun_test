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
        <div class="layui-card-header">网站设置</div>
        <div class="layui-card-body">
          <form class="layui-form" action="siteEdit.action" lay-filter="form1">

            <div class="layui-form-item">
              <label class="layui-form-label">网站名</label>
              <div class="layui-input-block">
                <input type="text" value="${site.title}" name="title" class="layui-input">
              </div>
            </div>

            <div class="layui-form-item">
              <label class="layui-form-label">广告</label>
              <div class="layui-input-block">
                <input type="text" value="${site.ad}" name="ad" class="layui-input">
              </div>
            </div>

            <div class="layui-form-item">
              <label class="layui-form-label">版权信息</label>
              <div class="layui-input-block">
                <input type="text" value="${site.copyright}" name="copyright" class="layui-input">
              </div>
            </div>

            <input value="${site.logo}" type="hidden" value="" name="logo" id="src">
            <div class="layui-form-item">
              <label class="layui-form-label">logo</label>
              <div class="layui-input-inline">
                <img src="src/${site.logo}" class="layui-upload-img" id="img-load"
                     style="max-width: 190px;max-height: 190px">
              </div>
<%--              <button type="button" class="layui-btn" id="img-upload">上传图片</button>--%>
              <div class="layui-upload-drag" id="img-upload">
                <i class="layui-icon"></i>
                <p>点击上传，或将文件拖拽到此处</p>
              </div>
              <div class="layui-input-block">
                <div class="layui-form-mid layui-word-aux">图片需1:1的比例,png格式,背景透明<br/>上传图片后,必须提交才能生效</div>
              </div>
            </div>


            <div class="layui-form-item">
              <div class="layui-input-block">
                <button class="layui-btn" type="submit">提交</button>
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