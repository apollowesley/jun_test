<?php
//如果是密码保护的文章，没经过密码验证不显示评论框
if ( post_password_required() )
	return;
?>
<?php
//定义评论框文本
global $dmeng_option;
$zero = $dmeng_option['comments']['_comments_zero'];//没有评论
$one = $dmeng_option['comments']['_comments_one'];//一条评论
$more = $dmeng_option['comments']['_comments_more'];//多条评论

$comment_author = $dmeng_option['comments']['_comment_author'];//名称
$comment_author_placeholder = $dmeng_option['comments']['_comment_author_placeholder'];//名称背景
$comment_email = $dmeng_option['comments']['_comment_email'];//邮箱
$comment_email_placeholder = $dmeng_option['comments']['_comment_email_placeholder'];//邮箱背景
$comment_url = $dmeng_option['comments']['_comment_url'];//网址
$comment_url_placeholder = $dmeng_option['comments']['_comment_url_placeholder'];//网址背景
$title_reply = $dmeng_option['comments']['_title_reply'];//评论框标题
$title_reply_to = $dmeng_option['comments']['_title_reply_to'];//评论给&s
$cancel_reply_link = $dmeng_option['comments']['_cancel_reply_link'];//取消评论
$label_submit = $dmeng_option['comments']['_label_submit'];//提交按钮
$from_placeholder = $dmeng_option['comments']['_from_placeholder'];//评论框背景
$comment_notes_before = $dmeng_option['comments']['_comment_notes_before'];//评论框上方
$comment_notes_after = $dmeng_option['comments']['_comment_notes_after'];//评论框下方
$comment_close = $dmeng_option['comments']['_comment_close'];//关闭评论时
$comment_approved = $dmeng_option['comments']['_comment_approved'];//等待审核时

//评论框样式
$have_comments_color = $dmeng_option['comments']['_have_comments_color'];//（有评论时）配色方案
$no_comments_color = $dmeng_option['comments']['_no_comments_color'];//（没评论时）配色方案
$comments_color = $dmeng_option['comments']['_comments_color'];//（评论内容）配色方案
?>
<div id="comments"  class="panel <?php if ( have_comments() ) { echo $have_comments_color;}else{echo $no_comments_color;} ?>">

		<div class="panel-heading">
		<h4><?php
		if ( comments_open() ) {
		comments_number( $zero, $one, $more );
		}else{
		comments_number( "$comment_close", "$comment_close 曾有一条评论。", "$comment_close 曾有%条评论。" );
		}
		?></h4>
		</div>
		<?php if ( comments_open() || have_comments() ) : ?>
		<div class="panel-body">

<ol>
<?php wp_list_comments('type=comment&style=ol&callback=mytheme_comment'); ?>
</ol>
		<?php
			// Are there comments to navigate through?
			if ( get_comment_pages_count() > 1 && get_option( 'page_comments' ) ) :
		?>
		<nav class="navigation comment-navigation" role="navigation">
		<ul class="pager">
			<li class="previous"><?php previous_comments_link( __( '&larr; 上一页' ) ); ?></li>
			<li class="next"><?php next_comments_link( __( '下一页 &rarr;' ) ); ?></li>
		</ul>
		</nav><!-- .comment-navigation -->
		<?php endif; // Check for comment navigation ?>
		
<?php
$comment_args = array(
  'id_form'           => 'commentform',
  'id_submit'         => 'submit',
  'title_reply'       => $title_reply,
  'title_reply_to'    => $title_reply_to,
  'cancel_reply_link' => $cancel_reply_link,
  'label_submit'      => $label_submit,

  'comment_field' =>  '<div class="clean"></div><div class="col-xs-12 mt20"><textarea class="form-control" rows="3" placeholder="'.$from_placeholder.'" id="comment" name="comment" cols="45" aria-required="true">' .
    '</textarea></div>',

  'must_log_in' => '<p class="must-log-in">' .
    sprintf(
      __( 'You must be <a href="%s">logged in</a> to post a comment.' ),
      wp_login_url( apply_filters( 'the_permalink', get_permalink() ) )
    ) . '</p>',

  'logged_in_as' => '<p class="logged-in-as">' .
    sprintf(
    __( 'Logged in as <a href="%1$s">%2$s</a>. <a href="%3$s" title="Log out of this account">Log out?</a>' ),
      admin_url( 'profile.php' ),
      $user_identity,
      wp_logout_url( apply_filters( 'the_permalink', get_permalink( ) ) )
    ) . '</p>',

  'comment_notes_before' => '<div class="col-xs-12 mt20 mb20"><span class="help-block">'.$comment_notes_before.'</span></div>',

  'comment_notes_after' => '<div class="col-xs-12 mt20 mb20"><span class="help-block">'.$comment_notes_after.'</span></div>',

  'fields' => apply_filters( 'comment_form_default_fields', array(

    'author' =>
      '<div class="col-lg-4 col-md-4 col-sm-4">' .
      '<label for="author">'.$comment_author.'</label> ' .
      ( $req ? '<span class="text-danger"><span class="glyphicon glyphicon-asterisk"></span></span>' : '' ) .
      '<input class="form-control" placeholder="'.$comment_author_placeholder.'" id="author" name="author" type="text" value="' . esc_attr( $commenter['comment_author'] ) .
      '" size="30"' . $aria_req . ' /></div>',

    'email' =>
      '<div class="col-lg-4 col-md-4 col-sm-4">' .
	  '<label for="email">'.$comment_email.'</label> ' .
      ( $req ? '<span class="text-danger"><span class="glyphicon glyphicon-asterisk"></span></span>' : '' ) .
      '<input class="form-control" placeholder="'.$comment_email_placeholder.'" id="email" name="email" type="text" value="' . esc_attr(  $commenter['comment_author_email'] ) .
      '" size="30"' . $aria_req . ' />' .
	  '</div>',

    'url' =>
      '<div class="col-lg-4 col-md-4 col-sm-4">' .
	  '<label for="url">'.$comment_url.'</label>' .
      '<input class="form-control" placeholder="'.$comment_url_placeholder.'" id="url" name="url" type="text" value="' . esc_attr( $commenter['comment_author_url'] ) .
      '" size="30" />' .
	  '</div>'
    )
  ),
);
comment_form($comment_args);
?>
	</div><?php endif; ?>
</div><!-- #comments -->