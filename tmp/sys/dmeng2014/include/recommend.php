<?php 
function recommend_meta() {   
    global $post, $recommend_meta;   
    foreach($recommend_meta as $meta_box) {   
        //获取保存的是   
        $meta_box_value = get_post_meta($post->ID, $meta_box['name'], true);   
        if($meta_box_value != "")      
            $meta_box['std'] = $meta_box_value;//将默认值替换为以保存的值   
           
        echo'<input type="hidden" name="'.$meta_box['name'].'_noncename" id="'.$meta_box['name'].'_noncename" value="'.wp_create_nonce( plugin_basename(__FILE__) ).'" />';   
        //通过选择类型输出不同的html代码   
        switch ( $meta_box['type'] ){   
            case 'text':   
                echo'<div style="clear:both;"></div><p class="howto">'.$meta_box['title'].'</p>';   
                echo '<input type="text" name="'.$meta_box['name'].'" value="'.$meta_box['std'].'" /><br />';   
                break; 
            case 'radio':   
                echo'<div style="clear:both;"></div><p class="howto">'.$meta_box['title'].'</p>';   
                $counter = 1;   
                foreach( $meta_box['buttons'] as $radiobutton ) {   
                    $checked ="";   
                    if(isset($meta_box['std']) && $meta_box['std'] == $counter) {   
                        $checked = 'checked = "checked"';   
                    }   
                    echo '<p style="margin:5px 0 0 0;width:50%;float:left;"><input '.$checked.' type="radio" class="kcheck" value="'.$counter.'" name="'.$meta_box['name'].'"/> '.$radiobutton.'</p>';   
                    $counter++;   
                }   
                break;
               
        }             
    }      
}
function create_recommend_meta() {      
    global $theme_name;      
     
    if ( function_exists('add_meta_box') ) {      
        add_meta_box( 'new-meta-boxes', '推荐文章', 'recommend_meta', 'post', 'side', 'high' );      
    }      
}
function save_recommend_meta( $post_id ) {      
    global $post, $recommend_meta;      
     
    foreach($recommend_meta as $meta_box) {      
        if ( !wp_verify_nonce( $_POST[$meta_box['name'].'_noncename'], plugin_basename(__FILE__) ))  {      
            return $post_id;      
        }      
     
        if ( 'page' == $_POST['post_type'] ) {      
            if ( !current_user_can( 'edit_page', $post_id ))      
                return $post_id;      
        }       
        else {      
            if ( !current_user_can( 'edit_post', $post_id ))      
                return $post_id;      
        }      
     
        $data = $_POST[$meta_box['name']];      
     
        if(get_post_meta($post_id, $meta_box['name']) == "")      
            add_post_meta($post_id, $meta_box['name'], $data, true);      
        elseif($data != get_post_meta($post_id, $meta_box['name'], true))      
            update_post_meta($post_id, $meta_box['name'], $data);      
        elseif($data == "")      
            delete_post_meta($post_id, $meta_box['name'], get_post_meta($post_id, $meta_box['name'], true));      
    }      
}
add_action('admin_menu', 'create_recommend_meta');      
add_action('save_post', 'save_recommend_meta'); 
$recommend_meta =    
array( 
           
    "recommendDisplay" => array(   
        "name" => "_recommend_display",   
        "std" => 2,
		"title" => "是否推荐文章（推荐文章标题有特色标签）",   
        "buttons" => array(   
                '推荐',   
                '不',
                ),
        "type"=>"radio"),
		
    "recommendLabel" => array(   
        "name" => "_recommend_label",   
        "std" => 3,      
        "title" => "推荐标签|设置推荐时有效，颜色指的是背景",   
        "buttons" => array(   
                '头条（灰色）',   
                '实用（蓝色）',
				'推荐（绿色）',
				'原创（天蓝）',
				'干货（黄色）',
				'热门（红色）',
				'自定义标签文字',
                ),
        "type"=>"radio"),  
		
    "customLabel" => array(   
        "name" => "_recommend_custom_label",   
        "std" => "",      
        "title" => "自定义标签|选择自定义时有效，背景为绿色",   
        "type"=>"text"),  

); 
//封装成函数供前台调用
function recommend() {
$post_ID = get_the_ID();
$recommend_display = get_post_meta($post_ID, "_recommend_display", true);
$recommend_label = get_post_meta($post_ID, "_recommend_label", true);
$recommend_custom_label = get_post_meta($post_ID, "_recommend_custom_label", true);
	if ($recommend_display=="1") {
		switch ($recommend_label) {
		case 1: echo ' <span class="label label-default">头条</span> ';break;
		case 2: echo ' <span class="label label-primary">实用</span> ';break;
		case 3: echo ' <span class="label label-success">推荐</span> ';break;
		case 4: echo ' <span class="label label-info">原创</span> ';break;
		case 5: echo ' <span class="label label-warning">干货</span> ';break;
		case 6: echo ' <span class="label label-danger">热门</span> ';break;
		default: echo ' <span class="label label-success">'.$recommend_custom_label.'</span> ';
		}
	}
}
?>