<?php
//移除WordPress自带的小工具
function remove_default_widget() {
	unregister_widget('WP_Widget_Recent_Posts');//移除近期文章
	unregister_widget('WP_Widget_Recent_Comments');//移除近期评论
	unregister_widget('WP_Widget_Meta');//移除站点功能
	unregister_widget('WP_Widget_Tag_Cloud');//移除标签云
	unregister_widget('WP_Widget_Text');//移除文本框
	unregister_widget('WP_Widget_Archives');//移除文章归档
	unregister_widget('WP_Widget_RSS');//移除RSS
	unregister_widget('WP_Nav_Menu_Widget');//移除菜单
	unregister_widget('WP_Widget_Pages');//移除页面
	unregister_widget('WP_Widget_Calendar');//移除日历
}
add_action( 'widgets_init', 'remove_default_widget' );
//新的最近文章
class recentPostWidget extends WP_Widget {

	function __construct() {
		$widget_ops = array('classname' => 'recent_entries_widget', 'description' => __( "您站点最近的文章") );
		parent::__construct('recent-posts', __('Recent Posts'), $widget_ops);
		$this->alt_option_name = 'recent_entries_widget';

		add_action( 'save_post', array($this, 'flush_widget_cache') );
		add_action( 'deleted_post', array($this, 'flush_widget_cache') );
		add_action( 'switch_theme', array($this, 'flush_widget_cache') );
	}

	function widget($args, $instance) {
		$cache = wp_cache_get('recent_posts_widget', 'widget');

		if ( !is_array($cache) )
			$cache = array();

		if ( ! isset( $args['widget_id'] ) )
			$args['widget_id'] = $this->id;

		if ( isset( $cache[ $args['widget_id'] ] ) ) {
			echo $cache[ $args['widget_id'] ];
			return;
		}

		ob_start();
		extract($args);

		$title = ( ! empty( $instance['title'] ) ) ? $instance['title'] : __( 'Recent Posts' );
		$title = apply_filters( 'widget_title', $title, $instance, $this->id_base );
		$number = ( ! empty( $instance['number'] ) ) ? absint( $instance['number'] ) : 10;
		if ( ! $number )
 			$number = 10;
		$show_date = isset( $instance['show_date'] ) ? $instance['show_date'] : false;

		$r = new WP_Query( apply_filters( 'widget_posts_args', array( 'posts_per_page' => $number, 'no_found_rows' => true, 'post_status' => 'publish', 'ignore_sticky_posts' => true ) ) );
		if ($r->have_posts()) :
?>
		<div itemscope itemtype="http://schema.org/ItemList">
		<?php echo $before_widget; ?>
		<?php if ( $title ) echo $before_title . '<span itemprop="name">'. $title .'</span>'. $after_title; ?>
		<ul class="list-group">
		<?php while ( $r->have_posts() ) : $r->the_post(); ?>	
			<a href="<?php the_permalink(); ?>" class="list-group-item" itemprop="url">
			  <span class="list-group-item-heading h5">
			  <?php if ( $show_date ) : ?><span class="badge pull-right ml5" title="<?php echo get_the_date(); ?>" itemprop="datePublished" datetime="<?php echo get_the_date('c'); ?>"><?php echo get_the_date('m-d'); ?></span><?php endif; ?>
			  <span itemprop="itemListElement"><?php get_the_title() ? the_title() : the_ID(); ?></span>
			  <?php echo recommend(); ?>
			  </span>
			</a>
		<?php endwhile; ?>
		</ul>
		<?php echo $after_widget; ?>
		</div>
<?php
		// Reset the global $the_post as this query will have stomped on it
		wp_reset_postdata();

		endif;

		$cache[$args['widget_id']] = ob_get_flush();
		wp_cache_set('recent_posts_widget', $cache, 'widget');
	}

	function update( $new_instance, $old_instance ) {
		$instance = $old_instance;
		$instance['title'] = strip_tags($new_instance['title']);
		$instance['number'] = (int) $new_instance['number'];
		$instance['show_date'] = isset( $new_instance['show_date'] ) ? (bool) $new_instance['show_date'] : false;
		$this->flush_widget_cache();

		$alloptions = wp_cache_get( 'alloptions', 'options' );
		if ( isset($alloptions['recent_entries_widget']) )
			delete_option('recent_entries_widget');

		return $instance;
	}

	function flush_widget_cache() {
		wp_cache_delete('recent_posts_widget', 'widget');
	}

	function form( $instance ) {
		$title     = isset( $instance['title'] ) ? esc_attr( $instance['title'] ) : '';
		$number    = isset( $instance['number'] ) ? absint( $instance['number'] ) : 5;
		$show_date = isset( $instance['show_date'] ) ? (bool) $instance['show_date'] : false;
?>
		<p><label for="<?php echo $this->get_field_id( 'title' ); ?>"><?php _e( 'Title:' ); ?></label>
		<input class="widefat" id="<?php echo $this->get_field_id( 'title' ); ?>" name="<?php echo $this->get_field_name( 'title' ); ?>" type="text" value="<?php echo $title; ?>" /></p>

		<p><label for="<?php echo $this->get_field_id( 'number' ); ?>"><?php _e( 'Number of posts to show:' ); ?></label>
		<input id="<?php echo $this->get_field_id( 'number' ); ?>" name="<?php echo $this->get_field_name( 'number' ); ?>" type="text" value="<?php echo $number; ?>" size="3" /></p>

		<p><input class="checkbox" type="checkbox" <?php checked( $show_date ); ?> id="<?php echo $this->get_field_id( 'show_date' ); ?>" name="<?php echo $this->get_field_name( 'show_date' ); ?>" />
		<label for="<?php echo $this->get_field_id( 'show_date' ); ?>"><?php _e( 'Display post date?' ); ?></label></p>
<?php
	}
}
//新的近期评论
class recentCommentsWidget extends WP_Widget {

	function __construct() {
		$widget_ops = array('classname' => 'recent_comments_widget', 'description' => __( '近期的数条评论' ) );
		parent::__construct('recent-comments', __('Recent Comments'), $widget_ops);
		$this->alt_option_name = 'recent_comments_widget';

		if ( is_active_widget(false, false, $this->id_base) )
			add_action( 'wp_head', array($this, 'recent_comments_style') );

		add_action( 'comment_post', array($this, 'flush_widget_cache') );
		add_action( 'edit_comment', array($this, 'flush_widget_cache') );
		add_action( 'transition_comment_status', array($this, 'flush_widget_cache') );
	}

	function recent_comments_style() {
		if ( ! current_theme_supports( 'widgets' ) // Temp hack #14876
			|| ! apply_filters( 'show_recent_comments_widget_style', true, $this->id_base ) )
			return;
	}

	function flush_widget_cache() {
		wp_cache_delete('recent_comments_widget', 'widget');
	}

