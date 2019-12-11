package com.apps.zarmeen.boom;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListViewAdapter extends BaseAdapter {
    ArrayList<Item> searchArrayList;
    ArrayList<Item> arraylist;
    LayoutInflater mInflater;
    public ListViewAdapter(Context context, ArrayList<Item> results) {
        super();
        searchArrayList = results;
        mInflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<Item>();
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
            convertView = mInflater.inflate(R.layout.grid_item, null);
            holder = new ViewHolder();
            holder.txtImage = (ImageView) convertView.findViewById(R.id.imgbtn);
            holder.txtName = (TextView) convertView.findViewById(R.id.mytext);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.txtImage.setImageBitmap(searchArrayList.get(position).getmImageDrawable());
        holder.txtName.setText(searchArrayList.get(position).getText());
        return convertView;
    }

    static class ViewHolder {
        ImageView txtImage;
        TextView txtName;
    }
    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        searchArrayList.clear();
        if (charText.length() == 0) {
            searchArrayList.addAll(arraylist);
        } else {
            for (Item wp : arraylist) {
                if (wp.getText().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    searchArrayList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
