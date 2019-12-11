package com.apps.zarmeen.boom;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
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

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;


public class SlidesFragment extends Fragment {
    DBhelper mDbHelper;
    Activity a;
    SQLiteDatabase db;
    GridView gv;
    private FirebaseAuth auth;
    View view;
    private OnFragmentInteractionListener mListener;

    public SlidesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SlidesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SlidesFragment newInstance(String param1, String param2) {
        SlidesFragment fragment = new SlidesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
             }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       if(view==null){view=inflater.inflate(R.layout.fragment_slides, container,false); }
        else{ViewGroup parent = (ViewGroup) view.getParent(); parent.removeView(view);}
        db = mDbHelper.getWritableDatabase();
        int cid=getArguments().getInt("CId");
        int eid=getArguments().getInt("EId");
        Item i;
        ArrayList<byte[]> objects=new ArrayList<>();
        Cursor c1=db.rawQuery("SELECT Slide from Slides where CId="+cid+" and EId="+eid+"",null);
            if(c1.moveToFirst()){
            do{
                if(c1.getBlob(c1.getColumnIndex("Slide"))!=null) {
                    objects.add(c1.getBlob(c1.getColumnIndex("Slide")));
                }
            }while(c1.moveToNext());
            gv = view.findViewById(R.id.slidegrid);
            SlidesAdapter adapter=new SlidesAdapter(a,objects);
            gv.setAdapter(adapter);
        }
            c1.close();
            return view;
        }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        a=activity;
        mDbHelper = new DBhelper(activity);
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
