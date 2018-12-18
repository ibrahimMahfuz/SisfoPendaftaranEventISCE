package com.example.mahfuzjibrahim.pendaftaranpesertaisce.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mahfuzjibrahim.pendaftaranpesertaisce.Db.Favorite;
import com.example.mahfuzjibrahim.pendaftaranpesertaisce.R;

import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteHolder> {

    onFavoriteItemClicked clickHandler;

    public void setClickHandler(onFavoriteItemClicked clickHandler){
        this.clickHandler = clickHandler;
    }

    public ArrayList<Favorite> getFavoriteList() {
        return favoriteList;
    }

    public void setFavoriteList(ArrayList<Favorite> favoriteList) {
        this.favoriteList = favoriteList;
        notifyDataSetChanged();
    }

    private ArrayList<Favorite> favoriteList;

    @NonNull
    @Override
    public FavoriteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.row_fav,parent,false);
        FavoriteAdapter.FavoriteHolder holder = new FavoriteAdapter.FavoriteHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteHolder holder, int position) {
        final Favorite favorite  = favoriteList.get(position);
        holder.m_rec_nama_peserta.setText(favorite.namaPeserta.toUpperCase());
        holder.m_rec_judul_kegiatan.setText(favorite.judulKegiatan);
        holder.m_rec_create.setText(favorite.createdAt.substring(0,10));
        holder.m_rec_email.setText("email : "+favorite.email);
        String url = "https://tugaspmobkelptiga.herokuapp.com/photo/" + favorite.photoPath;
        Log.d("mangap",favorite.photoPath);
        Glide.with(holder.itemView)
                .load(url)
                .apply(RequestOptions.circleCropTransform())
                .into(holder.m_rec_photo);
        holder.m_rec_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickHandler.favoriteItemClicked(favorite);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(favoriteList == null){
            return 0;
        }
        return favoriteList.size();
    }

    public class FavoriteHolder extends RecyclerView.ViewHolder {
        ImageView m_rec_photo;
        TextView m_rec_nama_peserta;
        TextView m_rec_judul_kegiatan;
        CardView m_rec_card;
        TextView m_rec_create;
        TextView m_rec_email;
        public FavoriteHolder(View itemView) {
            super(itemView);
            m_rec_photo = itemView.findViewById(R.id.fav_rec_photo);
            m_rec_nama_peserta = itemView.findViewById(R.id.fav_rec_nama_peserta);
            m_rec_judul_kegiatan = itemView.findViewById(R.id.fav_rec_judl_kegiatan);
            m_rec_card = itemView.findViewById(R.id.fav_rec_card_view);
            m_rec_create = itemView.findViewById(R.id.fav_rec_create);
            m_rec_email = itemView.findViewById(R.id.fav_rec_email);
        }
    }

    public interface onFavoriteItemClicked{
        void favoriteItemClicked(Favorite p);
    }
}
