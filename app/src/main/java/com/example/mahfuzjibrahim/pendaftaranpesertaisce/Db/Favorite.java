package com.example.mahfuzjibrahim.pendaftaranpesertaisce.Db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "favorites")
public class Favorite {
    @PrimaryKey
    public Integer id;

    @ColumnInfo(name = "judul_kegiatan")
    public String judulKegiatan;

    @ColumnInfo(name = "nama_peserta")
    public String namaPeserta;

    @ColumnInfo(name = "email")
    public String email;

    @ColumnInfo(name = "no_hp")
    public String noHp;

    @ColumnInfo(name = "institusi")
    public String institusi;

    @ColumnInfo(name="photo_path")
    public String photoPath;

    @ColumnInfo(name="created_at")
    public String createdAt;
}
