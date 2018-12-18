package com.example.mahfuzjibrahim.pendaftaranpesertaisce.Db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "tims")
public class Tim {

    @PrimaryKey
    public Integer id;

    @ColumnInfo(name = "peserta_id")
    public Integer pesertaId;

    @ColumnInfo(name = "nama")
    public String nama;

    @ColumnInfo(name = "email")
    public String email;

    @ColumnInfo(name = "no_hp")
    public String noHp;
}
