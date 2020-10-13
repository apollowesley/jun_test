<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Upload extends CI_Controller {
	
	public function index() {
		
	}
	
	public function product() {
		$admin_email = $this->session->userdata(ADMIN_EAMIL);
		if (isset($admin_email) && $admin_email != NULL) {
			$this->load->library("appcommon", "appcommon");
			$tempFile = $_FILES['file']['tmp_name'];
			$targetPath = $_SERVER['DOCUMENT_ROOT'] .'project/upload/enterprise/product';
			$targetFile = rtrim($targetPath,'/') . '/' .$this->appcommon->create_uuid()."-". $_FILES['file']['name'];
			
			// Validate the file type
			$fileTypes = array('jpg','jpeg','gif','png'); // File extensions
			$fileParts = pathinfo($_FILES['file']['name']);
			
			if (in_array($fileParts['extension'],$fileTypes)) {
				move_uploaded_file($tempFile,$targetFile);
				echo '1';
			} else {
				echo 'Invalid file type.';
			}
		} else {
			redirect(base_url()."admin/login");
		}
	}
	
}