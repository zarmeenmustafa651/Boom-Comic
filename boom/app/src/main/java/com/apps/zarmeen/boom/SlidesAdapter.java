package com.apps.zarmeen.boom;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class SlidesAdapter extends BaseAdapter {
    private ArrayList<byte[]> searchArrayList;
    ArrayList<byte[]> arraylist;
    private LayoutInflater mInflater;
    public SlidesAdapter(Context context, ArrayList<byte[]> results) {
        super();
        searchArrayList = results;
        mInflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<byte[]>();
        this.arraylist.addAll(results);
    }

    public int getCount() {
        return searchArrayList.size();
    }

    public Object getItem(int position) {
        return searchArrayList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.slides, null);
            holder = new ViewHolder();
            holder.txtImage = (ImageView) convertView.findViewById(R.id.slideimg);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.txtImage.setImageBitmap(BitmapFactory.decodeByteArray(searchArrayList.get(position), 0, searchArrayList.get(position).length));
        return convertView;
    }

    static class ViewHolder {
        ImageView txtImage;
    }
}
