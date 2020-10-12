<?php
//添加编辑器预览样式
function dmeng_theme_add_editor_styles() {
    add_editor_style('/css/bootstrap.min.css');
	add_editor_style('style.css');
}
add_action( 'init', 'dmeng_theme_add_editor_styles' );
//添加quicktags按钮
function dmeng_theme_add_quicktags() {
	if (wp_script_is('quicktags')){
?> 
<script type="text/javascript"> 
QTags.addButton( 'alert-success', '绿色警告框', '<div class="alert alert-success">', '</div>');
QTags.addButton( 'alert-info', '蓝色警告框', '<div class="alert alert-info">', '</div>');
QTags.addButton( 'alert-warning', '黄色警告框', '<div class="alert alert-warning">', '</div>');
QTags.addButton( 'alert-danger', '红色警告框', '<div class="alert alert-danger">', '</div>');
</script>
<?php
	}
}
add_action('admin_print_footer_scripts', 'dmeng_theme_add_quicktags' ); 
//增强编辑器开始
function add_editor_buttons($buttons) {
$buttons[] = 'fontselect';
$buttons[] = 'fontsizeselect';
$buttons[] = 'backcolor';
$buttons[] = 'underline';
$buttons[] = 'hr';
$buttons[] = 'sub';
$buttons[] = 'sup';
$buttons[] = 'cut';
$buttons[] = 'copy';
$buttons[] = 'paste';
$buttons[] = 'cleanup';
$buttons[] = 'wp_page';
$buttons[] = 'newdocument';
return $buttons;
}
add_filter("mce_buttons_3", "add_editor_buttons");
//增强编辑器结束
?>