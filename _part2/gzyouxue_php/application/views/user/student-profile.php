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
        			<h4>我的信息</h4>
       			</div>
                
                <div class="row">
                    <div class="col-sm-12">
                    	<p class="site section-title">基本信息</p>
                    </div>
                    <div class="col-sm-12">
                    	
                    	<?php $this->load->view("user/profile-basic-info"); ?>
                    	
                    </div>
                    <div class="col-sm-12">
                    		<p class="site section-title">学生信息</p>
                    </div>
                    <div class="col-sm-12">
                        <div class="col-xs-12 col-sm-3 col-md-2 col-lg-2">
                            <p>学校：</p>
                        </div>
                        <div class="col-xs-12 col-sm-9 col-md-10 col-lg-10">
                            <p><?=empty($user->school) ? PROFILE_EMPTY : $user->school; ?></p>
                        </div>
                        <div class="col-xs-12 col-sm-3 col-md-2 col-lg-2">
                            <p>年级：</p>
                        </div>
                        <div class="col-xs-12 col-sm-9 col-md-10 col-lg-10">
                            <p><?=empty($user->grade) ? PROFILE_EMPTY : $user->grade; ?></p>
                        </div>
                        <div class="col-xs-12 col-sm-3 col-md-2 col-lg-2">
                            <p>自我评价：</p>
                        </div>
                        <div class="col-xs-12 col-sm-9 col-md-10 col-lg-10">
                            <p><?=empty($user->evaluation) ? PROFILE_EMPTY : $user->evaluation; ?></p>
                        </div>
                    </div>
               </div>
               <a href="<?=base_url(); ?>user/student/edit_student.html" class="btn btn-primary">编辑我的信息</a>
                
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