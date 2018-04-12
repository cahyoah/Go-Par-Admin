package com.example.asus.go_paradmin.presenter;

import com.example.asus.go_paradmin.data.model.Reminder;
import com.example.asus.go_paradmin.data.network.RetrofitClient;
import com.example.asus.go_paradmin.ui.home.reminder.ReminderView;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReminderPresenter {
    private ReminderView reminderView;

    public ReminderPresenter(ReminderView reminderView){
        this.reminderView = reminderView;
    }

    public void showAllReminder(){
        RetrofitClient.getInstance()
                .getApi()
                .showAllReminder()
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.isSuccessful()){
                            JsonObject body = response.body();
                            boolean status = body.get("status").getAsBoolean();
                            if(status){
                                JsonArray reminderArray = body.get("data").getAsJsonArray();
                                Type type = new TypeToken<List<Reminder>>(){}.getType();
                                List<Reminder> reminderList =  new Gson().fromJson(reminderArray, type);
                                reminderView.onSuccessShowAllReminder(reminderList);
                            }else{
                                reminderView.onFailureShowAllReminder(body.get("messages").getAsString());
                            }
                        }else{
                            reminderView.onFailureShowAllReminder("No Data Found");
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        reminderView.onFailureShowAllReminder("Server Error");
                    }
                });


    }
}
