package com.example.myapplication.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.Absensi;

import java.util.List;


public class AbsensiAdapter2 extends ArrayAdapter<Absensi> {
    public AbsensiAdapter2(Context context, List<Absensi> absensiList) {
        super(context, 0, absensiList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_absensi, parent, false);
        }

        Absensi absensi = getItem(position);
        TextView textView = convertView.findViewById(R.id.textMataPelajaran);

        textView.setText(absensi.toString());

        return convertView;
    }


}
