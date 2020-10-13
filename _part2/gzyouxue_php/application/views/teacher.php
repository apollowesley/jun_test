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

    <!--[if lt IE 9]>
      <script src="<?=base_url(); ?>public/js/html5shiv.min.js"></script>
      <script src="<?=base_url(); ?>public/js/respond.min.js"></script>
    <![endif]-->
  </head>
  <body>
    
	<?php $this->load->view("header"); ?>
	
     <div class="container">
     	<div class="jumbotron site header">
        	<div class="row">
                <div class="col-lg-12">
                    <div>
                        <div class="pull-left">
                            <img src="<?=base_url(); ?>public/images/default-teacher-icon.png" class="img-circle site u-default-icon" />
                        </div>
                        <div class="site icon-left-default">
                            <p class="text-primary"><strong>闵老师</strong>&nbsp;&nbsp;<i class="fa fa-user site icon-female"></i></p>
                            <p><strong>赣南师范学院教育技术学专业</strong></p>
                        </div>
                        <div class="clearfix"></div>
                        <p>专长：数学，英语，计算机</p>
                        <p><span class="text-primary">1</span>&nbsp;份辅导正在进行中</p>
               	 		<p class="site a">成功辅导&nbsp;<a href="#all-tutorship"><strong>9名孩子</strong></a>，共&nbsp;<span class="text-primary">185</span>&nbsp;课时</p>
                        <p><small>自我评价：我是一个认真负责，知识过硬，亲近孩子的老师。期望辅导更多的孩子，让他们快乐地成长。</small></p>
                    </div>
                </div>
   			</div>
            <hr class="site hr-dash-white"/>
            <div class="text-center">
            	<p class="site a"><a href="#"><i class="fa fa-heart"></i>&nbsp;5</a>&nbsp;&nbsp;&nbsp;&nbsp;
                <a href="#"><i class="fa fa-comments"></i>&nbsp;0</a></p>
            </div>
        </div>
    </div>
    
    <div class="container margin-bottom-15">
    	<div class="page-header">
        	<h4 id="all-tutorship">所有辅导<small>（共5次）</small></h4>
        </div>
        <div class="row">
        	<div class="col-lg-12 site margin-bottom-15">
        		 <h4>
                 	<span class="label label-info">进行中</span>
                 	<span class="label label-info"><i class="fa fa-heart">&nbsp;0</i></span>
                    <span class="label label-info"><i class="fa fa-comments">&nbsp;0</i></span>
                 </h4>
            </div>
          	<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
           		<p class="site course-info"><span>数学</span><span>20课时</span><span>章贡区</span></p>
                <p>开始时间：2015年1月9日</p>
                <p class="site talk-words">辅导开始不久，暂无评价哦。</p>
                <p><a class="btn btn-primary" href="tutorship-detail-1.html">查看详情</a></p>
       		</div>
            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
            	 <div>
                 	<div class="pull-left">
                    	<a href="<?=base_url(); ?>student.html">
                   			<img src="<?=base_url(); ?>public/images/kid.gif" class="img-circle site u-small-icon" />
                        </a>
                	</div>
            		<div class="site icon-left-small">
                    	<p class="site a"><a href="<?=base_url(); ?>student.html"><strong>王小帅</strong></a></p>
                        <p><strong>赣州市文清实验学校小学部四年级</strong></p>
                    </div>
                    <div class="clearfix"></div>
                 </div>
                 <p>所在区域：章贡区</p>
                 <p class="site a">参加&nbsp;<a href="<?=base_url(); ?>student.html#all-tutorship"><strong>5次辅导</strong></a>, 共&nbsp;<span class="text-primary">100</span>&nbsp;课时</p>
                 <p class="site talk-words">我希望能有一个老师帮助我提高数学成绩。</p>
            </div>
      	</div>
        <hr class="site hr-dash-light-dark"/>
        <div class="row">
        	<div class="col-lg-12 site margin-bottom-15">
        		 <h4>
                 	<span class="label label-info">已完成</span>
                    <span class="label label-info"><i class="fa fa-heart">&nbsp;30</i></span>
                    <span class="label label-info"><i class="fa fa-comments">&nbsp;12</i></span>
           		 </h4>
            </div>
          	<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
           		<p class="site course-info"><span>数学</span><span>20课时</span><span>章贡区</span></p>
                <p>开始时间：2014年12月26日</p>
                <p>结束时间：2015年1月7日</p>
                <p class="site talk-words">王小帅这个孩子实际是很聪明的，可能是之前没有较好地掌握思考方法，经过辅导后，能较好地运用系统思维来解决数学问题。</p>
                <p><a class="btn btn-primary" href="tutorship-detail-1.html">查看详情</a></p>
       		</div>
            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
            	<div>
                 	<div class="pull-left">
                    	<a href="<?=base_url(); ?>student.html">
                   			<img src="<?=base_url(); ?>public/images/kid.gif" class="img-circle site u-small-icon" />
                        </a>
                	</div>
            		<div class="site icon-left-small">
                    	<p class="site a"><a href="<?=base_url(); ?>student.html"><strong>王小帅</strong></a></p>
                        <p><strong>赣州市文清实验学校小学部四年级</strong></p>
                    </div>
                    <div class="clearfix"></div>
                 </div>
                 <p>所在区域：章贡区</p>
                 <p class="site a">参加&nbsp;<a href="<?=base_url(); ?>student.html#all-tutorship"><strong>5次辅导</strong></a>, 共&nbsp;<span class="text-primary">100</span>&nbsp;课时</p>
                 <p class="site talk-words">闵老师是一个很好的老师，讲课也很有趣生动，让我很容易地理解课程的意思，并提高了学习成绩。</p>
            </div>
      	</div>
    </div>
    
    <?php $this->load->view("footer"); ?>
    
	<a id="page-back"></a>
    <script src="<?=base_url(); ?>public/js/jquery.min.js"></script>
    <script src="<?=base_url();?>public/js/bootstrap.min.js"></script>
    <script src="<?=base_url(); ?>public/js/jquery.scrollUp.min.js"></script>
    <script src="<?=base_url();?>public/js/jquery.page-back.js"></script>
    <script src="<?=base_url();?>public/js/main.js"></script>
  </body>
</html>