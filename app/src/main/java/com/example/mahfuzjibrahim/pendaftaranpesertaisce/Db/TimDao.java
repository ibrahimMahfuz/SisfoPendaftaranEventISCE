package com.example.mahfuzjibrahim.pendaftaranpesertaisce.Db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface TimDao {
    @Query("SELECT * FROM tims WHERE peserta_id = :pesertaId")
    List<Tim> getTim(int pesertaId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTIm(Tim tim);
}
