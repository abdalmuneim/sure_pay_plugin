import 'dart:async';

import 'package:flutter/foundation.dart';
import 'package:flutter/rendering.dart';
import 'package:flutter/services.dart';

import 'sure_pay_plugin_platform_interface.dart';

/// An implementation of [SurePayPluginPlatform] that uses method channels.
class MethodChannelSurePayPlugin extends SurePayPluginPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('sure_pay_plugin/channel');
  final EventChannel _eventChannel =
  const EventChannel("sure_pay_plugin/event");
  StreamSubscription? _streamSubscription;

  @override
  Future<String?> getPlatformVersion() async {
    final version =
    await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }

  @override
  Future<void> setEventChannel({required ValueChanged<String> callBack}) async {
    _streamSubscription =
        _eventChannel.receiveBroadcastStream().listen((event) {
          callBack(event);
        });
    return;
  }

  @override
  Future<void> initConnection() async {
    await methodChannel.invokeMethod('usbConnectionInitialize');
  }

  @override
  Future<void> setAmount({required String amount}) async {
    await methodChannel.invokeMethod('sendAmount', {'amount': amount});
  }

  @override
  Future<void> cancelStream() async {
    _streamSubscription?.cancel();
  }
}
