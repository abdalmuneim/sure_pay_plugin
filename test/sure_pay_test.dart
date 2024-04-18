import 'package:flutter_test/flutter_test.dart';
import 'package:sure_pay/sure_pay.dart';
import 'package:sure_pay/sure_pay_platform_interface.dart';
import 'package:sure_pay/sure_pay_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockSurePayPlatform
    with MockPlatformInterfaceMixin
    implements SurePayPlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');
}

void main() {
  final SurePayPlatform initialPlatform = SurePayPlatform.instance;

  test('$MethodChannelSurePay is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelSurePay>());
  });

  test('getPlatformVersion', () async {
    SurePay surePayPlugin = SurePay();
    MockSurePayPlatform fakePlatform = MockSurePayPlatform();
    SurePayPlatform.instance = fakePlatform;

    expect(await surePayPlugin.getPlatformVersion(), '42');
  });
}
