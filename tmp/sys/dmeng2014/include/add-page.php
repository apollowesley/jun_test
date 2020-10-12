<?php
function add_page_contribute($title,$slug,$page_template=''){   
    $allPages = get_pages();
    $exists = false;   
    foreach( $allPages as $page ){   
        if( strtolower( $page->post_name ) == strtolower( $slug ) ){   
            $exists = true;   
        }   
    }   
    if( $exists == false ) {   
        $new_page_id = wp_insert_post(   
            array(   
                'post_title' => $title,   
                'post_type'     => 'page',   
                'post_name'  => $slug,   
                'comment_status' => 'closed',   
                'ping_status' => 'closed',   
                'post_content' => '',   
                'post_status' => 'publish',   
                'post_author' => 1,   
                'menu_order' => 0   
            )   
        );     
        if($new_page_id && $page_template!=''){    
            update_post_meta($new_page_id, '_wp_page_template',  $page_template);   
        }   
    }   
}  
function dmeng_add_page() {   
    global $pagenow;    
    if ( 'themes.php' == $pagenow && isset( $_GET['activated'] ) ){   
        add_page_contribute('投稿或建议','contribute','contribute.php');
    }   
}   
add_action( 'load-themes.php', 'dmeng_add_page' );
?>