	function widget( $args, $instance ) {
		global $comments, $comment;

		$cache = wp_cache_get('recent_comments_widget', 'widget');

		if ( ! is_array( $cache ) )
			$cache = array();

		if ( ! isset( $args['widget_id'] ) )
			$args['widget_id'] = $this->id;

		if ( isset( $cache[ $args['widget_id'] ] ) ) {
			echo $cache[ $args['widget_id'] ];
			return;
		}

 		extract($args, EXTR_SKIP);
 		$output = '';

		$title = ( ! empty( $instance['title'] ) ) ? $instance['title'] : __( 'Recent Comments' );
		$title = apply_filters( 'widget_title', $title, $instance, $this->id_base );
		$number = ( ! empty( $instance['number'] ) ) ? absint( $instance['number'] ) : 5;
		if ( ! $number )
 			$number = 5;

		$comments = get_comments( apply_filters( 'widget_comments_args', array( 'number' => $number, 'status' => 'approve', 'post_status' => 'publish' ) ) );
		$output .= $before_widget;
		if ( $title )
			$output .= $before_title . $title . $after_title;

		$output .= '<ul id="recentcomments" class="list-group">';
		if ( $comments ) {
			// Prime cache for associated posts. (Prime post term cache if we need it for permalinks.)
			$post_ids = array_unique( wp_list_pluck( $comments, 'comment_post_ID' ) );
			_prime_post_caches( $post_ids, strpos( get_option( 'permalink_structure' ), '%category%' ), false );

			foreach ( (array) $comments as $comment) {
				$output .=  '<li class="list-group-item">' . /* translators: comments widget: 1: comment author, 2: post link */ sprintf(_x('%1$s on %2$s', 'widgets'), get_comment_author_link(), '<a href="' . esc_url( get_comment_link($comment->comment_ID) ) . '">' . get_the_title($comment->comment_post_ID) . '</a>') . '</li>';
			}
 		}
		$output .= '</ul>';
		$output .= $after_widget;

		echo $output;
		$cache[$args['widget_id']] = $output;
		wp_cache_set('recent_comments_widget', $cache, 'widget');
	}

	function update( $new_instance, $old_instance ) {
		$instance = $old_instance;
		$instance['title'] = strip_tags($new_instance['title']);
		$instance['number'] = absint( $new_instance['number'] );
		$this->flush_widget_cache();

		$alloptions = wp_cache_get( 'alloptions', 'options' );
		if ( isset($alloptions['recent_comments_widget']) )
			delete_option('recent_comments_widget');

		return $instance;
	}

	function form( $instance ) {
		$title  = isset( $instance['title'] ) ? esc_attr( $instance['title'] ) : '';
		$number = isset( $instance['number'] ) ? absint( $instance['number'] ) : 5;
?>
		<p><label for="<?php echo $this->get_field_id( 'title' ); ?>"><?php _e( 'Title:' ); ?></label>
		<input class="widefat" id="<?php echo $this->get_field_id( 'title' ); ?>" name="<?php echo $this->get_field_name( 'title' ); ?>" type="text" value="<?php echo $title; ?>" /></p>

		<p><label for="<?php echo $this->get_field_id( 'number' ); ?>"><?php _e( 'Number of comments to show:' ); ?></label>
		<input id="<?php echo $this->get_field_id( 'number' ); ?>" name="<?php echo $this->get_field_name( 'number' ); ?>" type="text" value="<?php echo $number; ?>" size="3" /></p>
<?php
	}
}
//新的站点功能
class metaWidget extends WP_Widget {

	function __construct() {
		$widget_ops = array('classname' => 'widget_meta', 'description' => __( "登录和登出、管理、feed和WordPress的链接") );
		parent::__construct('meta', __('Meta'), $widget_ops);
	}

	function widget( $args, $instance ) {
		extract($args);
		$title = apply_filters('widget_title', empty($instance['title']) ? __('Meta') : $instance['title'], $instance, $this->id_base);

		echo $before_widget;
		if ( $title )
			echo $before_title . $title . $after_title;
?>
			<ul class="list-group">
			<?php wp_register('<li class="list-group-item">', ''); ?>
			<li class="list-group-item"><?php wp_loginout(); ?></li>
			<li class="list-group-item"><a href="<?php bloginfo('rss2_url'); ?>" title="<?php echo esc_attr(__('Syndicate this site using RSS 2.0')); ?>"><?php _e('Entries <abbr title="Really Simple Syndication">RSS</abbr>'); ?></a></li>
			<li class="list-group-item"><a href="<?php bloginfo('comments_rss2_url'); ?>" title="<?php echo esc_attr(__('The latest comments to all posts in RSS')); ?>"><?php _e('Comments <abbr title="Really Simple Syndication">RSS</abbr>'); ?></a></li>
			<?php echo apply_filters( 'widget_meta_poweredby', sprintf( '<li class="list-group-item"><a href="%s" title="%s">%s</a></li>',
				esc_url( __( 'http://wordpress.org/' ) ),
				esc_attr__( 'Powered by WordPress, state-of-the-art semantic personal publishing platform.' ),
				_x( 'WordPress.org', 'meta widget link text' )
			) ); ?>
			<li class="list-group-item"><a href="http://www.dmeng.net/" title="WordPress主题来自多梦网络" target="_blank">多梦网络主题驱动</a></li>
			<?php wp_meta(); ?>
			</ul>
<?php
		echo $after_widget;
	}

	function update( $new_instance, $old_instance ) {
		$instance = $old_instance;
		$instance['title'] = strip_tags($new_instance['title']);

		return $instance;
	}

	function form( $instance ) {
		$instance = wp_parse_args( (array) $instance, array( 'title' => '' ) );
		$title = strip_tags($instance['title']);
?>
			<p><label for="<?php echo $this->get_field_id('title'); ?>"><?php _e('Title:'); ?></label> <input class="widefat" id="<?php echo $this->get_field_id('title'); ?>" name="<?php echo $this->get_field_name('title'); ?>" type="text" value="<?php echo esc_attr($title); ?>" /></p>
<?php
	}
}
//新的标签云
class tagCloudWidget extends WP_Widget {

	function __construct() {
		$widget_ops = array( 'description' => __( "用云的形式展现您常用的标签") );
		parent::__construct('tag_cloud', __('Tag Cloud'), $widget_ops);
	}

	function widget( $args, $instance ) {
		extract($args);
		$current_taxonomy = $this->_get_current_taxonomy($instance);
		if ( !empty($instance['title']) ) {
			$title = $instance['title'];
		} else {
			if ( 'post_tag' == $current_taxonomy ) {
				$title = __('Tags');
			} else {
				$tax = get_taxonomy($current_taxonomy);
				$title = $tax->labels->name;
			}
		}
		$title = apply_filters('widget_title', $title, $instance, $this->id_base);

		echo $before_widget;
		if ( $title )
			echo $before_title . $title . $after_title;
		echo '<div class="panel-body">';
		wp_tag_cloud( apply_filters('widget_tag_cloud_args', array('taxonomy' => $current_taxonomy) ) );
		echo "</div>\n";
		echo $after_widget;
	}

