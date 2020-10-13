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
      <script src="<?=base_url(); ?>public/js/1.4.2/respond.min.js"></script>
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
                    	
                        <div class="col-xs-12 col-sm-3 col-md-2 col-lg-2">
                            <p>用户类型：</p>
                        </div>
                        <div class="col-xs-12 col-sm-9 col-md-10 col-lg-10">
                            <p class="site a">您是普通用户。<a href="<?=base_url(); ?>help.html#user-type"><small>（查看用户类型说明）</small></a></p>
                            <p class="site a">如果您是学生本人，请&nbsp;<a href="#" data-toggle="modal" data-target="#student-modal"><strong>设置为学生</strong></a>；</p>
                            <p class="site a">如果您是学生家长，请&nbsp;<a href="#" data-toggle="modal" data-target="#parent-modal"><strong>设置为学生家长</strong></a>；</p>
                            <p class="site a">如果您是教师，请&nbsp;<a href="#" data-toggle="modal" data-target="#teacher-modal"><strong>设置为教师</strong></a>。</p>
                        </div>
                        
                    </div>
               </div>
               <a href="<?=base_url(); ?>user/normal/edit_normal.html" class="btn btn-primary">编辑我的信息</a>
               
            </div>
        </div>
    </div>
    
                <div class="modal fade" id="student-modal" tabindex="-1" role="dialog" aria-labelledby="student_label" aria-hidden="true" data-backdrop="static">
                  <div class="modal-dialog">
                    <div class="modal-content">
                      <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="student_label">请确定设置为学生</h4>
                      </div>
                      <div class="modal-body">
                        <p>设置为学生后，请进一步完善学生相关信息，完善信息后，您可以发布辅导需求。</p>
                        <p><small>提示：设置用户类型后，系统暂不支持用户类型的变更。</small></p>
                      </div>
                      <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                        <button id="to_student" type="button" class="btn btn-primary">设置并继续</button>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="modal fade" id="parent-modal" tabindex="-1" role="dialog" aria-labelledby="parent_label" aria-hidden="true" data-backdrop="static">
                  <div class="modal-dialog">
                    <div class="modal-content">
                      <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="parent_label">请确定设置为学生家长</h4>
                      </div>
                      <div class="modal-body">
                        <p>设置为学生家长后，请添加您的孩子的基本信息，添加孩子基本信息后，您可以为孩子发布辅导需求。</p>
                        <p><small>提示：设置用户类型后，系统暂不支持用户类型的变更。</small></p>
                      </div>
                      <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                        <button id="to_parent" type="button" class="btn btn-primary">设置并继续</button>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="modal fade" id="teacher-modal" tabindex="-1" role="dialog" aria-labelledby="teacher_label" aria-hidden="true" data-backdrop="static">
                  <div class="modal-dialog">
                    <div class="modal-content">
                      <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="teacher_label">请确定设置为教师</h4>
                      </div>
                      <div class="modal-body">
                        <p>设置为教师后，请进一步完善教师相关信息，完善信息后，您可以报名参与辅导。</p>
                        <p><small>提示：设置用户类型后，系统暂不支持用户类型的变更。</small></p>
                      </div>
                      <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                        <button id="to_teacher" type="button" class="btn btn-primary">设置并继续</button>
                      </div>
                    </div>
                  </div>
                </div>
    
    <?php $this->load->view("footer"); ?>
                
    <script src="<?=base_url(); ?>public/js/jquery.min.js"></script>
    <script src="<?=base_url(); ?>public/js/jquery.scrollUp.min.js"></script>
    <script src="<?=base_url(); ?>public/js/bootstrap.min.js"></script>
    <script src="<?=base_url(); ?>public/js/offcanvas.js"></script>
    <script src="<?=base_url(); ?>public/js/jquery.page-back.js"></script>
    <script src="<?=base_url(); ?>public/js/main.js"></script>
  </body>
</html>