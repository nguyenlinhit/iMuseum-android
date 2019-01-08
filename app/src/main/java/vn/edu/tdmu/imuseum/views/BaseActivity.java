package vn.edu.tdmu.imuseum.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by nvulinh on 11/13/17.
 *
 */

public abstract class BaseActivity extends AppCompatActivity{
    protected abstract void bindData();

    protected abstract int getActivityTitle();

    protected abstract int getLayoutId();

    protected abstract void initViews();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initViews();
        bindData();
    }
}
