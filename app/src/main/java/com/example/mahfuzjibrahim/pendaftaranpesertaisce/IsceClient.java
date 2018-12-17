package com.example.mahfuzjibrahim.pendaftaranpesertaisce;

import com.example.mahfuzjibrahim.pendaftaranpesertaisce.Model.PesertaList;
import com.example.mahfuzjibrahim.pendaftaranpesertaisce.Model.PesertaModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface IsceClient {
    @GET("api/getapi")
    Call<PesertaList> getPeserta();

    @POST("api/postpeserta")
    Call<PesertaModel> create_peserta(@Body PesertaModel pesertaModel);
}
