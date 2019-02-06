package com.example.asus.veterineruygulamasi1.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.asus.veterineruygulamasi1.Models.PetModel;
import com.example.asus.veterineruygulamasi1.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PetsAdapter extends RecyclerView.Adapter<PetsAdapter.ViewHolder> {

    List<PetModel> list;
    Context context;

    public PetsAdapter(List<PetModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //layout tanımlaması yapılır
        View view= LayoutInflater.from(context).inflate(R.layout.petlistitemlayout,parent,false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //atama işlemleri gerçekleştirilir
        holder.petlayoutcinsname.setText("Pet Cinsi ="+list.get(position).getPetcins().toString());
        holder.petlayoutpetname.setText("Pet İsmi ="+list.get(position).getPetisim().toString());
        holder.petlayoutturname.setText("Pet Türü ="+list.get(position).getPettur().toString());

        Picasso.get().load(list.get(position).getPetresim()).into(holder.patlayoutpetimage);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
     TextView  petlayoutpetname,petlayoutcinsname,petlayoutturname;
     CircleImageView patlayoutpetimage;
        //itemview ile listview in her elemanı için layout ile oluşturduğumuz view tanımlanması gerçekleştirilecek
        public ViewHolder( View itemView) {
            super(itemView);
            petlayoutpetname=itemView.findViewById(R.id.petlayoutpetname);
            petlayoutcinsname=itemView.findViewById(R.id.petlayoutcinsname);
            petlayoutturname=itemView.findViewById(R.id.petlayoutturname);
            patlayoutpetimage=itemView.findViewById(R.id.patlayoutpetimage);
        }
    }
}
