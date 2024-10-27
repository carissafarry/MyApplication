package com.example.myapplication.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.Absensi;

import java.util.ArrayList;
import java.util.List;

public class AbsensiAdapter extends RecyclerView.Adapter<AbsensiAdapter.AbsensiViewHolder> {

    private Context context;
    private List<Absensi> absensiList;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(Absensi Absensi);
    }

    public AbsensiAdapter(List<Absensi> absensiList,Context context, OnItemClickListener onItemClickListener) {
        this.absensiList = absensiList;
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public AbsensiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_absensi, parent, false);
        return new AbsensiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AbsensiViewHolder holder, int position) {
        Absensi absensi = absensiList.get(position);
        if (absensi != null) {
            holder.bind(absensi, onItemClickListener);
        }
    }

    @Override
    public int getItemCount() {
        return absensiList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<Absensi> absensiList) {
        this.absensiList.addAll(absensiList);
        Log.d("TAG", "this.absensilist-" + this.absensiList.toString());
        notifyDataSetChanged();
    }

    public static class AbsensiViewHolder extends RecyclerView.ViewHolder {

        private TextView kelasTextView;
        private TextView matpelTextView;
        private TextView jamMulaiTextView;
        private TextView jamAkhirTextView;
        private TextView statusTextView;

        public AbsensiViewHolder(@NonNull View itemView) {
            super(itemView);
//            kelasTextView = itemView.findViewById(R.id.textDetailNamaKelas);
//            matpelTextView = itemView.findViewById(R.id.textDetailMataPelajaran);
//            jamMulaiTextView = itemView.findViewById(R.id.textDetailJamMulai);
//            jamAkhirTextView = itemView.findViewById(R.id.textDetailJamAkhir);
//            statusTextView = itemView.findViewById(R.id.textDetailStatus);
        }

        public void bind(Absensi Absensi, OnItemClickListener onItemClickListener) {
            kelasTextView.setText(Absensi.getNamaKelas());
            matpelTextView.setText(Absensi.getMataPelajaran());
            jamMulaiTextView.setText(Absensi.getJamMulai());
            jamAkhirTextView.setText(Absensi.getJamAkhir());
            statusTextView.setText(Absensi.getDone());

            itemView.setOnClickListener(v -> onItemClickListener.onItemClick(Absensi));
        }
    }
}
