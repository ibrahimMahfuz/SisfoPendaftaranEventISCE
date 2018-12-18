package com.example.mahfuzjibrahim.pendaftaranpesertaisce;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.mahfuzjibrahim.pendaftaranpesertaisce.Adapter.FavoriteAdapter;
import com.example.mahfuzjibrahim.pendaftaranpesertaisce.Adapter.PesertaAdapter;
import com.example.mahfuzjibrahim.pendaftaranpesertaisce.Db.AppDatabase;
import com.example.mahfuzjibrahim.pendaftaranpesertaisce.Db.Favorite;
import com.example.mahfuzjibrahim.pendaftaranpesertaisce.Model.PesertaModel;

import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity implements FavoriteAdapter.onFavoriteItemClicked {
    FavoriteAdapter favoriteAdapter;
    RecyclerView fav_rec_view;
    AppDatabase mdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        mdb = Room.databaseBuilder(this, AppDatabase.class, "isce.db")
                .allowMainThreadQueries()
                .build();

        List<Favorite> favoriteList = mdb.pesertaFavoriteDao().getPesertaFavorites();
        List<PesertaModel> pesertaModels = new ArrayList<>();

        favoriteAdapter = new FavoriteAdapter();
        favoriteAdapter.setClickHandler(this);

        for (Favorite p : favoriteList){
            PesertaModel pesertaModel = new PesertaModel(
                    p.id,
                    p.judulKegiatan,
                    p.namaPeserta,
                    p.email,
                    p.noHp,
                    p.institusi,
                    p.photoPath,
                    p.createdAt
            );
            pesertaModels.add(pesertaModel);
        }

        fav_rec_view = findViewById(R.id.list_favorite);
        favoriteAdapter.setFavoriteList(new ArrayList<Favorite>(favoriteList));
        Toast.makeText(FavoriteActivity.this,String.valueOf(pesertaModels.size()),Toast.LENGTH_LONG).show();
        fav_rec_view.setAdapter(favoriteAdapter);
        fav_rec_view.setLayoutManager(new LinearLayoutManager(FavoriteActivity.this));
    }


    @Override
    public void favoriteItemClicked(Favorite p) {

    }
}
