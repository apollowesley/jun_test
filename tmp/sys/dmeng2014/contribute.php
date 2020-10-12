<?php
/**
 * Template Name: 投稿页面模版
 */
// 获取当前页面完整URL
function curPageURL() {
     $pageURL = 'http';

    if ($_SERVER["HTTPS"] == "on") 
     {
         $pageURL .= "s";
     }
     $pageURL .= "://";

    if ($_SERVER["SERVER_PORT"] != "80") 
     {
         $pageURL .= $_SERVER["SERVER_NAME"] . ":" . $_SERVER["SERVER_PORT"] . $_SERVER["REQUEST_URI"];
     } 
     else 
     {
         $pageURL .= $_SERVER["SERVER_NAME"] . $_SERVER["REQUEST_URI"];
     }
     return $pageURL;
}
if( isset($_POST['tougao_form']) && $_POST['tougao_form'] == 'send') {
    global $wpdb;
    $current_url = curPageURL();   // 注意修改此处的链接地址

    $last_post = $wpdb->get_var("SELECT `post_date` FROM `$wpdb->posts` ORDER BY `post_date` DESC LIMIT 1");

    // 博客当前最新文章发布时间与要投稿的文章至少间隔120秒。
    // 可自行修改时间间隔，修改下面代码中的120即可
    // 相比Cookie来验证两次投稿的时间差，读数据库的方式更加安全
    if ( current_time('timestamp') - strtotime($last_post) < 120 ) {
        wp_die('您投稿也太勤快了吧，先歇会儿！<a href="'.$current_url.'">点此返回</a>');
    }
        
    // 表单变量初始化
    $name = isset( $_POST['tougao_authorname'] ) ? trim(htmlspecialchars($_POST['tougao_authorname'], ENT_QUOTES)) : '';
    $email =  isset( $_POST['tougao_authoremail'] ) ? trim(htmlspecialchars($_POST['tougao_authoremail'], ENT_QUOTES)) : '';
    $blog =  isset( $_POST['tougao_authorblog'] ) ? trim(htmlspecialchars($_POST['tougao_authorblog'], ENT_QUOTES)) : '';
    $title =  isset( $_POST['tougao_title'] ) ? trim(htmlspecialchars($_POST['tougao_title'], ENT_QUOTES)) : '';
    $category =  isset( $_POST['cat'] ) ? (int)$_POST['cat'] : 0;
    $content =  isset( $_POST['tougao_content'] ) ? trim(htmlspecialchars($_POST['tougao_content'], ENT_QUOTES)) : '';
    
    // 表单项数据验证
    if ( empty($name) || mb_strlen($name) > 20 ) {
        wp_die('昵称必须填写，且长度不得超过20字。<a href="'.$current_url.'">点此返回</a>');
    }
    
    if ( empty($email) || strlen($email) > 60 || !preg_match("/^([a-z0-9\+_\-]+)(\.[a-z0-9\+_\-]+)*@([a-z0-9\-]+\.)+[a-z]{2,6}$/ix", $email)) {
        wp_die('Email必须填写，且长度不得超过60字，必须符合Email格式。<a href="'.$current_url.'">点此返回</a>');
    }
    
    if ( empty($title) || mb_strlen($title) > 100 ) {
        wp_die('标题必须填写，且长度不得超过100字。<a href="'.$current_url.'">点此返回</a>');
    }
    
    if ( empty($content) || mb_strlen($content) > 3000 || mb_strlen($content) < 10) {
        wp_die('内容必须填写，且长度不得超过3000字，不得少于10字。<a href="'.$current_url.'">点此返回</a>');
    }
    
    $post_content = '昵称: '.$name.'<br />Email: '.$email.'<br />blog: '.$blog.'<br />内容:<br />'.$content;
    
    $tougao = array(
        'post_title' => $title, 
        'post_content' => $post_content,
        'post_category' => array($category)
    );


    // 将文章插入数据库
    $status = wp_insert_post( $tougao );
  
    if ($status != 0) { 
        // 投稿成功给博主发送邮件
        // somebody#example.com替换博主邮箱
        // My subject替换为邮件标题，content替换为邮件内容
        wp_mail("somebody#example.com","My subject","content");

        wp_die('投稿成功！感谢投稿！<a href="'.$current_url.'">点此返回</a>', '投稿成功');
    }
    else {
        wp_die('投稿失败！<a href="'.$current_url.'">点此返回</a>');
    }
}
get_header();?>

<div class="col-lg-8 col-md-8 content">

<?php if (have_posts()) : ?>
<?php while (have_posts()) : the_post(); ?>

<div class="panel panel-default">
  <div class="panel-heading">
	<h1><?php the_title(); ?></h1>
  </div>
  <div class="panel-body">

<form class="ludou-tougao" method="post" action="<?php echo $_SERVER["REQUEST_URI"]; $current_user = wp_get_current_user(); ?>">
    <div class="col-lg-4 col-md-4 col-sm-4 mt20">
        <label for="tougao_authorname">昵称 <span class="text-danger"><span class="glyphicon glyphicon-asterisk"></span></span></label>
        <input class="form-control" type="text" size="40" value="<?php if ( 0 != $current_user->ID ) echo $current_user->user_login; ?>" id="tougao_authorname" name="tougao_authorname" placeholder="请输入你的名称" />
    </div>

    <div class="col-lg-4 col-md-4 col-sm-4 mt20">
        <label for="tougao_authoremail">邮箱 <span class="text-danger"><span class="glyphicon glyphicon-asterisk"></span></span></label>
        <input class="form-control" type="text" size="40" value="<?php if ( 0 != $current_user->ID ) echo $current_user->user_email; ?>" id="tougao_authoremail" name="tougao_authoremail" placeholder="请输入你的邮箱" />
    </div>
                    
    <div class="col-lg-4 col-md-4 col-sm-4 mt20">
        <label for="tougao_authorblog">网址</label>
        <input class="form-control" type="text" size="40" value="<?php if ( 0 != $current_user->ID ) echo $current_user->user_url; ?>" id="tougao_authorblog" name="tougao_authorblog" placeholder="没有网站可不填" />
    </div>

	<div class="clean"></div>
	
    <div class="col-lg-12 col-md-12 col-sm-12 mt20">
        <label for="tougao_title">标题 <span class="text-danger"><span class="glyphicon glyphicon-asterisk"></span></span></label>
        <input class="form-control" type="text" size="40" value="" id="tougao_title" name="tougao_title" placeholder="如：WordPress自定义文章类型实例解析" />
    </div>
                    
    <div class="col-lg-12 col-md-12 col-sm-12 mt20">
        <label style="vertical-align:top" for="tougao_content">内容 <span class="text-danger"><span class="glyphicon glyphicon-asterisk"></span></span></label>
        <textarea class="form-control" rows="15" cols="55" id="tougao_content" name="tougao_content" placeholder="请输入详细内容" ></textarea>
    </div>
                    
    <div class="clean"></div>
    <div style="text-align:center;" class="mt20">
        <input type="hidden" value="send" name="tougao_form" />
        <input type="submit" value="提交" class="btn btn-success" /> 
        <input type="reset" value="重填" class="btn btn-warning" />
    </div>
</form>
  </div>
  <div class="panel-footer text-muted">请用尽量简短的文字来描述问题（不能少于10字）</div>
</div>
<?php setPostViews(get_the_ID());?>
<?php endwhile; ?>
<?php endif; ?>

</div>
	
<div class="col-lg-4 col-md-4 sidebar">

	<div style="clear:both;"></div>
	
	<div class="well">
	<?php the_content(); ?>
	</div>

</div>

<?php get_footer();?>