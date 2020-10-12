//-----------------------------------
//author：walter wong
//create time:2019-06-28
//description:标题控件
//-----------------------------------

import 'package:dynamic_engine/lang/lng.dart';
import 'package:dynamic_engine/lang/lngtype.dart';
import 'package:flutter/material.dart';
import 'package:dynamic_engine/db_fields/text_dbfield.dart';
import 'basecomponent.dart';
import 'package:dynamic_engine/lang/baselang.dart';


class XLabel extends BaseComponent{

  TextDbField dbField = new TextDbField.getDefault('');

  static XLabel fromJsonMap(Map map){
      String title = map['title'];
      var xlabel = XLabel();
      xlabel.dbField.title = title;
      return xlabel;
   }

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: const EdgeInsets.only(left:15.0,right:15,top:15),
      child: Text(dbField.title)
    );
  }
}