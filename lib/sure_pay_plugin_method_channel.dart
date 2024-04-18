import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'sure_pay_plugin_platform_interface.dart';

/// An implementation of [SurePayPluginPlatform] that uses method channels.
class MethodChannelSurePayPlugin extends SurePayPluginPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('sure_pay_plugin');

  @override
  Future<String?> getPlatformVersion() async {
    final version = await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }
}
