package com.example.mahfuzjibrahim.pendaftaranpesertaisce.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class PesertaModel implements Parcelable {

    @SerializedName("id")
    private Integer id;

    @SerializedName("judul_kegiatan")
    private String judulKegiatan;

    @SerializedName("nama_peserta")
    private String namaPeserta;

    @SerializedName("email")
    private String email;

    @SerializedName("no_hp")
    private String noHp;

    @SerializedName("institusi")
    private String institusi;

    @SerializedName("photo_path")
    private String photoPath;

    @SerializedName("created_at")
    private String createdAt;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public static Creator<PesertaModel> getCREATOR() {
        return CREATOR;
    }

    public PesertaModel(Integer id, String judulKegiatan, String namaPeserta, String email, String noHp, String institusi, String photoPath, String createdAt) {
        this.id = id;
        this.judulKegiatan = judulKegiatan;
        this.namaPeserta = namaPeserta;
        this.email = email;
        this.noHp = noHp;
        this.institusi = institusi;
        this.photoPath = photoPath;
        this.createdAt = createdAt;
    }


    public PesertaModel(String judulKegiatan, String namaPeserta, String email, String noHp, String institusi, String photoPath) {
        this.judulKegiatan = judulKegiatan;
        this.namaPeserta = namaPeserta;
        this.email = email;
        this.noHp = noHp;
        this.institusi = institusi;
        this.photoPath = photoPath;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJudulKegiatan() {
        return judulKegiatan;
    }

    public void setJudulKegiatan(String judulKegiatan) {
        this.judulKegiatan = judulKegiatan;
    }

    public String getNamaPeserta() {
        return namaPeserta;
    }

    public void setNamaPeserta(String namaPeserta) {
        this.namaPeserta = namaPeserta;
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

    public String getInstitusi() {
        return institusi;
    }

    public void setInstitusi(String institusi) {
        this.institusi = institusi;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public PesertaModel(){}

    public PesertaModel(Integer id, String judulKegiatan, String namaPeserta, String email, String noHp, String institusi, String photoPath) {
        this.id = id;
        this.judulKegiatan = judulKegiatan;
        this.namaPeserta = namaPeserta;
        this.email = email;
        this.noHp = noHp;
        this.institusi = institusi;
        this.photoPath = photoPath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.judulKegiatan);
        dest.writeString(this.namaPeserta);
        dest.writeString(this.email);
        dest.writeString(this.noHp);
        dest.writeString(this.institusi);
        dest.writeString(this.photoPath);
        dest.writeString(this.createdAt);
    }

    protected PesertaModel(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.judulKegiatan = in.readString();
        this.namaPeserta = in.readString();
        this.email = in.readString();
        this.noHp = in.readString();
        this.institusi = in.readString();
        this.photoPath = in.readString();
        this.createdAt = in.readString();
    }

    public static final Creator<PesertaModel> CREATOR = new Creator<PesertaModel>() {
        @Override
        public PesertaModel createFromParcel(Parcel source) {
            return new PesertaModel(source);
        }

        @Override
        public PesertaModel[] newArray(int size) {
            return new PesertaModel[size];
        }
    };
}
