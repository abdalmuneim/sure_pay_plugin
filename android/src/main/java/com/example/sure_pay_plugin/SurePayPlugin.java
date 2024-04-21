package com.example.sure_pay_plugin;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.sure.poslibrary.usbIntegration.ActionsError;
import com.sure.poslibrary.usbIntegration.SureUsb;
import com.sure.poslibrary.usbIntegration.TerminalError;
import com.sure.poslibrary.usbIntegration.TransactionInformation;
import com.sure.poslibrary.usbIntegration.callbacks.FinancialTransactionCallback;

import java.util.HashMap;
import java.util.Map;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

/**
 * SurePayPlugin
 */
public class SurePayPlugin implements FlutterPlugin, MethodCallHandler {
    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    private Context context;


    private MethodChannel channel;
    private static final String CHANNELSTR = "sure_pay_plugin/channel";
    private static final String MY_EVENT_CHANNEL_NAME = "sure_pay_plugin/event";
    MyEventChannel myEventChannel = new MyEventChannel();
    Map<String, Object> data = new HashMap<>();

    public SurePayPlugin() {
        this.context = context;
    }

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), CHANNELSTR);
        channel.setMethodCallHandler(this);

        EventChannel eventChannel = new EventChannel(flutterPluginBinding.getBinaryMessenger(), MY_EVENT_CHANNEL_NAME); // timeHandlerEvent event name
        eventChannel.setStreamHandler(myEventChannel);
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
        switch (call.method) {
            case "usbConnectionInitialize":
                SureUsb.getInstance().openConnection(context);
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
}
