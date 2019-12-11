package com.apps.zarmeen.boom;

import android.graphics.Bitmap;

import java.sql.Blob;

public class comics
{
    int id;
    String title;
    String genre;
    String feature;
    String image;

    comics(){}
    comics( int i,String t,String g,String f,String img)
    {
        id=i;
        title=t;
        genre=g;
        feature=f;
        image=img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }





}
