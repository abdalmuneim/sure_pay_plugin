
import 'sure_pay_platform_interface.dart';

class SurePay {
  Future<String?> getPlatformVersion() {
    return SurePayPlatform.instance.getPlatformVersion();
  }
}
