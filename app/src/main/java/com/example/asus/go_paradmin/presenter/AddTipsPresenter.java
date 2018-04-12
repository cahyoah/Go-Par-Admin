package com.example.asus.go_paradmin.presenter;

import com.example.asus.go_paradmin.data.local.SaveUserData;
import com.example.asus.go_paradmin.data.network.RetrofitClient;
import com.example.asus.go_paradmin.ui.home.tips.TipsView;
import com.example.asus.go_paradmin.ui.tips.AddTipsView;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTipsPresenter {
    private AddTipsView addTipsView;

    public AddTipsPresenter(AddTipsView addTipsView){
        this.addTipsView = addTipsView;
    }

    public void postTips(String tipsTitle, String tipsDescription){
        RetrofitClient.getInstance()
                .getApi()
                .postTips(SaveUserData.getInstance().getUser().getId(),
                        tipsTitle,
                        tipsDescription)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.isSuccessful()){
                            JsonObject body = response.body();
                            boolean status = body.get("status").getAsBoolean();
                            String message = body.get("messages").getAsString();
                            if(status){
                                addTipsView.onSuccessAddTips();
                            }else{
                                addTipsView.onFailureAddTips(message);
                            }
                        }else{
                            addTipsView.onFailureAddTips("Add Tips Failed");
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        addTipsView.onFailureAddTips(t.getMessage());
                    }
                });

    }
}
