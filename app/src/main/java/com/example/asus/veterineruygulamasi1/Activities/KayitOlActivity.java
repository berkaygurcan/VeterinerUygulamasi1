package com.example.asus.veterineruygulamasi1.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.veterineruygulamasi1.Models.RegisterPojo;
import com.example.asus.veterineruygulamasi1.R;
import com.example.asus.veterineruygulamasi1.RestApi.ManagerAll;
import com.example.asus.veterineruygulamasi1.Utils.Warnings;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KayitOlActivity extends AppCompatActivity {
    private Button kayitOlButon;
    private EditText registerPassword, registerUserName, registerMailAdress;
    private TextView registerText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit_ol);
        tanimla();
        registerToUser();
        changeActivity();
    }
    public void tanimla(){

        kayitOlButon=findViewById(R.id.kayitOlButon);
        registerPassword=findViewById(R.id.registerPassword);
        registerUserName=findViewById(R.id.registerUserName);
        registerMailAdress=findViewById(R.id.registerMailAdress);
        registerText=findViewById(R.id.registerText);

    }
    public void registerToUser(){

        kayitOlButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mail=registerMailAdress.getText().toString();
                String userN=registerUserName.getText().toString();
                String pass=registerPassword.getText().toString();
                Log.i("deneme","bura çalıştı butona tıklama"+mail+userN+pass);
                register(mail,userN,pass);
            }
        });
    }
    public void changeActivity(){

        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ıntent=new Intent(KayitOlActivity.this,GirisYapActivity.class);
                startActivity(ıntent);
                finish();
            }
        });

    }
    public void register(String userMailAdres,String userName,String userPass){

        Log.i("deneme","bura çalıştı"+userMailAdres+userName+userPass);

        final Call<RegisterPojo> request= ManagerAll.getInstance().kayitOl(userMailAdres,userName,userPass);
        request.enqueue(new Callback<RegisterPojo>() {
            @Override
            public void onResponse(Call<RegisterPojo> call, Response<RegisterPojo> response) {
                if(response.body().isTf()){

                    Toast.makeText(getApplicationContext(),response.body().getText(),Toast.LENGTH_LONG).show();
                    Intent ıntent=new Intent(KayitOlActivity.this,GirisYapActivity.class);
                    startActivity(ıntent);
                    finish();

                }else{

                    Toast.makeText(getApplicationContext(),response.body().getText(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterPojo> call, Throwable t) {
                Toast.makeText(getApplicationContext(), Warnings.internetProblemText,Toast.LENGTH_LONG).show();

            }
        });
    }
}
