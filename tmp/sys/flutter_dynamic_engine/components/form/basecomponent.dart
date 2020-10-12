import 'package:flutter/material.dart';
import 'package:dynamic_engine/db_fields/base_dbfield.dart';

class BaseComponent extends StatelessWidget
{
  @override
  Widget build(BuildContext context) {
    return Container();
  }

  BaseDbField getDefaultDbField(){
    return BaseDbField();
  }
}