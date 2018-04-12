package com.example.asus.go_paradmin.presenter;

import com.example.asus.go_paradmin.data.local.SaveUserData;
import com.example.asus.go_paradmin.data.local.SessionLogin;
import com.example.asus.go_paradmin.data.model.Tips;
import com.example.asus.go_paradmin.data.network.RetrofitClient;
import com.example.asus.go_paradmin.ui.home.tips.TipsView;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TipsPresenter {
    private TipsView tipsView;

    public TipsPresenter(TipsView tipsView){
        this.tipsView = tipsView;
    }

    public void showAllTips(){
        RetrofitClient.getInstance()
                .getApi()
                .showAllTips()
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.isSuccessful()){
                            JsonObject body = response.body();
                            boolean status = body.get("status").getAsBoolean();
                            if(status){
                                JsonArray transactionArray = body.get("data").getAsJsonArray();
                                Type type = new TypeToken<List<Tips>>(){}.getType();
                                List<Tips> tipsList =  new Gson().fromJson(transactionArray, type);
                                tipsView.onSuccessShowAllTips(tipsList);
                            }else{
                                tipsView.onFailureShowAllTips(body.get("message").getAsString());
                            }
                        }else{
                            tipsView.onFailureShowAllTips("No Data Found");
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        tipsView.onFailureShowAllTips("Server Error");
                    }
                });


    }



}
