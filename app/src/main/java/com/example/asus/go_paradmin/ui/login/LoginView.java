package com.example.asus.go_paradmin.ui.login;

import com.example.asus.go_paradmin.data.model.User;

public interface LoginView {
    void onFailureLogin(String message);

    void onSuccessLogin(User user);

    void onCheckLogin(boolean loginStatus);
}
