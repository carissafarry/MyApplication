package com.example.myapplication.models.api;

public class AbsensiRequest {
    private String id_absensi;
    private Integer siswa_id;
    private Integer id_guru;
    private Integer kelas_id;
    private Integer mapel_id;
    private String jam_mulai;
    private String jam_akhir;

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

    public void setKelasId(Integer kelas_id) {
        this.kelas_id = kelas_id;
    }

    public void setMapelId(Integer mapel_id) {
        this.mapel_id = mapel_id;
    }

    public void setJamMulai(String jam_mulai) {
        this.jam_mulai = jam_mulai;
    }

    public void setJamAkhir(String jam_akhir) {
        this.jam_akhir = jam_akhir;
    }
}