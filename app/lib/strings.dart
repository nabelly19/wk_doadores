import 'dart:convert';
import 'package:flutter/services.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';

class Strings {
  static Map<String, String> _localizedStrings = {};

  static String get currentLanguageCode {
    String? envLang = dotenv.env['APP_LANGUAGE'];
    if (envLang != null && envLang.isNotEmpty) {
      return envLang;
    }
    return PlatformDispatcher.instance.locale.languageCode;
  }

  static Future<void> load() async {
    String languageCode = currentLanguageCode;

    String jsonString;
    try {
      jsonString = await rootBundle.loadString('assets/lang/$languageCode.json');
    } catch (e) {
      print("assets/lang/$languageCode.json not found!");
      return;
    }

    Map<String, dynamic> jsonMap = json.decode(jsonString);
    _localizedStrings = jsonMap.map((key, value) => MapEntry(key, value.toString()));
  }

  static String get(String key) {
    return _localizedStrings[key] ?? key;
  }
}
