package com.example.asus.go_paradmin.presenter;

import android.util.Base64;

import com.example.asus.go_paradmin.data.local.SaveUserData;
import com.example.asus.go_paradmin.data.local.SessionLogin;
import com.example.asus.go_paradmin.data.model.User;
import com.example.asus.go_paradmin.data.network.RetrofitClient;
import com.example.asus.go_paradmin.ui.login.LoginView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter {
    private LoginView loginView;
    public LoginPresenter(LoginView loginView){
        this.loginView = loginView;
    }

    public void checkLogin(){
        loginView.onCheckLogin(SessionLogin.getInstance().getLoginStatus());
    }
    public void login(String username, String password){
        RetrofitClient.getInstance()
                .getApi()
                .login(username, password)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.isSuccessful()){
                            JsonObject body = response.body();
                            boolean status = body.get("status").getAsBoolean();
                            if(status){
                                JsonObject userObject = body.get("data").getAsJsonObject();
                                Type type = new TypeToken<User>(){}.getType();
                                User user = new Gson().fromJson(userObject, type);
                                if(user.getUserAccess().equals("Admin")){
                                    SaveUserData.getInstance().saveUser(user);
                                    SessionLogin.getInstance().setLoginStatus(true);
                                    SaveUserData.getInstance().saveUserToken(setUserAuth(Integer.toString(user.getId()), user.getApiToken()));
                                    loginView.onSuccessLogin(user);
                                }else{
                                    loginView.onFailureLogin("Username atau Password Salah");
                                }
                            }else{
                                loginView.onFailureLogin("Username atau Password Salah");
                            }
                        }else{
                            loginView.onFailureLogin("Login Failed");
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        loginView.onFailureLogin("Login Failed");
                    }
                });
    }

    public String setUserAuth(String id, String token) {
        byte[] encodedBytes;
        String authorization;
        authorization = id + ":" + token;
        encodedBytes = Base64.encode(authorization.getBytes(), Base64.NO_WRAP);
        authorization = "Basic " + new String(encodedBytes);
        return authorization;
    }
}
