<?php
namespace serve\websocket\common\traits;

/**
 *
 */
trait End {
	public $content_type = [
		"text" => "text/html;charset=utf-8",
	];
	public function End($response, $data = null, $code = 200, $message = "请求成功", $content_type = "text") {
		// echo "common/End\n";
		if (empty($message)) {
			$msg = [
				'200' => "成功",
				"400" => "失败",
			];
			$message = $msg[$code] ?? "未知";
		}
		// 用户客户端直接连发送消息
		$returnData = [
			'message' => $message,
			'code' => $code,
			'data' => $data,
		];
		$response->header('content-type', $this->content_type[$content_type] ?? "text/html;charset=utf-8", true);
		$response->status($code);
		// echo "发送消息\n";
		$response->end("<h1>Hello sok</h1><div>" . json_encode($returnData, JSON_UNESCAPED_UNICODE) . "</div>");
	}

}
?>