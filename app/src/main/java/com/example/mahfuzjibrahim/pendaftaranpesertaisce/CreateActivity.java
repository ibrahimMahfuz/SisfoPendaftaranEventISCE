package com.example.mahfuzjibrahim.pendaftaranpesertaisce;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mahfuzjibrahim.pendaftaranpesertaisce.Model.PesertaList;
import com.example.mahfuzjibrahim.pendaftaranpesertaisce.Model.PesertaModel;
import com.example.mahfuzjibrahim.pendaftaranpesertaisce.Model.PesertaPostModel;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateActivity extends AppCompatActivity {
    Spinner m_judl_kegiatan;
    EditText m_nama_pes;
    EditText m_email;
    EditText m_phone;
    EditText m_institusi;
    ImageView m_photo;
    Bitmap bm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        m_judl_kegiatan = findViewById(R.id.in_jenis_keg);
        m_nama_pes = findViewById(R.id.in_nama_pes);
        m_email = findViewById(R.id.in_email);
        m_phone = findViewById(R.id.in_phone);
        m_institusi = findViewById(R.id.in_institusi);
        m_photo = findViewById(R.id.in_photo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void create_peserta(View v){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

        String email = m_email.getText().toString();


        PesertaPostModel pesertaPostModel = new PesertaPostModel(m_judl_kegiatan.getSelectedItem().toString(),m_nama_pes.getText().toString(),email,m_phone.getText().toString(),m_institusi.getText().toString(),encodedImage);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tugaspmobkelptiga.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IsceClient client = retrofit.create(IsceClient.class);
        Call<PesertaPostModel> call = client.create_peserta(pesertaPostModel);

        call.enqueue(new Callback<PesertaPostModel>() {
            @Override
            public void onResponse(Call<PesertaPostModel> call, Response<PesertaPostModel> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(CreateActivity.this,response.code(), Toast.LENGTH_LONG).show();
                    return;
                }

                Toast.makeText(CreateActivity.this,"OK.........", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<PesertaPostModel> call, Throwable t) {
                Toast.makeText(CreateActivity.this,"NOOOOO", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void getPicture(View v){
        Intent fotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (fotoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(fotoIntent, 1);
        }
    }

    public void getGalery(View v){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        if (photoPickerIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(photoPickerIntent, 2);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case 1:
                if(resultCode == RESULT_OK){
                    Bitmap selectedImage = data.getParcelableExtra("data");
                    m_photo.setImageBitmap(selectedImage);
                    bm = selectedImage;
                }

                break;
            case 2:
                if(resultCode == RESULT_OK){
                    try {
                        final Uri imageUri = data.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        m_photo.setImageBitmap(selectedImage);
                        bm = selectedImage;
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Toast.makeText(CreateActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                    }

                }
                break;
        }
    }

}
