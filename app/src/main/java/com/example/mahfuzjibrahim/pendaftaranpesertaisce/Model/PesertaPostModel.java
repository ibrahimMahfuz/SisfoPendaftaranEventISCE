package com.example.mahfuzjibrahim.pendaftaranpesertaisce.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class PesertaPostModel implements Parcelable {
    public PesertaPostModel(Integer id, String judulKegiatan, String namaPeserta, String email, String noHp, String institusi, String photoPath) {
        this.id = id;
        this.judulKegiatan = judulKegiatan;
        this.namaPeserta = namaPeserta;
        this.email = email;
        this.noHp = noHp;
        this.institusi = institusi;
        this.photoPath = photoPath;
    }

    public PesertaPostModel() {}

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

    public PesertaPostModel(String judulKegiatan, String namaPeserta, String email, String noHp, String institusi, String photoPath) {
        this.judulKegiatan = judulKegiatan;
        this.namaPeserta = namaPeserta;
        this.email = email;
        this.noHp = noHp;
        this.institusi = institusi;
        this.photoPath = photoPath;
    }

    protected PesertaPostModel(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        judulKegiatan = in.readString();
        namaPeserta = in.readString();
        email = in.readString();
        noHp = in.readString();
        institusi = in.readString();
        photoPath = in.readString();
    }

    public static final Creator<PesertaPostModel> CREATOR = new Creator<PesertaPostModel>() {
        @Override
        public PesertaPostModel createFromParcel(Parcel in) {
            return new PesertaPostModel(in);
        }

        @Override
        public PesertaPostModel[] newArray(int size) {
            return new PesertaPostModel[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(judulKegiatan);
        dest.writeString(namaPeserta);
        dest.writeString(email);
        dest.writeString(noHp);
        dest.writeString(institusi);
        dest.writeString(photoPath);
    }
}
