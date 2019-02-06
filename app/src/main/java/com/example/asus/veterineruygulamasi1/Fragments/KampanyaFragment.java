package com.example.asus.veterineruygulamasi1.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.asus.veterineruygulamasi1.Adapters.KampanyaAdapter;
import com.example.asus.veterineruygulamasi1.Models.KampanyaModel;
import com.example.asus.veterineruygulamasi1.R;
import com.example.asus.veterineruygulamasi1.RestApi.ManagerAll;
import com.example.asus.veterineruygulamasi1.Utils.ChangeFragments;
import com.example.asus.veterineruygulamasi1.Utils.Warnings;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class KampanyaFragment extends Fragment {
    private View view;
    private RecyclerView kampanyaRecView;
    private ChangeFragments changeFragments;
    private KampanyaAdapter kampanyaAdapter;
    private List<KampanyaModel> kampanyaList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_kampanya, container, false);
        tanimla();
        getKampanya();
        return view;
    }

    public void tanimla() {

        kampanyaRecView=view.findViewById(R.id.kampanyaRecView);
        kampanyaRecView.setLayoutManager(new GridLayoutManager(getContext(),1));
        changeFragments=new ChangeFragments(getContext());
        kampanyaList=new ArrayList<>();
    }
    public void getKampanya(){

        Call<List<KampanyaModel>> req= ManagerAll.getInstance().getKampanya();
        req.enqueue(new Callback<List<KampanyaModel>>() {
            @Override
            public void onResponse(Call<List<KampanyaModel>> call, Response<List<KampanyaModel>> response) {
                if(response.body().get(0).isTf())
                {
                    kampanyaList=response.body();
                    kampanyaAdapter=new KampanyaAdapter(kampanyaList,getContext());
                    kampanyaRecView.setAdapter(kampanyaAdapter);

                }else
                {
                    Toast.makeText(getContext(), "Herhangi bir kampanya bulunmamaktadır...",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<KampanyaModel>> call, Throwable t) {
                Toast.makeText(getContext(), Warnings.internetProblemText,Toast.LENGTH_LONG).show();
                changeFragments.change(new HomeFragment());
            }
        });
    }


}
