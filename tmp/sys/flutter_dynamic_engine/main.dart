import 'package:dynamic_engine/commons/utils.dart';
import 'package:dynamic_engine/lang/lng.dart';
import 'package:dynamic_engine/lang/lngtype.dart';
import 'package:dynamic_engine/parser/xform_parser.dart';
import 'package:flutter/material.dart';
import 'package:json_annotation/json_annotation.dart';
import 'components/form/xdatepicker.dart';
import 'db_fields/date_dbfield.dart';
import 'components/Form/xtext.dart';
import 'package:flutter_localizations/flutter_localizations.dart';

void main() => runApp(MyApp());
//var model=new XModel("Dynamic APP");

class MyApp extends StatelessWidget {

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    //设定当前语言种类
    Lng.localLangType = LngType.chs;

    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        // This is the theme of your application.
        //
        // Try running your application with "flutter run". You'll see the
        // application has a blue toolbar. Then, without quitting the app, try
        // changing the primarySwatch below to Colors.green and then invoke
        // "hot reload" (press "r" in the console where you ran "flutter run",
        // or press Run > Flutter Hot Reload in a Flutter IDE). Notice that the
        // counter didn't reset back to zero; the application is not restarted.
        primarySwatch: Colors.blue,
      ),
      home: MyHomePage(title: "Dynamic APP"),
      locale: const Locale('zh'),
      localizationsDelegates: [                             //此处
        GlobalMaterialLocalizations.delegate,
        GlobalWidgetsLocalizations.delegate,
      ],
      supportedLocales: [  // 支持的语言列表
          const Locale('zh','CH'),
          const Locale('en', 'US'),
      ]
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key key, this.title}) : super(key: key);

  // This widget is the home page of your application. It is stateful, meaning
  // that it has a State object (defined below) that contains fields that affect
  // how it looks.

  // This class is the configuration for the state. It holds the values (in this
  // case the title) provided by the parent (in this case the App widget) and
  // used by the build method of the State. Fields in a Widget subclass are
  // always marked "final".

  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  int _counter = 0;

  void _incrementCounter() {
    setState(() {
      // This call to setState tells the Flutter framework that something has
      // changed in this State, which causes it to rerun the build method below
      // so that the display can reflect the updated values. If we changed
      // _counter without calling setState(), then the build method would not be
      // called again, and so nothing would appear to happen.
      _counter++;
    });
  }

  @override
  Widget build(BuildContext context) {
    // This method is rerun every time setState is called, for instance as done
    // by the _incrementCounter method above.
    //
    // The Flutter framework has been optimized to make rerunning build methods
    // fast, so that you can just rebuild anything that needs updating rather
    // than having to individually change instances of widgets.
    // return Scaffold(
    //   appBar: AppBar(
    //     // Here we take the value from the MyHomePage object that was created by
    //     // the App.build method, and use it to set our appbar title.
    //     title: Text(widget.title),
    //   ),
    //   body: ,
    //   // floatingActionButton: FloatingActionButton(
    //   //   onPressed: _incrementCounter,
    //   //   tooltip: 'Increment',
    //   //   child: Icon(Icons.add),
    //   // ), 
    // );

return XFormParser().parser('''{
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
                    "field":"F_Name",
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
                    "field":"F_Date",
                    "proportion":"4",
                    "verify":"NotNull",
                    "isHide":"1",
                    "dfvalue":"",
                    "id":"42ca0aab-e977-2663-aa0e-30ac4b62d8bc"
                },
                {
                    "title":"类型选择",
                    "type":"dropdown",
                    "table":"Env_Problem",
                    "field":"F_DropType",
                    "proportion":"4",
                    "verify":"NotNull",
                    "isHide":"1",
                    "dfvalue":"",
                    "id":"42ca0aab-e977-2663-aa0e-30ac4b62d8bc"
                }
            ]
}''');

  }
}
