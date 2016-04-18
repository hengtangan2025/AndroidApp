package com.example.hengtangan2025.androidapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by hengtangan2025 on 2016/4/15.
 */
public class BottomButtonsLayout extends LinearLayout {
    public BottomButtonsLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.bottom_buttons, this);
    }
}