	function update( $new_instance, $old_instance ) {
		$instance['title'] = strip_tags(stripslashes($new_instance['title']));
		$instance['taxonomy'] = stripslashes($new_instance['taxonomy']);
		return $instance;
	}

	function form( $instance ) {
		$current_taxonomy = $this->_get_current_taxonomy($instance);
?>
	<p><label for="<?php echo $this->get_field_id('title'); ?>"><?php _e('Title:') ?></label>
	<input type="text" class="widefat" id="<?php echo $this->get_field_id('title'); ?>" name="<?php echo $this->get_field_name('title'); ?>" value="<?php if (isset ( $instance['title'])) {echo esc_attr( $instance['title'] );} ?>" /></p>
	<p><label for="<?php echo $this->get_field_id('taxonomy'); ?>"><?php _e('Taxonomy:') ?></label>
	<select class="widefat" id="<?php echo $this->get_field_id('taxonomy'); ?>" name="<?php echo $this->get_field_name('taxonomy'); ?>">
	<?php foreach ( get_taxonomies() as $taxonomy ) :
				$tax = get_taxonomy($taxonomy);
				if ( !$tax->show_tagcloud || empty($tax->labels->name) )
					continue;
	?>
		<option value="<?php echo esc_attr($taxonomy) ?>" <?php selected($taxonomy, $current_taxonomy) ?>><?php echo $tax->labels->name; ?></option>
	<?php endforeach; ?>
	</select></p><?php
	}

	function _get_current_taxonomy($instance) {
		if ( !empty($instance['taxonomy']) && taxonomy_exists($instance['taxonomy']) )
			return $instance['taxonomy'];

		return 'post_tag';
	}
}
//新的文本框
class textWidget extends WP_Widget {

	function __construct() {
		$widget_ops = array('classname' => 'text_widget', 'description' => __('任意文本或HTML'));
		$control_ops = array('width' => 400, 'height' => 350);
		parent::__construct('text', __('Text'), $widget_ops, $control_ops);
	}

	function widget( $args, $instance ) {
		extract($args);
		$title = apply_filters( 'widget_title', empty( $instance['title'] ) ? '' : $instance['title'], $instance, $this->id_base );
		$text = apply_filters( 'text_widget', empty( $instance['text'] ) ? '' : $instance['text'], $instance );
		echo $before_widget;
		if ( !empty( $title ) ) { echo $before_title . $title . $after_title; } ?>
			<div class="panel-body"><?php echo !empty( $instance['filter'] ) ? wpautop( $text ) : $text; ?></div>
		<?php
		echo $after_widget;
	}

	function update( $new_instance, $old_instance ) {
		$instance = $old_instance;
		$instance['title'] = strip_tags($new_instance['title']);
		if ( current_user_can('unfiltered_html') )
			$instance['text'] =  $new_instance['text'];
		else
			$instance['text'] = stripslashes( wp_filter_post_kses( addslashes($new_instance['text']) ) ); // wp_filter_post_kses() expects slashed
		$instance['filter'] = isset($new_instance['filter']);
		return $instance;
	}

	function form( $instance ) {
		$instance = wp_parse_args( (array) $instance, array( 'title' => '', 'text' => '' ) );
		$title = strip_tags($instance['title']);
		$text = esc_textarea($instance['text']);
?>
		<p><label for="<?php echo $this->get_field_id('title'); ?>"><?php _e('Title:'); ?></label>
		<input class="widefat" id="<?php echo $this->get_field_id('title'); ?>" name="<?php echo $this->get_field_name('title'); ?>" type="text" value="<?php echo esc_attr($title); ?>" /></p>

		<textarea class="widefat" rows="16" cols="20" id="<?php echo $this->get_field_id('text'); ?>" name="<?php echo $this->get_field_name('text'); ?>"><?php echo $text; ?></textarea>

		<p><input id="<?php echo $this->get_field_id('filter'); ?>" name="<?php echo $this->get_field_name('filter'); ?>" type="checkbox" <?php checked(isset($instance['filter']) ? $instance['filter'] : 0); ?> />&nbsp;<label for="<?php echo $this->get_field_id('filter'); ?>"><?php _e('Automatically add paragraphs'); ?></label></p>
<?php
	}
}
//新的文章归档
class archivesWidget extends WP_Widget {

	function __construct() {
		$widget_ops = array('classname' => 'archive_widget', 'description' => __( '您站点文章的月度存档') );
		parent::__construct('archives', __('Archives'), $widget_ops);
	}

	function widget( $args, $instance ) {
		extract($args);
		$c = ! empty( $instance['count'] ) ? '1' : '0';
		$d = ! empty( $instance['dropdown'] ) ? '1' : '0';
		$title = apply_filters('widget_title', empty($instance['title']) ? __('Archives') : $instance['title'], $instance, $this->id_base);

		echo $before_widget;
		if ( $title )
			echo $before_title . $title . $after_title;
			echo '<div>';
		if ( $d ) {
?>
		<div class="panel-body">
		<select name="archive-dropdown" onchange='document.location.href=this.options[this.selectedIndex].value;'> <option value=""><?php echo esc_attr(__('Select Month')); ?></option> <?php wp_get_archives(apply_filters('widget_archives_dropdown_args', array('type' => 'monthly', 'format' => 'option', 'show_post_count' => $c))); ?> </select>
		</div>
<?php
		} else {
?>
		
		<ul>
		<?php wp_get_archives(apply_filters('widget_archives_args', array('type' => 'monthly', 'show_post_count' => $c))); ?>
		</ul>
		
<?php
		}
		echo '</div>';
		echo $after_widget;
	}

	function update( $new_instance, $old_instance ) {
		$instance = $old_instance;
		$new_instance = wp_parse_args( (array) $new_instance, array( 'title' => '', 'count' => 0, 'dropdown' => '') );
		$instance['title'] = strip_tags($new_instance['title']);
		$instance['count'] = $new_instance['count'] ? 1 : 0;
		$instance['dropdown'] = $new_instance['dropdown'] ? 1 : 0;

		return $instance;
	}

	function form( $instance ) {
		$instance = wp_parse_args( (array) $instance, array( 'title' => '', 'count' => 0, 'dropdown' => '') );
		$title = strip_tags($instance['title']);
		$count = $instance['count'] ? 'checked="checked"' : '';
		$dropdown = $instance['dropdown'] ? 'checked="checked"' : '';
?>
		<p><label for="<?php echo $this->get_field_id('title'); ?>"><?php _e('Title:'); ?></label> <input class="widefat" id="<?php echo $this->get_field_id('title'); ?>" name="<?php echo $this->get_field_name('title'); ?>" type="text" value="<?php echo esc_attr($title); ?>" /></p>
		<p>
			<input class="checkbox" type="checkbox" <?php echo $dropdown; ?> id="<?php echo $this->get_field_id('dropdown'); ?>" name="<?php echo $this->get_field_name('dropdown'); ?>" /> <label for="<?php echo $this->get_field_id('dropdown'); ?>"><?php _e('Display as dropdown'); ?></label>
			<br/>
			<input class="checkbox" type="checkbox" <?php echo $count; ?> id="<?php echo $this->get_field_id('count'); ?>" name="<?php echo $this->get_field_name('count'); ?>" /> <label for="<?php echo $this->get_field_id('count'); ?>"><?php _e('Show post counts'); ?></label>
		</p>
<?php
	}
}
//新的RSS
class rssWidget extends WP_Widget {

