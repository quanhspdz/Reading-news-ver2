package com.example.news;

import android.content.AsyncQueryHandler;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class NewsAdapter extends BaseAdapter {
    Context context;
    int layout;
    ArrayList<NewsClass> arrayNews;

    public NewsAdapter(Context context, int layout, ArrayList<NewsClass> arrayNews) {
        this.context = context;
        this.layout = layout;
        this.arrayNews = arrayNews;
    }

    @Override
    public int getCount() {
        return arrayNews.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = new ViewHolder();
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);

            holder.imgNews = view.findViewById(R.id.imgThumb);
            holder.txtNews = view.findViewById(R.id.txtTitle);

            view.setTag(holder);
        }
        else {
            holder = (ViewHolder) view.getTag();
        }

        NewsClass news = arrayNews.get(i);
        holder.txtNews.setText(news.getTitle());

        if (news.getImgLink().isEmpty()) {
            holder.imgNews.setImageResource(R.drawable.news_icon);
        }
        else Picasso.with(view.getContext()).load(news.getImgLink()).into(holder.imgNews);

        return view;
    }
    private class ViewHolder {
        ImageView imgNews;
        TextView txtNews;
    }
}
