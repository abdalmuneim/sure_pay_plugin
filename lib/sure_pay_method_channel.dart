import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'sure_pay_platform_interface.dart';

/// An implementation of [SurePayPlatform] that uses method channels.
class MethodChannelSurePay extends SurePayPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('sure_pay');

  @override
  Future<String?> getPlatformVersion() async {
    final version = await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }
}
