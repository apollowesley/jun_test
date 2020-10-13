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
        			<h4>辅导详情</h4>
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
                                	<p class="site course-info"><span>数学</span><span>20课时</span><span>章贡区</span></p>
                                    <p>周一至周五18:00-20:00</p>
                                    <p>开始时间：2015年1月8日</p>
                                    <p>结束时间：还未结束</p>
                                    <p class="site a">参加了&nbsp;<a href="<?=base_url(); ?>admin/student.html#all-tutorship"><strong>5次辅导</strong></a>&nbsp;, 共&nbsp;<span class="text-primary">100</span>&nbsp;课时</p>
                                    <p>辅导需求：需要女教师，有一定教学经验。</p>
                                    <p>自我评价：我是一个挺好学的孩子，在数学方面可能未掌握学习方法及思考方法。</p>
                    			</div>
                    		</div>
                            
                    		<hr class="site hr-dash-light-dark"/>
                            
                            <div class="row">
                				<div class="col-xs-12 col-sm-12 col-md-6 col-lg-5">
                    				<div>
                            			<div class="pull-left">
                                			<img src="<?=base_url(); ?>public/images/default-teacher-icon.png" class="img-circle site u-small-icon" />
                            			</div>
                            			<div class="site icon-left-small">
                                			<p class="text-primary"><strong>闵老师</strong>&nbsp;&nbsp;<i class="fa fa-user site icon-female"></i></p>
                                			<p><strong>赣南师范学院教育技术学专业</strong></p>
                            			</div>
                            			<div class="clearfix"></div>
                    				</div>
                    			</div>
                    			<div class="col-xs-12 col-sm-12 col-md-6 col-lg-7">
                                	<p>专长：数学，英语，计算机</p>
                                    <p class="site a">成功辅导&nbsp;<a href="<?=base_url(); ?>admin/teacher.html#all-tutorship"><strong>9名学生</strong></a>，共<span class="text-primary">185</span>课时</p>
                                    <p>自我评价：我是一个认真负责，知识过硬，亲近孩子的老师。期望辅导更多的学生，让他们快乐地成长。</p>
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
                                        	<li class="divider"></li>
                                            <li><a href="#"><i class="fa fa-remove"></i>&nbsp;冻结</a></li>
                                      	</ul>
                                    </div>
                                    <div>
                                        <p class="site datetime">发布于：2015年1月7日</p>
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
                        <h4>所有报名教师<small>（共9名）</small></h4>
                    </div>
                    <div class="row" id="apply-teachers">
                        <div class="col-xs-12 col-sm-12 col-md-6 col-lg-3">
                            <div class="site apply-teacher selected">
                                <div class="pull-left">
                                    <img src="<?=base_url(); ?>public/images/default-teacher-icon.png" class="img-circle site u-small-icon" />
                                </div>
                                <div class="site icon-left-small">
                                    <p class="text-primary"><strong>闵老师</strong></p>
                                    <p><strong>赣南师范学院教育技术学专业</strong></p>
                                </div>
                                <div class="clearfix"></div>
                            </div>
                        </div>
                        <div class="col-xs-12 col-sm-12 col-md-6 col-lg-3">
                            <div  class="site apply-teacher">
                                <div class="pull-left">
                                    <img src="<?=base_url(); ?>public/images/default-teacher-icon.png" class="img-circle site u-small-icon" />
                                </div>
                                <div class="site icon-left-small">
                                    <p class="text-primary"><strong>闵老师</strong></p>
                                    <p><strong>赣南师范学院教育技术学专业</strong></p>
                                </div>
                                <div class="clearfix"></div>
                            </div>
                        </div>
                        <div class="col-xs-12 col-sm-12 col-md-6 col-lg-3">
                            <div  class="site apply-teacher">
                                <div class="pull-left">
                                    <img src="<?=base_url(); ?>public/images/default-teacher-icon.png" class="img-circle site u-small-icon" />
                                </div>
                                <div class="site icon-left-small">
                                    <p class="text-primary"><strong>闵老师</strong></p>
                                    <p><strong>赣南师范学院教育技术学专业</strong></p>
                                </div>
                                <div class="clearfix"></div>
                            </div>
                        </div>
                        <div class="col-xs-12 col-sm-12 col-md-6 col-lg-3">
                            <div  class="site apply-teacher">
                                <div class="pull-left">
                                    <img src="<?=base_url(); ?>public/images/default-teacher-icon.png" class="img-circle site u-small-icon" />
                                </div>
                                <div class="site icon-left-small">
                                    <p class="text-primary"><strong>闵老师</strong></p>
                                    <p><strong>赣南师范学院教育技术学专业</strong></p>
                                </div>
                                <div class="clearfix"></div>
                            </div>
                        </div>
                    </div>
                    <div>
                    	<ul class="pager">
                           <li class="disabled"><a href="javascript:;" id="prev-apply-teachers"><i class="fa fa-arrow-left"></i>&nbsp;往前翻</a></li>
                           <li><a href="javascript:;" id="next-apply-teachers">往后翻&nbsp;<i class="fa fa-arrow-right"></i></a></li>
                        </ul>
                    </div>
                    <div class="page-header">
                        <h4>辅导效果总结</h4>
                    </div>
                    <div>
                        <div class="pull-left">
                            <img src="<?=base_url(); ?>public/images/default-icon.png" class="img-circle site u-small-icon" />
                        </div>
                        <div class="site icon-left-small">
                            <div class="site comment-list">
                            	<div class="row">
                            		<div class="col-xs-10 col-sm-9 col-md-10 col-lg-10">
                                		<p class="site talk-words">请等待辅导结束，将由优学网总结权威可信的辅导效果哦。</p>
                                		<p class="site a"><a href="#">优学网</a>&nbsp;&nbsp;<small>2015年1月8日</small></p>
                                    </div>
                                    <div class="col-xs-2 col-sm-3 col-md-2 col-lg-2">
                                		<div class="dropdown pull-right site a">
                                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false">操作&nbsp;<span class="caret"></span></a>
                                            <ul class="dropdown-menu" role="menu">
                                              <li><a href="#"><i class="fa fa-edit"></i>&nbsp;编辑</a></li>
                                              <li><a href="#"><i class="fa fa-remove"></i>&nbsp;删除</a></li>
                                            </ul>
                                        </div>
                                	</div>
                                </div>
                            </div>
                        </div>
                        <div class="clearfix"></div>
                    </div>
                    <div class="page-header">
                        <h4>学生的心声<small>（共3条）</small></h4>
                    </div>
                    <div>
                        <div class="pull-left">
                            <a href="<?=base_url(); ?>admin/student.html">
                                <img src="<?=base_url(); ?>public/images/kid.gif" class="img-circle site u-small-icon" />
                            </a>
                        </div>
                        <div class="site icon-left-small">
                        	<div class="site comment-list">
                            	<div class="row">
                            		<div class="col-xs-10 col-sm-9 col-md-10 col-lg-10">
                                		<p class="site talk-words">闵老师是一个很好的老师，讲课也很有趣生动，让我很容易地理解课程的意思，并提高了学习成绩。</p>
                                		<p class="site a"><a href="<?=base_url(); ?>admin/student.html">王小帅</a>&nbsp;&nbsp;<small>2015年1月9日</small></p>
                                	</div>
                                	<div class="col-xs-2 col-sm-3 col-md-2 col-lg-2">
                                		<div class="dropdown pull-right site a">
                                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false">操作&nbsp;<span class="caret"></span></a>
                                            <ul class="dropdown-menu" role="menu">
                                              <li><a href="#"><i class="fa fa-edit"></i>&nbsp;编辑</a></li>
                                              <li><a href="#"><i class="fa fa-remove"></i>&nbsp;冻结</a></li>
                                            </ul>
                                        </div>
                                	</div>
                            	</div>
                            </div>
                            <div class="site comment-list">
                                <div class="row">
                            		<div class="col-xs-10 col-sm-9 col-md-10 col-lg-10">
                                		<p class="site talk-words">昨天已经有8位老师和我联系了，我正在考虑哪一位教师来辅导我。</p>
                                		<p class="site a"><a href="<?=base_url(); ?>admin/student.html">王小帅</a>&nbsp;&nbsp;<small>2015年1月8日</small></p>
                                	</div>
                                	<div class="col-xs-2 col-sm-3 col-md-2 col-lg-2">
                                		<div class="dropdown pull-right site a">
                                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false">操作&nbsp;<span class="caret"></span></a>
                                            <ul class="dropdown-menu" role="menu">
                                              <li><a href="#"><i class="fa fa-edit"></i>&nbsp;编辑</a></li>
                                              <li><a href="#"><i class="fa fa-remove"></i>&nbsp;冻结</a></li>
                                            </ul>
                                        </div>
                                	</div>
                            	</div>
                            </div>
                            <div class="site comment-list">
                                <div class="row">
                            		<div class="col-xs-10 col-sm-9 col-md-10 col-lg-10">
                                		<p class="site talk-words">我希望能有一个老师帮助我提高数学成绩。</p>
                                		<p class="site a"><a href="<?=base_url(); ?>admin/student.html">王小帅</a>&nbsp;&nbsp;<small>2015年1月7日</small></p>
                                	</div>
                                	<div class="col-xs-2 col-sm-3 col-md-2 col-lg-2">
                                		<div class="dropdown pull-right site a">
                                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false">操作&nbsp;<span class="caret"></span></a>
                                            <ul class="dropdown-menu" role="menu">
                                              <li><a href="#"><i class="fa fa-edit"></i>&nbsp;编辑</a></li>
                                              <li><a href="#"><i class="fa fa-remove"></i>&nbsp;冻结</a></li>
                                            </ul>
                                        </div>
                                	</div>
                            	</div>
                            </div>
                        </div>
                        <div class="clearfix"></div>
                    </div>
                    <div class="page-header">
                        <h4>教师的心声</h4>
                    </div>
                    <div>
                        <div class="pull-left">
                            <img src="<?=base_url(); ?>public/images/default-teacher-icon.png" class="img-circle site u-small-icon" />
                        </div>
                        <div class="site icon-left-small">
                            <div class="site comment-list">
                                <div class="row">
                            		<div class="col-xs-10 col-sm-9 col-md-10 col-lg-10">
                                		<p class="site talk-words">辅导开始不久，暂无评价哦。</p>
                                	</div>
                                	<div class="col-xs-2 col-sm-3 col-md-2 col-lg-2">
                                		<div class="dropdown pull-right site a">
                                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false">操作&nbsp;<span class="caret"></span></a>
                                            <ul class="dropdown-menu" role="menu">
                                              <li><a href="#"><i class="fa fa-edit"></i>&nbsp;编辑</a></li>
                                              <li><a href="#"><i class="fa fa-remove"></i>&nbsp;冻结</a></li>
                                            </ul>
                                        </div>
                                	</div>
                            	</div>
                            </div>
                        </div>
                        <div class="clearfix"></div>
                    </div>
                    <div class="page-header">
                        <h4 id="comment-area">评论</h4>
                    </div>
                    <div>
                        <div class="pull-left">
                            <a href="#">
                                <img src="<?=base_url(); ?>public/images/adult.jpg" class="img-circle site u-small-icon" />
                            </a>
                        </div>
                        <div class="site icon-left-small">
                            <div class="site comment-list">
                                <div class="row">
                            		<div class="col-xs-10 col-sm-9 col-md-10 col-lg-10">
                                		<p class="site talk-words">我觉得这位老师很不错。</p>
                                		<p class="site a"><a href="#">小刘老师</a>&nbsp;&nbsp;<small>2015年1月8日</small></p>
                                	</div>
                                	<div class="col-xs-2 col-sm-3 col-md-2 col-lg-2">
                                		<div class="dropdown pull-right site a">
                                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false">操作&nbsp;<span class="caret"></span></a>
                                            <ul class="dropdown-menu" role="menu">
                                              <li><a href="#"><i class="fa fa-edit"></i>&nbsp;编辑</a></li>
                                              <li><a href="#"><i class="fa fa-remove"></i>&nbsp;冻结</a></li>
                                            </ul>
                                        </div>
                                	</div>
                            	</div>
                            </div>
                        </div>
                        <div class="clearfix"></div>
                    </div>
                    <div>
                        <div class="pull-left">
                            <a href="#">
                                <img src="<?=base_url(); ?>public/images/default-icon.png" class="img-circle site u-small-icon" />
                            </a>
                        </div>
                        <div class="site icon-left-small">
                            <div class="site comment-list">
                                <div class="row">
                            		<div class="col-xs-10 col-sm-9 col-md-10 col-lg-10">
                                		<p class="site talk-words">以前我也辅导过这位同学，很聪明的一个孩子</p>
                                		<p class="site a"><a href="#">李开心</a>&nbsp;&nbsp;<small>2015年1月8日</small></p>
                                	</div>
                                	<div class="col-xs-2 col-sm-3 col-md-2 col-lg-2">
                                		<div class="dropdown pull-right site a">
                                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false">操作&nbsp;<span class="caret"></span></a>
                                            <ul class="dropdown-menu" role="menu">
                                              <li><a href="#"><i class="fa fa-edit"></i>&nbsp;编辑</a></li>
                                              <li><a href="#"><i class="fa fa-remove"></i>&nbsp;冻结</a></li>
                                            </ul>
                                        </div>
                                	</div>
                            	</div>
                            </div>
                            <p class="site a"><a href="#"><i class="fa fa-comment"></i> 我要评论</a></p>
                        </div>
                        <div class="clearfix"></div>
                    </div>
                    
            </div>
        </div>
    </div>
    
    <?php $this->load->view("footer");?>
    
	<a id="page-back"></a>
    <script id="apply-teacher-tmpl" type="text/x-dot-template">
		<div class="col-xs-12 col-sm-12 col-md-6 col-lg-3 site display-none">
        	<div  class="site apply-teacher">
            	<div class="pull-left">
                	<img src="<?=base_url(); ?>public/images/default-teacher-icon.png" class="img-circle site u-small-icon" />
                </div>
            	<div class="site icon-left-small">
                	<p class="text-primary"><strong>{{=it.t_name}}</strong></p>
                	<p><strong>{{=it.t_from}}</strong></p>
            	</div>
            	<div class="clearfix"></div>
        	</div>
    	</div> 
	</script>
    <script src="<?=base_url(); ?>public/js/jquery.min.js"></script>
    <script src="<?=base_url(); ?>public/js/bootstrap.min.js"></script>
    <script src="<?=base_url(); ?>public/js/jquery.scrollUp.min.js"></script>
    <script src="<?=base_url(); ?>public/js/jquery.page-back.js"></script>
    <script src="<?=base_url(); ?>public/js/offcanvas.js"></script>
    <script src="<?=base_url(); ?>public/js/doT.min.js"></script>
    <script src="<?=base_url(); ?>public/js/main.js"></script>
  </body>
</html>