	function __construct() {
		$widget_ops = array( 'description' => __('嵌入任意RSS或Atom feed中的条目') );
		$control_ops = array( 'width' => 400, 'height' => 200 );
		parent::__construct( 'rss', __('RSS'), $widget_ops, $control_ops );
	}

	function widget($args, $instance) {

		if ( isset($instance['error']) && $instance['error'] )
			return;

		extract($args, EXTR_SKIP);

		$url = ! empty( $instance['url'] ) ? $instance['url'] : '';
		while ( stristr($url, 'http') != $url )
			$url = substr($url, 1);

		if ( empty($url) )
			return;

		// self-url destruction sequence
		if ( in_array( untrailingslashit( $url ), array( site_url(), home_url() ) ) )
			return;

		$rss = fetch_feed($url);
		$title = $instance['title'];
		$desc = '';
		$link = '';

		if ( ! is_wp_error($rss) ) {
			$desc = esc_attr(strip_tags(@html_entity_decode($rss->get_description(), ENT_QUOTES, get_option('blog_charset'))));
			if ( empty($title) )
				$title = esc_html(strip_tags($rss->get_title()));
			$link = esc_url(strip_tags($rss->get_permalink()));
			while ( stristr($link, 'http') != $link )
				$link = substr($link, 1);
		}

		if ( empty($title) )
			$title = empty($desc) ? __('Unknown Feed') : $desc;

		$title = apply_filters('widget_title', $title, $instance, $this->id_base);
		$url = esc_url(strip_tags($url));
		$icon = includes_url('images/rss.png');
		if ( $title )
			$title = "<a class='rsswidget' href='$url' title='" . esc_attr__( 'Syndicate this content' ) ."'><img style='border:0' width='14' height='14' src='$icon' alt='RSS' /></a> <a class='rsswidget' href='$link' title='$desc'>$title</a>";

		echo $before_widget;
		if ( $title )
			echo $before_title . $title . $after_title;
		rssWidget_output( $rss, $instance );
		echo $after_widget;

		if ( ! is_wp_error($rss) )
			$rss->__destruct();
		unset($rss);
	}

	function update($new_instance, $old_instance) {
		$testurl = ( isset( $new_instance['url'] ) && ( !isset( $old_instance['url'] ) || ( $new_instance['url'] != $old_instance['url'] ) ) );
		return rssWidget_process( $new_instance, $testurl );
	}

	function form($instance) {

		if ( empty($instance) )
			$instance = array( 'title' => '', 'url' => '', 'items' => 10, 'error' => false, 'show_summary' => 0, 'show_author' => 0, 'show_date' => 0 );
		$instance['number'] = $this->number;

		rssWidget_form( $instance );
	}
}

function rssWidget_output( $rss, $args = array() ) {
	if ( is_string( $rss ) ) {
		$rss = fetch_feed($rss);
	} elseif ( is_array($rss) && isset($rss['url']) ) {
		$args = $rss;
		$rss = fetch_feed($rss['url']);
	} elseif ( !is_object($rss) ) {
		return;
	}

	if ( is_wp_error($rss) ) {
		if ( is_admin() || current_user_can('manage_options') )
			echo '<p>' . sprintf( __('<strong>RSS Error</strong>: %s'), $rss->get_error_message() ) . '</p>';
		return;
	}

	$default_args = array( 'show_author' => 0, 'show_date' => 0, 'show_summary' => 0 );
	$args = wp_parse_args( $args, $default_args );
	extract( $args, EXTR_SKIP );

	$items = (int) $items;
	if ( $items < 1 || 20 < $items )
		$items = 10;
	$show_summary  = (int) $show_summary;
	$show_author   = (int) $show_author;
	$show_date     = (int) $show_date;

	if ( !$rss->get_item_quantity() ) {
		echo '<ul><li>' . __( 'An error has occurred, which probably means the feed is down. Try again later.' ) . '</li></ul>';
		$rss->__destruct();
		unset($rss);
		return;
	}

	echo '<ul class="list-group">';
	foreach ( $rss->get_items(0, $items) as $item ) {
		$link = $item->get_link();
		while ( stristr($link, 'http') != $link )
			$link = substr($link, 1);
		$link = esc_url(strip_tags($link));
		$title = esc_attr(strip_tags($item->get_title()));
		if ( empty($title) )
			$title = __('Untitled');

		$desc = str_replace( array("\n", "\r"), ' ', esc_attr( strip_tags( @html_entity_decode( $item->get_description(), ENT_QUOTES, get_option('blog_charset') ) ) ) );
		$excerpt = wp_html_excerpt( $desc, 360 );

		// Append ellipsis. Change existing [...] to [&hellip;].
		if ( '[...]' == substr( $excerpt, -5 ) )
			$excerpt = substr( $excerpt, 0, -5 ) . '[&hellip;]';
		elseif ( '[&hellip;]' != substr( $excerpt, -10 ) && $desc != $excerpt )
			$excerpt .= ' [&hellip;]';

		$excerpt = esc_html( $excerpt );

		if ( $show_summary ) {
			$summary = "<p>$excerpt</p>";
		} else {
			$summary = '';
		}

		$date = '';
		if ( $show_date ) {
			$date = $item->get_date( 'U' );

			if ( $date ) {
				$date = ' <span class="text-info">' . date_i18n( get_option( 'date_format' ), $date ) . '</span>';
			}
		}

		$author = '';
		if ( $show_author ) {
			$author = $item->get_author();
			if ( is_object($author) ) {
				$author = $author->get_name();
				$author = ' <cite class="text-info">' . esc_html( strip_tags( $author ) ) . '</cite>';
			}
		}

		if ( $link == '' ) {
			echo "<a class='list-group-item' title='$title'><p class='list-group-item-heading h5'>$title</p>{$date}{$summary}{$author}</a>";
		} else {
			echo "<a class='list-group-item' href='$link' title='$title'><p class='list-group-item-heading h5'>$title</p>{$date}{$summary}{$author}</a>";
		}
	}
	echo '</ul>';
	$rss->__destruct();
	unset($rss);
}

