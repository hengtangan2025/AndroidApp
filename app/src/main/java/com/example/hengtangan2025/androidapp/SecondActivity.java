package com.example.hengtangan2025.androidapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

/**
 * Created by hengtangan2025 on 2016/3/3.
 */
public class SecondActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.second_layout);
    }
}
