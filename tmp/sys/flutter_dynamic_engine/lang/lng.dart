//-----------------------------------
//author：walter wong
//create time:2019-06-27
//description:语言加载主类
//-----------------------------------

import 'package:dynamic_engine/lang/baselang.dart';
import 'package:dynamic_engine/lang/chslang.dart';
import 'package:dynamic_engine/lang/enlang.dart';
import 'package:dynamic_engine/lang/lngtype.dart';

 class Lng{
  BaseLang _curlang;
  //设定当前语言
  static LngType localLangType = LngType.chs;
  //语言加载主类构造函数
  Lng(LngType language){
    switch(language)
    {
      case LngType.chs:
        _curlang = new ChsLang();
        break;
      case LngType.en:
        _curlang = new  EnLang();
        break;
    }
  }

  //语言缓存器
  static final Map<LngType, BaseLang> _cache=<LngType, BaseLang>{};

  //根据语言种类获取当前的语言处理类
  static BaseLang getLang([LngType language=LngType.chs]){
    if(_cache.containsKey(language))
      return _cache[language];

    var lg = new Lng(language);
    _cache[language]=lg._curlang;//缓存当前语言处理类
    return lg._curlang;
  }

  //获取应用初始化时设定的语言种类
  static BaseLang getLocalLang(){
    return getLang(localLangType);
  }
  
}