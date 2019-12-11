package com.apps.zarmeen.boom;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

public class GenreFragment extends Fragment {
    DBhelper mDbHelper;
    SQLiteDatabase db;
    View view;
    Activity a;
    GridViewAdapter adapter;
    GridView gv;
    String[] whereArgs ;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(view==null)
        {
            view=inflater.inflate(R.layout.fragment_genre, container,false);
        }
        else
        {
            ViewGroup parent = (ViewGroup) view.getParent();
            parent.removeView(view);
        }
        Spinner dropdown2 = view.findViewById(R.id.gs);
        String[] items2 = new String[]{"Comedy", "Horror", "Slice of life"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(a, android.R.layout.simple_spinner_dropdown_item, items2);
        dropdown2.setAdapter(adapter2);
        dropdown2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    whereArgs=new String[] {"Comedy"};
                }

                else
                    whereArgs = new String[] {(String)parent.getItemAtPosition(position)};
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        getAllData();
        return view;
    }
    private void getAllData(){
        db = mDbHelper.getWritableDatabase();
        Item i;
        List<Item> objects=new ArrayList<>();
        Cursor c1=db.rawQuery("SELECT * from Comics where Genre=?",whereArgs);
        if(c1.moveToFirst()){
            do{
                if(c1.getBlob(c1.getColumnIndex("Image"))!=null&&c1.getString(c1.getColumnIndex("Title"))!=null) {
                    byte[] comicImg = c1.getBlob(c1.getColumnIndex("Image"));
                    ByteArrayInputStream imageStream = new ByteArrayInputStream(comicImg);
                    i=new Item(c1.getString(c1.getColumnIndex("Title")), BitmapFactory.decodeStream(imageStream));
                    objects.add(i);
                }
            }while(c1.moveToNext());
            gv = view.findViewById(R.id.gg1);
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
