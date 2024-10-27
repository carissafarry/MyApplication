package com.example.myapplication.models.api;

public class AbsensiRequest {
    private String id_absensi;
    private Integer siswa_id;
    private Integer id_guru;

    // Getter and Setter
    public String getAbsensiId() {
        return id_absensi;
    }

    public void setAbsensiId(String id_absensi) {
        this.id_absensi = id_absensi;
    }

    public void setSiswaId(Integer siswa_id) {
        this.siswa_id = siswa_id;
    }

    public void setIdGuru(Integer id_guru) {
        this.id_guru = id_guru;
    }
}