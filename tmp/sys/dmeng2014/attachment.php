<?php get_header();?>

<?php $attachment_display = $dmeng_option['sidebar']['_attachment_display'];$attachment_number = $dmeng_option['sidebar']['_attachment_number']; ?>
<?php if ( $attachment_display=="yes-left") {
echo '<div class="col-lg-4 col-md-4 sidebar">
	<div class="clean"></div>';
		switch ($attachment_number) {
		case "one": if ( is_active_sidebar( 'one-sidebar' ) ) : dynamic_sidebar( 'one-sidebar' ); endif; ;break;
		case "two": if ( is_active_sidebar( 'two-sidebar' ) ) : dynamic_sidebar( 'two-sidebar' ); endif; ;break;
		case "three": if ( is_active_sidebar( 'three-sidebar' ) ) : dynamic_sidebar( 'three-sidebar' ); endif; ;break;
		case "four": if ( is_active_sidebar( 'four-sidebar' ) ) : dynamic_sidebar( 'four-sidebar' ); endif; ;break;
		}
echo '</div>';
}?>

<div class="<?php if ( $attachment_display=="no") { echo 'col-lg-12 col-md-12'; }else{echo 'col-lg-8 col-md-8'; } ?> content">

<?php if (have_posts()) : ?>
<?php while (have_posts()) : the_post(); ?>

<div class="panel panel-default">
  <div class="panel-heading">
	<h1 class="pull-left"><?php the_title(); ?></h1>
	<?php $shareDisplay = $dmeng_option['theme']['_share_display'];?>
	<?php
	if ( $shareDisplay=="yes") {
		if ( !wp_is_mobile() ) { ?>
			<div class="pull-right">
			<?php get_template_part( 'share' ); ?>
			</div>
	<?php
		}
	}	?>
	<div class="clean"></div>
  </div>
  <div class="panel-body">
	<div class="panel panel-primary">
		<div class="panel-heading">附件信息</div>
		<ul class="list-group">
		<img src="<?php echo wp_mime_type_icon(); ?>" class="pull-right" style="position:relative;z-index:999;margin:10px;">

<?php
	$post = get_post();

	$filename = esc_html( wp_basename( $post->guid ) );

	$media_dims = '';
	$meta = wp_get_attachment_metadata( $post->ID );
	if ( isset( $meta['width'], $meta['height'] ) )
		$media_dims .= "<span id='media-dims-$post->ID'>{$meta['width']}&nbsp;&times;&nbsp;{$meta['height']}</span> ";
	$media_dims = apply_filters( 'media_meta', $media_dims, $post );

	$att_url = wp_get_attachment_url( $post->ID );
?>
	<li class="list-group-item misc-pub-attachment">
			文件URL：<a href="<?php echo esc_attr($att_url); ?>" target="_blank"><span class="glyphicon glyphicon-link"></span> 文件链接</a>
	</li>
	<li class="list-group-item misc-pub-filename">
		文件名： <code itemprop="name"><?php echo $filename; ?></code>
	</li>
	<li class="list-group-item misc-pub-filetype">
		文件类型： <code><?php
			if ( preg_match( '/^.*?\.(\w+)$/', get_attached_file( $post->ID ), $matches ) )
				echo esc_html( strtoupper( $matches[1] ) );
			else
				echo strtoupper( str_replace( 'image/', '', $post->post_mime_type ) );
		?></code>
	</li>

	<?php
		$file  = get_attached_file( $post->ID );
		$file_size = false;

		if ( isset( $meta['filesize'] ) )
			$file_size = $meta['filesize'];
		elseif ( file_exists( $file ) )
			$file_size = filesize( $file );

		if ( ! empty( $file_size ) ) : ?>
			<li class="list-group-item misc-pub-filesize">
				文件大小： <code><?php echo size_format( $file_size ); ?></code>
			</li>
			<?php
		endif;

	if ( preg_match( '#^(audio|video)#', $post->post_mime_type ) ):

		$fields = apply_filters( 'media_submitbox_misc_sections', array(
			'mime_type'        => __( 'MIME类型：' ),
			'year'             => __( '年份：' ),
			'genre'            => __( '流派：' ),
			'length_formatted' => __( '长度：' ),
		) );

		foreach ( $fields as $key => $label ):
			if ( ! empty( $meta[$key] ) ) : ?>
		<li class="list-group-item misc-pub-mime-meta misc-pub-<?php echo sanitize_html_class( $key ); ?>">
			<?php echo $label ?> <code><?php echo esc_html( $meta[$key] ); ?></code>
		</li>
	<?php
			endif;
		endforeach;

		if ( ! empty( $meta['bitrate'] ) ) : ?>
		<li class="list-group-item misc-pub-bitrate">
			比特率： <code><?php
				echo round( $meta['bitrate'] / 1000 ), 'kb/s';

				if ( ! empty( $meta['bitrate_mode'] ) )
					echo ' ', strtoupper( $meta['bitrate_mode'] );

			?></code>
		</li>
	<?php
		endif;

		$audio_fields = apply_filters( 'audio_submitbox_misc_sections', array(
			'dataformat' => __( '音频格式：' ),
			'codec'      => __( '音频编码器：' )
		) );

		foreach ( $audio_fields as $key => $label ):
			if ( ! empty( $meta['audio'][$key] ) ) : ?>
		<li class="list-group-item misc-pub-audio misc-pub-<?php echo sanitize_html_class( $key ); ?>">
			<?php echo $label; ?> <code><?php echo esc_html( $meta['audio'][$key] ); ?></code>
		</li>
	<?php
			endif;
		endforeach;

	endif;

	if ( $media_dims ) : ?>
	<li class="list-group-item misc-pub-dimensions">
		尺寸： <code><?php echo $media_dims; ?></code>
	</li>
