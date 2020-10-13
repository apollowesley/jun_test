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
        			<h4>王小帅的个人信息</h4>
       			</div>
                <div class="row">
                	<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                    		<div class="row">
                				<div class="col-xs-12 col-sm-12 col-md-6 col-lg-5">
                    				<div>
                            			<div class="pull-left">
                                			<img src="<?=base_url(); ?>public/images/kid.gif" class="img-circle site u-small-icon" />
                            			</div>
                            			<div class="site icon-left-small">
                                			<p class="text-primary"><strong>王小帅</strong>&nbsp;&nbsp;<i class="fa fa-user site icon-male"></i></p>
                                			<p><strong>赣州市文清实验学校小学部四年级</strong></p>
                            			</div>
                            			<div class="clearfix"></div>
                    				</div>
                    			</div>
                    			<div class="col-xs-12 col-sm-12 col-md-6 col-lg-7">
                                	<p>真实姓名：王小帅</p
                                    ><p>所在区域：章贡区</p>
                                    <p><span class="text-primary">1</span>&nbsp;份辅导正在报名中</p>
                        			<p class="site a"><a href="#"><strong>9名教师</strong></a>&nbsp;已报名</p>
                                    <p class="site a">参加了&nbsp;<a href="#all-tutorship"><strong>5次辅导</strong></a>&nbsp;, 共&nbsp;<span class="text-primary">100</span>&nbsp;课时</p>
                                    <p>自我评价：我是一个挺好学的孩子，在数学方面可能未掌握学习方法及思考方法。</p>
                                    <p>联系人姓名：王大帅</p>
                                    <p>联系人手机号：18888888888</p>
                                    <p>联系人QQ号：88888888</p>
                                    <p>联系人新浪微博账号：88888888</p>
                                    <p>联系人微信账号：88888888</p>
                                    <p class="site a"><a href="#">此联系人继续添加一份孩子信息</a></p>
                    			</div>
                    		</div>
                            
                    		<hr/>
                            
                            <div class="row">
                            	<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                    <div class="btn-group pull-right">
                                        <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
                                        	所有操作&nbsp;<span class="caret"></span>
                                      	</button>
                                      	<ul class="dropdown-menu" role="menu">
                                        	<li><a href="<?=base_url(); ?>admin/student/edit.html"><i class="fa fa-edit"></i>&nbsp;编辑信息</a></li>
                                            
                                            <li><a href="<?=base_url(); ?>admin/tutorship/add_tutorship.html"><i class="fa fa-plus"></i>&nbsp;添加辅导</a></li>
                                        	<li class="divider"></li>
                                            <li><a href="#"><i class="fa fa-remove"></i>&nbsp;冻结</a></li>
                                      	</ul>
                                    </div>
                                    <div>
                                        <p class="site datetime">注册于：2015年1月7日</p>
                                        <p class="site datetime">更新于：2015年1月10日</p>
                                        <p>
                                            <a href="#"><span class="fa fa-heart">&nbsp;5</span></a> &nbsp;&nbsp;&nbsp;&nbsp;
                                            <a href="<?=base_url(); ?>admin/tutorship.html#comment-area"><span class="fa fa-comments">&nbsp;0</span></a>
                                        </p>
                                    </div>
                                    <div class="clearfix"></div>
                            	</div>
                            </div>
                    	</div>
                    </div>
                
       	<div class="page-header">
        	<h4 id="all-tutorship">所有辅导<small>（共5次）</small></h4>
        </div>
        <div class="row">
        	<div class="col-lg-12  site margin-bottom-15">
        		 <h4>
                 	<span class="label label-info">正在报名</span>
                 	<span class="label label-info"><i class="fa fa-heart">&nbsp;0</i></span>
                    <span class="label label-info"><i class="fa fa-comments">&nbsp;0</i></span>
                 </h4>
            </div>
          	<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
           		<p class="site course-info"><span>数学</span><span>20课时</span><span>章贡区</span></p>
                <p>开始时间：还未开始</p>
                <p class="site talk-words">我希望能有一个老师帮助我提高数学成绩。</p>
                <p><a class="btn btn-primary" href="<?=base_url(); ?>admin/tutorship.html">查看详情</a></p>
       		</div>
            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
            	 <div>
                	<div class="pull-left">
                   		<a href="#">
                        	<img src="<?=base_url(); ?>public/images/default-apply-icon.png" class="img-circle site u-small-icon"/>
                    	</a>
                 	</div>
                	<div class="site icon-left-small">
                    	<p class="site a"><a href="#"><strong>会是你吗？</strong></a></p>
                    	<p><strong>你来自哪里？</strong></p>
                	</div>
           		</div>
               	<p><span class="site a"><a href="#">报名辅导 <small>或点击头像报名</small></a></span></p>
                <p class="site a"><a href="#"><strong>9名教师</strong></a>&nbsp;已报名</p>
              	<p class="site talk-words">你对学生的评价也很重要。</p>
            </div>
      	</div>
        <hr class="site hr-dash-light-dark"/>
        <div class="row">
        	<div class="col-lg-12  site margin-bottom-15">
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
                <p class="site talk-words">闵老师是一个很好的老师，讲课也很有趣生动，让我很容易地理解课程的意思，并提高了学习成绩。</p>
                <p><a class="btn btn-primary" href="<?=base_url(); ?>admin/tutorship.html">查看详情</a></p>
       		</div>
            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
            	<div>
               		<div class="pull-left">
                    	<a href="<?=base_url(); ?>admin/teacher.html">
                    		<img src="<?=base_url(); ?>public/images/default-teacher-icon.png" class="img-circle site u-small-icon"/>
                        </a>
                	</div>
                    <div class="site icon-left-small">
                    	<p class="site a"><a href="<?=base_url(); ?>admin/teacher.html"><strong>闵老师</strong></a></p>
                  		<p><strong>赣南师范学院教育技术学专业</strong></p>
                    </div>
              	</div>
                <p>专长：数学，英语，计算机</p>
                <p class="site a">成功辅导&nbsp;<a href="<?=base_url(); ?>admin/teacher.html#all-tutorship"><strong>9名学生</strong></a>，共&nbsp;<span class="text-primary">185</span>&nbsp;课时</p>
                <p class="site talk-words">辅导开始不久，暂无评价哦。</p>
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
    <script src="<?=base_url(); ?>public/js/jquery.page-back.js"></script>
    <script src="<?=base_url(); ?>public/js/offcanvas.js"></script>
    <script src="<?=base_url(); ?>public/js/main.js"></script>
  </body>
</html>