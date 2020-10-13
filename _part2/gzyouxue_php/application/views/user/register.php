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
                      注册&nbsp;&nbsp;<a href="<?=base_url(); ?>">优学网</a>
                    </h4>
                    <p>为学生提供优质的教学及兴趣辅导，让学生更加快乐地成长！</p>
        		</div>
            </div>
            <div class="col-xs-12 col-sm-12 col-md-6 col-md-offset-3 col-lg-6 col-lg-offset-3">
            	<div class="row">
            		<div class="col-xs-12 col-sm-12 col-md-8 col-lg-8">
		            	<form class="form-horizontal" role="form" method="post">
		                  <div class="form-group">
		                    <label for="email" class="col-sm-3 control-label">登录邮箱</label>
		                    <div class="col-sm-9">
		                        <div class="input-group">
		                            <span class="input-group-addon"><i class="fa fa-user"></i></span>
		                          	<input type="email" class="form-control" id="email" name="email" placeholder="请输入登录邮箱">
		                       	</div>
		                    </div>
		                  </div>
		                  <div class="form-group">
		                    <label for="password" class="col-sm-3 control-label">登录密码</label>
		                    <div class="col-sm-9">
		                    	<div class="input-group">
		                        	<span class="input-group-addon"><i class="fa fa-lock"></i></span>
		                          	<input type="password" class="form-control" id="password" name="password" placeholder="请输入登录密码">
		                        </div>
		                    </div>
		                  </div>
		                  <div class="form-group">
		                    <label for="password" class="col-sm-3 control-label">确认密码</label>
		                    <div class="col-sm-9">
		                    	<div class="input-group">
		                        	<span class="input-group-addon"><i class="fa fa-lock"></i></span>
		                          	<input type="password" class="form-control" id="confrim_password" name="confirm_password" placeholder="请输入确认密码">
		                        </div>
		                    </div>
		                  </div>
		                  <div class="form-group">
		                    <div class="col-sm-5 col-sm-offset-3">
		                      <button id="user_reg" type="button" class="btn btn-primary">注&nbsp;&nbsp;册</button>
		                    </div>
		                  </div>
		                </form>
	                </div>
	                <div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">
	                	<p class="site a">已有账号？<a href="<?=base_url(); ?>user/login.html">立即登录</a></p>
						<p>推荐使用社交账号登录：</p>
						<p class="site a">
							 <a href="<?=base_url(); ?>auth/qq"><img src="<?=base_url(); ?>public/images/qq.png" alt="QQ登录" title="QQ登录" />&nbsp;QQ登录</a>
							 &nbsp;&nbsp;
							 <a href="<?=base_url(); ?>auth/weibo"><img src="<?=base_url(); ?>public/images/weibo.png" alt="新浪微博登录" title="新浪微博登录"/>&nbsp;微博登录</a>
						</p>
						<p>
							<a href="<?=base_url(); ?>#"><img src="<?=base_url(); ?>public/images/weixin.png" alt="微信登录" title="微信登录"/>&nbsp;微信登录</a>
						</p>
	                </div>
                </div>
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