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
        			<h4>特别鸣谢</h4>
       			</div>
                <div class="site page-content-section">
                	<h4>@王先生</h4>
                    <p>@王先生不仅是一个优秀的产品经理，同时也是一个优秀的技术者和管理者，自从有了这个平台的idea，@王先生一直致力于本平台的产品设计，技术开发和后期维护工作。</p>
                    <p>在这里为@王先生做一下广告宣传：如果您有互联网产品方面（如优秀网站开发，微网站开发，微信平台，社交平台开放接口，产品及技术顾问等）的需求，请联系@王先生:</p>
                    <p>TEL: 18507074625</p>
                    <p>QQ: 1435034544</p>
                </div>
                <div class="site page-content-section">
                	<h4>@闵老师</h4>
                    <p>@闵老师是一个优秀的年轻女教师，平时致力于教育事业，认真负责，颇受学生的喜爱。其他时间，@闵老师致力于本平台的教育顾问工作，为我们提供了众多有价值的资料。</p>
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