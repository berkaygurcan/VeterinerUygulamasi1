package com.example.asus.veterineruygulamasi1.RestApi;

import com.example.asus.veterineruygulamasi1.Models.AnswersModel;
import com.example.asus.veterineruygulamasi1.Models.AsiModel;
import com.example.asus.veterineruygulamasi1.Models.AskQuestionModel;
import com.example.asus.veterineruygulamasi1.Models.DeleteAnswerModel;
import com.example.asus.veterineruygulamasi1.Models.KampanyaModel;
import com.example.asus.veterineruygulamasi1.Models.LoginModel;
import com.example.asus.veterineruygulamasi1.Models.PetModel;
import com.example.asus.veterineruygulamasi1.Models.RegisterPojo;

import java.util.List;

import retrofit2.Call;

public class ManagerAll extends BaseManager {

    private static ManagerAll ourInstance = new ManagerAll();

    public static synchronized ManagerAll getInstance() {
        return ourInstance;
    }

    public Call<RegisterPojo> kayitOl(String mail, String kadi, String parola) {
        Call<RegisterPojo> x = getRestApi().registerUser(mail, kadi, parola);
        return x;
    }

    public Call<LoginModel> girisYap(String mail, String parola) {
        Call<LoginModel> x = getRestApi().loginUser(mail, parola);
        return x;
    }

    public Call<List<PetModel>> getPets(String mus_id) {
        Call<List<PetModel>> x = getRestApi().getPets(mus_id);
        return x;
    }

    public Call<AskQuestionModel> soruSor(String id, String soru) {
        Call<AskQuestionModel> x = getRestApi().soruSor(id, soru);
        return x;
    }

    public Call<List<AnswersModel>> getAnswers(String mus_id) {
        Call<List<AnswersModel>> x = getRestApi().getAnswers(mus_id);
        return x;
    }

    public Call<DeleteAnswerModel> deleteAnswer(String cevap, String soru) {
        Call<DeleteAnswerModel> x = getRestApi().deleteAnswer(cevap, soru);
        return x;
    }

    public Call<List<KampanyaModel>> getKampanya() {
        Call<List<KampanyaModel>> x = getRestApi().getKampanya();
        return x;
    }
    public Call<List<AsiModel>> getAsi(String id) {
        Call<List<AsiModel>> x = getRestApi().getAsi(id);
        return x;
    }
    public Call<List<AsiModel>> getGecmisAsi(String id,String petid) {
        Call<List<AsiModel>> x = getRestApi().getGecmisAsi(id,petid);
        return x;
    }

}
