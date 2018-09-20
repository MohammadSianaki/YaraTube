package com.yaratech.yaratube.ui.login.verification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;

import com.yaratech.yaratube.utils.TextUtils;

public class SmsBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "SmsBroadcastReceiver";

    private static final String serviceProviderNumber = "+98200049103";

    private Listener listener;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive() called with: context = [" + context + "], intent = [" + intent + "]");
        if (intent.getAction().equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {
            Log.d(TAG, "onReceive: 1");
            String smsSender = "";
            String smsBody = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                Log.d(TAG, "onReceive: 2");
                for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                    smsSender = smsMessage.getDisplayOriginatingAddress();
                    smsBody += smsMessage.getMessageBody();
                }
            } else {
                Log.d(TAG, "onReceive: 3");
                Bundle smsBundle = intent.getExtras();
                if (smsBundle != null) {
                    Log.d(TAG, "onReceive: 4");
                    Object[] pdus = (Object[]) smsBundle.get("pdus");
                    if (pdus == null) {
                        // Display some error to the user
                        Log.e(TAG, "SmsBundle had no pdus key");
                        return;
                    }
                    SmsMessage[] messages = new SmsMessage[pdus.length];
                    for (int i = 0; i < messages.length; i++) {
                        messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                        smsBody += messages[i].getMessageBody();
                    }
                    smsSender = messages[0].getOriginatingAddress();
                }
            }

            if (smsSender.equals(serviceProviderNumber)) {
                Log.d(TAG, "onReceive: equals sender address");
                listener.onTextReceived(TextUtils.removeNonDigits(smsBody));
            }
        }
    }

    void setListener(Listener listener) {
        this.listener = listener;
    }

    interface Listener {
        void onTextReceived(String text);
    }
}