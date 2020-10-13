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
    <link href="<?=base_url(); ?>public/css/iCheck/square/blue.css" rel="stylesheet">
    <link href="<?=base_url(); ?>public/css/bootstrap-multiselect.css" rel="stylesheet">
    <link href="<?=base_url(); ?>public/css/main.css" rel="stylesheet">

    <!--[if lt IE 9]>
      <script src="<?=base_url(); ?>public/js/html5shiv.min.js"></script>
      <script src="<?=base_url(); ?>public/js/respond.min.js"></script>
    <![endif]-->
  </head>
  <body>
    
  	<?php $this->load->view("header"); ?>

	<div class="container"> 
    	<div class="row row-offcanvas row-offcanvas-left">
        	
    		<?php $this->load->view("user/sidebar"); ?>
    	
            <div class="col-xs-12 col-sm-7 col-md-8 col-lg-9">
            	<div class="visible-xs">
                	<h4 class="site a"><a href="#" data-toggle="offcanvas" data-to-show="y">展开左侧列表</a></h4>
                </div>
            	<div class="jumbotron">
                	
              	</div>
                <div class="page-header">
        			<h4>添加孩子信息</h4>
       			</div>
                <div class="row">
                	<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                    	<form class="form-horizontal" role="form" method="post">
                          <p class="site section-title">孩子基本信息<small>（请认真如实填写，所有资料保密）</small></p>
                          <div class="form-group">
                            <label for="figure" class="col-sm-2 control-label">头像</label>
                            <div class="col-sm-10">
                            	<input type="text" class="form-control" id="figure" name="figure" placeholder="请上传头像">
                            </div>
                          </div>
                          <div class="form-group">
                            <label for="nickname" class="col-sm-2 control-label">昵称</label>
                            <div class="col-sm-10">
                            	<input type="text" class="form-control" id="nickname" name="nickname" placeholder="请输入昵称">
                            </div>
                          </div>
                          <div class="form-group">
                            <label for="real_name" class="col-sm-2 control-label">真实姓名</label>
                            <div class="col-sm-10">
                            	<input type="text" class="form-control" id="real_name" name="real_name" placeholder="请输入真实姓名">
                            </div>
                          </div>
                          <div class="form-group">
                            <label for="gender" class="col-sm-2 control-label">性别</label>
                                <div class="col-sm-10">
                                	<div id="radio_gender" class="radio">
                                      <input type="radio" name="radio_gender" id="gender-male" value="男">
                                      <label for="gender-male">男</label>
                                      &nbsp;&nbsp;&nbsp;&nbsp;
                                      <input type="radio" name="radio_gender" id="gender-female" value="女">
                                      <label for="gender-female">女</label>
                                   	</div>
                            	</div>
                          </div>
                          <div class="form-group">
                            <label for="school" class="col-sm-2 control-label">学校</label>
                            <div class="col-sm-10">
                            	<input type="text" class="form-control" id="school" name="school" placeholder="请输入学校">
                            </div>
                          </div>
                          <div class="form-group">
                            <label for="grade" class="col-sm-2 control-label">年级</label>
                            <div class="col-sm-10">
                            	<select id="select_grade" class="form-control" size="2">
                           		</select>
                            </div>
                          </div>
                          <div class="form-group">
                            <label for="evaluation" class="col-sm-2 control-label">自我评价</label>
                            <div class="col-sm-10">
                                <textarea class="form-control" rows="4" id="evaluation" name="text_evaluation" placeholder="请输入自我评价"></textarea>
                            </div>
                          </div>
                          
                          <p class="site section-title">全部完成</p>
                          <div class="form-group">
                            <div class="col-sm-10 col-sm-offset-2">
                              <button id="user_add_kid" type="button" class="btn btn-primary">保存</button>
                              <a href="<?=base_url(); ?>user/kids.html" class="btn btn-primary">取消</a>
                            </div>
                          </div>
                        </form>
                    </div>
                </div>
                
            </div>
        </div>
    </div>
    
    <?php $this->load->view("footer"); ?>

	<a id="page-back"></a>
    <script src="<?=base_url(); ?>public/js/jquery.min.js"></script>
    <script src="<?=base_url(); ?>public/js/jquery.scrollUp.min.js"></script>
    <script src="<?=base_url(); ?>public/js/bootstrap.min.js"></script>
    <script src="<?=base_url(); ?>public/js/bootstrap-paginator.min.js"></script>
    <script src="<?=base_url(); ?>public/js/jquery.page-back.js"></script>
    <script src="<?=base_url(); ?>public/js/jquery.options-creator.js"></script>
    <script src="<?=base_url(); ?>public/js/offcanvas.js"></script>
    <script src="<?=base_url(); ?>public/js/icheck.min.js"></script>
    <script src="<?=base_url(); ?>public/js/bootstrap-multiselect.js"></script>
    <script src="<?=base_url(); ?>public/js/main.js"></script>
    <script src="<?=base_url(); ?>public/js/main.data.js"></script>
    <script>
    	$("#select_grade").createOptions({
			data:grades,
			optgroup:true
		});
    	$('#select_grade').multiselect({
			nonSelectedText: '请选择年级',
			maxHeight:300
		});
		$('input[name="radio_gender"]').iCheck({
			radioClass: 'iradio_square-blue',
			increaseArea: '20%' // optional
		});
    </script>
  </body>
</html>