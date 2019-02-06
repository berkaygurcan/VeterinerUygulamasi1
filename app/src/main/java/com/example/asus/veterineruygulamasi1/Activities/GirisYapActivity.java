package com.example.asus.veterineruygulamasi1.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.veterineruygulamasi1.Models.LoginModel;
import com.example.asus.veterineruygulamasi1.R;
import com.example.asus.veterineruygulamasi1.RestApi.ManagerAll;
import com.example.asus.veterineruygulamasi1.Utils.GetSharedPreferences;
import com.example.asus.veterineruygulamasi1.Utils.Warnings;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GirisYapActivity extends AppCompatActivity {
    private EditText loginMailAdress, loginPassword;
    private TextView loginText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris_yap);
        tanimla();
        click();
    }

    public void tanimla() {

        loginMailAdress = findViewById(R.id.loginMailAdress);
        loginPassword = findViewById(R.id.loginPassword);
        loginText = findViewById(R.id.loginText);
        loginButton = findViewById(R.id.loginButton);

    }

    public void delete() {
        loginMailAdress.setText("");
        loginPassword.setText("");

    }

    public void click() {

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = loginMailAdress.getText().toString();
                String pass = loginPassword.getText().toString();
                login(mail, pass);
                delete();

            }
        });
        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ıntent = new Intent(GirisYapActivity.this, KayitOlActivity.class);
                startActivity(ıntent);
                finish();
            }
        });

    }

    public void login(String mailAdres, String parola) {

        Call<LoginModel> request = ManagerAll.getInstance().girisYap(mailAdres, parola);
        request.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {

                if (response.body().isTf()) {

                    Toast.makeText(getApplicationContext(),response.body().getText(),Toast.LENGTH_LONG).show();
                    Intent ıntent = new Intent(GirisYapActivity.this, MainActivity.class);
                    GetSharedPreferences getSharedPreferences=new GetSharedPreferences(GirisYapActivity.this);
                    getSharedPreferences.setSession(response.body().getId(),response.body().getUsername(),response.body().getMailadres());
                    startActivity(ıntent);
                    finish();


                } else {

                    Toast.makeText(getApplicationContext(),response.body().getText(),Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), Warnings.internetProblemText, Toast.LENGTH_LONG).show();
            }
        });


    }
}
