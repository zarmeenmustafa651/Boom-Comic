package com.apps.zarmeen.boom;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class GridViewAdapter extends ArrayAdapter<Item>
{
    public GridViewAdapter(@NonNull Context context, int resource, @NonNull List<Item> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View v = convertView;
        if (null == v) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.grid_item, null);
        }
        Item item = getItem(position);
        ImageView btn = (ImageView) v.findViewById(R.id.imgbtn);
        TextView textView = (TextView) v.findViewById(R.id.mytext);

        btn.setImageBitmap(item.getmImageDrawable());
        textView .setText(item.getText());

        return v;
    }
}
