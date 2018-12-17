package com.example.mahfuzjibrahim.pendaftaranpesertaisce.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mahfuzjibrahim.pendaftaranpesertaisce.Model.PesertaModel;
import com.example.mahfuzjibrahim.pendaftaranpesertaisce.R;

import java.util.ArrayList;

public class PesertaAdapter extends RecyclerView.Adapter<PesertaAdapter.PesertaHolder> {
    onPesertaItemClicked clickHandler;

    public void setClickHandler(onPesertaItemClicked clickHandler){
        this.clickHandler = clickHandler;
    }

    public ArrayList<PesertaModel> getDataPeserta() {
        return dataPeserta;
    }

    public void setDataPeserta(ArrayList<PesertaModel> dataPeserta) {
        this.dataPeserta = dataPeserta;
    }

    private ArrayList<PesertaModel> dataPeserta;
    @NonNull
    @Override
    public PesertaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.row_peserta,parent,false);
        PesertaHolder holder = new PesertaHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PesertaHolder holder, int position) {
        final PesertaModel pesertaModel = dataPeserta.get(position);
        holder.m_rec_nama_peserta.setText(pesertaModel.getNamaPeserta());
        holder.m_rec_judul_kegiatan.setText(pesertaModel.getJudulKegiatan());
        String url = "https://tugaspmobkelptiga.herokuapp.com/photo/" + pesertaModel.getPhotoPath();
        Glide.with(holder.itemView)
                .load(url)
                .into(holder.m_rec_photo);
        holder.m_rec_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickHandler.pesertaItemClicked(pesertaModel);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(dataPeserta == null){
            return 0;
        }
        return dataPeserta.size();
    }

    public class PesertaHolder extends RecyclerView.ViewHolder {
        ImageView m_rec_photo;
        TextView m_rec_nama_peserta;
        TextView m_rec_judul_kegiatan;
        CardView m_rec_card;
        public PesertaHolder(View itemView) {
            super(itemView);
            m_rec_photo = itemView.findViewById(R.id.rec_photo);
            m_rec_nama_peserta = itemView.findViewById(R.id.rec_nama_peserta);
            m_rec_judul_kegiatan = itemView.findViewById(R.id.rec_judl_kegiatan);
            m_rec_card = itemView.findViewById(R.id.rec_card_view);
        }
    }

    public interface onPesertaItemClicked{
        void pesertaItemClicked(PesertaModel p);
    }
}
