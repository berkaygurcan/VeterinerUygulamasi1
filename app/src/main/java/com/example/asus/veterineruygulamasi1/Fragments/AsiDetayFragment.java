package com.example.asus.veterineruygulamasi1.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.asus.veterineruygulamasi1.Adapters.SanalKarneGecmisAsiAdapter;
import com.example.asus.veterineruygulamasi1.Models.AsiModel;
import com.example.asus.veterineruygulamasi1.R;
import com.example.asus.veterineruygulamasi1.RestApi.ManagerAll;
import com.example.asus.veterineruygulamasi1.Utils.ChangeFragments;
import com.example.asus.veterineruygulamasi1.Utils.GetSharedPreferences;
import com.example.asus.veterineruygulamasi1.Utils.Warnings;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AsiDetayFragment extends Fragment {
    private View view;
    private String musid;
    private String petid;
    private GetSharedPreferences getSharedPreferences;
    private RecyclerView asiDetayRecView;
    private SanalKarneGecmisAsiAdapter adapter;
    private List<AsiModel> list;
    private ChangeFragments changeFragments;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_asi_detay, container, false);
        tanimla();
        getGecmisAsi();
        return view;
    }

    public void tanimla() {

        petid = getArguments().getString("petid");
        getSharedPreferences = new GetSharedPreferences(getActivity());
        musid = getSharedPreferences.getsession().getString("id", null);
        asiDetayRecView = view.findViewById(R.id.asiDetayRecView);
        asiDetayRecView.setLayoutManager(new GridLayoutManager(getContext(), 1));

        list = new ArrayList<>();

        changeFragments = new ChangeFragments(getContext());


    }

    public void getGecmisAsi() {

        Call<List<AsiModel>> req = ManagerAll.getInstance().getGecmisAsi(musid, petid);
        req.enqueue(new Callback<List<AsiModel>>() {
            @Override
            public void onResponse(Call<List<AsiModel>> call, Response<List<AsiModel>> response) {
                // Log.i("gecmisasilar",response.body().toString());
                if (response.body().get(0).isTf()) {

                    list = response.body();
                    adapter=new SanalKarneGecmisAsiAdapter(list,getContext());
                    asiDetayRecView.setAdapter(adapter);
                    Toast.makeText(getContext(), "Petinize ait " + list.size() + " adet geçmişte yapılan aşı bulunmaktadır.", Toast.LENGTH_LONG).show();
                } else {
                    changeFragments.change(new SanalKarnePetlerFragment());
                    Toast.makeText(getContext(), "Petinize ait geçmişte yapılan herhangi bir bulunmamaktadır.", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<List<AsiModel>> call, Throwable t) {
                Toast.makeText(getContext(), Warnings.internetProblemText, Toast.LENGTH_LONG).show();
            }
        });
    }

}
