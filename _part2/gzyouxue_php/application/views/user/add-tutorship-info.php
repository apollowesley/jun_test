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
    <link href="<?=base_url(); ?>public/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
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
                    	<form class="form-horizontal" role="form" method="post">
                          <p class="site section-title">辅导基本信息</p>
                          <div class="form-group">
                            <label for="subject" class="col-sm-2 control-label">科目</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="subject" name="subject" placeholder="请输入辅导科目">
                            </div>
                          </div>
                          <div class="form-group">
                            <label for="hours" class="col-sm-2 control-label">计划课时</label>
                            <div class="col-sm-10">
                            	<input type="text" class="form-control" id="hours" name="hours" placeholder="请输入计划课时，如20，每个课时1个小时">
                            </div>
                          </div>
                          <div class="form-group">
                            <label for="days" class="col-sm-2 control-label">总天数</label>
                            <div class="col-sm-10">
                            	<input type="text" class="form-control" id="days" name="days" placeholder="请输入辅导总天数">
                            </div>
                          </div>
                          <div class="form-group">
                            <label for="start-time" class="col-sm-2 control-label">开始时间</label>
                            <div class="col-sm-10">
                                <div class="input-group date form_date" data-date-format="yyyy-mm-dd" data-link-field="start-time">
                                    <input type="text" class="form-control" placeholder="请选择辅导开始的日期" readonly>
                                    <span class="input-group-addon"><span class="fa fa-remove"></span></span>
                                    <span class="input-group-addon"><span class="fa fa-calendar"></span></span>
                            	</div>
                            	<input type="hidden" id="start-time" name="start_time">
                            </div>
                          </div>
                          <div class="form-group">
                            <label for="location" class="col-sm-2 control-label">辅导地点</label>
                            <div class="col-sm-10">
                            	<input type="text" class="form-control" id="location" name="location" placeholder="请输入辅导所在地点">
                            </div>
                          </div>
                          <div class="form-group">
                            <label for="class_time" class="col-sm-2 control-label">时间要求</label>
                            <div class="col-sm-10">
                            	<input type="text" class="form-control" id="class_time" name="class_time" placeholder="请输入上课时间要求">
                            </div>
                          </div>
                          <div class="form-group">
                            <label for="request" class="col-sm-2 control-label">教师要求</label>
                            <div class="col-sm-10">
                                <textarea class="form-control" rows="3" id="request" name="text_request" placeholder="请输入教师要求"></textarea>
                            </div>
                          </div>
                          <div class="form-group">
                            <label for="remarks" class="col-sm-2 control-label">备注</label>
                            <div class="col-sm-10">
                                <textarea class="form-control" rows="3" id="remarks" name="text_remarks" placeholder="请输入备注"></textarea>
                            </div>
                          </div>
                          
                          <p class="site section-title">全部完成</p>
                          <div class="form-group">
                            <div class="col-sm-10 col-sm-offset-2">
                              <button id="user_add_tutorship" type="button" class="btn btn-primary">保存</button>
                              <a href="<?=base_url(); ?>user/tutorship/add_tutorship_student.html" class="btn btn-primary">取消</a>
                            </div>
                          </div>
                        </form>
                    </div>
                </div>
                
            </div>
        </div>
    </div>
    
    <?php $this->load->view("footer"); ?>

    <script src="<?=base_url(); ?>public/js/jquery.min.js"></script>
    <script src="<?=base_url(); ?>public/js/jquery.scrollUp.min.js"></script>
    <script src="<?=base_url(); ?>public/js/bootstrap.min.js"></script>
    <script src="<?=base_url(); ?>public/js/jquery.page-back.js"></script>
    <script src="<?=base_url(); ?>public/js/offcanvas.js"></script>
    <script src="<?=base_url(); ?>public/js/datetimepicker/bootstrap-datetimepicker.min.js"></script>
    <script src="<?=base_url(); ?>public/js/datetimepicker/bootstrap-datetimepicker.zh-CN.js"></script>
    <script src="<?=base_url(); ?>public/js/main.js"></script>
    <script>
		$('.form_date').datetimepicker({
			language:  'zh-CN',
			weekStart: 0,
			todayBtn:  1,
			autoclose: 1,
			todayHighlight: 1,
			startView: 2,
			minView: 2,
			forceParse: 0,
			pickerPosition:"bottom-left"
		});
    </script>
  </body>
</html>