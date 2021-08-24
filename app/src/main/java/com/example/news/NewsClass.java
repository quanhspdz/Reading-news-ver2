package com.example.news;

import android.graphics.Bitmap;

public class NewsClass {
    String title;
    String link;
    String imgLink;
    Bitmap bitmap;

    public NewsClass(String title, String link, String imgLink, Bitmap bitmap) {
        this.title = title;
        this.link = link;
        this.imgLink = imgLink;
        this.bitmap = bitmap;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}