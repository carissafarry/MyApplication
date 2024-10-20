package com.example.myapplication.models;

import java.util.Date;

public class Absensi {
    private String id_absensi;
    private String kelas_id;
    private String nama_kelas;
    private String nama_guru;
    private String mata_pelajaran;
    private String jenjang;
    private String jam_mulai;
    private String jam_akhir;
    private int done;

    public String getNamaKelas() {
        return nama_kelas;
    }

    public void setNamaKelas(String nama_kelas) {
        this.nama_kelas = nama_kelas;
    }

    public String getNamaGuru() {
        return nama_guru;
    }

    public void setNamaGuru(String nama_guru) {
        this.nama_guru = nama_guru;
    }

    public String getMataPelajaran() {
        return mata_pelajaran;
    }

    public void setMataPelajaran(String mata_pelajaran) {
        this.mata_pelajaran = mata_pelajaran;
    }

//    public String getJenjang() {
//        return jenjang;
//    }
//
//    public void setJenjang(String jenjang) {
//        this.jenjang = jenjang;
//    }

    public String getJamMulai() {
        return jam_mulai;
    }

    public void setJamMulai(String jam_mulai) {
        this.jam_mulai = jam_mulai;
    }

    public String getJamAkhir() {
        return jam_akhir;
    }

    public void setJamAkhir(String jam_akhir) {
        this.jam_akhir = jam_akhir;
    }

    public int getDone() {
        return done;
    }

    public void setDone(int done) {
        this.done = done;
    }

    @Override
    public String toString() {
        return "Kelas: " + nama_kelas + "\nMata Pelajaran: " + mata_pelajaran + "\nJam: " + jam_mulai + " - " + jam_akhir + "\nStatus: " + (done == 1 ? "Selesai" : "Tidak Tercatat");
    }
}
