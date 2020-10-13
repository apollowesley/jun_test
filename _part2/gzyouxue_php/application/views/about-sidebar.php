<div class="col-xs-6 col-sm-5 col-md-4 col-lg-3 sidebar-offcanvas" id="sidebar" role="navigation">
              <div class="list-group">
                <a href="<?=base_url(); ?>about.html" class="list-group-item<?php echo isset($about) && $about == true ? ' active' : ''; ?>">关于我们</a>
                <a href="<?=base_url(); ?>contact.html" class="list-group-item<?php echo isset($contact) && $contact == true ? ' active' : ''; ?>">联系方式</a>
                <a href="<?=base_url(); ?>evaluation.html" class="list-group-item<?php echo isset($evaluation) && $evaluation == true ? ' active' : ''; ?>">用户评价</a>
                <a href="<?=base_url(); ?>help.html" class="list-group-item<?php echo isset($help) && $help == true ? ' active' : ''; ?>">问题及帮助</a>
                <a href="<?=base_url(); ?>suggestion.html" class="list-group-item<?php echo isset($suggestion) && $suggestion == true ? ' active' : ''; ?>">留言及建议</a>
                <a href="<?=base_url(); ?>blacklist.html" class="list-group-item<?php echo isset($blacklist) && $blacklist == true ? ' active' : ''; ?>">黑名单</a>
                <a href="<?=base_url(); ?>privacy.html" class="list-group-item<?php echo isset($privacy) && $privacy == true ? ' active' : ''; ?>">隐私声明</a>
                <a href="<?=base_url(); ?>thanks.html" class="list-group-item<?php echo isset($thanks) && $thanks == true ? ' active' : ''; ?>">特别鸣谢</a>
              </div>
            </div>