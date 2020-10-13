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
        			<h4>账号设置</h4>
       			</div>
                
                <div class="row">
                	<div class="col-sm-12">
                		<p class="site section-title">账号绑定</p>
                	</div>
                    <div class="col-sm-12">
                    	<?php if (isset($from_bind) && $from_bind == true && isset($logined_user["is_bind"])): ?>
                    		<?php if ($logined_user["is_bind"] == true): //账号绑定成功提示信息?>
		                    	<div id="bind_success_alert" class="alert alert-info alert-dismissible fade in" role="alert">
							      <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
							      <h4>提示</h4>
							      <p>
							      您已成功绑定
							      <?php if ($logined_user["bind_with"] == BIND_WITH_QQ): ?>
							      QQ账号，以后可直接用QQ账号登录。
							      <?php elseif ($logined_user["bind_with"] == BIND_WITH_WEIBO): ?>
							      新浪微博账号，以后可直接用新浪微博账号登录。
							      <?php endif; ?>
							      </p>
							    </div>
							<?php else: //账号绑定失败提示信息?>    
							<?php endif; ?>
					    <?php endif; ?>
                    	<?php if (isset($logined_user["bind_with"])): ?>
                    		<?php if ($logined_user["bind_with"] == BIND_WITH_QQ): ?>
                    		<p><img src="<?=base_url(); ?>public/images/qq.png"/>&nbsp;您已经绑定QQ账号，可直接用QQ账号登录。</p>
                    		<?php elseif ($logined_user["bind_with"] == BIND_WITH_WEIBO): ?>
                    		<p><img src="<?=base_url(); ?>public/images/weibo.png"/>&nbsp;您已经绑定新浪微博账号，可直接用微博账号登录。</p>
                    		<?php else: ?>
	                    	<p>未绑定社交账号，您可以选择绑定QQ账号或新浪微博账号：</p>
	                    	<p class="site a"><a href="<?=base_url(); ?>auth/qq"><strong>绑定QQ账号</strong></a>，绑定后，你可以直接用QQ账号登录。</p>
	                    	<p class="site a"><a href="<?=base_url(); ?>auth/weibo"><strong>绑定微博账号</strong></a>，绑定后，你可以直接用微博账号登录。</p>	
                    		<?php endif; ?>
                    	<?php endif; ?>
                    </div>
                    <div class="col-sm-12">
                    	<p class="site section-title">密码修改</p>
                    </div>
                    <div class="col-sm-12">
                    	<?php if (isset($logined_user["email"]) && !empty($logined_user["email"])): ?>
                    	<div id="change_pwd_success" class="alert alert-info alert-dismissible hidden" role="alert">
							<h4>提示</h4>
							<p>您已经成功修改密码，请牢记您的新密码，如果您未绑定社交账号，推荐您绑定社交账号，绑定后，可直接用社交账号登录。</p>
						</div>
                    	<form id="user_change_pwd_form" class="form-horizontal" role="form">
                          <div class="form-group">
                            <label for="old_password" class="col-sm-2 control-label">旧密码</label>
                            <div class="col-sm-10">
                                <input type="password" class="form-control" id="old_password" name="old_password" placeholder="请输入旧密码">
                            </div>
                          </div>
                          <div class="form-group">
                            <label for="password" class="col-sm-2 control-label">新密码</label>
                            <div class="col-sm-10">
                                <input type="password" class="form-control" id="password" name="password" placeholder="请输入新密码">
                            </div>
                          </div>
                          <div class="form-group">
                            <label for="confirm_password" class="col-sm-2 control-label">确认密码</label>
                            <div class="col-sm-10">
                                <input type="password" class="form-control" id="confirm_password" name="confirm_password" placeholder="请输入确认密码">
                            </div>
                          </div>
                          <div class="form-group">
                            <div class="col-sm-10 col-sm-offset-2">
                              <button type="button" id="user_change_pwd" class="btn btn-primary">确认修改</button>
                            </div>
                          </div>
                        </form>
                        <?php else: ?>
                        	<?php if ($logined_user["bind_with"] == BIND_WITH_QQ): ?>
                        	<p>您直接使用QQ账号登录，无需更改登录密码。</p>
                        	<?php elseif ($logined_user["bind_with"] == BIND_WITH_WEIBO): ?>
                        	<p>您直接使用新浪微博账号登录，无需更改登录密码。</p>
                        	<?php endif; ?>
                        <?php endif; ?>
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
    <script src="<?=base_url(); ?>public/js/main.js"></script>
    <script src="<?=base_url(); ?>public/js/offcanvas.js"></script>
    
    <?php if (isset($from_bind) && $from_bind == true && isset($logined_user["is_bind"])): ?>
    	<?php if ($logined_user["is_bind"] == true): //账号绑定成功?>
	    	<script>
				$("div[id='bind_success_alert']").alert();
				setTimeout(function() {
					$("div[id='bind_success_alert']").alert("close");
				}, 10000);
	    	</script>
	    <?php else: //账号绑定失败?>
    	<?php endif; ?>
   	<?php endif; ?>
    
  </body>
</html>