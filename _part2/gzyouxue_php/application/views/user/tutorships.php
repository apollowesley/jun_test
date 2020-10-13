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
        	
        	<?php $this->load->view("user/sidebar"); ?>
        	
            <div class="col-xs-12 col-sm-7 col-md-8 col-lg-9">
            	<div class="visible-xs">
                	<h4 class="site a"><a href="#" data-toggle="offcanvas" data-to-show="y">展开左侧列表</a></h4>
                </div>
            	<div class="jumbotron">
                	
              	</div>
                <div class="page-header">
        			<h4>我的所有辅导<small>（共1000份）</small></h4>
       			</div>
                <div class="row">
                	<?php if (isset($tutorships) && !empty($tutorships)): ?>
                	<?php foreach ($tutorships as $tutorship): ?>
                	<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                		<div class="site admin tutorship-list-item">
                    		<div class="row">
                				<div class="col-xs-12 col-sm-12 col-md-6 col-lg-5">
                    				<div>
                            			<div class="pull-left">
                                			<a href="<?=base_url(); ?>admin/student.html">
                                    			<img src="<?=base_url(); ?>public/images/kid.gif" class="img-circle site u-small-icon"/>
                                			</a>
                            			</div>
                            			<div class="site icon-left-small">
                                			<p class="site a"><a href="<?=base_url(); ?>admin/student.html"><strong><?=$tutorship->stu_nickname; ?></strong></a></p>
                                			<p><strong><?=$tutorship->school; ?></strong></p>
                            			</div>
                            			<div class="clearfix"></div>
                    				</div>
                    			</div>
                    			<div class="col-xs-12 col-sm-12 col-md-6 col-lg-7">
                                    <p class="site course-info"><span><?=$tutorship->subject; ?></span><span><?=$tutorship->hours; ?>课时</span><span><?=$tutorship->location; ?></span></p>
                                    <p class="site talk-words"><?=!empty($tutorship->student_latest_thinking) ? $tutorship->student_latest_thinking : "我还没发布心声哦。"; ?></p>
                    			</div>
                    		</div>
                            
                    		<hr/>
                            
                            <?php if (empty($tutorship->te_uid)): ?>
                    		<div class="row">
                    			<div class="col-xs-12 col-sm-12 col-md-6 col-lg-5">
                    				<div>
                            			<div class="pull-left">
                                            <a href="#">
                                                <img src="<?=base_url(); ?>public/images/default-icon.png" class="img-circle site u-small-icon"/>
                                            </a>
                            			</div>
                                        <div class="site icon-left-small">
                                            <p class="site a"><a href="#"><strong>9名教师已报名</strong></a></p>
                                            <p><strong>点击左侧头像查看已报名教师的信息</strong></p>
                                        </div>
                                    </div>
                    			</div>
                    			<div class="col-xs-12 col-sm-12 col-md-6 col-lg-7">
                    				
                    			</div>
                    		</div>
                    		<?php else: ?>
                    		<div class="row">
                    			<div class="col-xs-12 col-sm-12 col-md-6 col-lg-5">
                    				<div>
                            			<div class="pull-left">
                                            <a href="<?=base_url(); ?>admin/teacher.html">
                                                <img src="<?=base_url(); ?>public/images/default-teacher-icon.png" class="img-circle site u-small-icon"/>
                                            </a>
                                        </div>
                                        <div class="site icon-left-small">
                                            <p class="site a"><a href="<?=base_url(); ?>admin/teacher.html"><strong><?=$tutorship->te_nickname; ?></strong></a></p>
                                            <p><strong><?=$tutorship->where_from; ?></strong></p>
                                        </div>
                                    </div>
                    			</div>
                    			<div class="col-xs-12 col-sm-12 col-md-6 col-lg-7">
                    				<p>专长：<?=$tutorship->speciality; ?></p>
                   					<p class="site talk-words"><?=!empty($tutorship->teacher_latest_thinking) ? $tutorship->teacher_latest_thinking : "辅导开始不久，老师还没发布心声哦。"; ?></p>
                    			</div>
                    		</div>
                    		<?php endif; ?>
                            
                            <hr />
                            <div class="row">
                            	<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                    <div class="btn-group pull-right">
                                        <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
                                        	所有操作&nbsp;<span class="caret"></span>
                                      	</button>
                                      	<ul class="dropdown-menu" role="menu">
                                        	<li><a href="<?=base_url(); ?>user/tutorship.html"><i class="fa fa-eye"></i>&nbsp;查看详情</a></li>
                                        	<li><a href="#"><i class="fa fa-edit"></i>&nbsp;编辑</a></li>
                                        	<li class="divider"></li>
                                            <li><a href="#"><i class="fa fa-remove"></i>&nbsp;冻结</a></li>
                                      	</ul>
                                    </div>
                                    <div>
                                        <p class="site datetime">发布于：<?=$tutorship->add_time; ?></p>
                                        <p class="site datetime">更新于：<?=$tutorship->update_time; ?></p>
                                        <p>
                                            <a href="#"><span class="fa fa-heart">&nbsp;5</span></a> &nbsp;&nbsp;&nbsp;&nbsp;
                                            <a href="<?=base_url(); ?>admin/tutorship.html#comment-area"><span class="fa fa-comments">&nbsp;0</span></a>
                                        </p>
                                    </div>
                                    <div class="clearfix"></div>
                            	</div>
                            </div>
                            
                            <div class="site tutorship-status"><h4><span class="label label-danger"><?=$tutorship->status; ?></span></h4></div>
                            
                    	</div>
                    </div>
                    <?php endforeach; ?>
                    <?php else: ?>
                    <div class="col-sm-12">
                    	<p>您还没有发布辅导哦，请您添加辅导。</p>
                    </div>
                    <?php endif; ?>
                    
                </div>
                
            </div>
        </div>
    </div>
    
    <?php $this->load->view("footer"); ?>

    <script src="<?=base_url(); ?>public/js/jquery.min.js"></script>
    <script src="<?=base_url(); ?>public/js/bootstrap.min.js"></script>
    <script src="<?=base_url(); ?>public/js/jquery.scrollUp.min.js"></script>
    <script src="<?=base_url(); ?>public/js/bootstrap-paginator.min.js"></script>
    <script src="<?=base_url(); ?>public/js/offcanvas.js"></script>
    <script src="<?=base_url(); ?>public/js/jquery.page-back.js"></script>
    <script src="<?=base_url(); ?>public/js/main.js"></script>
  </body>
</html>