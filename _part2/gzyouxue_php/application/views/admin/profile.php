<!DOCTYPE html>
<html lang="zh-cn">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><?=$title; ?></title>

    <link href="<?=base_url(); ?>public/css/bootstrap.min.css" rel="stylesheet">
    <link href="<?=base_url(); ?>public/css/font-awesome.min.css" rel="stylesheet">
    <link href="<?=base_url(); ?>public/css/main.css" rel="stylesheet">
    <link href="<?=base_url(); ?>public/css/offcanvas.css" rel="stylesheet">

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
        			<h4>我的信息</h4>
       			</div>
                
                <div class="row">
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                    	<div class="col-xs-12 col-sm-3 col-md-2 col-lg-2">
                            <p>头像：</p>
                        </div>
                        <div class="col-xs-12 col-sm-9 col-md-10 col-lg-10">
                            <p><img src="<?=$logined_admin["figure"]; ?>" class="img-circle site u-small-icon"/></p>
                        </div>
                        <div class="col-xs-12 col-sm-3 col-md-2 col-lg-2">
                            <p>登录邮箱：</p>
                        </div>
                        <div class="col-xs-12 col-sm-9 col-md-10 col-lg-10">
                            <p><?=$admin->email; ?></p>
                        </div>
                        <div class="col-xs-12 col-sm-3 col-md-2 col-lg-2">
                            <p>真实姓名：</p>
                        </div>
                        <div class="col-xs-12 col-sm-9 col-md-10 col-lg-10">
                            <p><?=$logined_admin["real_name"]; ?></p>
                        </div>
                        <div class="col-xs-12 col-sm-3 col-md-2 col-lg-2">
                            <p>性别：</p>
                        </div>
                        <div class="col-xs-12 col-sm-9 col-md-10 col-lg-10">
                            <p><?=$admin->gender; ?></p>
                        </div>
                        <div class="col-xs-12 col-sm-3 col-md-2 col-lg-2">
                            <p>所在区域：</p>
                        </div>
                        <div class="col-xs-12 col-sm-9 col-md-10 col-lg-10">
                            <p><?=$admin->area; ?></p>
                        </div>
                        <div class="col-xs-12 col-sm-3 col-md-2 col-lg-2">
                            <p>详细地址：</p>
                        </div>
                        <div class="col-xs-12 col-sm-9 col-md-10 col-lg-10">
                            <p><?=$admin->location; ?></p>
                        </div>
                        <div class="col-xs-12 col-sm-3 col-md-2 col-lg-2">
                            <p>手机号：</p>
                        </div>
                        <div class="col-xs-12 col-sm-9 col-md-10 col-lg-10">
                            <p><?=$admin->phone; ?></p>
                        </div>
                        <div class="col-xs-12 col-sm-3 col-md-2 col-lg-2">
                            <p>QQ：</p>
                        </div>
                        <div class="col-xs-12 col-sm-9 col-md-10 col-lg-10">
                            <p><?=$admin->qq; ?></p>
                        </div>
                        <div class="col-xs-12 col-sm-3 col-md-2 col-lg-2">
                            <p>微博：</p>
                        </div>
                        <div class="col-xs-12 col-sm-9 col-md-10 col-lg-10">
                            <p><?=$admin->weibo; ?></p>
                        </div>
                        <div class="col-xs-12 col-sm-3 col-md-2 col-lg-2">
                            <p>微信：</p>
                        </div>
                        <div class="col-xs-12 col-sm-9 col-md-10 col-lg-10">
                            <p><?=$admin->wechat; ?></p>
                        </div>
                        <div class="col-xs-12 col-sm-3 col-md-2 col-lg-2">
                            <p>添加时间：</p>
                        </div>
                        <div class="col-xs-12 col-sm-9 col-md-10 col-lg-10">
                            <p><?=$admin->add_time; ?></p>
                        </div>
                        <div class="col-xs-12 col-sm-3 col-md-2 col-lg-2">
                            <p>最近登录时间：</p>
                        </div>
                        <div class="col-xs-12 col-sm-9 col-md-10 col-lg-10">
                            <p><?=$admin->login_time; ?></p>
                        </div>
                        <div class="col-xs-12 col-sm-3 col-md-2 col-lg-2">
                            <p>最近修改时间：</p>
                        </div>
                        <div class="col-xs-12 col-sm-9 col-md-10 col-lg-10">
                            <p><?=$admin->update_time; ?></p>
                        </div>
                    </div>
                    
               </div>
               <a href="#" class="btn btn-primary">编辑我的信息</a>
                
            </div>
        </div>
    </div>
    
    <?php $this->load->view("footer"); ?>
    
    <script src="<?=base_url(); ?>public/js/jquery.min.js"></script>
    <script src="<?=base_url(); ?>public/js/jquery.scrollUp.min.js"></script>
    <script src="<?=base_url(); ?>public/js/bootstrap.min.js"></script>
    <script src="<?=base_url(); ?>public/js/jquery.page-back.js"></script>
    <script src="<?=base_url(); ?>public/js/main.js"></script>
    <script src="<?=base_url(); ?>public/js/offcanvas.js"></script>
  </body>
</html>