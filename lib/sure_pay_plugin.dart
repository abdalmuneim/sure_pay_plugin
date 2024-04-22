import 'package:flutter/foundation.dart';

import 'sure_pay_plugin_platform_interface.dart';

class SurePayPlugin {
  final ValueChanged<String> callBak;
  final String amount;

  SurePayPlugin({required this.callBak, required this.amount}) {
    _getCallBack();
    _initConnection();
    Future.delayed(
      const Duration(seconds: 2),
          () => _setAmount(),
    );
  }

  Future<String?> getPlatformVersion() {
    return SurePayPluginPlatform.instance.getPlatformVersion();
  }

  _initConnection() => SurePayPluginPlatform.instance.initConnection();

  _setAmount() => SurePayPluginPlatform.instance.setAmount(amount: amount);

  _getCallBack() =>
      SurePayPluginPlatform.instance.setEventChannel(callBack: callBak);
  static void cancelStream() => SurePayPluginPlatform.instance.cancelStream();
}