function rssWidget_form( $args, $inputs = null ) {

	$default_inputs = array( 'url' => true, 'title' => true, 'items' => true, 'show_summary' => true, 'show_author' => true, 'show_date' => true );
	$inputs = wp_parse_args( $inputs, $default_inputs );
	extract( $args );
	extract( $inputs, EXTR_SKIP );

	$number = esc_attr( $number );
	$title  = esc_attr( $title );
	$url    = esc_url( $url );
	$items  = (int) $items;
	if ( $items < 1 || 20 < $items )
		$items  = 10;
	$show_summary   = (int) $show_summary;
	$show_author    = (int) $show_author;
	$show_date      = (int) $show_date;

	if ( !empty($error) )
		echo '<p class="widget-error"><strong>' . sprintf( __('RSS Error: %s'), $error) . '</strong></p>';

	if ( $inputs['url'] ) :
?>
	<p><label for="rss-url-<?php echo $number; ?>"><?php _e('Enter the RSS feed URL here:'); ?></label>
	<input class="widefat" id="rss-url-<?php echo $number; ?>" name="widget-rss[<?php echo $number; ?>][url]" type="text" value="<?php echo $url; ?>" /></p>
<?php endif; if ( $inputs['title'] ) : ?>
	<p><label for="rss-title-<?php echo $number; ?>"><?php _e('Give the feed a title (optional):'); ?></label>
	<input class="widefat" id="rss-title-<?php echo $number; ?>" name="widget-rss[<?php echo $number; ?>][title]" type="text" value="<?php echo $title; ?>" /></p>
<?php endif; if ( $inputs['items'] ) : ?>
	<p><label for="rss-items-<?php echo $number; ?>"><?php _e('How many items would you like to display?'); ?></label>
	<select id="rss-items-<?php echo $number; ?>" name="widget-rss[<?php echo $number; ?>][items]">
<?php
		for ( $i = 1; $i <= 20; ++$i )
			echo "<option value='$i' " . selected( $items, $i, false ) . ">$i</option>";
?>
	</select></p>
<?php endif; if ( $inputs['show_summary'] ) : ?>
	<p><input id="rss-show-summary-<?php echo $number; ?>" name="widget-rss[<?php echo $number; ?>][show_summary]" type="checkbox" value="1" <?php if ( $show_summary ) echo 'checked="checked"'; ?>/>
	<label for="rss-show-summary-<?php echo $number; ?>"><?php _e('Display item content?'); ?></label></p>
<?php endif; if ( $inputs['show_author'] ) : ?>
	<p><input id="rss-show-author-<?php echo $number; ?>" name="widget-rss[<?php echo $number; ?>][show_author]" type="checkbox" value="1" <?php if ( $show_author ) echo 'checked="checked"'; ?>/>
	<label for="rss-show-author-<?php echo $number; ?>"><?php _e('Display item author if available?'); ?></label></p>
<?php endif; if ( $inputs['show_date'] ) : ?>
	<p><input id="rss-show-date-<?php echo $number; ?>" name="widget-rss[<?php echo $number; ?>][show_date]" type="checkbox" value="1" <?php if ( $show_date ) echo 'checked="checked"'; ?>/>
	<label for="rss-show-date-<?php echo $number; ?>"><?php _e('Display item date?'); ?></label></p>
<?php
	endif;
	foreach ( array_keys($default_inputs) as $input ) :
		if ( 'hidden' === $inputs[$input] ) :
			$id = str_replace( '_', '-', $input );
?>
	<input type="hidden" id="rss-<?php echo $id; ?>-<?php echo $number; ?>" name="widget-rss[<?php echo $number; ?>][<?php echo $input; ?>]" value="<?php echo $$input; ?>" />
<?php
		endif;
	endforeach;
}

function rssWidget_process( $widget_rss, $check_feed = true ) {
	$items = (int) $widget_rss['items'];
	if ( $items < 1 || 20 < $items )
		$items = 10;
	$url           = esc_url_raw( strip_tags( $widget_rss['url'] ) );
	$title         = isset( $widget_rss['title'] ) ? trim( strip_tags( $widget_rss['title'] ) ) : '';
	$show_summary  = isset( $widget_rss['show_summary'] ) ? (int) $widget_rss['show_summary'] : 0;
	$show_author   = isset( $widget_rss['show_author'] ) ? (int) $widget_rss['show_author'] :0;
	$show_date     = isset( $widget_rss['show_date'] ) ? (int) $widget_rss['show_date'] : 0;

	if ( $check_feed ) {
		$rss = fetch_feed($url);
		$error = false;
		$link = '';
		if ( is_wp_error($rss) ) {
			$error = $rss->get_error_message();
		} else {
			$link = esc_url(strip_tags($rss->get_permalink()));
			while ( stristr($link, 'http') != $link )
				$link = substr($link, 1);

			$rss->__destruct();
			unset($rss);
		}
	}

	return compact( 'title', 'url', 'link', 'items', 'error', 'show_summary', 'show_author', 'show_date' );
}
//新的自定义菜单
 class navMenuWidget extends WP_Widget {

	function __construct() {
		$widget_ops = array( 'description' => __('这个小工具可使自定义菜单显示在边栏中。') );
		parent::__construct( 'nav_menu', __('Custom Menu'), $widget_ops );
	}

	function widget($args, $instance) {
		// Get menu
		$nav_menu = ! empty( $instance['nav_menu'] ) ? wp_get_nav_menu_object( $instance['nav_menu'] ) : false;

		if ( !$nav_menu )
			return;

		$instance['title'] = apply_filters( 'widget_title', empty( $instance['title'] ) ? '' : $instance['title'], $instance, $this->id_base );

		echo $args['before_widget'];

		if ( !empty($instance['title']) )
			echo $args['before_title'] . $instance['title'] . $args['after_title'];

		wp_nav_menu( array( 'fallback_cb' => '', 'menu' => $nav_menu, 'container' => 'div', 'container_class' => 'panel-body' ) );

		echo $args['after_widget'];
	}

	function update( $new_instance, $old_instance ) {
		$instance['title'] = strip_tags( stripslashes($new_instance['title']) );
		$instance['nav_menu'] = (int) $new_instance['nav_menu'];
		return $instance;
	}

	function form( $instance ) {
		$title = isset( $instance['title'] ) ? $instance['title'] : '';
		$nav_menu = isset( $instance['nav_menu'] ) ? $instance['nav_menu'] : '';

		// Get menus
		$menus = wp_get_nav_menus( array( 'orderby' => 'name' ) );

		// If no menus exists, direct the user to go and create some.
		if ( !$menus ) {
			echo '<p>'. sprintf( __('No menus have been created yet. <a href="%s">Create some</a>.'), admin_url('nav-menus.php') ) .'</p>';
			return;
		}
		?>
		<p>
			<label for="<?php echo $this->get_field_id('title'); ?>"><?php _e('Title:') ?></label>
			<input type="text" class="widefat" id="<?php echo $this->get_field_id('title'); ?>" name="<?php echo $this->get_field_name('title'); ?>" value="<?php echo $title; ?>" />
		</p>
		<p>
			<label for="<?php echo $this->get_field_id('nav_menu'); ?>"><?php _e('Select Menu:'); ?></label>
			<select id="<?php echo $this->get_field_id('nav_menu'); ?>" name="<?php echo $this->get_field_name('nav_menu'); ?>">
		<?php
			foreach ( $menus as $menu ) {
				echo '<option value="' . $menu->term_id . '"'
					. selected( $nav_menu, $menu->term_id, false )
					. '>'. $menu->name . '</option>';
			}
		?>
			</select>
		</p>
		<?php
	}
}
//新的页面
class pagesWidget extends WP_Widget {

