package com.example.hengtangan2025.androidapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by hengtangan2025 on 2016/3/3.
 */
public class SecondActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.second_layout);

        Button Button2 = (Button) findViewById(R.id.button_2);
        Button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               openOptionsMenu();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.second_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                break;
            case R.id.menu_wallaper:
                break;
            case R.id.menu_search:
                break;
            case R.id.menu_setting:
//                showSettings();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


//    private void showSettings(){
//
//        final Intent settings = new Intent(android.provider.Settings.ACTION_SETTINGS);
//        settings.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
//                Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
//
//        startActivity(settings);
//    }
}




