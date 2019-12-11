package com.apps.zarmeen.boom;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

public class DBhelper extends SQLiteOpenHelper {
    public boolean change=false;
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "BoomComic.db";
    public static final String TABLE_COMICS="Comics";
    public DBhelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db){

        String sql = "CREATE TABLE Comics (CId INTEGER PRIMARY KEY AUTOINCREMENT,Title TEXT UNIQUE,Genre TEXT,Featuring TEXT, Image BLOB)\n" ;
        db.execSQL(sql);
        sql = "CREATE TABLE Episodes (EId INTEGER PRIMARY KEY AUTOINCREMENT, CId INTEGER ,ETitle TEXT UNIQUE,FOREIGN KEY(CId) REFERENCES Comics(CId) )";
        db.execSQL(sql);
        sql = "CREATE TABLE Slides (SId INTEGER PRIMARY KEY AUTOINCREMENT, EId INTEGER, CId INTEGER ,Slide BLOB,FOREIGN KEY(CId) REFERENCES Comics(CId),FOREIGN KEY(EId) REFERENCES Episodes(EId) )";
        db.execSQL(sql);
        sql = "CREATE TABLE Users (UId INTEGER PRIMARY KEY AUTOINCREMENT, UEmail TEXT UNIQUE ,UPassword TEXT )";
        db.execSQL(sql);
        sql = "CREATE TABLE Bookmark (BId INTEGER PRIMARY KEY AUTOINCREMENT,UId INTEGER,EId INTEGER,CId INTEGER,FOREIGN KEY(UId) REFERENCES Users(UId),FOREIGN KEY(CId) REFERENCES Comics(CId),FOREIGN KEY(EId) REFERENCES Episodes(EId))";
        db.execSQL(sql);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Comics");
        db.execSQL("DROP TABLE IF EXISTS Episodes");
        db.execSQL("DROP TABLE IF EXISTS Slides");
        db.execSQL("DROP TABLE IF EXISTS Users");
        db.execSQL("DROP TABLE IF EXISTS Bookmark");
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db,oldVersion,newVersion);
    }
    public ArrayList<Item> getData(String[] whereArgs) {
             String title ="";
             Bitmap img =null;
             ArrayList<Item> comics = new ArrayList<>();
             SQLiteDatabase db= getWritableDatabase();

            Cursor c1 =db.rawQuery("SELECT Image, Title FROM Comics Where Featuring=?",whereArgs);
                if(c1.moveToFirst()){
                    do{
                        if(c1.getString(c1.getColumnIndex("Image"))!=null) {
                            byte[] comicImg = c1.getBlob(c1.getColumnIndex("Image"));
                            ByteArrayInputStream imageStream = new ByteArrayInputStream(comicImg);
                            img= BitmapFactory.decodeStream(imageStream);
                        }
                        if(c1.getString(c1.getColumnIndex("Title"))!=null)
                        {
                            title = c1.getString(c1.getColumnIndex("Title"));
                        }
                        Item w1= new Item(title,img);
                        comics.add(w1);

                    }while(c1.moveToNext());
                }
                c1.close();
                return comics;
       }
    public Cursor getComics(){
        SQLiteDatabase db= getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_COMICS + " WHERE 1";
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }
	public ArrayList<comics> getAllComics(){
           SQLiteDatabase db= getWritableDatabase();
            String genre=null,feature=null,title=null,i=null;
            Bitmap img=null;
            int id=0;
            ArrayList<comics> comics= new ArrayList<>();
           String query = "SELECT * FROM " + TABLE_COMICS + " WHERE 1";
           Cursor c1 = db.rawQuery(query, null);
            if(c1.moveToFirst()){
                do{
                    if(c1.getString(c1.getColumnIndex("CId"))!=null)
                    {
                        String sid= c1.getString(c1.getColumnIndex("CId"));
                        id=Integer.parseInt(sid);
                    }
                    if(c1.getBlob(c1.getColumnIndex("Image"))!=null) {
                        byte[] comicImg = c1.getBlob(c1.getColumnIndex("Image"));
                        ByteArrayInputStream imageStream = new ByteArrayInputStream(comicImg);
                        img= BitmapFactory.decodeStream(imageStream);
                         i = img.toString();

                    }
                    if(c1.getString(c1.getColumnIndex("Title"))!=null)
                    {
                        title = c1.getString(c1.getColumnIndex("Title"));
                    }
                    if(c1.getString(c1.getColumnIndex("Genre"))!=null)
                    {
                        genre = c1.getString(c1.getColumnIndex("Genre"));
                    }
                    if(c1.getString(c1.getColumnIndex("Featuring"))!=null)
                    {
                        feature = c1.getString(c1.getColumnIndex("Featuring"));
                    }
                    comics w1= new comics(id,title,genre,feature,i);
                    comics.add(w1);

                }while(c1.moveToNext());
            }
            c1.close();
           return comics;
       }
    public ArrayList<episodes> getAllEpisodes(){
        SQLiteDatabase db= getWritableDatabase();
        int epId=0,comId=0;
        String EpiTitle=null;
        ArrayList<episodes> episode= new ArrayList<>();
        String query = "SELECT * FROM " +"Episodes" + " WHERE 1";
        Cursor c1 = db.rawQuery(query, null);
        if(c1.moveToFirst()){
            do{
                if(c1.getString(c1.getColumnIndex("EId"))!=null)
                {
                    String sid= c1.getString(c1.getColumnIndex("EId"));
                    epId=Integer.parseInt(sid);
                }
                if(c1.getString(c1.getColumnIndex("CId"))!=null)
                {
                    String sid= c1.getString(c1.getColumnIndex("CId"));
                    comId=Integer.parseInt(sid);
                }
                if(c1.getString(c1.getColumnIndex("ETitle"))!=null)
                {
                    EpiTitle = c1.getString(c1.getColumnIndex("ETitle"));
                }

                episodes w1= new episodes(epId,comId,EpiTitle);
                episode.add(w1);

            }while(c1.moveToNext());
        }
        c1.close();
        return episode;
    }
    public ArrayList<slides> getAllSlides(){
        SQLiteDatabase db= getWritableDatabase();

        String i=null;
        Bitmap img=null;
        int sid=0,cid=0,eid=0;
        ArrayList<slides> arr= new ArrayList<>();
        String query = "SELECT * FROM " + "Slides" + " WHERE 1";
        Cursor c1 = db.rawQuery(query, null);
        if(c1.moveToFirst()){
            do{
                if(c1.getString(c1.getColumnIndex("SId"))!=null)
                {
                    String s= c1.getString(c1.getColumnIndex("SId"));
                    sid=Integer.parseInt(s);
                }
                if(c1.getString(c1.getColumnIndex("EId"))!=null)
                {
                    String s= c1.getString(c1.getColumnIndex("EId"));
                    eid=Integer.parseInt(s);
                }
                if(c1.getString(c1.getColumnIndex("CId"))!=null)
                {
                    String s= c1.getString(c1.getColumnIndex("CId"));
                    cid=Integer.parseInt(s);
                }
                if(c1.getBlob(c1.getColumnIndex("Slide"))!=null) {
                    byte[] comicImg = c1.getBlob(c1.getColumnIndex("Slide"));
                    ByteArrayInputStream imageStream = new ByteArrayInputStream(comicImg);
                    img= BitmapFactory.decodeStream(imageStream);
                    i = img.toString();

                }

                slides w1= new slides(sid,eid,cid,i);
                arr.add(w1);

            }while(c1.moveToNext());
        }
        c1.close();
        return arr;
    }
    public ArrayList<bookmark> getAllBookmarks(){
        SQLiteDatabase db= getWritableDatabase();

        int bid=0,uid=0,cid=0,eid=0;
        ArrayList<bookmark> arr= new ArrayList<>();
        String query = "SELECT * FROM " + "Slides" + " WHERE 1";
        Cursor c1 = db.rawQuery(query, null);
        if(c1.moveToFirst()){
            do{
                if(c1.getString(c1.getColumnIndex("BId"))!=null)
                {
                    String s= c1.getString(c1.getColumnIndex("BId"));
                    bid=Integer.parseInt(s);
                }
                if(c1.getString(c1.getColumnIndex("EId"))!=null)
                {
                    String s= c1.getString(c1.getColumnIndex("EId"));
                    eid=Integer.parseInt(s);
                }
                if(c1.getString(c1.getColumnIndex("CId"))!=null)
                {
                    String s= c1.getString(c1.getColumnIndex("CId"));
                    cid=Integer.parseInt(s);
                }
                if(c1.getString(c1.getColumnIndex("UId"))!=null)
                {
                    String s= c1.getString(c1.getColumnIndex("UId"));
                    uid=Integer.parseInt(s);
                }

                bookmark w1= new bookmark(bid,uid,cid,eid);
                arr.add(w1);

            }while(c1.moveToNext());
        }
        c1.close();
        return arr;
    }
    public void addUser (String email,String psswrd){

        ContentValues values = new ContentValues();
        values.put("UEmail", email );
        values.put("UPassword",psswrd);
        SQLiteDatabase db= getWritableDatabase();
        db.insertWithOnConflict("Users",null,values,SQLiteDatabase.CONFLICT_REPLACE);
        Log.e("dbuserdata", "adding user data in db");
        db.close();

    }
    public void addComicSer (String t,String g,String f,String img){
        byte [] encodeByte= Base64.decode(img,Base64.DEFAULT);
        Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress( Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bitmapData = stream.toByteArray();
        SQLiteDatabase db= getWritableDatabase();
        ContentValues insertValues = new ContentValues();
        insertValues.put("Title", t);
        insertValues.put("Genre", g);
        insertValues.put("Featuring", f);
        insertValues.put("Image", bitmapData);
        db.insert("Comics", null, insertValues);
    }
    public void addEpisodeSer (int c,String t){
        SQLiteDatabase db= getWritableDatabase();
        ContentValues insertValues = new ContentValues();
        insertValues.put("ETitle", t);
        insertValues.put("CId",c);
        db.insert("Episodes", null, insertValues);
    }
    public void addSlideSer (int e,int c,String img){
        byte [] encodeByte= Base64.decode(img,Base64.DEFAULT);
        Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress( Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bitmapData = stream.toByteArray();
        SQLiteDatabase db= getWritableDatabase();
        ContentValues insertValues = new ContentValues();
        insertValues.put("EId", e);
        insertValues.put("CId", c);
        insertValues.put("Slide", bitmapData);
        db.insert("Slides", null, insertValues);
    }
    public void addBookmarkSer(int u,int c,int e){
        SQLiteDatabase db= getWritableDatabase();
        ContentValues insertValues = new ContentValues();
        insertValues.put("UId", u);
        insertValues.put("CId",c);
        insertValues.put("EId",e);
        db.insert("Bookmark", null, insertValues);
    }
    public void addBookmark(String email,int eid,int cid){
        SQLiteDatabase db= getReadableDatabase();
        String[]whereArgs=new String[]{email};
        Cursor cursor=db.rawQuery("Select UId from Users where UEmail=?" , whereArgs);
        int uid=0;
        if(cursor.moveToFirst()){
            uid=cursor.getInt(cursor.getColumnIndex("UId"));
        }
        db= getWritableDatabase();
        ContentValues insertValues = new ContentValues();
        insertValues.put("UId", uid);
        insertValues.put("EId", eid);
        insertValues.put("CId", cid);
        db.insert("Bookmark", null, insertValues);
    }
}