	function __construct() {
		$widget_ops = array('classname' => 'pages_widget', 'description' => __( '您站点的WordPress页面') );
		parent::__construct('pages', __('Pages'), $widget_ops);
	}

	function widget( $args, $instance ) {
		extract( $args );

		$title = apply_filters('widget_title', empty( $instance['title'] ) ? __( 'Pages' ) : $instance['title'], $instance, $this->id_base);
		$sortby = empty( $instance['sortby'] ) ? 'menu_order' : $instance['sortby'];
		$exclude = empty( $instance['exclude'] ) ? '' : $instance['exclude'];

		if ( $sortby == 'menu_order' )
			$sortby = 'menu_order, post_title';

		$out = wp_list_pages( apply_filters('widget_pages_args', array('title_li' => '', 'echo' => 0, 'sort_column' => $sortby, 'exclude' => $exclude) ) );

		if ( !empty( $out ) ) {
			echo $before_widget;
			if ( $title)
				echo $before_title . $title . $after_title;
		?>
		<div class="panel-body">
			<?php echo $out; ?>
		</div>
		<?php
			echo $after_widget;
		}
	}

	function update( $new_instance, $old_instance ) {
		$instance = $old_instance;
		$instance['title'] = strip_tags($new_instance['title']);
		if ( in_array( $new_instance['sortby'], array( 'post_title', 'menu_order', 'ID' ) ) ) {
			$instance['sortby'] = $new_instance['sortby'];
		} else {
			$instance['sortby'] = 'menu_order';
		}

		$instance['exclude'] = strip_tags( $new_instance['exclude'] );

		return $instance;
	}

	function form( $instance ) {
		//Defaults
		$instance = wp_parse_args( (array) $instance, array( 'sortby' => 'post_title', 'title' => '', 'exclude' => '') );
		$title = esc_attr( $instance['title'] );
		$exclude = esc_attr( $instance['exclude'] );
	?>
		<p><label for="<?php echo $this->get_field_id('title'); ?>"><?php _e('Title:'); ?></label> <input class="widefat" id="<?php echo $this->get_field_id('title'); ?>" name="<?php echo $this->get_field_name('title'); ?>" type="text" value="<?php echo $title; ?>" /></p>
		<p>
			<label for="<?php echo $this->get_field_id('sortby'); ?>"><?php _e( 'Sort by:' ); ?></label>
			<select name="<?php echo $this->get_field_name('sortby'); ?>" id="<?php echo $this->get_field_id('sortby'); ?>" class="widefat">
				<option value="post_title"<?php selected( $instance['sortby'], 'post_title' ); ?>><?php _e('Page title'); ?></option>
				<option value="menu_order"<?php selected( $instance['sortby'], 'menu_order' ); ?>><?php _e('Page order'); ?></option>
				<option value="ID"<?php selected( $instance['sortby'], 'ID' ); ?>><?php _e( 'Page ID' ); ?></option>
			</select>
		</p>
		<p>
			<label for="<?php echo $this->get_field_id('exclude'); ?>"><?php _e( 'Exclude:' ); ?></label> <input type="text" value="<?php echo $exclude; ?>" name="<?php echo $this->get_field_name('exclude'); ?>" id="<?php echo $this->get_field_id('exclude'); ?>" class="widefat" />
			<br />
			<small><?php _e( 'Page IDs, separated by commas.' ); ?></small>
		</p>
<?php
	}

}
//新的日历
class calendarWidget extends WP_Widget {

	function __construct() {
		$widget_ops = array('classname' => 'calendar_widget', 'description' => __( '您站点的文章日程表') );
		parent::__construct('calendar', __('Calendar'), $widget_ops);
	}

	function widget( $args, $instance ) {
		extract($args);
		$title = apply_filters('widget_title', empty($instance['title']) ? '' : $instance['title'], $instance, $this->id_base);
		echo $before_widget;
		if ( $title )
			echo $before_title . $title . $after_title;
		echo '<div class="panel-body"><div align="center">';
		get_calendar();
		echo '</div></div>';
		echo $after_widget;
	}

	function update( $new_instance, $old_instance ) {
		$instance = $old_instance;
		$instance['title'] = strip_tags($new_instance['title']);

		return $instance;
	}

	function form( $instance ) {
		$instance = wp_parse_args( (array) $instance, array( 'title' => '' ) );
		$title = strip_tags($instance['title']);
?>
		<p><label for="<?php echo $this->get_field_id('title'); ?>"><?php _e('Title:'); ?></label>
		<input class="widefat" id="<?php echo $this->get_field_id('title'); ?>" name="<?php echo $this->get_field_name('title'); ?>" type="text" value="<?php echo esc_attr($title); ?>" /></p>
<?php
	}
}
//随机文章
class randPostWidget extends WP_Widget {

	function __construct() {
		$widget_ops = array('classname' => 'widget_rand_entries', 'description' => __( "您站点的随机文章") );
		parent::__construct('rand-posts', __('随机文章（多梦主题）'), $widget_ops);
		$this->alt_option_name = 'widget_rand_entries';

		add_action( 'save_post', array($this, 'flush_widget_cache') );
		add_action( 'deleted_post', array($this, 'flush_widget_cache') );
		add_action( 'switch_theme', array($this, 'flush_widget_cache') );
	}

