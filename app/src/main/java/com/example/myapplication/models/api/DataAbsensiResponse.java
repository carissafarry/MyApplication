package com.example.myapplication.models.api;

public class DataAbsensiResponse {
    private Integer siswa_id;
    private String nama_siswa;
    private Integer status_absen;

    // Getter and Setter
    public Integer getSiswaId() {
        return siswa_id;
    }

    public String getNamaSiswa() {
        return nama_siswa;
    }

    public String getStatusAbsen() {
        return (status_absen == 1 ? "Hadir" : "Tidak Tercatat");
    }
}
