<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
        <div class="container">
          <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
              <span class="sr-only">导航</span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="<?php echo base_url(); ?>">优学网</a>
          </div>
          <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
              <li class="dropdown">
              	<?php if (isset($logined_admin) && !empty($logined_admin) && isset($logined_admin["uid"])): ?>
                <a href="#" class="dropdown-toggle site user-a" data-toggle="dropdown" aria-expanded="false"><img src="<?=$logined_admin["figure"]; ?>" class="img-circle site u-mini-icon" />&nbsp;&nbsp;<?=$logined_admin["real_name"]?>&nbsp;<span class="caret"></span></a>
                <ul class="dropdown-menu" role="menu">
                  <li><a href="<?=base_url(); ?>admin/profile.html"><i class="fa fa-info-circle"></i>&nbsp;我的信息</a></li>
                  <li><a href="#"><i class="fa fa-cog"></i>&nbsp;账号设置</a></li>
                  <li class="divider"></li>
                  <li><a href="<?=base_url(); ?>admin/logout.html"><i class="fa fa-sign-out"></i>&nbsp;退出</a></li>
                </ul>
                <?php endif; ?>
              </li>
            </ul>
          </div>
        </div>
      </nav>