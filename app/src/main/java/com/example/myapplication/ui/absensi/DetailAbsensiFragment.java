package com.example.myapplication.ui.absensi;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentDetailAbsensiBinding;
import com.example.myapplication.interfaces.AbsensiApiService;
import com.example.myapplication.models.Absensi;


import java.io.Serializable;

import com.example.myapplication.models.api.ApiResponse;
import com.example.myapplication.models.api.AbsensiRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.ApiClient;
import utils.DateConverter;

public class DetailAbsensiFragment extends Fragment {
    private FragmentDetailAbsensiBinding binding;
    private static final String ARG_ABSENSI = "absensi";
    private Absensi absensi;
    private AbsensiApiService apiService;

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

        TextView textView = view.findViewById(R.id.textDetailNamaKelas);

        // Initialize API service
        apiService = ApiClient.getClient(requireContext()).create(AbsensiApiService.class);

        String absensi_id = null;
        if (getArguments() != null) {
            absensi_id = getArguments().getString("absensi_id");
            fetchAbsensiDetail(textView, absensi_id);
        }

        return view;
    }

    private void fetchAbsensiDetail(TextView textView, String id) {
        AbsensiRequest request = new AbsensiRequest();
        request.setAbsensiId(id);

        Call<ApiResponse<Absensi>> call = apiService.getAbsensiDetail(request);
        call.enqueue(new Callback<ApiResponse<Absensi>>() {
            @Override
            public void onResponse(Call<ApiResponse<Absensi>> call, Response<ApiResponse<Absensi>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Absensi detail = response.body().getResponseData();

                    String formattedJamMulai = DateConverter.formatDateTime(detail.getJamMulai());
                    String formattedJamAkhir = DateConverter.formatDateTime(detail.getJamAkhir());

                    if (detail != null) {
                        String displayText = "ID: " + detail.getId() + "\n" +
                                "Kelas: " + detail.getNamaKelas() + "\n" +
                                "Mata Pelajaran: " + detail.getMataPelajaran() + "\n" +
                                "Jam Mulai: " + formattedJamMulai + "\n" +
                                "Jam Akhir: " + formattedJamAkhir + "\n" +
                                "Status: " + detail.getDone();
                        textView.setText(displayText);
                    }
                } else {
                    Log.e("DetailAbsensiFragment", "Request failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Absensi>> call, Throwable t) {
                Log.e("DetailAbsensiFragment", "Network error: " + t.getMessage());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
