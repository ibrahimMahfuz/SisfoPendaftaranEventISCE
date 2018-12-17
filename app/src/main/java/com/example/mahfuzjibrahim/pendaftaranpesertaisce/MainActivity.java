package com.example.mahfuzjibrahim.pendaftaranpesertaisce;

import android.content.Intent;
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
import android.widget.Toast;

import com.example.mahfuzjibrahim.pendaftaranpesertaisce.Adapter.PesertaAdapter;
import com.example.mahfuzjibrahim.pendaftaranpesertaisce.Model.PesertaList;
import com.example.mahfuzjibrahim.pendaftaranpesertaisce.Model.PesertaModel;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ambilData();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createIntent = new Intent(MainActivity.this,CreateActivity.class);
                startActivity(createIntent);
            }
        });

        rvListPeserta = findViewById(R.id.rec_view_peserta);
        pesertaAdapter = new PesertaAdapter();
        pesertaAdapter.setClickHandler(this);
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
        }

        return super.onOptionsItemSelected(item);
    }


    public void ambilData(){
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


            }

            @Override
            public void onFailure(Call<PesertaList> call, Throwable t) {
                Toast.makeText(MainActivity.this,"NOOOOOOOOOOOOOOOOOO", Toast.LENGTH_LONG).show();
            }
        });

    }

    public ArrayList<PesertaModel> dummyData(){
        ArrayList<PesertaModel> pesertaModels = new ArrayList<>();
        pesertaModels.add(new PesertaModel(1,"qwerty","asdasd","ttttt","aswqeed","rrrrrr","atata"));
        pesertaModels.add(new PesertaModel(1,"qwerty","asdasd","ttttt","aswqeed","rrrrrr","atata"));
        pesertaModels.add(new PesertaModel(1,"qwerty","asdasd","ttttt","aswqeed","rrrrrr","atata"));
        pesertaModels.add(new PesertaModel(1,"qwerty","asdasd","ttttt","aswqeed","rrrrrr","atata"));

        return pesertaModels;
    }

    @Override
    public void pesertaItemClicked(PesertaModel p) {
        Intent detailMovieIntent = new Intent(this, DetailActivity.class);
        detailMovieIntent.putExtra("peserta",p);
        startActivityForResult(detailMovieIntent, 1);
    }
}
