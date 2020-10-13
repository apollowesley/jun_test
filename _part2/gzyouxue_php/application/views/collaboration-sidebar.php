        	<div class="col-xs-6 col-sm-5 col-md-4 col-lg-3 sidebar-offcanvas" id="sidebar" role="navigation">
              <div class="list-group">
                <a href="<?=base_url(); ?>school.html" class="list-group-item<?php echo isset($school) && $school == true ? ' active':''; ?>">学校列表</a>
                <a href="<?=base_url(); ?>brand.html" class="list-group-item<?php echo isset($brand) && $brand == true ? ' active':''; ?>">品牌列表</a>
                <a href="<?=base_url(); ?>sponsor.html" class="list-group-item<?php echo isset($sponsor) && $sponsor == true ? ' active':''; ?>">赞助商列表</a>
                <a href="<?=base_url(); ?>ads.html" class="list-group-item<?php echo isset($ads) && $ads == true ? ' active':''; ?>">广告合作</a>
                <a href="<?=base_url(); ?>links.html" class="list-group-item<?php echo isset($links) && $links == true ? ' active':''; ?>">友情链接</a>
              </div>
            </div>