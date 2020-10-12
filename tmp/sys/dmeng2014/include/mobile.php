<?php
function mobile_redirect()
{
    if( is_home() )
    {
        include( get_template_directory() . '/include/mobile/index.php' );
        exit();
    }elseif( is_single() )
    {
        include( get_template_directory() . '/include/mobile/single.php' );
        exit();
    }elseif( is_category() )
    {
        include( get_template_directory() . '/include/mobile/category.php' );
        exit();
    }elseif( is_search() )
    {
        include( get_template_directory() . '/include/mobile/search.php' );
        exit();
    }elseif( is_tag() )
    {
        include( get_template_directory() . '/include/mobile/tag.php' );
        exit();
    }elseif( is_page() )
    {
        include( get_template_directory() . '/include/mobile/page.php' );
        exit();
    }elseif( is_archive() )
    {
        include( get_template_directory() . '/include/mobile/archive.php' );
        exit();
    }elseif( is_attachment() )
    {
        include( get_template_directory() . '/include/mobile/attachment.php' );
        exit();
    }elseif( is_404() )
    {
        include( get_template_directory() . '/include/mobile/404.php' );
        exit();
    }
}
add_action( 'template_redirect', 'mobile_redirect' );
?>