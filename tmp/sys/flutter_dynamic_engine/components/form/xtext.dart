//-----------------------------------
//author：walter wong
//create time:2019-06-27
//description:基础文本输入控件
//-----------------------------------

import 'package:dynamic_engine/components/form/form_validator.dart';
import 'package:dynamic_engine/lang/lng.dart';
import 'package:dynamic_engine/lang/lngtype.dart';
import 'package:flutter/material.dart';
import 'package:dynamic_engine/db_fields/text_dbfield.dart';
import 'basecomponent.dart';
import 'package:dynamic_engine/lang/baselang.dart';

//基本输入控件
class XText extends BaseComponent
{
  final TextDbField dbField = new TextDbField.getDefault(Lng.getLocalLang().defaultTextTitle);

  Map _formDataMap;

  //map:json表单描述
  //formDataMap 提交数据存储位置
   static XText fromJsonMap(Map map,[Map formDataMap]){
     
      String title = map['title'];
      var xtext = XText();
      xtext._formDataMap = formDataMap;
      xtext.dbField.title = title;
      xtext.dbField.field = map['field'];
      xtext.dbField.table = map['table'];
      xtext.dbField.verify = map['verify'];
      xtext.dbField.dfValue = map['dfvalue'];
      xtext.dbField.type = map['type'];
      xtext.dbField.isHide = int.parse(map['isHide']);
      return xtext;
   }

  @override
  Widget build(BuildContext context) {
    if(0==dbField.isHide)
    {
      return Container(
        padding: const EdgeInsets.only(left:15.0,right:15,top:15),
        child: Row(
          //crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            //Padding(child: Text(_dbField.title+':',maxLines: 1),padding: EdgeInsets.only(top:15,right: 5),),
            Expanded(flex: 1,child: new TextFormField(decoration: InputDecoration(labelText: dbField.title+(dbField.verify=='NotNull'?"(*)":"")+':'),onSaved:(val){
              if(this._formDataMap!=null){
                this._formDataMap[this.dbField.field] = val;
              }
            } ,validator: (val){
              return FormValidator.buildValidator(val,this.dbField.verify);
            },))
          ],
        )
      );
    }
    else{
      return new Container(height:0.0,width:0.0);
    }
  }

}
