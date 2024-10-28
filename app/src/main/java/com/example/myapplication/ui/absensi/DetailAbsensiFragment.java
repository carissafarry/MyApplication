package com.example.myapplication.ui.absensi;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.adapter.SiswaHadirAdapter;
import com.example.myapplication.databinding.FragmentDetailAbsensiBinding;
import com.example.myapplication.interfaces.AbsensiApiService;
import com.example.myapplication.interfaces.AbsensiSiswaApiService;
import com.example.myapplication.models.Absensi;

import java.io.Serializable;
import java.util.List;

import com.example.myapplication.models.api.ApiResponse;
import com.example.myapplication.models.api.AbsensiRequest;
import com.example.myapplication.models.api.DataAbsensiResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.ApiClient;
import utils.DateConverter;

public class DetailAbsensiFragment extends Fragment {
    private FragmentDetailAbsensiBinding binding;
    private static final String ARG_ABSENSI = "absensi";
    private Absensi absensi;
    private AbsensiApiService detailAbsensiApiService;
    private AbsensiSiswaApiService absensiSiswaApiService;
//    private RecyclerView recyclerViewSiswaHadir;
//    private SiswaHadirAdapter siswaHadirAdapter;
    private TableLayout tableLayoutSiswaHadir;
    private TableLayout tableLayoutDetailAbsensi;
    private ImageView qrCodeImage;

    private static final String API_DETAIL_URL = "http://192.168.100.70:8000/absensi/detail"; // Replace with your API URL

    public static DetailAbsensiFragment newInstance(Absensi absensi) {
        DetailAbsensiFragment fragment = new DetailAbsensiFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_ABSENSI, (Serializable) absensi);
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_absensi, container, false);

//        TextView textView = view.findViewById(R.id.textDetailNamaKelas);
//        TextView textMatpelView = view.findViewById(R.id.textDetailMataPelajaran);

//        recyclerViewSiswaHadir = view.findViewById(R.id.recyclerViewSiswaHadir);
//        recyclerViewSiswaHadir.setLayoutManager(new LinearLayoutManager(getContext()));
        tableLayoutSiswaHadir = view.findViewById(R.id.tableLayoutSiswaHadir);
        tableLayoutDetailAbsensi = view.findViewById(R.id.tableLayoutDetailAbsensi);

        // Initialize API service
        detailAbsensiApiService = ApiClient.getClient(requireContext()).create(AbsensiApiService.class);
        absensiSiswaApiService = ApiClient.getClient(requireContext()).create(AbsensiSiswaApiService.class);

        String absensi_id = null;
        if (getArguments() != null) {
            qrCodeImage = view.findViewById(R.id.qrCodeImage);
            absensi_id = getArguments().getString("absensi_id");
            fetchAbsensiDetail(absensi_id);
            fetchDataAbsensi(absensi_id);
        }

        return view;
    }

    private void loadQRCodeImage(Absensi absensi) {
        // Replace with the path to your QR code image
        String imagePath = "file:///android_asset/" + absensi.getMataPelajaran().toLowerCase()  + ".png"; // or a URL if it's hosted online

        // Use Glide to load the image
        Glide.with(this)
                .load(imagePath)
                .into(qrCodeImage);
    }

    private void fetchAbsensiDetail(String id) {
        AbsensiRequest request = new AbsensiRequest();
        request.setAbsensiId(id);

        Call<ApiResponse<Absensi>> call = detailAbsensiApiService.getAbsensiDetail(request);
        call.enqueue(new Callback<ApiResponse<Absensi>>() {
            @Override
            public void onResponse(Call<ApiResponse<Absensi>> call, Response<ApiResponse<Absensi>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    absensi = response.body().getResponseData();

                    if (absensi != null) {
                        loadQRCodeImage(absensi);
                        populateTableDetailAbsensi(absensi);
                    }
                } else {
                    Log.e("DetailAbsensiFragment-fetchAbsensiDetail", "Request failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Absensi>> call, Throwable t) {
                Log.e("DetailAbsensiFragment-fetchAbsensiDetail", "Network error: " + t.getMessage());
            }
        });
    }

    private void fetchDataAbsensi(String id) {
        AbsensiRequest request = new AbsensiRequest();
        request.setAbsensiId(id);

        Call<ApiResponse<List<DataAbsensiResponse>>> call = absensiSiswaApiService.getDataAbsensiAll(request);
        call.enqueue(new Callback<ApiResponse<List<DataAbsensiResponse>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<DataAbsensiResponse>>> call, Response<ApiResponse<List<DataAbsensiResponse>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<DataAbsensiResponse> dataKehadiran = response.body().getResponseData();
                    if (dataKehadiran != null) {
                        populateTableKehadiran(dataKehadiran);
                    }

                    // Display data by adapter
//                    siswaHadirAdapter = new SiswaHadirAdapter(dataAbsensi);
//                    recyclerViewSiswaHadir.setAdapter(siswaHadirAdapter);
                } else {
                    Log.e("DetailAbsensiFragment-fetchDataAbsensi", "Request failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<DataAbsensiResponse>>> call, Throwable t) {
                Log.e("DetailAbsensiFragment-fetchDataAbsensi", "Network error: " + t.getMessage());
            }
        });
    }

    private void populateTableDetailAbsensi(Absensi detailAbsensi) {
        if (detailAbsensi == null) {
            Log.e("DetailAbsensiFragment", "No detail data available.");
            return;
        }

        // Create a helper method to add rows
        addRowToTable("ID Absensi", String.valueOf(detailAbsensi.getId()));
        addRowToTable("Kelas", detailAbsensi.getNamaKelas());
        addRowToTable("Mata Pelajaran", detailAbsensi.getMataPelajaran());
        addRowToTable("Guru", detailAbsensi.getNamaGuru());

        String formattedJamMulai = DateConverter.formatDateTime(detailAbsensi.getJamMulai());
        String formattedJamAkhir = DateConverter.formatDateTime(detailAbsensi.getJamAkhir());

        addRowToTable("Jam Mulai", formattedJamMulai);
        addRowToTable("Jam Selesai", formattedJamAkhir);
    }

    private void addRowToTable(String label, String value) {
        TableRow row = new TableRow(getContext());

        TextView labelTextView = new TextView(getContext());
        labelTextView.setText(label);
        labelTextView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 2f));
        row.addView(labelTextView);

        TextView valueTextView = new TextView(getContext());
        valueTextView.setText(value);
        valueTextView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 2f));
        row.addView(valueTextView);

        tableLayoutDetailAbsensi.addView(row);
    }

    private void populateTableKehadiran(List<DataAbsensiResponse> dataAbsensi) {
        int index = 1;
        for (DataAbsensiResponse absen : dataAbsensi) {
            TableRow row = new TableRow(getContext());

            TextView noTextView = new TextView(getContext());
            noTextView.setText(String.valueOf(index));
            noTextView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
            row.addView(noTextView);

            TextView namaTextView = new TextView(getContext());
            namaTextView.setText(absen.getNamaSiswa());
            namaTextView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 2f));
            row.addView(namaTextView);

            TextView kehadiranTextView = new TextView(getContext());
            kehadiranTextView.setText(absen.getStatusAbsen());
            kehadiranTextView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 2f));
            row.addView(kehadiranTextView);

            tableLayoutSiswaHadir.addView(row);
            index++;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
