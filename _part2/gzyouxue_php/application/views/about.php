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
    	
        	<?php $this->load->view("about-sidebar"); ?>
        	
            <div class="col-xs-12 col-sm-7 col-md-8 col-lg-9">
            	<div class="visible-xs">
                	<h4 class="site a"><a href="#" data-toggle="offcanvas" data-to-show="y">展开左侧列表</a></h4>
                </div>
            	<div class="jumbotron">
                	
              	</div>
                <div class="page-header">
        			<h4>关于我们</h4>
       			</div>
                <div class="site page-content-section">
                    <h4>优学网</h4>
                    <p>优学网的宗旨是<strong>为孩子提供优质的教学及兴趣辅导，让孩子更加快乐地成长！</strong></p>
                </div>
                <div class="site page-content-section">
                    <h4>服务</h4>
                    <p>优学网的用户为家长及孩子，教师（大学生）。</p>
                    <ul>
                        <li>
                            <p><strong>孩子的基础教育</strong></p>
                            <p>家长及孩子可以在优学网找到合适的基础教育辅导教师。</p>
                        </li>
                        <li>
                            <p><strong>孩子的素质教育</strong></p>
                            <p>家长及孩子可以在优学网找到合适的兴趣辅导教师或兴趣辅导班。</p>
                        </li>
                        <li>
                            <p><strong>教师（大学生）的业余时间</strong></p>
                            <p>教师及在校大学生可以在优学网找到合适的需要被辅导的孩子。</p>
                        </li>
                    </ul>
                    <p class="site a">为更好地服务于孩子及教师，我们的服务会<a href="help.html#charge">收取少量费用（收取廉价的费用，提供品质的服务）</a>。</p>
                    <p class="site a">更多服务相关的问题及帮助，请访问<a href="help.html">问题及帮助</a>页面。</p>
                </div>
                <div class="site page-content-section">
                    <h4>发展</h4>
                    <p>优学网成立于<strong>2014年12月</strong></p>
                    <p>优学网目前扎根于赣州，自成立以来，受到了家长，孩子及教师、大学生的广泛关注。目前优学网(请看网站首页最新数据)：</p>
                    <ul>
                        <li>500&nbsp;名教师已加入</li>
                        <li>1000&nbsp;份辅导已完成</li>
                        <li>820&nbsp;名孩子已获得优质辅导</li>
                    </ul>
                </div>
                <div class="site page-content-section">
                    <h4>联系优学网</h4>
                    <p class="site a">QQ联系？微信联系？没问题，请访问<a href="contact.html">联系方式</a>页面</p>
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