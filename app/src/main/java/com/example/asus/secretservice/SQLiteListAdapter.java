package com.example.asus.secretservice;

import java.util.ArrayList;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SQLiteListAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> text;

    public SQLiteListAdapter(
            Context context2,
            ArrayList<String> txt

    ) {

        this.context = context2;
        this.text = txt;

    }

    public int getCount() {
// TODO Auto-generated method stub
        return text.size();
    }

    public Object getItem(int position) {
// TODO Auto-generated method stub
        return null;
    }

    public long getItemId(int position) {
// TODO Auto-generated method stub
        return 0;
    }

    public View getView(int position, View child, ViewGroup parent) {

        Holder holder;

        LayoutInflater layoutInflater;

        if (child == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            child = layoutInflater.inflate(R.layout.listviewdata, null);

            holder = new Holder();

            holder.text = (TextView) child.findViewById(R.id.textView);

            child.setTag(holder);

        } else {

            holder = (Holder) child.getTag();
        }
        holder.text.setTextColor(Color.BLACK);
        holder.text.setText(text.get(position));



        return child;
    }

    public class Holder {
        TextView text;

    }
}