<?php
	endif;
?>

        </ul>
<?php if ( !wp_is_mobile() ) { //如果不是手机就显示可用的附件预览 ?>
<?php //附件预览开始
	$thumb_url = false;
	if ( $attachment_id = intval( $post->ID ) )
		$thumb_url = wp_get_attachment_image_src( $attachment_id, array( 900, 450 ), true );

	$filename = esc_html( basename( $post->guid ) );
	$title = esc_attr( $post->post_title );
	$alt_text = get_post_meta( $post->ID, '_wp_attachment_image_alt', true );

	$att_url = wp_get_attachment_url( $post->ID ); ?>

	<?php if ( wp_attachment_is_image( $post->ID ) ) : ?>
	<div class="panel-heading">图片预览</div>
	<div class="panel-body">
	
		<div class="imgedit-response" id="imgedit-response-<?php echo $attachment_id; ?>"></div>

		<div class="wp_attachment_image" id="media-head-<?php echo $attachment_id; ?>">
			<p id="thumbnail-head-<?php echo $attachment_id; ?>"><img class="thumbnail" src="<?php echo esc_attr($att_url); ?>" style="max-width:100%" alt="" /></p>
		</div>
		
	</div>
	<?php
	elseif ( $attachment_id && 0 === strpos( $post->post_mime_type, 'audio/' ) ):

		echo '
	<div class="panel-heading">音频预览</div>
	<div class="panel-body">';
	
		echo wp_audio_shortcode( array( 'src' => $att_url ) );
		
		echo '
	</div>';

	elseif ( $attachment_id && 0 === strpos( $post->post_mime_type, 'video/' ) ):

		$meta = wp_get_attachment_metadata( $attachment_id );
		$w = ! empty( $meta['width'] ) ? min( $meta['width'], 600 ) : 0;
		$h = 0;
		if ( ! empty( $meta['height'] ) )
			$h = $meta['height'];
		if ( $h && $w < $meta['width'] )
			$h = round( ( $meta['height'] * $w ) / $meta['width'] );

		$attr = array( 'src' => $att_url );

		if ( ! empty( $meta['width' ] ) )
			$attr['width'] = $w;

		if ( ! empty( $meta['height'] ) )
			$attr['height'] = $h;
			
		echo '
	<div class="panel-heading">视频预览</div>
	<div class="panel-body">';

		echo wp_video_shortcode( $attr );
		
		echo '
	</div>';
	
	else :
	
		echo '<div class="panel-footer">此附件类型暂不支持预览。</div>';

	endif;
//附件预览结束 ?>
<?php } else { //否则是手机则显示不支持预览
echo '<div class="panel-footer">手机暂不支持预览附件。</div>';
}?>

	</div>
	<?php the_content(); ?>
  </div>
  <div class="panel-footer">
  由 <?php the_author(); ?> 在 <?php echo get_the_date(); ?> 发布 <?php setPostViews(get_the_ID());?> <?php echo ' 共有 '.getPostViews(get_the_ID()).' 次浏览';?> <a href="#comments"><span class="text-danger"><abbr title="内容报错"><span class="glyphicon glyphicon-info-sign"></span> 报错</span></abbr></a>
  </div>
</div>
<?php endwhile; ?>
<?php endif; ?>

</div>
	
<?php if ( $attachment_display=="yes-right") {
echo '<div class="col-lg-4 col-md-4 sidebar">
	<div class="clean"></div>';
		switch ($attachment_number) {
		case "one": if ( is_active_sidebar( 'one-sidebar' ) ) : dynamic_sidebar( 'one-sidebar' ); endif; ;break;
		case "two": if ( is_active_sidebar( 'two-sidebar' ) ) : dynamic_sidebar( 'two-sidebar' ); endif; ;break;
		case "three": if ( is_active_sidebar( 'three-sidebar' ) ) : dynamic_sidebar( 'three-sidebar' ); endif; ;break;
		case "four": if ( is_active_sidebar( 'four-sidebar' ) ) : dynamic_sidebar( 'four-sidebar' ); endif; ;break;
		}
echo '</div>';
}?>

<?php get_footer();?>