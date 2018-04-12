package com.example.asus.go_paradmin.presenter;

import com.example.asus.go_paradmin.data.model.Reminder;
import com.example.asus.go_paradmin.data.model.Tips;
import com.example.asus.go_paradmin.data.network.RetrofitClient;
import com.example.asus.go_paradmin.ui.detailreminder.DetailReminderView;
import com.example.asus.go_paradmin.ui.detailtips.DetailTipsView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailReminderPresenter {
    private DetailReminderView detailReminderView;

    public DetailReminderPresenter (DetailReminderView detailReminderView){
        this.detailReminderView = detailReminderView;
    }

    public void showDetailReminder(int reminderId){
        RetrofitClient.getInstance()
                .getApi()
                .showDetailReminder(reminderId)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.isSuccessful()){
                            JsonObject body = response.body();
                            boolean status = body.get("status").getAsBoolean();
                            String message = body.get("messages").getAsString();
                            if(status){
                                JsonObject reminderObject = body.get("data").getAsJsonObject();
                                Type type = new TypeToken<Reminder>(){}.getType();
                                Reminder reminder = new Gson().fromJson(reminderObject, type);
                                detailReminderView.onSuccessShowDetailReminder(reminder);
                            }else{
                                detailReminderView.onFailureShowDetailReminder(message);
                            }
                        }else{
                            detailReminderView.onFailureShowDetailReminder("Pengambilan data gagal");
                        }

                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        detailReminderView.onFailureShowDetailReminder(t.getMessage());
                    }
                });
    }

    public void deleteReminder(int reminderId){
        RetrofitClient.getInstance()
                .getApi()
                .deleteReminder(reminderId)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.isSuccessful()){
                            JsonObject body = response.body();
                            boolean status = body.get("status").getAsBoolean();
                            String message = body.get("messages").getAsString();
                            if(status){
                                detailReminderView.onSuccessDeleteReminder();
                            }else{
                                detailReminderView.onFailureDeleteReminder(message);
                            }
                        }else{
                            detailReminderView.onFailureDeleteReminder("Penghapusan data gagal");
                        }

                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        detailReminderView.onFailureDeleteReminder(t.getMessage());
                    }
                });
    }

}
