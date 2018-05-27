package com.example.asus.go_paradmin.presenter;

import com.example.asus.go_paradmin.data.local.SaveUserData;
import com.example.asus.go_paradmin.data.network.RetrofitClient;
import com.example.asus.go_paradmin.ui.addreminder.AddReminderView;
import com.example.asus.go_paradmin.ui.tips.AddTipsView;
import com.google.gson.JsonObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddReminderPresenter {
    private AddReminderView addReminderView;

    public AddReminderPresenter(AddReminderView addReminderView){
        this.addReminderView = addReminderView;
    }
    public void postReminder(String reminderTitle, String reminderDescription, String reminderYes, String reminderNo, String reminderAge, RequestBody filename, MultipartBody.Part fileToUpload){
        RetrofitClient.getInstance()
                .getApi()
                .postReminder(SaveUserData.getInstance().getUser().getId(),
                        reminderTitle,
                        reminderDescription,
                        reminderYes,
                        reminderNo,
                        reminderAge,
                        filename,
                        fileToUpload)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.isSuccessful()){
                            JsonObject body = response.body();
                            boolean status = body.get("status").getAsBoolean();
                            String message = body.get("messages").getAsString();
                            if(status){
                                addReminderView.onSuccessAddReminder();
                            }else{
                                addReminderView.onFailureAddReminder(message);
                            }
                        }else{
                            addReminderView.onFailureAddReminder("Add Reminder Failed");
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        addReminderView.onFailureAddReminder(t.getMessage());
                    }
                });

    }

}
