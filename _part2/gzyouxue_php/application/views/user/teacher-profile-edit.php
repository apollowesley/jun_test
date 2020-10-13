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
    <link href="<?=base_url(); ?>public/css/iCheck/square/blue.css" rel="stylesheet">
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
        			<h4>编辑我的信息</h4>
       			</div>
                <div class="row">
                	<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                    	<form class="form-horizontal" role="form" method="post">
                    	  <p class="site section-title">基本信息</p>
                          <?php $this->load->view("user/profile-basic-info-edit"); ?>
                          
                          <p class="site section-title">教师基本信息</p>
                          <div class="form-group">
                            <label for="radio_t_type" class="col-sm-2 control-label">我是</label>
                                <div class="col-sm-10">
                                	<div id="radio_t_type" class="radio">
                                      <input type="radio" name="radio_t_type" id="type-student" value="在校大学生"<?=isset($user->t_type) && !empty($user->t_type) && $user->t_type == "在校大学生" ? " checked" : ""; ?>>
                                      <label for="type-student">在校大学生</label>
                                      &nbsp;&nbsp;&nbsp;&nbsp;
                                      <input type="radio" name="radio_t_type" id="type-teacher" value="在职教师"<?=isset($user->t_type) && !empty($user->t_type) && $user->t_type == "在职教师" ? " checked" : ""; ?>>
                                      <label for="type-teacher">在职教师</label>
                                       &nbsp;&nbsp;&nbsp;&nbsp;
                                      <input type="radio" name="radio_t_type" id="type-other" value="其他"<?=isset($user->t_type) && !empty($user->t_type) && $user->t_type == "其他" ? " checked" : ""; ?>>
                                      <label for="type-other">其他</label>
                                   	</div>
                            	</div>
                          </div>
                          <div class="form-group">
                            <label for="where_from" class="col-sm-2 control-label">来自于</label>
                            <div class="col-sm-10">
                            	<input type="text" class="form-control" id="where_from" name="where_from" placeholder="请输入您来自于哪个学校或哪个单位，大学生请具体到专业" value="<?=isset($user->where_from) && !empty($user->where_from) ? $user->where_from : ''; ?>">
                            </div>
                          </div>
                          <div class="form-group">
                            <label for="select_speciality" class="col-sm-2 control-label">专长</label>
                            <div class="col-sm-10">
                            	<select id="select_speciality" class="form-control" tabindex="-1" multiple>
                           		</select>
                            </div>
                          </div>
                          <div class="form-group">
                            <label for="evaluation" class="col-sm-2 control-label">自我评价</label>
                            <div class="col-sm-10">
                                <textarea class="form-control" rows="4" id="evaluation" name="text_evaluation" placeholder="请输入自我评价"><?=isset($user->evaluation) && !empty($user->evaluation) ? $user->evaluation : ''; ?></textarea>
                            </div>
                          </div>
                          
                          <p class="site section-title">全部完成</p>
                          <div class="form-group">
                            <div class="col-sm-10 col-sm-offset-2">
                              <button id="save_teacher_info" type="button" class="btn btn-primary">保存</button>
                              <a href="<?=base_url(); ?>user/teacher.html" class="btn btn-primary">取消</a>
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
    <script src="<?=base_url(); ?>public/js/jquery.options-creator.js"></script>
    <script src="<?=base_url(); ?>public/js/offcanvas.js"></script>
    <script src="<?=base_url(); ?>public/js/icheck.min.js"></script>
    <script src="<?=base_url(); ?>public/js/bootstrap-multiselect.js"></script>
    <script src="<?=base_url(); ?>public/js/main.js"></script>
    <script src="<?=base_url(); ?>public/js/main.data.js"></script>
    <script>
	    $("#select_speciality").createOptions({
			data:specialits,
			optgroup:true,
			selected_value_str:'<?=isset($user->speciality) && !empty($user->speciality) ? $user->speciality : ''; ?>'	
		});
		$('#select_speciality').multiselect({
			nonSelectedText: '请选择三项专长',
			maxHeight:300,
			onChange: function(option, checked) {
				selectLimit("speciality", option, checked, 3);
	        }
		});
		$('input[name="radio_t_type"],input[name="radio_gender"]').iCheck({
			radioClass: 'iradio_square-blue',
			increaseArea: '20%' // optional
		});
    </script>
  </body>
</html>