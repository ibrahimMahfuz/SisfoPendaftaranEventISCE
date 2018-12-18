package com.example.mahfuzjibrahim.pendaftaranpesertaisce.Db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Peserta.class,Tim.class,Favorite.class},version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PesertaDao pesertaDao();
    public abstract TimDao timDao();
    public abstract FavoriteDao pesertaFavoriteDao();
}
