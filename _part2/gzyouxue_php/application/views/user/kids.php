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
        			<h4>我的孩子</h4>
       			</div>
                <div class="row">
					<?php if (count($all_kids) == 0): ?>
					<div class="col-sm-12">
						<p>您还未添加孩子信息。</p>
						<p>请点击&nbsp;<strong>添加一个孩子</strong>&nbsp;按钮添加您的孩子信息。</p>
					</div>
					<?php else: 
						$idx = 0;
					?>
					<?php foreach ($all_kids as $kid): ?>
                	<div data-id="<?=$kid->uid; ?>" class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
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
                                			<p class="site a"><a href="<?=base_url(); ?>admin/student.html"><strong><?=$kid->nickname; ?></strong></a></p>
                                			<p><strong><?=$kid->school; ?></strong></p>
                            			</div>
                            			<div class="clearfix"></div>
                    				</div>
                    			</div>
                    			<div class="col-xs-12 col-sm-12 col-md-6 col-lg-7">
                                	<p>真实姓名：<?=$kid->real_name; ?></p>
                                	<p>性别：<?=$kid->gender; ?></p>
                                	<p>区域：<?=$kid->location; ?></p>
                                	<p>年级：<?=$kid->grade; ?></p>
                                    <p class="site a">参加了&nbsp;<a href="#all-tutorship"><strong>5次辅导</strong></a>&nbsp;, 共&nbsp;<span class="text-primary">100</span>&nbsp;课时</p>
                                    <p>自我评价：<?=$kid->evaluation; ?></p>
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
                                        	<li><a href="<?=base_url(); ?>admin/student.html"><i class="fa fa-eye"></i>&nbsp;查看详情</a></li>
                                        	<li><a href="<?=base_url(); ?>admin/student/edit.html"><i class="fa fa-edit"></i>&nbsp;编辑信息</a></li>
                                            <li><a href="<?=base_url(); ?>admin/tutorship/add_tutorship.html"><i class="fa fa-plus"></i>&nbsp;添加辅导</a></li>
                                            <li class="divider"></li>
                                            <li><a id="user_delete_kid_<?=++$idx; ?>" data-id="<?=$kid->uid; ?>" href="javascript:;"><i class="fa fa-remove"></i>&nbsp;删除</a>
                                      	</ul>
                                    </div>
                                    <div>
                                        <p class="site datetime">添加于：<?=$kid->kid_add_time; ?></p>
                                        <p class="site datetime">更新于：<?=$kid->kid_update_time; ?></p>
                                        <p>
                                            <a href="#"><span class="fa fa-heart">&nbsp;5</span></a> &nbsp;&nbsp;&nbsp;&nbsp;
                                            <a href="<?=base_url(); ?>admin/tutorship.html#comment-area"><span class="fa fa-comments">&nbsp;0</span></a>
                                        </p>
                                    </div>
                                    <div class="clearfix"></div>
                            	</div>
                            </div>
                            
                            <div class="site tutorship-status"><h4><span class="label label-danger">报名中</span></h4></div>
                            
                    	</div>
                    </div>
                    <?php endforeach; ?>
                    <?php endif; ?>
                    
                    <div class="col-sm-12">
                    	<a href="<?=base_url(); ?>user/kids/add_kid.html" class="btn btn-primary">添加一个孩子</a>
                    </div>
                    
                </div>
                
            </div>
        </div>
    </div>

    <div class="modal fade" id="delete_kid_confirmation" tabindex="-1" role="dialog" aria-labelledby="confirmation_label" aria-hidden="true" data-backdrop="static">
                  <div class="modal-dialog">
                    <div class="modal-content">
                      <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="confirmation_label">确认删除？</h4>
                      </div>
                      <div class="modal-body">
                        <p>您确定删除该孩子信息吗？删除后不可找回。</p>
                      </div>
                      <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                        <button id="delete_kid" type="button" class="btn btn-primary">确定</button>
                      </div>
                    </div>
                  </div>
                </div>
    			<div class="modal fade" id="delete_kid_successed" tabindex="-1" role="dialog" aria-labelledby="success_label" aria-hidden="true" data-backdrop="static">
                  <div class="modal-dialog">
                    <div class="modal-content">
                      <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="success_label">提示</h4>
                      </div>
                      <div class="modal-body">
                        <p>您已经成功删除了该孩子信息。</p>
                      </div>
                      <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="modal fade" id="delete_kid_failed" tabindex="-1" role="dialog" aria-labelledby="fail_label" aria-hidden="true" data-backdrop="static">
                  <div class="modal-dialog">
                    <div class="modal-content">
                      <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="fail_label">提示</h4>
                      </div>
                      <div class="modal-body">
                        <p>该孩子信息删除失败，请稍候再试。</p>
                      </div>
                      <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                      </div>
                    </div>
                  </div>
                </div>
    
    <?php $this->load->view("footer"); ?>

    <script src="<?=base_url(); ?>public/js/jquery.min.js"></script>
    <script src="<?=base_url(); ?>public/js/bootstrap.min.js"></script>
    <script src="<?=base_url(); ?>public/js/jquery.scrollUp.min.js"></script>
    <script src="<?=base_url(); ?>public/js/jquery.page-back.js"></script>
    <script src="<?=base_url(); ?>public/js/offcanvas.js"></script>
    <script src="<?=base_url(); ?>public/js/main.js"></script>
  </body>
</html>