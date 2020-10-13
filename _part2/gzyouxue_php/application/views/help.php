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
        	
        	<?php $this->load->view("about-sidebar");?>
        	
            <div class="col-xs-12 col-sm-7 col-md-8 col-lg-9">
            	<div class="visible-xs">
                	<h4 class="site a"><a href="#" data-toggle="offcanvas" data-to-show="y">展开左侧列表</a></h4>
                </div>
            	<div class="jumbotron">
                	
              	</div>
                <div class="page-header">
        			<h4>问题及帮助</h4>
       			</div>
                <div class="site page-content-section">
                	<h4>可以直接使用QQ账号或者新浪微博账号登录吗？</h4>
                    <p>为了用户更加方便地登录系统而无需注册任何账号，系统支持和鼓励用户直接使用QQ账号或者新浪微博账号登录。</p>
                    <p>使用QQ账号或新浪微博账号登录系统是安全可靠的，并且受到腾讯、新浪的隐私保护及安全保护。</p>
                </div>
                <div class="site page-content-section">
                	<h4>为什么要绑定QQ号或新浪微博账号？</h4>
                    <p></p>
                </div>
                <div class="site page-content-section">
                	<h4>你们的服务是免费的吗？</h4>
                    <p>很遗憾地告诉大家，为了支撑我们平台的运营，并且让平台为更多的学生、家长及教师服务，我们的平台需要一定的资金，所以我们的服务不是免费的，我们只收取少量服务费。</p>
                </div>
                <div class="site page-content-section">
                	<h4>课时是怎么算的？</h4>
                    <p>课时按小时计算，一个课时为一个小时，如计划课时为20课时，则相当于上20小时的课程。</p>
                </div>
                <div class="site page-content-section">
                	<h4 id="user-type">有哪些用户类型？</h4>
                    <p>1、普通用户：不能发布辅导需求，不能报名辅导；具有查看，关注和发表评论等功能；</p>
                    <p>2、学生：能发布辅导需求；具有查看，关注和发表评论等功能；</p>
                    <p>3、学生家长：可以管理自己的多个孩子，为孩子发布辅导需求；具有查看，关注和发表评论等功能；</p>
                    <p>4、教师：能报名辅导；具有查看，关注和发表评论等功能。</p>
                    <p>用户登录后，请选择自己的用户类型（选择用户类型后，暂不支持修改）并尽快完善自己的信息，享受更多的功能。</p>
                </div>
                
            </div>
        </div>
    </div>
    
    <?php $this->load->view("footer"); ?>
    
	<a id="page-back"></a>
    <script src="<?=base_url(); ?>public/js/jquery.min.js"></script>
    <script src="<?=base_url(); ?>public/js/bootstrap.min.js"></script>
    <script src="<?=base_url(); ?>public/js/jquery.scrollUp.min.js"></script>
    <script src="<?=base_url(); ?>public/js/offcanvas.js"></script>
    <script src="<?=base_url(); ?>public/js/jquery.page-back.js"></script>
    <script src="<?=base_url(); ?>public/js/main.js"></script>
  </body>
</html>