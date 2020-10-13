<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Auth_code {
	
	public $authCode = ''; // 验证码
	private $width = ''; // 验证码图片宽
	private $height = ''; // 验证码图片高
	private $len = ''; // 验证码长度
	private $tilt = array (-30, 30); // 验证码倾斜角度
	private $font = 'AlteHaasGroteskBold.ttf'; // 字体文件
	private $str = ''; // 验证码基
	private $image = ''; // 生成图片的句柄
	                          
	// 构造函数
	function __construct($width = NULL, $height = NULL, $len = NULL) {
		if ($width == NULL) {
			$this->width = AUTH_CODE_IMAGE_WIDTH;
		} else {
			$this->width = $width;
		}
		if ($height == NULL) {
			$this->height = AUTH_CODE_IMAGE_HEIGHT;
		} else {
			$this->height = $height;
		}
		if ($len == NULL) {
			$this->len = AUTH_CODE_LENGTH;
		} else {
			$this->len = $len;
		}
		$this->str = AUTH_CODE_STRING;
		for($i = 0; $i < $this->len; $i ++) {
			$this->authCode .= $this->str [rand ( 0, strlen($this->str) - 1 )];
		}
	}
	
	// 生成验证码图片
	private function imageCreate() {
		$this->image = imagecreatetruecolor ( $this->width, $this->height );
	}
	
	// 干扰颜色
	private function random_color() {
		return imagecolorallocate ( $this->image, rand ( 50, 180 ), rand ( 50, 180 ), rand ( 50, 180 ) );
	}
	
	// 生成干扰点
	private function draw_point() {
		for($i = 0; $i < $this->width * 2; $i ++) {
			imagesetpixel ( $this->image, rand ( 1, $this->width - 1 ), rand ( 1, $this->height - 1 ), $this->random_color () );
		}
	}
	
	// 生成干扰线
	private function draw_line() {
		for($i = 0; $i < $this->len; $i ++) {
			$x1 = rand ( 1, $this->width - 1 );
			$y1 = rand ( 1, $this->height - 1 );
			$x2 = rand ( 1, $this->width - 1 );
			$y2 = rand ( 1, $this->height - 1 );
			imageline ( $this->image, $x1, $y1, $x2, $y2, $this->random_color () );
		}
	}
	
	// 把验证码写入图片（不能和$this->imgstrfloat()同时使用）
	private function imgstr() {
		$start_x = 1;
		for($i = 0; $i < $this->len; $i ++) {
			$fontsize = rand ( 4, 8 ); // 字体大小
			$tmp_1 = $fontsize * 2.5;
          	$tmp_2 = $i > 0 ? $tmp_1 : 0;
			$y = rand ( 1, $this->height / 2 );
			$x = rand ( $start_x + $tmp_2, ($i + 1) * ($this->width) / $this->len - $tmp_1 );
			$start_x = $x;
			$color = imagecolorallocate ( $this->image, rand ( 200, 255 ), rand ( 200, 255 ), rand ( 200, 255 ) );
			imagestring ( $this->image, $fontsize, $x, $y, $this->authCode [$i], $color );
		}
	}
	
	// 把验证码倾斜写入图片（注意这里不能和$this->imgstr()方法同时使用）
	private function imgstrfloat() {
		$start_x = 1;
		for($i = 0; $i < $this->len; $i ++) {
			$fontfloat = rand ( $this->tilt [0], $this->tilt [1] );
			$fontsize = rand ( 10, 15 );        //字体大小
          	$tmp_1 = $i > 0 ? $fontsize : 0;
			$y = rand ( $fontsize + 2, $this->height - 2 );
			$x = rand ( $start_x + $tmp_1 + 2, ($i + 1) * ($this->width) / $this->len - $fontsize - 2 );	
			$start_x = $x;
			$color = imagecolorallocate ( $this->image, rand ( 200, 255 ), rand ( 200, 255 ), rand ( 200, 255 ) );
			imagettftext ( $this->image, $fontsize, $fontfloat, $x, $y, $color, $this->font, $this->authCode [$i] );
		}
	}
	
	// 输出验证码图片
	function output() {
		$this->imageCreate ();
		$this->imgstr ();
		// $this->imgstrfloat();
		$this->draw_point ();
		$this->draw_line ();
		header ( 'content-type:image/png' );
		imagepng ( $this->image );
		imagedestroy ( $this->image );
	}
}

?>
