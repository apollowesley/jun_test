<?php
namespace serve\websocket\common\traits;
use Swoole\Coroutine as co;
global $taskList;
$taskList = [];
global $taskTimerAfter;
$taskTimerAfter = true;
/**
 *
 */
trait Push {

	// 统一发送消息出口 发给fd,不备份消息数据，即使用户不在线，未收到的消息也不会被保存到数据库。用户上线接收不到。
	public function pushFd($server, $msg = [], $to_fd) {
		if (is_array($msg)) {
			$msg = json_encode($msg, JSON_UNESCAPED_UNICODE);
		}
		// print_r($server->exist($to_fd));
		$isOnLine = 0; //返回值 对方是否在线

		if ($server->exist($to_fd)) {
			$isOnLine = 1;
			// 客户端直连发送消息
			$server->push($to_fd, $msg);
		} else {
			$server->close($to_fd);
		}

		return $isOnLine;
	}

	// 向用户发送消息，提取uid对应所有fd 然后触发pushToFd(),逐个推送消息
	// 支持数据备份，保存未读消息到数据库。如果开启数据备份或者未读消息保存,用户如果不在线，下次登陆会读库获取
	public function pushUid($server, $msg, $to_uid) {
		// echo "pushToUid:" . $to_uid;
		global $taskList;
		global $taskTimerAfter;
		$clientInfo = $GLOBALS['clientInfo'][$to_uid] ?? [];
		//返回值 对方是否在线
		$msg['state'] = empty($clientInfo) ? 0 : 1;
		// $msg['state'] = $isOnLine;
		$msg['id'] = $msg['id'] ?? $this->makeOnlyID('chat');
		// 发送消息给接收方
		if (!empty($clientInfo) && is_array($clientInfo)) {
			foreach ($clientInfo as $key => $value) {
				$msg['state'] = $this->pushFd($server, $msg, $value['fd']) ?? $msg['state'];
			}
		}

		// 是否开启数据备份 ||或者未读消息入库
		if ($server->websocketConfig['save_unread_db'] || ($server->websocketConfig['backup_db'] && $msg['state'] == 0)) {
			// 开启了未读消息入库

			// echo "tasks\n";
			$taskList[] = $msg;

			if ($taskTimerAfter) {
				$taskTimerAfter = false;
				// $taskList = $taskList;
				// $taskList = [];
				\Swoole\Timer::after(2000, function () use ($server) {
					global $taskList;
					global $taskTimerAfter;
					$taskTimerAfter = true;
					$arr = [];
					foreach ($taskList as $value) {
						$arr[] = array_shift($taskList);
					}
					// print_r(count($arr));
					// echo "tasks_timeout-START\n";

					$data = (object) [];
					$data->action = 'tasks';
					$data->class = 'saveChatDb';
					$data->data = $arr;
					$server->task($data);
				});
			} else {

			}
			// return 1;
		}
		return $msg;

	}
	// 群发消息给所有用户uid
	//遍历主线程全局$GLOBALS['clientInfo'],通过uid 并且触发pushToUid() ，逐个推送消息
	public function pushUidAll($server, $msg = [], $UidAll = []) {
		// echo "pushToAll";
		$isOnLine = 0;
		if (empty($UidAll)) {
			$start_fd = 0;
			$clientInfo = $GLOBALS['clientInfo'];

			foreach ($clientInfo as $to_uid) {
				$isOnLine = $this->pushUid($server, $msg, $to_uid) ?? $isOnLine;
			}

		} elseif (is_array($UidAll)) {
			foreach ($UidAll as $to_uid) {
				$isOnLine = $this->pushUid($server, $msg, $to_uid) ?? $isOnLine;
			}
		}

		return $isOnLine;

	}

	// 群发消息给所有用户（$allFd为空将会发给所有用户），否则$allFd是数组value=fd可以规定某些用户范围。
	//遍历fd 并且触发pushToFd() ，逐个推送消息
	public function pushFdAll($server, $msg = [], $allFd = []) {
		// echo "pushToAll";
		$isOnLine = 0;
		if (empty($allFd)) {
			$start_fd = 0;
			while (true) {
				$conn_list = $server->getClientList($start_fd, 100);

				if (empty($conn_list)) {
					// echo "\n finish\n";
					break;
				}
				$start_fd = end($conn_list);

				foreach ($conn_list as $fd) {
					$isOnLine = $this->pushFd($server, $msg, $fd) ?? $isOnLine;
				}
			}
		} elseif (is_array($allFd)) {
			foreach ($allFd as $fd) {
				$isOnLine = $this->pushFd($server, $msg, $fd) ?? $isOnLine;
			}
		}

		return $isOnLine;

	}

}
?>