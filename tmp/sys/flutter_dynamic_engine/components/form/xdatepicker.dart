//-----------------------------------
//author：walter wong
//create time:2019-06-27
//description:时间选择控件
//-----------------------------------

import 'package:dynamic_engine/components/form/form_validator.dart';
import 'package:dynamic_engine/db_fields/date_dbfield.dart';
import 'package:dynamic_engine/lang/lng.dart';
import 'package:dynamic_engine/lang/lngtype.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'basecomponent.dart';
import 'package:dynamic_engine/lang/baselang.dart';

import 'datetime_picker_formfield.dart';

class XDatePicker extends StatefulWidget
{
   DateDbField dbField = new DateDbField.getDefault(Lng.getLang(LngType.chs).defaultTextTitle);

   Map _formDataMap;

  @override
  DatePickerState createState() => DatePickerState(dbField,_formDataMap);

  static XDatePicker fromJsonMap(Map map,[Map formDataMap]){
      String title = map['title'];
      var datePicker = XDatePicker();
      datePicker._formDataMap = formDataMap;
      datePicker.dbField.title = title;
      datePicker.dbField.field = map['field'];
      datePicker.dbField.table = map['table'];
      datePicker.dbField.verify = map['verify'];
      datePicker.dbField.dfValue = map['dfvalue'];
      datePicker.dbField.type = map['type'];
      datePicker.dbField.isHide = int.parse(map['isHide']);
      return datePicker;
   }

}

class DatePickerState extends State<XDatePicker> {

  DateDbField _dbField ;
  DateTime date;
  Map _formDataMap;
  DatePickerState(DateDbField field,Map formDataMap){
    _dbField=field;
    _formDataMap = formDataMap;
  }

  @override
  Widget build(BuildContext context){
    if(0==_dbField.isHide)
    {
        return Container(
              padding: const EdgeInsets.only(left:15.0,right:15,top:15),
              child: DateTimePickerFormField(decoration:InputDecoration(labelText:_dbField.title+(_dbField.verify=='NotNull'?"(*)":"")+":",suffixIcon:Icon(Icons.date_range),enabled: true), onChanged: (dt)=>setState(()=>date=dt),format: DateFormat('yyyy-MM-dd'),
              onSaved: (val){
                if(this._formDataMap!=null){
                      this._formDataMap[this._dbField.field] = val?.toString();
                    }
              },validator: (val){
                    return FormValidator.buildValidator(val?.toString(),this._dbField.verify);
                  })
            );
    }
    else{
      return new Container(height:0.0,width:0.0);
    }
    
  }
}
