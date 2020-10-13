<div class="col-xs-6 col-sm-5 col-md-4 col-lg-3 sidebar-offcanvas" id="sidebar" role="navigation">
              <div class="list-group">
              	<div class="list-group-item site list-group-title"><i class="fa fa-suitcase"></i>&nbsp;辅导管理</div> 
                <a href="<?=base_url(); ?>admin/tutorships.html" class="list-group-item<?php echo isset($tutorships) && $tutorships == true ? ' active' : ''; ?>">所有辅导</a>
                <a href="<?=base_url(); ?>admin/tutorship/add_student.html" class="list-group-item<?php echo isset($add_tutorship) && $add_tutorship == true ? ' active' : ''; ?>">添加辅导</a>
              	<div class="list-group-item site list-group-title"><i class="fa fa-user"></i>&nbsp;用户管理</div> 
                <a href="<?=base_url(); ?>admin/students.html" class="list-group-item<?php echo isset($students) && $students == true ? ' active' : ''; ?>">所有学生</a>
                <a href="<?=base_url(); ?>admin/parents.html" class="list-group-item<?php echo isset($parents) && $parents == true ? ' active' : ''; ?>">所有家长</a>
                <a href="<?=base_url(); ?>admin/teachers.html" class="list-group-item<?php echo isset($teachers) && $teachers == true ? ' active' : ''; ?>">所有教师</a>
                <div class="dropdown list-group-item<?php echo isset($add) && $add == true ? ' active' : ''; ?>">
                    <div class="dropdown-toggle site pointer-cursor" data-toggle="dropdown" aria-expanded="false">
                    	添加用户 <span class="caret"></span>
                    </div>
                    <ul class="dropdown-menu" role="menu">
                      <li<?php echo isset($add_student) && $add_student == true ? ' class="active"' : ''; ?>><a href="<?=base_url(); ?>admin/student/add.html">学生</a></li>
                      <li<?php echo isset($add_parent) && $add_parent == true ? ' class="active"' : ''; ?>><a href="<?=base_url(); ?>admin/parent/add.html">家长</a></li>
                      <li<?php echo isset($add_teacher) && $add_teacher == true ? ' class="active"' : ''; ?>><a href="<?=base_url(); ?>admin/teacher/add.html">教师</a></li>
                    </ul>
                </div>
                <div class="list-group-item site list-group-title"><i class="fa fa-book"></i>&nbsp;超级课程管理</div> 
                <a href="#" class="list-group-item">所有课程</a>
                <a href="#" class="list-group-item">添加课程</a>
                <div class="list-group-item site list-group-title"><i class="fa fa-headphones"></i>&nbsp;访谈管理</div> 
                <a href="#" class="list-group-item">所有访谈</a>
                <a href="#" class="list-group-item">添加访谈</a>
                <div class="list-group-item site list-group-title"><i class="fa fa-leaf"></i>&nbsp;广告管理</div> 
                <a href="#" class="list-group-item">所有广告</a>
                <a href="#" class="list-group-item">发布广告</a>
                <div class="list-group-item site list-group-title"><i class="fa fa-cog"></i>&nbsp;系统管理</div>
                <a href="<?=base_url(); ?>admin/profile.html" class="list-group-item<?php echo isset($profile) && $profile == true ? ' active' : ''; ?>">我的信息</a>
                <?php if (isset($logined_admin) && isset($logined_admin["uid"]) && isset($logined_admin["admin_type"]) && $logined_admin["admin_type"] == ADMIN_TYPE_SUPER): ?>
                <a href="#" class="list-group-item">所有管理员</a>
                <a href="<?=base_url(); ?>admin/profile/add_admin.html" class="list-group-item">添加管理员</a>
                <a href="#" class="list-group-item">优教账号</a>
                <?php endif; ?>
                <a href="<?=base_url(); ?>admin/profile/setting.html" class="list-group-item">账号设置</a>
                <a href="<?=base_url(); ?>admin/logout.html" class="list-group-item">退出</a>
              </div>
            </div>