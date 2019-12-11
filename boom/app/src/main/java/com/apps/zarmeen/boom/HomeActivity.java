package com.apps.zarmeen.boom;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class HomeActivity extends AppCompatActivity {
    GridViewAdapter adapter;
    Intent newIntent;
    GridView gv;
    DBhelper mDbHelper = new DBhelper(this);
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar myToolbar = findViewById(R.id.tToolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setLogo(R.drawable.boom);
        newIntent =  new Intent(this,RetrieveDataService.class);
        startService(newIntent);
        Fragment fragment=new DailyFeatureFragment();
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.home_fragment, fragment);
        fragmentTransaction.commit();


//        List<Item> objects=getAllData();
//
//        gv = (GridView) findViewById(R.id.g1);
////        gv=fragment.
//        adapter=new GridViewAdapter(this,R.layout.grid_item,objects);
//        gv.setAdapter(adapter);
    }
    public void GOTO(View view)
    {
        Intent intent =  new Intent(this,DailyFeatureFragment.class);
        startActivity(intent);
    }

//    private ArrayList<Item> getComics() {
//
//        String[] whereArgs = new String[] {"MONDAY"};
//        Cursor c1 =db.rawQuery("SELECT Image, Title FROM Comics Where Featuring=?",whereArgs);
//        if(c1.moveToFirst()){
//            do{
//                byte[] comicImg = c1.getBlob(c1.getColumnIndex("Image"));
//                String title = c1.getString(c1.getColumnIndex("Title"));
//            }while(c1.moveToNext());
//        }
//        c1.close();return null;
//    }

    private List<Item> getAllData()
    {
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
                    i=new Item(c1.getString(c1.getColumnIndex("Title")),BitmapFactory.decodeStream(imageStream));
                    objects.add(i);
                }
            }while(c1.moveToNext());
//            gv = findViewById(R.id.g1);
//            adapter=new GridViewAdapter(this,R.layout.grid_item,objects);
//            gv.setAdapter(adapter);
        }
        c1.close();
return objects;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.item1:
                SearchMenu();
                return true;
//            case R.id.item3:
//                SubscribeMenu();
//                return true;
            case R.id.item4:
                SettingMenu();
                return true;
            case R.id.item5:
                GenreMenu();
                return true;
            case R.id.item6:
                FeatureMenu();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    protected void SearchMenu(){
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }
    protected void SubscribeMenu(){

    }
    protected void SettingMenu(){
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }
    protected void GenreMenu(){
        Fragment fragment=new GenreFragment();
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.home_fragment, fragment);
        fragmentTransaction.commit();
    }
    protected void FeatureMenu(){
        Fragment fragment=new DailyFeatureFragment();
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.home_fragment, fragment);
        fragmentTransaction.commit();
    }
}
