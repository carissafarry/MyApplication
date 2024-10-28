package com.example.myapplication.ui.absensi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.interfaces.AbsensiSiswaApiService;
import com.example.myapplication.models.api.AbsensiRequest;
import com.example.myapplication.models.api.ApiResponse;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.ApiClient;

public class QRScannerFragment extends Fragment {
    private Button btnScan;
    private AbsensiSiswaApiService absensiApiService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qr_scanner, container, false);

        btnScan = view.findViewById(R.id.btnScan);
        btnScan.setOnClickListener(v -> startQRScanner());

        absensiApiService = ApiClient.getClient(requireContext()).create(AbsensiSiswaApiService.class);

        return view;
    }

    private void startQRScanner() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Scan a QR Code");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        qrLauncher.launch(options);
    }

    private final ActivityResultLauncher<ScanOptions> qrLauncher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() != null) {
            String qrData = result.getContents();
            Toast.makeText(getActivity(), "Scanned: " + qrData, Toast.LENGTH_LONG).show();
            sendQrDataToApi(qrData);
        }
    });

    private void sendQrDataToApi(String qrData) {
        AbsensiRequest request = new AbsensiRequest();
        request.setCode(qrData);

        Call<ApiResponse<Void>> call = absensiApiService.doAbsensi(request);

        call.enqueue(new Callback<ApiResponse<Void>>() {
            @Override
            public void onResponse(Call<ApiResponse<Void>> call, Response<ApiResponse<Void>> response) {
                Toast.makeText(getActivity(), "Failed to send data", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ApiResponse<Void>> call, Throwable t) {
                Toast.makeText(getActivity(), "Error sending data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

