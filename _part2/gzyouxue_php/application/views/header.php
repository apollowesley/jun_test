    <nav class="navbar navbar-default navbar-fixed-top" role="navigation">
        <div class="container">
          <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
              <span class="sr-only">导航</span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="<?=base_url(); ?>">优学网</a>
          </div>
          <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
              <li<?php echo isset($tutorships) && $tutorships == true ? ' class="active"' : ''; ?>><a href="<?=base_url(); ?>">辅导</a></li>
              <li<?php echo isset($students) && $students == true ? ' class="active"' : ''; ?>><a href="<?=base_url(); ?>students.html">学生</a></li>
              <li<?php echo isset($teachers) && $teachers == true ? ' class="active"' : ''; ?>><a href="<?=base_url(); ?>teachers.html">教师</a></li>
              <li<?php echo isset($subjects) && $subjects == true ? ' class="active"' : ''; ?>><a href="#">超级课程</a></li>
              <li<?php echo isset($others) && $others == true ? ' class="active"' : ''; ?>><a href="#">有料</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
            	<?php if(isset($logined_user) && !empty($logined_user) && isset($logined_user["uid"])): ?>
            		<li class="dropdown">
		                <a href="#" class="dropdown-toggle site user-a" data-toggle="dropdown" aria-expanded="false"><img src="<?=$logined_user["figure"]; ?>" class="img-circle site u-mini-icon" />&nbsp;&nbsp;<?=$logined_user["nickname"]; ?>&nbsp;<span class="caret"></span></a>
		                <ul class="dropdown-menu" role="menu">
		                <?php if ($logined_user["user_type"] == USER_TYPE_NORMAL): ?>
		                  <li><a href="<?=base_url(); ?>user/normal.html"><i class="fa fa-info-circle"></i>&nbsp;个人中心</a></li>
		                  <?php elseif ($logined_user["user_type"] == USER_TYPE_PARENT): ?>
		                  <li><a href="<?=base_url(); ?>user/parents.html"><i class="fa fa-info-circle"></i>&nbsp;个人中心</a></li>
		                  <?php elseif ($logined_user["user_type"] == USER_TYPE_STUDENT): ?>
		                  <li><a href="<?=base_url(); ?>user/student.html"><i class="fa fa-info-circle"></i>&nbsp;个人中心</a></li>
		                  <?php elseif ($logined_user["user_type"] == USER_TYPE_TEACHER): ?>
		                  <li><a href="<?=base_url(); ?>user/teacher.html"><i class="fa fa-info-circle"></i>&nbsp;个人中心</a></li>
		                  <?php endif; ?>
		                  <li><a href="<?=base_url(); ?>user/setting.html"><i class="fa fa-cog"></i>&nbsp;账号设置</a></li>
		                  <li class="divider"></li>
		                  <li><a href="<?=base_url(); ?>user/logout.html"><i class="fa fa-sign-out"></i>&nbsp;退出</a></li>
		                </ul>
		              </li>
            	<?php else: ?>
              <li><p class="navbar-text">登录：</p></li>
              <li><a href="<?=base_url(); ?>auth/qq"><img src="<?=base_url(); ?>public/images/qq.png" alt="QQ登录" title="QQ登录" />&nbsp;QQ</a></li>
              <li><a href="<?=base_url(); ?>auth/weibo"><img src="<?=base_url(); ?>public/images/weibo.png" alt="新浪微博登录" title="新浪微博登录"/>&nbsp;微博</a></li>
              <li><a href="<?=base_url(); ?>#"><img src="<?=base_url(); ?>public/images/weixin.png" alt="微信登录" title="微信登录"/>&nbsp;微信</a></li>
              <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false">更多<span class="caret"></span></a>
                <ul class="dropdown-menu" role="menu">
                  <li><a href="<?=base_url(); ?>user/login.html"><i class="fa fa-sign-in"></i>&nbsp;普通登录</a></li>
                  <li class="divider"></li>
                  <li><a href="<?=base_url(); ?>user/register.html"><i class="fa fa-coffee"></i>&nbsp;用户注册</a></li>
                </ul>
              </li>
              <?php endif;?>
            </ul>
          </div>
        </div>
      </nav>