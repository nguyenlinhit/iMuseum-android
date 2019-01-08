package vn.edu.tdmu.imuseum.dialog;

import android.content.Context;
import android.os.Bundle;

import vn.edu.tdmu.imuseum.ultils.Utils;

public class ProgressDialog extends android.app.ProgressDialog {
    private static final String LOG_TAG = ProgressDialog.class.getSimpleName();
    private String message;

    public ProgressDialog(Context context, int message) {
        this(context, context.getResources().getString(message));
    }

    public ProgressDialog(Context context, String message) {
        super(context);
        this.message = message;
    }

    public ProgressDialog(Context context) {
        super(context);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
        setMessage(this.message);
    }

    public void show() {
        if (isShowing()) {
            Utils.showWarningLog(LOG_TAG, "Badly handle dialog: duplicate dialog");
        } else {
            super.show();
        }
    }

    public void dismiss() {
        if (isShowing()) {
            super.dismiss();
        } else {
            Utils.showWarningLog(LOG_TAG, "Badly handle dialog: dialog is currently not showing to be dismissed");
        }
    }
}
