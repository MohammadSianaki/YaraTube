package com.yaratech.yaratube.utils;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import com.yaratech.yaratube.R;

public class SnackbarUtils {

    public interface SnackbarCallback {
        void onRetryAgainPressed();
    }

    private SnackbarUtils() {
        //no instance
    }

    public static void showServerConnectionFailureSnackbar(View view, SnackbarCallback callback) {
        Snackbar snackbar = Snackbar
                .make(view, R.string.failure_server_connection_message, Snackbar.LENGTH_LONG)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        callback.onRetryAgainPressed();
                    }
                });

        // Changing message text color
        snackbar.setActionTextColor(Color.RED);

        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        snackbar.show();
    }

    public static void showSuccessfulMessage(View view, String message) {
        Snackbar snackbar = Snackbar
                .make(view, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}
