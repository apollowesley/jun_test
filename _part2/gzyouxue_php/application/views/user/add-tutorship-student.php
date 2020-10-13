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
    <link href="<?=base_url(); ?>public/css/bootstrap-multiselect.css" rel="stylesheet">
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
        			<h4>添加辅导信息</h4>
       			</div>
                <div class="row">
                	<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                    	<p class="site section-title">选择孩子</p>
                    </div>
                    
                   	<?php if (isset($students) && !empty($students)): ?>
                   	<?php foreach ($students as $student): ?>
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
                                			<p class="site a"><a href="kid.html"><strong><?=$student->nickname; ?></strong></a></p>
                                			<p><strong><?=$student->school; ?></strong></p>
                            			</div>
                            			<div class="clearfix"></div>
                    				</div>
                    			</div>
                    			<div class="col-xs-12 col-sm-12 col-md-6 col-lg-7">
                                	<p>真实姓名：<?=$student->real_name; ?></p>
                                    <p>所在区域：<?=$student->location; ?></p>
                                    <p class="site a">参加了&nbsp;<a href="#all-tutorship"><strong>5次辅导</strong></a>&nbsp;, 共&nbsp;<span class="text-primary">100</span>&nbsp;课时</p>
                    			</div>
                    		</div>
                            
                    		<hr/>
                            
                            <div class="row">
                            	<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                    <div class="btn-group pull-right">
                                        <a href="<?=base_url(); ?>user/tutorship/add_tutorship.html?student=<?=$student->uid; ?>" class="btn btn-primary"">选择</a>
                                    </div>
                                    <div class="clearfix"></div>
                            	</div>
                            </div>
                            
                            <div class="site tutorship-status"><h4><span class="label label-danger">报名中</span></h4></div>
                            
                    	</div>
                    </div>
                    <?php endforeach; ?>
                    <?php else: ?>
                    <div class="col-sm-12">
                    	<p class="site a">您还未添加孩子信息，请转到&nbsp;<a href="<?=base_url(); ?>user/kids.html">我的孩子</a>&nbsp;添加。</p>
                    </div>
                    <?php endif; ?>
                    
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
    <script src="<?=base_url(); ?>public/js/bootstrap-multiselect.js"></script>
    <script src="<?=base_url(); ?>public/js/main.js"></script>
    <script src="<?=base_url(); ?>public/js/main.data.js"></script>
  </body>
</html>