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
    	<div class="jumbotron">
            <div class="row">
                <div class="col-xs-12 col-sm-12 col-md-9 col-lg-9">
                    <div id="carousel" class="carousel slide" data-ride="carousel">
                      <ol class="carousel-indicators">
                        <li data-target="#carousel" data-slide-to="0" class="active"></li>
                        <li data-target="#carousel" data-slide-to="1" class=""></li>
                        <li data-target="#carousel" data-slide-to="2" class=""></li>
                      </ol>
                      <div class="carousel-inner" role="listbox">
                        <div class="item active">
                          <img alt="back1" src="<?=base_url(); ?>public/images/back1.jpg">
                        </div>
                        <div class="item">
                          <img alt="back2" src="<?=base_url(); ?>public/images/back2.jpg">
                        </div>
                        <div class="item">
                          <img alt="back3" src="<?=base_url(); ?>public/images/back3.jpg">
                        </div>
                      </div>
                      <a class="left carousel-control" href="#carousel" role="button" data-slide="prev">
                        <span class="glyphicon glyphicon-chevron-left"></span>
                        <span class="sr-only">Previous</span>
                      </a>
                      <a class="right carousel-control" href="#carousel" role="button" data-slide="next">
                        <span class="glyphicon glyphicon-chevron-right"></span>
                        <span class="sr-only">Next</span>
                      </a>
                    </div>
                </div>
                
                <?php $this->load->view("statistics"); ?>
                
             </div>
        </div>
    </div>
    
    <div class="container margin-bottom-15">
    	<div class="page-header">
        	<h4>所有孩子<small>（共200名）</small></h4>
        </div>
        <div class="row">
            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-4">
            	<div class="site teacher-list-item">
                    <div>
                    	<div class="pull-left">
                        	<a href="<?=base_url(); ?>student.html">
                        		<img src="<?=base_url(); ?>public/images/kid.gif" class="img-circle site u-small-icon"/>
                            </a>
                        </div>
                        <div class="site icon-left-small">
                        	<p class="site a"><a href="<?=base_url(); ?>student.html"><strong>王小帅</strong></a></p>
                           	<p><strong>赣州市文清实验学校小学部四年级</strong></p>
                        </div>
                        <div class="clearfix"></div>
                    </div>
                    <p class="site course-info"><span>数学</span><span>20课时</span><span>章贡区</span></p>
                    <p><span class="text-primary">1</span>&nbsp;份辅导正在报名中</p>
                    <p class="site a"><a href="#"><strong>9名教师</strong></a>&nbsp;已报名</p>
                    <p class="site a">参加了&nbsp;<a href="#all-tutorship"><strong>5次辅导</strong></a>&nbsp;, 共&nbsp;<span class="text-primary">100</span>&nbsp;课时</p>
                    <p>自我评价：我是一个挺好学的孩子，在数学方面可能未掌握学习方法及思考方法。</p>
                    <p class="site talk-words">我希望能有一个老师帮助我提高数学成绩。</p>
                   	<hr />
                    <div class="pull-right">
                    	<a class="btn btn-primary" href="<?=base_url(); ?>student.html">查看详情&nbsp;(378)</a>
                    </div>
                    <div>
                    	<p class="site datetime">2015年1月7日</p>
                    	<p>
                            <a href="#"><span class="fa fa-heart">&nbsp;5</span></a> &nbsp;&nbsp;&nbsp;&nbsp;
                            <a href="#"><span class="fa fa-comments">&nbsp;2</span></a>
                        </p>
                    </div>
                    <div class="clearfix"></div>
                    <div class="site teacher-status"><h4><span class="label label-danger">报名中</span></h4></div>
                </div>
            </div>
            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-4">
            	<div class="site teacher-list-item">
                    <div>
                    	<div class="pull-left">
                        	<a href="#">
                        		<img src="<?=base_url(); ?>public/images/kid.gif" class="img-circle site u-small-icon"/>
                            </a>
                        </div>
                        <div class="site icon-left-small">
                        	<p class="site a"><a href="#"><strong>王小帅</strong></a></p>
                           	<p><strong>赣州市文清实验学校小学部四年级</strong></p>
                        </div>
                        <div class="clearfix"></div>
                    </div>
                    <p class="site course-info"><span>数学</span><span>20课时</span><span>章贡区</span></p>
                    <p><span class="text-primary">1</span>&nbsp;份辅导正在进行中</p>
                    <p class="site a">参加了&nbsp;<a href="#all-tutorship"><strong>5次辅导</strong></a>&nbsp;, 共&nbsp;<span class="text-primary">100</span>&nbsp;课时</p>
                    <p>自我评价：我是一个挺好学的孩子，在数学方面可能未掌握学习方法及思考方法。</p>
                    <p class="site talk-words">闵老师是一个很好的老师，讲课也很有趣生动，让我很容易地理解课程的意思，并提高了学习成绩。</p>
                   	<hr />
                    <div class="pull-right">
                    	<a class="btn btn-primary" href="#">查看详情&nbsp;(378)</a>
                    </div>
                    <div>
                    	<p class="site datetime">
                        	2015年1月7日
                        </p>
                    	<p>
                            <a href="#"><span class="fa fa-heart"> 30</span></a> &nbsp;&nbsp;&nbsp;&nbsp;
                            <a href="#"><span class="fa fa-comments"> 50</span></a>
                        </p>
                    </div>
                    <div class="clearfix"></div>
                    <div class="site teacher-status"><h4><span class="label label-warning">辅导中</span></h4></div>
                </div>
            </div>
            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-4">
            	<div class="site teacher-list-item">
                    <div>
                    	<div class="pull-left">
                        	<a href="#">
                        		<img src="<?=base_url(); ?>public/images/kid.gif" class="img-circle site u-small-icon"/>
                            </a>
                        </div>
                        <div class="site icon-left-small">
                        	<p class="site a"><a href="#"><strong>王小帅</strong></a></p>
                           	<p><strong>赣州市文清实验学校小学部四年级</strong></p>
                        </div>
                        <div class="clearfix"></div>
                    </div>
                    <p class="site course-info"><span>数学</span><span>20课时</span><span>章贡区</span></p>
                    <p class="site a">参加了&nbsp;<a href="#all-tutorship"><strong>5次辅导</strong></a>&nbsp;, 共&nbsp;<span class="text-primary">100</span>&nbsp;课时</p>
                    <p>自我评价：我是一个挺好学的孩子，在数学方面可能未掌握学习方法及思考方法。</p>
                    <p class="site talk-words">闵老师是一个很好的老师，讲课也很有趣生动，让我很容易地理解课程的意思，并提高了学习成绩。</p>
                   	<hr />
                    <div class="pull-right">
                    	<a class="btn btn-primary" href="#">查看详情&nbsp;(378)</a>
                    </div>
                    <div>
                    	<p class="site datetime">
                        	2015年1月7日
                        </p>
                    	<p>
                            <a href="#"><span class="fa fa-heart"> 30</span></a> &nbsp;&nbsp;&nbsp;&nbsp;
                            <a href="#"><span class="fa fa-comments"> 50</span></a>
                        </p>
                    </div>
                    <div class="clearfix"></div>
                    <div class="site teacher-status"><h4><span class="label label-success">无辅导</span></h4></div>
                </div>
            </div>
        </div>
        
        <nav class="text-center">
        	<ul id="app-pager"></ul>
        </nav>
        
    </div>
    
    <?php $this->load->view("footer"); ?>

    <script src="<?=base_url(); ?>public/js/jquery.min.js"></script>
    <script src="<?=base_url(); ?>public/js/bootstrap.min.js"></script>
    <script src="<?=base_url(); ?>public/js/jquery.scrollUp.min.js"></script>
    <script src="<?=base_url(); ?>public/js/bootstrap-paginator.min.js"></script>
    <script src="<?=base_url(); ?>public/js/jquery.page-back.js"></script>
    <script src="<?=base_url(); ?>public/js/main.js"></script>
    <script>
    	$('#app-pager').bootstrapPaginator(pager_options);
    </script>
  </body>
</html>