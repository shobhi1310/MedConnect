package com.example.medconnect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

class ContactRowAdapter extends ArrayAdapter<String> {
    public ContactRowAdapter(Context context, String[] names) {
        super(context,R.layout.contact_row,names);
    }

    final String[] emails={"cs18b006@iittp.ac.in","cs18b021@iittp.ac.in","cs18b034@iittp.ac.in","cs18b038@iittp.ac.in","cs18b029@iittp.ac.in"};


    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater myInflater = LayoutInflater.from(getContext());
        View customView = myInflater.inflate(R.layout.contact_row,parent,false);
        //get a reference
        String name = getItem(position);
        TextView myName = (TextView) customView.findViewById(R.id.name);
        TextView myEmail = (TextView) customView.findViewById(R.id.email);
        ImageView myImg = (ImageView) customView.findViewById(R.id.img);
        myName.setText(name);
        myEmail.setText(emails[position]);
        myImg.setImageResource(R.drawable.dood);
        return customView;
    }
}

