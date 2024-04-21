package com.example.sure_pay_plugin;


import android.util.Log;

import io.flutter.plugin.common.EventChannel;

public class MyEventChannel implements EventChannel.StreamHandler {
    private EventChannel.EventSink eventSink;

    @Override
    public void onListen(Object arguments, EventChannel.EventSink events) {
        // The Flutter app has opened the Event Channel
        Log.d("onListenEvents","events is " +events);
        Log.d("onListenArguments","arguments is "+arguments);
        eventSink = events;
    }

    @Override
    public void onCancel(Object arguments) {
        // The Flutter app has closed the Event Channel
        eventSink = null;
    }

    public void sendEvent(String message) {
        // Send a message to the Flutter app
        Log.d("sendEvent","message is "+ message);
        if (eventSink != null) {
            eventSink.success(message);
        }
    }
}