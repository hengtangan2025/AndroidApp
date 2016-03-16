package com.example.hengtangan2025.androidapp;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by hengtangan2025 on 2016/3/15.
 */
public class PublishLayout extends LinearLayout {

    public PublishLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.publish,this);
        Button titleBack = (Button) findViewById(R.id.publish);
        titleBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) getContext()).finish();
            }
        });
    }
}
