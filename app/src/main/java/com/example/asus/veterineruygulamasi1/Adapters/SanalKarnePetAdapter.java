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
import com.example.asus.veterineruygulamasi1.Models.PetModel;
import com.example.asus.veterineruygulamasi1.R;
import com.example.asus.veterineruygulamasi1.Utils.ChangeFragments;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SanalKarnePetAdapter extends RecyclerView.Adapter<SanalKarnePetAdapter.ViewHolder> {

    List<PetModel> list;
    Context context;

    public SanalKarnePetAdapter(List<PetModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //layout tanımlaması yapılır
        View view = LayoutInflater.from(context).inflate(R.layout.sanalkarnepetlayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //atama işlemleri gerçekleştirilir
        holder.sanalKarnePetText.setText(list.get(position).getPetisim().toString());
        holder.sanalKarneBilgiText.setText(list.get(position).getPetisim().toString() + " isimli " + list.get(position).getPettur() + " türüne " + list.get(position).getPetcins() + " cinsine ait petinizine " +
                "ait geçmiş aşıları görmek için tıklayınız...");


        Picasso.get().load(list.get(position).getPetresim()).into(holder.sanalKarnePetImage);

        holder.sanalLayoutCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangeFragments changeFragments=new ChangeFragments(context);
                //parametre göndermek lazım fragmentler arası parametre göndermeliyiz bunun için changefragment class ımızı uygun hale getirdik

                changeFragments.changeWithParameter(new AsiDetayFragment(),list.get(position).getPetid());

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView sanalKarneBilgiText, sanalKarnePetText;
        CircleImageView sanalKarnePetImage;
        CardView sanalLayoutCardView;

        //itemview ile listview in her elemanı için layout ile oluşturduğumuz view tanımlanması gerçekleştirilecek
        public ViewHolder(View itemView) {
            super(itemView);
            sanalKarneBilgiText = itemView.findViewById(R.id.sanalKarneBilgiText);
            sanalKarnePetText = itemView.findViewById(R.id.sanalKarnePetText);
            sanalKarnePetImage = itemView.findViewById(R.id.sanalKarnePetImage);
            sanalLayoutCardView = itemView.findViewById(R.id.sanalLayoutCardView);

        }
    }
}
