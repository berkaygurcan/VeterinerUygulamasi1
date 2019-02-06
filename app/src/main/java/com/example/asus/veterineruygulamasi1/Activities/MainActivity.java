package com.example.asus.veterineruygulamasi1.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.asus.veterineruygulamasi1.Fragments.HomeFragment;
import com.example.asus.veterineruygulamasi1.Models.RegisterPojo;
import com.example.asus.veterineruygulamasi1.R;
import com.example.asus.veterineruygulamasi1.RestApi.ManagerAll;
import com.example.asus.veterineruygulamasi1.Utils.ChangeFragments;
import com.example.asus.veterineruygulamasi1.Utils.GetSharedPreferences;
import com.example.asus.veterineruygulamasi1.Utils.Warnings;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences getSharedPrefences;
    private GetSharedPreferences preferences;
    private ImageView anasayfaButon,aramaYapButon,cikisYap;
    private ChangeFragments changeFragments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getFragment();
        tanimla();
        kontrol();
        action();
    }

    private void getFragment() {
        changeFragments=new ChangeFragments(MainActivity.this);
        changeFragments.change(new HomeFragment());
    }
    @Override
    public void onBackPressed() {
    }

    public  void tanimla(){

        preferences=new GetSharedPreferences(MainActivity.this);
       getSharedPrefences= preferences.getsession();
        anasayfaButon=findViewById(R.id.anasayfaButon);
        aramaYapButon=findViewById(R.id.aramaYapButon);
        cikisYap=findViewById(R.id.cikisYap);


    }

    public void action(){
        anasayfaButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragments.change(new HomeFragment());
            }
        });
        cikisYap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   preferences.getsession().edit().clear().commit();//sharedpreferances temizleme böylede olur
                preferences.deleteToSession();
                Intent ıntent=new Intent(MainActivity.this,MainActivity.class);
                startActivity(ıntent);
            }
        });
        aramaYapButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ıntent=new Intent(Intent.ACTION_VIEW);
                ıntent.setData(Uri.parse("tel:05377218383"));
                startActivity(ıntent);
            }
        });
    }
    public void kontrol(){

        if(getSharedPrefences.getString("id",null)==null && getSharedPrefences.getString("username",null)==null
                && getSharedPrefences.getString("mailadres",null)==null)
        {
            Intent ıntent=new Intent(MainActivity.this,GirisYapActivity.class);
            startActivity(ıntent);
            finish();

        }
    }

}
