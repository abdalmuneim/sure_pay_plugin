import 'package:flutter/foundation.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'sure_pay_plugin_method_channel.dart';

abstract class SurePayPluginPlatform extends PlatformInterface {
  /// Constructs a SurePayPluginPlatform.
  SurePayPluginPlatform() : super(token: _token);

  static final Object _token = Object();

  static SurePayPluginPlatform _instance = MethodChannelSurePayPlugin();

  /// The default instance of [SurePayPluginPlatform] to use.
  ///
  /// Defaults to [MethodChannelSurePayPlugin].
  static SurePayPluginPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [SurePayPluginPlatform] when
  /// they register themselves.
  static set instance(SurePayPluginPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }

  Future<void> initConnection() {
    throw UnimplementedError('initConnection() has not been implemented.');
  }

  Future<void> setAmount({required String amount}) {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }

  Future<void> setEventChannel({required ValueChanged<String> callBack}) {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }

  Future<void> cancelStream() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }
}
