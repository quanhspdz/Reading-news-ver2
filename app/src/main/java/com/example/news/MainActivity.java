package com.example.news;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lvNews;
    ArrayList<NewsClass> arrayNews;
    NewsAdapter adapter;
    ProgressDialog progressDialog;
    int numberOfNews = 0;

    //set url for news
    String GamekNews = "https://api.rss2json.com/v1/api.json?rss_url=https%3A%2F%2Fgamek.vn%2Ftrang-chu.rss",
                ThNienCNNews = "https://api.rss2json.com/v1/api.json?rss_url=https%3A%2F%2Fthanhnien.vn%2Frss%2Fcong-nghe.rss",
                CNETNews = "https://api.rss2json.com/v1/api.json?rss_url=https%3A%2F%2Fwww.cnet.com%2Frss%2Fnews%2F",
                VNExNews = "https://api.rss2json.com/v1/api.json?rss_url=https%3A%2F%2Fvnexpress.net%2Frss%2Ftin-moi-nhat.rss",
                CNETAnroidNews = "https://api.rss2json.com/v1/api.json?rss_url=https%3A%2F%2Fwww.cnet.com%2Frss%2Fandroid-update%2F",
                CNETGamingNews = "https://api.rss2json.com/v1/api.json?rss_url=https%3A%2F%2Fwww.cnet.com%2Frss%2Fgaming%2F";
    String currentNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvNews = findViewById(R.id.lvNews);
        arrayNews = new ArrayList<>();
        progressDialog = new ProgressDialog(MainActivity.this);

        adapter = new NewsAdapter(MainActivity.this, R.layout.each_list_item, arrayNews);

        //set the current new to CNET
        currentNews = CNETNews;
        setNewsUrl(currentNews);
        lvNews.setAdapter(adapter);

        //click an item of listview will send to full news activity
        lvNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, FullNewsActivity.class);
                intent.putExtra("link", arrayNews.get(i).getLink());
                startActivity(intent);
            }
        });


    }

    //set up menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_option, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //set menu's item clicked
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuRefresh: {
                setNewsUrl(currentNews);
                break;
            }
            case R.id.menuVNEX: {
                setNewsUrl(VNExNews);
                break;
            }
            case R.id.menuCNET: {
                setNewsUrl(CNETNews);
                break;
            }
            case R.id.menuGamek: {
                setNewsUrl(GamekNews);
                break;
            }
            case R.id.menuThNienCN: {
                setNewsUrl(ThNienCNNews);
                break;
            }
            case R.id.menuCNETAndroid: {
                setNewsUrl(CNETAnroidNews);
                break;
            }
            case R.id.menuCNETGamingNews: {
                setNewsUrl(CNETGamingNews);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    //function set News
    private void setNewsUrl(String url) {
        while (arrayNews.size() > 0) {
            arrayNews.remove(0);
        }
        new LoadNewsList().execute(url);
        currentNews = url;
    }

    //asyncTask to load news's title and thumbnail
    private class LoadNewsList extends AsyncTask<String, Void, String> {

        //show a progressDialog during the task
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Loading news... please wait...");
            progressDialog.show();
        }

        //load json
        @Override
        protected String doInBackground(String... strings) {
            StringBuilder stringBuilder = new StringBuilder();
            try {
                URL url = new URL(strings[0]);
                InputStream inputStream = url.openConnection().getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

        //set arrayNews and close progressDialog
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                //get a big oject in json
                JSONObject bigOject = new JSONObject(s);

                //get a array of items
                JSONArray bigArray = bigOject.getJSONArray("items");
                numberOfNews = bigArray.length();

                int i;
                String imglink = "", title = "", link = "";
                JSONObject eachItem = null;

                //set up arrayNews's item with bigArray's item
                for (i = 0; i < numberOfNews; i++) {
                    eachItem = bigArray.getJSONObject(i);

                    title = eachItem.getString("title");
                    link = eachItem.getString("link");
                    imglink = eachItem.getString("thumbnail");

                    arrayNews.add(i, new NewsClass(title, link, imglink, null));
                }
                adapter.notifyDataSetChanged();
                lvNews.setAdapter(adapter);
                progressDialog.dismiss();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}