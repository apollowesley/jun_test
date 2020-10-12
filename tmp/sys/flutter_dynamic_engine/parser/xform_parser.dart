//-----------------------------------
//author：walter wong
//create time:2019-07-01
//description:基础表单渲染器
//-----------------------------------

import 'package:dynamic_engine/commons/utils.dart';
import 'package:dynamic_engine/components/Form/xtext.dart';
import 'package:dynamic_engine/components/form/xdatepicker.dart';
import 'package:dynamic_engine/components/form/xdropdown.dart';
import 'package:dynamic_engine/components/form/xlable.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'dart:convert';

class XFormParser{

final Map _formDataMap = new Map();
final GlobalKey<FormState> _formKey = new GlobalKey<FormState>();
/*
****************json数据格式****************
{
            "id":"1",
            "text":"主表信息",
            "sort":"0",
            "componts":[
                {
                    "title":"环境问题交办",
                    "type":"label",
                    "proportion":"1",
                    "id":"23e56aef-d3f0-057b-7a39-93a0dbad98a2"
                },
                {
                    "title":"序号",
                    "type":"text",
                    "table":"Env_Problem",
                    "field":"F_OrderIndex",
                    "proportion":"4",
                    "verify":"NotNull",
                    "isHide":"0",
                    "dfvalue":"",
                    "id":"42ca0aab-e977-2663-aa0e-30ac4b62d8bc"
                },
                {
                    "title":"姓名",
                    "type":"text",
                    "table":"Env_Problem",
                    "field":"F_OrderIndex",
                    "proportion":"4",
                    "verify":"NotNull",
                    "isHide":"0",
                    "dfvalue":"",
                    "id":"42ca0aab-e977-2663-aa0e-30ac4b62d8bc"
                },
                {
                    "title":"日期",
                    "type":"datetime",
                    "table":"Env_Problem",
                    "field":"F_OrderIndex",
                    "proportion":"4",
                    "verify":"NotNull",
                    "isHide":"0",
                    "dfvalue":"",
                    "id":"42ca0aab-e977-2663-aa0e-30ac4b62d8bc"
                }
            ]
}
*/
  //对json数据进行解析并渲染组件集合
  Widget parser(String json){

    try{
      var map = jsonDecode(json);
      List<dynamic> componts = map["componts"];
      List<Widget> widgets = <Widget>[];

      componts.forEach((f){
          widgets.add(_buildFromMap(f));
      });

      var body = SingleChildScrollView(child: Form(key: _formKey, child:Column(children: widgets)));
      return _buildScaffoldForm(map,body);
    }
    catch(e){
      return Container(child:Text(e.toString()));
    }
    
  }

  //对单个组件描述进行组件渲染
  Widget _buildFromMap(Map<String, dynamic> map){
    String type = map['type'];
    switch(type){
      case 'label':
        return XLabel.fromJsonMap(map);
      
      case 'datetime':
        return XDatePicker.fromJsonMap(map,_formDataMap);

      case 'dropdown':
        return XDropDown.fromJsonMap(map,_formDataMap);

      case 'text':
      default:
        return XText.fromJsonMap(map,_formDataMap);
    }
  }

  Widget _buildScaffoldForm(Map map,Widget body){
    _formDataMap['id']=map['id'];
    return Scaffold(appBar:_buildAppBar(map['text']),body: body);
  }

  //构建标题栏
  AppBar _buildAppBar(String title){
    //构建appbar的标题和按钮
    AppBar appBar = AppBar(title: Text(title),actions: _buildTopActions(),);


    //todo：权限控制


    return appBar;
  }

  //构建顶部按钮功能集
  List<Widget> _buildTopActions(){
    return <Widget>[
      IconButton(icon:Icon(Icons.save),tooltip: '保存',onPressed: (){
      debugPrint('form Saved.');
      formSave();
    }),
    PopupMenuButton(icon:Icon(Icons.more_horiz),itemBuilder: (BuildContext context) => <PopupMenuEntry<String>>[
                      const PopupMenuItem<String>(value:"1", child:ListTile(leading: Icon(Icons.delete),title: Text('删除'))),
                      const PopupMenuItem<String>(value:"2", child:ListTile(leading: Icon(Icons.cancel),title: Text('取消'))),
                    ],
                    onSelected: (String result){
                          switch(result){
                            case "1":
                            break;
                            case "2":
                            break;
                          }
                    },)];
  }

  formSave(){
      final form = _formKey.currentState;
      if (form!=null&&form.validate()) {
      form.save();
      var formData = jsonEncode(_formDataMap);
      print('----Form Saved.----');
      print(formData);

      //todo:封装onPreSaved事件

      //todo:onSaved事件
      
    }
  }

  _formDelete(){

  }


}