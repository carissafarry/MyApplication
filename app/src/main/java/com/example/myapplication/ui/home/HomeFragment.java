package com.example.myapplication.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.R;
import com.example.myapplication.adapter.AbsensiAdapter2;
import com.example.myapplication.databinding.FragmentHomeBinding;
import com.example.myapplication.interfaces.AbsensiApiService;
import com.example.myapplication.models.Absensi;

import java.util.ArrayList;
import java.util.List;

import com.example.myapplication.models.api.AbsensiRequest;
import com.example.myapplication.models.api.ApiResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.ApiClient;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private ListView listView;
    private List<Absensi> absensiList;
    private AbsensiAdapter2 absensiAdapter;
    private ArrayAdapter<String> adapter;
    private AbsensiApiService apiService;

    private static final String API_URL = "http://192.168.100.70:8000/absensi/getAll";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        listView = view.findViewById(R.id.listViewAbsensi);

        // Example data
        absensiList = new ArrayList<>();
        absensiAdapter = new AbsensiAdapter2(getContext(), absensiList);
        listView.setAdapter(absensiAdapter);

        // Initialize API service
        apiService = ApiClient.getClient().create(AbsensiApiService.class);

        AbsensiRequest request = new AbsensiRequest();
        request.setSiswaId(2);
        fetchDataFromApi(request);

        listView.setOnItemClickListener((parent, view1, position, id) -> {
            Absensi absensiItem = absensiList.get(position);
            onAbsensiItemClick(absensiItem);
        });

        return view;
    }

    private void fetchDataFromApi(AbsensiRequest request) {
        Call<ApiResponse<List<Absensi>>> call = apiService.getAbsensiAll(request);

        call.enqueue(new Callback<ApiResponse<List<Absensi>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Absensi>>> call, Response<ApiResponse<List<Absensi>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    absensiList.clear();
                    absensiList.addAll(response.body().getResponseData());
                    absensiAdapter.notifyDataSetChanged();
                } else {
                    showError("Failed to fetch data");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Absensi>>> call, Throwable t) {
                showError("Error: " + t.getMessage());
            }
        });
    }

    private void onAbsensiItemClick(Absensi absensiItem) {
        Bundle bundle = new Bundle();
        bundle.putString("absensi_id", String.valueOf(absensiItem.getId()));
        NavHostFragment.findNavController(HomeFragment.this)
                .navigate(R.id.action_absensiList_to_detailAbsensiFragment, bundle);
    }

    private void showError(String message) {
        requireActivity().runOnUiThread(() -> {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        });
    }
}