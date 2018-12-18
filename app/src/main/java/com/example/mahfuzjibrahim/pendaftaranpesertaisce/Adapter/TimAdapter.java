package com.example.mahfuzjibrahim.pendaftaranpesertaisce.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mahfuzjibrahim.pendaftaranpesertaisce.Model.TimModel;
import com.example.mahfuzjibrahim.pendaftaranpesertaisce.R;

import java.util.ArrayList;

public class TimAdapter extends BaseAdapter {
    private ArrayList<TimModel> timModelArrayList;

    public ArrayList<TimModel> getTimModelArrayList() {
        return timModelArrayList;
    }

    public void setTimModelArrayList(ArrayList<TimModel> timModelArrayList) {
        this.timModelArrayList = timModelArrayList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if(timModelArrayList.isEmpty()){
            return 0;
        }
        return timModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.row_tim,parent,false);
        TextView m_tim_nama = convertView.findViewById(R.id.tim_nama);
        TextView m_tim_email = convertView.findViewById(R.id.tim_email);
        TextView m_tim_no_hp = convertView.findViewById(R.id.tim_no_hp);

        m_tim_nama.setText(timModelArrayList.get(position).getNama());
        m_tim_email.setText(timModelArrayList.get(position).getEmail());
        m_tim_no_hp.setText(timModelArrayList.get(position).getNoHp());

        return convertView;
    }
}
