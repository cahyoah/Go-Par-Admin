package com.example.asus.go_paradmin.presenter;

import com.example.asus.go_paradmin.data.local.SaveUserData;
import com.example.asus.go_paradmin.data.local.SessionLogin;
import com.example.asus.go_paradmin.data.model.Fraggment;
import com.example.asus.go_paradmin.data.model.User;
import com.example.asus.go_paradmin.data.network.RetrofitClient;
import com.example.asus.go_paradmin.ui.home.HomeView;
import com.example.asus.go_paradmin.ui.home.reminder.ReminderFragment;
import com.example.asus.go_paradmin.ui.home.tips.TipsFragment;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePresenter {
    private HomeView homeView;
    public HomePresenter(HomeView homeView){
        this.homeView = homeView;
    }

    public void showFragmentList(){
        ArrayList<Fraggment> fraggmentArrayList = new ArrayList<>();
        fraggmentArrayList.add(new Fraggment(new ReminderFragment(), "Reminder"));
        fraggmentArrayList.add(new Fraggment(new TipsFragment(), "Tips"));

        homeView.showFragment(fraggmentArrayList);
    }

    public void logout(){
        RetrofitClient.getInstance()
                .getApi()
                .logout("exit")
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.isSuccessful()){
                            JsonObject body = response.body();
                            boolean status = body.get("status").getAsBoolean();
                            if(status){
                                SessionLogin.getInstance().setLoginStatus(false);
                                homeView.onSuccessLogout();
                            }else{
                                homeView.onFailureLogout("Username atau Password Salah");
                            }
                        }else{
                            homeView.onFailureLogout("Login Failed");
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        homeView.onFailureLogout("Login Failed");
                    }
                });
    }


}

