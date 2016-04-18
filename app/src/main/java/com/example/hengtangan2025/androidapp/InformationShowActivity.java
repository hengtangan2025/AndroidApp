package com.example.hengtangan2025.androidapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by hengtangan2025 on 2016/4/13.
 */
public class InformationShowActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_show);
        TextView title = (TextView) findViewById(R.id.title);
        TextView content = (TextView) findViewById(R.id.content);
        final ImageView imageView = (ImageView) findViewById(R.id.image);
        Intent intent = getIntent();
        String get_title = intent.getStringExtra("title");
        String get_content = intent.getStringExtra("content");
        String get_image = intent.getStringExtra("image_url");
        DownImage downImage = new DownImage(get_image);
        title.setText(get_title);
        content.setText(get_content);
        downImage.loadImage(new DownImage.ImageCallBack() {

            @Override
            public void getDrawable(Drawable drawable) {
                // TODO Auto-generated method stub
                imageView.setImageDrawable(drawable);
            }
        });
    }



    public static class DownImage {
        public String image_path;


        public DownImage(String image_path) {
            this.image_path = image_path;
        }

        public void loadImage(final ImageCallBack callBack){

            final Handler handler = new Handler(){

                @Override
                public void handleMessage(Message msg) {
                    // TODO Auto-generated method stub
                    super.handleMessage(msg);
                    Drawable drawable = (Drawable) msg.obj;
                    callBack.getDrawable(drawable);
                }

            };

            new Thread(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    try {
                        Drawable drawable = Drawable.createFromStream(new URL(image_path).openStream(), "");

                        Message message = Message.obtain();
                        message.obj = drawable;
                        handler.sendMessage(message);
                    } catch (MalformedURLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }).start();
        }


        public interface ImageCallBack{
            public void getDrawable(Drawable drawable);
        }

    }
}
