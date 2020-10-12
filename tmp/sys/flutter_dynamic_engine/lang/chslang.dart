//-----------------------------------
//author：walter wong
//create time:2019-06-27
//description:中文处理类
//-----------------------------------

import 'baselang.dart';

class ChsLang implements BaseLang{
  @override
  String get defaultTextTitle => '基本输入框';

  @override
  String get emptyContainer=>'空容器';

  @override
  String get emptyValidatorErrorMsg=>'值不允许为空';
}