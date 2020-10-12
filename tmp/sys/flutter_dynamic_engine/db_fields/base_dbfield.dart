//-----------------------------------
//author：walter wong
//create time:2019-06-26
//description:表单控件model层基础描述类
//-----------------------------------
import 'package:json_annotation/json_annotation.dart';

@JsonSerializable()
class BaseDbField{
  String _field;
  String _type;
  String _title;
  int _isHide;
  String _table;
  String _verify;
  String _dfvalue;

  //对应的数据库字段名
  String get field=>_field;
  set field(String value)=>_field=value;

  //对应的控件类型
  String get type=>_type;
  set type(String value)=>_type = value;


  //标题
  String get title=>_title;
  set title(String value)=>_title=value;

  //是否隐藏
  int get isHide=>_isHide;
  set isHide(int value)=>_isHide=value;

  //数据库表名
  String get table=>_table;
  set table(String value)=>_table=value;

  //校验规则
  String get verify=>_verify;
  set verify(String value)=>_verify=value;

  //默认值
  String get dfValue=>_dfvalue;
  set dfValue(String value)=>_dfvalue=value;

  BaseDbField();

  //初始化默认对象
  BaseDbField.getDefault(String title){
    this._title = title;
  }
}