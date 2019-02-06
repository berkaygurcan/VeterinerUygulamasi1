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

import com.example.asus.veterineruygulamasi1.Adapters.PetsAdapter;
import com.example.asus.veterineruygulamasi1.Adapters.SanalKarnePetAdapter;
import com.example.asus.veterineruygulamasi1.Models.PetModel;
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


public class SanalKarnePetlerFragment extends Fragment {
    private View view;
    private RecyclerView sanalKarnePetlerRecyView;
    private List<PetModel> petList;;
    private SanalKarnePetAdapter sanalKarnePetAdapter;
    ChangeFragments changeFragments;
    private  String mus_id;
    private GetSharedPreferences getSharedPreferences;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_sanal_karne_petler, container, false);
        tanimla();
        getPets(mus_id);
        return view;
    }
    public void tanimla() {

        petList = new ArrayList<>();
        sanalKarnePetlerRecyView = view.findViewById(R.id.sanalKarnePetlerRecyView);
        sanalKarnePetlerRecyView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        changeFragments = new ChangeFragments(getContext());
        getSharedPreferences=new GetSharedPreferences(getActivity());
        mus_id=getSharedPreferences.getsession().getString("id",null);

    }

    public void getPets(String mus_id) {

        Call<List<PetModel>> req = ManagerAll.getInstance().getPets(mus_id);
        req.enqueue(new Callback<List<PetModel>>() {
            @Override
            public void onResponse(Call<List<PetModel>> call, Response<List<PetModel>> response) {
                //  Log.i("listem", response.body().toString());
                if (response.body().get(0).isTf()) {
                    petList = response.body();
                    sanalKarnePetAdapter = new SanalKarnePetAdapter(petList, getContext());
                    sanalKarnePetlerRecyView.setAdapter(sanalKarnePetAdapter);

                 //   Toast.makeText(getContext(), "Sistemde kayıtlı " + petList.size() + " petiniz bulunmaktadır.", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(getContext(), "Sistemde kayıtlı petiniz bulunmamaktadır.", Toast.LENGTH_LONG).show();
                    changeFragments.change(new HomeFragment());

                }


            }

            @Override
            public void onFailure(Call<List<PetModel>> call, Throwable t) {
                Toast.makeText(getContext(), Warnings.internetProblemText,Toast.LENGTH_LONG).show();
            }
        });


    }

}
