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

import com.example.myapplication.R;
import com.example.myapplication.models.Absensi;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import utils.ApiCallback;
import utils.DateConverter;
import utils.HttpClient;

public class HomeFragment extends Fragment {

    private ListView listView;
    private ArrayList<Absensi> absensiList;
    private ArrayAdapter<Absensi> absensiAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        listView = root.findViewById(R.id.list_absensi);
        absensiList = new ArrayList<>();
        absensiAdapter = new ArrayAdapter<>(
                getContext(),
                R.layout.list_item_absensi,
                R.id.textTanggalAbsensi,
                absensiList
        );
        listView.setAdapter(absensiAdapter);
        fetchAbsensiData();

        return root;
    }

    private void fetchAbsensiData() {
        String url = "http://192.168.100.70:8000/absensi/getAll";

        // Create the request JSON object
        JSONObject jsonObject = new JSONObject();
        try {
            // TODO
//            jsonObject.put("id_guru", 1);
            jsonObject.put("siswa_id", 2);
        } catch (Exception e) {
            showError("Error creating JSON: " + e.getMessage());
            return;
        }

        HttpClient.getInstance().postRequest(url, jsonObject, getContext(), new ApiCallback<JSONArray>() {
            @Override
            public void onSuccess(JSONArray response) {
                try {
                    // Clear existing data
                    absensiList.clear();

                    // Parse the absensi data
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject absensiJson = response.getJSONObject(i);

                        Absensi absensi = new Absensi();

                        String jenjang = absensiJson.getString("jenjang");
                        String nama_kelas = absensiJson.getString("nama_kelas");
                        String kelas_id = absensiJson.getString("kelas_id");

                        absensi.setNamaKelas(jenjang + " " + nama_kelas + " " + kelas_id);
                        absensi.setMataPelajaran(absensiJson.getString("mata_pelajaran"));
                        absensi.setDone(absensiJson.getInt("done"));

                        // Convert to formatted string
                        String jamMulai = absensiJson.getString("jamMulai");
                        String jamAkhir = absensiJson.getString("jamAkhir");
                        absensi.setJamMulai(DateConverter.convertDateFormat(jamMulai));
                        absensi.setJamAkhir(DateConverter.convertDateFormat(jamAkhir));

                        // Add the parsed Absensi object to the list
                        absensiList.add(absensi);
                    }

                    // Update UI on the main thread
                    requireActivity().runOnUiThread(() -> {
                        absensiAdapter.notifyDataSetChanged();
                        Toast.makeText(getContext(), "Data Absensi Loaded", Toast.LENGTH_SHORT).show();
                    });
                } catch (Exception e) {
                    showError("Error: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                showError("Error: " + errorMessage);
            }
        });
    }

    private void showError(String message) {
        requireActivity().runOnUiThread(() -> {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        });
    }
}