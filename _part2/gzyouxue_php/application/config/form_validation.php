<?php  if ( ! defined('BASEPATH')) exit('No direct script access allowed');

$config = array(
		'user_reg' => array(
				array(
						'field' => 'email',
						'label' => '登录邮箱',
						'rules' => 'tirm|required|valid_email|callback_email_not_exist'
				),
				array(
						'field' => 'password',
						'label' => '登录密码',
						'rules' => 'trim|required'
				),
				array(
						'field' => 'confirm_password',
						'label' => '确认密码',
						'rules' => 'trim|required|matches[password]'
				)
		),
		'user_login' => array(
				array(
						'field' => 'email',
						'label' => '登录邮箱',
						'rules' => 'tirm|required|valid_email|callback_valid_user'
				),
				array(
						'field' => 'password',
						'label' => '登录密码',
						'rules' => 'trim|required'
				)
		),
		'user_change_pwd' => array(
				array(
						'field' => 'old_password',
						'label' => '旧邮箱',
						'rules' => 'tirm|required|callback_valid_old_password'
				),
				array(
						'field' => 'password',
						'label' => '新密码',
						'rules' => 'trim|required'
				),
				array(
						'field' => 'confirm_password',
						'label' => '确认密码',
						'rules' => 'trim|required|matches[password]'
				)
		),
		'user_add_kid' => array(
				array(
						'field' => 'nickname',
						'label' => '昵称',
						'rules' => 'tirm|required'
				),
				array(
						'field' => 'radio_gender',
						'label' => '性别',
						'rules' => 'trim|required'
				),
				array(
						'field' => 'school',
						'label' => '学校',
						'rules' => 'trim|required'
				),
				array(
						'field' => 'select_grade',
						'label' => '年级',
						'rules' => 'trim|required'
				)
		),
		'user_add_stu' => array(
				array(
						'field' => 'school',
						'label' => '学校',
						'rules' => 'trim|required'
				),
				array(
						'field' => 'select_grade',
						'label' => '年级',
						'rules' => 'trim|required'
				)
		),
		'user_add_teacher' => array(
				array(
						'field' => 'radio_t_type',
						'label' => '教师类型',
						'rules' => 'tirm|required'
				),
				array(
						'field' => 'where_from',
						'label' => '来自于',
						'rules' => 'trim|required'
				),
				array(
						'field' => 'select_speciality',
						'label' => '专长',
						'rules' => 'trim|required'
				)
		),
		'user_save_normal' => array(
				array(
						'field' => 'nickname',
						'label' => '昵称',
						'rules' => 'tirm|required'
				),
				array(
						'field' => 'radio_gender',
						'label' => '性别',
						'rules' => 'trim|required'
				),
				array(
						'field' => 'my_email',
						'label' => '常用邮箱',
						'rules' => 'trim|required|valid_email'
				)
		),
		'user_save_parent' => array(
				array(
						'field' => 'nickname',
						'label' => '昵称',
						'rules' => 'tirm|required'
				),
				array(
						'field' => 'radio_gender',
						'label' => '性别',
						'rules' => 'trim|required'
				),
				array(
						'field' => 'my_email',
						'label' => '常用邮箱',
						'rules' => 'trim|required|valid_email'
				)
		),
		'user_save_student' => array(
				array(
						'field' => 'nickname',
						'label' => '昵称',
						'rules' => 'tirm|required'
				),
				array(
						'field' => 'radio_gender',
						'label' => '性别',
						'rules' => 'trim|required'
				),
				array(
						'field' => 'my_email',
						'label' => '常用邮箱',
						'rules' => 'trim|required|valid_email'
				),
				array(
						'field' => 'school',
						'label' => '学校',
						'rules' => 'trim|required'
				),
				array(
						'field' => 'select_grade',
						'label' => '年级',
						'rules' => 'trim|required'
				)
		),
		'user_save_teacher' => array(
				array(
						'field' => 'nickname',
						'label' => '昵称',
						'rules' => 'tirm|required'
				),
				array(
						'field' => 'radio_gender',
						'label' => '性别',
						'rules' => 'trim|required'
				),
				array(
						'field' => 'my_email',
						'label' => '常用邮箱',
						'rules' => 'trim|required|valid_email'
				),
				array(
						'field' => 'radio_t_type',
						'label' => '教师类型',
						'rules' => 'tirm|required'
				),
				array(
						'field' => 'where_from',
						'label' => '来自于',
						'rules' => 'trim|required'
				),
				array(
						'field' => 'select_speciality',
						'label' => '专长',
						'rules' => 'trim|required'
				)
		),
		'user_add_tutorship' => array(
				array(
						'field' => 'subject',
						'label' => '科目',
						'rules' => 'tirm|required'
				),
				array(
						'field' => 'hours',
						'label' => '课时',
						'rules' => 'trim|required|integer'
				),
				array(
						'field' => 'days',
						'label' => '总天数',
						'rules' => 'trim|required|integer'
				),
				array(
						'field' => 'start_time',
						'label' => '开始时间',
						'rules' => 'trim|required'
				)
		),
		'admin_login' => array(
				array(
						'field' => 'email',
						'label' => '登录邮箱',
						'rules' => 'tirm|required|valid_email|callback_valid_admin'
				),
				array(
						'field' => 'password',
						'label' => '登录密码',
						'rules' => 'trim|required'
				),
				array(
						'field' => 'auth_code',
						'label' => '验证码',
						'rules' => 'trim|required|callback_valid_authcode'
				)
		)
);