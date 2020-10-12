import 'base_dbfield.dart';
import 'package:json_annotation/json_annotation.dart';

@JsonSerializable()
class DateDbField extends BaseDbField{
    String _dateformat='yyyy-MM-dd';//默认时间格式

    //获取时间字段的格式
    String get dateFormat=>_dateformat;
    set dateFormat(String value)=>_dateformat=value;

    DateDbField.getDefault(String title):super.getDefault(title){

  }
}