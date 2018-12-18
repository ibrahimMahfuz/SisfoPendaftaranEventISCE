package com.example.mahfuzjibrahim.pendaftaranpesertaisce.Db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface PesertaDao {
    @Query("SELECT * FROM pesertas")
    List<Peserta> getPeserta();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPeserta(Peserta peserta);
}
