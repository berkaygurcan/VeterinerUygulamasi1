package com.example.asus.veterineruygulamasi1.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.asus.veterineruygulamasi1.Fragments.AsiDetayFragment;
import com.example.asus.veterineruygulamasi1.Models.AsiModel;
import com.example.asus.veterineruygulamasi1.Models.PetModel;
import com.example.asus.veterineruygulamasi1.R;
import com.example.asus.veterineruygulamasi1.Utils.ChangeFragments;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SanalKarneGecmisAsiAdapter extends RecyclerView.Adapter<SanalKarneGecmisAsiAdapter.ViewHolder> {

    List<AsiModel> list;
    Context context;

    public SanalKarneGecmisAsiAdapter(List<AsiModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //layout tanımlaması yapılır
        View view = LayoutInflater.from(context).inflate(R.layout.sanalkarnegecmislayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //atama işlemleri gerçekleştirilir
        holder.sanalKarneAsiIsmiText.setText(list.get(position).getAsiisim()+" Aşısı Yapılmıştır.");
        holder.sanalKarneGecmisAsiBilgiText.setText(list.get(position).getAsiisim()+" isimli petinize "
        +list.get(position).getAsitarih()+" tarihinde "+list.get(position).getAsiisim()+" aşısı yapılmıştır.");

        Picasso.get().load(list.get(position).getPetresim().toString()).into(holder.sanalKarneGecmisAsiImage);




    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
      TextView  sanalKarneAsiIsmiText,sanalKarneGecmisAsiBilgiText;
      CircleImageView  sanalKarneGecmisAsiImage;

        //itemview ile listview in her elemanı için layout ile oluşturduğumuz view tanımlanması gerçekleştirilecek
        public ViewHolder(View itemView) {
            super(itemView);
            sanalKarneAsiIsmiText = itemView.findViewById(R.id.sanalKarneAsiIsmiText);
            sanalKarneGecmisAsiBilgiText = itemView.findViewById(R.id.sanalKarneGecmisAsiBilgiText);
            sanalKarneGecmisAsiImage = itemView.findViewById(R.id.sanalKarneGecmisAsiImage);


        }
    }
}
