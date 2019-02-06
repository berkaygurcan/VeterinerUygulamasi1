package com.example.asus.veterineruygulamasi1.Adapters;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.design.button.MaterialButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.veterineruygulamasi1.Models.AnswersModel;
import com.example.asus.veterineruygulamasi1.Models.DeleteAnswerModel;
import com.example.asus.veterineruygulamasi1.Models.KampanyaModel;
import com.example.asus.veterineruygulamasi1.R;
import com.example.asus.veterineruygulamasi1.RestApi.ManagerAll;
import com.example.asus.veterineruygulamasi1.Utils.Warnings;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KampanyaAdapter extends RecyclerView.Adapter<KampanyaAdapter.ViewHolder> {

    List<KampanyaModel> list;
    Context context;

    public KampanyaAdapter(List<KampanyaModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //layout tanımlaması yapılır
        View view = LayoutInflater.from(context).inflate(R.layout.kampanyaitemlayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //atama işlemleri gerçekleştirilir
        holder.kampanyaBaslikText.setText(list.get(position).getBaslik());
        holder.kampanyaText.setText(list.get(position).getText());

        Picasso.get().load(list.get(position).getResim()).into(holder.kampanyaImageView);

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
      TextView  kampanyaBaslikText,kampanyaText;
       ImageView kampanyaImageView;

        //itemview ile listview in her elemanı için layout ile oluşturduğumuz view tanımlanması gerçekleştirilecek
        public ViewHolder(View itemView) {
            super(itemView);
            kampanyaBaslikText=itemView.findViewById(R.id.kampanyaBaslikText);
            kampanyaText=itemView.findViewById(R.id.kampanyaText);
            kampanyaImageView=itemView.findViewById(R.id.kampanyaImageView);
        }
    }
}
