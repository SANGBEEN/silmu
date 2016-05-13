package org.androidtown.location;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

/**
 * Created by saltfactory on 6/8/15.
 */
public class MyGcmListenerService extends GcmListenerService {
    public MyGcmListenerService(){

    }
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");
        Log.d("test", "From: " + from);
        Log.d("test", "Message: " + message);


        sendNotification(message);
    }

        private void sendNotification(String str){

        }




}