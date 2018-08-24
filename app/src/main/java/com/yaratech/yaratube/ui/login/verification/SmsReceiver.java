package com.yaratech.yaratube.ui.login.verification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class SmsReceiver extends BroadcastReceiver {

    public static final String SMS_SENDER_ADDRESS = "+98200049103";
    private static SmsListener mListener;
    private static final String TAG = "SmsReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle data = intent.getExtras();
        Object[] pdus = (Object[]) data.get("pdus");
        for (int i = 0; i < pdus.length; i++) {
            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);
            String sender = smsMessage.getDisplayOriginatingAddress();
            Log.d(TAG, "onReceivedMessage : sender is :  " + sender);
            if (sender.equals(SMS_SENDER_ADDRESS)) {
                mListener.onReceivedMessage(smsMessage.getMessageBody());
                break;
            }
        }
    }

    public static void bindListener(SmsListener listener) {
        mListener = listener;
    }

    public static void unBindListener() {
        mListener = null;
    }
}
