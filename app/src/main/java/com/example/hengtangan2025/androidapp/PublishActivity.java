package com.example.hengtangan2025.androidapp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by hengtangan2025 on 2016/3/7.
 */
public class PublishActivity extends Activity {
    public static final int CROP_PHOTO = 2;
    public static final int CHOOSE_PHOTO = 3;
    public static final int SHOW_RESPONSE = 0;
    private static final String url = "http://192.168.0.230:3000/informations/create_from_app";
    private static Map<String, String> info_map;
    private String imagePath;
    private String imageFileName;
    private Button chooseFromAlbum;
    private ImageView picture;
    private EditText TitleText;
    private EditText ContentText;
    private String Title;
    private String Content;
    private String Information_type;
    private Uri imageUri;
    private RadioGroup Information_types;


    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_RESPONSE:
                    String info_response = (String) msg.obj;
                    info_map = setData(info_response);
                    String title = info_map.get("title");
                    String content = info_map.get("content");
                    String image = info_map.get("image_url");
                    Intent intent = new Intent(PublishActivity.this, InformationShowActivity.class);
                    intent.putExtra("title", title);
                    intent.putExtra("content", content);
                    intent.putExtra("image_url", image);
                    startActivity(intent);
                    break;
            }
        }
    };
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    public Map<String, String> setData(String response) {
        Map<String, String> info_map = new HashMap<String, String>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            String info_id = jsonObject.getString("id");
            String title = jsonObject.getString("title");
            String content = jsonObject.getString("content");
            String create_time = jsonObject.getString("created_at");
            String information_type = jsonObject.getString("information_type");
            String image_name = jsonObject.getString("image");
            String image_url = "http://192.168.0.230:3000/assets/" + image_name;

            info_map.put("id", info_id);
            info_map.put("title", title);
            info_map.put("content", content);
            info_map.put("info_type", information_type);
            info_map.put("create_time", create_time);
            info_map.put("image_url", image_url);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return info_map;
    }

    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        chooseFromAlbum = (Button) findViewById(R.id.choose_from_album);
        picture = (ImageView) findViewById(R.id.picture);
        Button publish = (Button) findViewById(R.id.publish);
        TitleText = (EditText) findViewById(R.id.infomation_title);
        ContentText = (EditText) findViewById(R.id.information_content);

        Information_types = (RadioGroup) findViewById(R.id.information_types);
        Information_types.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int radioButtonId = group.getCheckedRadioButtonId();
                RadioButton rb = (RadioButton)PublishActivity.this.findViewById(radioButtonId);
                Information_type = (String) rb.getText();
            }
        });

        chooseFromAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.GET_CONTENT");
                intent.setType("image/*");
                startActivityForResult(intent, CHOOSE_PHOTO);
            }
        });

        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Title = TitleText.getText().toString();
                Content = ContentText.getText().toString();
                sendRequestWithHttpURLConnection();
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    private void sendRequestWithHttpURLConnection() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("title", Title);
                    params.put("content", Content);
                    params.put("image", imageFileName);
                    params.put("information_type", Information_type);
                    StringBuilder resualt = HttpUtil.submitPostData(url, params, "UTF-8");
                    Message message = new Message();
                    message.what = SHOW_RESPONSE;
                    message.obj = resualt.toString();
                    handler.sendMessage(message);
                } catch (Exception e) {
                    System.out.println("send post request error!" + e);
                } finally {
                    System.out.println("send post request success!");
                }

            }
        }).start();
    }


    @Override
    protected void onActivityResult(int requestCode, int resualtCode, Intent data) {
        switch (requestCode) {
            case CROP_PHOTO:
                if (resualtCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        picture.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (resualtCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        Uri uri = data.getData();
                        imagePath = getImagePath(uri, null);
                        imageFileName = imagePath.substring(imagePath.lastIndexOf('/') + 1);
                        handleImageOnKitKat(data);
                        uploadFile();
                    } else {
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }

    private void uploadFile() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                String newName = imageFileName;
                String end = "\r\n";
                String twoHyphens = "--";
                String boundary = "*****";
                String params_url = "http://192.168.0.230:3000/informations/uploadfile?image_name=" + imageFileName;
                try {
                    URL url = new URL(params_url);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
          /* 允许Input、Output，不使用Cache */
                    con.setDoInput(true);
                    con.setDoOutput(true);
                    con.setUseCaches(false);
          /* 设置传送的method=POST */
                    con.setRequestMethod("POST");
          /* setRequestProperty */
                    con.setRequestProperty("Connection", "Keep-Alive");
                    con.setRequestProperty("Charset", "Base64");
                    con.setRequestProperty("Content-Type",
                            "multipart/form-data;boundary=" + boundary);
          /* 设置DataOutputStream */
                    DataOutputStream ds =
                            new DataOutputStream(con.getOutputStream());
                    ds.writeBytes(twoHyphens + boundary + end);
                    ds.writeBytes("Content-Disposition: form-data; " +
                            "name=\"file1\";filename=\"" +
                            newName + "\"" + end);
                    ds.writeBytes(end);
          /* 取得文件的FileInputStream */
                    FileInputStream fStream = new FileInputStream(imagePath);
          /* 设置每次写入1024bytes */
                    int bufferSize = 1024;
                    byte[] buffer = new byte[bufferSize];
                    int length = -1;
          /* 从文件读取数据至缓冲区 */
                    while ((length = fStream.read(buffer)) != -1) {
            /* 将资料写入DataOutputStream中 */
                        ds.write(buffer, 0, length);
                    }
                    ds.writeBytes(end);
                    ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
          /* close streams */
                    fStream.close();
                    ds.flush();
          /* 取得Response内容 */
                    InputStream is = con.getInputStream();
                    int ch;
                    StringBuffer b = new StringBuffer();
                    while ((ch = is.read()) != -1) {
                        b.append((char) ch);
                    }
                    System.out.println(fStream);
                    System.out.println("上传成功");
          /* 关闭DataOutputStream */
                    ds.close();
                } catch (Exception e) {
                    System.out.println("上传失败" + e);
                }
            }
        }).start();
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            imagePath = getImagePath(uri, null);
        }
        displayImage(imagePath);
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            picture.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "failed to get iamge", Toast.LENGTH_SHORT).show();
        }

    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Publish Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.hengtangan2025.androidapp/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Publish Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.hengtangan2025.androidapp/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
