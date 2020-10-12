//-----------------------------------
//author：walter wong
//create time:2019-06-27
//description:基础下拉字典选择控件
//-----------------------------------

import 'package:dynamic_engine/db_fields/dropdown_dbfield.dart';
import 'package:dynamic_engine/lang/lng.dart';
import 'package:flutter/material.dart';


class XDropDown extends StatefulWidget{

  final DropDownDbField dbField = new DropDownDbField.getDefault(Lng.getLocalLang().defaultTextTitle);

  Map _formDataMap; 

  @override
  XDropDownState createState() => XDropDownState(dbField,_formDataMap);

  static XDropDown fromJsonMap(Map map,[Map formDataMap]){
    var xdropdown = XDropDown();
    xdropdown.dbField.title = map['title'];
    xdropdown.dbField.field = map['field'];
    xdropdown.dbField.table = map['table'];
    xdropdown.dbField.verify = map['verify'];
    xdropdown.dbField.dfValue = map['dfvalue'];
    xdropdown.dbField.type = map['type'];
    xdropdown.dbField.isHide = int.parse(map['isHide']);
    xdropdown._formDataMap = formDataMap;
    return xdropdown;
  }
}

class XDropDownState extends State<XDropDown> {

  DropDownDbField _dbField ;
  String _selectedFruit;
  Map _formDataMap;

  List keyVals = [
    {'key':'1','value':'Apple'},
    {'key':'2','value':'Banana'},
    {'key':'3','value':'Pineapple'},
    {'key':'4','value':'Mango'}
    ];

  XDropDownState(DropDownDbField field,Map formDataMap){
    _dbField=field;
    _formDataMap = formDataMap;
  }

  void changedDropDownItem(dynamic selectedFruit) {
    //String v = _getVal(selectedFruit.toString());
    if(this._formDataMap!=null){
      this._formDataMap[this._dbField.field] = selectedFruit;
    }
    setState(() {
          _selectedFruit = selectedFruit;
    });
  }

  String _getVal(String key){
    String val='';
    if(keyVals!=null)
    {
      keyVals.forEach((f){
        if(f['key']==key){
            val = f['value'];
            return;
        }
      });
    }
    print('***********$val*********');  
    return val;
  }


  @override
  Widget build(BuildContext context){
  return Container(
                padding: const EdgeInsets.only(left:15.0,right:15,top:15),
                child: DropdownButtonFormField(
      decoration: InputDecoration(labelText: _dbField.title+(_dbField.verify=='NotNull'?"(*)":"")+':'),
      value: _selectedFruit,
      items:keyVals.map((item){
        return DropdownMenuItem(
                  child: Text(item['value']),
                  value: item['key'],
                );}).toList(),
                onChanged: changedDropDownItem,
                )
              );
  }

}