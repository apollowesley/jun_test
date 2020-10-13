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
        			<h4>编辑我的信息</h4>
       			</div>
                <div class="row">
                	<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                    	<form class="form-horizontal" role="form" method="post">
                    	  <p class="site section-title">基本信息</p>
                    	
                          <?php $this->load->view("user/profile-basic-info-edit"); ?>
                          
                          <p class="site section-title">全部完成</p>
                          <div class="form-group">
                            <div class="col-sm-10 col-sm-offset-2">
                              <button id="save_normal_info" type="button" class="btn btn-primary">保存</button>
                              <a href="<?=base_url(); ?>user/profile/normal.html" class="btn btn-primary">取消</a>
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
    <script src="<?=base_url(); ?>public/js/icheck.min.js"></script>
    <script src="<?=base_url(); ?>public/js/main.js"></script>
    <script src="<?=base_url(); ?>public/js/main.data.js"></script>
    <script>
		$('input[name="radio_gender"]').iCheck({
			radioClass: 'iradio_square-blue',
			increaseArea: '20%' // optional
		});
    </script>
  </body>
</html>