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
  
  	<div class="container">
    	<div class="row">
        	<div class="col-xs-12 col-sm-12 col-md-6 col-md-offset-3 col-lg-6 col-lg-offset-3">
            	<div class="page-header">
        			<h4 class="site a">
                      <a href="<?=base_url(); ?>">优学网</a>
                    </h4>
                    <p>为学生提供优质的教学及兴趣辅导，让学生更加快乐地成长！</p>
        		</div>
            </div>
            <div class="col-xs-12 col-sm-12 col-md-4 col-md-offset-3 col-lg-6 col-lg-offset-3">
            	<form class="form-horizontal" role="form" method="post">
                  <div class="form-group">
                    <label for="email" class="col-sm-2 control-label">登录邮箱</label>
                    <div class="col-sm-10">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-user"></i></span>
                          	<input type="email" class="form-control" id="email" name="email" placeholder="请输入登录邮箱">
                       	</div>
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="password" class="col-sm-2 control-label">登录密码</label>
                    <div class="col-sm-10">
                    	<div class="input-group">
                        	<span class="input-group-addon"><i class="fa fa-lock"></i></span>
                          	<input type="password" class="form-control" id="password" name="password" placeholder="请输入登录密码">
                        </div>
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="auth_code" class="col-sm-2 control-label">验证码</label>
                    <div class="col-sm-10">
                        <div class="input-group">
                        	<span class="input-group-addon"><i class="fa fa-question-circle"></i></span>
                          	<input type="text" class="form-control" id="auth_code" name="auth_code" placeholder="请输入验证码">
                       	</div>
                    </div>
                  </div>
                  <div class="form-group">
                  	<div class="col-sm-4 col-sm-offset-2">
                      <img src="<?=base_url(); ?>authcode" alt="验证码" />
                      <span class="site a"><a id="change-auth-code" href="javascript:;">看不清？</a></span>
                    </div>
                    <div class="col-sm-6">
                      <button id="admin-login" type="button" class="btn btn-primary pull-right">登&nbsp;&nbsp;录</button>
                    </div>
                  </div>
                </form>
            </div>
        </div>
    </div>

    <script src="<?=base_url(); ?>public/js/jquery.min.js"></script>
    <script src="<?=base_url(); ?>public/js/bootstrap.min.js"></script>
    <script src="<?=base_url(); ?>public/js/jquery.scrollUp.min.js"></script>
    <script src="<?=base_url(); ?>public/js/jquery.page-back.js"></script>
    <script src="<?=base_url(); ?>public/js/main.js"></script>
    <script>
		$(function(){
			inputFocus("email");
		});
    </script>
  </body>
</html>