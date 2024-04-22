package com.example.sure_pay_plugin;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sure.poslibrary.usbIntegration.ActionsError;
import com.sure.poslibrary.usbIntegration.SureUsb;
import com.sure.poslibrary.usbIntegration.TerminalError;
import com.sure.poslibrary.usbIntegration.TransactionInformation;
import com.sure.poslibrary.usbIntegration.callbacks.FinancialTransactionCallback;

import java.util.HashMap;
import java.util.Map;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry;

/**
 * SurePayPlugin
 */
public class SurePayPlugin implements FlutterPlugin, MethodCallHandler, PluginRegistry.ActivityResultListener, ActivityAware {
    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    private MethodChannel channel;
    private Activity activity = null;
    private static final String SURE_PAY_PLUGIN_CHANNEL = "sure_pay_plugin/channel";
    private static final String SURE_PAY_PLUGIN_EVENT = "sure_pay_plugin/event";
    MyEventChannel myEventChannel = new MyEventChannel();
    Map<String, Object> data = new HashMap<>();

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), SURE_PAY_PLUGIN_CHANNEL);
        channel.setMethodCallHandler(this);

        EventChannel eventChannel = new EventChannel(flutterPluginBinding.getBinaryMessenger(), SURE_PAY_PLUGIN_EVENT); // timeHandlerEvent event name
        eventChannel.setStreamHandler(myEventChannel);
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
        switch (call.method) {
            case "getPlatformVersion":
                result.success("Android "+ Build.VERSION.RELEASE);
                break;
            case "usbConnectionInitialize":
                SureUsb.getInstance().openConnection(activity);
                append("initialized", "initialized");
                sendOtherData("");
                result.success(data.toString());
                break;
            case "sendAmount":
                String amount = call.argument("amount");
                SureUsb.getInstance().sendAmount(amount, new FinancialTransactionCallback() {
                    @Override
                    public void OnGetConnectionError(ActionsError error) {
                        String message = "purchase with amount " + amount + " got Connection Error " + error.toString();
                        append("OnGetConnectionError", message);
                        sendOtherData(message);
                    }

                    @Override
                    public void OnGetTransactionInformation(TransactionInformation model) {
                        String message = "purchase with amount " + amount + " got response " + model.toString();
                        append("OnGetTransactionInformation", message);
                        sendOtherData(model.toString());
                    }

                    @Override
                    public void OnGetTerminalError(TerminalError error) {
                        String message = "purchase with amount " + amount + " got Terminal Error " + error.toString();
                        append("OnGetConnectionError", message);
                        sendOtherData(message);
                    }
                });

                result.success(data.toString());
                break;
            default:
                append("default", "default");
                result.notImplemented();
                break;
        }

    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        channel.setMethodCallHandler(null);
    }


    private void append(String key, String dataSTR) {
        data.put(key, dataSTR);
        Log.d("data in app", data.toString());
    }

    private void sendOtherData(final String otherData) {
        Log.d("sendOtherData", "otherData is " + otherData);
        data.put("sendOtherData", otherData);
        myEventChannel.sendEvent(otherData);
    }

    @Override
    public void onAttachedToActivity(@NonNull ActivityPluginBinding binding) {
        activity = binding.getActivity();
        binding.addActivityResultListener(this);
    }

    @Override
    public void onDetachedFromActivityForConfigChanges() {

    }

    @Override
    public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding binding) {

    }

    @Override
    public void onDetachedFromActivity() {

    }

    @Override
    public boolean onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d("onActivityResult", data.getData().getUserInfo());
        Log.d("onActivityResultRequestCode", String.valueOf(requestCode));
        Log.d("onActivityResultResultCode", String.valueOf(resultCode));
        return false;
    }
}