	function widget($args, $instance) {
		$cache = wp_cache_get('widget_rand_posts', 'widget');

		if ( !is_array($cache) )
			$cache = array();

		if ( ! isset( $args['widget_id'] ) )
			$args['widget_id'] = $this->id;

		if ( isset( $cache[ $args['widget_id'] ] ) ) {
			echo $cache[ $args['widget_id'] ];
			return;
		}

		ob_start();
		extract($args);

		$title = ( ! empty( $instance['title'] ) ) ? $instance['title'] : __( '随机文章' );
		$title = apply_filters( 'widget_title', $title, $instance, $this->id_base );
		$number = ( ! empty( $instance['number'] ) ) ? absint( $instance['number'] ) : 10;
		if ( ! $number )
 			$number = 10;
		$show_excerpt = isset( $instance['show_excerpt'] ) ? $instance['show_excerpt'] : false;

		$r = new WP_Query( apply_filters( 'widget_rand_posts_args', array( 'posts_per_page' => $number, 'no_found_rows' => true, 'post_status' => 'publish', 'orderby' => 'rand', 'ignore_sticky_posts' => true ) ) );
		if ($r->have_posts()) :
?>
		<div itemscope itemtype="http://schema.org/ItemList">
		<?php echo $before_widget; ?>
		<?php if ( $title ) echo $before_title . '<span itemprop="name">'. $title .'</span>'. $after_title; ?>
		<ul class="list-group">
		<?php while ( $r->have_posts() ) : $r->the_post(); ?>			
			<a href="<?php the_permalink(); ?>" class="list-group-item" itemprop="url">
			  <?php if ( $show_excerpt ) : ?>
			  <p class="list-group-item-heading h4"><span itemprop="itemListElement"><?php get_the_title() ? the_title() : the_ID(); ?></span> <?php echo recommend(); ?></p>
			  <div itemprop="description"><?php the_excerpt(); ?></div>
			  <?php else : ?>
			  <p class="list-group-item-heading h5"><span itemprop="itemListElement"><?php get_the_title() ? the_title() : the_ID(); ?></span> <?php echo recommend(); ?></p>
			  <?php endif; ?>
			</a>
		<?php endwhile; ?>
		</ul>
		<?php echo $after_widget; ?>
		</div>
<?php
		// Reset the global $the_post as this query will have stomped on it
		wp_reset_postdata();

		endif;

		$cache[$args['widget_id']] = ob_get_flush();
		wp_cache_set('widget_rand_posts', $cache, 'widget');
	}

	function update( $new_instance, $old_instance ) {
		$instance = $old_instance;
		$instance['title'] = strip_tags($new_instance['title']);
		$instance['number'] = (int) $new_instance['number'];
		$instance['show_excerpt'] = isset( $new_instance['show_excerpt'] ) ? (bool) $new_instance['show_excerpt'] : false;
		$this->flush_widget_cache();

		$alloptions = wp_cache_get( 'alloptions', 'options' );
		if ( isset($alloptions['widget_rand_entries']) )
			delete_option('widget_rand_entries');

		return $instance;
	}

	function flush_widget_cache() {
		wp_cache_delete('widget_rand_posts', 'widget');
	}

	function form( $instance ) {
		$title     = isset( $instance['title'] ) ? esc_attr( $instance['title'] ) : '';
		$number    = isset( $instance['number'] ) ? absint( $instance['number'] ) : 5;
		$show_excerpt = isset( $instance['show_excerpt'] ) ? (bool) $instance['show_excerpt'] : false;
?>
		<p><label for="<?php echo $this->get_field_id( 'title' ); ?>"><?php _e( 'Title:' ); ?></label>
		<input class="widefat" id="<?php echo $this->get_field_id( 'title' ); ?>" name="<?php echo $this->get_field_name( 'title' ); ?>" type="text" value="<?php echo $title; ?>" /></p>

		<p><label for="<?php echo $this->get_field_id( 'number' ); ?>"><?php _e( 'Number of posts to show:' ); ?></label>
		<input id="<?php echo $this->get_field_id( 'number' ); ?>" name="<?php echo $this->get_field_name( 'number' ); ?>" type="text" value="<?php echo $number; ?>" size="3" /></p>

		<p><input class="checkbox" type="checkbox" <?php checked( $show_excerpt ); ?> id="<?php echo $this->get_field_id( 'show_excerpt' ); ?>" name="<?php echo $this->get_field_name( 'show_excerpt' ); ?>" />
		<label for="<?php echo $this->get_field_id( 'show_excerpt' ); ?>"><?php _e( '显示文章摘要？' ); ?></label></p>
<?php
	}
}
//三列推荐文章
class showPostWidget extends WP_Widget {

	function __construct() {
		$widget_ops = array('classname' => 'widget_show_entries', 'description' => __( "显示您站点三个不同排序方式的文章") );
		parent::__construct('show-posts', __('三列推荐文章（多梦主题）'), $widget_ops);
		$this->alt_option_name = 'widget_show_entries';

		add_action( 'save_post', array($this, 'flush_widget_cache') );
		add_action( 'deleted_post', array($this, 'flush_widget_cache') );
		add_action( 'switch_theme', array($this, 'flush_widget_cache') );
	}

	function widget($args, $instance) {
		$cache = wp_cache_get('widget_show_posts', 'widget');

		if ( !is_array($cache) )
			$cache = array();

		if ( ! isset( $args['widget_id'] ) )
			$args['widget_id'] = $this->id;

		if ( isset( $cache[ $args['widget_id'] ] ) ) {
			echo $cache[ $args['widget_id'] ];
			return;
		}

		ob_start();
		extract($args);

		$number = ( ! empty( $instance['number'] ) ) ? absint( $instance['number'] ) : 10;
		if ( ! $number )
 			$number = 5;
?>
	<div class="panel-group mb20" id="accordion">
<?php
$the_query = new WP_Query( array( 'posts_per_page' => $number, 'meta_key' => 'post_views_count', 'orderby' => 'post_views_count', 'ignore_sticky_posts' => true ) );
if ( $the_query->have_posts() ) { ?>
        <div class="panel panel-danger" itemscope itemtype="http://schema.org/ItemList">
          <div class="panel-heading">
            <h3 class="panel-title">
              <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne" class="collapsed">
                <span class="glyphicon glyphicon-fire mr10"></span> <span itemprop="name">按浏览次数推荐的<?php echo $number;?>篇</span>
              </a>
            </h3>
          </div>
          <div id="collapseOne" class="panel-collapse collapse" style="height: 0px;">
<?php
	while ( $the_query->have_posts() ) {
		$the_query->the_post();
		echo '<a href="';
		the_permalink();
		echo '" class="list-group-item" itemprop="url">';
		echo '<span class="badge ml5" title="'.getPostViews(get_the_ID()).'次浏览">'.getPostViews(get_the_ID()).'</span>';
		echo '<span itemprop="itemListElement">';
		the_title();
		echo '</span></a>';
	}
?>
          </div>
        </div>
<?php
}
wp_reset_postdata();
?>
<?php
$the_query = new WP_Query( array( 'posts_per_page' => $number, 'orderby' => 'comment_count', 'ignore_sticky_posts' => true ) );
if ( $the_query->have_posts() ) { ?>
        <div class="panel panel-success" itemscope itemtype="http://schema.org/ItemList">
          <div class="panel-heading">
            <h3 class="panel-title">
              <a data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" class="collapsed">
                <span class="glyphicon glyphicon-volume-up mr10"></span> <span itemprop="name">最受网友争议<?php echo $number;?>篇文章</span>
              </a>
            </h3>
          </div>
          <div id="collapseTwo" class="panel-collapse collapse" style="height: 0px;">
<?php
	while ( $the_query->have_posts() ) {
		$the_query->the_post();
		echo '<a href="';
		the_permalink();
		echo '" class="list-group-item" itemprop="url">';
		echo '<span class="badge ml5">';
		comments_number( '0', '1', '%' );
		echo '</span>';
		echo '<span itemprop="itemListElement">';
		the_title();
		echo '</span></a>';
	}
?>
          </div>
        </div>
<?php
}
wp_reset_postdata();
?>

    
<?php
$the_query = new WP_Query( array( 'posts_per_page' => $number, 'orderby' => 'rand', 'ignore_sticky_posts' => true ) );
if ( $the_query->have_posts() ) { ?>
        <div class="panel panel-info" itemscope itemtype="http://schema.org/ItemList">
          <div class="panel-heading">
            <h3 class="panel-title">
              <a data-toggle="collapse" data-parent="#accordion" href="#collapseThree" class="">
                <span class="glyphicon glyphicon-thumbs-up mr10"></span> <span itemprop="name">随机推荐<?php echo $number;?>篇精选文章</span>
              </a>
            </h3>
          </div>
          <div id="collapseThree" class="panel-collapse in" style="height: auto;">
<?php
	while ( $the_query->have_posts() ) {
		$the_query->the_post();
		echo '<a href="';
		the_permalink();
		echo '" class="list-group-item" itemprop="url">';
		echo '<span class="badge ml5" title="'.getPostViews(get_the_ID()).'次浏览">'.getPostViews(get_the_ID()).'</span>';
		echo '<span itemprop="itemListElement">';
		the_title();
		echo '</span></a>';
	}
?>
          </div>
        </div>
<?php
}
wp_reset_postdata();
?>
	</div>
<?php
		$cache[$args['widget_id']] = ob_get_flush();
		wp_cache_set('widget_show_posts', $cache, 'widget');
	}

