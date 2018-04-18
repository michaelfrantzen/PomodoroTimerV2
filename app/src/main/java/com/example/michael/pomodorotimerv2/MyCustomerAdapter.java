package com.example.michael.pomodorotimerv2;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyCustomerAdapter extends BaseAdapter implements ListAdapter {

    private ArrayList<Profile> list = new ArrayList<Profile>();
    private Context context;
    private ProfileDir profileDir;

    public MyCustomerAdapter(ArrayList<Profile> list, Context context, ProfileDir dir) {
        this.list = list;
        this.context = context;
        this.profileDir = dir;
    }

    @Override
    public int getCount(){
        return list.size();
    }

    @Override
    public Object getItem(int pos){
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos){
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        View view = convertView;
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.new_listview, null);
        }

        TextView listItemText = (TextView)view.findViewById(R.id.list_item_name);
        listItemText.setText(list.get(position).toString());
        listItemText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Profile p = (Profile)getItem(position);
                Intent i = new Intent(context, TimerActivity.class);
                i.putExtra("timeAmount", "" + p.getWorkTime());
                i.putExtra("restAmount", "" + p.getRestTime());
                i.putExtra("intervalAmount", "" + p.getInterval());
                i.putExtra("profileName", p.getName());
                i.putExtra("position", position);
                context.startActivity(i);
            }
        });

        ImageButton deleteBtn = (ImageButton) view.findViewById(R.id.delete_btn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(position);
                profileDir.deleteProfile(position);
                notifyDataSetChanged();
            }
        });
        return view;
    }


}
