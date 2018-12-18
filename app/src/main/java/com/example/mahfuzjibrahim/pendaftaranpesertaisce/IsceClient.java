package com.example.mahfuzjibrahim.pendaftaranpesertaisce;

import com.example.mahfuzjibrahim.pendaftaranpesertaisce.Model.PesertaList;
import com.example.mahfuzjibrahim.pendaftaranpesertaisce.Model.PesertaModel;
import com.example.mahfuzjibrahim.pendaftaranpesertaisce.Model.PesertaPostModel;
import com.example.mahfuzjibrahim.pendaftaranpesertaisce.Model.TimModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IsceClient {
    @GET("api/getapi")
    Call<PesertaList> getPeserta();

    @GET("/api/gettim/{peserta_id}")
    Call<List<TimModel>> getTimById(@Path("peserta_id") int peserta_id);

    @POST("api/postpeserta")
    Call<PesertaPostModel> create_peserta(@Body PesertaPostModel pesertaPostModel);
}
