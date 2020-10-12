import 'package:dynamic_engine/lang/baselang.dart';

class EnLang implements BaseLang{
  @override
  String get defaultTextTitle=>'Please Input Text';

  @override
  String get emptyContainer=>'Empty Container';

  String get emptyValidatorErrorMsg=>'Value is not allowed to be empty';
}