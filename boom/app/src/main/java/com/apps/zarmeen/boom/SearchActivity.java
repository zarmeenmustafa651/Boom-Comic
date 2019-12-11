package com.apps.zarmeen.boom;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Locale;

import static com.apps.zarmeen.boom.DBhelper.TABLE_COMICS;

public class SearchActivity extends AppCompatActivity {

    EditText editsearch;
    ListViewAdapter adapter;
    ListView lv1;
    DBhelper mDbHelper = new DBhelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ArrayList<Item> searchResults1 = getmydata();
        lv1 = (ListView) findViewById(R.id.ListView01);
        adapter = new ListViewAdapter(this, searchResults1);
        lv1.setAdapter(adapter);
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = lv1.getItemAtPosition(position);
                Item fullObject = (Item) o;
                String[]whereArgs=new String[]{((Item) o).getText()};
                SQLiteDatabase db =mDbHelper.getReadableDatabase();
                int cid=0;
                Cursor cursor=db.rawQuery("Select CId from Comics where Title=?",whereArgs);
                if(cursor.moveToFirst()){
                    cid=cursor.getInt(cursor.getColumnIndex("CId"));
                }
                Intent intent = new Intent(SearchActivity.this, EpisodesActivity.class);
                intent.putExtra("CId",cid);
                startActivity(intent);
            }
        });
        final EditText editsearch = (EditText) findViewById(R.id.search);
        editsearch.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                if(lv1.getVisibility() !=View.VISIBLE ) {
                    lv1.setVisibility(View.VISIBLE);
                }
            }

            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub

            }


            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
                String text = editsearch.getText().toString().toLowerCase(Locale.getDefault());
                if(!text.equals("")){
                    adapter.filter(text);
                }

            }
        });
    }
    public ArrayList<Item> getmydata ()
    {
        ArrayList<Item> s = new ArrayList<>();
        final Cursor c = mDbHelper.getComics();
        c.moveToFirst();
        while (!c.isAfterLast()){
            String w = "";
            Bitmap img=null;
            if(c.getString(c.getColumnIndex("Title"))!=null)
            {
                w= c.getString(c.getColumnIndex("Title"));
            }
            if(c.getBlob(c.getColumnIndex("Image"))!=null)
            {
                byte[] comicImg = c.getBlob(c.getColumnIndex("Image"));
                ByteArrayInputStream imageStream = new ByteArrayInputStream(comicImg);
                img= BitmapFactory.decodeStream(imageStream);
           }
            Item w1= new Item(w,img);
            s.add(w1);
            c.moveToNext();
        }
        return s;
    }
}