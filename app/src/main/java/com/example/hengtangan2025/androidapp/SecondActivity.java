package com.example.hengtangan2025.androidapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by hengtangan2025 on 2016/3/3.
 */
public class SecondActivity extends Activity {
    private static final int SHOW_RESPONSE = 0;
    private TextView responseText;
    boolean locked;
    private String [] data = {"Apple", "Banana", "Orange", "Watermelon", "Pear", "Grape", "Pineapple", "Strawberry", "Cherry", "Mango"};

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_RESPONSE:
                    String response = (String) msg.obj;
                    responseText.setText(response);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.second_layout);
        responseText = (TextView) findViewById(R.id.response_text);
        sendRequestWithURLConnection();



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


    private void sendRequestWithURLConnection() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL("http://192.168.0.230:3000/informations");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    Message message = new Message();
                    message.what = SHOW_RESPONSE;
                    message.obj = response.toString();
                    handler.sendMessage(message);
            } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
                }
        }).start();
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




