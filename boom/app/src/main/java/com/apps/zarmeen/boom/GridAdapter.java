package com.apps.zarmeen.boom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridAdapter extends BaseAdapter {
    private Integer[] petImage={
            R.drawable.blue_chair,
            R.drawable.cluster_fudge,
            R.drawable.hapi_buni,
            R.drawable.lars,
            R.drawable.library_ghost,
            R.drawable.randomphilia,
            R.drawable.safely_endangered,
            R.drawable.saphie_one_eyed_cat
    };
    private Context context;
    private LayoutInflater thisInflater;
    private String[] imageLabels;
    public GridAdapter(Context con, String[ ] labs){
        super();
        this.context = con;
        this.thisInflater = LayoutInflater.from(con);
        this.imageLabels = labs;
    }
    @Override
    public int getCount() {

        return petImage.length;

    }
    @Override
    public Object getItem(int position) {

        return position;

    }

    @Override
    public long getItemId(int position) {

        return position;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView=thisInflater.inflate(R.layout.grid_item,parent,false);
            TextView textHeading =(TextView) convertView.findViewById(R.id.mytext);
            ImageView thumbnailImage =(ImageView) convertView.findViewById(R.id.imgbtn);
            textHeading.setText(imageLabels[position]);
            thumbnailImage.setImageResource(petImage[position]);
        }
        return convertView;
    }
}
