<?php
//主题版本号，请勿修改，否则会影响主题的正常运作
if ( is_admin() ) :
$dmeng = wp_get_theme();
$dmengVersion = $dmeng->get( 'Version' );
$version = json_decode(file_get_contents("http://cdn.dmeng.net/version/version.json"),true);
if ( $version["NO"] != $dmengVersion ){
	function version_update_alerts() {
		$version = json_decode(file_get_contents("http://cdn.dmeng.net/version/version.json"),true);
		echo '<div class="updated fade"><p>检测到多梦主题在'.$version["date"].'发布了新版本<span style="color:#d98500;"> [ '.$version["name"].' ] </span> <a href="'.$version["url"].'" target="_blank">点击查看更新</a></p></div>';
	}
	add_action( 'admin_notices', 'version_update_alerts' );
}
endif;
?>