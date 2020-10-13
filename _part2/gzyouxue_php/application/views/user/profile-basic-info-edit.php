						  <div class="form-group">
                            <label for="figure" class="col-sm-2 control-label">头像</label>
                            <div class="col-sm-10">
                            	<input type="text" class="form-control" id="figure" placeholder="请上传头像">
                            </div>
                          </div>
                          <div class="form-group">
                            <label for="nickname" class="col-sm-2 control-label">昵称</label>
                            <div class="col-sm-10">
                            	<input type="text" class="form-control" id="nickname" name="nickname" placeholder="请输入昵称" value="<?=isset($user->nickname) && !empty($user->nickname) ? $user->nickname : ''; ?>">
                            </div>
                          </div>
                          <div class="form-group">
                            <label for="real_name" class="col-sm-2 control-label">真实姓名</label>
                            <div class="col-sm-10">
                            	<input type="text" class="form-control" id="real_name" name="real_name" placeholder="请输入真实姓名" value="<?=isset($user->real_name) && !empty($user->real_name) ? $user->real_name : ''; ?>">
                            </div>
                          </div>
                          <div class="form-group">
                            <label for="radio_gender" class="col-sm-2 control-label">性别</label>
                                <div class="col-sm-10">
                                	<div id="radio_gender" class="radio">
                                      <input type="radio" name="radio_gender" id="gender-male" value="男"<?=isset($user->gender) && !empty($user->gender) && $user->gender == "男" ? " checked" : ""; ?>>
                                      <label for="gender-male">男</label>
                                      &nbsp;&nbsp;&nbsp;&nbsp;
                                      <input type="radio" name="radio_gender" id="gender-female" value="女"<?=isset($user->gender) && !empty($user->gender) && $user->gender == "女" ? " checked" : ""; ?>>
                                      <label for="gender-female">女</label>
                                   	</div>
                            	</div>
                          </div>
                          <div class="form-group">
                            <label for="location" class="col-sm-2 control-label">所在区域</label>
                            <div class="col-sm-10">
                            	<input type="text" class="form-control" id="location" name="location" placeholder="请选择所在区域" value="<?=isset($user->location) && !empty($user->location) ? $user->location :''; ?>">
                            </div>
                          </div>
                          <div class="form-group">
                            <label for="phone" class="col-sm-2 control-label">手机号</label>
                            <div class="col-sm-10">
                            	<input type="text" class="form-control" id="phone" name="phone" placeholder="请输入手机号" value="<?=isset($user->phone) && !empty($user->phone) ? $user->phone : ''; ?>">
                            </div>
                          </div>
                          <div class="form-group">
                            <label for="qq" class="col-sm-2 control-label">QQ</label>
                            <div class="col-sm-10">
                            	<input type="text" class="form-control" id="qq" name="qq" placeholder="请输入QQ账号" value="<?=isset($user->qq) && !empty($user->qq) ? $user->qq : ''; ?>">
                            </div>
                          </div>
                          <div class="form-group">
                            <label for="weibo" class="col-sm-2 control-label">微博账号</label>
                            <div class="col-sm-10">
                            	<input type="text" class="form-control" id="weibo" name="weibo" placeholder="请输入微博账号" value="<?=isset($user->weibo) && !empty($user->weibo) ? $user->weibo : ''; ?>">
                            </div>
                          </div>
                          <div class="form-group">
                            <label for="wechat" class="col-sm-2 control-label">微信账号</label>
                            <div class="col-sm-10">
                            	<input type="text" class="form-control" id="wechat" name="wechat" placeholder="请输入微信账号" value="<?=isset($user->wechat) && !empty($user->wechat) ? $user->wechat : ''; ?>">
                            </div>
                          </div>
                          <div class="form-group">
                            <label for="my_email" class="col-sm-2 control-label">常用邮箱</label>
                            <div class="col-sm-10">
                            	<input type="text" class="form-control" id="my_email" name="my_email" placeholder="请输入常用邮箱" value="<?=isset($user->my_email) && !empty($user->my_email) ? $user->my_email : ''; ?>">
                            </div>
                          </div>