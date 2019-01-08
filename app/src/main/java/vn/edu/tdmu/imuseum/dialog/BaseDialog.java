package vn.edu.tdmu.imuseum.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import vn.edu.tdmu.imuseum.ultils.Utils;

public abstract class BaseDialog extends Dialog {
    private static final String LOG_TAG = BaseDialog.class.getSimpleName();

    protected abstract void bindingData();

    protected abstract int getLayoutId();

    protected abstract void initView();

    public BaseDialog(Context context) {
        super(context);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().setLayout(-1, -2);
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        setContentView(getLayoutId());
        setCancelable(false);
        initView();
        bindingData();
        setCanceledOnTouchOutside(true);
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
