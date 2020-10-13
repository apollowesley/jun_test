<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="modal fade nav-search-box" tabindex="-1" role="dialog"
	aria-labelledby="navSearchModalLabel">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header" style="padding: 5px 15px;">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close" style="margin-top: 10px;">
					<span aria-hidden="true">×</span>
				</button>
				<h4>
					<i class="fa fa-search"></i> 搜索文章
				</h4>
			</div>
			<div class="modal-body">
				<form action="#" method="post" class="form-horizontal searchForm"
					id="searchForm">
					<input type="hidden" name="pageNumber" value="1">
					<div class="input-group bottom-line">
						<input type="text" class="form-control br-none" name="keywords"
							value="" required="required" placeholder="输入搜索内容"> <span
							class="input-group-btn">
							<button class="btn btn-default br-none nav-search-btn pointer"
								type="submit">
								<i class="fa fa-search"></i> 搜索
							</button>
						</span>
					</div>
					<div class="clear"></div>
					<ul class="list-unstyled list-inline search-hot">
						<li><strong
							style="position: relative; top: 2px; color: #999999;">热门搜索：</strong></li>
						<li><a class="pointer" rel="external nofollow"><span
								class="label label-default">Java</span></a></li>
						<li><a class="pointer" rel="external nofollow"><span
								class="label label-primary">Springboot</span></a></li>
						<li><a class="pointer" rel="external nofollow"><span
								class="label label-success">Linux</span></a></li>
						<li><a class="pointer" rel="external nofollow"><span
								class="label label-info">Maven</span></a></li>
						<li><a class="pointer" rel="external nofollow"><span
								class="label label-warning">Bootstrap</span></a></li>
						<li><a class="pointer" rel="external nofollow"><span
								class="label label-danger">阿里云</span></a></li>
					</ul>
				</form>
			</div>
		</div>
	</div>
</div>