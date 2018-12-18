package com.example.mahfuzjibrahim.pendaftaranpesertaisce.Model;

import com.google.gson.annotations.SerializedName;

public class TimModel {

    @SerializedName("id")
    private Integer id;

    @SerializedName("peserta_id")
    private Integer pesertaId;

    @SerializedName("nama")
    private String nama;

    @SerializedName("email")
    private String email;

    @SerializedName("no_hp")
    private String noHp;

    public TimModel() {

    }

    public TimModel(Integer id, String nama, String email, String noHp) {
        this.id = id;
        this.nama = nama;
        this.email = email;
        this.noHp = noHp;
    }

    public int getPesertaId() {
        return pesertaId;
    }

    public void setPesertaId(Integer pesertaId) {
        this.pesertaId = pesertaId;
    }

    public TimModel(Integer id, Integer pesertaId, String nama, String email, String noHp) {
        this.id = id;
        this.pesertaId = pesertaId;
        this.nama = nama;
        this.email = email;
        this.noHp = noHp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }
}
