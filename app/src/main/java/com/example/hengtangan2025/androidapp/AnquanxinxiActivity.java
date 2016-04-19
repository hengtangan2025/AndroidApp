package com.example.hengtangan2025.androidapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hengtangan2025 on 2016/3/15.
 */
public class AnquanxinxiActivity extends Activity{
    private  static final int SHOW_RESPONSE = 0;
    private  static final int SHOW_INFORMATION = 1;
    private  static List<Map<String, String>> info_list = null;
    private  static Map<String,String> info_map = null;
    private  static final String get_all = "http://192.168.0.230:3000/api/informations/anquanxinxi";
    boolean locked;

    private Handler handler = new Handler() {

        public void handleMessage (Message msg) {
            switch (msg.what) {
                case SHOW_RESPONSE:
                    String response = (String) msg.obj;
                    info_list = setData(response);


                    ListView anquanxinxi_list = (ListView)findViewById(R.id.anquanxinxi_list);

                    BaseAdapter info_listview = new BaseAdapter() {

                        @Override
                        public int getCount() {
                            return info_list.size();
                        }

                        @Override
                        public Object getItem(int position) {
                            return info_list.get(position);
                        }

                        @Override
                        public long getItemId(int position) {
                            return position;
                        }

                        @Override
                        public View getView(int position, View convertView, ViewGroup parent) {
                            View view = null;
                            if (convertView == null) {
                                view = LayoutInflater.from(AnquanxinxiActivity.this).inflate(R.layout.info_listview, null);
                            } else {
                                view = convertView;
                            }
                            TextView textView1 = (TextView) view.findViewById(R.id.title);
                            TextView textView2 = (TextView) view.findViewById(R.id.info_type);
                            TextView textView3 = (TextView) view.findViewById(R.id.create_time);
                            final ImageView imageView = (ImageView) view.findViewById(R.id.image);
                            textView1.setText(info_list.get(position).get("title"));
                            textView2.setText(info_list.get(position).get("create_time"));
                            textView3.setText(info_list.get(position).get("info_type"));
                            DownImage downImage = new DownImage(info_list.get(position).get("image_url"));
                            downImage.loadImage(new DownImage.ImageCallBack() {

                                @Override
                                public void getDrawable(Drawable drawable) {
                                    // TODO Auto-generated method stub
                                    imageView.setImageDrawable(drawable);
                                }
                            });
                            return view;
                        }
                    };

                    anquanxinxi_list.setAdapter(info_listview);
                    break;
                case SHOW_INFORMATION:
                    String info_response = (String) msg.obj;
                    info_map = getData(info_response);
                    String title = info_map.get("title");
                    String content = info_map.get("content");
                    String image = info_map.get("image_url");
                    Intent intent = new Intent(AnquanxinxiActivity.this, InformationShowActivity.class);
                    intent.putExtra("title", title);
                    intent.putExtra("content", content);
                    intent.putExtra("image_url", image);
                    startActivity(intent);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
        setContentView(R.layout.anquanxinxi);

        sendRequestWithURLConnection(get_all, SHOW_RESPONSE);

        ListView anquanxinxi_list = (ListView)findViewById(R.id.anquanxinxi_list);

        anquanxinxi_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String info_id = info_list.get(position).get("info_id");
                String get_info = "http://192.168.0.230:3000/informations/" + info_id;
                sendRequestWithURLConnection(get_info, SHOW_INFORMATION);
            }
        });

        Button Button1 = (Button) findViewById(R.id.button_1);
        Button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnquanxinxiActivity.this, PublishActivity.class);
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
        Intent intent = new Intent(AnquanxinxiActivity.this, JiuyexinxiActivity.class);
        startActivity(intent);
    }

    private void showGongyougushi() {
        Intent intent = new Intent(AnquanxinxiActivity.this, GongyougushiActivity.class);
        startActivity(intent);
    }

    private void showJinengpeixun() {
        Intent intent = new Intent(AnquanxinxiActivity.this, JinengpeixunActivity.class);
        startActivity(intent);
    }

    private void showAnquanxinxi() {
        Intent intent = new Intent(AnquanxinxiActivity.this, AnquanxinxiActivity.class);
        startActivity(intent);
    }

    private void showCaiwugongkai() {
        Intent intent = new Intent(AnquanxinxiActivity.this, CaiwugongkaiActivity.class);
        startActivity(intent);
    }

    private void showGuanyuwomen() {
        Intent intent = new Intent(AnquanxinxiActivity.this, GuanyuwomenActivity.class);
        startActivity(intent);
    }

    private void showMujuanhuodong() {
        Intent intent = new Intent(AnquanxinxiActivity.this, MujuanhuodongActivity.class);
        startActivity(intent);
    }

    private void showMujuanchangyi() {
        Intent intent = new Intent(AnquanxinxiActivity.this, MujuanchangyiActivity.class);
        startActivity(intent);
    }


    private void sendRequestWithURLConnection(final String string, final int a) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(string);
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
                    message.what = a;
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

    public Map<String, String> getData(String response) {
        Map<String,String> info_map = new HashMap<String, String>();
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
            info_map.put("info_type",  information_type);
            info_map.put("create_time", create_time);
            info_map.put("image_url", image_url);

        } catch (Exception e){
            e.printStackTrace();
        }
        return info_map;
    };

    public List<Map<String, String>> setData(String response) {
        List<Map<String, String>> list = new ArrayList<Map<String,String>>();
        try {
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++ ) {
                Map<String, String> info_map = new HashMap<String, String>();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                JSONObject a = jsonObject.getJSONObject("_id");
                String info_id = a.getString("$oid");
                String title = jsonObject.getString("title");
                String create_time = jsonObject.getString("created_at");
                String information_type = jsonObject.getString("information_type");
                String image_name = jsonObject.getString("image");
                String image_url = "http://192.168.0.230:3000/assets/" + image_name;


                info_map.put("info_id", info_id);
                info_map.put("title", title);
                info_map.put("info_type",  information_type);
                info_map.put("create_time", create_time);
                info_map.put("image_url", image_url);

                list.add(info_map);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
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
