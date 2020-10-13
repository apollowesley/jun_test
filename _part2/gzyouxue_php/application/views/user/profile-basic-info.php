						<div class="col-xs-12 col-sm-3 col-md-2 col-lg-2">
                            <p>头像：</p>
                        </div>
                        <div class="col-xs-12 col-sm-9 col-md-10 col-lg-10">
                            <p><img src="<?=$logined_user["figure"]; ?>" class="img-circle site u-small-icon"/></p>
                        </div>
                        <div class="col-xs-12 col-sm-3 col-md-2 col-lg-2">
                            <p>昵称：</p>
                        </div>
                        <div class="col-xs-12 col-sm-9 col-md-10 col-lg-10">
                            <p><?=$logined_user["nickname"]; ?></p>
                        </div>
                        <div class="col-xs-12 col-sm-3 col-md-2 col-lg-2">
                            <p>真实姓名：</p>
                        </div>
                        <div class="col-xs-12 col-sm-9 col-md-10 col-lg-10">
                            <p><?=empty($user->real_name) ? PROFILE_EMPTY : $user->real_name; ?></p>
                        </div>
                        <div class="col-xs-12 col-sm-3 col-md-2 col-lg-2">
                            <p>性别：</p>
                        </div>
                        <div class="col-xs-12 col-sm-9 col-md-10 col-lg-10">
                            <p><?=empty($user->gender) ? PROFILE_EMPTY : $user->gender; ?></p>
                        </div>
                        <div class="col-xs-12 col-sm-3 col-md-2 col-lg-2">
                            <p>所在区域：</p>
                        </div>
                        <div class="col-xs-12 col-sm-9 col-md-10 col-lg-10">
                            <p><?=empty($user->location) ? PROFILE_EMPTY : $user->location; ?></p>
                        </div>
                        <div class="col-xs-12 col-sm-3 col-md-2 col-lg-2">
                            <p>手机号：</p>
                        </div>
                        <div class="col-xs-12 col-sm-9 col-md-10 col-lg-10">
                            <p><?=empty($user->phone) ? PROFILE_EMPTY : $user->phone; ?></p>
                        </div>
                        <div class="col-xs-12 col-sm-3 col-md-2 col-lg-2">
                            <p>QQ：</p>
                        </div>
                        <div class="col-xs-12 col-sm-9 col-md-10 col-lg-10">
                            <p><?=empty($user->qq) ? PROFILE_EMPTY : $user->qq; ?></p>
                        </div>
                        <div class="col-xs-12 col-sm-3 col-md-2 col-lg-2">
                            <p>微博：</p>
                        </div>
                        <div class="col-xs-12 col-sm-9 col-md-10 col-lg-10">
                            <p><?=empty($user->weibo) ? PROFILE_EMPTY : $user->weibo; ?></p>
                        </div>
                        <div class="col-xs-12 col-sm-3 col-md-2 col-lg-2">
                            <p>微信：</p>
                        </div>
                        <div class="col-xs-12 col-sm-9 col-md-10 col-lg-10">
                            <p><?=empty($user->wechat) ? PROFILE_EMPTY : $user->wechat; ?></p>
                        </div>
                        <div class="col-xs-12 col-sm-3 col-md-2 col-lg-2">
                            <p>常用邮箱：</p>
                        </div>
                        <div class="col-xs-12 col-sm-9 col-md-10 col-lg-10">
                            <p><?=empty($user->my_email) ? PROFILE_EMPTY : $user->my_email; ?></p>
                        </div>
                        <div class="col-xs-12 col-sm-3 col-md-2 col-lg-2">
                            <p>注册时间：</p>
                        </div>
                        <div class="col-xs-12 col-sm-9 col-md-10 col-lg-10">
                            <p><?=$user->reg_time; ?></p>
                        </div>
                        <div class="col-xs-12 col-sm-3 col-md-2 col-lg-2">
                            <p>最近登录时间：</p>
                        </div>
                        <div class="col-xs-12 col-sm-9 col-md-10 col-lg-10">
                            <p><?=$user->login_time; ?></p>
                        </div>
                        <div class="col-xs-12 col-sm-3 col-md-2 col-lg-2">
                            <p>最近修改时间：</p>
                        </div>
                        <div class="col-xs-12 col-sm-9 col-md-10 col-lg-10">
                            <p><?=$user->update_time; ?></p>
                        </div>
                        
                        