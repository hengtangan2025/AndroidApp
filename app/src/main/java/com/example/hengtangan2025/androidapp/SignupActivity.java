package com.example.hengtangan2025.androidapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hengtangan2025 on 2016/4/19.
 */
public class SignupActivity extends Activity {
    public static final int SHOW_RESPONSE = 0;
    private static final String signup_url = "http://192.168.0.230:3000/users/sign_up_from_app";

    private EditText PhoneNumberView;
    private EditText UserNameView;
    private EditText PasswordView;
    private String PhoneNumber;
    private String Password;
    private String UserName;

    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_RESPONSE:
                    String info_response = (String) msg.obj;
                    System.out.println("!!!!!!!!!!!!!!!!!!!");
                    System.out.println(info_response);
                    Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        PhoneNumberView = (EditText) findViewById(R.id.account_number);
        UserNameView = (EditText) findViewById(R.id.name_number);
        PasswordView = (EditText) findViewById(R.id.password);

        Button button = (Button) findViewById(R.id.sign_up_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneNumber = PhoneNumberView.getText().toString();
                UserName = UserNameView.getText().toString();
                Password = PasswordView.getText().toString();
                signup();
            }
        });
    }


    private void signup() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("name", UserName);
                    params.put("account_number", PhoneNumber);
                    params.put("password", Password);
                    StringBuilder resualt = HttpUtil.submitPostData(signup_url, params, "UTF-8");
                    Message message = new Message();
                    message.what = SHOW_RESPONSE;
                    message.obj = resualt.toString();
                    handler.sendMessage(message);
                } catch (Exception e) {
                    //返回报错信息
                    System.out.println("send post request error!" + e);
                } finally {
                    System.out.println("send post request success!");
                }

            }
        }).start();
    }
}
