package com.example.asus.veterineruygulamasi1.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.button.MaterialButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.asus.veterineruygulamasi1.Adapters.AnswersAdapter;
import com.example.asus.veterineruygulamasi1.Models.AnswersModel;
import com.example.asus.veterineruygulamasi1.Models.AskQuestionModel;
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


public class HomeFragment extends Fragment {

    private View view;
    private LinearLayout petlerimLayout, sorusorlinearlayout,cevapLayout,kampanyaLinearLayout,asiTakipLayout,sanalKarneLayout;
    private ChangeFragments changeFragments;
    private GetSharedPreferences getSharedPreferences;
    private String mus_id;
    private AnswersAdapter answersAdapter;
    private List<AnswersModel> answersList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        tanimla();
        action();

        return view;
    }

    public void tanimla() {

        petlerimLayout = view.findViewById(R.id.petlerimLayout);
        sorusorlinearlayout = view.findViewById(R.id.sorusorlinearlayout);
        cevapLayout = view.findViewById(R.id.cevapLayout);
        kampanyaLinearLayout=view.findViewById(R.id.kampanyaLinearLayout);
        asiTakipLayout=view.findViewById(R.id.asiTakipLayout);
        sanalKarneLayout=view.findViewById(R.id.sanalKarneLayout);

        answersList=new ArrayList<>();
        changeFragments = new ChangeFragments(getContext());
        getSharedPreferences = new GetSharedPreferences(getActivity());
        mus_id = getSharedPreferences.getsession().getString("id", null);

    }

    public void action() {
        petlerimLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                changeFragments.change(new UserPetsFragment());
            }
        });
        sorusorlinearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openQuestionAlert();
            }
        });

        cevapLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAnswers(mus_id);

            }
        });

        kampanyaLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragments.change(new KampanyaFragment());
            }
        });
        asiTakipLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragments.change(new AsiFragment());
            }
        });
        sanalKarneLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragments.change(new SanalKarnePetlerFragment());
            }
        });
    }


    public void openQuestionAlert() {
        //alert diyalog acilması icin kodlama yapmamız lazım
        LayoutInflater layoutInflater = this.getLayoutInflater();//?
        View view = layoutInflater.inflate(R.layout.sorusoralertlayout, null);

        final EditText sorusorEditText = view.findViewById(R.id.sorusorEditText);
        MaterialButton sorusorButon = (MaterialButton) view.findViewById(R.id.sorusorButon);

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setView(view);
        alert.setCancelable(true);
        //artık alert dialogumuzu açabiliriz
        final AlertDialog alertDialog = alert.create();

        sorusorButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String soru = sorusorEditText.getText().toString();
                sorusorEditText.setText("");

                alertDialog.cancel();
                askQuestion(mus_id, soru,alertDialog);
            }
        });
        alertDialog.show();

    }
    public void openAnswerAlert() {
        //alert diyalog acilması icin kodlama yapmamız lazım
        LayoutInflater layoutInflater = this.getLayoutInflater();//?
        View view = layoutInflater.inflate(R.layout.cevapalertlayout, null);

        RecyclerView cevapRecylerView=view.findViewById(R.id.cevapRecylerView);

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setView(view);
        alert.setCancelable(true);
        //artık alert dialogumuzu açabiliriz
        final AlertDialog alertDialog = alert.create();
        cevapRecylerView.setLayoutManager(new GridLayoutManager(getContext(),1));
        cevapRecylerView.setAdapter(answersAdapter);

        alertDialog.show();

    }


    public void askQuestion(String mus_id, String text, final AlertDialog alr) {

        final Call<AskQuestionModel> req = ManagerAll.getInstance().soruSor(mus_id, text);
        req.enqueue(new Callback<AskQuestionModel>() {
            @Override
            public void onResponse(Call<AskQuestionModel> call, Response<AskQuestionModel> response) {
                if (response.body().isTf()) {//yani soru başarılı bir şekilde iletildiyse
                    Toast.makeText(getContext(), response.body().getText(), Toast.LENGTH_LONG).show();
                    alr.cancel();

                } else {
                    Toast.makeText(getContext(), response.body().getText(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AskQuestionModel> call, Throwable t) {
                Toast.makeText(getContext(), Warnings.internetProblemText, Toast.LENGTH_LONG).show();

            }
        });

    }

    public void getAnswers(String mus_id){

        Call<List<AnswersModel>> req=ManagerAll.getInstance().getAnswers(mus_id);
        req.enqueue(new Callback<List<AnswersModel>>() {
            @Override
            public void onResponse(Call<List<AnswersModel>> call, Response<List<AnswersModel>> response) {
                if(response.body().get(0).isTf()){
                    if(response.isSuccessful()){
                        answersList=response.body();
                        answersAdapter =new AnswersAdapter(answersList,getContext());
                        openAnswerAlert();//başarılıysa direk açılsın

                    }


                }
                else{

                    Toast.makeText(getContext(),"Herhangibir cevap yok...",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<List<AnswersModel>> call, Throwable t) {

                Toast.makeText(getContext(),Warnings.internetProblemText,Toast.LENGTH_LONG).show();
            }
        });

    }

}
