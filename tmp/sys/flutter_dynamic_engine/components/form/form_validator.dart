//-----------------------------------
//author：walter wong
//create time:2019-07-01
//description:表单输入控件的校验规则处理器
//-----------------------------------

import 'package:dynamic_engine/lang/lng.dart';

class FormValidator{

  //构造字段校验方法的入口
  static String buildValidator(String val,String verify){
    switch(verify)
    {
      case 'NotNull':
      return _verifyEmpty(val);

      default:
      return null;
    }
  }

  //用于校验是否为空的方法
  static String _verifyEmpty(String val){
    return val==null||val.isEmpty?Lng.getLocalLang().emptyValidatorErrorMsg:null;
  }

}