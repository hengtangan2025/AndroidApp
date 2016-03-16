package com.example.hengtangan2025.androidapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by hengtangan2025 on 2016/3/3.
 */
public class SecondActivity extends Activity {
    boolean locked;
    private String [] data = {"Apple", "Banana", "Orange", "Watermelon", "Pear", "Grape", "Pineapple", "Strawberry", "Cherry", "Mango"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.second_layout);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SecondActivity.this, android.R.layout.simple_list_item_1, data);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        Button Button1 = (Button) findViewById(R.id.button_1);
        Button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this, PublishActivity.class);
                startActivity(intent);
            }
        });

        Button Button2 = (Button) findViewById(R.id.button_2);
        Button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locked = true;
                openOptionsMenu();
            }
        });

        Button Button3 = (Button) findViewById(R.id.button_3);
        Button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locked = false;
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
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        MenuInflater inflater = getMenuInflater();
        if (locked) {
            inflater.inflate(R.menu.second_menu, menu);
        }
        else {
            inflater.inflate(R.menu.second_menu2, menu);
        }
        return super.onPrepareOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_jiuyexinxi:
                showJiuyexinxi();
                break;
            case R.id.menu_gongyougushi:
                showGongyougushi();
                break;
            case R.id.menu_jinengpeixun:
                showJinengpeixun();
                break;
            case R.id.menu_anquanxinxi:
                showAnquanxinxi();
                break;
            case R.id.menu_mujuanchangyi:
                showMujuanchangyi();
                break;
            case R.id.menu_mujuanhuodong:
                showMujuanhuodong();
                break;
            case R.id.menu_guanyuwomen:
                showGuanyuwomen();
                break;
            case R.id.menu_caiwugongkai:
                showCaiwugongkai();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showJiuyexinxi() {
        Intent intent = new Intent(SecondActivity.this, JiuyexinxiActivity.class);
        startActivity(intent);
    }

    private void showGongyougushi() {
        Intent intent = new Intent(SecondActivity.this, GongyougushiActivity.class);
        startActivity(intent);
    }

    private void showJinengpeixun() {
        Intent intent = new Intent(SecondActivity.this, JinengpeixunActivity.class);
        startActivity(intent);
    }

    private void showAnquanxinxi() {
        Intent intent = new Intent(SecondActivity.this, AnquanxinxiActivity.class);
        startActivity(intent);
    }

    private void showCaiwugongkai() {
        Intent intent = new Intent(SecondActivity.this, CaiwugongkaiActivity.class);
        startActivity(intent);
    }

    private void showGuanyuwomen() {
        Intent intent = new Intent(SecondActivity.this, GuanyuwomenActivity.class);
        startActivity(intent);
    }

    private void showMujuanhuodong() {
        Intent intent = new Intent(SecondActivity.this, MujuanhuodongActivity.class);
        startActivity(intent);
    }

    private void showMujuanchangyi() {
        Intent intent = new Intent(SecondActivity.this, MujuanchangyiActivity.class);
        startActivity(intent);
    }
}




