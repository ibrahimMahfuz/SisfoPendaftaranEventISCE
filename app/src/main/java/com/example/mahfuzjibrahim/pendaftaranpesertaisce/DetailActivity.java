package com.example.mahfuzjibrahim.pendaftaranpesertaisce;

import android.arch.persistence.room.Room;
import android.content.ClipData;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.ThumbnailImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.mahfuzjibrahim.pendaftaranpesertaisce.Adapter.TimAdapter;
import com.example.mahfuzjibrahim.pendaftaranpesertaisce.Db.AppDatabase;
import com.example.mahfuzjibrahim.pendaftaranpesertaisce.Db.Favorite;
import com.example.mahfuzjibrahim.pendaftaranpesertaisce.Db.Tim;
import com.example.mahfuzjibrahim.pendaftaranpesertaisce.Model.PesertaList;
import com.example.mahfuzjibrahim.pendaftaranpesertaisce.Model.PesertaModel;
import com.example.mahfuzjibrahim.pendaftaranpesertaisce.Model.TimModel;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class DetailActivity extends AppCompatActivity {
    PesertaModel pesertaModel;
    ImageView mImangeView;
    CollapsingToolbarLayout collapsingToolbarLayout;
    TextView m_det_jud_keg;
    TextView m_det_email;
    TextView m_det_no_hp;
    TextView m_institusi;
    ListView listView;
    TimAdapter timAdapter;
    AppDatabase mdb;
    ImageView m_fav;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        collapsingToolbarLayout = findViewById(R.id.toolbar_layout);

        mdb = Room.databaseBuilder(this, AppDatabase.class, "isce.db")
                .allowMainThreadQueries()
                .build();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "ayam", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        pesertaModel = getIntent().getParcelableExtra("peserta");

        ambilData(pesertaModel.getId());

        getSupportActionBar().setTitle(pesertaModel.getNamaPeserta());

       mImangeView = findViewById(R.id.fragment_kids_profile_pic);
       m_det_jud_keg = findViewById(R.id.det_jud_keg);
       m_det_email = findViewById(R.id.det_email);
       m_det_no_hp = findViewById(R.id.det_no_hp);
       m_institusi = findViewById(R.id.det_institusi);
       listView = findViewById(R.id.tim_dynamic);
       m_fav = findViewById(R.id.image_fav);

       m_det_jud_keg.setText(pesertaModel.getJudulKegiatan());
       m_det_email.setText(pesertaModel.getEmail());
       m_det_no_hp.setText(pesertaModel.getNoHp());
       m_institusi.setText(pesertaModel.getInstitusi());


        String url = "https://tugaspmobkelptiga.herokuapp.com/photo/" + pesertaModel.getPhotoPath();
        Glide.with(this)
                .asBitmap()
                .load(url)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        Drawable drawable = new BitmapDrawable(resource);
                        collapsingToolbarLayout.setBackground(drawable);
                    }
                });


        if(mdb.pesertaFavoriteDao().getPesertaFavoritesbyName(this.pesertaModel.getId()) == null){
            m_fav.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        }else{
            m_fav.setImageResource(R.drawable.ic_favorite_black_24dp);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home) {
            finish();
        }else if(item.getItemId()==R.id.favorite){
            Toast.makeText(DetailActivity.this,"Favorite",Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }


    public void ambilData(int id){
        final int theId = id;
        if(isConnected()){
            Toast.makeText(DetailActivity.this,"Connection Established",Toast.LENGTH_LONG).show();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://tugaspmobkelptiga.herokuapp.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            IsceClient client = retrofit.create(IsceClient.class);

            Call<List<TimModel>> call = client.getTimById(theId);

            call.enqueue(new Callback<List<TimModel>>() {
                @Override
                public void onResponse(Call<List<TimModel>> call, Response<List<TimModel>> response) {
                    List<TimModel> list = response.body();

                    if(list.isEmpty()){
                        return;
                    }
                    timAdapter = new TimAdapter();
                    timAdapter.setTimModelArrayList(new ArrayList<TimModel>(list));
                    listView.setAdapter(timAdapter);

                    saveTimDb(new ArrayList<TimModel>(list));
                }

                @Override
                public void onFailure(Call<List<TimModel>> call, Throwable t) {
                    Snackbar snackbar = Snackbar.make(collapsingToolbarLayout,"Somethink Wrong",Snackbar.LENGTH_INDEFINITE)
                            .setAction("RETRY", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ambilData(theId);
                                }
                            });
                    snackbar.show();
                }
            });
        }else{
            Toast.makeText(DetailActivity.this,"Connection Not Established",Toast.LENGTH_LONG).show();
            List<Tim> tims = mdb.timDao().getTim(theId);
            List<TimModel> timModels = new ArrayList<>();

            for (Tim p : tims){
                TimModel timModel = new TimModel(
                        p.id,
                        p.pesertaId,
                        p.nama,
                        p.email,
                        p.noHp
                );
                timModels.add(timModel);
            }

            timAdapter = new TimAdapter();
            timAdapter.setTimModelArrayList(new ArrayList<TimModel>(timModels));
            listView = findViewById(R.id.tim_dynamic);
            listView.setAdapter(timAdapter);

        }

    }

    public void saveTimDb(ArrayList<TimModel> timModels){
        for (TimModel t : timModels){
            Tim tim = new Tim();
            tim.id = t.getId();
            tim.pesertaId = t.getPesertaId();
            tim.nama = t.getNama();
            tim.email = t.getEmail();
            tim.noHp = t.getNoHp();

            mdb.timDao().insertTIm(tim);
        }
    }

    public void savePesertaFavoriteData(PesertaModel pesertaModels){
        PesertaModel pm = pesertaModels;
            Favorite favorite = new Favorite();
            favorite.id = pm.getId();
            favorite.namaPeserta = pm.getNamaPeserta();
            favorite.judulKegiatan = pm.getJudulKegiatan();
            favorite.email = pm.getEmail();
            favorite.noHp = pm.getNoHp();
            favorite.institusi = pm.getInstitusi();
            favorite.photoPath = pm.getPhotoPath();
            favorite.createdAt = pm.getCreatedAt();
            mdb.pesertaFavoriteDao().insertPesertaFavorites(favorite);
    }

    public Boolean isConnected(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }

    public void changeFav(View v){
        if(m_fav.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.ic_favorite_border_black_24dp).getConstantState()){
            m_fav.setImageResource(R.drawable.ic_favorite_black_24dp);
            savePesertaFavoriteData(this.pesertaModel);
        }else{
            m_fav.setImageResource(R.drawable.ic_favorite_border_black_24dp);
            mdb.pesertaFavoriteDao().delete(this.pesertaModel.getId());
        }
    }
}
