package com.apps.zarmeen.boom;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

public class DailyFeatureFragment extends Fragment {
    DBhelper mDbHelper;
    SQLiteDatabase db;
    View view;
    Activity a;
    GridViewAdapter adapter;
    GridView gv;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(view==null)
        {
            view=inflater.inflate(R.layout.fragment_daily_feature, container,false);
        }
        else
        {
            ViewGroup parent = (ViewGroup) view.getParent();
            parent.removeView(view);
        }
        getAllData();
        return view;
    }
    private void getAllData(){
        db = mDbHelper.getWritableDatabase();
        String[] whereArgs = new String[] {"Monday"};
        Item i;
        List<Item> objects=new ArrayList<>();
        Cursor c1=db.rawQuery("SELECT * from Comics where Featuring=?",whereArgs);
        if(c1.moveToFirst()){
            do{
                if(c1.getBlob(c1.getColumnIndex("Image"))!=null&&c1.getString(c1.getColumnIndex("Title"))!=null) {
                    byte[] comicImg = c1.getBlob(c1.getColumnIndex("Image"));
                    ByteArrayInputStream imageStream = new ByteArrayInputStream(comicImg);
                    i=new Item(c1.getString(c1.getColumnIndex("Title")), BitmapFactory.decodeStream(imageStream));
                    objects.add(i);
                }
            }while(c1.moveToNext());
            gv = view.findViewById(R.id.g1);
            adapter=new GridViewAdapter(a,R.layout.grid_item,objects);
            gv.setAdapter(adapter);
        }
        c1.close();
        objects=new ArrayList<>();
        whereArgs = new String[] {"Tuesday"};
        c1=db.rawQuery("SELECT * from Comics where Featuring=?",whereArgs);
        if(c1.moveToFirst()){
            do{
                if(c1.getBlob(c1.getColumnIndex("Image"))!=null&&c1.getString(c1.getColumnIndex("Title"))!=null) {
                    byte[] comicImg = c1.getBlob(c1.getColumnIndex("Image"));
                    ByteArrayInputStream imageStream = new ByteArrayInputStream(comicImg);
                    i=new Item(c1.getString(c1.getColumnIndex("Title")), BitmapFactory.decodeStream(imageStream));
                    objects.add(i);
                }
            }while(c1.moveToNext());
            gv = view.findViewById(R.id.g2);
            adapter=new GridViewAdapter(a,R.layout.grid_item,objects);
            gv.setAdapter(adapter);
        }
        c1.close();
        objects=new ArrayList<>();
        whereArgs = new String[] {"Wednesday"};
        c1=db.rawQuery("SELECT * from Comics where Featuring=?",whereArgs);
        if(c1.moveToFirst()){
            do{
                if(c1.getBlob(c1.getColumnIndex("Image"))!=null&&c1.getString(c1.getColumnIndex("Title"))!=null) {
                    byte[] comicImg = c1.getBlob(c1.getColumnIndex("Image"));
                    ByteArrayInputStream imageStream = new ByteArrayInputStream(comicImg);
                    i=new Item(c1.getString(c1.getColumnIndex("Title")), BitmapFactory.decodeStream(imageStream));
                    objects.add(i);
                }
            }while(c1.moveToNext());
            gv = view.findViewById(R.id.g2);
            adapter=new GridViewAdapter(a,R.layout.grid_item,objects);
            gv.setAdapter(adapter);
        }
        c1.close();
        objects=new ArrayList<>();
        whereArgs = new String[] {"Thursday"};
        c1=db.rawQuery("SELECT * from Comics where Featuring=?",whereArgs);
        if(c1.moveToFirst()){
            do{
                if(c1.getBlob(c1.getColumnIndex("Image"))!=null&&c1.getString(c1.getColumnIndex("Title"))!=null) {
                    byte[] comicImg = c1.getBlob(c1.getColumnIndex("Image"));
                    ByteArrayInputStream imageStream = new ByteArrayInputStream(comicImg);
                    i=new Item(c1.getString(c1.getColumnIndex("Title")), BitmapFactory.decodeStream(imageStream));
                    objects.add(i);
                }
            }while(c1.moveToNext());
            gv = view.findViewById(R.id.g4);
            adapter=new GridViewAdapter(a,R.layout.grid_item,objects);
            gv.setAdapter(adapter);
        }
        c1.close();
        objects=new ArrayList<>();
        whereArgs = new String[] {"Friday"};
        c1=db.rawQuery("SELECT * from Comics where Featuring=?",whereArgs);
        if(c1.moveToFirst()){
            do{
                if(c1.getBlob(c1.getColumnIndex("Image"))!=null&&c1.getString(c1.getColumnIndex("Title"))!=null) {
                    byte[] comicImg = c1.getBlob(c1.getColumnIndex("Image"));
                    ByteArrayInputStream imageStream = new ByteArrayInputStream(comicImg);
                    i=new Item(c1.getString(c1.getColumnIndex("Title")), BitmapFactory.decodeStream(imageStream));
                    objects.add(i);
                }
            }while(c1.moveToNext());
            gv = view.findViewById(R.id.g5);
            adapter=new GridViewAdapter(a,R.layout.grid_item,objects);
            gv.setAdapter(adapter);
        }
        c1.close();
        objects=new ArrayList<>();
        whereArgs = new String[] {"Saturday"};
        c1=db.rawQuery("SELECT * from Comics where Featuring=?",whereArgs);
        if(c1.moveToFirst()){
            do{
                if(c1.getBlob(c1.getColumnIndex("Image"))!=null&&c1.getString(c1.getColumnIndex("Title"))!=null) {
                    byte[] comicImg = c1.getBlob(c1.getColumnIndex("Image"));
                    ByteArrayInputStream imageStream = new ByteArrayInputStream(comicImg);
                    i=new Item(c1.getString(c1.getColumnIndex("Title")), BitmapFactory.decodeStream(imageStream));
                    objects.add(i);
                }
            }while(c1.moveToNext());
            gv = view.findViewById(R.id.g6);
            adapter=new GridViewAdapter(a,R.layout.grid_item,objects);
            gv.setAdapter(adapter);
        }
        c1.close();
        objects=new ArrayList<>();
        whereArgs = new String[] {"Sunday"};
        c1=db.rawQuery("SELECT * from Comics where Featuring=?",whereArgs);
        if(c1.moveToFirst()){
            do{
                if(c1.getBlob(c1.getColumnIndex("Image"))!=null&&c1.getString(c1.getColumnIndex("Title"))!=null) {
                    byte[] comicImg = c1.getBlob(c1.getColumnIndex("Image"));
                    ByteArrayInputStream imageStream = new ByteArrayInputStream(comicImg);
                    i=new Item(c1.getString(c1.getColumnIndex("Title")), BitmapFactory.decodeStream(imageStream));
                    objects.add(i);
                }
            }while(c1.moveToNext());
            gv = view.findViewById(R.id.g7);
            adapter=new GridViewAdapter(a,R.layout.grid_item,objects);
            gv.setAdapter(adapter);
        }
        c1.close();
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        a=activity;
        mDbHelper = new DBhelper(activity);
    }
}
