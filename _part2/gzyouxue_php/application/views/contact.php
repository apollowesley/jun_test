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
        			<h4>联系方式</h4>
       			</div>
       			<div class="site page-content-section">
                    <p>您有任何<strong>问题、建议、评价</strong>，或者<strong>想对我们说些什么</strong>，请联系我们，我们会第一时间回复您！</p>
                    <p>1、QQ：847315251</p>
                    <p>2、QQ群：847315251</p>
                    <p>3、微信：Wgssmart</p>
                    <p>4、微信公众号：Wgssmart</p>
                    <p class="site a">5、新浪微博：<a href="http://weibo.com/wgssmart" target="_blank">@Wgssmart</a></p>
                    <p>6、邮箱：847315251@qq.com</p>
                </div>
                <div class="row">
                	<div class="col-xs-6 col-sm-6 col-md-4 col-lg-3">
                        <div class="thumbnail site barcode-thumbnail">
                            <img src="<?=base_url(); ?>public/images/wgssmart-wechat.png" alt="扫描添加微信" title="扫描添加微信">
                            <div class="caption">
                                <p>扫描添加微信</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-6 col-sm-6 col-md-4 col-lg-3">
                        <div class="thumbnail site barcode-thumbnail">
                            <img src="<?=base_url(); ?>public/images/wgssmart-wechat.png" alt="扫描关注微信公众号" title="扫描关注微信公众号">
                            <div class="caption">
                                <p>扫描关注微信公众号</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-6 col-sm-6 col-md-4 col-lg-3">
                        <div class="thumbnail site barcode-thumbnail">
                            <img src="<?=base_url(); ?>public/images/wgssmart-wechat.png" alt="扫描访问新浪微博主页" title="扫描访问新浪微博主页">
                            <div class="caption">
                                <p>扫描访问新浪微博主页</p>
                            </div>
                        </div>
                    </div>
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