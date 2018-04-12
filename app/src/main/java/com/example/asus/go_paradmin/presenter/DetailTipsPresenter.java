package com.example.asus.go_paradmin.presenter;

import com.example.asus.go_paradmin.data.local.SaveUserData;
import com.example.asus.go_paradmin.data.local.SessionLogin;
import com.example.asus.go_paradmin.data.model.Tips;
import com.example.asus.go_paradmin.data.model.User;
import com.example.asus.go_paradmin.data.network.RetrofitClient;
import com.example.asus.go_paradmin.ui.detailtips.DetailTipsView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailTipsPresenter {
    private DetailTipsView detailTipsView;

    public DetailTipsPresenter (DetailTipsView detailTipsView){
        this.detailTipsView = detailTipsView;
    }

    public void showDetailTips(int tipsId){
        RetrofitClient.getInstance()
                .getApi()
                .showDetailTips(tipsId)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.isSuccessful()){
                            JsonObject body = response.body();
                            boolean status = body.get("status").getAsBoolean();
                            String message = body.get("messages").getAsString();
                            if(status){
                                JsonObject tipsObject = body.get("data").getAsJsonObject();
                                Type type = new TypeToken<Tips>(){}.getType();
                                Tips tips = new Gson().fromJson(tipsObject, type);
                                detailTipsView.onSuccessShowDetailTips(tips);
                            }else{
                                detailTipsView.onFailureShowDetailTips(message);
                            }
                        }else{
                            detailTipsView.onFailureShowDetailTips("Pengambilan data gagal");
                        }

                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        detailTipsView.onFailureShowDetailTips(t.getMessage());
                    }
                });
    }

    public void deleteTips(int tipsId){
        RetrofitClient.getInstance()
                .getApi()
                .deleteTips(tipsId)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.isSuccessful()){
                            JsonObject body = response.body();
                            boolean status = body.get("status").getAsBoolean();
                            String message = body.get("messages").getAsString();
                            if(status){
                                detailTipsView.onSuccessDeleteTips();
                            }else{
                                detailTipsView.onFailureDeleteTips(message);
                            }
                        }else{
                            detailTipsView.onFailureDeleteTips("Penghapusan data gagal");
                        }

                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        detailTipsView.onFailureDeleteTips(t.getMessage());
                    }
                });
    }

    public void updateTips(int tipsId, String tipsTitle, String tipsDescription){
        RetrofitClient.getInstance()
                .getApi()
                .updateTips(tipsId, SaveUserData.getInstance().getUser().getId(), tipsTitle, tipsDescription)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.isSuccessful()){
                            JsonObject body = response.body();
                            boolean status = body.get("status").getAsBoolean();
                            String message = body.get("messages").getAsString();
                            if(status){
                                JsonObject tipsObject = body.get("data").getAsJsonObject();
                                Type type = new TypeToken<Tips>(){}.getType();
                                Tips tips = new Gson().fromJson(tipsObject, type);
                                detailTipsView.onSuccessUpdateTips(tips);
                            }else{
                                detailTipsView.onFailureUpdateTips(message);
                            }
                        }else{
                            detailTipsView.onFailureUpdateTips("Pembaruan data gagal");
                        }

                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        detailTipsView.onFailureUpdateTips(t.getMessage());
                    }
                });
    }

}
