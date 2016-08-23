package com.monigarr.servicefusionrealmdemo.view.base;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by monigarr on 8/22/16.
 */

public abstract class BaseActivity extends AppCompatActivity {

    abstract protected void initComponents();

    public void showMessage(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}