	function update( $new_instance, $old_instance ) {
		$instance = $old_instance;
		$instance['number'] = (int) $new_instance['number'];
		$this->flush_widget_cache();

		$alloptions = wp_cache_get( 'alloptions', 'options' );
		if ( isset($alloptions['widget_show_entries']) )
			delete_option('widget_show_entries');

		return $instance;
	}

	function flush_widget_cache() {
		wp_cache_delete('widget_show_posts', 'widget');
	}

	function form( $instance ) {
		$number    = isset( $instance['number'] ) ? absint( $instance['number'] ) : 5;
?>
		<p><label for="<?php echo $this->get_field_id( 'number' ); ?>"><?php _e( 'Number of posts to show:' ); ?></label>
		<input id="<?php echo $this->get_field_id( 'number' ); ?>" name="<?php echo $this->get_field_name( 'number' ); ?>" type="text" value="<?php echo $number; ?>" size="3" /></p>
<?php
	}
}
//广告文本框
class adTextWidget extends WP_Widget {

	function __construct() {
		$widget_ops = array('classname' => 'ad_adText_widget', 'description' => __('展示广告专用，当滚动距离超过“广告距离顶部距离+广告元素高度”时会自动固定在侧边栏，所以推荐放置在侧边栏最后一个，且每个页面只能放置一个。（手机访问不显示）'));
		$control_ops = array('width' => 400, 'height' => 350);
		parent::__construct('adText', __('广告文本框'), $widget_ops, $control_ops);
	}

	function widget( $args, $instance ) {
		extract($args);
		$title = apply_filters( 'widget_title', empty( $instance['title'] ) ? '' : $instance['title'], $instance, $this->id_base );
		$adText = apply_filters( 'ad_adText_widget', empty( $instance['adText'] ) ? '' : $instance['adText'], $instance );
		if ( !wp_is_mobile() ) {
		echo '<div id="ad" data-spy="affix" class="fade in"><a class="close" data-dismiss="alert" href="#" aria-hidden="true" style="position:absolute;color:#c5c5c5;" title="关闭"><span class="glyphicon glyphicon-ban-circle"></span></a>';
		echo $before_widget;
		if ( !empty( $title ) ) { echo $before_title . $title . $after_title; } ?>
			<div class="panel-body" style="padding:0;text-align:center;"><?php echo !empty( $instance['filter'] ) ? wpautop( $adText ) : $adText; ?></div>
		<?php
		echo $after_widget;
		echo '</div>';
		}?>
<script>var ad=$('#ad'),st;var sd=$('.sidebar'),st;ad.attr('data-offset-top',ad.offset().top+ad.innerWidth());var wd=sd.innerWidth()-30;ad.attr({style:'width:'+wd+'px'});</script>
<?php global $dmeng_option;if ( $dmeng_option['navbar']['_navbar_position']=="navbar-fixed-top") : ?>
<script>document.writeln('<style>.affix{padding-top:'+navh+'px;}@media(max-width:992px){.affix{padding-top:0;}}</style>');</script>
<?php endif; ?>
		<?php
	}

	function update( $new_instance, $old_instance ) {
		$instance = $old_instance;
		$instance['title'] = strip_tags($new_instance['title']);
		if ( current_user_can('unfiltered_html') )
			$instance['adText'] =  $new_instance['adText'];
		else
			$instance['adText'] = stripslashes( wp_filter_post_kses( addslashes($new_instance['adText']) ) ); // wp_filter_post_kses() expects slashed
		$instance['filter'] = isset($new_instance['filter']);
		return $instance;
	}

	function form( $instance ) {
		$instance = wp_parse_args( (array) $instance, array( 'title' => '', 'adText' => '' ) );
		$title = strip_tags($instance['title']);
		$adText = esc_textarea($instance['adText']);
?>
		<p><label for="<?php echo $this->get_field_id('title'); ?>"><?php _e('Title:'); ?></label>
		<input class="widefat" id="<?php echo $this->get_field_id('title'); ?>" name="<?php echo $this->get_field_name('title'); ?>" type="adText" value="<?php echo esc_attr($title); ?>" /></p>

		<textarea class="widefat" rows="16" cols="20" id="<?php echo $this->get_field_id('adText'); ?>" name="<?php echo $this->get_field_name('adText'); ?>"><?php echo $adText; ?></textarea>

		<p><input id="<?php echo $this->get_field_id('filter'); ?>" name="<?php echo $this->get_field_name('filter'); ?>" type="checkbox" <?php checked(isset($instance['filter']) ? $instance['filter'] : 0); ?> />&nbsp;<label for="<?php echo $this->get_field_id('filter'); ?>"><?php _e('Automatically add paragraphs'); ?></label></p>
<?php
	}
}
//注册新的小工具
function myplugin_register_widgets() {
	register_widget( 'showPostWidget' );//三列推荐文章
	register_widget( 'randPostWidget' );//随机文章
	register_widget( 'recentPostWidget' );//最近文章
	register_widget( 'recentCommentsWidget' );//最近评论
	register_widget( 'metaWidget' );//站点功能
	register_widget( 'tagCloudWidget' );//标签云
	register_widget( 'textWidget' );//文本框
	register_widget( 'archivesWidget' );//文章归档
	register_widget( 'rssWidget' );//RSS
	register_widget( 'navMenuWidget' );//自定义菜单
	register_widget( 'pagesWidget' );//页面
	register_widget( 'calendarWidget' );//日历
	register_widget( 'adTextWidget' );//广告文本框
}
add_action( 'widgets_init', 'myplugin_register_widgets' );
?>