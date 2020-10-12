<?php
if ( function_exists('register_sidebar') )
//注册小工具边栏
register_sidebar(array(
  'name' => __( '一号边栏' ),
  'id' => 'one-sidebar',
  'description' => __( '默认显示在文章、分类目录、标签页、默认页面模版、左边栏模版和三列页面模版的左侧，可按喜好在主题设置的侧边栏设置显示到其他页面。' ),
  'before_widget' => '<aside class="panel panel-default">',
  'after_widget' => '</aside>',
  'before_title' => '<div class="panel-heading"><h3 class="panel-title">',
  'after_title' => '</h3></div>'
));
register_sidebar(array(
  'name' => __( '二号边栏' ),
  'id' => 'two-sidebar',
  'description' => __( '默认显示在三列页面模版的右侧，可按喜好在主题设置的侧边栏设置显示到其他页面。' ),
  'before_widget' => '<aside class="panel panel-default">',
  'after_widget' => '</aside>',
  'before_title' => '<div class="panel-heading"><h3 class="panel-title">',
  'after_title' => '</h3></div>'
));
register_sidebar(array(
  'name' => __( '三号边栏' ),
  'id' => 'three-sidebar',
  'description' => __( '默认不调用这个边栏，可按喜好在主题设置的侧边栏设置显示到其他页面。' ),
  'before_widget' => '<aside class="panel panel-default">',
  'after_widget' => '</aside>',
  'before_title' => '<div class="panel-heading"><h3 class="panel-title">',
  'after_title' => '</h3></div>'
));
register_sidebar(array(
  'name' => __( '四号边栏' ),
  'id' => 'four-sidebar',
  'description' => __( '默认不调用这个边栏，可按喜好在主题设置的侧边栏设置显示到其他页面。' ),
  'before_widget' => '<aside class="panel panel-default">',
  'after_widget' => '</aside>',
  'before_title' => '<div class="panel-heading"><h3 class="panel-title">',
  'after_title' => '</h3></div>'
));
register_sidebar(array(
  'name' => __( '底部边栏' ),
  'id' => 'footer-sidebar',
  'description' => __( '显示在网站底部的边栏，一般是放置文字内容，如网站声明、版权说明或友情链接。' ),
  'before_widget' => '<aside>',
  'after_widget' => '</aside>',
  'before_title' => '<h3>',
  'after_title' => '</h3>'
));
?>