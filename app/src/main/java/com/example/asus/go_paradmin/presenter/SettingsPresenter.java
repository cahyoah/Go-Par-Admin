package com.example.asus.go_paradmin.presenter;

import com.example.asus.go_paradmin.data.local.SaveUserData;
import com.example.asus.go_paradmin.data.model.User;
import com.example.asus.go_paradmin.data.network.RetrofitClient;
import com.example.asus.go_paradmin.ui.settings.SettingsView;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsPresenter {
    private SettingsView settingsView;

    public SettingsPresenter(SettingsView settingsView){
        this.settingsView = settingsView;
    }

    public void updatePassword(String currentPassword, String newPassword){
        RetrofitClient.getInstance()
                .getApi()
                .updatePassword(SaveUserData.getInstance().getUser().getId(), currentPassword, newPassword)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.isSuccessful()){
                            JsonObject body = response.body();
                            boolean status = body.get("status").getAsBoolean();
                            String message = body.get("messages").getAsString();
                            if(status){
                                settingsView.onSuccessUpdatePassword();
                            }else{
                                settingsView.onFailedUpdatePassword(message);
                            }
                        }else{
                            settingsView.onFailedUpdatePassword("Add Tips Failed");
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        settingsView.onFailedUpdatePassword(t.getMessage());
                    }
                });
    }

    public void getUserData(){
        User user = SaveUserData.getInstance().getUser();
        if(user !=null){
            settingsView.onSuccessShowUserData(user);
        }else{
            settingsView.onFailedShowUserData();
        }

    }
}
