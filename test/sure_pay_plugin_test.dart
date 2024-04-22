import 'package:flutter_test/flutter_test.dart';
import 'package:sure_pay_plugin/sure_pay_plugin.dart';
import 'package:sure_pay_plugin/sure_pay_plugin_platform_interface.dart';
import 'package:sure_pay_plugin/sure_pay_plugin_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockSurePayPluginPlatform
    with MockPlatformInterfaceMixin
    implements SurePayPluginPlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');
}

void main() {
  final SurePayPluginPlatform initialPlatform = SurePayPluginPlatform.instance;

  test('$MethodChannelSurePayPlugin is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelSurePayPlugin>());
  });

  test('getPlatformVersion', () async {
    SurePayPlugin surePayPlugin = SurePayPlugin();
    MockSurePayPluginPlatform fakePlatform = MockSurePayPluginPlatform();
    SurePayPluginPlatform.instance = fakePlatform;

    expect(await surePayPlugin.getPlatformVersion(), '42');
  });
}
