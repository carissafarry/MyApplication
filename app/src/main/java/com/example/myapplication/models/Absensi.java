package com.example.myapplication.models;

import java.util.Date;

public class Absensi {
    private Integer id_absensi;
    private Integer guru_id;
    private String kelas_id;
    private String nama_kelas;
    private String nama_guru;
    private String mata_pelajaran;
    private String jenjang;
    private String jam_mulai;
    private String jam_akhir;
    private int done;

    public Absensi(Integer id_absensi, String nama_kelas, String nama_guru, String mata_pelajaran) {
        this.id_absensi = id_absensi;
        this.nama_kelas = nama_kelas;
        this.nama_guru = nama_guru;
        this.mata_pelajaran = mata_pelajaran;
    }

    public Integer getId() {
        return id_absensi;
    }

    public void setId(Integer id_absensi) {
        this.id_absensi = id_absensi;
    }

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

    public String getJenjang() {
        return jenjang;
    }

    public void setJenjang(String jenjang) {
        this.jenjang = jenjang;
    }

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

    public String getDone() {
        return (done == 1 ? "Selesai" : "Tidak Tercatat");
    }

    public void setDone(int done) {
        this.done = done;
    }

    @Override
    public String toString() {
        return "Id Absensi: " + id_absensi + "\nKelas: " + nama_kelas + "\nMata Pelajaran: " + mata_pelajaran + "\nJam: " + jam_mulai + " - " + jam_akhir + "\nStatus: " + (done == 1 ? "Selesai" : "Tidak Tercatat");
    }
}
