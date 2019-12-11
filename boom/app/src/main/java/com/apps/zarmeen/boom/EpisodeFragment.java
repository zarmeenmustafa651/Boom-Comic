package com.apps.zarmeen.boom;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.google.firebase.auth.FirebaseAuth;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

public class EpisodeFragment extends Fragment {
    DBhelper mDbHelper;
    Activity a;
    SQLiteDatabase db;
    GridView gv;
    private FirebaseAuth auth;
    View view;
    public EpisodeFragment() {
        // Required empty public constructor
    }

     public static EpisodeFragment newInstance(String param1, String param2) {
        EpisodeFragment fragment = new EpisodeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(view==null){view=inflater.inflate(R.layout.fragment_episode, container,false); }
        else{ViewGroup parent = (ViewGroup) view.getParent(); parent.removeView(view);}
        db = mDbHelper.getWritableDatabase();
        int cid=getArguments().getInt("CId");
        String[] whereArgs = new String[] {String.valueOf(cid)};
        Item i;
        List<String> objects=new ArrayList<>();
        //Cursor c1=db.execSQL("SELECT * from Episodes where CId="+cid+" ");
        Cursor c1=db.rawQuery("SELECT * from Episodes where CId="+cid+"",null);
        if(c1.moveToFirst()){
            do{
                if(c1.getString(c1.getColumnIndex("ETitle"))!=null) {
                    objects.add(c1.getString(c1.getColumnIndex("ETitle")));
                }
            }while(c1.moveToNext());
            gv = view.findViewById(R.id.eg1);
            ArrayAdapter adapter=new ArrayAdapter<String>(a,android.R.layout.simple_list_item_1,objects);
            gv.setAdapter(adapter);
            gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> a, View v, int position, long id) {

                    int e=0;
                    Object o = gv.getItemAtPosition(position);
                    String []whereArgs =new String[]{(String) o};
                    Cursor c1=db.rawQuery("Select EId from Episodes where ETitle=?",whereArgs);
                    if(c1.moveToFirst()){
                        e=c1.getInt(c1.getColumnIndex("EId"));
                    }
                    Fragment fragment=new SlidesFragment();
                    Bundle eid=new Bundle();
                    eid.putInt("CId",getArguments().getInt("CId"));
                    eid.putInt("EId",e);
                    fragment.setArguments(eid);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.episodes_fragment, fragment);
                    fragmentTransaction.commit();
                    auth = FirebaseAuth.getInstance();
                    String Email= auth.getCurrentUser().getEmail();
                    mDbHelper.addBookmark(Email,e,getArguments().getInt("CId"));
//                Toast.makeText(lv1.getContext(), "You have chosen: " + " " + fullObject.getText(), Toast.LENGTH_LONG).show();
                }
            });
        }
        c1.close();
        return view;
    }
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> a, View v, int position, long id) {

                    int e=0;
                    Object o = gv.getItemAtPosition(position);
                    String []whereArgs =new String[]{(String) o};
                    Cursor c1=db.rawQuery("Select EId from Episodes where ETitle=?",whereArgs);
                    if(c1.moveToFirst()){
                        e=c1.getInt(c1.getColumnIndex("EId"));
                    }
                    Fragment fragment=new SlidesFragment();
                    Bundle eid=new Bundle();
                    eid.putInt("CId",getArguments().getInt("CId"));
                    eid.putInt("EId",e);
                    fragment.setArguments(eid);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.slides_fragment, fragment);
                    fragmentTransaction.commit();
                    auth = FirebaseAuth.getInstance();
                    String Email= auth.getCurrentUser().getEmail();
                    mDbHelper.addBookmark(Email,e,getArguments().getInt("CId"));
//                Toast.makeText(lv1.getContext(), "You have chosen: " + " " + fullObject.getText(), Toast.LENGTH_LONG).show();
                }
            });
        }else if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> a, View v, int position, long id) {

                    int e=0;
                    Object o = gv.getItemAtPosition(position);
                    String []whereArgs =new String[]{(String) o};
                    Cursor c1=db.rawQuery("Select EId from Episodes where ETitle=?",whereArgs);
                    if(c1.moveToFirst()){
                        e=c1.getInt(c1.getColumnIndex("EId"));
                    }
                    Fragment fragment=new SlidesFragment();
                    Bundle eid=new Bundle();
                    eid.putInt("CId",getArguments().getInt("CId"));
                    eid.putInt("EId",e);
                    fragment.setArguments(eid);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.episodes_fragment, fragment);
                    fragmentTransaction.commit();
                    auth = FirebaseAuth.getInstance();
                    String Email= auth.getCurrentUser().getEmail();
                    mDbHelper.addBookmark(Email,e,getArguments().getInt("CId"));
//                Toast.makeText(lv1.getContext(), "You have chosen: " + " " + fullObject.getText(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        a=activity;
        mDbHelper = new DBhelper(activity);
    }

}
