<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Enterprise extends CI_Controller {
	
	public function index()
	{
		$user_email = $this->session->userdata(USER_EMAIL);
		if (isset($user_email) && $user_email != "") {
			$data[USER_EMAIL] = $user_email;
		}
		$data['title'] = ENTERPRISE_PAGE_TITLE;
		$data['enterprise'] = true;
		$data['footer'] = FOOTER_RIGHT;
		$data["page_no"] = 1;
		$data["page_size"] = USER_ENTERPRISE_PAGE_SIZE;
		$this->load->model("enterprise_model", "enterprise");
		$data["enterprises"] = $this->enterprise->findByPageWithEnabledType(1, USER_ENTERPRISE_PAGE_SIZE, "Y");
		$this->load->view('header', $data);
		$this->load->view('enterprise');
		$this->load->view('footer');
	}
	
	public function page() {
		$user_email = $this->session->userdata ( USER_EMAIL );
		if (isset ( $user_email ) && $user_email != "") {
			$data [USER_EMAIL] = $user_email;
		}
		$data ["title"] = ENTERPRISE_MANAGEMENT_TITLE;
		$data['enterprise'] = true;
		$data['footer'] = FOOTER_RIGHT;
		$pageNo = $this->uri->segment ( 3, 1 );
		if ($pageNo <= 0) {
			$pageNo = 1;
		}
		$data ["page_no"] = $pageNo;
		$data ["page_size"] = USER_ENTERPRISE_PAGE_SIZE;
		$this->load->model ( "enterprise_model", "enterprise" );
		$data ["enterprises"] = $this->enterprise->findByPageWithEnabledType ( $pageNo, USER_ENTERPRISE_PAGE_SIZE, "Y" );
		$this->load->view ( "header", $data );
		$this->load->view ( "enterprise" );
		$this->load->view ( "footer" );
	}
	
	public function total() {
		$this->load->model ( "enterprise_model", "enterprise" );
		$result ["total"] = $this->enterprise->totalWithEnabledType ("Y");
		echo json_encode ( $result );
	}
	
	public function entry() {
		$user_email = $this->session->userdata("user_email");
		if (isset($user_email) && $user_email != "") {
			$data["user_email"] = $user_email;
		}
		$data["title"] = ENTERPRISE_DETAIL_TITLE;
		$data['enterprise'] = true;
		$data["footer"] = FOOTER_LEFT;
		$id = $this->uri->segment(3);
		$type = $this->uri->segment(5);
		if ($id > 0) {
			$result = $this->enterprise->entry($id)->result();
			if (count($result) == 1) {
				$data["enterprise"] = $result[0];
			} else {
				$data["enterprise"] = NULL;
			}
		}
		if (empty($type) || (!empty($type) && $type != "json")) {
			$this->load->view("header", $data);
			$this->load->view("enterprise_detail");
			$this->load->view("footer");
		} else if (!empty($type) && $type == "json") {
			echo json_encode($data["enterprise"]);
		}
	}
}