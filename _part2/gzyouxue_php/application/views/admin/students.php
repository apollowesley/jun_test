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
        			<h4>所有学生<small>（共520名）</small></h4>
       			</div>
                <div class="row">
                	
                    <div class="col-sm-12">
                    	<div class="panel-group" id="kid-search" role="tablist" aria-multiselectable="true">
                          <div class="panel panel-default">
                            <div class="panel-heading" role="tab" id="kid-search-heading">
                              <h4 class="panel-title site a">
                                <a class="btn-block" data-toggle="collapse" data-parent="#kid-search" href="#kid-search-collapse" aria-expanded="false" aria-controls="kid-search-collapse"><i class="fa fa-search"></i>&nbsp;搜索学生</a>
                              </h4>
                            </div>
                            <div id="kid-search-collapse" class="panel-collapse collapse" role="tabpanel" aria-labelledby="kid-search-heading">
                              <div class="panel-body">
                              	<form class="form-horizontal" role="form" method="post">
                                  <div class="form-group">
                                    <label for="search-type" class="col-sm-2 control-label">搜索方式</label>
                                    <div class="col-sm-10">
                                        <select id="search-type" class="form-control" tabindex="-1">
                                        </select>
                                    </div>
                                  </div>
                                  <div class="form-group">
                                    <label for="keyword" class="col-sm-2 control-label">关键词</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" id="keyword" placeholder="请输入关键词">
                                    </div>
                                  </div>
                                  <div class="form-group">
                                    <div class="col-sm-10 col-sm-offset-2">
                                      <button type="button" id="kid-search-btn" data-loading-text="搜索中&hellip;" class="btn btn-primary" autocomplete="off">搜索</button>
                                      <button type="button" id="cancel-kid-search-btn" class="btn btn-primary">取消</button>
                                    </div>
                                  </div>
                        		</form>
                        
                              </div>
                            </div>
                          </div>
                        </div>
                    </div>
                
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
                                			<p class="site a"><a href="<?=base_url(); ?>admin/student.html"><strong>王小帅</strong></a></p>
                                			<p><strong>赣州市文清实验学校小学部四年级</strong></p>
                            			</div>
                            			<div class="clearfix"></div>
                    				</div>
                    			</div>
                    			<div class="col-xs-12 col-sm-12 col-md-6 col-lg-7">
                                	<p>真实姓名：王小帅</p>
                                    <p>所在区域：章贡区</p>
                                    <p class="site a">参加了&nbsp;<a href="#all-tutorship"><strong>5次辅导</strong></a>&nbsp;, 共&nbsp;<span class="text-primary">100</span>&nbsp;课时</p>
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
                            
                            <div class="site tutorship-status"><h4><span class="label label-danger">报名中</span></h4></div>
                            
                    	</div>
                    </div>
                    
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
                                			<p class="site a"><a href="<?=base_url(); ?>admin/student.html"><strong>王小帅</strong></a></p>
                                			<p><strong>赣州市文清实验学校小学部四年级</strong></p>
                            			</div>
                            			<div class="clearfix"></div>
                    				</div>
                    			</div>
                    			<div class="col-xs-12 col-sm-12 col-md-6 col-lg-7">
                                	<p>真实姓名：王小帅</p>
                                    <p>所在区域：章贡区</p>
                                    <p class="site a">参加了&nbsp;<a href="#all-tutorship"><strong>5次辅导</strong></a>&nbsp;, 共&nbsp;<span class="text-primary">100</span>&nbsp;课时</p>
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
                                        	<li><a href="#"><i class="fa fa-eye"></i>&nbsp;查看详情</a></li>
                                        	<li><a href="#"><i class="fa fa-edit"></i>&nbsp;编辑信息</a></li>
                                            <li><a href="#"><i class="fa fa-plus"></i>&nbsp;添加辅导</a></li>
                                        	<li class="divider"></li>
                                            <li><a href="#"><i class="fa fa-remove"></i>&nbsp;冻结</a></li>
                                      	</ul>
                                    </div>
                                    <div>
                                        <p class="site datetime">注册于：2015年1月7日</p>
                                        <p class="site datetime">更新于：2015年1月10日</p>
                                        <p>
                                            <a href="#"><span class="fa fa-heart">&nbsp;5</span></a> &nbsp;&nbsp;&nbsp;&nbsp;
                                            <a href="tutorship-detail.html#comment-area"><span class="fa fa-comments">&nbsp;0</span></a>
                                        </p>
                                    </div>
                                    <div class="clearfix"></div>
                            	</div>
                            </div>
                            
                            <div class="site tutorship-status"><h4><span class="label label-warning">辅导中</span></h4></div>
                            
                    	</div>
                    </div>
                    
                </div>
                
                <nav class="text-center">
                    <ul id="app-pager"></ul>
                </nav>
                
            </div>
        </div>
    </div>
    
    <?php $this->load->view("footer"); ?>

    <script src="<?=base_url(); ?>public/js/jquery.min.js"></script>
    <script src="<?=base_url(); ?>public/js/bootstrap.min.js"></script>
    <script src="<?=base_url(); ?>public/js/jquery.scrollUp.min.js"></script>
    <script src="<?=base_url(); ?>public/js/bootstrap-paginator.min.js"></script>
    <script src="<?=base_url(); ?>public/js/jquery.page-back.js"></script>
    <script src="<?=base_url(); ?>public/js/jquery.options-creator.js"></script>
    <script src="<?=base_url(); ?>public/js/offcanvas.js"></script>
    <script src="<?=base_url(); ?>public/js/bootstrap-multiselect.js"></script>
    <script src="<?=base_url(); ?>public/js/main.js"></script>
    <script src="<?=base_url(); ?>public/js/main.data.js"></script>
    <script>
    	$('#app-pager').bootstrapPaginator(pager_options);
		$("#search-type").createOptions({
			 data:kid_search_types
		 });
		 $("#search-type").multiselect({
			 nonSelectedText: '请选择搜索方式',
			 maxHeight:300
		});
		$('#kid-search-btn').on('click', function () {
			var $btn = $(this).button('loading');
			// business logic...
			setTimeout(function() {
				$btn.button('reset');
			}, 3000);
		 });
		 $('#cancel-kid-search-btn').on('click', function () {
			 $("#kid-search-collapse").collapse("hide");
		 });
    </script>
  </body>
</html>