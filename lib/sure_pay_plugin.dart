
import 'sure_pay_plugin_platform_interface.dart';

class SurePayPlugin {
  Future<String?> getPlatformVersion() {
    return SurePayPluginPlatform.instance.getPlatformVersion();
  }
}
