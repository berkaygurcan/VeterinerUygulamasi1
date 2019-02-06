package com.example.asus.veterineruygulamasi1.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.button.MaterialButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.veterineruygulamasi1.Models.AsiModel;
import com.example.asus.veterineruygulamasi1.R;
import com.example.asus.veterineruygulamasi1.RestApi.ManagerAll;
import com.example.asus.veterineruygulamasi1.Utils.ChangeFragments;
import com.example.asus.veterineruygulamasi1.Utils.GetSharedPreferences;
import com.squareup.picasso.Picasso;
import com.squareup.timessquare.CalendarPickerView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AsiFragment extends Fragment {
    private View view;
    private CalendarPickerView calenderPickerView;
    private DateFormat format;
    private Calendar nextYear;//java utilden date ve calander i secmeliyiz
    private Date today;
    private List<AsiModel> asiList;
    private List<Date> dateList;
    private ChangeFragments changeFragments;
    private String id;
    private GetSharedPreferences getSharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_asi, container, false);
        tanimla();
        getAsi();
        clickToCalendar();
        return view;
    }

    public void tanimla() {

        changeFragments = new ChangeFragments(getContext());
        calenderPickerView = view.findViewById(R.id.calenderPickerView);
        format = new SimpleDateFormat("dd/MM/yyyy");
        //şimdi picker view da neleri gösterecegiz biz bi sene sonraya kadar gösterelim
        nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);//1 yıl sonrasına kadar göster
        today = new Date();


        //gösterme işlemi için
        calenderPickerView.init(today, nextYear.getTime());//gettime ile date formatına aldık

        asiList = new ArrayList<>();
        dateList = new ArrayList<>();
        getSharedPreferences=new GetSharedPreferences(getActivity());
        id=getSharedPreferences.getsession().getString("id",null);

    }

    public void getAsi() {

        final Call<List<AsiModel>> req = ManagerAll.getInstance().getAsi(id);
        req.enqueue(new Callback<List<AsiModel>>() {
            @Override
            public void onResponse(Call<List<AsiModel>> call, Response<List<AsiModel>> response) {
                if (response.isSuccessful())//response işlemi başarı şekilde bittiyse
                {
                    if (response.body().get(0).isTf()) {
                        //burada ilk liste olusturup picker view ı doldurmamız gerekli.
                        //Pickerview date formatında bir liste alır
                        asiList = response.body();
                        for (int i = 0; i < asiList.size(); i++) {
                            String dataString = response.body().get(i).getAsitarih().toString();
                            try {
                                Date date = format.parse(dataString);//format bize date döndürüyodu ondan date değişkeni olusturduk
                                //ve en son bu parse ettiğimiz değeri date değişkeni vasıtasıyla datelistemize ekledik
                                dateList.add(date);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        calenderPickerView.init(today, nextYear.getTime())
                                .withHighlightedDates(dateList);//bu fonk işaretli gelmesini sağlar parametre içinde tarihlerin


                    }
                } else {
                    changeFragments.change(new HomeFragment());
                    Toast.makeText(getContext(), "Petinize Ait Aşı Gelecek Tarihte Yoktur...", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<List<AsiModel>> call, Throwable t) {

            }
        });


    }

    public void clickToCalendar() {
        calenderPickerView.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                for (int i = 0; i < dateList.size(); i++) {
                    if (date.toString().equals(dateList.get(i).toString())) {
                        openQuestionAlert(asiList.get(i).getPetisim().toString(),asiList.get(i).getAsitarih().toString()
                                ,asiList.get(i).getAsiisim().toString(), asiList.get(i).getPetresim().toString());
                    }
                }
            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });

    }

    public void openQuestionAlert(String petIsmi,String tarih,String asiIsmi,String resimUrl) {
        //alert diyalog acilması icin kodlama yapmamız lazım
        LayoutInflater layoutInflater = this.getLayoutInflater();//?
        View view = layoutInflater.inflate(R.layout.asitakiplayout, null);

        TextView petİsimText =view.findViewById(R.id.petİsimText);
        TextView petAsiTakipBilgiText=view.findViewById(R.id.petAsiTakipBilgiText);
       CircleImageView asiTakipCircleImageView=view.findViewById(R.id.asiTakipCircleImageView);

        petİsimText.setText(petIsmi);
        petAsiTakipBilgiText.setText(petIsmi+" isimli petinizin "+tarih+" tarihinde "+asiIsmi+" aşısı yapılacaktır.");
        Picasso.get().load(resimUrl).into(asiTakipCircleImageView);

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setView(view);
        alert.setCancelable(true);
        //artık alert dialogumuzu açabiliriz
        final AlertDialog alertDialog = alert.create();


        alertDialog.show();
        }

}
