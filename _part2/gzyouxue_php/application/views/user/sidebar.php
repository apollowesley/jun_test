<div class="col-xs-6 col-sm-5 col-md-4 col-lg-3 sidebar-offcanvas" id="sidebar" role="navigation">
              <div class="list-group">
              	<div class="list-group-item site list-group-title"><i class="fa fa-info-circle"></i>&nbsp;信息管理</div> 
              	<?php if ($logined_user["user_type"] == USER_TYPE_NORMAL): ?>
		        <a href="<?=base_url(); ?>user/normal.html" class="list-group-item<?php echo isset($profile) && $profile == true ? " active" : ""; ?>">我的信息</a>
		        <?php elseif ($logined_user["user_type"] == USER_TYPE_PARENT): ?>
		        <a href="<?=base_url(); ?>user/parents.html" class="list-group-item<?php echo isset($profile) && $profile == true ? " active" : ""; ?>">我的信息</a>
		        <?php elseif ($logined_user["user_type"] == USER_TYPE_STUDENT): ?>
		        <a href="<?=base_url(); ?>user/student.html" class="list-group-item<?php echo isset($profile) && $profile == true ? " active" : ""; ?>">我的信息</a>
		        <?php elseif ($logined_user["user_type"] == USER_TYPE_TEACHER): ?>
		        <a href="<?=base_url(); ?>user/teacher.html" class="list-group-item<?php echo isset($profile) && $profile == true ? " active" : ""; ?>">我的信息</a>
		        <?php endif; ?>
                <?php if (isset($parent_profile) && $parent_profile == true): ?>
                <a href="<?=base_url(); ?>user/parents/kids.html" class="list-group-item<?php echo isset($kids) && $kids == true ? " active" : ""; ?>">我的孩子</a>
                <?php endif;?>
                <?php if (isset($tutorship_admin) && $tutorship_admin == true): ?>
                <div class="list-group-item site list-group-title"><i class="fa fa-suitcase"></i>&nbsp;辅导管理</div> 
                <a href="<?=base_url(); ?>user/tutorships.html" class="list-group-item<?php echo isset($tutorships) && $tutorships == true ? " active" : ""; ?>">所有辅导</a>
                <?php endif; ?>
                <?php if (isset($student_add_tutorship) && $student_add_tutorship == true): ?>
                <a href="<?=base_url(); ?>user/tutorship/add_tutorship.html" class="list-group-item<?php echo isset($add_tutorship) && $add_tutorship == true ? " active" : ""; ?>">添加辅导</a>
                <?php endif; ?>
                <?php if (isset($parent_add_tutorship) && $parent_add_tutorship == true): ?>
                <a href="<?=base_url(); ?>user/tutorship/add_tutorship_student.html" class="list-group-item<?php echo isset($add_tutorship) && $add_tutorship == true ? " active" : ""; ?>">添加辅导</a>
                <?php endif; ?>
                <div class="list-group-item site list-group-title"><i class="fa fa-group"></i>&nbsp;社会化管理</div> 
                <a href="#" class="list-group-item<?php echo isset($likes) && $likes == true ? " active" : ""; ?>">我的关注</a>
                <a href="#" class="list-group-item<?php echo isset($comments) && $comments == true ? " active" : ""; ?>">我的评论</a>
                <div class="list-group-item site list-group-title"><i class="fa fa-cog"></i>&nbsp;系统管理</div> 
                <a href="<?=base_url(); ?>user/setting.html" class="list-group-item<?php echo isset($setting) && $setting == true ? " active" : ""; ?>">账号设置</a>
                <a href="<?=base_url(); ?>user/logout.html" class="list-group-item">退出</a>
              </div>
            </div>