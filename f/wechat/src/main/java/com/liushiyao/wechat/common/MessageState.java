package com.liushiyao.wechat.common;

public class MessageState {

  //================返回类型=================//
  //返回消息类型：文本
  public static final String RESP_MESSAGE_TYPE_TEXT = "text";
  //返回消息类型：音乐
  public static final String RESP_MESSAGE_TYPE_MUSIC = "music";
  //返回消息类型：图文
  public static final String RESP_MESSAGE_TYPE_NEWS = "news";
  //返回消息类型：语音
  public static final String RESP_MESSAGE_TYPE_VOICE = "voice";

  //==================请求类型===============//
  //请求消息类型：文本
  public static final String REQ_MESSAGE_TYPE_TEXT = "text";
  //请求消息类型：图片
  public static final String REQ_MESSAGE_TYPE_IMAGE = "image";
  //请求消息类型：视频
  public static final String REQ_MESSAGE_TYPE_VIDEO = "video";
  //请求消息类型：小视频
  public static final String REQ_MESSAGE_TYPE_SHORT_VIDEO = "short video";
  //请求消息类型：语音
  public static final String REQ_MESSAGE_TYPE_VOICE = "voice";
  //请求消息类型：链接
  public static final String REQ_MESSAGE_TYPE_LINK = "link";
  //请求消息类型：地理位置
  public static final String REQ_MESSAGE_TYPE_LOCATION = "location";
  // 请求消息类型：事件
  public static final String REQ_MESSAGE_TYPE_EVENT = "event";

  //===============时间类型===================//
  /**
   * 事件类型：subscribe(订阅)
   */
  public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";

  /**
   * 事件类型：unsubscribe(取消订阅)
   */
  public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";

  /**
   * 事件类型：CLICK(自定义菜单点击事件)
   */
  public static final String EVENT_TYPE_CLICK = "CLICK";


}
