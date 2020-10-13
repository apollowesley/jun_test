<!DOCTYPE html>
<html lang="zh-cn">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><?=$title; ?></title>

    <link href="<?=base_url(); ?>public/css/bootstrap.min.css" rel="stylesheet">
    <link href="<?=base_url(); ?>public/css/font-awesome.min.css" rel="stylesheet">
    <link href="<?=base_url(); ?>public/css/offcanvas.css" rel="stylesheet">
    <link href="<?=base_url(); ?>public/css/bootstrap-multiselect.css" rel="stylesheet">
    <link href="<?=base_url(); ?>public/css/iCheck/square/blue.css" rel="stylesheet">
    <link href="<?=base_url(); ?>public/css/main.css" rel="stylesheet">

    <!--[if lt IE 9]>
      <script src="<?=base_url(); ?>public/js/html5shiv.min.js"></script>
      <script src="<?=base_url(); ?>public/js/respond.min.js"></script>
    <![endif]-->
  </head>
  <body>
    
  	<?php $this->load->view("admin/header"); ?>

	<div class="container"> 
    	<div class="row row-offcanvas row-offcanvas-left">
        	
    		<?php $this->load->view("admin/sidebar"); ?>
    	
            <div class="col-xs-12 col-sm-7 col-md-8 col-lg-9">
            	<div class="visible-xs">
                	<h4 class="site a"><a href="#" data-toggle="offcanvas" data-to-show="y">展开左侧列表</a></h4>
                </div>
            	<div class="jumbotron">
                	
              	</div>
                <div class="page-header">
        			<h4>添加学生信息</h4>
       			</div>
                <div class="row">
                	<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                    	<form class="form-horizontal" role="form" method="post">
                          <p class="site section-title">学生基本信息<small>（请认真如实填写，所有资料保密）</small></p>
                          <div class="form-group">
                            <label for="email" class="col-sm-2 control-label">登录邮箱</label>
                            <div class="col-sm-10">
                                <input type="email" class="form-control" id="email" placeholder="请输入登录邮箱">
                            </div>
                          </div>
                          <div class="form-group">
                            <label for="password" class="col-sm-2 control-label">登录密码</label>
                            <div class="col-sm-10">
                            	<input type="password" class="form-control" id="password" placeholder="请输入登录密码">
                            </div>
                          </div>
                          <div class="form-group">
                            <label for="nickname" class="col-sm-2 control-label">昵称</label>
                            <div class="col-sm-10">
                            	<input type="text" class="form-control" id="nickname" placeholder="请输入昵称">
                            </div>
                          </div>
                          <div class="form-group">
                            <label for="head-icon" class="col-sm-2 control-label">头像</label>
                            <div class="col-sm-10">
                            	<input type="text" class="form-control" id="head-icon" placeholder="请上传头像">
                            </div>
                          </div>
                          <div class="form-group">
                            <label for="real-name" class="col-sm-2 control-label">真实姓名</label>
                            <div class="col-sm-10">
                            	<input type="text" class="form-control" id="real-name" placeholder="请输入真实姓名">
                            </div>
                          </div>
                          <div class="form-group">
                            <label for="gender" class="col-sm-2 control-label">性别</label>
                                <div class="col-sm-10">
                                	<div id="gender" class="radio">
                                      <input type="radio" name="gender" id="gender-male" checked>
                                      <label for="gender-male">男</label>
                                      &nbsp;&nbsp;&nbsp;&nbsp;
                                      <input type="radio" name="gender" id="gender-female">
                                      <label for="gender-female">女</label>
                                   	</div>
                            	</div>
                          </div>
                          <div class="form-group">
                            <label for="location" class="col-sm-2 control-label">所在区域</label>
                            <div class="col-sm-10">
                            	<input type="text" class="form-control" id="location" placeholder="请选择所在区域">
                            </div>
                          </div>
                          <div class="form-group">
                            <label for="school" class="col-sm-2 control-label">学校</label>
                            <div class="col-sm-10">
                            	<input type="text" class="form-control" id="school" placeholder="请输入学校">
                            </div>
                          </div>
                          <div class="form-group">
                            <label for="grade" class="col-sm-2 control-label">年级</label>
                            <div class="col-sm-10">
                            	<select id="grade" class="form-control" size="2">
                           		</select>
                            </div>
                          </div>
                          <div class="form-group">
                            <label for="evaluation" class="col-sm-2 control-label">自我评价</label>
                            <div class="col-sm-10">
                                <textarea class="form-control" rows="3" id="evaluation" placeholder="请输入自我评价"></textarea>
                            </div>
                          </div>
                          
                          <p class="site section-title">联系人信息<small>（请认真如实填写，所有资料保密）</small></p>
                          <div class="form-group">
                            <label for="identity" class="col-sm-2 control-label">我是</label>
                                <div class="col-sm-10">
                                	<div id="identity" class="radio">
                                      <input type="radio" name="identity" id="parents">
                                      <label for="parents">学生家长</label>
                                      &nbsp;&nbsp;&nbsp;&nbsp;
                                      <input type="radio" name="identity" id="student">
                                      <label for="student">学生本人</label>
                                   	</div>
                            	</div>
                          </div>
                          <div class="form-group">
                            <label for="linkman-name" class="col-sm-2 control-label">姓名</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="linkman-name" placeholder="请输入联系人姓名">
                            </div>
                          </div>
                          <div class="form-group">
                            <label for="linkman-phone" class="col-sm-2 control-label">手机号</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="linkman-phone" placeholder="请输入联系人手机号">
                            </div>
                          </div>
                          <div class="form-group">
                            <label for="linkman-qq" class="col-sm-2 control-label">QQ</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="linkman-qq" placeholder="请输入联系人QQ号">
                            </div>
                          </div>
                          <div class="form-group">
                            <label for="linkman-weibo" class="col-sm-2 control-label">新浪微博</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="linkman-weibo" placeholder="请输入联系人新浪微博账号">
                            </div>
                          </div>
                          <div class="form-group">
                            <label for="linkman-wechat" class="col-sm-2 control-label">微信</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="linkman-wechat" placeholder="请输入联系人微信账号">
                            </div>
                          </div>
                          <div class="form-group">
                            <label for="linkman-wechat" class="col-sm-2 control-label">邮箱</label>
                            <div class="col-sm-10">
                                <input type="email" class="form-control" id="linkman-wechat" placeholder="请输入联系人邮箱">
                            </div>
                          </div>
                          
                          <p class="site section-title">全部完成</p>
                          <div class="form-group">
                            <div class="col-sm-10 col-sm-offset-2">
                              <button type="button" class="btn btn-primary">保存</button>
                              <button type="button" class="btn btn-primary">取消</button>
                            </div>
                          </div>
                        </form>
                    </div>
                </div>
                
            </div>
        </div>
    </div>
    
    <?php $this->load->view("footer"); ?>

    <script src="<?=base_url(); ?>public/js/jquery.min.js"></script>
    <script src="<?=base_url(); ?>public/js/jquery.scrollUp.min.js"></script>
    <script src="<?=base_url(); ?>public/js/bootstrap.min.js"></script>
    <script src="<?=base_url(); ?>public/js/jquery.page-back.js"></script>
    <script src="<?=base_url(); ?>public/js/jquery.options-creator.js"></script>
    <script src="<?=base_url(); ?>public/js/offcanvas.js"></script>
    <script src="<?=base_url(); ?>public/js/bootstrap-multiselect.js"></script>
    <script src="<?=base_url(); ?>public/js/icheck.min.js"></script>
    <script src="<?=base_url(); ?>public/js/main.js"></script>
    <script src="<?=base_url(); ?>public/js/main.data.js"></script>
    <script>
		$("#grade").createOptions({
			data:grades,
			optgroup:true
		});
		$('#grade').multiselect({
			nonSelectedText: '请选择年级',
			maxHeight:300
		});
		$('input[name="gender"],input[name="identity"]').iCheck({
			radioClass: 'iradio_square-blue',
			increaseArea: '20%' // optional
		});
    </script>
  </body>
</html>