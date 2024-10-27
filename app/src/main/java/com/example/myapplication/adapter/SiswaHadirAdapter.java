package com.example.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;
import com.example.myapplication.models.api.DataAbsensiResponse;
import java.util.List;

public class SiswaHadirAdapter extends RecyclerView.Adapter<SiswaHadirAdapter.SiswaViewHolder> {
    private List<DataAbsensiResponse> siswaList;

    public SiswaHadirAdapter(List<DataAbsensiResponse> siswaList) {
        this.siswaList = siswaList;
    }

    @NonNull
    @Override
    public SiswaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_siswa_hadir, parent, false);
        return new SiswaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SiswaViewHolder holder, int position) {
        DataAbsensiResponse siswa = siswaList.get(position);
        holder.namaSiswaTextView.setText(siswa.getNamaSiswa());
    }

    @Override
    public int getItemCount() {
        return siswaList.size();
    }

    static class SiswaViewHolder extends RecyclerView.ViewHolder {
        TextView namaSiswaTextView;

        SiswaViewHolder(View itemView) {
            super(itemView);
            namaSiswaTextView = itemView.findViewById(R.id.textNamaSiswaHadir);
        }
    }
}
