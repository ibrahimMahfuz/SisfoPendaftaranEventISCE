package com.example.mahfuzjibrahim.pendaftaranpesertaisce;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mahfuzjibrahim.pendaftaranpesertaisce.Adapter.PesertaAdapter;
import com.example.mahfuzjibrahim.pendaftaranpesertaisce.Db.AppDatabase;
import com.example.mahfuzjibrahim.pendaftaranpesertaisce.Db.Peserta;
import com.example.mahfuzjibrahim.pendaftaranpesertaisce.Db.Tim;
import com.example.mahfuzjibrahim.pendaftaranpesertaisce.Model.PesertaList;
import com.example.mahfuzjibrahim.pendaftaranpesertaisce.Model.PesertaModel;
import com.example.mahfuzjibrahim.pendaftaranpesertaisce.Model.TimModel;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements PesertaAdapter.onPesertaItemClicked {
    RecyclerView rvListPeserta;
    PesertaAdapter pesertaAdapter;
    ProgressBar progressBar;
    AppDatabase mdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mdb = Room.databaseBuilder(this, AppDatabase.class, "isce.db")
                .allowMainThreadQueries()
                .build();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createIntent = new Intent(MainActivity.this,CreateActivity.class);
                startActivity(createIntent);
            }
        });

        progressBar = findViewById(R.id.rec_progressBar);

        rvListPeserta = findViewById(R.id.rec_view_peserta);
        pesertaAdapter = new PesertaAdapter();
        pesertaAdapter.setClickHandler(this);
        ambilData();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            ambilData();
            return true;
        }else if(id == R.id.menu_favorite){
            Intent intentFav = new Intent(MainActivity.this,FavoriteActivity.class);
            startActivity(intentFav);
        }

        return super.onOptionsItemSelected(item);
    }


    public void ambilData(){
        progressBar.setVisibility(View.VISIBLE);
        rvListPeserta.setVisibility(View.GONE);
        if(isConnected()){
            Toast.makeText(MainActivity.this,"Connection Established",Toast.LENGTH_LONG).show();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://tugaspmobkelptiga.herokuapp.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            IsceClient client = retrofit.create(IsceClient.class);

            Call<PesertaList> call = client.getPeserta();

            call.enqueue(new Callback<PesertaList>() {
                @Override
                public void onResponse(Call<PesertaList> call, Response<PesertaList> response) {
                    if (!response.isSuccessful()){
                        Toast.makeText(MainActivity.this,response.code(), Toast.LENGTH_LONG).show();
                        return;
                    }

                    PesertaList lists = response.body();
                    List<PesertaModel> pesertaModels = lists.value;
                    ArrayList<PesertaModel> listPeserta = new ArrayList<PesertaModel>(pesertaModels);
                    pesertaAdapter.setDataPeserta(listPeserta);
                    rvListPeserta.setAdapter(pesertaAdapter);
                    rvListPeserta.setLayoutManager(new LinearLayoutManager(MainActivity.this));

                    savePesertaData(listPeserta);

                    progressBar.setVisibility(View.GONE);
                    rvListPeserta.setVisibility(View.VISIBLE);


                }

                @Override
                public void onFailure(Call<PesertaList> call, Throwable t) {
                    Snackbar snackbar = Snackbar.make(rvListPeserta,"Somethink Wrong",Snackbar.LENGTH_INDEFINITE)
                            .setAction("RETRY", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ambilData();
                                }
                            });
                    snackbar.show();
                }
            });
        }else{
            Toast.makeText(MainActivity.this,"Connection Established",Toast.LENGTH_LONG).show();
            List<Peserta> pesertas = mdb.pesertaDao().getPeserta();
            List<PesertaModel> pesertaModels = new ArrayList<>();

                for (Peserta p : pesertas){
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

            pesertaAdapter.setDataPeserta(new ArrayList<PesertaModel>(pesertaModels));
            rvListPeserta.setAdapter(pesertaAdapter);
            rvListPeserta.setLayoutManager(new LinearLayoutManager(MainActivity.this));

            progressBar.setVisibility(View.GONE);
            rvListPeserta.setVisibility(View.VISIBLE);

        }


    }


    @Override
    public void pesertaItemClicked(PesertaModel p) {
        Intent detailMovieIntent = new Intent(this, DetailActivity.class);
        detailMovieIntent.putExtra("peserta",p);
        startActivityForResult(detailMovieIntent, 1);
    }

    public Boolean isConnected(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }

    public void savePesertaData(ArrayList<PesertaModel> pesertaModels){
        for (PesertaModel pm : pesertaModels){
            Peserta peserta = new Peserta();
            peserta.id = pm.getId();
            peserta.namaPeserta = pm.getNamaPeserta();
            peserta.judulKegiatan = pm.getJudulKegiatan();
            peserta.email = pm.getEmail();
            peserta.noHp = pm.getNoHp();
            peserta.institusi = pm.getInstitusi();
            peserta.photoPath = pm.getPhotoPath();
            peserta.createdAt = pm.getCreatedAt();

            mdb.pesertaDao().insertPeserta(peserta);
        }
    }

}
