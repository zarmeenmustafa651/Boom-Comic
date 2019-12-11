package com.apps.zarmeen.boom;

import android.graphics.Bitmap;

public class Item
{
    private Bitmap mImageDrawable;
    private String txt = "";

    public Item(String txt, Bitmap img) {
        this.txt = txt;
        this.mImageDrawable=img;

    }

    public Bitmap getmImageDrawable() {
        return mImageDrawable;
    }
    public void setmImageDrawable(Bitmap mImageDrawable) {
        this.mImageDrawable = mImageDrawable;
    }
    public void setText(String name) {
        this.txt = name;
    }
    public String getText() {
        return this.txt;
    }

}
