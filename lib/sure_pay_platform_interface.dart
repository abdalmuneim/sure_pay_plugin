import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'sure_pay_method_channel.dart';

abstract class SurePayPlatform extends PlatformInterface {
  /// Constructs a SurePayPlatform.
  SurePayPlatform() : super(token: _token);

  static final Object _token = Object();

  static SurePayPlatform _instance = MethodChannelSurePay();

  /// The default instance of [SurePayPlatform] to use.
  ///
  /// Defaults to [MethodChannelSurePay].
  static SurePayPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [SurePayPlatform] when
  /// they register themselves.
  static set instance(SurePayPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }
}
